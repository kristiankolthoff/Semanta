package de.unima.dws.semanta.crossword.model;


public interface Word extends Iterable<Cell>{

	public Word addCell(Cell cell);
	
	public int size();
	
	public boolean isValid();
	
	public String getCurrentValue();
	
	public String getWord();
	
	public Orientation getOrientation();
	
	public void setOrientation(Orientation orientation);
}
