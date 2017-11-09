package nl.pdok.catalog.gmlconverter;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

@JsonRootName(value="gmlToJson")
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

    @JsonProperty(value = "featureNode")
    private String featureNode;

    @JsonProperty(value = "pathToGML")
    private String pathToGML;

    @JsonProperty(value = "arrayNodes")
    private List<String> arrayNodes;

    @JsonProperty(value = "skipNodes")
    private List<String> skipNodes;

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

    public String getFeatureNode() {
        return featureNode;
    }

    public void setFeatureNode(String featureNode) {
        this.featureNode = featureNode;
    }

    public String getPathToGML() {
        return pathToGML;
    }

    public void setPathToGML(String pathToGML) {
        this.pathToGML = pathToGML;
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
}
