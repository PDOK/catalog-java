package nl.pdok.catalog;

import nl.pdok.catalog.exceptions.ConfigurationException;
import nl.pdok.catalog.featured.FeatureTemplate;
import nl.pdok.catalog.workbench.FmeWorkbenchEnvConfig;
import nl.pdok.catalog.workbench.Workbench;
import nl.pdok.catalog.workbench.WorkbenchType;
import org.apache.commons.io.FileUtils;
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
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;

/**
 *
 * @author Raymond Kroon <raymond@k3n.nl>
 */
public class FileSystemCatalogTest {

    private FmeWorkbenchEnvConfig fmeworkbenchenvconfig;

    /** Catalogus for fme */
    private Catalog catalogusInTempFolder;
    
    /** Catalogus based on /testcatalogus folder */
    private Catalog catalogusFromTestResources;

    public File datasetsFolder;

    @Before
    public void setup() throws IOException {
        TemporaryFolder tempFolder = new TemporaryFolder();
        tempFolder.create();
        datasetsFolder = tempFolder.newFolder("datasets");
        catalogusInTempFolder = new FileSystemCatalog(tempFolder.getRoot(), fmeworkbenchenvconfig);
        catalogusFromTestResources = new FileSystemCatalog(new File(FileSystemCatalogTest.class.getResource("/testcatalogus/").getFile()), fmeworkbenchenvconfig);
    }

    private void createTemplateFeatureFolderStructure(String dataset, String extractType) throws IOException {
        File citygmlFolder = new File (new File(new File (datasetsFolder,  dataset), "templates"), extractType);
        citygmlFolder.getParentFile().mkdirs();

        Path partialPathA = Paths.get(citygmlFolder.getPath(), "partials", "partialA.mustache");
        Files.createDirectories(partialPathA.getParent());
        Files.createFile(partialPathA);
        Path partialPathB = Paths.get(citygmlFolder.getPath(), "partials", "partialB.mustache");
        Files.createFile(partialPathB);
        Path partialPathC = Paths.get(citygmlFolder.getPath(), "partials", "somesubdir", "partialC.mustache");
        Files.createDirectories(partialPathC.getParent());
        Files.createFile(partialPathC);

        Path featureA = Paths.get(citygmlFolder.getPath(), "featureA.mustache");
        Files.createFile(featureA);
        Path featureB = Paths.get(citygmlFolder.getPath(), "featureB.mustache");
        Files.createFile(featureB);

        Path featureC = Paths.get(citygmlFolder.getPath(), "somesubdirectory", "featureC.mustache");
        Files.createDirectories(featureC.getParent());
        Files.createFile(featureC);
    }

    @Test
    public void getFeatureTemplates() throws IOException {
        // Create dummy structure for reading feature templates
        createTemplateFeatureFolderStructure("b1", "ec");
        createTemplateFeatureFolderStructure("b1", "eg");
        createTemplateFeatureFolderStructure("b2", "eg");

        // Check there is one b2 feature templates
        Set<FeatureTemplate> b2 = catalogusInTempFolder.getFeatureTemplates("b2");
        Assert.assertEquals(1, b2.size());
        FeatureTemplate b2Template = b2.iterator().next();
        Assert.assertEquals("eg", b2Template.getExtractType());
        Assert.assertEquals(2, b2Template.getPartialTemplates().size());
        Assert.assertTrue(containsFile(b2Template.getPartialTemplates(), "partialA.mustache"));
        Assert.assertTrue(containsFile(b2Template.getPartialTemplates(), "partialB.mustache"));
        Assert.assertTrue(containsFile(b2Template.getCollectionTemplates(), "featureA.mustache"));
        Assert.assertTrue(containsFile(b2Template.getCollectionTemplates(), "featureB.mustache"));

        // Check there are two bgt feature templates
        Assert.assertEquals(2, catalogusInTempFolder.getFeatureTemplates("b1").size());

        // Check there are no notexisting feature
        Assert.assertEquals(0, catalogusInTempFolder.getFeatureTemplates("notexisting").size());
    }

    @Test
    public void testGetServiceName() {
        Assert.assertEquals("testworkspace", catalogusFromTestResources.getServiceName("d1"));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetServiceNameNonExisting() {
        catalogusFromTestResources.getServiceName("not-existing");
    }

    @Test
    public void testGetGeoserverType() {
        Assert.assertEquals("testgeoserver", catalogusFromTestResources.getGeoserverType("d1"));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetGeoserverTypeNonExisting() {
        catalogusFromTestResources.getGeoserverType("not-existing");
    }

    @Test
    public void testGetTargetProjection() {
        Assert.assertNull(catalogusFromTestResources.getTargetProjection("d1"));
        Assert.assertEquals("RD", catalogusFromTestResources.getTargetProjection("reprojected"));
    }

    @Test(expected = IllegalStateException.class)
    public void testGetInvalidTargetProjection() {
         catalogusFromTestResources.getTargetProjection("invalid-reprojected");
    }
    
    @Test(expected = IllegalStateException.class)
    public void testGetTargetProjectionNonExisting() {
        catalogusFromTestResources.getTargetProjection("not-existing");
    }
    
    private boolean containsFile(List<Path> paths, String fileName) {
        boolean res = false;
        for (Path path : paths) {

            if (Files.isRegularFile(path) && StringUtils.equals(path.toFile().getName(), fileName)) {
                res = true;
                break;
            }
        }
        return res;
    }

    @Test
    public void testDdlResource() throws IOException {

        final String DATASET_NAME = "dataset_1";
        final String RESOURCE_DDL = "dataset_1.sql";

        File datasetFolderFile = new File(datasetsFolder, DATASET_NAME);
        File ddlResourceFolder = new File(datasetFolderFile, FileSystemCatalog.DDL_RESOURCE_FOLDER);
        ddlResourceFolder.mkdirs();
        File ddlResourceFile = new File(ddlResourceFolder, RESOURCE_DDL);
        ddlResourceFile.createNewFile();

        try (FileInputStream ddlResource = (FileInputStream) catalogusInTempFolder.getDdlResource(DATASET_NAME)) {
            assertNotNull(ddlResource);
        }
    }

    @Test
    public void testDatasetExists() throws IOException {
        final String DATASET_NAME = "dataset_dummy";
        File datasetFolderFile = new File(datasetsFolder, DATASET_NAME);
        datasetFolderFile.mkdirs();

        assertTrue(catalogusInTempFolder.datasetExists(DATASET_NAME));
        assertFalse(catalogusInTempFolder.datasetExists("no_dataset"));
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
}
