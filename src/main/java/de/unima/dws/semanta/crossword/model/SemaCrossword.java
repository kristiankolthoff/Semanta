package de.unima.dws.semanta.crossword.model;

import java.nio.file.Path;
import java.util.Iterator;
import java.util.List;

public class SemaCrossword implements Crossword{

	private static final long serialVersionUID = 1L;
	private List<Word> words;

	@Override
	public Iterator<Word> iterator() {
		return words.iterator();
	}

	@Override
	public Crossword addWord(Word word, int startX, int startY, int endX,
			int endY) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cell getCell(int x, int y) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Word> getWords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Word> getWords(Orientation orientation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(Path path) {
		// TODO Auto-generated method stub
		
	}


}
