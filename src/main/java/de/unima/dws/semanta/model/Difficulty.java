package de.unima.dws.semanta.model;

public enum Difficulty {

	BEGINNER("Easy"),
	ADVANCED("Advanced"),
	EXPERT("Expert");
	
	private String value;
	
	Difficulty(String value) {
		this.setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
