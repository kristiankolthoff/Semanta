package de.unima.dws.semanta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.jena.sparql.sse.ItemTransformer;

import de.unima.dws.semanta.crossword.generation.CrosswordGenerator;
import de.unima.dws.semanta.crossword.generation.InternalGreedyCrosswordGenerator;
import de.unima.dws.semanta.crossword.generation.SimpleCrosswordGenerator;
import de.unima.dws.semanta.crossword.model.Cell;
import de.unima.dws.semanta.crossword.model.Crossword;
import de.unima.dws.semanta.crossword.model.HAWord;
import de.unima.dws.semanta.generator.distractor.DistractorGenerator;
import de.unima.dws.semanta.generator.distractor.TypeDistractorGenerator;
import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.HAEntity;
import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.recommender.Recommender;

public class Application {

	@Inject
	private Semanta semanta;
	private CrosswordGenerator generator;
	private Recommender recommender;
	private List<ResourceInfo> infos;
	private Crossword crossword;
	private Difficulty difficulty;
	private ResourceInfo topic;
	private int numOfWords;
	private boolean distractorsLoaded;
	
	@PostConstruct
	public void initialize() {
		generator = new InternalGreedyCrosswordGenerator(new SimpleCrosswordGenerator(), 10, null);
		this.recommender = new Recommender();
		crossword = null;
		topic = null;
		numOfWords = 5;
	}
	
	public List<ResourceInfo> getTopicResults(String topic) {
		return this.semanta.fetchTopics(topic, 10);
	}
	
	public Crossword generateCrossword(String topic, Difficulty difficulty, int numOfWords) {
		System.out.println(difficulty);
		this.difficulty = difficulty;
		this.numOfWords = numOfWords;
		if(!topic.contains("http")) {
			List<ResourceInfo> infos = semanta.fetchTopics(topic, 5);
			if(!infos.isEmpty()) {
				//TODO only set if topic not resource uri already
				this.topic = infos.get(0);
				topic = this.topic.getUri();
			}
		}
		List<HAEntity> entities = semanta.fetchEntities(topic, numOfWords, true, difficulty);
		List<HAWord> words = new ArrayList<>();
		for(HAEntity entity : entities) {
			System.out.println(entity);
			words.add(new HAWord(entity));
		}
		Crossword crossword = this.generator.generate(words);
		crossword.normalize();
//		Crossword crossword = generator.generate(
////				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("tested").addHint("something that can be tested")),
////				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("semanta").addHint("name of this application")),
////				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("barbara").addHint("well known girl name")),
////				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("software").addHint("opposite of hardware")),
////				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("engineering").addHint("something that can be tested,"
////						+ " something that can be tested")),
////				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("amen").addHint("word of the catholic curch")),
////				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("timberners").addHint("inventor of the internet")),
////				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("norbertlammert").addHint("president of the bundestag")),
////				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("robben").addHint("famous soccer player of the netherlands")),
//				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("oliverkahn").addHint("well knwon german goal keeper, whats his name?")),
////				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("ronaldo").addHint("best soccer player of all time")),
//				new HAWord(new HAEntity(ResourceFactory.createResource()).setAnswer("wammtu").addHint("they can fly and help people, what is it?")));
//		crossword.normalize();
		this.crossword = crossword;
		return crossword;
	}
	
	public Crossword generateCrossword(ResourceInfo info, Difficulty difficulty, int numOfWords) {
		this.topic = info;
		return generateCrossword(info.getUri(), difficulty, numOfWords);
	}
	
	public Crossword regenerateCrossword() {
		if(topic != null) {
			return generateCrossword(topic, difficulty, numOfWords);
		}
		return crossword;
	}
	
	public void generateDistractors() {
		for(HAWord word : this.crossword) {
			HAEntity entity = word.getHAEntity();
			entity.setOAResources(this.semanta.generateDistractors(entity, null, 
					difficulty, semanta.getTopicResource()));
		}
	}
	
	
	
//	private String getRegex(HAWord word) {
//		List<Cell> intersections = this.crossword.getIntersections(word);
//		StringBuilder sb = new StringBuilder();
//		sb.append("'^");
//		int intersec = 0, space = 0;
//		for(Cell cell : word) {
//			if(cell.equals(intersections.get(intersec))) {
//				intersec++;
//				if(space != 0) {
//					sb.append("[a-zA-Z]{" + space + "}"+);
//				}
//				space = 0;
//			}
//			space++;
//		}
//		sb.append("[a-zA-Z]{" + startCell + "}");
//		sb.append("$'");
//		[a-zA-Z]{4}r [a-zA-Z]{4}
//		return null;
//	}

	public void generateRecommendations(Consumer<List<ResourceInfo>> callback) {
		this.recommender.getRecommendations(12, callback);
	}

	public Semanta getSemanta() {
		return semanta;
	}

	public void setSemanta(Semanta semanta) {
		this.semanta = semanta;
	}

	public CrosswordGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(CrosswordGenerator generator) {
		this.generator = generator;
	}

	public Recommender getRecommender() {
		return recommender;
	}

	public void setRecommender(Recommender recommender) {
		this.recommender = recommender;
	}

	public Crossword getCrossword() {
		return crossword;
	}

	public ResourceInfo getTopic() {
		return topic;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public List<ResourceInfo> getInfos() {
		return infos;
	}

	public boolean isDistractorsLoaded() {
		return distractorsLoaded;
	}

	public void setDistractorsLoaded(boolean distractorsLoaded) {
		this.distractorsLoaded = distractorsLoaded;
	}
	
	

}
