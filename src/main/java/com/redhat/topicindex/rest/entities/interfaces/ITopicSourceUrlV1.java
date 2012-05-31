package com.redhat.topicindex.rest.entities.interfaces;

import javax.xml.bind.annotation.XmlSeeAlso;

import com.redhat.topicindex.rest.entities.TopicSourceUrlV1;

@XmlSeeAlso(TopicSourceUrlV1.class)
public interface ITopicSourceUrlV1 extends IBaseRESTEntityV1<ITopicSourceUrlV1>
{
	public static final String URL_NAME = "url";
	public static final String DESCRIPTION_NAME = "description";
	public static final String TITLE_NAME = "title";
	
	String getUrl();
	void setUrl(String url);
	
	String getTitle();
	void setTitle(String title);
	
	String getDescription();
	void setDescription(String description);
}
