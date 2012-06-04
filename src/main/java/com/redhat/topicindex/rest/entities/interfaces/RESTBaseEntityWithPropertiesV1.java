package com.redhat.topicindex.rest.entities.interfaces;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

public abstract class RESTBaseEntityWithPropertiesV1<T extends RESTBaseEntityV1<T>> extends RESTBaseEntityV1<T>
{
	public static final String PROPERTIES_NAME = "properties";
	
	protected BaseRestCollectionV1<RESTPropertyTagV1> properties = null;
	
	public void cloneInto(final RESTBaseEntityWithPropertiesV1<T> clone, final boolean deepCopy)
	{
		super.cloneInto(clone, deepCopy);
		
		if (deepCopy)
		{
			clone.properties = this.properties == null ? null : this.properties.clone(deepCopy);
		}
		else
		{
			clone.properties = this.properties;
		}
	}
	
	public BaseRestCollectionV1<RESTPropertyTagV1> getProperties()
	{
		return properties;
	}

	public void setProperties(final BaseRestCollectionV1<RESTPropertyTagV1> properties)
	{
		this.properties = properties;
	}

	public void explicitSetProperties(final BaseRestCollectionV1<RESTPropertyTagV1> properties)
	{
		this.properties = properties;
		setParamaterToConfigured(PROPERTIES_NAME);
	}
}
