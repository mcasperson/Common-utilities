package com.redhat.topicindex.rest.entities.interfaces;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

public interface IBaseRESTEntityWithPropertiesV1<T extends IBaseRESTEntityV1<T>> extends IBaseRESTEntityV1<T>
{
	public static final String PROPERTIES_NAME = "properties";
	
	BaseRestCollectionV1<IPropertyTagV1> getProperties();
	void setProperties(BaseRestCollectionV1<IPropertyTagV1> properties);
	void explicitSetProperties(BaseRestCollectionV1<IPropertyTagV1> properties);
	
	IPropertyTagV1 returnProperty(Integer propertyId);
}
