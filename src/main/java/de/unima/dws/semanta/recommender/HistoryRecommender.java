package de.unima.dws.semanta.recommender;

import java.util.Arrays;
import java.util.List;


@FunctionalInterface
public interface HistoryRecommender {

	public List<Recommendation> recommend(List<HistoryEntry> entries);
	
	public default List<Recommendation> recommend(HistoryEntry... entries) {
		return recommend(Arrays.asList(entries));
	}
}
