package com.redhat.topicindex.rest.entities;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

/**
 * The base class for all entities used by the REST interface.
 */
public abstract class BaseRESTEntityV1<T extends BaseRESTEntityV1<T>>
{
	public static final String REVISIONS_NAME = "revisions";
	
	/** The id of the entity */
	private Integer id;
	/** The revision of the entity */
	private Number revision;
	/**
	 * Maintains a list of the database fields that have been specifically set
	 * on this object. This allows us to distinguish them from those that are
	 * just null by default
	 */
	private List<String> configuredParameters = null;
	private String selfLink = null;
	private String editLink = null;
	private String deleteLink = null;
	private String addLink = null;
	/** The names of collections that can be expanded */
	private List<String> expand = null;
	/** true if the database entity this REST entity represents should be added to the collection */ 
	private boolean addItem = false;
	/** true if the database entity this REST entity represents should be removed from the collection */
	private boolean removeItem = false;
	/** A list of the Envers revision numbers */
	private BaseRestCollectionV1<T> revisions = null;
	
	public void cloneInto(final BaseRESTEntityV1<T> clone, final boolean deepCopy)
	{
		clone.id = new Integer(this.id);
		clone.revision = this.revision;
		clone.selfLink = this.selfLink;
		clone.editLink = this.editLink;
		clone.deleteLink = this.deleteLink;
		clone.addItem = this.addItem;
		clone.expand = this.expand;
		clone.addItem = this.addItem;
		clone.removeItem = this.removeItem;
		
		if (deepCopy)
		{
			clone.revisions = this.revisions == null ? null : this.revisions.clone(deepCopy);
		}
		else
		{
			clone.revisions = this.revisions;
		}
	}
	
	public abstract T clone(final boolean deepCopy);
	
	/**
	 * This is a convenience method that adds a value to the configuredParameters collection
	 * @param paramater The parameter to specify as configured
	 */
	protected void setParamaterToConfigured(final String paramater)
	{
		if (configuredParameters == null)
			configuredParameters = new ArrayList<String>();
		if (!configuredParameters.contains(paramater))
			configuredParameters.add(paramater);
	}
	
	public boolean isParameterSet(final String parameter)
	{
		return getConfiguredParameters() != null && getConfiguredParameters().contains(parameter);
	}

	public void setLinks(final String baseUrl, final String restBasePath, final String dataType, final Object id)
	{
		this.setSelfLink(baseUrl + "/1/" + restBasePath + "/get/" + dataType + "/" + id);
		this.setDeleteLink(baseUrl + "/1/" + restBasePath + "/delete/" + dataType + "/" + id);
		this.setAddLink(baseUrl + "/1/" + restBasePath + "/post/" + dataType + "/" + id);
		this.setEditLink(baseUrl + "/1/" + restBasePath + "/put/" + dataType + "/" + id);
	}

	@XmlElement
	public String getSelfLink()
	{
		return selfLink;
	}

	public void setSelfLink(final String selfLink)
	{
		this.selfLink = selfLink;
	}

	@XmlElement
	public String getEditLink()
	{
		return editLink;
	}

	public void setEditLink(final String editLink)
	{
		this.editLink = editLink;
	}

	@XmlElement
	public String getDeleteLink()
	{
		return deleteLink;
	}

	public void setDeleteLink(final String deleteLink)
	{
		this.deleteLink = deleteLink;
	}

	@XmlElement
	public String getAddLink()
	{
		return addLink;
	}

	public void setAddLink(final String addLink)
	{
		this.addLink = addLink;
	}

	@XmlElement
	public List<String> getExpand()
	{
		return expand;
	}

	public void setExpand(final List<String> expand)
	{
		this.expand = expand;
	}

	@XmlElement
	public boolean getAddItem()
	{
		return addItem;
	}

	public void setAddItem(final boolean addItem)
	{
		this.addItem = addItem;
	}

	@XmlElement
	public boolean getRemoveItem()
	{
		return removeItem;
	}

	public void setRemoveItem(final boolean removeItem)
	{
		this.removeItem = removeItem;
	}

	@XmlElement
	public List<String> getConfiguredParameters()
	{
		return configuredParameters;
	}

	public void setConfiguredParameters(List<String> configuredParameters)
	{
		this.configuredParameters = configuredParameters;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public Number getRevision()
	{
		return revision;
	}

	public void setRevision(Number revision)
	{
		this.revision = revision;
	}

	public BaseRestCollectionV1<T> getRevisions()
	{
		return revisions;
	}

	public void setRevisions(BaseRestCollectionV1<T> revisions)
	{
		this.revisions = revisions;
	}


}
