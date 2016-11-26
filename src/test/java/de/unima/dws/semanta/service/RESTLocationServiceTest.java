package de.unima.dws.semanta.service;

import org.junit.Test;

import de.unima.dws.semanta.recommender.Location;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class RESTLocationServiceTest {

	@Test
	public void restServiceTest() {
		RESTLocationService service = new RESTLocationService.Factory().build();
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
	                      System.out.println(location);
	                    }
	                });
		 System.out.println();
	}
}
