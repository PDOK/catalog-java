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
        TransformationConfiguration configuration = TransformationConfigurationReader.read("dummy", new File("test/resources/datasets/dummy/transformation.json"));
        assertEquals("pdok-featured-test", configuration.getEngine());

    }
}
