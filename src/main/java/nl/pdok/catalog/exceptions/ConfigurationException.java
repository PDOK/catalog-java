package nl.pdok.catalog.exceptions;

public class ConfigurationException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2703721819177493053L;

	public ConfigurationException(String msg, Exception e) {
        super(msg, e);
    }

    public ConfigurationException(String msg) {
        super(msg);
    }
}
