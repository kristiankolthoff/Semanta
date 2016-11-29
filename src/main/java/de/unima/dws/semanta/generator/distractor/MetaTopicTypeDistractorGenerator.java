package de.unima.dws.semanta.generator.distractor;

import java.util.Collections;
import java.util.List;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.model.ResourceInfo;

public class MetaTopicTypeDistractorGenerator implements DistractorGenerator{

	private DistractorGenerator typeDistractor;
	private DistractorGenerator topicDistractor;
	
	public MetaTopicTypeDistractorGenerator() {
		this.typeDistractor = new TypeDistractorGenerator();
		this.topicDistractor = new TopicDistractorGenerator();
	}
	
	@Override
	public List<ResourceInfo> generate(Entity entitiy, Resource topicResource, Difficulty difficulty,
			int length, int numEntities) {
		if(difficulty == Difficulty.BEGINNER) {
			return typeDistractor.generate(entitiy, topicResource, difficulty, length, numEntities);
		} else if(difficulty == Difficulty.ADVANCED) {
			return topicDistractor.generate(entitiy, topicResource, difficulty, length, numEntities);
		} else if(difficulty == Difficulty.EXPERT) {
			return topicDistractor.generate(entitiy, topicResource, difficulty, length, numEntities);
		}
		return Collections.emptyList();
	}

}
