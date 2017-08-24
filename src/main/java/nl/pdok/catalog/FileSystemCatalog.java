package nl.pdok.catalog;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.FluentIterable;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.DirectoryStream;
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
import nl.pdok.catalog.exceptions.ConfigurationException;
import nl.pdok.catalog.extract.ExtractConfiguration;
import nl.pdok.catalog.extract.ExtractConfigurationReader;
import nl.pdok.catalog.featured.FeatureTemplate;
import nl.pdok.catalog.featured.FeaturedCollectionOptions;
import nl.pdok.catalog.gitutil.GitInteractionsHandler;
import nl.pdok.catalog.job.JobConfiguration;
import nl.pdok.catalog.job.JobConfigurationDataset;
import nl.pdok.catalog.job.JobConfigurationReader;
import nl.pdok.catalog.jobentry.JobEntriesReader;
import nl.pdok.catalog.jobentry.JobEntry;
import nl.pdok.catalog.testdata.TestData;
import nl.pdok.catalog.tiling.TilingConfiguration;
import nl.pdok.catalog.tiling.TilingConfigurationReader;
import nl.pdok.catalog.transformation.TransformationConfiguration;
import nl.pdok.catalog.transformation.TransformationConfigurationReader;
import nl.pdok.catalog.workbench.FmeWorkbenchEnvConfig;
import nl.pdok.catalog.workbench.Workbench;
import nl.pdok.catalog.workbench.WorkbenchParameter;
import nl.pdok.catalog.workbench.WorkbenchResource;
import nl.pdok.catalog.workbench.WorkbenchType;
import nl.pdok.util.ZipUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.NameFileComparator;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

public class FileSystemCatalog implements Catalog {

    private static final String RESOURCE_EXTENSION = ".resources";
    private static final String PARAMETERS_EXTENSION = ".parameters";

    private static final String DDL_RESOURCE_EXTENSION = ".sql";
    protected static final String DDL_RESOURCE_FOLDER = "ddl";
    private static final String CSV_RESOURCE_FOLDER = "csv";

    private static final String SQL_RESOURCE_FOLDER = "sql";
    private static final String SHAPES_TO_DB_FOLDER = "shapesToDB";
    private static final String SHAPES_TO_FEATURED_FOLDER = "shapesToFeatured";

    private static final String MAPPROXY_FOLDER = "mapproxy";
    private static final String MAPPROXY_SERVICE = "mapproxy";
    private static final String MAPPROXY_SEED = "seed";
    private static final String MAPPROXY_CONFIG_EXTENSION = ".yaml";

    private static final String DATASETS_FOLDER = "datasets";
    private static final String TEST_FOLDER = "testset";
    private static final String TEST_DATA_FOLDER = "data";
    private static final String TEST_EXPECTED_FOLDER = "expected";
    private static final String TEST_PREPARE_FOLDER = "prepare";
    private static final String TEMPLATES_FOLDER = "templates";
    private static final String DICTIONARY_SEPARATION_CHAR = "=";

    private static final String SHARED_FOLDER = "shared";

    private static final String FILENAME_JOB_CONFIGURATION = "configuration.json";
    private static final String FILENAME_TILING_CONFIGURATION = "tiling.json";
    private static final String FILENAME_TRANSFORMATION_CONFIGURATION = "transformation.json";
    private static final String FILENAME_EXTRACT_CONFIGURATION = "extract.json";

    private static final String WORKBENCH_EXTENSION = ".fmw";

    private static final String WORKBENCH_FOLDER = "workbench";
    private static final String TEMPLATE_FOLDER = "templates";
    private static final String TEMPLATE_PARTIAL_FOLDER = "partials";
    private static final String XML2JSON_FOLDER = "xml2json";

    private static final String TRANSFORMERS = "transformers";
    private static final String TRANSFORMER_EXTENSION = ".fmx";

