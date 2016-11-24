package de.unima.dws.semanta.generator.distractor;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;


import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.service.SparqlService;
import de.unima.dws.semanta.utilities.Settings;

public class TypeDistractorGeneratorTest {

	private TypeDistractorGenerator distractor;
	
	@Before
	public void init() {
		SparqlService.setEndpoint(Settings.DEFAULT_ENDPOINT_DBPEDIA);
		distractor = new TypeDistractorGenerator();
	}
	
	@Test
	public void generateBeginnerTest() {
		List<ResourceInfo> infos = distractor.generate(
				new Entity(SparqlService.queryResourceWithTypeHierachy("http://dbpedia.org/resource/Germany"), null, true), 
				SparqlService.queryResourceWithTypeHierachy("http://dbpedia.org/resource/Germany"), Difficulty.BEGINNER, 3);
		System.out.println(infos);
		assertEquals(3, infos.size());
	}
}
