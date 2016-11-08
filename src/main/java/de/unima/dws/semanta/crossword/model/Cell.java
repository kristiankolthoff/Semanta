package de.unima.dws.semanta.crossword.model;

import javafx.beans.property.SimpleStringProperty;

public class Cell {

	private Type type;
	private SimpleStringProperty label;
	private SimpleStringProperty solution;
	
	private static enum Type {
		EMPTY,
		OCCUPIED,
		LABEL;
	}
	
	public boolean isValid() {
		return label.equals(solution);
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public SimpleStringProperty getLabel() {
		return label;
	}

	public void setLabel(SimpleStringProperty label) {
		this.label = label;
	}

	public SimpleStringProperty getSolution() {
		return solution;
	}

	public void setSolution(SimpleStringProperty solution) {
		this.solution = solution;
	}

	
}
