package de.unima.dws.semanta.generator.distractor;

import java.util.List;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.service.SparqlService;

public class TypeDistractorGenerator implements DistractorGenerator{

	@Override
	public List<ResourceInfo> generate(Entity entity, Resource topicResource, Difficulty difficulty,
			int length, int numEntities) {
		List<ResourceInfo> distractors = null;
		if(difficulty == Difficulty.BEGINNER) {
			distractors = SparqlService.querySimilarTypeResources(
					entity.getResource().getURI(), entity.getGeneralOntType().getURI(), length, 3);
		} else if(difficulty == Difficulty.ADVANCED) {
			distractors = SparqlService.querySimilarTypeResources(
					entity.getResource().getURI(), entity.getMediumOntType().getURI(), length, 3);
		} else if(difficulty == Difficulty.EXPERT) {
			distractors = SparqlService.querySimilarTypeResources(
					entity.getResource().getURI(), entity.getSpecialOntType().getURI(), length, 3);
		}
		return distractors;
	}
	

}
