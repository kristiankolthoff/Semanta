package de.unima.dws.semanta.generator.ha;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.model.HAEntity;

@FunctionalInterface
public interface HAGenerator {

	/**
	 * Generates a HAEntity for the first Resource parameter
	 * @param entity the Entity a hint/answer entity should be generated from
	 * @param topicResource the resource of the topic entity
	 * @return hint/answer entity based on the given resource
	 */
	public HAEntity generate(Entity entitiy, Resource topicResource);
}
