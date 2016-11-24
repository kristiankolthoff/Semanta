package de.unima.dws.semanta.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import de.unima.dws.semanta.utilities.Settings;

public class HAEntity {

	private List<String> hints;
	private String answer;
	//TODO refactor to entity
	private Resource resource;
	private String entAbstract;
	private String imageURL;
	private List<ResourceInfo> distractors;
	
	public HAEntity(List<String> hints, String answer, Resource resource, 
			String entAbstract, List<ResourceInfo> distractors) {
		this.hints = hints;
		this.answer = answer;
		this.resource = resource;
		this.entAbstract = entAbstract;
		this.distractors = distractors;
	}
	
	public HAEntity(Resource resource) {
		this.resource = resource;
		this.hints = new ArrayList<>();
		this.distractors = new ArrayList<>();
	}
	
	public HAEntity addHint(String hint) {
		this.hints.add(hint);
		return this;
	}
	
	public HAEntity addOptionalAnswer(ResourceInfo oaResource) {
		this.distractors.add(oaResource);
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
		return resource;
	}

	public HAEntity setResource(Resource resource) {
		this.resource = resource;
		return this;
	}

	public String getEntAbstract() {
		if(entAbstract == null) {
			StmtIterator it = resource.listProperties();
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
		return this;
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
				+ ((resource == null) ? 0 : resource.hashCode());
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
		if (resource == null) {
			if (other.resource != null)
				return false;
		} else if (!resource.equals(other.resource))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "HAEntity [hints=" + hints + ", answer=" + answer
				+ ", resource=" + resource + ", entAbstract=" + entAbstract
				+ ", oaResources=" + distractors + "]";
	}

	public String getImageURL() {
		if(imageURL == null) {
			StmtIterator it = resource.listProperties();
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

}
