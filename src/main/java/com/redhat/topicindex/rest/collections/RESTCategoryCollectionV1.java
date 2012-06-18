package com.redhat.topicindex.rest.collections;

import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTCategoryV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTCategoryCollectionV1 extends BaseRestCollectionV1<RESTCategoryV1>
{
	public List<RESTCategoryV1> getItems()
	{
		return this.items;
	}
	
	public void setItems(final List<RESTCategoryV1> items)
	{
		this.items = items;
	}
}
