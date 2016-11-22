package de.unima.dws.semanta.selector;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.service.SparqlService;

public class PageRankEntitySelector implements EntitySelector{

	private double max;
	private double t1;
	private double t2;
	private double min;
	private final int MAX_LIMIT = 300;
	
	@Override
	public Entity select(Resource topicResource, Difficulty difficulty) {
		
		return null;
	}
	
	public void estimateThreshold() {
		
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

}
