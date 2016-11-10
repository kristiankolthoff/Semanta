package de.unima.dws.semanta.crossword.generation;

import java.util.List;

import org.apache.jena.ext.com.google.common.collect.MinMaxPriorityQueue;

import de.unima.dws.semanta.crossword.model.Crossword;
import de.unima.dws.semanta.crossword.model.HAWord;

public class RandomizedGreedyCrosswordGenerator implements CrosswordGenerator{

	private CrosswordGenerator internal;
	private static final int SIZE_CONST = 10;
	private MinMaxPriorityQueue<Crossword> queue;
	
	public RandomizedGreedyCrosswordGenerator(CrosswordGenerator internal) {
		this.internal = internal;
		this.queue = MinMaxPriorityQueue.
				orderedBy((first, second) -> 
				{return ((Crossword)first).get().compareTo(((Crossword)second).get());}).
				maximumSize(200).
				create();
	}

	@Override
	public Crossword generate(List<HAWord> words) {
		final int multiplier = words.size() * SIZE_CONST;
		for (int i = 0; i < multiplier; i++) {
			queue.add(internal.generate(words));
		}
		Crossword bestCrossword = queue.pollLast();
		queue.clear();
		return bestCrossword;
	}

}
