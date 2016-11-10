package de.unima.dws.semanta.crossword.model;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Before;
import org.junit.Test;

import de.unima.dws.semanta.model.HAEntity;

public class CrosswordTest {

	private Crossword crossword;
	
	@Before
	public void init() {
		this.crossword = new Crossword();
	}
	
	@Test
	public void addHorizontalWordFailTest() {
		this.crossword.addWord(new HAWord(
				new HAEntity(null, "germany", null, null, null)), 0, 0, 3, 0);
		assertEquals(0, this.crossword.size());
	}
	
	@Test
	public void addWordHorizontalSuccesTest() {
		this.crossword.addWord(new HAWord(
				new HAEntity(null, "germany", null, null, null)), 0, 0, 6, 0);
		assertEquals(1, this.crossword.size());
	}
	
	@Test
	public void addVerticalWordFailTest() {
		this.crossword.addWord(new HAWord(
				new HAEntity(null, "germany", null, null, null)), 0, 1, 0, 10);
		assertEquals(0, this.crossword.size());
	}
	
	@Test
	public void addWordVerticalSuccesTest() {
		this.crossword.addWord(new HAWord(
				new HAEntity(null, "germany", null, null, null)), 0, 2, 0, 8);
		assertEquals(1, this.crossword.size());
	}
	
	@Test
	public void addMultiWordCrossSuccesTest() {
		this.crossword.addWord(new HAWord(
				new HAEntity(null, "test", null, null, null)), 0, 1, 3, 1);
		this.crossword.addWord(new HAWord(
				new HAEntity(null, "semanta", null, null, null)), 1, 0, 1, 6);
		assertEquals(2, this.crossword.size());
	}
	
	@Test
	public void addMultiWordCrossFailTest() {
		this.crossword.addWord(new HAWord(
				new HAEntity(null, "test", null, null, null)), 0, 1, 3, 1);
		this.crossword.addWord(new HAWord(
				new HAEntity(null, "semanta", null, null, null)), 2, 0, 2, 6);
		assertEquals(1, this.crossword.size());
	}
	
	@Test
	public void orientationVerticalDownTest2() {
		this.crossword.addWord(new HAWord(
				new HAEntity(null, "germany", null, null, null)), 0, 0, 3, 0);
		assertEquals(0, this.crossword.size());
	}
	
	@Test
	public void cellTest() {
		this.crossword.addWord(new HAWord(
				new HAEntity(null, "test", null, null, null)), 0, 0, 3, 0);
		Iterator<HAWord> words = this.crossword.iterator();
		HAWord word = words.next();
		Iterator<Cell> it = word.iterator();
		Cell c1 = it.next();
		Cell c2 = it.next();
		Cell c3 = it.next();
		Cell c4 = it.next();
		assertEquals(c1.getLabel(), "t");
		assertEquals(c2.getLabel(), "e");
		assertEquals(c3.getLabel(), "s");
		assertEquals(c4.getLabel(), "t");
	}
	
	@Test
	public void sizeWidthTest() {
		this.crossword.addWord(new HAWord(
				new HAEntity(null, "test", null, null, null)), 0, 1, 3, 1);
		this.crossword.addWord(new HAWord(
				new HAEntity(null, "semanta", null, null, null)), 1, 0, 1, 6);
		assertEquals(4, this.crossword.getWidth());
	}
	
	@Test
	public void sizeHeightTest() {
		this.crossword.addWord(new HAWord(
				new HAEntity(null, "test", null, null, null)), 0, 1, 3, 1);
		this.crossword.addWord(new HAWord(
				new HAEntity(null, "semanta", null, null, null)), 1, 0, 1, 6);
		assertEquals(7, this.crossword.getHeight());
	}
}
