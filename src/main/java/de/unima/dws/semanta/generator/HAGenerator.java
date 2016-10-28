package de.unima.dws.semanta.generator;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

@FunctionalInterface
public interface HAGenerator {

	public HAEntity generate(Resource resource, Property property, Resource topicResource);
}