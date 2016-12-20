package nl.pdok.catalog.jobconfig;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class JobConfigurationReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobConfigurationReader.class);
    
    public static JobConfiguration read(File configurationFile, String datasetName) {
        try {
            return new ObjectMapper().readValue(configurationFile, JobConfiguration.class);
        } catch (Exception e) {
            LOGGER.warn("Cannot read JobConfiguration for {}. (File={})", datasetName, configurationFile);
            throw new IllegalStateException("Cannot load configuration for dataset " + datasetName + ": " + e.getMessage(), e);
        }
    }
}