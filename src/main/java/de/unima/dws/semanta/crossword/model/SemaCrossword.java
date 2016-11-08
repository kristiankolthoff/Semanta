package de.unima.dws.semanta.crossword.model;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SemaCrossword implements Crossword{

	private static final long serialVersionUID = 1L;
	private List<Word> words;

	
	public SemaCrossword() {
		this.words = new ArrayList<>();
	}
	
	@Override
	public Iterator<Word> iterator() {
		return words.iterator();
	}

	@Override
	public Crossword addWord(Word word, int startX, int startY, int endX,
			int endY) {
		Orientation orientation = computeOrientation(startX, startY, endX, endY);
		if(orientation == null) {
			return this;
		}
		int length = computeLength(orientation, startX, startY, endX, endY);
		if(length != word.getWord().length()) {
			return this;
		}
		word.setOrientation(orientation);
		List<Cell> cells = new ArrayList<>();
		if(orientation == Orientation.VERTICAL_DOWN) {
			int current = 0;
			for (int i = startY; i <= endY ; i++) {
				cells.add(new Cell(startX, i, 
						String.valueOf(word.getWord().charAt(current)), ""));
				current++;
			}
		} else if(orientation == Orientation.VERTICAL_UP) {
			int current = 0;
			for (int i = startY; i >= endY ; i--) {
				cells.add(new Cell(startX, i, 
						String.valueOf(word.getWord().charAt(current)), ""));
				current++;
			}
		} else if(orientation == Orientation.HORIZONTAL_LEFT) {
			int current = 0;
			for (int i = startX; i >= endX ; i--) {
				cells.add(new Cell(i, startY, 
						String.valueOf(word.getWord().charAt(current)), ""));
				current++;
			}
		} else if(orientation == Orientation.HORIZONTAL_RIGHT) {
			int current = 0;
			for (int i = startX; i <= endX ; i++) {
				cells.add(new Cell(i, startY, 
						String.valueOf(word.getWord().charAt(current)), ""));
				current++;
			}
		}
		if(validateCells(cells)) {
			for(Cell cell : cells) {
				word.addCell(cell);
			}
			this.words.add(word);
		}
		return this;
	}
	
	private boolean validateCells(List<Cell> cells) {
		for(Cell cellToVal : cells) {
			for(Word word : words) {
				for(Cell cellCurr : word) {
					if(cellToVal.samePosition(cellCurr) && 
							!cellToVal.sameContent(cellCurr)) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	
	private int computeLength(Orientation orientation, int startX, int startY, int endX, int endY) {
		int length = 0;
		if(orientation == Orientation.VERTICAL_DOWN) {
			length = endY + 1 - startY;
		} else if(orientation == Orientation.VERTICAL_UP) {
			length = startY + 1 - endY;
		} else if(orientation == Orientation.HORIZONTAL_LEFT) {
			length = startX + 1 - endY;
		} else if(orientation == Orientation.HORIZONTAL_RIGHT) {
			length = endX + 1 - startX;
		}
		return length;
	}
	
	private Orientation computeOrientation(int startX, int startY, int endX,
			int endY) {
		Orientation orientation = null;
		if(startX == endX && startY != endY) {
			//Vertical word
			orientation = (startY > endY) ? Orientation.VERTICAL_UP 
					: Orientation.VERTICAL_DOWN;
		} else if(startY == endY && startX != endX) {
			//Horizontal word
			orientation = (startX > endX) ? Orientation.HORIZONTAL_LEFT : 
				Orientation.HORIZONTAL_RIGHT;
		}
		return orientation;
	}

	@Override
	public Cell getCell(int x, int y) {
		for(Word word : words) {
			for(Cell cell : word) {
				if(cell.getX() == x && cell.getY() == y) {
					return cell;
				}
			}
		}
		return null;
	}

	@Override
	public boolean isEmpty(int x, int y) {
		return getCell(x,y) == null;
	}

	@Override
	public int size() {
		return words.size();
	}

	@Override
	public int getWidth() {
		int max = Integer.MIN_VALUE;
		for(Word word : words) {
			for(Cell cell : word) {
				if(cell.getX() > max) {
					max = cell.getX();
				}
			}
		}
		return max + 1;
	}

	@Override
	public int getHeight() {
		int max = Integer.MIN_VALUE;
		for(Word word : words) {
			for(Cell cell : word) {
				if(cell.getY() > max) {
					max = cell.getY();
				}
			}
		}
		return max + 1;
	}

	@Override
	public boolean validate() {
		for(Word word : words) {
			if(!word.isValid()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void render() {
		
	}

	@Override
	public void write(Path path) {
		
	}


}
