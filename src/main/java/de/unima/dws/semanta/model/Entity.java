package de.unima.dws.semanta.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.graph.Triple;
import org.apache.jena.graph.impl.LiteralLabel;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
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
	
	public List<Resource> getTypes(String propertyType, String ontTypeRegex) {
		return Entity.getTypes(this.resource, propertyType, ontTypeRegex);
	}
	
	public List<Resource> getTypes() {
		return Entity.getTypes(this.resource, Settings.RDF_TYPE, Settings.DBO);
	}
	
	public String getLabel(String lang) {
		return Entity.getLabel(this.resource, lang);
	}
	
	public String getLabel() {
		return Entity.getLabel(this.resource, Settings.LANG);
	}
	
	public Resource getSpecialOntType() {
		List<Resource> resources = this.getTypes();
		for(Resource source : resources) {
			boolean occuredAsSubclass = false;
			for(Resource target : resources) {
				StmtIterator it = target.listProperties();
				while(it.hasNext()) {
					Triple triple = it.next().asTriple();
					if(triple.getPredicate().getURI().equals(Settings.RDFS_SUBCLASS_OF) && 
							source.getURI().equals(triple.getObject().getURI())) {
								occuredAsSubclass = true;
								break;
							}
				}
			}
			if(!occuredAsSubclass) {
				return source;
			}
		}
		return ResourceFactory.createResource();
	}
	
	public Resource getMediumOntType() {
		return ResourceFactory.createResource();
	}
	
	public Resource getGeneralOntType() {
		List<Resource> resources = this.getTypes();
		for(Resource resource : resources) {
			Statement stmt = resource.getProperty(ResourceFactory.createProperty(Settings.RDFS_SUBCLASS_OF));
			if(stmt.asTriple().getObject().getURI().equals(Settings.OWL_THING)) {
				return resource;
			}
		}
		return ResourceFactory.createResource();
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (forward ? 1231 : 1237);
		result = prime * result
				+ ((property == null) ? 0 : property.hashCode());
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
		Entity other = (Entity) obj;
		if (forward != other.forward)
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
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
		return "Entity [resource=" + resource + ", property=" + property
				+ ", forward=" + forward + "]";
	}
	
	public static String getLabel(Resource resource, String lang) {
		StmtIterator it = resource.listProperties();
		while(it.hasNext()) {
			Statement st = it.next();
			Triple triple = st.asTriple();
			if(triple.getPredicate().getURI().equals(Settings.RDFS_LABEL)) {
				LiteralLabel literal = triple.getObject().getLiteral();
				if(literal.language().equals(lang)) {
					return literal.getValue().toString();
				}
			}
		}
		return null;
	}
	
	public static List<Resource> getTypes(Resource resource, String propertyType, 
			String ontTypeRegex) {
		StmtIterator it = resource.listProperties();
		List<Resource> types = new ArrayList<>();
		while(it.hasNext()) {
			Statement stmt = it.next();
			Triple triple = stmt.asTriple();
			if(triple.getPredicate().getURI().equals(propertyType) &&
					triple.getObject().getURI().contains(ontTypeRegex)) {
				types.add(stmt.getResource());
			}
		}
		return types;
	}
}