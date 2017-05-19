package nl.pdok.catalog.job;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonTypeName;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeName("dataset")
public class JobConfigurationDataset {

    private String name;
    private String target_projection;
    private Boolean versioned;
    private Boolean withindexes;
    private Boolean withgtpkmetadata;

    @JsonProperty("db_schema")
    private String dbSchema;

    @JsonProperty("mosaic_store")
    private String mosaicStore;

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

    public String getDbSchema() {
        return dbSchema;
    }

    public void setDbSchema(String dbSchema) {
        this.dbSchema = dbSchema;
    }

    public String getMosaicStore() {
        return mosaicStore;
    }

    public void setMosaicStore(String mosaicStore) {
        this.mosaicStore = mosaicStore;
    }
}
