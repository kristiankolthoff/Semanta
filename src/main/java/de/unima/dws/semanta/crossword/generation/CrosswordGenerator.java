package de.unima.dws.semanta.crossword.generation;

import java.util.Arrays;
import java.util.List;

import de.unima.dws.semanta.crossword.model.Crossword;
import de.unima.dws.semanta.crossword.model.HAWord;

@FunctionalInterface
public interface CrosswordGenerator {

	public Crossword generate(List<HAWord> words);
	
	public default Crossword generate(HAWord... words) {
		return generate(Arrays.asList(words));
	}
}
