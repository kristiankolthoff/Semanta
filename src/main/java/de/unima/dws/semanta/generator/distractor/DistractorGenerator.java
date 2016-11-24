package de.unima.dws.semanta.generator.distractor;

import java.util.List;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.model.ResourceInfo;

@FunctionalInterface
public interface DistractorGenerator {

	/**
	 * Given a Resource, generates a list of related Resource objects
	 * which represent optional answer entities for a question in the crossword.
	 * Note that the size of the retured list should be equal to numEntities.
	 * @param entity the Entity representing the correct answer to a question
	 * @param numEntities the number of resources that should be generated
	 * @return a list of generated resources according to the numEntities number
	 */
	public List<ResourceInfo> generate(Entity entity, Resource topicResource, 
			Difficulty difficulty, int numEntities);

}
