package com.redhat.topicindex.rest.collections;

import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTUserV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTUserCollectionV1 extends BaseRestCollectionV1<RESTUserV1, RESTUserCollectionV1>
{
	private List<RESTUserV1> items;
	
	@Override
	public List<RESTUserV1> getItems()
	{
		return this.items;
	}
	
	@Override
	public void setItems(final List<RESTUserV1> items)
	{
		this.items = items;
	}
}
