package com.redhat.topicindex.rest.collections;

import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTTopicV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTTopicCollectionV1 extends BaseRestCollectionV1<RESTTopicV1>
{
	public List<RESTTopicV1> getItems()
	{
		return this.items;
	}
	
	public void setItems(final List<RESTTopicV1> items)
	{
		this.items = items;
	}
}
