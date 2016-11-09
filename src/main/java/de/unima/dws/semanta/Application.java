package de.unima.dws.semanta;

import java.util.List;

import de.unima.dws.semanta.model.HAEntity;
import de.unima.dws.semanta.service.SparqlService;
import de.unima.dws.semanta.utilities.Settings;

public class Application {

	public static void main(String[] args) {
		SparqlService.setEndpoint(Settings.DEFAULT_ENDPOINT_DBPEDIA);
		Semanta semanta = new Semanta();
		List<HAEntity> haEntities = semanta.fetchEntities("germany", 5, true);
		for(HAEntity entity : haEntities) {
			System.out.println(entity);
		}
		
		/* List<ResourceInfo> i = SparqlService.getTopics("Obama", 10);
		
		for (int j = 0; j < 10; j++) {
			System.out.println(i.get(j).getResource().getProperty(VCARD.LABEL));
		} */
	}
}
