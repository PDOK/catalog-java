package nl.pdok.catalog.tiling;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TilingConfiguration {

    private String workspace;
    private Boolean persistent;
    private TilingLayer[] layers;

    public TilingConfiguration() {
        super();
    }

    public String getWorkspace() {
        return workspace;
    }

    public void setWorkspace(String workspace) {
        this.workspace = workspace;
    }

    public TilingLayer[] getLayers() {
        return layers;
    }

    public void setLayers(TilingLayer[] layers) {
        this.layers = layers;
    }

    public Boolean getPersistent() {
        return persistent;
    }

    public void setPersistent(Boolean persistent) {
        this.persistent = persistent;
    }
}
