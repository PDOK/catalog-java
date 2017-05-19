package nl.pdok.catalog.transformation;

import java.io.File;
import nl.pdok.catalog.exceptions.ConfigurationException;
import org.codehaus.jackson.map.ObjectMapper;

public class TransformationConfigurationReader {

    public static TransformationConfiguration read(String datasetName, File configurationFile)
            throws ConfigurationException {
        if (!configurationFile.exists()) {
            throw new ConfigurationException(String.format("File with configuration for dataset %s cannot be found",
                    datasetName));
        } else {
            try {
                return new ObjectMapper().readValue(configurationFile, TransformationConfiguration.class);
            } catch (Exception objectMapperException) {
                throw new ConfigurationException(String.format("Configuration for dataset %s cannot be read/parsed",
                        datasetName), objectMapperException);
            }
        }
    }
}
