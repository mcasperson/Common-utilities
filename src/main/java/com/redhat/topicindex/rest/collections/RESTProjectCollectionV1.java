package com.redhat.topicindex.rest.collections;

import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTProjectV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTProjectCollectionV1 extends BaseRestCollectionV1<RESTProjectV1>
{
	public List<RESTProjectV1> getItems()
	{
		return this.items;
	}
	
	public void setItems(final List<RESTProjectV1> items)
	{
		this.items = items;
	}
}
