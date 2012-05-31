package com.redhat.topicindex.rest.entities;

import com.redhat.topicindex.rest.entities.interfaces.ITopicSourceUrlV1;

public class TopicSourceUrlV1 extends BaseRESTEntityV1<ITopicSourceUrlV1> implements ITopicSourceUrlV1
{
	private String url;
	private String title;
	private String description;
	
	@Override
	public TopicSourceUrlV1 clone(boolean deepCopy)
	{
		final TopicSourceUrlV1 retValue = new TopicSourceUrlV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.url = this.url;
		retValue.description = description;
		retValue.title = this.title;
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
