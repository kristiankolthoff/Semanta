package de.unima.dws.semanta.selector;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.service.SparqlService;

public class NodeSumSelector implements EntitySelector {

	private List<Entity> cache;
	
	public NodeSumSelector() {
		this.cache = new ArrayList<>();
	}
	public Entity select(Resource topicResource) {
		List<String> filterSubjects = new ArrayList<>();
		List<String> filterPredicates = new ArrayList<>();
		for(Entity entity : this.cache) {
			filterSubjects.add(entity.getResource().getURI());
			filterPredicates.add(entity.getProperty().getURI());
		}
		ResultSet result = SparqlService.queryNodeSumEntities(topicResource.getURI(), 
				this.estimateThreshold(topicResource), filterSubjects, filterPredicates);
		QuerySolution qs = result.next();
		Resource resource = SparqlService.queryResourceWithTypeHierachy(qs.getResource("s").getURI());
		Entity entity = new Entity(resource, ResourceFactory.createProperty(qs.getResource("p").getURI()), false);
		cache.add(entity);
		return entity;
	}
	
	public int estimateThreshold(Resource topicResource) {
		return 200;
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
