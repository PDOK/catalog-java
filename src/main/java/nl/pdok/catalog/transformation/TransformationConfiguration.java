package nl.pdok.catalog.transformation;

public class TransformationConfiguration {

    private String transformationEngine;
    private String extractionEngine;

    public String getEngine() {
        return transformationEngine;
    }

    public void setEngine(String transformationEngine) {
        this.transformationEngine = transformationEngine;
    }

    public String getExtractionEngine() {
        return extractionEngine;
    }

    public void setExtractionEngine(String extractionEngine) {
        this.extractionEngine = extractionEngine;
    }

}
