package de.unima.dws.semanta.selector;

import java.util.List;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;

public class MetaEntitySelector implements EntitySelector{

	private EntitySelector pageRankSelector;
	private EntitySelector outSelector;
	
	public MetaEntitySelector() {
		this.pageRankSelector = new NodeDegreeSelector(300);
		this.outSelector = new OutEntitySelector();
	}
	
	@Override
	public List<Entity> select(Resource topicResource, Difficulty difficulty, int numEntities) {
		int num = numEntities / 2;
		List<Entity> outEntites = this.outSelector.select(topicResource, difficulty, num);
		List<Entity> pageEntites = this.pageRankSelector.select(topicResource, difficulty, 
				numEntities-outEntites.size());
		pageEntites.addAll(outEntites);
		return pageEntites;
	}

}
