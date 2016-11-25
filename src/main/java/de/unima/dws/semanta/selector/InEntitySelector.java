package de.unima.dws.semanta.selector;

import java.util.List;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;

public class InEntitySelector implements EntitySelector{

	@Override
	public List<Entity> select(Resource topicResource, Difficulty difficulty, int numEntities) {
		return null;
	}


}
