package nl.pdok.catalog.exceptions;

import java.io.IOException;

public class FileReaderException extends Exception {

    private static final long serialVersionUID = -1238753271309357494L;

    public FileReaderException(String errorMsg, IOException e) {
        super(errorMsg, e);
    }

}
