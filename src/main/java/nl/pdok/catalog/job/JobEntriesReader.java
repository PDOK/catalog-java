package nl.pdok.catalog.job;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import nl.pdok.catalog.jsonentities.JobEntry;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobEntriesReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobEntriesReader.class);

    public static final String ALGEMENE_JOBS = "algemene_jobs";
    private static final String FILE_NAME = "job_entries.json";

    public static List<JobEntry> retrieveJobEntriesByDatasetFromCatalogus(File catalogusFolder, String datasetName) {
        String filePath = catalogusFolder.getPath() + buildFilePathForDataset(datasetName);

        File file = new File(filePath);
        StringBuilder builder = new StringBuilder();

        try {
            for (String str : Files.readAllLines(file.toPath(), StandardCharsets.UTF_8)) {
                builder.append(str);
            }
        } catch (IOException e) {
            LOGGER.error("There was a problem reading the JobEntries file for dataset: \"" + datasetName + "\".", e);
            return new ArrayList<>();
        }

        return parseStringToJobEntries(builder.toString());
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

    private static List<JobEntry> parseStringToJobEntries(String jsonString) {
        List<JobEntry> listJobEntries = new ArrayList<>();
        JSONParser parser = new JSONParser();

        JSONObject json;
        try {
            json = (JSONObject) parser.parse(jsonString);

            JSONArray jsonArr = (JSONArray) json.get("job_entries");
            for (Object jsonObject : jsonArr) {
                JobEntry entry = parseJsonObjectIntoJobEntry((JSONObject) jsonObject);
                listJobEntries.add(entry);
            }
        } catch (ParseException e) {
            LOGGER.error("There was an error when attempting to parse the json file", e);
        }
        return listJobEntries;
    }

    private static JobEntry parseJsonObjectIntoJobEntry(JSONObject jsonObj) {
        JobEntry entry = new JobEntry();
        entry.setName((String) jsonObj.get("name"));
        entry.setExecutionOrder((Long) jsonObj.get("execution_order"));
        entry.setJobName((String) jsonObj.get("job_name"));
        entry.setDataIn((JSONObject)jsonObj.get("data_in"));
        entry.setDataInType((String) jsonObj.get("data_in_type"));
        entry.setActive((Boolean) jsonObj.get("Active"));
        return entry;
    }
}
