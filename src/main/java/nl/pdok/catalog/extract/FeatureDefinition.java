package nl.pdok.catalog.extract;

import java.util.ArrayList;
import java.util.List;

public class FeatureDefinition {

    private String name;
    private String description;
    private boolean selected;
    private String datatype;
    private List<String> innertypes = new ArrayList<>();

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

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public List<String> getInnertypes() {
        return innertypes;
    }

    public void setInnertypes(List<String> innertypes) {
        this.innertypes = innertypes;
    }
}
