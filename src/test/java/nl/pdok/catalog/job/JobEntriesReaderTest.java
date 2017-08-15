package nl.pdok.catalog.job;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import nl.pdok.catalog.jsonentities.JobEntry;

public class JobEntriesReaderTest {
	
	@Test(expected=IOException.class)
	public void testRetrieveJobEntriesByDatasetFromCatalogusNoFile() throws IOException {
		JobEntriesReader.retrieveJobEntriesByDatasetFromCatalogus(new File(JobEntriesReaderTest.class
                .getResource("/testcatalogus/").getFile()), "bag");
	}
	
	@Test
	public void testRetrieveJobEntriesByDatasetFromCatalogusAlgemene() throws IOException {
		List<JobEntry> entries = JobEntriesReader.retrieveJobEntriesByDatasetFromCatalogus(new File(JobEntriesReaderTest.class
                .getResource("/testcatalogus/").getFile()), JobEntriesReader.ALGEMENE_JOBS);
		Assert.assertEquals(6,entries.size());
	}
	
	@Test
	public void testRetrieveJobEntriesByDatasetFromCatalogusBagActueel() throws IOException {
		List<JobEntry> entries = JobEntriesReader.retrieveJobEntriesByDatasetFromCatalogus(new File(JobEntriesReaderTest.class
                .getResource("/testcatalogus/").getFile()), "bagactueel");
		Assert.assertEquals(1,entries.size());
	}
	
}
