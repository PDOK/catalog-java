
package nl.pdok.catalog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;

public interface ICatalog {
	
    static final String RESOURCE_EXTENSION = ".resources";
    static final String PARAMETERS_EXTENSION = ".parameters";
    
    static final String DDL_RESOURCE_EXTENSION = ".sql";
    static final String DDL_RESOURCE_FOLDER = "ddl";
    static final String XLS_RESOURCE_FOLDER = "xls";
    static final String CSV_RESOURCE_FOLDER = "csv";
    
    static final String SQL_RESOURCE_FOLDER = "sql";
    static final String SHAPES_TO_DB_FOLDER = "shapesToDB";
    static final String SHAPES_TO_FEATURED_FOLDER = "shapesToFeatured";
    
    static final String DATASETSFOLDER = "datasets";
    static final String TESTFOLDER = "testset";
    static final String TESTDATAFOLDER = "data";
    static final String TESTEXPECTEDFOLDER = "expected";
    static final String TESTPREPAREFOLDER = "prepare";
    static final String TEMPLATESFOLDER = "templates";
    static final String DICTIONARY_SEPARATION_CHAR = "=";
    
    static final String SHAREDFOLDER = "shared";

    static final String FILENAME_JOB_CONFIGURATION = "configuration.json";    
    static final String FILENAME_TILING_CONFIGURATION = "tiling.json";    
    static final String FILENAME_EXTRACT_CONFIGURATION = "extract.json";    
    
    public String getRootLocation();

    public Path getDatasetLocationTest(String datasetName);
    public Path getDatasetLocationTestData(String datasetName);
    public Path getDatasetLocationTestExpected(String datasetName);
    public Path getDatasetLocationShapesToDB(String datasetName);
    public Path getDatasetLocationShapesToFeatured(String datasetName);
	public Path getDatasetLocationTemplatesPath(String dataset);
	public List<TestData> getDatasetTestData(String datasetName);
	public File getTemplateResource(String dataset,String format, String file, String subfolder);
	
    InputStream getResourcePrepareTestset(String datasetName, String resourceName) throws IOException;
    
    InputStream getDdlResource(String datasetName) throws IOException;
    InputStream getSqlResource(String datasetName, String fileName) throws IOException;
	InputStream getCsvResource(String datasetName, String fileName) throws IOException;
    
    boolean datasetExists(String datasetName);
    
    Collection<String> getDatasetNames();
    
    
}
