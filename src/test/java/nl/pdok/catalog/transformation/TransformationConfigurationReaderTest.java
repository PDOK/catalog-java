package nl.pdok.catalog.transformation;

import nl.pdok.catalog.exceptions.ConfigurationException;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;

/**
 * Created by stroej on 23-12-2016.
 */
public class TransformationConfigurationReaderTest {

    @Test
    public void testReadFile() throws ConfigurationException {
        TransformationConfiguration configuration = TransformationConfigurationReader.read("dummy", new File("src/test/resources/transformation/datasets/dummy/transformation.json"));
        assertEquals("pdok-featured-test", configuration.getEngine());
    }

    @Test(expected = ConfigurationException.class)
    public void testConfigurationNotExists() throws ConfigurationException{
       TransformationConfigurationReader.read("not-exists", new File("transformation.json"));
    }

    @Test(expected = ConfigurationException.class)
    public void testConfigurationNotValid() throws ConfigurationException {
        File transformationFile = new File("src/test/resources/transformation/datasets/dummy/invalid-transformation.json");
        assertTrue(transformationFile.exists());
        TransformationConfigurationReader.read("dummy", transformationFile);
    }

}
