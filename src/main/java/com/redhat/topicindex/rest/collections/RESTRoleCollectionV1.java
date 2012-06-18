package com.redhat.topicindex.rest.collections;

import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTRoleV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTRoleCollectionV1 extends BaseRestCollectionV1<RESTRoleV1, RESTRoleCollectionV1>
{
	private List<RESTRoleV1> items;
	
	@Override
	public List<RESTRoleV1> getItems()
	{
		return this.items;
	}
	
	@Override
	public void setItems(final List<RESTRoleV1> items)
	{
		this.items = items;
	}
}
