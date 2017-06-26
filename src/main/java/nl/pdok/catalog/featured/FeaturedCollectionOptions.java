package nl.pdok.catalog.featured;

public class FeaturedCollectionOptions {

    private String collection;
    private String[] options;
    private DatabaseMapping mapping;

    public String getCollection() {
        return collection;
    }

    public void setCollection(String collection) {
        this.collection = collection;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public DatabaseMapping getMapping() {
        return mapping;
    }

    public void setMapping(DatabaseMapping mapping) {
        this.mapping = mapping;
    }
}
