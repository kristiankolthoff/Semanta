package de.unima.dws.semanta.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.rdf.model.Resource;

public class HAEntity {

	private List<String> hints;
	private String answer;
	private Resource resource;
	private String entAbstract;
	private List<Resource> oaResources;
	
	public HAEntity(List<String> hints, String answer, Resource resource, 
			String entAbstract, List<Resource> optionalAnswers) {
		this.hints = hints;
		this.answer = answer;
		this.resource = resource;
		this.entAbstract = entAbstract;
		this.oaResources = optionalAnswers;
	}
	
	public HAEntity(Resource resource) {
		this.resource = resource;
		this.hints = new ArrayList<>();
		this.oaResources = new ArrayList<>();
	}
	
	public HAEntity addHint(String hint) {
		this.hints.add(hint);
		return this;
	}
	
	public HAEntity addOptionalAnswer(Resource oaResource) {
		this.oaResources.add(oaResource);
		return this;
	}

	public List<String> getHints() {
		return hints;
	}

	public HAEntity setHints(List<String> hints) {
		this.hints = hints;
		return this;
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
		return entAbstract;
	}
	
	public String getHintsBeautified() {
		StringBuilder sb = new StringBuilder();
		for(String s : hints) {
			sb.append(s);
			sb.append(", ");
		}
		return sb.toString();
	}

	public HAEntity setEntAbstract(String entAbstract) {
		this.entAbstract = entAbstract;
		return this;
	}

	public List<Resource> getOAResources() {
		return oaResources;
	}

	public HAEntity setOAResources(List<Resource> oaResources) {
		this.oaResources = oaResources;
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
				+ ", oaResources=" + oaResources + "]";
	}

}
