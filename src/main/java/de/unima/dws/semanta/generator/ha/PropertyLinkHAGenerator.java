package de.unima.dws.semanta.generator.ha;

import java.util.List;

import org.apache.jena.rdf.model.Resource;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.model.HAEntity;
import de.unima.dws.semanta.service.NLPService;

public class PropertyLinkHAGenerator implements HAGenerator{

	@Override
	public HAEntity generate(Entity entity, Resource topicResource, Difficulty difficulty) {
		HAEntity haEntity = new HAEntity(entity.getResource());
		haEntity.setAnswer(entity.getLabel()).
				 addHint(getHint(entity, topicResource));
		return haEntity;
	}
	
	public String getHint(Entity entity, Resource topicResource) {
		//also consider inverse links between entity and topic
		List<Resource> types = entity.getTypes();
		StringBuilder sb = new StringBuilder();
		sb.append("The ");
		sb.append(entity.getProperty().getLocalName());
		if(!types.isEmpty()) {
			String type = types.get((int)(Math.random()*types.size())).
					getLocalName().toLowerCase();
			if(NLPService.isVowel(type.charAt(0))) {
				sb.append(" of an ");
			} else {
				sb.append(" of a ");
			}
			sb.append(type);
		}
		return sb.toString().trim().toLowerCase();
	}
	

}