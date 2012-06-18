package com.redhat.topicindex.rest.collections;

import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTTopicSourceUrlV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTTopicSourceUrlCollectionV1 extends BaseRestCollectionV1<RESTTopicSourceUrlV1>
{
	public List<RESTTopicSourceUrlV1> getItems()
	{
		return this.items;
	}
	
	public void setItems(final List<RESTTopicSourceUrlV1> items)
	{
		this.items = items;
	}
}
