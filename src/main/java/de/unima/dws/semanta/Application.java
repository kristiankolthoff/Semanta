package de.unima.dws.semanta;

import java.util.List;

import de.unima.dws.semanta.generator.HAEntity;

public class Application {

	public static void main(String[] args) {
		Semanta semanta = new Semanta();
		List<HAEntity> haEntities = semanta.fetchEntities("germany", 0, true);
		for(HAEntity entity : haEntities) {
			System.out.println(entity);
		}
	}
}
