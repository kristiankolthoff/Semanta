package de.unima.dws.semanta.model;

import org.apache.jena.rdf.model.Resource;

public class ResourceInfo {
	
	private Resource resource;
	private String uri;
	private String type;
	
	public ResourceInfo(Resource resource, String uri, String type) {
		this.resource = resource;
		this.uri = uri;
		this.type = type;
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
	
	
}
