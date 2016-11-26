package de.unima.dws.semanta.generator.ha;

import org.apache.jena.rdf.model.ResourceFactory;
import org.junit.Before;
import org.junit.Test;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.model.HAEntity;
import de.unima.dws.semanta.service.SparqlService;
import de.unima.dws.semanta.utilities.Settings;

public class SummaryHAGenertorTest {

	private SummaryHAGenerator generator;
	
	@Before
	public void init() {
		SparqlService.setEndpoint(Settings.DEFAULT_ENDPOINT_DBPEDIA);
		generator = new SummaryHAGenerator();
	}
	
	@Test
	public void removeAdditionalInfoTest() {
		HAEntity entity = generator.generate(new Entity(SparqlService.
				queryResourceWithTypeHierachy("http://dbpedia.org/resource/Berlin"), null, false), 
				ResourceFactory.createResource("http://dbpedia.org/resource/Germany"), Difficulty.EXPERT);
		System.out.println(entity.getHintsBeautified());
		
	}
	
	@Test
	public void findRegexTest() {
		HAEntity entity = generator.generate(new Entity(SparqlService.
				queryResourceWithTypeHierachy("http://dbpedia.org/resource/Angela_Merkel"), null, false), 
				ResourceFactory.createResource("http://dbpedia.org/resource/Germany"), Difficulty.EXPERT);
		System.out.println(entity.getHintsBeautified());
	}
}
