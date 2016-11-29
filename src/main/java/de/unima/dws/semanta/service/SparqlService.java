package de.unima.dws.semanta.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.Syntax;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.utilities.Settings;


public class SparqlService {

	private static String endpoint;
	private static Query query;
	private static QueryExecution qExec;
	
	private SparqlService() {}
	
	@SuppressWarnings("resource")
	public static ResultSet queryExtendedSyntax(String query) {
		QueryEngineHTTP engine = new QueryEngineHTTP(endpoint, query);
		return engine.execSelect();
	}
	
	public static ResultSet query(String query) {
		qExec = QueryExecutionFactory.sparqlService(endpoint, QueryFactory.create(query, Syntax.syntaxARQ));
		return qExec.execSelect();
	}
	
	public static Model queryDescribe(String uri) {
		final String query = "DESCRIBE <" + uri + ">";
		qExec = QueryExecutionFactory.sparqlService(endpoint, QueryFactory.create(query));
		return qExec.execDescribe();
	}
	
	// Get Properties
	public static Resource getPropertiesOfURI(String uri, int limit) {
		
		limit = 50;
		
		final StringBuilder query = new StringBuilder();
		query.append("PREFIX dbo:<http://dbpedia.org/ontology/> \n");
		query.append("PREFIX vrank:<http://purl.org/voc/vrank#> \n");
		query.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n");
		query.append("SELECT distinct ?p ?r ?o \n");
		query.append("FROM <http://dbpedia.org> \n");
		query.append("FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank> \n");
		query.append("WHERE { \n");
		query.append("<");
		query.append(uri);
		query.append("> ?p ?o . \n");
		query.append("?p rdf:type dbo:ObjectProperty . \n");
		query.append("?o vrank:hasRank/vrank:rankValue ?r. \n");	
		query.append("} ORDER BY DESC(?r) \n");
		query.append("limit ");
		query.append(limit);
		
		ResultSet result = SparqlService.query(query.toString());
		return SparqlService.buildResource(result, uri, "p", "o","");
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
	
	public static List<ResourceInfo> querySimilarTypeResources(String uri, String type, int length, int limit) {
		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX dbo: <http://dbpedia.org/ontology/> \n");
		sb.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n");
		sb.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n");
		sb.append("SELECT DISTINCT ?s ?label \n");
		sb.append("WHERE { \n");
		sb.append("?s rdf:type <" + type + "> . \n");
		sb.append("?s rdfs:label ?label . \n");
		sb.append("FILTER (lang(?label) = 'en'). \n");
		sb.append("FILTER(strlen(str(?label)) = " + length + ") \n");
		sb.append("} \n");
		sb.append("order by asc( bif:rnd(" + new Random().nextInt(Integer.MAX_VALUE) + ", ?s)) \n");
		sb.append("LIMIT " + limit);
		System.out.println(sb.toString());
		return SparqlService.buildTinyResourceInfo(SparqlService.queryExtendedSyntax(sb.toString()), "s", "label");
	}
	
	public static List<ResourceInfo> querySimilarTopicResources(String uri, String type, int length, int limit) {
		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX dbo: <http://dbpedia.org/ontology/> \n");
		sb.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n");
		sb.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n");
		sb.append("SELECT DISTINCT ?s ?label \n");
		sb.append("WHERE { \n");
		sb.append("{ \n");
		sb.append("?s rdf:type <" + type + "> . \n");
		sb.append("?s ?p <" + uri + "> . \n");
		sb.append("} UNION \n");
		sb.append("{ \n");
		sb.append("?s rdf:type <" + type + "> . \n");
		sb.append("<" + uri + "> ?h ?s . \n");
		sb.append("} \n");
		sb.append("?s rdfs:label ?label . \n");
		sb.append("FILTER (lang(?label) = 'en'). \n");
		sb.append("FILTER(strlen(str(?label)) = " + length + ") \n");
		sb.append("} \n");
		sb.append("order by asc( bif:rnd(" + new Random().nextInt(Integer.MAX_VALUE) + ", ?s)) \n");
		sb.append("LIMIT " + limit);
		System.out.println(sb.toString());
		return SparqlService.buildTinyResourceInfo(SparqlService.queryExtendedSyntax(sb.toString()), "s", "label");
	}
	
	public static List<ResourceInfo> querySimilarLinkingEntityResources(String topic, String entity,
			String type, int length, int limit) {
		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX dbo: <http://dbpedia.org/ontology/> \n");
		sb.append("PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n");
		sb.append("PREFIX owl: <http://www.w3.org/2002/07/owl#> \n");
		sb.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n");
		sb.append("SELECT DISTINCT ?s ?label \n");
		sb.append("WHERE { \n");
		sb.append("?s rdf:type <" + type + "> . \n");
		sb.append("?s ?p <" + topic + "> . \n");
		sb.append("<" + entity + "> ?h1 ?o .\n");
		sb.append("?s ?p2 ?o .\n");
		sb.append("?p2 rdf:type owl:ObjectProperty .\n");
		sb.append("?s rdfs:label ?label . \n");
		sb.append("FILTER (lang(?label) = 'en'). \n");
		sb.append("FILTER(strlen(str(?label)) = " + length + ") \n");
		sb.append("} \n");
		sb.append("order by asc( bif:rnd(" + new Random().nextInt(Integer.MAX_VALUE) + ", ?s)) \n");
		sb.append("LIMIT " + limit);
		System.out.println(sb.toString());
		return SparqlService.buildTinyResourceInfo(SparqlService.queryExtendedSyntax(sb.toString()), "s", "label");
	}
	
	public static List<ResourceInfo> buildTinyResourceInfo(ResultSet rs, String uri, String label) {
		List<ResourceInfo> infos = new ArrayList<>();
		while(rs.hasNext()) {
			QuerySolution qs = rs.next();
			Resource r = qs.getResource(uri);
			infos.add(new ResourceInfo(r, r.getURI(), qs.getLiteral(label).getString(), null, null, 0));
		}
		return infos;
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
		System.out.println(query);
		ResultSet result = SparqlService.query(query);
		return SparqlService.buildResource(result, uri, "p", "o", "wiki");
	}
	
	public static Resource queryResourceWithTypeHierachy(String uri) {
		return SparqlService.queryResourceWithTypeHierachy(uri, Settings.RDF_TYPE, Settings.DBO);
	}
	
	public static List<Double> queryOrderedPageRanks(String uri, int limit) {
		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n");
		sb.append("PREFIX vrank:<http://purl.org/voc/vrank#> \n");
		sb.append("PREFIX dbo:<http://dbpedia.org/ontology/> \n");
		sb.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n");
		sb.append("SELECT distinct ?v \n");
		sb.append("FROM <http://dbpedia.org> \n");
		sb.append("FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank> \n");
		sb.append("WHERE { \n");
		sb.append("?s ?p <" + uri + "> . \n");
		sb.append("?s vrank:hasRank/vrank:rankValue ?v. \n");
		sb.append("?s rdfs:label ?label \n");
		sb.append("FILTER(lang(?label) = \"en\") . \n");
		sb.append("FILTER(strlen(str(?label)) < 20) . \n");
		sb.append("} \n");
		sb.append("order by desc(?v) limit " + limit + " \n");
		System.out.println(sb.toString());
		ResultSet rs = SparqlService.query(sb.toString());
		List<Double> pageRanks = new ArrayList<>();
		while(rs.hasNext()) {
			pageRanks.add(rs.next().getLiteral("v").getDouble());
		}
		return pageRanks;
	}
	
	public static List<Double> queryOrderedNodeDegrees(String uri, int limit) {
		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n");
		sb.append("PREFIX vrank:<http://purl.org/voc/vrank#> \n");
		sb.append("PREFIX dbo:<http://dbpedia.org/ontology/> \n");
		sb.append("SELECT (count(?p1) as ?c) \n");
		sb.append("WHERE { \n");
		sb.append("?s ?p <" + uri + "> . \n");
		sb.append("?in ?p1 ?s . \n");
		sb.append("} \n");
		sb.append("group by ?s \n");
		sb.append("order by desc(?c) limit " + limit + " \n");
		System.out.println(sb.toString());
		ResultSet rs = SparqlService.query(sb.toString());
		List<Double> pageRanks = new ArrayList<>();
		while(rs.hasNext()) {
			pageRanks.add(rs.next().getLiteral("c").getDouble());
		}
		return pageRanks;
	}
	
	
	public static List<Resource> queryEntitesByPageRankThreshold(String uri, double tUpper, double tLower, 
			int limit, int randomSeed) {
		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n");
		sb.append("PREFIX vrank:<http://purl.org/voc/vrank#> \n");
		sb.append("PREFIX dbo:<http://dbpedia.org/ontology/> \n");
		sb.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n");
		sb.append("SELECT distinct ?s \n");
		sb.append("FROM <http://dbpedia.org> \n");
		sb.append("FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank> \n");
		sb.append("WHERE { \n");
		sb.append("?s ?p <" + uri + "> . \n");
		sb.append("?s vrank:hasRank/vrank:rankValue ?v. \n");
		sb.append("FILTER(?v > " + tLower + " && ?v < " + tUpper + ")");
		sb.append("?s rdfs:label ?label \n");
		sb.append("FILTER(lang(?label) = \"en\") . \n");
		sb.append("FILTER(strlen(str(?label)) < 20) . \n");
		sb.append("} \n");
		sb.append("ORDER BY RAND(" + randomSeed + ") LIMIT " + limit + " \n");
		System.out.println(sb.toString());
		ResultSet rs = SparqlService.queryExtendedSyntax(sb.toString());
		List<Resource> results = new ArrayList<>();
		while(rs.hasNext()) {
			results.add(SparqlService.queryResourceWithTypeHierachy(rs.next().getResource("s").getURI()));
		}
		return results;
	}
	
		
	public static List<Resource> queryEntitesByNodeDegreeThreshold(String uri, double tUpper, double tLower, 
			int limit, int randomSeed) {
		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n");
		sb.append("PREFIX vrank:<http://purl.org/voc/vrank#> \n");
		sb.append("PREFIX dbo:<http://dbpedia.org/ontology/> \n");
		sb.append("SELECT distinct ?s \n");
		sb.append("WHERE { \n");
		sb.append("?in ?p1 ?s . \n");
		sb.append("?s ?p <" + uri + "> . \n");
		sb.append("} \n");
		sb.append("GROUP BY ?s \n");
		sb.append("HAVING (COUNT(?p1) > " + tLower + " && COUNT(?p1) < " + tUpper + ") \n");
		sb.append("ORDER BY RAND(" + randomSeed + ") LIMIT " + limit + " \n");
		System.out.println(sb.toString());
		ResultSet rs = SparqlService.queryExtendedSyntax(sb.toString());
		List<Resource> results = new ArrayList<>();
		while(rs.hasNext()) {
			results.add(SparqlService.queryResourceWithTypeHierachy(rs.next().getResource("s").getURI()));
		}
		return results;
	}
	
	public static ResultSet queryResourcePropertyRanks(String uri) {
		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n");
		sb.append("PREFIX dbo:<http://dbpedia.org/ontology/> \n");
		sb.append("PREFIX vrank:<http://purl.org/voc/vrank#> \n");
		sb.append("PREFIX owl: <http://www.w3.org/2002/07/owl#> \n");
		sb.append("SELECT DISTINCT ?labelP ?labelO \n");
		sb.append("FROM <http://dbpedia.org> \n");
		sb.append("FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank>  \n");
		sb.append("WHERE { \n");
		sb.append("<" + uri + "> ?p ?o . \n");
		sb.append("?p rdf:type owl:ObjectProperty . \n");
		sb.append("?o vrank:hasRank/vrank:rankValue ?r. \n");
		sb.append("?p rdfs:label ?labelP . \n");
		sb.append("?o rdfs:label ?labelO . \n");
		sb.append("FILTER (lang(?labelP) = 'en') . \n");
		sb.append("FILTER (lang(?labelO) = 'en') . \n");
		sb.append("} \n");
		sb.append("ORDER BY DESC(?r) \n");
		sb.append("LIMIT 50");
		System.out.println(sb.toString());
		return SparqlService.queryExtendedSyntax(sb.toString());
	}
	
	public static List<Resource> queryResourcesByPropertyRanks(String uri) {
		StringBuilder sb = new StringBuilder();
		sb.append("PREFIX rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n");
		sb.append("PREFIX dbo:<http://dbpedia.org/ontology/> \n");
		sb.append("PREFIX vrank:<http://purl.org/voc/vrank#> \n");
		sb.append("PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n");
		sb.append("PREFIX owl: <http://www.w3.org/2002/07/owl#> \n");
		sb.append("SELECT DISTINCT ?o \n");
		sb.append("FROM <http://dbpedia.org> \n");
		sb.append("FROM <http://people.aifb.kit.edu/ath/#DBpedia_PageRank>  \n");
		sb.append("WHERE { \n");
		sb.append("<" + uri + "> ?p ?o . \n");
		sb.append("?p rdf:type owl:ObjectProperty . \n");
		sb.append("?o vrank:hasRank/vrank:rankValue ?r. \n");
		sb.append("?o rdfs:label ?labelO . \n");
		sb.append("FILTER (lang(?labelO) = 'en') . \n");
		sb.append("FILTER(strlen(str(?labelO)) < 20) . \n");
		sb.append("} \n");
		sb.append("ORDER BY DESC(?r) \n");
		sb.append("LIMIT 50");
		System.out.println(sb.toString());
		ResultSet rs = SparqlService.queryExtendedSyntax(sb.toString());
		List<Resource> results = new ArrayList<>();
		while(rs.hasNext()) {
			results.add(SparqlService.queryResourceWithTypeHierachy(rs.next().getResource("o").getURI()));
		}
		return results;
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
