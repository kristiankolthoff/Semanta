package de.unima.dws.semanta;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

import de.unima.dws.semanta.generator.EntitySelector;
import de.unima.dws.semanta.generator.HAEntity;
import de.unima.dws.semanta.generator.HAGenerator;
import de.unima.dws.semanta.generator.NodeSumSelector;
import de.unima.dws.semanta.generator.OAGenerator;
import de.unima.dws.semanta.generator.PropertyHAGenerator;
/**
 * Semanta is the main logical component for generating a semantic topic-based crossword
 * puzzle as combines strategies for selecting topic-related entities, generates HAEntities
 * based on the selected Resources and additionally identifies and retrieves related opational
 * answers for each HAEntitiy.
 */
public class Semanta {

	private EntitySelector selector;
	private HAGenerator generator;
	private OAGenerator optionalGenerator;
	
	public Semanta(EntitySelector selector, HAGenerator generator) {
		this.selector = selector;
		this.generator = generator;
	}

	public Semanta() {
		this.selector = new NodeSumSelector();
		this.generator = new PropertyHAGenerator();
	}
	
	public List<HAEntity> fetchEntities(String topic, int numEntities, boolean optionalAnswers) {
		List<HAEntity> haEntities = new ArrayList<>();
		for (int i = 0; i < numEntities; i++) {
			Resource topicResource = ResourceFactory.createResource(topic);
			Resource haResource = this.selector.select(topicResource);
			HAEntity entity = this.generator.generate(haResource, null, topicResource);
			if(optionalAnswers) {
				entity.setOAResources(this.optionalGenerator.generate(haResource, 3));
			}
			haEntities.add(entity);
		}
		return haEntities;
	}

	public EntitySelector getSelector() {
		return selector;
	}

	public void setSelector(EntitySelector selector) {
		this.selector = selector;
	}

	public HAGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(HAGenerator generator) {
		this.generator = generator;
	}

	public OAGenerator getOptionalGenerator() {
		return optionalGenerator;
	}

	public void setOptionalGenerator(OAGenerator optionalGenerator) {
		this.optionalGenerator = optionalGenerator;
	}
}
