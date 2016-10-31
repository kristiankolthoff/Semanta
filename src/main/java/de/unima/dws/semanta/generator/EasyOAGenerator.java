package de.unima.dws.semanta.generator;

import java.util.Collections;
import java.util.List;

import org.apache.jena.rdf.model.Resource;

public class EasyOAGenerator implements OAGenerator {

	@Override
	public List<Resource> generate(Resource resource, int numEntities) {
		System.out.println("generate optional answers");
		return Collections.emptyList();
	}

}
