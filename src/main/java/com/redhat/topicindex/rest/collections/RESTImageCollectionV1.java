package com.redhat.topicindex.rest.collections;

import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTImageV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTImageCollectionV1 extends BaseRestCollectionV1<RESTImageV1>
{
	public List<RESTImageV1> getItems()
	{
		return this.items;
	}
	
	public void setItems(final List<RESTImageV1> items)
	{
		this.items = items;
	}
}