    private FmeWorkbenchEnvConfig fmeworkbenchenvconfig = null;
    private Path datasetsFolder;
    private Path catalogFolder;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileSystemCatalog.class);

    public FileSystemCatalog(File catalogFolder, FmeWorkbenchEnvConfig fmeworkbenchenvconfig) {
        this(catalogFolder);
        this.fmeworkbenchenvconfig = fmeworkbenchenvconfig;
    }

    public FileSystemCatalog(File catalogFolder) {
        this.catalogFolder = catalogFolder.toPath();
        this.datasetsFolder = Paths.get(this.catalogFolder.toString(), DATASETS_FOLDER);
    }

    @Override
    public Collection<String> getDatasetNames() {
        Collection<String> datasetNames = new ArrayList<>();
        try (DirectoryStream<Path> catalog = Files.newDirectoryStream(datasetsFolder, getFilterPathIsDirectory(true))) {
            for (Path dataset : catalog) {
                datasetNames.add(dataset.getFileName().toString());
            }
        } catch (IOException ex) {
            LOGGER.trace(ex.getMessage(), ex);
        }
        return datasetNames;
    }

    @Override
    public List<Workbench> getWorkbenches(String datasetName) {

        List<Workbench> results = new LinkedList<>();

        File workbenchesFolder = workbenchesFolder(datasetName);

        if (!workbenchesFolder.exists() || !workbenchesFolder.isDirectory()) {
            return results;
        }

        for (WorkbenchType type : WorkbenchType.values()) {
            results.addAll(getWorkbenches(datasetName, type));
        }

        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Workbench> getWorkbenches(String datasetName, WorkbenchType type) {
        List<Workbench> results = new LinkedList<>();

        File typedWorkbenchesFolder = typedWorkbenchesFolder(datasetName, type);

        if (!typedWorkbenchesFolder.exists() || !typedWorkbenchesFolder.isDirectory()) {
            return results;
        }

        File[] workbenches = typedWorkbenchesFolder.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(WORKBENCH_EXTENSION);
            }
        });

        Arrays.sort(workbenches, NameFileComparator.NAME_INSENSITIVE_COMPARATOR);

        for (File workbenchFile : workbenches) {
            results.add(getWorkbench(datasetName, type, workbenchFile.getName()));
        }

        return results;
    }

    @Override
    public Workbench getWorkbench(String datasetName, WorkbenchType type, String workbenchName) {

        File workbenchFile = workbenchFile(datasetName, type, workbenchName);

        if (!workbenchFile.exists() || !workbenchFile.isFile()) {
            return null;
        }

        Workbench result = new Workbench(workbenchFile.getName(), workbenchFile);

        for (File resource : getResources(datasetName, type, workbenchName)) {
            result.addResource(new WorkbenchResource(resource.getName(), resource));
        }

        try {
            for (WorkbenchParameter parameter : getParameters(datasetName, type, workbenchName)) {
                result.addParameter(parameter);
            }
        } catch (IOException ex) {
            LOGGER.warn(ex.getMessage(), ex);
            // nothing
        }

        return result;
    }

    @Override
    public List<Workbench> getTransformers() {

        List<Workbench> results = new LinkedList<>();
        File transformersFolder = Paths.get(catalogFolder.toString(), SHARED_FOLDER, TRANSFORMERS).toFile();

        if (!transformersFolder.exists() || !transformersFolder.isDirectory()) {
            return results;
        }

        File[] workbenches = transformersFolder.listFiles(new FileFilter() {

            @Override
            public boolean accept(File pathname) {
                return pathname.isFile() && pathname.getName().endsWith(TRANSFORMER_EXTENSION);
            }
        });

        for (File workbenchFile : workbenches) {
            results.add(new Workbench(workbenchFile.getName(), workbenchFile));
        }

        return results;

    }

    private File workbenchesFolder(String datasetName) {
        return Paths.get(datasetsFolder.toString(), datasetName, WORKBENCH_FOLDER).toFile();
    }

    private File typedWorkbenchesFolder(String datasetName, WorkbenchType type) {
        return new File(workbenchesFolder(datasetName), type.toString().toLowerCase());
    }

    private File workbenchFile(String datasetName, WorkbenchType type, String workbenchName) {
        return new File(typedWorkbenchesFolder(datasetName, type), workbenchName);
    }

    private File[] getResources(String datasetName, WorkbenchType type, String workbenchName) {
        File typedWorkbenchesFolder = typedWorkbenchesFolder(datasetName, type);
        File resourceFolder = new File(typedWorkbenchesFolder, workbenchName + RESOURCE_EXTENSION);

        if (resourceFolder.exists() && resourceFolder.isDirectory()) {
            return resourceFolder.listFiles();
        } else {
            return new File[0];
        }
    }

    @SuppressWarnings("unchecked")
    private List<WorkbenchParameter> getParameters(String datasetName, WorkbenchType type, String workbenchName)
            throws IOException {

        List<WorkbenchParameter> results = new LinkedList<>();

        File typedWorkbenchesFolder = typedWorkbenchesFolder(datasetName, type);

        File parametersFile = new File(typedWorkbenchesFolder, workbenchName + PARAMETERS_EXTENSION);

        if (parametersFile.exists() && parametersFile.isFile()) {
            List<String> lines = FileUtils.readLines(parametersFile);

            for (String line : lines) {
                WorkbenchParameter parameter = getParameter(line);
                if (parameter != null) {
                    results.add(parameter);
                }
            }
        }

        for (WorkbenchParameter parameter : results) {
            if (parameter.getValue().equals("*")) {
                for (WorkbenchParameter workbenchenvparameter : fmeworkbenchenvconfig.getParameters()) {
                    if (workbenchenvparameter.getName().equals(parameter.getName())) {
                        parameter.setValue(workbenchenvparameter.getValue());
                    }
                }
            }
        }
        return results;
    }

    private WorkbenchParameter getParameter(String line) {
        if (line == null || line.isEmpty()) {
            return null;
        }

        String[] splitted = line.split(DICTIONARY_SEPARATION_CHAR, 2);
        if (splitted.length <= 1 || splitted[1].isEmpty()) {
            return null;
        }

        return new WorkbenchParameter(splitted[0], splitted[1]);
    }

    public String getServiceName(String datasetName) {
        return loadJobConfiguration(datasetName).getWorkspace();
    }

    @Override
    public String getGeoserverType(String datasetName) {
        return loadJobConfiguration(datasetName).getGeoserver();
    }

    @Override
    public String getTargetProjection(final String datasetName) {
        return (String) getSingleDatasetProperty(datasetName, datasetName, "target_projection");
    }

    @Override
    public boolean isVersioned(final String datasetName) {
        return BooleanUtils.toBooleanDefaultIfNull((Boolean) getSingleDatasetProperty(datasetName, datasetName,
                "versioned"), false);
    }

    @Override
    public boolean withIndexes(final String datasetName) {
        return BooleanUtils.toBooleanDefaultIfNull((Boolean) getSingleDatasetProperty(datasetName, datasetName,
                "withindexes"), true);
    }

    @Override
    public boolean withGtPkMetadata(final String datasetName) {
        return BooleanUtils.toBooleanDefaultIfNull((Boolean) getSingleDatasetProperty(datasetName, datasetName,
                "withgtpkmetadata"), true);
    }

    @Override
    public String getDatasetNameVersioned(final String datasetName, long version) {
        return isVersioned(datasetName) ? datasetName + "_v" + version : datasetName;
    }

    @Override
    public Set<String> getVersionedSchemas(final String datasetName, final long version) {
        Set<String> schemas = new HashSet<>();
        if (datasetExists(datasetName)) {
            JobConfiguration jobConfig = loadJobConfiguration(datasetName);
            for (JobConfigurationDataset dataset : jobConfig.getDatasets()) {
                if (dataset.getVersioned() != null && dataset.getVersioned() && dataset.getDbSchema() != null) {
                    schemas.add(dataset.getDbSchema() + "_v" + version);
                }
            }
        }
        return schemas;
    }

    private Object getSingleDatasetProperty(final String workspaceName, final String datasetName,
            final String propertyName) {
        JobConfiguration jobConfig = loadJobConfiguration(workspaceName);

        Object res = null;
        if (jobConfig != null) {
            List<Object> propertyInDatasetConfigs = FluentIterable.from(jobConfig.getDatasets())
                    .filter(new Predicate<JobConfigurationDataset>() {

                        @Override
                        public boolean apply(JobConfigurationDataset t) {
                            return StringUtils.equals(t.getName(), datasetName);
                        }
                    }).transform(new Function<JobConfigurationDataset, Object>() {

                        @Override
                        public Object apply(JobConfigurationDataset f) {
                            try {
                                return PropertyUtils.getProperty(f, propertyName);
                            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                                String msg = buildPropertyExceptionMessage(f, propertyName, workspaceName, datasetName);
                                throw new IllegalStateException(msg, ex);
                            }
                        }
                    }).filter(Predicates.notNull()).toList();
            if (propertyInDatasetConfigs.size() > 1) {
                throw new IllegalStateException("Multiple values found for property " + propertyName + " in workspace "
                        + workspaceName + " and dataset " + datasetName);
            } else if (propertyInDatasetConfigs.size() == 1) {
                res = propertyInDatasetConfigs.get(0);
            }
        }
        return res;
    }

    private String buildPropertyExceptionMessage(JobConfigurationDataset f, String propertyName, String workspaceName,
            String datasetName) {
        List<String> properties = FluentIterable.from(Arrays.asList(BeanUtils.getPropertyDescriptors(f.getClass())))
                .transform(new Function<PropertyDescriptor, String>() {

                    @Override
                    public String apply(PropertyDescriptor f1) {
                        return f1.getName();
                    }
                }).toList();
        return "Property " + propertyName + "is not defined for dataset configuration in catalogus workspace "
                + workspaceName + ", " + datasetName + ". Available properties: " + Joiner.on(", ").join(properties);
    }

    @Override
    public ArrayList<FeaturedCollectionOptions> getFeaturedOptions(String datasetName) {
        ArrayList<FeaturedCollectionOptions> collectionoptions = loadJobConfiguration(datasetName).getFeatured();
        if (collectionoptions == null) {
            collectionoptions = new ArrayList<>();
        }
        return collectionoptions;
    }

    private JobConfiguration loadJobConfiguration(String datasetName) {
        File configurationFile = Paths.get(datasetsFolder.toString(), datasetName, FILENAME_JOB_CONFIGURATION).toFile();
        return JobConfigurationReader.read(configurationFile, datasetName);
    }

    @Override
    public TilingConfiguration getTilingConfiguration(String datasetName) {
        File configurationFile = Paths.get(datasetsFolder.toString(), datasetName, FILENAME_TILING_CONFIGURATION)
                .toFile();
        return TilingConfigurationReader.read(configurationFile, datasetName);
    }

    @Override
    public InputStream getMapProxyTemplate(String datasetName, String configFile) throws IOException {
        if (MAPPROXY_SERVICE.equals(configFile.toLowerCase()) || MAPPROXY_SEED.equals(configFile.toLowerCase())) {
            File mapYaml = Paths.get(datasetsFolder.toString(), datasetName, MAPPROXY_FOLDER,
                    configFile + MAPPROXY_CONFIG_EXTENSION).toFile();
            return new FileInputStream(mapYaml);
        } else {
            LOGGER.warn("no mapproxy configfile found with the name: " + configFile);
            throw new RuntimeException();
        }
    }

    @Override
    public ExtractConfiguration getExtractConfiguration(String datasetName) {
        File configurationFile = Paths.get(datasetsFolder.toString(), datasetName, FILENAME_EXTRACT_CONFIGURATION)
                .toFile();
        return ExtractConfigurationReader.read(configurationFile, datasetName);
    }

    @Override
    public List<JobConfigurationDataset> getDatastoresConfiguration(String datasetName) {
        return loadJobConfiguration(datasetName).getDatasets();
    }

    @Override
    public Set<String> getExtractTypes(String datasetName) throws IOException {
        Set<String> res = new HashSet<>();

        Path templateFolder = Paths.get(datasetsFolder.toString(), datasetName, TEMPLATE_FOLDER);

        if (Files.isDirectory(templateFolder)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(templateFolder)) {
                for (Path entry : stream) {
                    if (Files.isDirectory(entry)) {
                        res.add(entry.getFileName().toString());
                    }
                }
            }
        }

        return res;
    }

    @Override
    public Set<FeatureTemplate> getFeatureTemplates(String datasetName) throws IOException {
        Set<FeatureTemplate> res = new HashSet<>();

        for (String extractType : getExtractTypes(datasetName)) {
            Path entry = Paths.get(datasetsFolder.toString(), datasetName, TEMPLATE_FOLDER, extractType);
            res.add(getFeatureTemplateExtractType(entry));
        }

        return res;
    }

    private FeatureTemplate getFeatureTemplateExtractType(Path extractTypeFolder) throws IOException {
        FeatureTemplate res = new FeatureTemplate(extractTypeFolder.getFileName().toString());

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(extractTypeFolder)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)
                        && StringUtils.equals(TEMPLATE_PARTIAL_FOLDER, entry.getFileName().toString())) {
                    getFeatureTemplatePartials(entry, res);
                } else if (Files.isRegularFile(entry)) {
                    res.addFeature(entry);
                }
            }
        }

        return res;
    }

    private void getFeatureTemplatePartials(Path extractTypeFolder, FeatureTemplate res) throws IOException {
        try (DirectoryStream<Path> partialsStream = Files.newDirectoryStream(extractTypeFolder)) {
            for (Path partialEntry : partialsStream) {
                if (Files.isRegularFile(partialEntry)) {
                    res.addPartialFeature(partialEntry);
                }
            }
        }
    }

    @Override
    public String getXml2JsonMapping(String datasetName, String translator) throws IOException {
        Path xml2jsonFolder = Paths.get(datasetsFolder.toString(), datasetName, XML2JSON_FOLDER);

        if (!Files.exists(xml2jsonFolder)) {
            throw new IllegalStateException("XML2JSON_FOLDER not found at " + xml2jsonFolder);
        }

        StringBuilder resultBuilder = new StringBuilder();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(xml2jsonFolder)) {
            for (Path path : stream) {
                if (path.getFileName().toString().toLowerCase().contains(translator)) {
                    String body = new String(Files.readAllBytes(path));
                    resultBuilder.append(body);
                    LOGGER.debug("{} read as xml2json file", path);
                    break;
                }
            }
        }

        String result = resultBuilder.toString();
        LOGGER.debug("Using xml2json mapping: {}", result);
        return result;
    }

    @Override
    public String getEngineTransformJson(String datasetName, String defaultEngine) throws ConfigurationException {
        File transformFile = Paths.get(datasetsFolder.toString(), datasetName, FILENAME_TRANSFORMATION_CONFIGURATION)
                .toFile();

        if (!transformFile.exists()) {
            return defaultEngine;
        }

        TransformationConfiguration configuration = TransformationConfigurationReader.read(datasetName, transformFile);
        String engine = configuration.getEngine();
        if (engine == null) {
            throw new ConfigurationException("Configuration-item 'engine' for dataset " + datasetName + " is empty");
        } else {
            return configuration.getEngine();
        }
    }

    @Override
    public String getRootLocation() {
        return this.catalogFolder.toString();
    }

    protected DirectoryStream.Filter<Path> getFilterPathIsDirectory(final boolean isDirectory) {
        return new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                return Files.isDirectory(entry) == isDirectory;
            }
        };
    }

    @Override
    public List<TestData> getDatasetTestData(String datasetName) {
        Path testDataFolder = getDatasetLocationTestData(datasetName);
        List<TestData> results = new ArrayList<>();

        try (DirectoryStream<Path> ds = Files.newDirectoryStream(testDataFolder, getFilterPathIsDirectory(false))) {
            for (Path testDataFile : ds) {
                boolean zipped = ZipUtils.isZip(testDataFile.toFile());
                results.add(new TestData(testDataFile.getFileName().toString(),
                        new FileInputStream(testDataFile.toFile()), zipped));
            }
        } catch (IOException ex) {
            //nothing
        }

        return results;
    }

    @Override
    public Path getDatasetLocationTest(String datasetName) {
        return Paths.get(datasetsFolder.toString(), datasetName, TEST_FOLDER);
    }

    @Override
    public Path getDatasetLocationTestData(String datasetName) {
        return Paths.get(getDatasetLocationTest(datasetName).toString(), TEST_DATA_FOLDER);
    }

    @Override
    public Path getDatasetLocationTestExpected(String datasetName) {
        return Paths.get(getDatasetLocationTest(datasetName).toString(), TEST_EXPECTED_FOLDER);
    }

    @Override
    public Path getDatasetLocationShapesToDB(String datasetName) {
        return Paths.get(datasetsFolder.toString(), datasetName, SHAPES_TO_DB_FOLDER);
    }

    @Override
    public Path getDatasetLocationShapesToFeatured(String datasetName) {
        return Paths.get(datasetsFolder.toString(), datasetName, SHAPES_TO_FEATURED_FOLDER);
    }

    @Override
    public Path getDatasetLocationTemplatesPath(String dataset) {
        return Paths.get(this.datasetsFolder.toString(), dataset, TEMPLATES_FOLDER);
    }

    @Override
    public File getTemplateResource(String dataset, String format, String file, String subfolder) {
        if (subfolder.isEmpty()) {
            return Paths.get(getDatasetLocationTemplatesPath(dataset).toString(), format, file).toFile();
        } else {
            return Paths.get(getDatasetLocationTemplatesPath(dataset).toString(), format, subfolder, file).toFile();
        }
    }

    @Override
    public InputStream getDdlResource(String datasetName) throws IOException {
        File ddlResourceFile = Paths.get(datasetsFolder.toString(), datasetName, DDL_RESOURCE_FOLDER,
                datasetName + DDL_RESOURCE_EXTENSION).toFile();
        return new FileInputStream(ddlResourceFile);
    }

    @Override
    public InputStream getSqlResource(String datasetName, String fileName) throws IOException {
        File sqlResourceFile = Paths.get(datasetsFolder.toString(), datasetName, SQL_RESOURCE_FOLDER, fileName)
                .toFile();
        return new FileInputStream(sqlResourceFile);
    }

    @Override
    public InputStream getCsvResource(String datasetName, String fileName) throws IOException {
        File csvResourceFile = Paths.get(datasetsFolder.toString(), datasetName, CSV_RESOURCE_FOLDER, fileName)
                .toFile();
        return new FileInputStream(csvResourceFile);
    }

    @Override
    public boolean datasetExists(String datasetName) {
        File datasetFolder = Paths.get(datasetsFolder.toString(), datasetName).toFile();
        return datasetFolder.exists();
    }

    @Override
    public InputStream getResourcePrepareTestset(String datasetName, String resourceName) throws IOException {
        File prepareTestsetFile = Paths.get(datasetsFolder.toString(), datasetName, TEST_FOLDER, TEST_PREPARE_FOLDER,
                resourceName).toFile();
        return new FileInputStream(prepareTestsetFile);
    }

    public static String getWorkbenchExtension() {
        return WORKBENCH_EXTENSION;
    }

    /* (non-Javadoc)
     * @see nl.pdok.catalog.Catalog#retrieveJobEntriesByDataset(java.lang.String)
     */
    @Override
    public List<JobEntry> retrieveJobEntriesByDataset(String dataset) {
        return JobEntriesReader.retrieveJobEntriesByDatasetFromCatalogus(catalogFolder.toFile(), dataset);
    }

    /* (non-Javadoc)
     * @see nl.pdok.catalog.Catalog#checkout(java.lang.String, java.lang.String)
     */
    @Override
    public boolean checkout(String branchName, String authorization) {
        return GitInteractionsHandler.checkout(branchName, catalogFolder.toFile(), authorization);
    }
    
    /* (non-Javadoc)
     * @see nl.pdok.catalog.Catalog#isCatalogusBranchPresent()
     */
    @Override
    public boolean isCatalogusBranchPresent() {
        return GitInteractionsHandler.isCatalogusPresent(catalogFolder.toFile());
    }

    /* (non-Javadoc)
     * @see nl.pdok.catalog.Catalog#checkCatalogusBranch()
     */
    @Override
    public String checkCatalogusBranch() {
        return GitInteractionsHandler.whichBranchIsPresent(catalogFolder.toFile());
    }
}
