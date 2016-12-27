package nl.pdok.catalog;

import nl.pdok.catalog.workbench.Workbench;
import nl.pdok.catalog.workbench.WorkbenchType;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by stroej on 23-12-2016.
 */
public class FileSystemCatalogWorkbenchTest {



    /** Catalogus create in temp-folder */
    private Catalog catalogusInTempFolder;
    private File datasetsFolder;

    @Before
    public void setup() throws IOException {
        TemporaryFolder tempFolder = new TemporaryFolder();
        tempFolder.create();
        datasetsFolder = tempFolder.newFolder("datasets");
        catalogusInTempFolder = new FileSystemCatalog(tempFolder.getRoot(), null);
     }

    @Test
    public void testWorkbenchNoResources() throws IOException {
        File folder = new File(datasetsFolder, "dataset");
        File workbenchFolder = new File(new File(folder, "workbench"), "full");
        workbenchFolder.mkdirs();

        File workbenchFile = new File(workbenchFolder, "name.fmw");
        workbenchFile.createNewFile();

        Workbench workbench = catalogusInTempFolder.getWorkbench("dataset", WorkbenchType.FULL, "name.fmw");

        assertEquals("Expected no resource files", workbench.getResources().size(), 0);
    }

    @Test
    public void testWorkbenchWithResources() throws IOException {
        File folder = new File(datasetsFolder, "dataset");
        File workbenchFolder = new File(new File(folder, "workbench"), "full");
        workbenchFolder.mkdirs();

        File workbenchFile = new File(workbenchFolder, "name.fmw");
        workbenchFile.createNewFile();

        File resourceFolder = new File(workbenchFolder, "name.fmw.resources");
        resourceFolder.mkdir();

        File resource1 = new File(resourceFolder, "bestand.xsd");
        resource1.createNewFile();
        File resource2 = new File(resourceFolder, "iets_anders.jpeg");
        resource2.createNewFile();

        Workbench workbench = catalogusInTempFolder.getWorkbench("dataset", WorkbenchType.FULL, "name.fmw");

        assertEquals("Expected 2 resource files", 2, workbench.getResources().size());
    }

    @Test
    public void testWorkbenchWithParameters() throws IOException {
        File folder = new File(datasetsFolder, "dataset");
        File workbenchFolder = new File(new File(folder, "workbench"), "full");
        workbenchFolder.mkdirs();

        File workbenchFile = new File(workbenchFolder, "name.fmw");
        workbenchFile.createNewFile();

        File parametersFile = new File(workbenchFolder, "name.fmw.parameters");
        parametersFile.createNewFile();
        FileUtils.writeLines(parametersFile, Arrays.asList(new String[]{
                "parameter1=test", "parameter2=\\**.gml", "geenparameter1=", "geenparameter2"}));

        Workbench workbench = catalogusInTempFolder.getWorkbench("dataset", WorkbenchType.FULL, "name.fmw");

        assertEquals("Expected 2 parameters", 2, workbench.getParameters().size());
        assertEquals("parameter1", workbench.getParameters().get(0).getName());
        assertEquals("test", workbench.getParameters().get(0).getValue());
        assertEquals("parameter2", workbench.getParameters().get(1).getName());
        assertEquals("\\**.gml", workbench.getParameters().get(1).getValue());
    }

    @Test
    public void testWorkbenchNoWorkbench() throws IOException {

        Workbench workbench = catalogusInTempFolder.getWorkbench("dataset", WorkbenchType.FULL, "name");

        assertNull("Workbench found", workbench);
    }

}
