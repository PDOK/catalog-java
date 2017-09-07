package nl.pdok.catalog.transformation;

public class TransformationConfiguration {

    private String transformationEngine;

    //  Can deletes in extraction be done by using the version only
    private Boolean uniqueVersions;

    public String getEngine() {
        return transformationEngine;
    }

    public void setEngine(String transformationEngine) {
        this.transformationEngine = transformationEngine;
    }

    public Boolean getUniqueVersions() {
        return uniqueVersions;
    }

    public void setUniqueVersions(Boolean uniqueVersions) {
        this.uniqueVersions = uniqueVersions;
    }

}
