package de.unima.dws.semanta.generator.oa;

import java.util.List;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.service.SparqlService;

public class HardOAGenerator implements OAGenerator{

	@Override
	public List<Resource> generate(Entity entity, Resource topicResource, int numEntities) {
		entity.getSpecialOntType();
		return null;
	}

}
