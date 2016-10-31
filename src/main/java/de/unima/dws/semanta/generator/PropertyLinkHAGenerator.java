package de.unima.dws.semanta.generator;

import java.util.List;

import org.apache.jena.graph.Triple;
import org.apache.jena.graph.impl.LiteralLabel;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.model.HAEntity;
import de.unima.dws.semanta.utilities.Settings;

public class PropertyLinkHAGenerator implements HAGenerator{

	@Override
	public HAEntity generate(Entity entity, Resource topicResource) {
		HAEntity haEntity = new HAEntity(entity.getResource());
		haEntity.setAnswer(getLabel(entity.getResource(), Settings.LANG)).
				 addHint(getHint(entity, topicResource));
		return haEntity;
	}
	
	public String getLabel(Resource resource, String lang) {
		StmtIterator it = resource.listProperties();
		while(it.hasNext()) {
			Statement st = it.next();
			Triple triple = st.asTriple();
			if(triple.getPredicate().getURI().equals(Settings.RDFS_LABEL)) {
				LiteralLabel literal = triple.getObject().getLiteral();
				if(literal.language().equals(Settings.LANG)) {
					return literal.getValue().toString();
				}
			}
		}
		return null;
	}
	
	public String getHint(Entity entity, Resource topicResource) {
		List<Resource> types = entity.getTypes();
		String hint = entity.getProperty().getLocalName() + " of a " + 
				((types.isEmpty()) ? "" : types.get((int)(Math.random()*types.size())).getLocalName());
		return hint.toLowerCase();
	}
	

}
