package de.unima.dws.semanta.model;

import org.apache.jena.rdf.model.Resource;

public class ResourceInfo {
	
	private Resource resource;
	private String uri;
	private String label;
	private String type;
	
	public ResourceInfo(Resource resource, String uri, String type, String label) {
		this.resource = resource;
		this.uri = uri;
		this.type = type;
		this.label = label;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	

	@Override
	public String toString() {
		return "ResourceInfo [label=" + label + "]";
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	
}
