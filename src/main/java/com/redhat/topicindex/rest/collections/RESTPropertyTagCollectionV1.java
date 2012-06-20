package com.redhat.topicindex.rest.collections;

import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTPropertyTagV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTPropertyTagCollectionV1 extends BaseRestCollectionV1<RESTPropertyTagV1, RESTPropertyTagCollectionV1>
{
	private List<RESTPropertyTagV1> items;
	
	@Override
	public List<RESTPropertyTagV1> getItems()
	{
		return this.items;
	}
	
	@Override
	public void setItems(final List<RESTPropertyTagV1> items)
	{
		this.items = items;
	}
}
