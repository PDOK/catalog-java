package nl.pdok.catalog.jobentry;

import java.io.File;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import nl.pdok.catalog.exceptions.FileReaderException;

public class JobEntriesReaderTest {
    
    JobEntriesReader reader;
    
    @Before
    public void init() {
        reader = new JobEntriesReader(new File(JobEntriesReaderTest.class.getResource("/testcatalogus/").getFile()));
    }

    @Test(expected = FileReaderException.class)
    public void testRetrieveJobEntriesByDatasetFromCatalogusNoFile() throws FileReaderException {
        reader.retrieveJobEntriesByDatasetFromCatalogus("bag");
    }

    @Test
    public void testRetrieveJobEntriesByDatasetFromCatalogusEmptyFile() throws FileReaderException {
        List<JobEntry> entries = reader.retrieveJobEntriesByDatasetFromCatalogus("d1");
        Assert.assertTrue(entries.isEmpty());
    }

    @Test
    public void testRetrieveJobEntriesByDatasetFromCatalogusAlgemene() throws FileReaderException {
        List<JobEntry> entries = reader.retrieveJobEntriesByDatasetFromCatalogus(JobEntriesReader.ALGEMENE_JOBS);
        Assert.assertEquals(6, entries.size());
    }

    @Test
    public void testRetrieveJobEntriesByDatasetFromCatalogusBagActueel() throws FileReaderException {
        List<JobEntry> entries = reader.retrieveJobEntriesByDatasetFromCatalogus("bagactueel");
        Assert.assertEquals(1, entries.size());
    }
}
