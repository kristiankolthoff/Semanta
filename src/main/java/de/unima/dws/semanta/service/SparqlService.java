package de.unima.dws.semanta.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;

import de.unima.dws.semanta.model.ResourceInfo;


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
	
	public static List<ResourceInfo> queryTopic(String topic, int limit) {
		final String query = "PREFIX dbo: <http://dbpedia.org/ontology/> " +
				"SELECT ?s " +
				"{" +
				"?s rdfs:label ?o . " +
				"FILTER regex(str(?o, " + topic + ")) . " +
				"}" +
				"LIMIT " + limit;
		List<ResourceInfo> resourceInfos = new ArrayList<>();
		ResultSet result = SparqlService.query(query);
		while(result.hasNext()) {
			QuerySolution qs = result.next();
			Resource resource = qs.getResource("s");
			resourceInfos.add(new ResourceInfo(resource, resource.getURI(), null));
		}
		return resourceInfos;
	}
	
	public static Resource queryResource(String uri, String...propertiesToRemove) {
		final String query = "SELECT ?p ?o " +
				"{" +
				"<" + uri + ">" + " ?p ?o . " +
				"}";
		ResultSet result = SparqlService.query(query);
		return SparqlService.buildResource(result, uri, "p", "o", propertiesToRemove);
	}
	
	public static Resource queryResource(String uri) {
		final String query = "SELECT ?p ?o " +
				"{" +
				"<" + uri + ">" + " ?p ?o . " +
				"}";
		ResultSet result = SparqlService.query(query);
		return SparqlService.buildResource(result, uri, "p", "o", "wiki");
	}
	
	private static Resource buildResource(ResultSet result, String uri, String p, 
			String o, String... propertiesToRemove) {
		Model model = ModelFactory.createDefaultModel();
		Resource resource = model.createResource(uri);
		while(result.hasNext()) {
			QuerySolution qs = result.next();
			Property property = ResourceFactory.createProperty(qs.getResource(p).getURI());
			boolean skipProperty = false;
			for (int i = 0; i < propertiesToRemove.length; i++) {
				if(property.getURI().contains(propertiesToRemove[i])) {
					skipProperty = true;
					break;
				}
			}
			if(!skipProperty) {
				try {
					Resource object = qs.getResource(o);
					resource.addProperty(property, object);
				} catch(ClassCastException ex) {
					Literal literal = qs.getLiteral(o);
					resource.addLiteral(property, literal);
				}
			}
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
