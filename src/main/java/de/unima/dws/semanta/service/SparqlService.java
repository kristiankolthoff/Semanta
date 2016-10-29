package de.unima.dws.semanta.service;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;


public class SparqlService {

	private static String endpoint;
	private static Query query;
	private static QueryExecution qExec;
	
	private SparqlService() {}
	
	public static ResultSet query(String query) {
		qExec = QueryExecutionFactory.sparqlService(endpoint, QueryFactory.create(query));
		return qExec.execSelect();
	}
	
	public static Model queryDescribe(String uri) {
		final String query = "DESCRIBE <" + uri + ">";
		qExec = QueryExecutionFactory.sparqlService(endpoint, QueryFactory.create(query));
		return qExec.execDescribe();
	}
	
	public static Resource queryResource(String uri) {
		Resource resource = ResourceFactory.createResource(uri);
		ResultSet result = SparqlService.query(uri);
		while(result.hasNext()) {
			QuerySolution qs = result.next();
			Resource property = qs.getResource("p");
			Resource object = qs.getResource("o");
			resource.addProperty((Property) property, object);
		}
		return resource;
	}
	
	public static Resource queryResourceSanitized(String uri) {
		Resource resource = ResourceFactory.createResource(uri);
		ResultSet result = SparqlService.query(uri);
		while(result.hasNext()) {
			QuerySolution qs = result.next();
			Resource property = qs.getResource("p");
			Resource object = qs.getResource("o");
			resource.addProperty((Property) property, object);
		}
		return resource;
	}

	public String getEndpoint() {
		return endpoint;
	}
	
	public static void setEndpoint(String endpoint) {
		SparqlService.endpoint = endpoint;
	}

	public static QueryExecution getqExec() {
		return qExec;
	}

	public static void setqExec(QueryExecution qExec) {
		SparqlService.qExec = qExec;
	}

	public static Query getQuery() {
		return query;
	}

	public static void setQuery(Query query) {
		SparqlService.query = query;
	}
}
