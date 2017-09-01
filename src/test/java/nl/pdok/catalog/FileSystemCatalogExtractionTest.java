package nl.pdok.catalog;

import nl.pdok.catalog.exceptions.ConfigurationException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FileSystemCatalogExtractionTest {

    /**
     * Catalogus based on /transformation folder.
     */
    private Catalog catalogusFromTestResources;
    private static final String DEFAULT_APPLICATION_ID_EXTRACTION_ENGINE = "extraction-engine";

    @Before
    public void setup() throws IOException {
        catalogusFromTestResources = new FileSystemCatalog(
                new File(FileSystemCatalogExtractionTest.class.getResource("/transformation").getFile()));
    }

    @Test
    public void testGetextractionEngine() throws ConfigurationException {
        assertEquals("extraction-engine",
                    catalogusFromTestResources.getExtractionEngine("extraction", DEFAULT_APPLICATION_ID_EXTRACTION_ENGINE));
    }

    @Test
    public void testGetTransformConfigurationNotExists() throws ConfigurationException {
        assertEquals(DEFAULT_APPLICATION_ID_EXTRACTION_ENGINE,
                catalogusFromTestResources.getExtractionEngine("not-exists", DEFAULT_APPLICATION_ID_EXTRACTION_ENGINE));
    }

    @Test
    public void testExtractionEngineNotPresent() throws ConfigurationException {
        assertEquals(DEFAULT_APPLICATION_ID_EXTRACTION_ENGINE,
                    catalogusFromTestResources.getExtractionEngine("dummy", DEFAULT_APPLICATION_ID_EXTRACTION_ENGINE));

    }

    @Test
    public void testGetTransformJsonEmpty() throws ConfigurationException {
        assertEquals(DEFAULT_APPLICATION_ID_EXTRACTION_ENGINE,
                    catalogusFromTestResources.getExtractionEngine("transform-json-empty", DEFAULT_APPLICATION_ID_EXTRACTION_ENGINE));
    }
}
