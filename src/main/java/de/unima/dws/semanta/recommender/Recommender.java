package de.unima.dws.semanta.recommender;

import java.util.List;

import de.unima.dws.semanta.model.ResourceInfo;

public class Recommender implements LocationRecommender, HistoryRecommender{

	private LocationRecommender locationRecommender;
	private HistoryRecommender historyRecommender;
	
	public Recommender(LocationRecommender locationRecommender, HistoryRecommender historyRecommender) {
		this.locationRecommender = locationRecommender;
		this.historyRecommender = historyRecommender;
	}
	
	public Recommender() {
		this.locationRecommender = null;
		this.historyRecommender = null;
	}
	
	public LocationRecommender getLocationRecommender() {
		return locationRecommender;
	}
	
	public void setLocationRecommender(LocationRecommender locationRecommender) {
		this.locationRecommender = locationRecommender;
	}
	
	public HistoryRecommender getHistoryRecommender() {
		return historyRecommender;
	}
	
	public void setHistoryRecommender(HistoryRecommender historyRecommender) {
		this.historyRecommender = historyRecommender;
	}

	@Override
	public List<ResourceInfo> recommend(List<HistoryEntry> entries) {
		return null;
	}

	@Override
	public List<ResourceInfo> recommend(Location location) {
		return null;
	}
	
	
	
	
}
