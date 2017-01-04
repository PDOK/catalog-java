
package nl.pdok.catalog;

import nl.pdok.catalog.exceptions.ConfigurationException;
import nl.pdok.catalog.extract.ExtractConfiguration;
import nl.pdok.catalog.featured.FeatureTemplate;
import nl.pdok.catalog.featured.FeaturedCollectionOptions;
import nl.pdok.catalog.job.JobConfigurationDataset;
import nl.pdok.catalog.testdata.TestData;
import nl.pdok.catalog.tiling.TilingConfiguration;
import nl.pdok.catalog.workbench.Workbench;
import nl.pdok.catalog.workbench.WorkbenchType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Raymond Kroon &lt;raymond@k3n.nl&gt;
 */
public interface Catalog  {
	
   // dataset
   String getRootLocation();
   boolean datasetExists(String datasetName);
   String getDatasetNameVersioned(String datasetName, long version);
   boolean isVersioned(String datasetName);
   Collection<String> getDatasetNames();

   // testdata
   Path getDatasetLocationTest(String datasetName);
   Path getDatasetLocationTestData(String datasetName);
   List<TestData> getDatasetTestData(String datasetName);
   Path getDatasetLocationTestExpected(String datasetName);
   InputStream getResourcePrepareTestset(String datasetName, String resourceName) throws IOException;

   Path getDatasetLocationTemplatesPath(String dataset);
   File getTemplateResource(String dataset, String format, String file, String subfolder);

   InputStream getDdlResource(String datasetName) throws IOException;
   InputStream getSqlResource(String datasetName, String fileName) throws IOException;
   InputStream getCsvResource(String datasetName, String fileName) throws IOException;
   List<JobConfigurationDataset> getDatastoresConfiguration(String datasetName);

    // geoserver
    String getServiceName(String datasetName);
    String getGeoserverType(String datasetName);
    String getTargetProjection(String datasetName);
    boolean withIndexes(String datasetName);
    boolean withGtPkMetadata(String datasetName);
    TilingConfiguration getTilingConfiguration(String datasetName);

    // extract management
    ExtractConfiguration getExtractConfiguration(String datasetName);

    // shapes
    Path getDatasetLocationShapesToDB(String datasetName);
    Path getDatasetLocationShapesToFeatured(String datasetName);

    // Featured
	ArrayList<FeaturedCollectionOptions> getFeatureOptions(String datasetName);
    Set<FeatureTemplate> getFeatureTemplates(String datasetName) throws IOException;
    String getXml2JsonMapping(String datasetName, String translator) throws IOException;
    String getEngineTransformJson(String datasetName) throws ConfigurationException;

    // FME
    List<Workbench> getWorkbenches(String datasetName);
    List<Workbench> getWorkbenches(String datasetName, WorkbenchType type);
    Workbench getWorkbench(String datasetName, WorkbenchType type, String workbenchName);
    List<Workbench> getTransformers();
}
