package de.unima.dws.semanta.crossword.model;

import javafx.beans.property.SimpleStringProperty;

public class Cell {

	private int x;
	private int y;
	private SimpleStringProperty label;
	private SimpleStringProperty solution;
	
	public Cell(int x, int y, SimpleStringProperty label, 
			SimpleStringProperty solution) {
		this.x = x;
		this.y = y;
		this.label = label;
		this.solution = solution;
	}
	
	public Cell(int x, int y, String label, 
			String solution) {
		this.x = x;
		this.y = y;
		this.label = new SimpleStringProperty(label);
		this.solution = new SimpleStringProperty(solution);
	}
	
	public boolean isValid() {
		return label.get().equals(solution.get());
	}

	public SimpleStringProperty getLabelProperty() {
		return label;
	}

	public void setLabelProperty(SimpleStringProperty label) {
		this.label = label;
	}

	public SimpleStringProperty getSolutionProperty() {
		return solution;
	}

	public void setSolutionProperty(SimpleStringProperty solution) {
		this.solution = solution;
	}
	
	public void setSolution(String solution) {
		this.solution.set(solution);
	}
	
	public void setLabel(String label) {
		this.label.set(label);
	}
	
	public String getSolution() {
		return this.solution.get();
	}
	
	public String getLabel() {
		return this.label.get();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean samePosition(Cell cell) {
		return this.x == cell.getX() && this.y == cell.getY();
	}
	
	public boolean sameContent(Cell cell) {
		return this.getLabel().equals(cell.getLabel());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cell [x=" + x + ", y=" + y + ", label=" + label + ", solution=" + solution + "]";
	}
	
}