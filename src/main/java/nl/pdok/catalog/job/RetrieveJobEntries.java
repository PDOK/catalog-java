package nl.pdok.catalog.job;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class RetrieveJobEntries {

	public static final String ALGEMENE_JOBS = "algemene_jobs";
    
    private static final String FILE_NAME = "job_entries.json";
    
	
//	public static void main(String[] args) throws IOException, ClassNotFoundException {
//		retrieveByDataset(new File("D:\\tempDir\\catalogus") , "bag");
//	}

	public static String retrieveJobEntriesByDatasetFromCatalogus(File catalogusFolder, String datasetName) throws IOException {
		String filePath = catalogusFolder.getPath() + buildFilePathForDataset(datasetName);
		
		File file = new File(filePath);
		StringBuilder builder = new StringBuilder();
		
		for (String str : Files.readAllLines(file.toPath())) {
			builder.append(str);
		}
		
		System.out.println(builder.toString());
		return builder.toString();
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
}
