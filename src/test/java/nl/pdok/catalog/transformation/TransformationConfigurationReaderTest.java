package nl.pdok.catalog.transformation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import nl.pdok.catalog.exceptions.ConfigurationException;
import org.junit.Test;

public class TransformationConfigurationReaderTest {

    @Test
    public void testReadFile() throws ConfigurationException {
        TransformationConfiguration configuration = TransformationConfigurationReader.read("dummy", new File(
                "src/test/resources/transformation/datasets/dummy/transformation.json"));
        assertEquals("pdok-featured-test", configuration.getEngine());
    }

    @Test(expected = ConfigurationException.class)
    public void testConfigurationNotExists() throws ConfigurationException {
        TransformationConfigurationReader.read("not-exists", new File("transformation.json"));
    }

    @Test(expected = ConfigurationException.class)
    public void testConfigurationNotValid() throws ConfigurationException {
        File transformationFile = new File(
                "src/test/resources/transformation/datasets/dummy/invalid-transformation.json");
        assertTrue(transformationFile.exists());
        TransformationConfigurationReader.read("dummy", transformationFile);
    }
}
