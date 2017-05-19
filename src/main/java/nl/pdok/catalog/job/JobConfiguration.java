package nl.pdok.catalog.job;

import java.util.ArrayList;
import java.util.List;
import nl.pdok.catalog.featured.FeaturedCollectionOptions;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JobConfiguration {

    private String workspace;
    private String geoserver;
    private ArrayList<FeaturedCollectionOptions> featured;

    private List<JobConfigurationDataset> datasets;

    public JobConfiguration() {
        super();
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public String getGeoserver() {
        return geoserver;
    }

    public void setGeoserver(String geoserver) {
        this.geoserver = geoserver;
    }

    public ArrayList<FeaturedCollectionOptions> getFeatured() {
        return featured;
    }

    public void setFeatured(ArrayList<FeaturedCollectionOptions> featured) {
        this.featured = featured;
    }

    public List<JobConfigurationDataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<JobConfigurationDataset> datasets) {
        this.datasets = datasets;
    }
}
