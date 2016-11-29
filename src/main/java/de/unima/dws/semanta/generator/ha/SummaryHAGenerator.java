package de.unima.dws.semanta.generator.ha;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	private Random random;
	
	public SummaryHAGenerator() {
		this.random = new Random();
	}
	
	@Override
	public HAEntity generate(Entity entity, Resource topicResource, Difficulty difficulty) {
		String summary = this.extractSummary(entity.getResource());
		String[] sentences = NLPService.detectSentences(summary);
		String hint = this.getHint(entity, sentences, 1, difficulty);
		return new HAEntity(entity).
				addHint(hint).
				setAnswer(entity.getLabel());
	}
	
	public String getHint(Entity entity, String[] sentences, int numOfHints, Difficulty difficulty) {
		List<String> entitySentences = new ArrayList<>();
		for (int i = 0; i < sentences.length; i++) {
			String sanitized = getSanitizedSentence(sentences[i], entity);
			if(sanitized != null) {
				entitySentences.add(sanitized);
			}
		}
		if(!entitySentences.isEmpty()) {
			if(difficulty == Difficulty.BEGINNER) {
				return entitySentences.get(0);
			} else if(difficulty == Difficulty.ADVANCED) {
				int position = (entitySentences.size() > 2) ? 1 : 0;
				return entitySentences.get(position);
			} else if(difficulty == Difficulty.EXPERT) {
				int position = (entitySentences.size() > 3) ? 2 : 0;
				return entitySentences.get(position);
			}
		}
		return entity.isTyped() ? entity.getSpecialOntType().getLocalName() : "";
	}
	
	public String getSanitizedSentence(String sentence, Entity entity) {
		 String pattern = "\\s+\\(.*?\\)";
		 Pattern r = Pattern.compile(pattern);
		 sentence = r.matcher(sentence).replaceAll("");
		 String[] words = entity.getLabel().split(" ");
		 String labelRegex = ""; //"Angela?\\s*[A-Za-z0-9]*\\s*Merkel";	  
		 for (int i = 0; i < words.length; i++) {
			labelRegex +=  words[i];
			if(i != words.length - 1) {
				labelRegex += "\\s*[A-Za-z0-9]*\\s*";
			}
		 }
		 String type = entity.isTyped() ? 
					entity.getSpecialOntType().getLocalName().toLowerCase() : "thing";
	     Pattern labelPattern = Pattern.compile(labelRegex);
	     sentence = labelPattern.matcher(sentence).replaceAll(" the " + type);
	     for (int i = 0; i < words.length; i++) {
			if(sentence.contains(words[i])) {
				sentence = sentence.replaceAll(words[i], " the " + type);
				break;
			}
		}
	    sentence = sentence.trim();
	    sentence = applyRules(sentence);
	    return sentence.contains(type) ? sentence : null;
	}
	
	public String applyRules(String sentence) {
		sentence = sentence.trim().replaceAll(" +", " ");
		String[] words = sentence.split(" ");
		if(words.length > 2) {
			if(words[0].toLowerCase().equals("the") && words[1].toLowerCase().equals("the")) {
			words[0] = "";
			}
		}
		String val = String.join(" ", words).trim();
		val = val.substring(0, 1).toUpperCase() + val.substring(1);
		return val;
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
