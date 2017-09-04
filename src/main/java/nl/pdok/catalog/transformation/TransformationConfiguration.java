package nl.pdok.catalog.transformation;

public class TransformationConfiguration {

    private String transformationEngine;
    private String versionedDeletes;

    public String getEngine() {
        return transformationEngine;
    }

    public void setEngine(String transformationEngine) {
        this.transformationEngine = transformationEngine;
    }

    public String getVersionedDeletes() {
        return versionedDeletes;
    }

    public void setVersionedDeletes(String versionedDeletes) {
        this.versionedDeletes = versionedDeletes;
    }

}
