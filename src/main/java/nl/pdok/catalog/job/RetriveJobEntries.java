package nl.pdok.catalog.job;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

public class RetriveJobEntries {

	public static String retrieveByTypeDataset(String datasetType) throws IOException {

		StringBuilder urlStr = new StringBuilder();
		if ("algemene_jobs".equalsIgnoreCase(datasetType)) {
			urlStr.append("http://github.so.kadaster.nl/raw/PDOK/catalogus/pdok-1263/algemene_jobs/job_entries.json");

		} else {
			urlStr.append("http://github.so.kadaster.nl/raw/PDOK/catalogus/pdok-1263/datasets/");
			urlStr.append(datasetType);
			urlStr.append("/job_entries.json");
		}
		URL gitURL = new URL(urlStr.toString());
		HttpURLConnection httpConnection = (HttpURLConnection) gitURL.openConnection();
		httpConnection.setRequestProperty("Authorization",
				"Basic enpfcGRvazphYTliNGYwYjQ0MzVjYWMyZjIwZTEyNjVmYTBmYTI0MTc0MmYwODkz");
//		Map<String, List<String>> headerMap = httpConnection.getHeaderFields();

		InputStream fileInputStream = httpConnection.getInputStream();
		return convertStreamToString(fileInputStream);
	}

	private static String convertStreamToString(InputStream inputStream) throws IOException {
		if (inputStream != null) {
			Writer stringWriter = new StringWriter();

			char[] characterBuffer = new char[2048];
			try {
				Reader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
				int counter;
				while ((counter = bufferedReader.read(characterBuffer)) != -1) {
					stringWriter.write(characterBuffer, 0, counter);
				}
			} finally {
				inputStream.close();
			}
			return stringWriter.toString();
		} else {
			return "No Contents";
		}
	}
}
