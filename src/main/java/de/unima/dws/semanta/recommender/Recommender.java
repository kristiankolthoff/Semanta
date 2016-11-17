package de.unima.dws.semanta.recommender;

public class Recommender {

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
	
	
	
	
}
