package de.unima.dws.semanta.recommender;

import java.util.Arrays;
import java.util.List;

import de.unima.dws.semanta.model.ResourceInfo;


@FunctionalInterface
public interface HistoryRecommender {

	public List<ResourceInfo> recommend(List<HistoryEntry> entries);
	
	public default List<ResourceInfo> recommend(HistoryEntry... entries) {
		return recommend(Arrays.asList(entries));
	}
}
