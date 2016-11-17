package de.unima.dws.semanta.recommender;

import java.util.List;

import de.unima.dws.semanta.model.ResourceInfo;

@FunctionalInterface
public interface LocationRecommender {

	public List<ResourceInfo> recommend(Location location);
}
