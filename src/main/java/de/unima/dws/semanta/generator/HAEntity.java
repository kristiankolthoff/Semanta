package de.unima.dws.semanta.generator;

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
	
	public boolean addHint(String hint) {
		return this.hints.add(hint);
	}
	
	public boolean addOptionalAnswer(Resource oaResource) {
		return this.oaResources.add(oaResource);
	}

	public List<String> getHints() {
		return hints;
	}

	public void setHints(List<String> hints) {
		this.hints = hints;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public String getEntAbstract() {
		return entAbstract;
	}

	public void setEntAbstract(String entAbstract) {
		this.entAbstract = entAbstract;
	}

	public List<Resource> getOAResources() {
		return oaResources;
	}

	public void setOAResources(List<Resource> oaResources) {
		this.oaResources = oaResources;
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
				+ ", resource=" + resource + "]";
	}
	
}
