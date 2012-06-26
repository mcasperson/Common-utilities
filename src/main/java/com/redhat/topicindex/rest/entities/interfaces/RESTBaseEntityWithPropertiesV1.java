package com.redhat.topicindex.rest.entities.interfaces;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.collections.RESTPropertyTagCollectionV1;

public abstract class RESTBaseEntityWithPropertiesV1<T extends RESTBaseEntityV1<T, U>, U extends BaseRestCollectionV1<T, U>> extends RESTBaseEntityV1<T, U>
{
	public static final String PROPERTIES_NAME = "properties";
	
	protected RESTPropertyTagCollectionV1 properties = null;
	
	public void cloneInto(final RESTBaseEntityWithPropertiesV1<T, U> clone, final boolean deepCopy)
	{
		super.cloneInto(clone, deepCopy);
		
		if (deepCopy)
		{
			if (this.properties != null)
			{
				clone.properties = new RESTPropertyTagCollectionV1();
				this.properties.cloneInto(clone.properties, deepCopy);
			}
		}
		else
		{
			clone.properties = this.properties;
		}
	}
	
	public RESTPropertyTagCollectionV1 getProperties()
	{
		return properties;
	}

	public void setProperties(final RESTPropertyTagCollectionV1 properties)
	{
		this.properties = properties;
	}

	public void explicitSetProperties(final RESTPropertyTagCollectionV1 properties)
	{
		this.properties = properties;
		setParamaterToConfigured(PROPERTIES_NAME);
	}
}
