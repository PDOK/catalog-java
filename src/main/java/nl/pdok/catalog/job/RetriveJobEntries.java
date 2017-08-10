package nl.pdok.catalog.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.auth.UsernamePasswordCredentials;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

public class RetriveJobEntries {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		try {
			
			CredentialsProvider.setDefault(new UsernamePasswordCredentialsProvider("zz_pdok", "aa9b4f0b4435cac2f20e1265fa0fa241742f0893"));

			Git git = Git.cloneRepository()
					  .setURI( "http://github.so.kadaster.nl/PDOK/catalogus" )
					  .setDirectory( new File("D:/tempDir/pdok-1263-catalogus/") )
					  .setBranch( "pdok-1263" )
					  .call();
			System.out.println(git.log().toString());
		} catch (InvalidRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

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
		// Map<String, List<String>> headerMap =
		// httpConnection.getHeaderFields();

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
