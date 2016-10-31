package de.unima.dws.semanta;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.generator.EasyOAGenerator;
import de.unima.dws.semanta.generator.HAGenerator;
import de.unima.dws.semanta.generator.OAGenerator;
import de.unima.dws.semanta.generator.PropertyHAGenerator;
import de.unima.dws.semanta.generator.PropertyLinkHAGenerator;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.model.HAEntity;
import de.unima.dws.semanta.selector.EntitySelector;
import de.unima.dws.semanta.selector.OutEntitySelector;
import de.unima.dws.semanta.service.SparqlService;
/**
 * Semanta is the main logical component for generating a semantic topic-based crossword
 * puzzle as combines strategies for selecting topic-related entities, generates HAEntities
 * based on the selected Resources and additionally identifies and retrieves related optional
 * answers for each HAEntitiy.
 */
public class Semanta {

	private EntitySelector selector;
	private HAGenerator generator;
	private OAGenerator optionalGenerator;
	
	public Semanta(EntitySelector selector, HAGenerator generator,
			OAGenerator optionalGenerator) {
		this.selector = selector;
		this.generator = generator;
		this.optionalGenerator = optionalGenerator;
	}

	public Semanta() {
		this.selector = new OutEntitySelector();
		this.generator = new PropertyLinkHAGenerator();
		this.optionalGenerator = new EasyOAGenerator();
	}
	
	public List<HAEntity> fetchEntities(String topic, int numEntities, boolean optionalAnswers) {
		List<HAEntity> haEntities = new ArrayList<>();
		Resource topicResource = SparqlService.queryResource("http://dbpedia.org/resource/Germany");
		for (int i = 0; i < numEntities; i++) {
			Entity resourceEntity = this.selector.select(topicResource);
			HAEntity entity = this.generator.generate(resourceEntity, topicResource);
			if(optionalAnswers) {
				entity.setOAResources(this.optionalGenerator.generate(resourceEntity, 3));
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
