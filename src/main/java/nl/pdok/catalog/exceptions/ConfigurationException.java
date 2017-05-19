package nl.pdok.catalog.exceptions;

public class ConfigurationException extends Exception {

    public ConfigurationException(String msg, Exception e) {
        super(msg, e);
    }

    public ConfigurationException(String msg) {
        super(msg);
    }
}
