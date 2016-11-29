package de.unima.dws.semanta.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import de.unima.dws.semanta.utilities.Settings;

public class HAEntity {

	private List<String> hints;
	private String answer;
	private Entity entity;
	private String entAbstract;
	private String imageURL;
	private List<String> facts;
	private List<ResourceInfo> distractors;
	
	public HAEntity(List<String> hints, String answer, Entity entity, 
			String entAbstract, List<ResourceInfo> distractors) {
		this.hints = hints;
		this.answer = answer;
		this.entity = entity;
		this.entAbstract = entAbstract;
		this.distractors = distractors;
		this.facts = new ArrayList<>();
	}
	
	public HAEntity(Entity entity) {
		this.entity = entity;
		this.hints = new ArrayList<>();
		this.distractors = new ArrayList<>();
		this.facts = new ArrayList<>();
	}
	
	public HAEntity addHint(String hint) {
		this.hints.add(hint);
		return this;
	}
	
	public HAEntity addOptionalAnswer(ResourceInfo oaResource) {
		this.distractors.add(oaResource);
		return this;
	}
	
	public HAEntity addFact(String fact) {
		this.facts.add(fact);
		return this;
	}

	public List<String> getHints() {
		return hints;
	}

	public HAEntity setHints(List<String> hints) {
		this.hints = hints;
		return this;
	}
	
	public String getSanitizedAnswer() {
		return sanitizeAnswer(answer);
	}
	
	private static String sanitizeAnswer(String answer) {
		return answer.replaceAll("\\s+","").trim().toLowerCase();
	}

	public String getAnswer() {
		return answer;
	}

	public HAEntity setAnswer(String answer) {
		this.answer = answer;
		return this;
	}

	public Resource getResource() {
		return entity.getResource();
	}

	public HAEntity setResource(Resource resource) {
		this.entity.setResource(resource);
		return this;
	}
	
	public String getDistractorA() {
		return sanitizeAnswer(this.distractors.get(0).getLabel());
	}
	
	public String getDistractorB() {
		return sanitizeAnswer(this.distractors.get(1).getLabel());
	}
	
	public String getDistractorC() {
		return sanitizeAnswer(this.distractors.get(2).getLabel());
	}
	
	public String getDistractorD() {
		return sanitizeAnswer(this.distractors.get(3).getLabel());
	}

	public String getEntAbstract() {
		if(entAbstract == null) {
			StmtIterator it = entity.getResource().listProperties();
			while(it.hasNext()) {
				Statement stmt = it.next();
				if(stmt.asTriple().getPredicate().getURI().toString().contains("abstract")) {
					Literal literal = stmt.getObject().asLiteral();
					if(literal.getLanguage().equals(Settings.LANG)) {
						entAbstract = literal.getString();
						break;
					}
				}
			}
		}
		return entAbstract;
	}
	
	public String getTypes() {
		List<Resource> types = Entity.getTypesUnordered(entity.getResource(), Settings.RDF_TYPE, Settings.DBO);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < types.size(); i++) {
			sb.append(types.get(i).getLocalName());
			if(i != types.size()-1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
	
	public String getHintsBeautified() {
		StringBuilder sb = new StringBuilder();
		for(String s : hints) {
			sb.append(s);
			sb.append(" ");
		}
		return sb.toString();
	}

	public HAEntity setEntAbstract(String entAbstract) {
		this.entAbstract = entAbstract;
		return this;
	}

	public List<ResourceInfo> getOAResources() {
		return distractors;
	}

	public HAEntity setOAResources(List<ResourceInfo> oaResources) {
		this.distractors = oaResources;
		this.distractors.add(new ResourceInfo(null, null, null, answer));
		Collections.shuffle(distractors);
		return this;
	}
	
	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answer == null) ? 0 : answer.hashCode());
		result = prime * result
				+ ((entAbstract == null) ? 0 : entAbstract.hashCode());
		result = prime * result + ((hints == null) ? 0 : hints.hashCode());
		result = prime * result
				+ ((entity.getResource() == null) ? 0 : entity.getResource().hashCode());
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
		HAEntity other = (HAEntity) obj;
		if (answer == null) {
			if (other.answer != null)
				return false;
		} else if (!answer.equals(other.answer))
			return false;
		if (entAbstract == null) {
			if (other.entAbstract != null)
				return false;
		} else if (!entAbstract.equals(other.entAbstract))
			return false;
		if (hints == null) {
			if (other.hints != null)
				return false;
		} else if (!hints.equals(other.hints))
			return false;
		if (entity.getResource() == null) {
			if (other.entity.getResource() != null)
				return false;
		} else if (!entity.getResource().equals(other.entity.getResource()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HAEntity [hints=" + hints + ", answer=" + answer
				+ ", resource=" + entity.getResource() + ", entAbstract=" + entAbstract
				+ ", oaResources=" + distractors + "]";
	}

	public String getImageURL() {
		if(imageURL == null) {
			StmtIterator it = entity.getResource().listProperties();
			while(it.hasNext()) {
				Statement stmt = it.next();
				if(stmt.asTriple().getPredicate().getURI().toString().contains("thumbnail")) {
					imageURL = stmt.asTriple().getObject().getURI();
					imageURL = imageURL.substring(0, 4) + "s" + imageURL.substring(4);
					break;
				}
			}
		}
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public List<String> getFacts() {
		return facts;
	}

	public void setFacts(List<String> facts) {
		this.facts = facts;
	}

}
