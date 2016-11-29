package de.unima.dws.semanta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.crossword.model.HAWord;
import de.unima.dws.semanta.generator.distractor.DistractorGenerator;
import de.unima.dws.semanta.generator.distractor.LinkingEntityDistractorGenerator;
import de.unima.dws.semanta.generator.distractor.TopicDistractorGenerator;
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
	private Resource topicResource;
	
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
		this.distractorGenerator = new TopicDistractorGenerator();
	}
	
	public List<HAEntity> fetchEntities(String uri, int numEntities, boolean optionalAnswers, Difficulty difficulty) {
		List<HAEntity> haEntities = new ArrayList<>();
		topicResource = SparqlService.queryResource(uri);
		List<Entity> resourceEntity = this.selector.select(topicResource, difficulty, numEntities);
		for (Entity ent : resourceEntity) {
			HAEntity entity = this.generator.generate(ent, topicResource, difficulty);
			boolean alreadyFetched = false;
			for(HAEntity savedEnt : haEntities) {
				if(savedEnt.getAnswer().equals(entity.getAnswer())) {
					alreadyFetched = true;
					break;
				}
			}
			if(!alreadyFetched) {
				haEntities.add(entity);
			}
		}
		return haEntities;
	}
	
	public List<ResourceInfo> generateDistractors(HAEntity entity, String regex, 
			Difficulty difficulty, Resource topicResource) {
		List<ResourceInfo> distractors = this.distractorGenerator.generate(entity.getEntity(), topicResource, 
				difficulty, entity.getSanitizedAnswer().length(), 3);
		return distractors;
	}
	
	public List<HAEntity> fetchEntities(String uri, int numEntities, boolean optionalAnswers) {
		return fetchEntities(uri, numEntities, optionalAnswers, Difficulty.BEGINNER);
	}
	
	public void getFacts(ResourceInfo info) {
		ResultSet rs = SparqlService.queryResourcePropertyRanks(info.getResource().getURI());
		while(rs.hasNext()) {
			QuerySolution qs = rs.next();
			info.addFact(qs.getLiteral("labelP").getString() + 
					" " + qs.getLiteral("labelO").getString());
		}
	}
	
	public void getFacts(HAWord word) {
		if(word.getHAEntity().getFacts().isEmpty()) {
			ResultSet rs = SparqlService.queryResourcePropertyRanks(word.getHAEntity().getResource().getURI());
			while(rs.hasNext()) {
				QuerySolution qs = rs.next();
				word.getHAEntity().addFact(qs.getLiteral("labelP").getString() + 
						" " + qs.getLiteral("labelO").getString());
			}
		}
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

	public Resource getTopicResource() {
		return topicResource;
	}

	public void setTopicResource(Resource topicResource) {
		this.topicResource = topicResource;
	}
	
}
