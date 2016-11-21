package de.unima.dws.semanta.selector;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.service.SparqlService;

public class OutEntitySelector implements EntitySelector{

	private List<Entity> cache;
	
	public OutEntitySelector() {
		this.cache = new ArrayList<>();
	}
	
	@Override
	public Entity select(Resource topicResource) {
		StmtIterator it = topicResource.listProperties();
		while(it.hasNext()) {
			Statement stmt = it.next();
			Triple triple = stmt.asTriple();
			if(selectTriple(triple)) {
				Entity entity = new Entity(SparqlService.queryResourceWithTypeHierachy(triple.getObject().getURI()),
						ResourceFactory.createProperty(triple.getPredicate().getURI()), true);
				cache.add(entity);
				return entity;
			}
		}
		return new Entity(ResourceFactory.createResource(), ResourceFactory.createProperty(""), true);
	}
	
	
	public boolean isCached(Resource resource) {
		return this.cache.contains(resource);
	}
	
	public boolean isURICached(String uri) {
		for(Entity entity : this.cache) {
			if(entity.getResource().getURI().equals(uri)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean selectTriple(Triple triple) {
		return !triple.getObject().isLiteral() && 
				!triple.getPredicate().getURI().contains("rdf") &&
				!triple.getPredicate().getURI().contains("owl") && 
				!triple.getPredicate().getURI().contains("purl") && 
				!triple.getPredicate().getURI().contains("wiki") &&
				!isURICached(triple.getObject().getURI());
	}

	public List<Entity> getCache() {
		return cache;
	}

	@Override
	public void clear() {
		this.cache.clear();
	}

	@Override
	public int size() {
		return this.cache.size();
	}

}