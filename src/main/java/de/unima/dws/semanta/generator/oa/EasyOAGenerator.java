package de.unima.dws.semanta.generator.oa;

import java.util.Collections;
import java.util.List;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.model.ResourceInfo;

public class EasyOAGenerator implements OAGenerator {

	@Override
	public List<ResourceInfo> generate(Entity entity, Resource topicResource, Difficulty difficulty,  int numEntities) {
		System.out.println("generate optional answers");
		return Collections.emptyList();
	}

}
