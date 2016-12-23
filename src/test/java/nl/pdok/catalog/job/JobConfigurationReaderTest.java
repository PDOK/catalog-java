package nl.pdok.catalog.job;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class JobConfigurationReaderTest {

    @Test
    public void testExists() {
        JobConfiguration configuration = JobConfigurationReader.read(new File(JobConfigurationReaderTest.class.getResource("/testcatalogus/datasets/d1/configuration.json").getFile()), "ahn");
        Assert.assertNotNull(configuration);
        Assert.assertEquals("testworkspace", configuration.getWorkspace());
    }

    @Test(expected = IllegalStateException.class)
    public void testNotExists() {
        JobConfigurationReader.read(new File("does-not-exist"), "datasetname");
    }
}
