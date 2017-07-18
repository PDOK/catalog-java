package nl.pdok.catalog.featured;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatabaseMapping {

    private String[] array;
    
    private String[] unnest;

    public String[] getArray() {
        return array;
    }

    public void setArray(String[] array) {
        this.array = array;
    }

    public String[] getUnnest() {
        return unnest;
    }

    public void setUnnest(String[] unnest) {
        this.unnest = unnest;
    }
}