package nl.pdok.catalog.job;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonTypeName;

/**
 *
 * @author niek
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("dataset")
public class JobConfigurationDataset {

    private String name;
    private String target_projection;
    private Boolean versioned;
    private Boolean withindexes;
    private Boolean withgtpkmetadata;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTarget_projection() {
        return target_projection;
    }

    public void setTarget_projection(String target_projection) {
        this.target_projection = target_projection;
    }

    public Boolean getVersioned() {
        return versioned;
    }

    public void setVersioned(Boolean versioned) {
        this.versioned = versioned;
    }

    public Boolean getWithindexes() {
        return withindexes;
    }

    public void setWithindexes(Boolean withindexes) {
        this.withindexes = withindexes;
    }

    public Boolean getWithgtpkmetadata() {
        return withgtpkmetadata;
    }

    public void setWithgtpkmetadata(Boolean withgtpkmetadata) {
        this.withgtpkmetadata = withgtpkmetadata;
    }
}