package de.unima.dws.semanta.selector;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.service.SparqlService;

public class NodeDegreeSelector implements EntitySelector {

	
	public List<Entity> select(Resource topicResource, Difficulty difficulty, int numEntities) {
		List<Entity> entities = new ArrayList<>();
//		ResultSet result = SparqlService.queryNodeSumEntities(topicResource.getURI(), 
//				this.estimateThreshold(topicResource), filterSubjects, filterPredicates);
//		QuerySolution qs = result.next();
//		Resource resource = SparqlService.queryResourceWithTypeHierachy(qs.getResource("s").getURI());
//		Entity entity = new Entity(resource, ResourceFactory.createProperty(qs.getResource("p").getURI()), false);
		return entities;
	}
	
	public int estimateThreshold(Resource topicResource) {
		return 200;
	}


}
