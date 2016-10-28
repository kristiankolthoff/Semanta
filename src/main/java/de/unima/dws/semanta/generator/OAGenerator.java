package de.unima.dws.semanta.generator;

import java.util.List;

import org.apache.jena.rdf.model.Resource;

@FunctionalInterface
public interface OAGenerator {

	public List<Resource> generate(Resource resource, int numEntities);

}
