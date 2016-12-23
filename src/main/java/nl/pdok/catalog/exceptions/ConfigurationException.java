package nl.pdok.catalog.exceptions;

/**
 * Created by stroej on 22-12-2016.
 */
public class ConfigurationException extends Exception {

    public ConfigurationException(String msg, Exception e) {
        super (msg, e);
    }

    public ConfigurationException(String msg) {
        super(msg);
    }
}

