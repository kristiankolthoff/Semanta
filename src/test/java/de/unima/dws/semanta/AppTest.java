package de.unima.dws.semanta;

import java.util.List;

import org.junit.Test;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.HAEntity;

/**
 * Unit test for simple App.
 */
public class AppTest {
 
	@Test
	public void semantaTest() {
		Semanta semanta = new Semanta();
		List<HAEntity> vals = semanta.
				fetchEntities("http://dbpedia.org/resource/Cristiano_Ronaldo", 5, true, Difficulty.BEGINNER);
		System.out.println(vals.size());
	}
}
