/**
 * 
 */
package nl.pdok.catalog.job;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author henrya
 *
 */
public class GitInteractionsHandler {

	private static final String AUTHORIZATION = "Authorization";
	private static final String BASIC_AUTH_STRING = "Basic enpfcGRvazphYTliNGYwYjQ0MzVjYWMyZjIwZTEyNjVmYTBmYTI0MTc0MmYwODkz";
	private static final String BASE_GIT_PATH = "http://github.so.kadaster.nl/PDOK/catalogus/archive/";

	private static final Logger LOGGER = LoggerFactory.getLogger(GitInteractionsHandler.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (checkout("pdok-1263", new File("D:/tempDir/catalogus"))) {
			System.out.println("success");
		} else {
			System.out.println("Failure");
		}
	}

	public static boolean checkout(String branchName, File destinationFolder) {
		InputStream fileInputStream;
		try {
			fileInputStream = retrieveZipFromGit(branchName);
			clearCatalogusOldDirectory(destinationFolder.getParentFile());
			unpackZipIntoTempFolder(fileInputStream, destinationFolder.getParentFile());
		} catch (IOException e) {
			LOGGER.error("Something went wrong with retrieving and unpacking the zip for " + branchName + " from Git.", e);
			return false;
		}
		try {
			for (int i = 1; 1 < 5; i++) {
				if (renameFolders(destinationFolder, branchName)) {
					break;
				}
				Thread.sleep(200);
			}
		} catch (IOException e) {
			LOGGER.error("Something went wrong with renaming the Catalogus Folders.", e);
			return false;
		} catch (InterruptedException e) {
			LOGGER.error("InterruptedException on the sleep timer.", e);
			return false;
		}
		return true;
	}

	private static InputStream retrieveZipFromGit(String branchName) throws IOException {
		String url = BASE_GIT_PATH + branchName.trim() + ".zip";
		URL gitURL = new URL(url);
		HttpURLConnection httpConnection = (HttpURLConnection) gitURL.openConnection();
		httpConnection.setRequestProperty(AUTHORIZATION, BASIC_AUTH_STRING);

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

				System.out.println("file unzip : " + newFile.getAbsoluteFile());

				new File(newFile.getParent()).mkdirs();

				writeFile(buffer, zis, newFile);
			}
			ze = zis.getNextEntry();
		}

		zis.closeEntry();
		zis.close();
	}

	private static void writeFile(byte[] buffer, ZipInputStream zis, File newFile)
			throws FileNotFoundException, IOException {
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
		System.out.println(tempFolder.getAbsolutePath());
		System.out.println(oldFolder.getAbsolutePath());
		System.out.println(destinationFolder.getAbsolutePath());
		try {
			Files.move(destinationFolder.toPath(), oldFolder.toPath(), StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			return false;
		}
		try {
			Files.move(tempFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.ATOMIC_MOVE);
		} catch (IOException e) {
			Files.move(oldFolder.toPath(), destinationFolder.toPath(), StandardCopyOption.ATOMIC_MOVE);
			return false;
		}
		return true;
	}

}
