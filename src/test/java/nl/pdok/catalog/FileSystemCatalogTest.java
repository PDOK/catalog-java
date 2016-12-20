package nl.pdok.catalog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

/**
 *
 * @author Raymond Kroon <raymond@k3n.nl>
 */
public class FileSystemCatalogTest {

    private FileSystemCatalog catalogus;

    public TemporaryFolder testFolder = new TemporaryFolder();
    public File datasetsFolder;

    @Before
    public void setup() throws IOException {
        testFolder.create();
        catalogus = new FileSystemCatalog(testFolder.getRoot());
        datasetsFolder = testFolder.newFolder("datasets");
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

        try (FileInputStream ddlResource = (FileInputStream) catalogus.getDdlResource(DATASET_NAME)) {
            assertNotNull(ddlResource);
        }
    }

    @Test
    public void testDatasetExists() throws IOException {
        final String DATASET_NAME = "dataset_dummy";
        File datasetFolderFile = new File(datasetsFolder, DATASET_NAME);
        datasetFolderFile.mkdirs();

        assertTrue(catalogus.datasetExists(DATASET_NAME));
        assertFalse(catalogus.datasetExists("no_dataset"));
    }
}
