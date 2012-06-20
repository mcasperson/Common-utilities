package com.redhat.topicindex.rest.collections;

import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTTranslatedTopicStringV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTTranslatedTopicStringCollectionV1 extends BaseRestCollectionV1<RESTTranslatedTopicStringV1, RESTTranslatedTopicStringCollectionV1>
{
	private List<RESTTranslatedTopicStringV1> items;
	
	@Override
	public List<RESTTranslatedTopicStringV1> getItems()
	{
		return this.items;
	}
	
	@Override
	public void setItems(final List<RESTTranslatedTopicStringV1> items)
	{
		this.items = items;
	}
}
