package nl.pdok.catalog.gmlconverter;

import java.io.File;
import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.pdok.catalog.exceptions.FileReaderException;
import nl.pdok.catalog.util.FileReaderUtil;

public class GmlConverterConfigReader {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GmlConverterConfigReader.class);

    private static final String FILE_NAME = "configuration.json";
    
    private String basePath;
    
    public GmlConverterConfigReader(File catalogusFolder) {
        basePath = catalogusFolder.getPath().concat(File.separator).concat("datasets");
    }

    public GmlConverterConfig retrieveGmlConverterConfigFromCatalogus(String datasetName) throws FileReaderException {
        String filePath = basePath + buildFilePathForDataset(datasetName);

        String fileAsString = FileReaderUtil.retrieveFileToStringFromFilePath(filePath, LOGGER);

        return parseStringToGmlConverterConfig(fileAsString);
    }

    private String buildFilePathForDataset(String datasetName) {
        StringBuilder pathStr = new StringBuilder();
        pathStr.append(File.separator);
        pathStr.append(datasetName);
        pathStr.append(File.separator);
        pathStr.append("gmlConverterConfig");
        pathStr.append(File.separator);
        pathStr.append(FILE_NAME);
        return pathStr.toString();
    }

    private GmlConverterConfig parseStringToGmlConverterConfig(String jsonString) throws FileReaderException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            GmlConverterConfigWrapper wrapper = mapper.readValue(jsonString, GmlConverterConfigWrapper.class);
            return wrapper.getGmlToJson();
        } catch (IOException e) {
            String errorMsg = "There was an error when attempting to parse the json file";
            LOGGER.error(errorMsg, e);
            throw new FileReaderException(errorMsg, e);
        }
    }
    
    private static class GmlConverterConfigWrapper {
        
        private GmlConverterConfig gmlToJson;

        public GmlConverterConfig getGmlToJson() {
            return gmlToJson;
        }

        public void setGmlToJson(GmlConverterConfig gmlToJson) {
            this.gmlToJson = gmlToJson;
        }
    }
}
