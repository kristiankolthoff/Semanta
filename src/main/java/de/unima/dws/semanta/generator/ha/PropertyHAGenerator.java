package de.unima.dws.semanta.generator.ha;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.model.HAEntity;

public class PropertyHAGenerator implements HAGenerator {

	
	public HAEntity generate(Entity entity, Resource topicResource, Difficulty difficulty) {
		System.out.println("generate HA");
		return null;
	}

}
