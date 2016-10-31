package de.unima.dws.semanta.generator;

import java.util.Collections;
import java.util.List;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Entity;

public class EasyOAGenerator implements OAGenerator {

	@Override
	public List<Resource> generate(Entity entity, int numEntities) {
		System.out.println("generate optional answers");
		return Collections.emptyList();
	}

}
