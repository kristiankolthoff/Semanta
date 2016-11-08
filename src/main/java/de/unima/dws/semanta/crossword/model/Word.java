package de.unima.dws.semanta.crossword.model;


public interface Word extends Iterable<Cell>{

	public Word addCell(Cell cell);
	
	public boolean isValid();
	
	public String getCurrentValue();
	
	public String getWord();
}
