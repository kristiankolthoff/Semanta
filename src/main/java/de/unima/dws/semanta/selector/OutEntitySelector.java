package de.unima.dws.semanta.selector;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

public class OutEntitySelector implements EntitySelector{

	@Override
	public Resource select(Resource topicResource) {
		System.out.println("Called");
		System.out.println(topicResource.getURI());
		return ResourceFactory.createResource();
	}

}
