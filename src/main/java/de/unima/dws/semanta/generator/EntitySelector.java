package de.unima.dws.semanta.generator;

import org.apache.jena.rdf.model.Resource;

@FunctionalInterface
public interface EntitySelector {

	public Resource select(Resource topicResource);
}
