package de.unima.dws.semanta.selector;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Entity;

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
	 * @return an Entity contining the selected resource
	 */
	public Entity select(Resource topicResource);
}
