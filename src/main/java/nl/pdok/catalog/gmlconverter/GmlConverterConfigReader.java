package nl.pdok.catalog.gmlconverter;

import static nl.pdok.catalog.util.FileReaderUtil.retrieveFileToStringFromFilePath;

import java.io.File;
import java.io.IOException;
import nl.pdok.catalog.exceptions.FileReaderException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GmlConverterConfigReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(GmlConverterConfigReader.class);

    private static final String FILE_NAME = "configuration.json";

    private String basePath;

    public GmlConverterConfigReader(File catalogusFolder) {
        basePath = catalogusFolder.getPath().concat(File.separator).concat("datasets");
    }

    public GmlConverterConfig retrieveGmlConverterConfigFromCatalogus(String datasetName) throws FileReaderException {
        String filePath = basePath + buildFilePathForDataset(datasetName);

        String fileAsString = retrieveFileToStringFromFilePath(filePath, LOGGER);

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
        mapper.enable(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE);
        mapper.setSerializationInclusion(Inclusion.NON_NULL);
        try {
            return mapper.readValue(jsonString, GmlConverterConfig.class);
        } catch (IOException e) {
            String errorMsg = "There was an error when attempting to parse the json file";
            LOGGER.error(errorMsg, e);
            throw new FileReaderException(errorMsg, e);
        }
    }
}
