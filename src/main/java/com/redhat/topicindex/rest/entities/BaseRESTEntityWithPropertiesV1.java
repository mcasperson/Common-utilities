package com.redhat.topicindex.rest.entities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

/**
 * This is the base class for all entities that have Property Tags
 */
public abstract class BaseRESTEntityWithPropertiesV1<T extends BaseRESTEntityV1<T>> extends BaseRESTEntityV1<T>
{
	public static final String PROPERTIES_NAME = "properties";
	private BaseRestCollectionV1<PropertyTagV1> properties = null;
	
	public void cloneInto(final BaseRESTEntityWithPropertiesV1<T> clone, final boolean deepCopy)
	{
		super.cloneInto(clone, deepCopy);
		
		if (deepCopy)
		{
			clone.properties = this.properties.clone(deepCopy);
		}
		else
		{
			clone.properties = this.properties;
		}
	}
	
	@XmlElement
	public BaseRestCollectionV1<PropertyTagV1> getProperties()
	{
		return properties;
	}

	public void setProperties(final BaseRestCollectionV1<PropertyTagV1> properties)
	{
		this.properties = properties;
	}

	public void setPropertiesExplicit(final BaseRestCollectionV1<PropertyTagV1> properties)
	{
		this.properties = properties;
		setParamaterToConfigured(PROPERTIES_NAME);
	}

	@XmlTransient
	public PropertyTagV1 getProperty(final Integer propertyTagId)
	{
		if (this.properties != null && this.properties.getItems() != null)
		{
			for (final PropertyTagV1 property : this.properties.getItems())
			{
				if (property.getId().equals(propertyTagId))
					return property;
			}
		}

		return null;
	}
}
