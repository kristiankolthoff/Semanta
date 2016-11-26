package de.unima.dws.semanta.recommender;

import java.util.List;

import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.service.SparqlService;

public class IPBasedLocationRecommender implements LocationRecommender{

	
	@Override 
	public List<ResourceInfo> recommend(Location location, int numOfRecommendations) {
		System.out.println("Recommend location");
		List<ResourceInfo> recommendationCountry = SparqlService.
				getTopics(location.getCountry(), numOfRecommendations / 2);
		List<ResourceInfo> recommendationCity = SparqlService.
				getTopics(location.getCity(), numOfRecommendations / 2);
		recommendationCity.addAll(recommendationCountry);
		return recommendationCity;
	}

}
