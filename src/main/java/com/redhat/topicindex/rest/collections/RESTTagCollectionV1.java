package com.redhat.topicindex.rest.collections;

import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTTagV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTTagCollectionV1 extends BaseRestCollectionV1<RESTTagV1>
{
	public List<RESTTagV1> getItems()
	{
		return this.items;
	}
	
	public void setItems(final List<RESTTagV1> items)
	{
		this.items = items;
	}
}
