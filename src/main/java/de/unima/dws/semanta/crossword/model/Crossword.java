package de.unima.dws.semanta.crossword.model;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.List;

public interface Crossword extends Iterable<Word>, Serializable{
	
	public Crossword addWord(Word word, int startX, int startY, int endX, int endY);
	
	public Cell getCell(int x, int y);
	
	public List<Word> getWords();
	
	public List<Word> getWords(Orientation orientation);
	
	public boolean isEmpty(int x, int y);
	
	public int size();
	
	public int getWidth();
	
	public int getHeight();
	
	public boolean validate();
	
	public void render();
	
	public void write(Path path);
}
