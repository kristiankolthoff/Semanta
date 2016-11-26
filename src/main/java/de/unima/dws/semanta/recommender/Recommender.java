package de.unima.dws.semanta.recommender;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import de.unima.dws.semanta.model.ResourceInfo;
import de.unima.dws.semanta.service.RESTLocationService;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class Recommender{

	private LocationRecommender locationRecommender;
	private HistoryRecommender historyRecommender;
	private RESTLocationService service;
	private int size;
	private List<ResourceInfo> recommendationsLocation;
	private List<ResourceInfo> recommendationsHistory;
	
	private static final int DEFAULT_SIZE = 12;
	
	public Recommender(LocationRecommender locationRecommender, HistoryRecommender historyRecommender, int size) {
		this.locationRecommender = locationRecommender;
		this.historyRecommender = historyRecommender;
		this.service = new RESTLocationService.Factory().build();
		this.size = size;
	}
	
	public Recommender() {
		this.locationRecommender = new IPBasedLocationRecommender();
		this.service = new RESTLocationService.Factory().build();
		this.size = DEFAULT_SIZE;
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

	public void getRecommendations(int numOfRecommentations, Consumer<List<ResourceInfo>> callback) {
		this.recommendationsHistory = new ArrayList<>();
		@SuppressWarnings("unused")
		Subscription subscription = service.getLocation()
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<Location>() {
                    @Override
                    public void onCompleted() {
                    	System.out.println("completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                    	System.out.println(e);
                    }

                    @Override
                    public void onNext(Location location) {
                    	Recommender.this.recommendationsLocation = locationRecommender.
                    			recommend(location, Recommender.this.size);
                    	System.out.println(location);
                    	verifyStatus(callback);
                    }
                });
	}
	
	private void verifyStatus(Consumer<List<ResourceInfo>> callback) {
		if(recommendationsHistory != null && recommendationsLocation != null) {
			List<ResourceInfo> recommendations = new ArrayList<>();
			recommendations.addAll(recommendationsHistory);
			recommendations.addAll(recommendationsLocation);
			System.out.println("callback");
			callback.accept(recommendationsLocation);
		}
	}
	
	
	
}
