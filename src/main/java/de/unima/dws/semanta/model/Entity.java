package de.unima.dws.semanta.model;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
/**
 * An Entity is basically a wrapper for a Resource, augementing the
 * Resource with additional information on how the Resource is linked
 * to the topic entity Resource.
 */
public class Entity {

	private Resource resource;
	/**
	 * Property describing the semantic link
	 * between this Resource and the topic entity Resource
	 */
	private Property property;
	/**
	 * Describes the direction of connection between
	 * this Resource and the topic entity Resource.
	 * If true, this Resource receives an incoming link from
	 * the topic entity Resource and vice versa.
	 */
	private boolean forward;
	
	public Entity(Resource resource, Property property, boolean forward) {
		this.resource = resource;
		this.property = property;
		this.forward = forward;
	}
	public Resource getResource() {
		return resource;
	}
	public void setResource(Resource resource) {
		this.resource = resource;
	}
	public Property getProperty() {
		return property;
	}
	public void setProperty(Property property) {
		this.property = property;
	}
	public boolean isForward() {
		return forward;
	}
	public void setForward(boolean forward) {
		this.forward = forward;
	}
	
}