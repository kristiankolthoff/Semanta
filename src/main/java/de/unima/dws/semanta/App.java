package de.unima.dws.semanta;


import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import com.sun.xml.internal.bind.v2.runtime.property.PropertyFactory;



/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World! greetings from Mars" );
        Query q = QueryFactory.create("PREFIX dbo: <http://dbpedia.org/ontology/> SELECT ?s ?p" +
        		" WHERE {" +
        			" ?s ?p <http://dbpedia.org/resource/Germany> ." +
        			" ?s ?p1 ?out ."+
        			" }"+
        			"group by ?s ?p"
        		);
        String query = "DESCRIBE <http://dbpedia.org/resource/Ansungtangmyun>";
		QueryExecution qExcec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", QueryFactory.create(query));
		Model m = qExcec.execDescribe();
		StmtIterator it = m.listStatements();
		while(it.hasNext()) {
			Statement st = it.next();
			Triple t = st.asTriple();
			System.out.println(t.getPredicate() + " " + t.getObject());
		}
		//		ResultSet rs = qExcec.execSelect();
		
//		while(rs.hasNext()) {
//			QuerySolution qs = rs.next();
//			RDFNode node = qs.get("s");
//			Resource r = node.asResource();
//			System.out.println("----------");
//			StmtIterator it = r.listProperties();
//			Resource res = ResourceFactory.createResource("http://dbpedia.org/resource/Germany");
//			while(it.hasNext()) {
//				Statement stmt = it.next();
//				System.out.println(stmt.toString());
//			}
//			System.out.println();
//			System.out.println(r.toString());
//		}
    }
}
