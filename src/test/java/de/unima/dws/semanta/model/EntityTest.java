package de.unima.dws.semanta.model;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.jena.rdf.model.Resource;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.unima.dws.semanta.service.SparqlService;
import de.unima.dws.semanta.utilities.Settings;

public class EntityTest {

	@Before
	public void init() {
		SparqlService.setEndpoint(Settings.DEFAULT_ENDPOINT_DBPEDIA);
	}
	
	@Test
	public void getGeneralOntTypeTest1() {
		Resource resource = SparqlService.queryResourceWithTypeHierachy("http://dbpedia.org/resource/Stanislaw_Tillich");
		Entity entity = new Entity(resource, null, false);
		Resource typeResource = entity.getGeneralOntType();
		assertEquals("http://dbpedia.org/ontology/Agent", typeResource.getURI());
	}
	
	@Test
	public void getGeneralOntTypeTest2() {
		Resource resource = SparqlService.queryResourceWithTypeHierachy("http://dbpedia.org/resource/Mark_Selby");
		Entity entity = new Entity(resource, null, false);
		Resource typeResource = entity.getGeneralOntType();
		assertEquals("http://dbpedia.org/ontology/Agent", typeResource.getURI());
	}
	
	@Test
	public void getSpecialOntTypeTest1() {
		Resource resource = SparqlService.queryResourceWithTypeHierachy("http://dbpedia.org/resource/Stanislaw_Tillich");
		Entity entity = new Entity(resource, null, false);
		Resource typeResource = entity.getSpecialOntType();
		assertEquals("http://dbpedia.org/ontology/OfficeHolder", typeResource.getURI());
	}
	
	@Test
	public void getSpecialOntTypeTest2() {
		Resource resource = SparqlService.queryResourceWithTypeHierachy("http://dbpedia.org/resource/Mark_Selby");
		Entity entity = new Entity(resource, null, false);
		Resource typeResource = entity.getSpecialOntType();
		assertEquals("http://dbpedia.org/ontology/SnookerChamp", typeResource.getURI());
	}
	
	
	@Test @Ignore
	public void getMediumOntTypeTest1() {
		Resource resource = SparqlService.queryResourceWithTypeHierachy("http://dbpedia.org/resource/Stanislaw_Tillich");
		Entity entity = new Entity(resource, null, false);
		Resource typeResource = entity.getMediumOntType();
		assertEquals("http://dbpedia.org/ontology/Person", typeResource.getURI());
	}
	
	@Test
	public void getTypesTest() {
		Resource resource = SparqlService.queryResourceWithTypeHierachy("http://dbpedia.org/resource/Oliver_Kahn");
		Entity entity = new Entity(resource, null, false);
		List<Resource> types = entity.getTypesOrdered();
//		assertEquals("http://dbpedia.org/ontology/SnookerChamp", typeResource.getURI());
	}
}
