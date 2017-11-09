package nl.pdok.catalog.util;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.slf4j.Logger;

import nl.pdok.catalog.exceptions.FileReaderException;

public class FileReaderUtil {

    public static String retrieveFileToStringFromFilePath(String filePath, Logger logger)
            throws FileReaderException {
        File file = new File(filePath);
        StringBuilder builder = new StringBuilder();

        try {
            for (String str : Files.readAllLines(file.toPath(), StandardCharsets.UTF_8)) {
                builder.append(str);
            }
        } catch (IOException e) {
            String errorMsg = "There was a problem reading the file: \"" + filePath + "\".";
            logger.error(errorMsg, e);
            throw new FileReaderException(errorMsg, e);
        }
        return builder.toString();
    }

}
