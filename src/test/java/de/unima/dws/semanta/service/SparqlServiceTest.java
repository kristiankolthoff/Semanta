package de.unima.dws.semanta.service;

import static org.junit.Assert.*;

import org.apache.jena.graph.Triple;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.junit.Before;
import org.junit.Test;

public class SparqlServiceTest {

	
	@Before
	public void init() {
		final String DBPEDIA_ENDPOINT = "http://dbpedia.org/sparql";
		SparqlService.setEndpoint(DBPEDIA_ENDPOINT);
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
		assertEquals(202, count);
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
		assertEquals(168, count);
	}
}
