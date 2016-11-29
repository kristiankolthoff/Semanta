package de.unima.dws.semanta.selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;

public class MetaEntitySelector implements EntitySelector{

	private EntitySelector pageRankSelector;
	private EntitySelector outSelector;
	
	public MetaEntitySelector() {
		this.pageRankSelector = new PageRankEntitySelector(300);
		this.outSelector = new OutEntitySelector();
	}
	
	@Override
	public List<Entity> select(Resource topicResource, Difficulty difficulty, int numEntities) {
		List<Entity> outEntites = this.outSelector.select(topicResource, difficulty, numEntities);
		List<Entity> pageEntites = this.pageRankSelector.select(topicResource, difficulty, 
				numEntities);
		List<Entity> result = new ArrayList<>();
		result.addAll(outEntites);
		result.addAll(pageEntites);
		Collections.shuffle(result);
		if(result.size() > numEntities) {
			return result.subList(0, numEntities);
		} else {
			return result;
		}
	}

}
