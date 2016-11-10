package de.unima.dws.semanta.crossword.generation;

import org.junit.Test;

import de.unima.dws.semanta.crossword.model.Crossword;
import de.unima.dws.semanta.crossword.model.HAWord;
import de.unima.dws.semanta.model.HAEntity;

public class CrosswordGeneratorTest {

	@Test
	public void testSimpleCrosswordGenerator() {
		CrosswordGenerator generator = new SimpleCrosswordGenerator();
		Crossword crossword = generator.generate(
				new HAWord(new HAEntity(null, "tested", null, null, null)),
				new HAWord(new HAEntity(null, "semanta", null, null, null)),
				new HAWord(new HAEntity(null, "angel", null, null, null)));
		System.out.println();
		crossword.normalize();
		System.out.println();
	}
}
