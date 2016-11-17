package de.unima.dws.semanta.recommender;

import java.util.List;

@FunctionalInterface
public interface LocationRecommender {

	public List<Recommendation> recommend(Location location);
}
