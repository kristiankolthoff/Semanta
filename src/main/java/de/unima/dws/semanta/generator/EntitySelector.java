package de.unima.dws.semanta.generator;

import java.util.List;

import org.apache.jena.rdf.model.Resource;

@FunctionalInterface
public interface EntitySelector {

	public List<Resource> select(Resource topicResource, int numEntities);
}
