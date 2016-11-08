package de.unima.dws.semanta.crossword.generation;

import java.util.List;

import de.unima.dws.semanta.crossword.model.Crossword;

@FunctionalInterface
public interface CrosswordGenerator {

	public Crossword generate(List<String> words);
	
	public default Crossword generate(String... words) {
		return null;
		
	}
}
