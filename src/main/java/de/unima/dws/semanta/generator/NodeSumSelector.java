package de.unima.dws.semanta.generator;

import java.util.Collections;
import java.util.List;

import org.apache.jena.rdf.model.Resource;

public class NodeSumSelector implements EntitySelector {

	public List<Resource> select(Resource topicResource, int numEntities) {
		if(numEntities == 0) {
			return Collections.emptyList();
		}
		return null;
	}

}
