package nl.pdok.catalog.jobentry;

import java.io.File;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class JobEntriesReaderTest {

    @Test(expected = JobEntryException.class)
    public void testRetrieveJobEntriesByDatasetFromCatalogusNoFile() throws JobEntryException {
        JobEntriesReader.retrieveJobEntriesByDatasetFromCatalogus(
                new File(JobEntriesReaderTest.class.getResource("/testcatalogus/").getFile()), "bag");
    }

    @Test
    public void testRetrieveJobEntriesByDatasetFromCatalogusEmptyFile() throws JobEntryException {
        List<JobEntry> entries = JobEntriesReader.retrieveJobEntriesByDatasetFromCatalogus(
                new File(JobEntriesReaderTest.class.getResource("/testcatalogus/").getFile()), "d1");
        Assert.assertTrue(entries.isEmpty());
    }

    @Test
    public void testRetrieveJobEntriesByDatasetFromCatalogusAlgemene() throws JobEntryException {
        List<JobEntry> entries = JobEntriesReader.retrieveJobEntriesByDatasetFromCatalogus(
                new File(JobEntriesReaderTest.class.getResource("/testcatalogus/").getFile()),
                JobEntriesReader.ALGEMENE_JOBS);
        Assert.assertEquals(6, entries.size());
    }

    @Test
    public void testRetrieveJobEntriesByDatasetFromCatalogusBagActueel() throws JobEntryException {
        List<JobEntry> entries = JobEntriesReader.retrieveJobEntriesByDatasetFromCatalogus(
                new File(JobEntriesReaderTest.class.getResource("/testcatalogus/").getFile()), "bagactueel");
        Assert.assertEquals(1, entries.size());
    }
}
