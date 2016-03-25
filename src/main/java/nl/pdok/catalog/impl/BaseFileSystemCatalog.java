package nl.pdok.catalog.impl;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import nl.pdok.catalog.model.DataObject;

import nl.pdok.catalog.IBaseCatalog;
import nl.pdok.util.ZipUtils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.log4j.Logger;

/**
 *
 * @author Raymond Kroon <raymond@k3n.nl>
 */
public class BaseFileSystemCatalog implements IBaseCatalog {

	protected static final Logger LOGGER = Logger.getLogger(BaseFileSystemCatalog.class);

    protected  Path datasetsFolder;
    protected Path catalogFolder;

    public BaseFileSystemCatalog(File catalogFolder) {
        this.catalogFolder = catalogFolder.toPath();
        this.datasetsFolder = Paths.get(this.catalogFolder.toString(), DATASETSFOLDER);
    }

    @Override
    public String getRootLocation() {
        return this.catalogFolder.toString();
    }

    @Override
    public Collection<String> getDatasetNames() {
        Collection<String> datasetNames = new ArrayList<String>();
        try (DirectoryStream<Path> catalog = Files.newDirectoryStream(datasetsFolder, getFilterPathIsDirectory(true))) {
            for (Path dataset : catalog) {
                datasetNames.add(dataset.getFileName().toString());
            }
        } catch (IOException ex) {
            //nothing
        }
        return datasetNames;
    }

    protected Filter<Path> getFilterPathIsDirectory(final boolean isDirectory) {
        return new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return Files.isDirectory(entry) == isDirectory;
            }
        };
    }

    @Override
    public List<DataObject> getDatasetTestData(String datasetName) {
        Path testDataFolder = getDatasetLocationTestData(datasetName);
        List<DataObject> results = new ArrayList<DataObject>();

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(testDataFolder, getFilterPathIsDirectory(false))) {
            for (Path testDataFile : ds) {
                boolean zipped = ZipUtils.isZip(testDataFile.toFile());
                results.add(new DataObject(testDataFile.getFileName().toString(), new FileInputStream(testDataFile.toFile()), zipped));
            }
        } catch (IOException ex) {
            //nothing
        }

        return results;
    }

    @Override
    public Path getDatasetLocationTest(String datasetName) {
        return Paths.get(datasetsFolder.toString(), datasetName, TESTFOLDER);
    }

    @Override
    public Path getDatasetLocationTestData(String datasetName) {
        return Paths.get(getDatasetLocationTest(datasetName).toString(), TESTDATAFOLDER);
    }

    @Override
    public Path getDatasetLocationTestExpected(String datasetName) {
        return Paths.get(getDatasetLocationTest(datasetName).toString(), TESTEXPECTEDFOLDER);
    }

    @Override
    public Path getDatasetLocationShapesToDB(String datasetName) {
        return Paths.get(datasetsFolder.toString(), datasetName, SHAPES_TO_DB_FOLDER);
    }
   
    @Override
    public InputStream getDdlResource(String datasetName) throws IOException {
        File ddlResourceFile = Paths.get(datasetsFolder.toString(), datasetName, DDL_RESOURCE_FOLDER, datasetName + DDL_RESOURCE_EXTENSION).toFile();
        return new FileInputStream(ddlResourceFile);
    }

    @Override
    public InputStream getSqlResource(String datasetName, String fileName) throws IOException {
        File sqlResourceFile = Paths.get(datasetsFolder.toString(), datasetName, SQL_RESOURCE_FOLDER, fileName).toFile();
        return new FileInputStream(sqlResourceFile);
    }

    @Override
    public InputStream getCsvResource(String datasetName, String fileName) throws IOException {
        File csvResourceFile = Paths.get(datasetsFolder.toString(), datasetName, CSV_RESOURCE_FOLDER, fileName).toFile();
        return new FileInputStream(csvResourceFile);
    }

    @Override
    public boolean datasetExists(String datasetName) {
        File datasetFolder = Paths.get(datasetsFolder.toString(), datasetName).toFile();
        return datasetFolder.exists();
    }

    @Override
    public InputStream getResourcePrepareTestset(String datasetName,
            String resourceName) throws IOException {
        File prepareTestsetFile = Paths.get(datasetsFolder.toString(), datasetName, TESTFOLDER, TESTPREPAREFOLDER, resourceName).toFile();
        return new FileInputStream(prepareTestsetFile);
    }
}
