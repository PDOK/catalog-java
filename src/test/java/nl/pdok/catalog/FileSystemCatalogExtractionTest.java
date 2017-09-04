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
    private static final String DEFAULT_VERSIONED_DELETES = "false";

    @Before
    public void setup() throws IOException {
        catalogusFromTestResources = new FileSystemCatalog(
                new File(FileSystemCatalogExtractionTest.class.getResource("/transformation").getFile()));
    }

    @Test
    public void testGetextractionEngine() throws ConfigurationException {
        assertEquals("true",
                    catalogusFromTestResources.getVersionedDeletes("extraction", DEFAULT_VERSIONED_DELETES));
    }

    @Test
    public void testGetTransformConfigurationNotExists() throws ConfigurationException {
        assertEquals(Boolean.FALSE.toString().toLowerCase(),
                catalogusFromTestResources.getVersionedDeletes("not-exists", DEFAULT_VERSIONED_DELETES));
    }

    @Test
    public void testExtractionEngineNotPresent() throws ConfigurationException {
        assertEquals(Boolean.FALSE.toString().toLowerCase(),
                    catalogusFromTestResources.getVersionedDeletes("dummy", DEFAULT_VERSIONED_DELETES));

    }

    @Test
    public void testGetTransformJsonEmpty() throws ConfigurationException {
        assertEquals(Boolean.FALSE.toString().toLowerCase(),
                    catalogusFromTestResources.getVersionedDeletes("transform-json-empty", DEFAULT_VERSIONED_DELETES));
    }
}
