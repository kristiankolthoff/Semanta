package de.unima.dws.semanta.selector;

import java.util.List;
import java.util.Random;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.service.SparqlService;

public class PageRankEntitySelector implements EntitySelector{

	//TODO implement cache
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
	public Entity select(Resource topicResource, Difficulty difficulty) {
		estimateThresholds(topicResource.getURI());
		Resource resource = null;
		if(difficulty == Difficulty.BEGINNER) {
			resource = SparqlService.queryEntitesByPageRankThreshold(topicResource.getURI(), 
					max, t1, 1, random.nextInt(10000));
		} else if(difficulty == Difficulty.ADVANCED) {
			resource = SparqlService.queryEntitesByPageRankThreshold(topicResource.getURI(), 
					t1, t2, 1, random.nextInt(10000));
		} else if(difficulty == Difficulty.EXPERT) {
			resource = SparqlService.queryEntitesByPageRankThreshold(topicResource.getURI(), 
					t1, min, 1, random.nextInt(10000));
		}
		return new Entity(resource, null, false);
	}
	
	public void estimateThresholds(String uri) {
		List<Double> ranks = SparqlService.queryOrderedPageRanks(uri, limit);
		max = ranks.get(0);
		min = ranks.get(ranks.size()-1);
		t1 = ranks.get(ranks.size()/3);
		t2 = ranks.get((ranks.size()/3)*2);
	}

	@Override
	public void clear() {
		
	}

	@Override
	public int size() {
		return 0;
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
