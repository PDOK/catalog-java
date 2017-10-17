package nl.pdok.catalog.featured;

/**
 * Describes how delta information is to be recorded.
 */
public class DeltaConfiguration {

    private String featureRootTag;
    
    public void setFeatureRootTag(String featureRootTag) {
        this.featureRootTag = featureRootTag;
    }
    
    public String getFeatureRootTag() {
        return featureRootTag;
    }
}
