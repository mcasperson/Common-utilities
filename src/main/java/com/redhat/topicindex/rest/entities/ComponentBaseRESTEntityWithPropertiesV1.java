package com.redhat.topicindex.rest.entities;

import com.redhat.topicindex.rest.entities.interfaces.RESTBaseEntityWithPropertiesV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTPropertyTagV1;

/**
 * This is the base class for all entities that have Property Tags
 */
public abstract class ComponentBaseRESTEntityWithPropertiesV1<T extends RESTBaseEntityWithPropertiesV1<T>> 
{
	final T source;
	
	public ComponentBaseRESTEntityWithPropertiesV1(final T source)
	{
		this.source = source;
	}
	
	public RESTPropertyTagV1 returnProperty(final Integer propertyTagId)
	{
		return returnProperty(source, propertyTagId);
	}
	
	static public <T extends RESTBaseEntityWithPropertiesV1<T>> RESTPropertyTagV1 returnProperty(final T source, final Integer propertyTagId)
	{
		if (source.getProperties() != null && source.getProperties().getItems() != null)
		{
			for (final RESTPropertyTagV1 property : source.getProperties().getItems())
			{
				if (property.getId().equals(propertyTagId))
					return property;
			}
		}

		return null;
	}
}
