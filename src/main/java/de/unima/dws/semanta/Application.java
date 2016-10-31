package de.unima.dws.semanta;

import java.util.List;

import de.unima.dws.semanta.model.HAEntity;
import de.unima.dws.semanta.service.SparqlService;

public class Application {

	public static void main(String[] args) {
		final String DBPEDIA_ENDPOINT = "http://dbpedia.org/sparql";
		SparqlService.setEndpoint(DBPEDIA_ENDPOINT);
		Semanta semanta = new Semanta();
		List<HAEntity> haEntities = semanta.fetchEntities("germany", 5, true);
		for(HAEntity entity : haEntities) {
			System.out.println(entity);
		}
	}
}
