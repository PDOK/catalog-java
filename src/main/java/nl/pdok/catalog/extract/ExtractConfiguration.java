package nl.pdok.catalog.extract;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExtractConfiguration {

    private String name;
    private String description;
    private boolean history;
    private boolean showcoverage;
    private boolean selectionrequired;
    private List<FeatureDefinition> features = new ArrayList<FeatureDefinition>();
    private Map<String, String> formats = new HashMap<String, String>();
    private List<String> cached = new ArrayList<>();

    public ExtractConfiguration() {
        this.name = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }

    public boolean isShowcoverage() {
        return showcoverage;
    }

    public void setShowcoverage(boolean showcoverage) {
        this.showcoverage = showcoverage;
    }

    public boolean isSelectionrequired() {
        return selectionrequired;
    }

    public void setSelectionrequired(boolean selectionrequired) {
        this.selectionrequired = selectionrequired;
    }

    public List<FeatureDefinition> getFeatures() {
        return features;
    }

    public void setFeatures(List<FeatureDefinition> features) {
        this.features = features;
    }

    public Map<String, String> getFormats() {
        return formats;
    }

    public void setFormats(Map<String, String> formats) {
        this.formats = formats;
    }

    public List<String> getCached() {
        return cached;
    }

    public void setCached(List<String> cached) {
        this.cached = cached;
    }
}
