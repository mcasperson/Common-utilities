package com.redhat.topicindex.rest.collections;

import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTBlobConstantV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTBlobConstantCollectionV1 extends BaseRestCollectionV1<RESTBlobConstantV1>
{
	public List<RESTBlobConstantV1> getItems()
	{
		return this.items;
	}
	
	public void setItems(final List<RESTBlobConstantV1> items)
	{
		this.items = items;
	}
}
