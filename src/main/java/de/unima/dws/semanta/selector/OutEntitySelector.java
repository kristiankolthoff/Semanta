package de.unima.dws.semanta.selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.service.SparqlService;

public class OutEntitySelector implements EntitySelector{

	@Override
	public List<Entity> select(Resource topicResource, Difficulty difficulty, int numEntities) {
		System.out.println("---------OUTENTITYSELECTOR---------");
		List<Resource> resources = SparqlService.queryResourcesByPropertyRanks(topicResource.getURI());
		if(difficulty == Difficulty.BEGINNER) {
			return select(resources, 0, numEntities);
		} else if(difficulty == Difficulty.ADVANCED) {
			return select(resources, resources.size() / 3, resources.size() / 3 + numEntities);
		} else if(difficulty == Difficulty.EXPERT) {
			return select(resources, (resources.size() / 3)*2, (resources.size() / 3)*2 + numEntities);
		}
		return Collections.emptyList();
	}
	
	private List<Entity> select(List<Resource> resources, int from, int to) {
		if(resources.isEmpty()) {
			return Collections.emptyList();
		}
		List<Entity> entities = new ArrayList<>();
		for (int i = from; i < to; i++) {
			entities.add(new Entity(resources.get(i), null, true));
		}
		return entities;
		
	}

}