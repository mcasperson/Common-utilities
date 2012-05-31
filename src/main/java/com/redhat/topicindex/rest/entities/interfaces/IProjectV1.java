package com.redhat.topicindex.rest.entities.interfaces;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;


public interface IProjectV1 extends IBaseRESTEntityV1<IProjectV1>
{
	public static final String NAME_NAME = "name";
	public static final String DESCRIPTION_NAME = "description";
	public static final String TAGS_NAME = "tags";
	
	String getName();
	void setName(String name);
	
	String getDescription();
	void setDescription(String description);
	
	BaseRestCollectionV1<ITagV1> getTags();
	void setTags(BaseRestCollectionV1<ITagV1> tags);
}
