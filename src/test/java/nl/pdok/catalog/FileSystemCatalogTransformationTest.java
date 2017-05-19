package nl.pdok.catalog;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import nl.pdok.catalog.exceptions.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

public class FileSystemCatalogTransformationTest {

    /**
     * Catalogus based on /testcatalogus folder.
     */
    private Catalog catalogusFromTestResources;
    private static final String DEFAULT_APPLICATION_ID_FEATURED_ENGINE = "featured-engine";

    @Before
    public void setup() throws IOException {
        catalogusFromTestResources = new FileSystemCatalog(
                new File(FileSystemCatalogTransformationTest.class.getResource("/transformation").getFile()));
    }

    @Test
    public void testGetTransformJsonEngine() throws ConfigurationException {
        assertEquals("pdok-featured-test", catalogusFromTestResources
                .getEngineTransformJson("dummy", DEFAULT_APPLICATION_ID_FEATURED_ENGINE));
    }

    public void testGetTransformConfigurationNotExists() throws ConfigurationException {
        assertEquals(DEFAULT_APPLICATION_ID_FEATURED_ENGINE, catalogusFromTestResources
                .getEngineTransformJson("not-exists", DEFAULT_APPLICATION_ID_FEATURED_ENGINE));
    }

    @Test
    public void testGetTransformJsonEngineNotPresent() throws ConfigurationException {
        try {
            catalogusFromTestResources
                    .getEngineTransformJson("no-transform-json-engine", DEFAULT_APPLICATION_ID_FEATURED_ENGINE);
        } catch (ConfigurationException configExeception) {
            assertTrue(StringUtils.contains(configExeception.getMessage(), "parsed"));
            return;
        }
        assertTrue(false);
    }

    @Test
    public void testGetTransformJsonEmpty() throws ConfigurationException {
        try {
            String engine = catalogusFromTestResources
                    .getEngineTransformJson("transform-json-empty", DEFAULT_APPLICATION_ID_FEATURED_ENGINE);
            System.out.println(engine);
        } catch (ConfigurationException configExeception) {
            assertTrue(StringUtils.contains(configExeception.getMessage(), "empty"));
            return;
        }
        assertTrue(false);
    }
}
