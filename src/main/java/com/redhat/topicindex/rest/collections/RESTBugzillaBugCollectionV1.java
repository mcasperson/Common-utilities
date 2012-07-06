package com.redhat.topicindex.rest.collections;

import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTBugzillaBugV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTBugzillaBugCollectionV1 extends BaseRestCollectionV1<RESTBugzillaBugV1, RESTBugzillaBugCollectionV1>
{
	private List<RESTBugzillaBugV1> items;
	
	@Override
	public List<RESTBugzillaBugV1> getItems()
	{
		return this.items;
	}
	
	@Override
	public void setItems(final List<RESTBugzillaBugV1> items)
	{
		this.items = items;
	}
}
