package nl.pdok.catalog;

import nl.pdok.catalog.exceptions.ConfigurationException;
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
    private static final boolean DEFAULT_UNIQUE_VERSIONS = true;

    @Before
    public void setup() throws IOException {
        catalogusFromTestResources = new FileSystemCatalog(
                new File(FileSystemCatalogExtractionTest.class.getResource("/transformation").getFile()));
    }

    @Test
    public void testGetUniqueVersionFalse() throws ConfigurationException {
        assertEquals(Boolean.FALSE,
                    catalogusFromTestResources.getUniqueVersions("extraction", DEFAULT_UNIQUE_VERSIONS));
    }

    @Test
    public void testGetTransformConfigurationNotExists() throws ConfigurationException {
        assertEquals(Boolean.TRUE,
                catalogusFromTestResources.getUniqueVersions("not-exists", DEFAULT_UNIQUE_VERSIONS));
    }

    @Test
    public void testExtractionEngineNotPresent() throws ConfigurationException {
        assertEquals(Boolean.TRUE,
                    catalogusFromTestResources.getUniqueVersions("dummy", DEFAULT_UNIQUE_VERSIONS));

    }

    @Test
    public void testGetTransformJsonEmpty() throws ConfigurationException {
        assertEquals(Boolean.TRUE,
                    catalogusFromTestResources.getUniqueVersions("transform-json-empty", DEFAULT_UNIQUE_VERSIONS));
    }
}
