package de.unima.dws.semanta.crossword.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.unima.dws.semanta.model.HAEntity;

public class HAWord implements Iterable<Cell>{

	private HAEntity entity;
	private List<Cell> cells;
	private Orientation orientation;
	private int index;
	private boolean solved;
	
	public HAWord(HAEntity entity, Orientation orientation) {
		this.orientation = orientation;
		this.entity = entity;
		this.cells = new ArrayList<>();
	}
	
	public HAWord(HAEntity entity) {
		this.entity = entity;
		this.cells = new ArrayList<>();
	}
	
	public HAWord(Orientation orientation) {
		this.orientation = orientation;
		this.cells = new ArrayList<>();
	}
	
	public HAWord() {
		this.cells = new ArrayList<>();
	}
	
	public Iterator<Cell> iterator() {
		return cells.iterator();
	}

	public HAWord addCell(Cell cell) {
		cells.add(cell);
		return this;
	}
	
	public HAEntity getHAEntity() {
		return entity;
	}

	public int size() {
		return cells.size();
	}

	public boolean isValid() {
		for(Cell cell : cells) {
			if(!cell.getLabel().equals("LA") && !cell.isValid()) {
				return false;
			}
		}
		return true;
	}

	public String getCurrentValue() {
		StringBuilder sb = new StringBuilder();
		for(Cell cell : cells) {
			sb.append(cell.getSolution());
		}
		return sb.toString();
	}

	public String getWord() {
		return entity.getSanitizedAnswer();
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	@Override
	public String toString() {
		return "HAWord [entity=" + entity + ", cells=" + cells + ", orientation=" + orientation + "]";
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}

	
}
