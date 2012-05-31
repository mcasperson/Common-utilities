package com.redhat.topicindex.rest.entities.interfaces;

import javax.xml.bind.annotation.XmlSeeAlso;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.CategoryV1;

@XmlSeeAlso(CategoryV1.class)
public interface ICategoryV1 extends IBaseRESTEntityV1<ICategoryV1>
{
	public static final String NAME_NAME = "name";
	public static final String DESCRIPTION_NAME = "description";
	public static final String MUTUALLYEXCLUSIVE_NAME = "mutuallyExclusive";
	public static final String SORT_NAME = "sort";
	public static final String TAGS_NAME = "tags";
	
	String getName();
	void setName(String name);
	void explicitSetName(String name);
	
	String getDescription();
	void setDescription(String description);
	void explicitSetDescription(String description);
	
	boolean getMutuallyExclusive();
	void setMutuallyExclusive(boolean mutuallyExclusive);
	void explicitSetMutuallyExclusive(boolean mutuallyExclusive);
	
	Integer getSort();
	void setSort(Integer sort);
	
	BaseRestCollectionV1<ITagV1> getTags();
	void setTags(BaseRestCollectionV1<ITagV1> tags);
}
