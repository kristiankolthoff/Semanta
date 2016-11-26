package de.unima.dws.semanta.generator.ha;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.graph.Triple;
import org.apache.jena.graph.impl.LiteralLabel;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import de.unima.dws.semanta.model.Difficulty;
import de.unima.dws.semanta.model.Entity;
import de.unima.dws.semanta.model.HAEntity;
import de.unima.dws.semanta.service.NLPService;
import de.unima.dws.semanta.utilities.Settings;

public class SummaryHAGenerator implements HAGenerator {

	
	@Override
	public HAEntity generate(Entity entity, Resource topicResource, Difficulty difficulty) {
		String summary = this.extractSummary(entity.getResource());
		String[] sentences = NLPService.detectSentences(summary);
		String hint = this.getHint(entity, sentences, 1);
		return new HAEntity(entity.getResource()).
				addHint(hint).
				setAnswer(entity.getLabel());
	}
	
	public String getHint(Entity entity, String[] sentences, int numOfHints) {
		List<String> entitySentences = new ArrayList<>();
		for (int i = 0; i < sentences.length; i++) {
			if(sentences[i].contains(entity.getLabel())) {
				String sentence = sentences[i].replaceAll("\\(^0-9A-Za-z\\)", " -----");
				entitySentences.add(sentence);
			}
		}
		numOfHints = (numOfHints > entitySentences.size()) ? 
				entitySentences.size() : numOfHints;
		StringBuilder sb = new StringBuilder();
		String type = entity.isTyped() ? 
				entity.getSpecialOntType().getLocalName().toLowerCase() : "thing";
		for (int i = 0; i < numOfHints; i++) {
			sb.append(" the ");
			sb.append(entitySentences.get(i).replace(entity.getLabel(), type));
		}
		return sb.toString().trim();
	}
	
	
	public String extractSummary(Resource resource) {
		StmtIterator it = resource.listProperties();
		while(it.hasNext()) {
			Statement stmt = it.next();
			Triple triple = stmt.asTriple();
			if(triple.getPredicate().getURI().equals(Settings.DBO_ABSTRACT)) {
				LiteralLabel literal = triple.getObject().getLiteral();
				if(literal.language().equals(Settings.LANG)) {
					return literal.getValue().toString();
				}
			}
		}
		return null;
	}

}
