package nl.pdok.catalog.job;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import nl.pdok.catalog.jsonentities.JobEntry;

public class RetrieveJobEntries {

	public static final String ALGEMENE_JOBS = "algemene_jobs";
    
    private static final String FILE_NAME = "job_entries.json";
    
//	public static void main(String[] args) throws IOException, ClassNotFoundException {
//		System.out.println(retrieveJobEntriesByDatasetFromCatalogus(new File("D:\\development\\git\\catalogus\\catalogus") , "bag"));
//	}

	public static List<JobEntry> retrieveJobEntriesByDatasetFromCatalogus(File catalogusFolder, String datasetName) throws IOException {
		String filePath = catalogusFolder.getPath() + buildFilePathForDataset(datasetName);
		
		File file = new File(filePath);
		StringBuilder builder = new StringBuilder();
		
		for (String str : Files.readAllLines(file.toPath(), StandardCharsets.UTF_8)) {
			builder.append(str);
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
			for (int ii = 0; ii < jsonArr.size(); ii++) {
				 JobEntry entry = parseJSONObjectIntoJobEntry((JSONObject) jsonArr.get(ii));
				 listJobEntries.add(entry);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return listJobEntries;
	}

	private static JobEntry parseJSONObjectIntoJobEntry(JSONObject jObj) {
		JobEntry entry = new JobEntry();
		 entry.setName((String) jObj.get("name"));
		 entry.setExecutionOrder((Long) jObj.get("execution_order"));
		 entry.setJobName((String) jObj.get("job_name"));
		 entry.setDataIn((JSONObject)jObj.get("data_in"));
		 entry.setDataInType((String) jObj.get("data_in_type"));
		 entry.setActive((Boolean) jObj.get("Active"));
		return entry;
	}
	
}
