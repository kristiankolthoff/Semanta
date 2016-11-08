package de.unima.dws.semanta.crossword.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.unima.dws.semanta.model.HAEntity;

public class HAWord implements Word{

	private HAEntity entity;
	private List<Cell> cells;
	private Orientation orientation;
	
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
	
	@Override
	public Iterator<Cell> iterator() {
		return cells.iterator();
	}

	@Override
	public Word addCell(Cell cell) {
		cells.add(cell);
		return this;
	}

	@Override
	public int size() {
		return cells.size();
	}

	@Override
	public boolean isValid() {
		for(Cell cell : cells) {
			if(!cell.isValid()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String getCurrentValue() {
		StringBuilder sb = new StringBuilder();
		for(Cell cell : cells) {
			sb.append(cell.getSolution());
		}
		return sb.toString();
	}

	@Override
	public String getWord() {
		return entity.getAnswer();
	}

	@Override
	public Orientation getOrientation() {
		return orientation;
	}

	@Override
	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	@Override
	public String toString() {
		return "HAWord [entity=" + entity + ", cells=" + cells + ", orientation=" + orientation + "]";
	}
	
}
