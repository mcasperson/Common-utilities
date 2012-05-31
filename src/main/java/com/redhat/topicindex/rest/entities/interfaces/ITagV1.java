package com.redhat.topicindex.rest.entities.interfaces;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;


public interface ITagV1 extends IBaseRESTEntityWithPropertiesV1<ITagV1>
{
	public static final String NAME_NAME = "name";
	public static final String DESCRIPTION_NAME = "description";
	public static final String CATEGORIES_NAME = "categories";
	public static final String PARENT_TAGS_NAME = "parenttags";
	public static final String CHILD_TAGS_NAME = "childtags";
	public static final String PROJECTS_NAME = "projects";
	
	String getName();
	void setName(String name);
	void explicitSetName(String name);
	
	String getDescription();
	void setDescription(String description);
	void explicitSetDescription(String description);
	
	BaseRestCollectionV1<ICategoryV1> getCategories();
	void setCategories(BaseRestCollectionV1<ICategoryV1> categories);
	void explicitSetCategories(BaseRestCollectionV1<ICategoryV1> categories);
	
	BaseRestCollectionV1<ITagV1> getParentTags();
	void setParentTags(BaseRestCollectionV1<ITagV1> parentTags);
	void explicitSetParentTags(BaseRestCollectionV1<ITagV1> parentTags);
	
	BaseRestCollectionV1<ITagV1> getChildTags();
	void setChildTags(BaseRestCollectionV1<ITagV1> childTags);
	void explicitSetChildTags(BaseRestCollectionV1<ITagV1> childTags);
	
	BaseRestCollectionV1<IProjectV1> getProjects();
	void setProjects(BaseRestCollectionV1<IProjectV1> categories);
	void explicitSetProjects(BaseRestCollectionV1<IProjectV1> categories);
	
	boolean containedInCategory(Integer categoryId);
}
