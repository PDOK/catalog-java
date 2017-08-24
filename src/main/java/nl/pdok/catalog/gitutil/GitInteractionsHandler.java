package nl.pdok.catalog.gitutil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GitInteractionsHandler {

    private static final String GIT_BRANCH_TXT = "gitBranch.txt";
    private static final String AUTHORIZATION = "Authorization";

    private static final Logger LOGGER = LoggerFactory.getLogger(GitInteractionsHandler.class);

    public static boolean isCatalogusPresent(File destinationFolder) {
        return destinationFolder.exists();
    }
    
    public static String whichBranchIsPresent(File destinationFolder) {
        File file = new File(destinationFolder.getPath() + File.separator + GIT_BRANCH_TXT);
        List<String> lines;
        try {
            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            return "Unable to read File.";
        }
        String output = "";
        for (String string : lines) {
            output += string;
        }
        return output;
    }

    public static boolean checkout(String gitAddress, String branchName, File destinationFolder, String authorization) {
        if (downloadAndUnpackZipFileFromGit(gitAddress + branchName, destinationFolder, authorization)) {
            try {
                for (int i = 1; i <= 5; i++) {
                    if (renameFolders(destinationFolder, branchName)) {
                        writeBranchNameToFile(branchName, destinationFolder);
                        return true;
                    }
                    Thread.sleep(200);
                }
            } catch (IOException e) {
                LOGGER.error("Something went wrong with renaming the Catalogus Folders.", e);
            } catch (InterruptedException e) {
                LOGGER.error("InterruptedException on the sleep timer.", e);
            }
        }
        return false;
    }

    private static void writeBranchNameToFile(String branchName, File destinationFolder) throws IOException {
        File file = new File(destinationFolder + File.separator + GIT_BRANCH_TXT);
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(branchName);
        writer.flush();
        writer.close();
    }

    private static boolean downloadAndUnpackZipFileFromGit(String branchName, File destinationFolder,
            String authorization) {
        InputStream fileInputStream;
        try {
            fileInputStream = retrieveZipFromGit(branchName, authorization);
            clearCatalogusOldDirectory(destinationFolder.getParentFile());
            unpackZipIntoTempFolder(fileInputStream, destinationFolder.getParentFile());
            return true;
        } catch (IOException e) {
            LOGGER.error("Something went wrong with retrieving and unpacking the zip for " + branchName + " from Git.",
                    e);
            return false;
        }
    }

    private static InputStream retrieveZipFromGit(String branchName, String authorization) throws IOException {
        String url = branchName.trim() + ".zip";
        URL gitUrl = new URL(url);
        HttpURLConnection httpConnection = (HttpURLConnection) gitUrl.openConnection();
        httpConnection.setRequestProperty(AUTHORIZATION, authorization);

        return httpConnection.getInputStream();
    }

    private static void clearCatalogusOldDirectory(File parentDirectory) throws IOException {
        File file = new File(parentDirectory + File.separator + "catalogus_old");
        if (file.exists()) {
            FileUtils.cleanDirectory(file);
            FileUtils.deleteDirectory(file);
        }
    }

    private static void unpackZipIntoTempFolder(InputStream inputStream, File parentDirectory) throws IOException {
        byte[] buffer = new byte[1024];

        if (!parentDirectory.exists()) {
            parentDirectory.mkdir();
        }

        ZipInputStream zis = new ZipInputStream(inputStream);
        ZipEntry ze = zis.getNextEntry();
        while (ze != null) {

            if (!ze.isDirectory()) {
                String fileName = ze.getName();
                File newFile = new File(parentDirectory + File.separator + fileName);

                new File(newFile.getParent()).mkdirs();

                writeFile(buffer, zis, newFile);
            }
            ze = zis.getNextEntry();
        }

        zis.closeEntry();
        zis.close();
    }

    private static void writeFile(byte[] buffer, ZipInputStream zis, File newFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(newFile);

        int len;
        while ((len = zis.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }

        fos.close();
    }

    private static boolean renameFolders(File destinationFolder, String branchName) throws IOException {
        File tempFolder = new File(destinationFolder.getParentFile() + File.separator + "catalogus-" + branchName);
        File oldFolder = new File(destinationFolder.getParentFile() + File.separator + "catalogus_old");
        try {
            if (destinationFolder.exists()) {
                Files.move(destinationFolder.toPath(), oldFolder.toPath(), StandardCopyOption.ATOMIC_MOVE);
            }
        } catch (IOException e) {
            LOGGER.info("Unable to move catalogus to _old.", e);
            return false;
        }
        try {
            Files.move(tempFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException e) {
            LOGGER.error("Failed to place new checkout in catalogus! Attempting to return current version.", e);
            Files.move(oldFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.ATOMIC_MOVE);
            return false;
        }
        return true;
    }
}
