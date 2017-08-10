package nl.pdok.catalog.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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

	public static final String ALGEMENE_JOBS = "algemene_jobs";
    
    private static final String BASE_GIT_PATH = "http://github.so.kadaster.nl/raw/PDOK/catalogus/";
    private static final String GIT_BRANCH = "pdok-1263/";
    private static final String FILE_NAME = "/job_entries.json";
    
    private static final String OUTPUT_FOLDER = "D:/tempDir/temp";
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
//		try {
//			
//			CredentialsProvider.setDefault(new UsernamePasswordCredentialsProvider("zz_pdok", "aa9b4f0b4435cac2f20e1265fa0fa241742f0893"));
//
//			Git git = Git.cloneRepository()
//					  .setURI( "http://github.so.kadaster.nl/PDOK/catalogus" )
//					  .setDirectory( new File("D:/tempDir/pdok-1263-catalogus/") )
//					  .setBranch( "pdok-1263" )
//					  .call();
//			System.out.println(git.log().toString());
//		} catch (InvalidRemoteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TransportException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (GitAPIException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		retrieveByTypeDataset(ALGEMENE_JOBS);
	}

	public static String retrieveByTypeDataset(String datasetType) throws IOException {

//		StringBuilder urlStr = new StringBuilder();
//		if ("algemene_jobs".equalsIgnoreCase(datasetType)) {
//			urlStr.append("http://github.so.kadaster.nl/raw/PDOK/catalogus/pdok-1263/algemene_jobs/job_entries.json");
//
//		} else {
//			urlStr.append("http://github.so.kadaster.nl/raw/PDOK/catalogus/pdok-1263/datasets/");
//			urlStr.append(datasetType);
//			urlStr.append("/job_entries.json");
//		}
		URL gitURL = new URL(buildGitURLForDataset(datasetType));
		HttpURLConnection httpConnection = (HttpURLConnection) gitURL.openConnection();
		httpConnection.setRequestProperty("Authorization",
				"Basic enpfcGRvazphYTliNGYwYjQ0MzVjYWMyZjIwZTEyNjVmYTBmYTI0MTc0MmYwODkz");
		// Map<String, List<String>> headerMap =
		// httpConnection.getHeaderFields();

		InputStream fileInputStream = httpConnection.getInputStream();
		unzipFileSystem(fileInputStream);
		return "done";
//		return convertStreamToString(fileInputStream);
	}

	private static String buildGitURLForDataset(String datasetType) {
		StringBuilder urlStr = new StringBuilder();
		if (ALGEMENE_JOBS.equalsIgnoreCase(datasetType)) {
			urlStr.append(BASE_GIT_PATH + GIT_BRANCH + ALGEMENE_JOBS + FILE_NAME);
		} else {
			urlStr.append(BASE_GIT_PATH + GIT_BRANCH + "datasets/");
			urlStr.append(datasetType);
			urlStr.append(FILE_NAME);
		}
		return "http://github.so.kadaster.nl/PDOK/catalogus/archive/pdok-1263.zip";
		//return urlStr.toString();
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

	private static void unzipFileSystem(InputStream inputStream) throws IOException {
		
		byte[] buffer = new byte[1024];
		
		File folder = new File(OUTPUT_FOLDER);
    	if(!folder.exists()){
    		folder.mkdir();
    	}
		
		ZipInputStream zis = new ZipInputStream(inputStream);
		ZipEntry ze = zis.getNextEntry();
		while(ze!=null){

			if (!ze.isDirectory()) {
	    	   String fileName = ze.getName();
	           File newFile = new File(OUTPUT_FOLDER + File.separator + fileName);

	           System.out.println("file unzip : "+ newFile.getAbsoluteFile());

	            //create all non exists folders
	            //else you will hit FileNotFoundException for compressed folder
	            new File(newFile.getParent()).mkdirs();

	            FileOutputStream fos = new FileOutputStream(newFile);

	            int len;
	            while ((len = zis.read(buffer)) > 0) {
	       		fos.write(buffer, 0, len);
	            }

	            fos.close();
	    	}
            ze = zis.getNextEntry();
		}

	        zis.closeEntry();
	    	zis.close();

	    	System.out.println("Done");
		
	}
}
