package nl.pdok.catalog.jobentry;

import java.io.IOException;

public class JobEntryException extends Exception {

    public JobEntryException(String errorMsg, IOException e) {
        super(errorMsg, e);
    }

    private static final long serialVersionUID = -1238753271309357494L;

}
