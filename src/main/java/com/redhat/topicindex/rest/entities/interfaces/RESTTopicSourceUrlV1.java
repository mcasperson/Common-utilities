package com.redhat.topicindex.rest.entities.interfaces;

import com.redhat.topicindex.rest.collections.RESTTopicSourceUrlCollectionV1;

public class RESTTopicSourceUrlV1 extends RESTBaseEntityV1<RESTTopicSourceUrlV1, RESTTopicSourceUrlCollectionV1>
{
	public static final String URL_NAME = "url";
	public static final String DESCRIPTION_NAME = "description";
	public static final String TITLE_NAME = "title";
	
	private String url = null;
	private String title = null;
	private String description = null;
	/** A list of the Envers revision numbers */
	private RESTTopicSourceUrlCollectionV1 revisions = null;
	
	@Override
	public RESTTopicSourceUrlCollectionV1 getRevisions()
	{
		return revisions;
	}

	@Override
	public void setRevisions(final RESTTopicSourceUrlCollectionV1 revisions)
	{
		this.revisions = revisions;
	}
	
	@Override
	public RESTTopicSourceUrlV1 clone(boolean deepCopy)
	{
		final RESTTopicSourceUrlV1 retValue = new RESTTopicSourceUrlV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.url = this.url;
		retValue.description = description;
		retValue.title = this.title;
		
		if (deepCopy)
		{		
			if (this.revisions != null)
			{
				retValue.revisions = new RESTTopicSourceUrlCollectionV1();
				this.revisions.cloneInto(retValue.revisions, deepCopy);
			}
		}
		else
		{
			retValue.revisions = this.revisions;
		}
		
		return retValue;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(final String url)
	{
		this.url = url;
	}
	
	public void setUrlExplicit(final String url)
	{
		this.url = url;
		this.setParamaterToConfigured(URL_NAME);
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(final String title)
	{
		this.title = title;
	}
	
	public void setTitleExplicit(final String title)
	{
		this.title = title;
		this.setParamaterToConfigured(TITLE_NAME);
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}
	
	public void setDescriptionExplicit(final String description)
	{
		this.description = description;
		this.setParamaterToConfigured(DESCRIPTION_NAME);
	}
}
