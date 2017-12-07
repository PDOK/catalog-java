package nl.pdok.catalog.gmlconverter;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties
public class GmlConverterConfig implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8243114907630395209L;

    @JsonProperty(value = "gmlId")
    private String gmlId;

    @JsonProperty(value = "action")
    private String action;

    @JsonProperty(value = "dataset")
    private String dataset;

    @JsonProperty(value = "featureNodes")
    private List<String> featureNodes;

    @JsonProperty(value = "arrayNodes")
    private List<String> arrayNodes;

    @JsonProperty(value = "skipNodes")
    private List<String> skipNodes;

    @JsonProperty(value = "renameNodes")
    private Map<String, String> renameNodes;

    @JsonProperty(value = "attributeStrategy")
    private String attributeStrategy;

    @JsonProperty(value = "attributeExcept")
    private List<String> attributeExcept;

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

    public String getAttributeStrategy() {
        return attributeStrategy;
    }

    public void setAttributeStrategy(String attributeStrategy) {
        this.attributeStrategy = attributeStrategy;
    }

    public List<String> getAttributeExcept() {
        return attributeExcept;
    }

    public void setAttributeExcept(List<String> attributeExcept) {
        this.attributeExcept = attributeExcept;
    }
}
