package de.unima.dws.semanta.selector;

import java.util.List;

import org.apache.jena.rdf.model.ResourceFactory;
import org.junit.Before;
import org.junit.Test;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.service.SparqlService;
import de.unima.dws.semanta.utilities.Settings;

public class OutEntitySelectorTest {

	private OutEntitySelector selector;
	
	@Before
	public void init() {
		SparqlService.setEndpoint(Settings.DEFAULT_ENDPOINT_DBPEDIA);
		this.selector = new OutEntitySelector();
	}
	
	@Test
	public void selectBeginnerTest() {
		List<Entity> entity = this.selector.select(ResourceFactory.
				createResource("http://dbpedia.org/resource/Germany"), Difficulty.EXPERT, 5);
		System.out.println(entity);
		
	}
}
