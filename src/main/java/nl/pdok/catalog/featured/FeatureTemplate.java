package nl.pdok.catalog.featured;

import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Representation of a Feature template.
 *
 * <p>Example for the BGT:</p>
 *
 * <p>extractType = 'citygml'</p>
 *
 * <p>partials = ['city-model-start.mustache', 'city-model-eind.mustache', ...]</p>
 *
 * <p>features = ['buurt.mustache', 'functioneelgebied.mustache', ...]</p>
 */
public class FeatureTemplate implements Serializable {

    /**
     * Extract type (for instance 'citygml').
     */
    private final String extractType;
    
    /**
     * Delta configuration (optional).
     */
    private final DeltaConfiguration deltaConfiguration;

    /**
     * List of partials files.
     */
    private final List<Path> partials = new ArrayList<>();

    /**
     * Lists of feature files.
     */
    private final List<Path> features = new ArrayList<>();

    public FeatureTemplate(String extractType, DeltaConfiguration deltaConfiguration) {
        this.extractType = extractType;
        this.deltaConfiguration = deltaConfiguration;
    }

    public String getExtractType() {
        return extractType;
    }
    
    public DeltaConfiguration getDeltaConfiguration() {
        return deltaConfiguration;
    }

    public List<Path> getPartialTemplates() {
        return partials;
    }

    public List<Path> getCollectionTemplates() {
        return features;
    }

    public void addFeature(Path featurePath) {
        features.add(featurePath);
    }

    public void addPartialFeature(Path partialFeaturePath) {
        partials.add(partialFeaturePath);
    }

    /**
     * @return A set of the filenames of the features.
     */
    public Set<String> getFeatureCollections() {
        return FluentIterable.from(features).transform(new Function<Path, String>() {

            @Override
            public String apply(Path f) {
                return f.getFileName().toString();
            }
        }).toSet();
    }
}
