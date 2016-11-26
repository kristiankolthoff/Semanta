package de.unima.dws.semanta.service;


import de.unima.dws.semanta.recommender.Location;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

public interface RESTLocationService {
	
	@GET("json")
	Observable<Location> getLocation();

	public class Factory {
		
		public RESTLocationService build() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://ip-api.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(RESTLocationService.class);
        }
	}
}
