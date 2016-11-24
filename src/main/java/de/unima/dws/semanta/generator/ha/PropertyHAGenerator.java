package de.unima.dws.semanta.generator.ha;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.model.HAEntity;
import de.unima.dws.semanta.service.SparqlService;

public class PropertyHAGenerator implements HAGenerator {

	private List<Literal> properties;
	private List<Literal> objects;
	private Random random;
	private StringBuilder sb;
	
	public PropertyHAGenerator() {
		this.properties = new ArrayList<>();
		this.objects = new ArrayList<>();
		this.random = new Random();
		this.sb = new StringBuilder();
	}
	
	public HAEntity generate(Entity entity, Resource topicResource, Difficulty difficulty) {
		rankProperties(entity);
		HAEntity val = new HAEntity(entity.getResource()).setAnswer(entity.getLabel());
		if(properties.isEmpty()) {
			return val;
		}
		sb.setLength(0);
		String hint = null;
		int position = random.nextInt((properties.size()>3) ? properties.size() / 3 : properties.size());
		System.out.println(position);
		if(difficulty == Difficulty.BEGINNER) {
			hint = getHint(position, entity.getSpecialOntType().getLocalName().toLowerCase());
		} else if(difficulty == Difficulty.ADVANCED) {
			int offset = properties.size() / 3;
			hint = getHint(position + offset, entity.getSpecialOntType().getLocalName().toLowerCase());
		} else if(difficulty == Difficulty.EXPERT) {
			int offset = (properties.size() / 3) * 2;
			hint = getHint(position + offset, entity.getGeneralOntType().getLocalName());
		}
		return val.addHint(hint);
	}
	
	private void rankProperties(Entity entity) {
		ResultSet rs = SparqlService.queryResourcePropertyRanks(entity.getResource().getURI());
		while(rs.hasNext()) {
			QuerySolution qs = rs.next();
			properties.add(qs.getLiteral("labelP"));
			objects.add(qs.getLiteral("labelO"));
		}
	}
	
	private String getHint(int position, String type) {
		sb.append(type + " with ");
		sb.append(properties.get(position).getString() + " ");
		sb.append(objects.get(position).getString() + " ");
		return sb.toString();
	}

}
