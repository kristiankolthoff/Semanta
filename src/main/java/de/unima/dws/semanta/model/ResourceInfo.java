package de.unima.dws.semanta.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.apache.jena.rdf.model.Resource;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType
@XmlRootElement(name = "ResourceInfo")
public class ResourceInfo {
	
	@XmlElement
	private Resource resource;
	private String uri;
	private String label;
	private String summary;
	@XmlTransient
	private Optional<String> imageURL;
	private List<String> types;
	private List<String> facts;
	@XmlTransient
	private int index;
	@XmlTransient
	private String type;
	
	public ResourceInfo(Resource resource, String uri, String type, String label) {
		this.types = new ArrayList<>();
		this.facts = new ArrayList<>();
		this.resource = resource;
		this.uri = uri;
		this.type = type;
		this.label = label;
	}
	
	
	 public ResourceInfo(Resource resource, String uri, String label, 
			 String summary, String imageURL, int index) {
		this.facts = new ArrayList<>();
		this.facts = new ArrayList<>();
		this.resource = resource;
		this.uri = uri;
		this.label = label;
		this.summary = summary;
		this.imageURL = Optional.ofNullable(imageURL);
		this.index = index;
	}

	 public ResourceInfo addFact(String fact) {
		 this.facts.add(fact);
		 return this;
	 }


	public ResourceInfo() {	}
	
	@PostConstruct
	public void initialize() {
		
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

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public Optional<String> getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = Optional.ofNullable(imageURL);
	}

	public String getTypes() {
		StringBuilder sb = new StringBuilder();
		for(String s : types) {
			sb.append(s);
			sb.append(", ");
		}
		return sb.toString();
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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


	public List<String> getFacts() {
		return facts;
	}


	public void setFacts(List<String> facts) {
		this.facts = facts;
	}
	
	
}
