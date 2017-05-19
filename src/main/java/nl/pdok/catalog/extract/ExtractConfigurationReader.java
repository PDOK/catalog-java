package nl.pdok.catalog.extract;

import java.io.File;
import java.io.FileNotFoundException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class ExtractConfigurationReader {

    private static final Logger LOGGER = Logger.getLogger(ExtractConfigurationReader.class);
    private static final String ERROR = "Er is een fout opgetreden bij het inlezen en/of parsen van de json-"
            + "configuratie uit bestand %s";
    private static final String NO_FILE = "Er is geen extract.json bestand";

    public static ExtractConfiguration read(File configurationFile, String datasetName) {
        try {
            return new ObjectMapper().readValue(configurationFile, ExtractConfiguration.class);
        } catch (FileNotFoundException e) {
            LOGGER.info(NO_FILE);
            throw new IllegalStateException("Cannot load configuration for dataset " + datasetName + ": "
                    + e.getMessage(), e);
        } catch (Exception e) {
            LOGGER.error(String.format(ERROR, configurationFile.getAbsolutePath()), e);
            throw new IllegalStateException("Cannot load configuration for dataset " + datasetName + ": "
                    + e.getMessage(), e);
        }
    }
}
