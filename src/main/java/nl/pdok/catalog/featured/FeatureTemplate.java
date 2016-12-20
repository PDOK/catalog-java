/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * Example for the BGT:
 *
 * extractType = 'citygml'
 *
 * partials = ['city-model-start.mustache', 'city-model-eind.mustache', ..]
 *
 * features = ['buurt.mustache','functioneelgebied.mustache',..]
 *
 *
 * @author Niek Hoogma
 */
public class FeatureTemplate implements Serializable {

    /**
     * Extract type (for instance 'citygml')
     */
    private final String extractType;

    /**
     * List of partials files.
     */
    private final List<Path> partials = new ArrayList<>();

    /**
     * Lists of feature files.
     */
    private final List<Path> features = new ArrayList<>();

    public FeatureTemplate(String extractType) {
        this.extractType = extractType;
    }

    public String getExtractType() {
        return extractType;
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
