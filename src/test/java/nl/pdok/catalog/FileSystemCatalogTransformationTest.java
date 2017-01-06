package nl.pdok.catalog;

import nl.pdok.catalog.exceptions.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

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

    @Test
    public void testGetTransformConfigurationNotExists() throws ConfigurationException{
        assertEquals(FileSystemCatalog.DEFAULT_APLLICATION_ID_FEATURED_ENGINE, catalogusFromTestResources.getEngineTransformJson("not-exists"));
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
