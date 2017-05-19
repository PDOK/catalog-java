package nl.pdok.catalog.tiling;

import java.io.File;
import java.io.FileNotFoundException;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;

public class TilingConfigurationReader {

    private static final Logger LOGGER = Logger.getLogger(TilingConfigurationReader.class);
    private static final String ERROR = "Er is een fout opgetreden bij het inlezen en/of parsen van de json-"
            + "configuratie uit bestand %s";
    private static final String NO_FILE = "Er is geen tiling.json bestand";

    public static TilingConfiguration read(File configurationFile, String datasetName) {
        TilingConfiguration configuration = null;
        try {
            configuration = new ObjectMapper().readValue(configurationFile, TilingConfiguration.class);
        } catch (FileNotFoundException e) {
            LOGGER.info(NO_FILE);
        } catch (Exception e) {
            LOGGER.error(String.format(ERROR, configurationFile.getAbsolutePath()), e);
        } finally {
            if (configuration == null) {
                configuration = new TilingConfiguration();
                configuration.setWorkspace(datasetName);
                configuration.setLayers(new TilingLayer[0]); //initialize with empty array
            }
        }
        return configuration;
    }
}
