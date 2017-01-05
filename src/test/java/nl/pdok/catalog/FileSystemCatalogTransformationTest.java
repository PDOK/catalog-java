package nl.pdok.catalog;

import nl.pdok.catalog.exceptions.ConfigurationException;
import nl.pdok.catalog.featured.FeatureTemplate;
import nl.pdok.catalog.workbench.FmeWorkbenchEnvConfig;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 *
 * @author Raymond Kroon <raymond@k3n.nl>
 */
public class FileSystemCatalogTransformationTest {

    /** Catalogus based on /testcatalogus folder */
    private Catalog catalogusFromTestResources;

    @Before
    public void setup() throws IOException {
        catalogusFromTestResources = new FileSystemCatalog(new File(FileSystemCatalogTransformationTest.class.getResource("/transformation").getFile()));
    }

    @Test
    public void testGetTransformJsonEngine() throws ConfigurationException{
        assertEquals("pdok-featured-test", catalogusFromTestResources.getEngineTransformJson("dummy"));
    }

    @Test(expected = ConfigurationException.class)
    public void testGetTransformConfigurationNotExists() throws ConfigurationException{
        catalogusFromTestResources.getEngineTransformJson("not-exists");
    }

    @Test
    public void testGetTransformJsonEngineNotPresent() throws ConfigurationException{
        try {
            catalogusFromTestResources.getEngineTransformJson("no-transform-json-engine");
        } catch (ConfigurationException configExeception){
            assertTrue(StringUtils.contains(configExeception.getMessage(), "parsed"));
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testGetTransformJsonEmpty() throws ConfigurationException{
        try {
            String engine = catalogusFromTestResources.getEngineTransformJson("transform-json-empty");
            System.out.println(engine);
        } catch (ConfigurationException configExeception){
            assertTrue(StringUtils.contains(configExeception.getMessage(), "emmpty"));
            return;
        }
        assertTrue(false);
    }
}
