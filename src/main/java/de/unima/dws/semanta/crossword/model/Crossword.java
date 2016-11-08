package de.unima.dws.semanta.crossword.model;

import java.io.Serializable;
import java.nio.file.Path;

public interface Crossword extends Iterable<Word>, Serializable{
	
	public Crossword addWord(Word word, int startX, int startY, int endX, int endY);
	
	public Cell getCell(int x, int y);
	
	public boolean isEmpty(int x, int y);
	
	public int size();
	
	public int getWidth();
	
	public int getHeight();
	
	public boolean validate();
	
	public void render();
	
	public void write(Path path);
}
