package com.redhat.topicindex.rest.entities.interfaces;

public interface ITopicSourceUrlV1 extends IBaseRESTEntityV1<ITopicSourceUrlV1>
{
	String getUrl();
	void setUrl(String url);
	
	String getTitle();
	void setTitle(String title);
	
	String getDescription();
	void setDescription(String description);
}
