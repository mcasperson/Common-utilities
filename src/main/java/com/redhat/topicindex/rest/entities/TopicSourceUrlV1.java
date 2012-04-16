package com.redhat.topicindex.rest.entities;

public class TopicSourceUrlV1 extends BaseRESTEntityV1<TopicSourceUrlV1>
{
	public static final String URL_NAME = "url";
	public static final String DESCRIPTION_NAME = "description";
	public static final String TITLE_NAME = "title";
	
	private String url;
	private String title;
	private String description;

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
