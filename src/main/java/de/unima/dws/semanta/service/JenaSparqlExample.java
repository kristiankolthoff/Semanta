package de.unima.dws.semanta.service;


import java.lang.*;

import org.apache.jena.graph.Triple;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.QuerySolutionMap;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.rdf.model.impl.ResourceImpl;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;


public class JenaSparqlExample {


	public void init() {
		final String DBPEDIA_ENDPOINT = "http://dbpedia.org/sparql";
		SparqlService.setEndpoint(DBPEDIA_ENDPOINT);
	}
	

	public void dbpediaEndpointTest() {
		  String sparqlQuery = "" +
				  "PREFIX dbpedia-owl:<http://dbpedia.org/ontology/> \n" +
				  "select distinct ?label, ?abstract where { \n" +
				  "?s rdfs:label ?label. \n" +
				  "?s dbpedia-owl:abstract ?abstract. \n" +
				  "FILTER (lang(?label) = 'en'). \n" +
				  "FILTER (lang(?abstract) = 'en'). \n" +
				  "?label bif:contains \"Bashar\" . }";
		  
		  System.out.println("");
		



	}
	

}
