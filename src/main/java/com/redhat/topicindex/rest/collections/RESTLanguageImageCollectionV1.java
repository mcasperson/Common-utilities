package com.redhat.topicindex.rest.collections;

import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTLanguageImageV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTLanguageImageCollectionV1 extends BaseRestCollectionV1<RESTLanguageImageV1, RESTLanguageImageCollectionV1>
{
	private List<RESTLanguageImageV1> items;
	
	@Override
	public List<RESTLanguageImageV1> getItems()
	{
		return this.items;
	}
	
	@Override
	public void setItems(final List<RESTLanguageImageV1> items)
	{
		this.items = items;
	}
}
