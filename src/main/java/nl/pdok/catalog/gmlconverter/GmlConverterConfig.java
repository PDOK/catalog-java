package nl.pdok.catalog.gmlconverter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonRootName(value = "mapping")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GmlConverterConfig implements Serializable {

    private static final long serialVersionUID = 8243114907630395209L;

    @JsonProperty(value = "gmlId")
    private String gmlId;

    @JsonProperty(value = "action")
    private String action;

    @JsonProperty(value = "dataset")
    private String dataset;

    @JsonProperty(value = "featureNodes")
    private List<String> featureNodes = new ArrayList<>();

    @JsonProperty(value = "arrayNodes")
    private List<String> arrayNodes = new ArrayList<>();

    @JsonProperty(value = "skipNodes")
    private List<String> skipNodes = new ArrayList<>();

    @JsonProperty(value = "renameNodes")
    private Map<String, String> renameNodes = new HashMap<>();

    @JsonProperty(value = "attributeStrategy")
    private AttributeStrategy attributeStrategy = AttributeStrategy.NOTHING;

    @JsonProperty(value = "attributeExcept")
    private List<String> attributeExcept = new ArrayList<>();

    public String getGmlId() {
        return gmlId;
    }

    public void setGmlId(String gmlId) {
        this.gmlId = gmlId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDataset() {
        return dataset;
    }

    public void setDataset(String dataset) {
        this.dataset = dataset;
    }

    public List<String> getFeatureNodes() {
        return featureNodes;
    }

    public void setFeatureNodes(List<String> featureNodes) {
        this.featureNodes = featureNodes;
    }

    public List<String> getArrayNodes() {
        return arrayNodes;
    }

    public void setArrayNodes(List<String> arrayNodes) {
        this.arrayNodes = arrayNodes;
    }

    public List<String> getSkipNodes() {
        return skipNodes;
    }

    public void setSkipNodes(List<String> skipNodes) {
        this.skipNodes = skipNodes;
    }

    public Map<String, String> getRenameNodes() {
        return renameNodes;
    }

    public void setRenameNodes(Map<String, String> renameNodes) {
        this.renameNodes = renameNodes;
    }

    public AttributeStrategy getAttributeStrategy() {
        return attributeStrategy;
    }

    public void setAttributeStrategy(AttributeStrategy attributeStrategy) {
        this.attributeStrategy = attributeStrategy;
    }

    public List<String> getAttributeExcept() {
        return attributeExcept;
    }

    public void setAttributeExcept(List<String> attributeExcept) {
        this.attributeExcept = attributeExcept;
    }
}
