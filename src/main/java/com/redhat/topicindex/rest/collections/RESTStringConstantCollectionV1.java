package com.redhat.topicindex.rest.collections;

import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTStringConstantV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTStringConstantCollectionV1 extends BaseRestCollectionV1<RESTStringConstantV1, RESTStringConstantCollectionV1>
{
	private List<RESTStringConstantV1> items;
	
	@Override
	public List<RESTStringConstantV1> getItems()
	{
		return this.items;
	}
	
	@Override
	public void setItems(final List<RESTStringConstantV1> items)
	{
		this.items = items;
	}
}
