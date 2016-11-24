package de.unima.dws.semanta.crossword.generation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import de.unima.dws.semanta.crossword.model.Cell;
import de.unima.dws.semanta.crossword.model.Crossword;
import de.unima.dws.semanta.crossword.model.HAWord;
import de.unima.dws.semanta.crossword.model.Orientation;

public class SimpleCrosswordGenerator implements CrosswordGenerator{

	private Random random;
	
	public SimpleCrosswordGenerator() {
		this.random = new Random();
	}
	
	@Override
	public Crossword generate(List<HAWord> words) {
		Crossword crossword = new Crossword();
		Collections.sort(words, (first, second) -> {
			return second.getWord().length() - first.getWord().length();
		});
//		Collections.shuffle(words);
		for (int i = 0; i < words.size(); i++) {
			HAWord word = words.get(i);
			List<HAWord> possibleWords = getPositions(crossword, word);
			crossword.addWordWithCells(possibleWords.get(random.nextInt(possibleWords.size())));
		}
		return crossword;
	}
	
	private List<HAWord> getPositions(final Crossword crossword, final HAWord word) {
		List<HAWord> words = new ArrayList<>();
		if(crossword.size() == 0) {
			words.add(contructFirstWord(word));
		}
		String label = word.getWord();
		for(HAWord wordCurr : crossword) {
			for(Cell cell : wordCurr) {
				//Check for letter matches with target label
				for (int i = 0; i < label.length(); i++) {
					if(cell.getLabel().equals(String.valueOf(label.charAt(i)))) {
						HAWord possibleWord = getWord(word, cell, i, wordCurr.getOrientation());
						if(crossword.validateWord(possibleWord)) {
							words.add(possibleWord);
						}
					}
				}
			}
		}
		return words;
	}

	private HAWord getWord(HAWord originalWord, Cell matchCell, int matchPos, Orientation orientation) {
		HAWord word = new HAWord(originalWord.getHAEntity());
		if(orientation == Orientation.HORIZONTAL) {
			int count = 0;
			word.addCell(new Cell(matchCell.getX(), matchCell.getY()-matchPos-1, String.valueOf("LA"), ""));
			for (int i = matchCell.getY()-matchPos; i < 
					originalWord.getWord().length()-matchPos; i++) {
				if(matchCell.getY() == i) {
					word.addCell(matchCell);
				} else {
					word.addCell(new Cell(matchCell.getX(), i, 
							String.valueOf(originalWord.getWord().charAt(count)), ""));
				}
				count++;
			}
			word.setOrientation(Orientation.VERTICAL);
		} else if(orientation == Orientation.VERTICAL) {
			int count = 0;
			word.addCell(new Cell(matchCell.getX()-matchPos-1, matchCell.getY(), String.valueOf("LA"), ""));
			for (int i = matchCell.getX()-matchPos; i < 
					originalWord.getWord().length()+matchCell.getX()-matchPos ; i++) {
				if(matchCell.getX() == i) {
					word.addCell(matchCell);
				} else {
					word.addCell(new Cell(i, matchCell.getY(), 
							String.valueOf(originalWord.getWord().charAt(count)), ""));
				}
				count++;
			}
			word.setOrientation(Orientation.HORIZONTAL);
		}
		return word;
	}
	
	private HAWord contructFirstWord(HAWord originalWord) {
		HAWord word = new HAWord(originalWord.getHAEntity());
		word.addCell(new Cell(-1, 0, String.valueOf("LA"), ""));
		for (int i = 0; i < originalWord.getWord().length(); i++) {
			word.addCell(new Cell(i, 0, String.valueOf(originalWord.getWord().charAt(i)), ""));
		}
		word.setOrientation(Orientation.HORIZONTAL);
		return word;
	}
}
