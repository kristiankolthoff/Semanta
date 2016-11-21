package de.unima.dws.semanta.service;

import static org.junit.Assert.*;

import java.util.List;

import org.apache.jena.graph.Triple;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.junit.Before;
import org.junit.Test;

import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.utilities.Settings;

public class SparqlServiceTest {

	
	@Before
	public void init() {
		SparqlService.setEndpoint(Settings.DEFAULT_ENDPOINT_DBPEDIA);
	}
	
	@Test
	public void dbpediaEndpointTest() {
		final String QUERY = "PREFIX dbo: <http://dbpedia.org/ontology/> SELECT ?o " +
							" WHERE {" +
							" <http://dbpedia.org/resource/Germany> ?p ?o ." +
							" }";
		ResultSet result = SparqlService.query(QUERY);
		QuerySolution qs = result.next();
		assertEquals("http://www.w3.org/2002/07/owl#Thing", qs.get("o").toString());
	}
	
	@Test
	public void getTopics()
	{
		List<ResourceInfo> infos = SparqlService.getTopics("barack obama", 10);
		System.out.println(infos.size());
	}
	
	@Test
	public void queryDescribeTest() {
		Model model = SparqlService.queryDescribe("http://dbpedia.org/resource/Ansungtangmyun");
		StmtIterator it = model.listStatements();
		while(it.hasNext()) {
			Statement stmt = it.next();
			Triple triple = stmt.asTriple();
			System.out.println(triple.toString());
		}
	}
	
	@Test
	public void retrieveResourceFromStringTest() {
		Resource resource = SparqlService.queryResource("http://dbpedia.org/resource/Germany");
		StmtIterator it = resource.listProperties();
		int count = 0;
		while(it.hasNext()) {
			it.next();
			count++;
		}
		assertEquals(193, count);
	}
	
	@Test
	public void retrieveResourceFromStringRmRDFTypeTest() {
		Resource resource = SparqlService.queryResource("http://dbpedia.org/resource/Germany", "type");
		StmtIterator it = resource.listProperties();
		int count = 0;
		while(it.hasNext()) {
			it.next();
			count++;
		}
		assertEquals(170, count);
	}
	
	@Test
	public void queryResourceWithTypeHierachyTest() {
		Resource resource = SparqlService.
				queryResourceWithTypeHierachy("http://dbpedia.org/resource/Stanislaw_Tillich");
		StmtIterator it = resource.listProperties();
		while(it.hasNext()) {
			Statement stmt = it.next();
			Triple triple = stmt.asTriple();
			if(triple.getPredicate().getURI().equals(Settings.RDF_TYPE) && 
					triple.getObject().getURI().contains(Settings.DBO)) {
				Resource typeResource = stmt.getResource();
				StmtIterator typeIt = typeResource.listProperties();
				System.out.println("---------------------------------------");
				System.out.println(typeResource);
				int count = 0;
				while(typeIt.hasNext()) {
					System.out.println(typeIt.next().asTriple());
					count++;
				}
				assertNotEquals(0, count);
			}
		}
	}
}
