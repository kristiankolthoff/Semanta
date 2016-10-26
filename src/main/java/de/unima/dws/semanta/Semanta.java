package de.unima.dws.semanta;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

import de.unima.dws.semanta.generator.EntitySelector;
import de.unima.dws.semanta.generator.HAEntity;
import de.unima.dws.semanta.generator.HAGenerator;
import de.unima.dws.semanta.generator.NodeSumSelector;
import de.unima.dws.semanta.generator.PropertyHAGenerator;

public class Semanta {

	private EntitySelector selector;
	private HAGenerator generator;
	
	public Semanta(EntitySelector selector, HAGenerator generator) {
		this.selector = selector;
		this.generator = generator;
	}

	public Semanta() {
		this.selector = new NodeSumSelector();
		this.generator = new PropertyHAGenerator();
	}
	
	public List<HAEntity> fetchEntities(String topic, int numEntities) {
		Resource topicResource = ResourceFactory.createResource(topic);
		List<Resource> haResources = this.selector.select(topicResource, numEntities);
		List<HAEntity> haEntities = new ArrayList<>();
		for(Resource resource : haResources) {
			haEntities.add(this.generator.generate(resource));
		}
		return haEntities;
	}

	public EntitySelector getSelector() {
		return selector;
	}

	public void setSelector(EntitySelector selector) {
		this.selector = selector;
	}

	public HAGenerator getGenerator() {
		return generator;
	}

	public void setGenerator(HAGenerator generator) {
		this.generator = generator;
	}
}
