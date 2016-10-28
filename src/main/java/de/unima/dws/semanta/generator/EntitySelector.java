package de.unima.dws.semanta.generator;

import org.apache.jena.rdf.model.Resource;

@FunctionalInterface
public interface EntitySelector {

	/**
	 * Based on the topic entitiy as a Resource,
	 * selects another topic related entity as a Resource.
	 * This method is called repeatedly until enough Resources
	 * were selected. Note that the implementing class should avoid
	 * returning the same entity or entities which are linked to
	 * the topic entitiy via the same semantic link among repeated calls.
	 * @param topicResource the topic entity represented as a resource
	 * @return the resource of the selected entity
	 */
	public Resource select(Resource topicResource);
}
