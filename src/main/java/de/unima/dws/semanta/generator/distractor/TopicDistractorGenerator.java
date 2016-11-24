package de.unima.dws.semanta.generator.distractor;

import java.util.List;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.service.SparqlService;

public class TopicDistractorGenerator implements DistractorGenerator{

	@Override
	public List<ResourceInfo> generate(Entity entity, Resource topicResource, Difficulty difficulty, int numEntities) {
		List<ResourceInfo> distractors = null;
		if(difficulty == Difficulty.BEGINNER) {
			distractors = SparqlService.querySimilarTopicResources(
					entity.getResource().getURI(), entity.getGeneralOntType().getURI(), 3);
		} else if(difficulty == Difficulty.ADVANCED) {
			distractors = SparqlService.querySimilarTopicResources(
					entity.getResource().getURI(), entity.getMediumOntType().getURI(), 3);
		} else if(difficulty == Difficulty.EXPERT) {
			distractors = SparqlService.querySimilarTopicResources(
					entity.getResource().getURI(), entity.getSpecialOntType().getURI(), 3);
		}
		return distractors;
	}

}
