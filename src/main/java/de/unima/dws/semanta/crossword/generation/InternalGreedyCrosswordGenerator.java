package de.unima.dws.semanta.crossword.generation;

import java.util.Comparator;
import java.util.List;

import org.apache.jena.ext.com.google.common.collect.MinMaxPriorityQueue;

import de.unima.dws.semanta.crossword.model.Crossword;
import de.unima.dws.semanta.crossword.model.HAWord;

public class InternalGreedyCrosswordGenerator implements CrosswordGenerator{

	private int sizeMultiplier;
	private CrosswordGenerator internal;
	private MinMaxPriorityQueue<Crossword> queue;
	
	public InternalGreedyCrosswordGenerator(CrosswordGenerator internal, 
			int sizeMultiplier, Comparator<Crossword> customComparator) {
		this.sizeMultiplier = sizeMultiplier;
		this.internal = internal;
		this.initQueue(customComparator);
	}
	
	private void initQueue(Comparator<Crossword> comparator) {
		comparator = (comparator == null) ? (first, second) -> 
		{return ((Crossword)second).get().
				compareTo(((Crossword)first).get());} : comparator;
		this.queue = MinMaxPriorityQueue.
				orderedBy(comparator).
				maximumSize(200).
				create();
	}

	@Override
	public Crossword generate(List<HAWord> words) {
		final int multiplier = sizeMultiplier * sizeMultiplier;
		for (int i = 0; i < multiplier; i++) {
			try {
			queue.add(internal.generate(words));
			System.out.println("GENERATED " + i);
			} catch(Exception ex) {
				System.out.println("problem " + i);
			}
		}
		Crossword bestCrossword = queue.pollLast();
		queue.clear();
		return bestCrossword;
	}

}
