package de.unima.dws.semanta.generator.oa;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.service.SparqlService;
import de.unima.dws.semanta.utilities.Settings;

public class HardOAGenerator implements OAGenerator{

	private Random random;
	
	public static final int LIMIT = 200;
	
	public HardOAGenerator() {
		this.random = new Random();
	}
	
	@Override
	public List<Resource> generate(Entity entity, Resource topicResource, int numEntities) {
		ResultSet result = null;
		if(entity.isTyped()) {
			result = SparqlService.querySimilarResources(topicResource.getURI(), 
					entity.getSpecialOntType().getURI(), LIMIT);
		} else {
			result = SparqlService.querySimilarResources(topicResource.getURI(), 
					Settings.OWL_THING, LIMIT);
		}
		return this.selectRandomly(result, numEntities);
	}
	
	public List<Resource> selectRandomly(ResultSet result, int numEntities) {
		List<Resource> resources = new ArrayList<>();
		List<Resource> selectedResources = new ArrayList<>();
		while(result.hasNext()) {
			Resource plainResource = result.next().getResource("s");
			resources.add(plainResource);
		}
		for (int i = 0; i < numEntities; i++) {
			Resource resource = resources.remove(random.nextInt(resources.size()));
			selectedResources.add(SparqlService.queryResourceWithTypeHierachy(resource.getURI()));
		}
		return selectedResources;
	}

}
