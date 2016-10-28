package de.unima.dws.semanta.generator;

import java.util.List;

import org.apache.jena.rdf.model.Resource;

@FunctionalInterface
public interface OAGenerator {

	/**
	 * Given a Resource, generates a list of related Resource objects
	 * which represent optional answer entities for a question in the crossword.
	 * Note that the size of the retured list should be equal to numEntities.
	 * @param resource the resource representing the correct answer to a question
	 * @param numEntities the number of resources that should be generated
	 * @return a list of generated resources according to the numEntities number
	 */
	public List<Resource> generate(Resource resource, int numEntities);

}
