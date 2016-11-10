package de.unima.dws.semanta.crossword.model;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;


public class Crossword implements Iterable<HAWord>, Serializable, Supplier<Double>{
	
	private static final long serialVersionUID = 1L;
	private List<HAWord> words;
	private double score;
	
	public Crossword() {
		this.words = new ArrayList<>();
	}
	
	public Iterator<HAWord> iterator() {
		return words.iterator();
	}
	
	public boolean validateWord(HAWord word) {
		List<Cell> cells = new ArrayList<>();
		for(Cell cell : word) {
			cells.add(cell);
		}
		return validateCells(cells);
	}
	
	public boolean addWordWithCells(HAWord word) {
		return (validateWord(word)) ? words.add(word) : false;
	}
	
	//Adds cells again! caution
	public boolean addWord(HAWord word) {
		Iterator<Cell> it = word.iterator();
		Cell start = null, end = null;
		if(it.hasNext()) {
			start = it.next();			
		}
		while(it.hasNext()) {
			end = it.next();
		}
		return (start != null && end != null) ? addWord(word, start.getX(), 
				start.getY(), end.getX(), end.getY()) : false;
	}

	public boolean addWord(HAWord word, int startX, int startY, int endX,
			int endY) {
		Orientation orientation = computeOrientation(startX, startY, endX, endY);
		if(orientation == null) {
			return false;
		}
		int length = computeLength(orientation, startX, startY, endX, endY);
		if(length != word.getWord().length()) {
			return false;
		}
		word.setOrientation(orientation);
		List<Cell> cells = new ArrayList<>();
		if(orientation == Orientation.VERTICAL) {
			int current = 0;
			for (int i = startY; i <= endY ; i++) {
				cells.add(new Cell(startX, i, 
						String.valueOf(word.getWord().charAt(current)), ""));
				current++;
			}
		} else if(orientation == Orientation.HORIZONTAL) {
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
		return false;
	}
	
	public boolean addWord(HAWord word, WordDimension dimension) {
		return addWord(word, dimension.getStartX(), 
				dimension.getStartY(), dimension.getEndX(), dimension.getEndY());
	}
	
	private boolean validateCells(List<Cell> cells) {
		for(Cell cellToVal : cells) {
			for(HAWord word : words) {
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
		if(orientation == Orientation.VERTICAL) {
			length = endY + 1 - startY;
		} else if(orientation == Orientation.HORIZONTAL) {
			length = endX + 1 - startX;
		}
		return length;
	}
	
	private Orientation computeOrientation(int startX, int startY, int endX,
			int endY) {
		Orientation orientation = null;
		if(startX == endX && startY < endY) {
			//Vertical word
			orientation = Orientation.VERTICAL;
		} else if(startY == endY && startX < endX) {
			//Horizontal word
			orientation = Orientation.HORIZONTAL;
		}
		return orientation;
	}

	public Cell getCell(int x, int y) {
		for(HAWord word : words) {
			for(Cell cell : word) {
				if(cell.getX() == x && cell.getY() == y) {
					return cell;
				}
			}
		}
		return null;
	}

	public boolean isEmpty(int x, int y) {
		return getCell(x,y) == null;
	}

	public int size() {
		return words.size();
	}

	public int getWidth() {
		int max = Integer.MIN_VALUE;
		for(HAWord word : words) {
			for(Cell cell : word) {
				if(cell.getX() > max) {
					max = cell.getX();
				}
			}
		}
		return max + 1;
	}

	public int getHeight() {
		int max = Integer.MIN_VALUE;
		for(HAWord word : words) {
			for(Cell cell : word) {
				if(cell.getY() > max) {
					max = cell.getY();
				}
			}
		}
		return max + 1;
	}

	public boolean validate() {
		for(HAWord word : words) {
			if(!word.isValid()) {
				return false;
			}
		}
		return true;
	}
	
	public void normalize() {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		for(HAWord word : words) {
			for(Cell cell : word) {
				if(cell.getX() < minX) {
					minX = cell.getX();
				}
				if(cell.getY() < minY) {
					minY = cell.getY();
				}
			}
		}
		minX = Math.abs(minX);
		minY = Math.abs(minY);
		for(HAWord word : words) {
			for(Cell cell : word) {
				cell.setX(cell.getX() + minX);
				cell.setY(cell.getY() + minY);
			}
		}
	}

	public void render() {
		
	}

	public void write(Path path) {
		
	}
	
	@Override
	public Double get() {
		this.score = (double) (getWidth() * getHeight());
		return score;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Crossword [score=" + score + "]";
	}

}