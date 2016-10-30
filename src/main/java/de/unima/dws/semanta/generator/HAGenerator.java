package de.unima.dws.semanta.generator;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.HAEntity;

@FunctionalInterface
public interface HAGenerator {

	/**
	 * Generates a HAEntity for the first Resource parameter
	 * @param resource the resource a hint/answer entity should generated from
	 * @param property the property that links the resource with the resource of the topic entity
	 * @param topicResource the resource of the topic entity
	 * @return hint/answer entity based on the given resource
	 */
	public HAEntity generate(Resource resource, Property property, Resource topicResource);
}
