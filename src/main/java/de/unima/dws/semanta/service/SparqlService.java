package de.unima.dws.semanta.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.jena.graph.Triple;
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
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.utilities.Settings;


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

	// Get topics related to a search word
		public static List<ResourceInfo> getTopics(String topic, int limit) {
			
			topic = topic.replace(" ", " AND ");

			final StringBuilder query = new StringBuilder();
			query.append("PREFIX dbo:<http://dbpedia.org/ontology/> \n");
			query.append("PREFIX vrank:<http://purl.org/voc/vrank#> \n");
			query.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n");
			query.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n");
			query.append("PREFIX skos: <http://www.w3.org/2004/02/skos/core#>");
			query.append("SELECT distinct ?s ?o ?abstract ?img \n");
			query.append("FROM <http://dbpedia.org> \n");
			query.append("FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank> \n");
			query.append("WHERE { \n");
			query.append("?s rdfs:label ?o. \n");
			query.append("?s dbo:abstract ?abstract. \n");
			query.append("OPTIONAL {?s dbo:thumbnail ?img} \n");	
			query.append("?s vrank:hasRank/vrank:rankValue ?v. \n");
			query.append("FILTER (lang(?o) = 'en'). \n");
			query.append("FILTER (lang(?abstract) = 'en'). \n");
			query.append("FILTER (!EXISTS {?s rdf:type skos:Concept}) \n");
			query.append("?o <bif:contains> \"");
			query.append(topic);
			query.append("\".} ORDER BY DESC(?v) LIMIT ");
			query.append(limit);

			System.out.println(query.toString());

			List<ResourceInfo> resourceInfos = new ArrayList<>();
			ResultSet result = SparqlService.query(query.toString());
			while(result.hasNext()) {
				QuerySolution qs = result.next();
				Resource resource = qs.getResource("s");
				Literal label =  qs.getLiteral("o");
				
				Literal abst = qs.getLiteral("abstract");
				Resource img = qs.getResource("img");
				String imgURL = (img != null) ? img.getURI().substring(0, 4) + "s" + img.getURI().substring(4) : null;
				resourceInfos.add(new ResourceInfo(resource, resource.getURI(), 
						label.toString(), abst.toString(), imgURL, resourceInfos.size()+1));
			}
			return resourceInfos;
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
			resourceInfos.add(new ResourceInfo(resource, resource.getURI(), null, null));
		}
		return resourceInfos;
	}
	
	public static ResultSet queryNodeSumEntities(String uri, int threshold, 
			List<String> filterSubjects, List<String> filterPredicates) {
		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>");
		sb.append("PREFIX dbo: <http://dbpedia.org/ontology/> " +
		"SELECT ?s ?p (count(?p1) as ?c) " +
		"WHERE {" +
		"?s ?p <" + uri + "> . " +
		"?s ?p1 ?out . ");
		sb.append("?s rdfs:label ?label . ");
		for(String filterS : filterSubjects) {
			sb.append("FILTER(!REGEX(STR(?s), \"" + filterS + "\")) . ");
		}
		//Note that this is only necessecary if hints are generated directly
		//by the linking property to the topic entity. Otherwise, we might
		//lose interesing entites
		for(String filterP : filterPredicates) {
			sb.append("FILTER(!REGEX(STR(?p), \"" + filterP + "\")) . ");
		}
		sb.append("} ");
		sb.append("group by ?s ?p " +
		"having (count(?out) > " + threshold + ") " +
		"order by desc(?c) ");
		System.out.println(sb.toString());
		return SparqlService.query(sb.toString());
	}
	
	public static ResultSet querySimilarResources(String uri, String type, int limit) {
		final String query = "PREFIX dbo: <http://dbpedia.org/ontology/> " +
							 "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> " +
					"SELECT ?s " +
					"WHERE { " +
					"{ " +
					"?s rdf:type <" + type + "> . " +
					"?s ?p <" + uri + "> . " +
					"} UNION " +
					"{ " +
					"?s rdf:type <" + type + "> . " +
					"<" + uri + "> ?h ?s . " + 
					"} " +
					"} " +
					"LIMIT " + limit;
		return SparqlService.query(query);
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
		return SparqlService.buildResource(result, uri, "p", "o", "wiki", "thumbnail");
	}
	
	public static Resource queryResourceWithTypeHierachy(String uri) {
		return SparqlService.queryResourceWithTypeHierachy(uri, Settings.RDF_TYPE, Settings.DBO);
	}
	
	
	public static Resource queryResourceWithTypeHierachy(String uri, 
			String propertyType, String ontTypeRegex) {
		Resource resource = SparqlService.queryResource(uri);
		Model model = resource.getModel();
		StmtIterator it = resource.listProperties();
		while(it.hasNext()) {
			Statement stmt = it.next();
			Triple triple = stmt.asTriple();
			if(triple.getPredicate().getURI().equals(propertyType) &&
					triple.getObject().getURI().contains(ontTypeRegex)) {
				Resource typeResource = SparqlService.queryResource(triple.getObject().getURI());
				model.add(typeResource.getModel());
			}
		}
		return resource;
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
