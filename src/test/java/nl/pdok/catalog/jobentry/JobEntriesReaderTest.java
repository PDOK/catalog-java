package nl.pdok.catalog.jobentry;

import java.io.File;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

import nl.pdok.catalog.exceptions.FileReaderException;

public class JobEntriesReaderTest {

    @Test(expected = FileReaderException.class)
    public void testRetrieveJobEntriesByDatasetFromCatalogusNoFile() throws FileReaderException {
        JobEntriesReader.retrieveJobEntriesByDatasetFromCatalogus(
                new File(JobEntriesReaderTest.class.getResource("/testcatalogus/").getFile()), "bag");
    }

    @Test
    public void testRetrieveJobEntriesByDatasetFromCatalogusEmptyFile() throws FileReaderException {
        List<JobEntry> entries = JobEntriesReader.retrieveJobEntriesByDatasetFromCatalogus(
                new File(JobEntriesReaderTest.class.getResource("/testcatalogus/").getFile()), "d1");
        Assert.assertTrue(entries.isEmpty());
    }

    @Test
    public void testRetrieveJobEntriesByDatasetFromCatalogusAlgemene() throws FileReaderException {
        List<JobEntry> entries = JobEntriesReader.retrieveJobEntriesByDatasetFromCatalogus(
                new File(JobEntriesReaderTest.class.getResource("/testcatalogus/").getFile()),
                JobEntriesReader.ALGEMENE_JOBS);
        Assert.assertEquals(6, entries.size());
    }

    @Test
    public void testRetrieveJobEntriesByDatasetFromCatalogusBagActueel() throws FileReaderException {
        List<JobEntry> entries = JobEntriesReader.retrieveJobEntriesByDatasetFromCatalogus(
                new File(JobEntriesReaderTest.class.getResource("/testcatalogus/").getFile()), "bagactueel");
        Assert.assertEquals(1, entries.size());
    }
}
