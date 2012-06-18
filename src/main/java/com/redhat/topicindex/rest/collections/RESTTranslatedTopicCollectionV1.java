package com.redhat.topicindex.rest.collections;

import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTTranslatedTopicV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTTranslatedTopicCollectionV1 extends BaseRestCollectionV1<RESTTranslatedTopicV1, RESTTranslatedTopicCollectionV1>
{
	private List<RESTTranslatedTopicV1> items;
	
	@Override
	public List<RESTTranslatedTopicV1> getItems()
	{
		return this.items;
	}
	
	@Override
	public void setItems(final List<RESTTranslatedTopicV1> items)
	{
		this.items = items;
	}
}
