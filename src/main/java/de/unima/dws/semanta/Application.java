package de.unima.dws.semanta;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.apache.commons.lang3.builder.Diff;
import org.apache.jena.rdf.model.ResourceFactory;

import de.unima.dws.semanta.crossword.generation.CrosswordGenerator;
import de.unima.dws.semanta.crossword.generation.InternalGreedyCrosswordGenerator;
import de.unima.dws.semanta.crossword.generation.SimpleCrosswordGenerator;
import de.unima.dws.semanta.crossword.model.Crossword;
import de.unima.dws.semanta.crossword.model.HAWord;
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
	
	@PostConstruct
	public void initialize() {
		generator = new InternalGreedyCrosswordGenerator(new SimpleCrosswordGenerator(), 5, null);
//		generator = new SimpleCrosswordGenerator();
		crossword = null;
		topic = null;
	}
	
	public List<ResourceInfo> getTopicResults(String topic) {
//		ResourceInfo info = new ResourceInfo(ResourceFactory.createResource(), "http://", "agent", "Barack Obama");
//        info.setIndex(1);
//        info.setImageURL("http://a5.files.biography.com/image/upload/c_fill,cs_srgb,dpr_1.0,g_face,h_300,q_80,w_300/MTE4MDAzNDEwNzg5ODI4MTEw.jpg");
//        info.setSummary("blac bla bla");
//        List<ResourceInfo> list = new ArrayList<>();
//        list.add(info);
//        this.infos = list;
//        return list;
		return this.semanta.fetchTopics(topic, 10);
	}
	
	public Crossword generateCrossword(String topic, Difficulty difficulty) {
		System.out.println(difficulty);
		this.difficulty = difficulty;
		if(!topic.contains("http")) {
			List<ResourceInfo> infos = semanta.fetchTopics(topic, 5);
			if(!infos.isEmpty()) {
				//TODO only set if topic not resource uri already
				this.topic = infos.get(0);
				topic = this.topic.getUri();
			}
		}
		List<HAEntity> entities = semanta.fetchEntities(topic, 5, true, difficulty);
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
	
	public Crossword generateCrossword(ResourceInfo info, Difficulty difficulty) {
		this.topic = info;
		return generateCrossword(info.getUri(), difficulty);
	}
	
	public Crossword regenerateCrossword() {
		if(topic != null) {
			return generateCrossword(topic, difficulty);
		}
		return crossword;
	}
	
	public List<ResourceInfo> generateRecommendations() {
		ResourceInfo info = new ResourceInfo(ResourceFactory.createResource(), "http://", "agent", "Barack Obama");
        info.setIndex(1);
        info.setType("Agent");
        info.setImageURL("http://a5.files.biography.com/image/upload/c_fill,cs_srgb,dpr_1.0,g_face,h_300,q_80,w_300/MTE4MDAzNDEwNzg5ODI4MTEw.jpg");
        info.setSummary("blac bla bla");
        List<ResourceInfo> list = new ArrayList<>();
        list.add(info);
        list.add(info);
        list.add(info);
        list.add(info);
        list.add(info);
        list.add(info);
        list.add(info);
        list.add(info);
        list.add(info);
        list.add(info);
        list.add(info);
        list.add(info);
        return list;
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

}
