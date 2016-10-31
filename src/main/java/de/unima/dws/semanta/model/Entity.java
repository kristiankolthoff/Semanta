package de.unima.dws.semanta.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.graph.Triple;
import org.apache.jena.graph.impl.LiteralLabel;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;

import de.unima.dws.semanta.utilities.Settings;
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
	
	public List<Resource> getTypes() {
		StmtIterator it = resource.listProperties();
		List<Resource> types = new ArrayList<>();
		while(it.hasNext()) {
			Statement stmt = it.next();
			Triple triple = stmt.asTriple();
			if(triple.getPredicate().getURI().equals(Settings.RDF_TYPE) &&
					triple.getObject().getURI().contains(Settings.DBO)) {
				//If complete resource of type property available, return this
				//rather than plain string
				types.add(stmt.getResource());
			}
		}
		return types;
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