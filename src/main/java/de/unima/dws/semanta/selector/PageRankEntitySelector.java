package de.unima.dws.semanta.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.service.SparqlService;

public class PageRankEntitySelector implements EntitySelector{

	private double max;
	private double t1;
	private double t2;
	private double min;
	private int limit;
	private Random random;
	
	public PageRankEntitySelector(int limit) {
		this.limit = limit;
		this.random = new Random();
	}
	
	@Override
	public List<Entity> select(Resource topicResource, Difficulty difficulty, int numEntities) {
		estimateThresholds(topicResource.getURI());
		List<Resource> resources = null;
		if(difficulty == Difficulty.BEGINNER) {
			resources = SparqlService.queryEntitesByPageRankThreshold(topicResource.getURI(), 
					max, t1, numEntities, random.nextInt(10000));
		} else if(difficulty == Difficulty.ADVANCED) {
			resources = SparqlService.queryEntitesByPageRankThreshold(topicResource.getURI(), 
					t1, t2, numEntities, random.nextInt(10000));
		} else if(difficulty == Difficulty.EXPERT) {
			resources = SparqlService.queryEntitesByPageRankThreshold(topicResource.getURI(), 
					t1, min, numEntities, random.nextInt(10000));
		}
		List<Entity> entities = new ArrayList<>();
		for(Resource resource : resources) {
			entities.add(new Entity(resource, null, false));
		}
		return entities;
	}
	
	public void estimateThresholds(String uri) {
		List<Double> ranks = SparqlService.queryOrderedPageRanks(uri, limit);
		max = ranks.get(0);
		min = ranks.get(ranks.size()-1);
		t1 = ranks.get(ranks.size()/3);
		t2 = ranks.get((ranks.size()/3)*2);
	}

	public double getMax() {
		return max;
	}

	public double getT1() {
		return t1;
	}

	public double getT2() {
		return t2;
	}

	public double getMin() {
		return min;
	}

}
