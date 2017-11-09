package nl.pdok.catalog.gmlconverter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import nl.pdok.catalog.exceptions.FileReaderException;

public class GmlConverterConfigReaderTest {
    
    GmlConverterConfigReader reader;
    
    @Before
    public void init() {
        reader = new GmlConverterConfigReader(new File(GmlConverterConfigReaderTest.class.getResource("/testcatalogus/").getFile()));
    }
    
    @Test(expected = FileReaderException.class)
    public void testGmlConverterConfigFromCatalogusNoFile() throws FileReaderException {
        reader.retrieveGmlConverterConfigFromCatalogus("bag");
    }
    
    @Test(expected = FileReaderException.class)
    public void testGmlConverterConfigFromCatalogusJsonMappingError() throws FileReaderException {
        reader.retrieveGmlConverterConfigFromCatalogus("invalid-reprojected");
    }

    @Test
    public void testGmlConverterConfigFromCatalogus() throws FileReaderException {
        GmlConverterConfig config = reader.retrieveGmlConverterConfigFromCatalogus("protectedSite");
        assertEquals(".ProtectedSite.\"gml:id\"", config.getGmlId());
        assertEquals("new", config.getAction());
        assertEquals("ProtectedSite", config.getDataset());
        assertEquals("ProtectedSite", config.getFeatureNode());
        assertEquals("../gml/natura2000-small.gml", config.getPathToGML());
        assertTrue(config.getArrayNodes().isEmpty());
        assertFalse(config.getSkipNodes().isEmpty());
        assertEquals(2, config.getSkipNodes().size());
        assertTrue(config.getSkipNodes().contains(".siteName.GeographicalName"));
        assertTrue(config.getSkipNodes().contains(".siteName.GeographicalName.spelling.SpellingOfName"));
    }

}
