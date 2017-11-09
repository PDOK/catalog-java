package nl.pdok.catalog.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;

import nl.pdok.catalog.exceptions.FileReaderException;

public class FileReaderUtil {

    public static String retrieveFileToStringFromFilePath(String filePath, Logger logger)
            throws FileReaderException {
        File file = new File(filePath);
        try {
            return FileUtils.readFileToString(file);
        } catch (IOException e) {
            String errorMsg = "There was a problem reading the file: \"" + filePath + "\".";
            logger.error(errorMsg, e);
            throw new FileReaderException(errorMsg, e);
        }
    }

}
