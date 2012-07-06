package com.redhat.topicindex.rest.collections;

import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTImageV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTImageCollectionV1 extends BaseRestCollectionV1<RESTImageV1, RESTImageCollectionV1>
{
	private List<RESTImageV1> items;
	
	@Override
	public List<RESTImageV1> getItems()
	{
		return this.items;
	}
	
	@Override
	public void setItems(final List<RESTImageV1> items)
	{
		this.items = items;
	}
}
