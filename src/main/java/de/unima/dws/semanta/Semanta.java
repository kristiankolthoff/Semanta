package de.unima.dws.semanta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.generator.distractor.DistractorGenerator;
import de.unima.dws.semanta.generator.distractor.TypeDistractorGenerator;
import de.unima.dws.semanta.generator.ha.HAGenerator;
import de.unima.dws.semanta.generator.ha.PropertyHAGenerator;
import de.unima.dws.semanta.generator.ha.SummaryHAGenerator;
import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.model.HAEntity;
import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.selector.EntitySelector;
import de.unima.dws.semanta.selector.MetaEntitySelector;
import de.unima.dws.semanta.selector.NodeDegreeSelector;
import de.unima.dws.semanta.selector.PageRankEntitySelector;
import de.unima.dws.semanta.service.SparqlService;
import de.unima.dws.semanta.utilities.Settings;
/**
 * Semanta is the main logical component for generating a semantic topic-based crossword
 * puzzle as combines strategies for selecting topic-related entities, generates HAEntities
 * based on the selected Resources and additionally identifies and retrieves related optional
 * answers for each HAEntitiy.
 */
public class Semanta {

	private EntitySelector selector;
	private HAGenerator generator;
	private DistractorGenerator distractorGenerator;
	
	public Semanta(EntitySelector selector, HAGenerator generator,
			DistractorGenerator distractorGenerator) {
		this.selector = selector;
		this.generator = generator;
		this.distractorGenerator = distractorGenerator;
	}

	public Semanta() {
		initialize();
	}
	
	@PostConstruct
	public void initialize() {
		SparqlService.setEndpoint(Settings.DEFAULT_ENDPOINT_DBPEDIA);
		this.selector = new MetaEntitySelector();
		this.generator = new SummaryHAGenerator();
		this.distractorGenerator = new TypeDistractorGenerator();
	}
	
	public List<HAEntity> fetchEntities(String uri, int numEntities, boolean optionalAnswers, Difficulty difficulty) {
		List<HAEntity> haEntities = new ArrayList<>();
		Resource topicResource = SparqlService.queryResource(uri);
		List<Entity> resourceEntity = this.selector.select(topicResource, difficulty, numEntities);
		for (int i = 0; i < numEntities; i++) {
			HAEntity entity = this.generator.generate(resourceEntity.get(i), topicResource, difficulty);
			if(false) {
				entity.setOAResources(this.distractorGenerator.generate(resourceEntity.get(i), topicResource, difficulty, 3));
			}
			haEntities.add(entity);
		}
		return haEntities;
	}
	
	public List<HAEntity> fetchEntities(String uri, int numEntities, boolean optionalAnswers) {
		return fetchEntities(uri, numEntities, optionalAnswers, Difficulty.BEGINNER);
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

	public DistractorGenerator getOptionalGenerator() {
		return distractorGenerator;
	}

	public void setOptionalGenerator(DistractorGenerator optionalGenerator) {
		this.distractorGenerator = optionalGenerator;
	}
	
	public List<ResourceInfo> fetchTopics(String keyword,int topicsCount) {
		return SparqlService.getTopics(keyword, topicsCount);
	}
	
}
