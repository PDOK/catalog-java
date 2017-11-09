package nl.pdok.catalog.jobentry;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.pdok.catalog.exceptions.FileReaderException;
import nl.pdok.catalog.util.FileReaderUtil;

public class JobEntriesReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobEntriesReader.class);

    public static final String ALGEMENE_JOBS = "algemene_jobs";
    private static final String FILE_NAME = "job_entries.json";

    public static List<JobEntry> retrieveJobEntriesByDatasetFromCatalogus(File catalogusFolder, String datasetName)
            throws FileReaderException {
        String filePath = catalogusFolder.getPath() + buildFilePathForDataset(datasetName);

        String fileAsString = FileReaderUtil.retrieveFileToStringFromFilePath(filePath, LOGGER);

        return parseStringToJobEntries(fileAsString);
    }

    private static String buildFilePathForDataset(String datasetName) {
        StringBuilder pathStr = new StringBuilder();
        pathStr.append(File.separator);
        if (ALGEMENE_JOBS.equalsIgnoreCase(datasetName)) {
            pathStr.append(ALGEMENE_JOBS);
            pathStr.append(File.separator);
            pathStr.append(FILE_NAME);
        } else {
            pathStr.append("datasets");
            pathStr.append(File.separator);
            pathStr.append(datasetName);
            pathStr.append(File.separator);
            pathStr.append(FILE_NAME);
        }
        return pathStr.toString();
    }

    private static List<JobEntry> parseStringToJobEntries(String jsonString) throws FileReaderException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JobEntry[] entries = mapper.readValue(jsonString, JobEntry[].class);

            return Arrays.asList(entries);
        } catch (IOException e) {
            String errorMsg = "There was an error when attempting to parse the json file";
            LOGGER.error(errorMsg, e);
            throw new FileReaderException(errorMsg, e);
        }
    }
}
