package com.redhat.topicindex.rest.entities.interfaces;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

public class RESTTagV1 extends RESTBaseEntityWithPropertiesV1<RESTTagV1>
{
	public static final String NAME_NAME = "name";
	public static final String DESCRIPTION_NAME = "description";
	public static final String CATEGORIES_NAME = "categories";
	public static final String PARENT_TAGS_NAME = "parenttags";
	public static final String CHILD_TAGS_NAME = "childtags";
	public static final String PROJECTS_NAME = "projects";
	
	protected String name = null;
	protected String description = null;
	protected BaseRestCollectionV1<RESTCategoryV1> categories = new BaseRestCollectionV1<RESTCategoryV1>();
	protected BaseRestCollectionV1<RESTTagV1> parentTags = new BaseRestCollectionV1<RESTTagV1>();
	protected BaseRestCollectionV1<RESTTagV1> childTags = new BaseRestCollectionV1<RESTTagV1>();
	protected BaseRestCollectionV1<RESTProjectV1> projects = new BaseRestCollectionV1<RESTProjectV1>();
	
	@Override
	public RESTTagV1 clone(boolean deepCopy)
	{
		final RESTTagV1 retValue = new RESTTagV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.name = this.name;
		retValue.description = description;
		
		if (deepCopy)
		{
			retValue.categories = this.categories == null ? null : this.categories.clone(deepCopy);
			retValue.parentTags = this.parentTags == null ? null : this.parentTags.clone(deepCopy);
			retValue.childTags = this.childTags == null ? null : this.childTags.clone(deepCopy);
			retValue.projects = this.projects == null ? null : this.projects.clone(deepCopy);
		}
		else
		{
			retValue.categories = this.categories;
			retValue.parentTags = this.parentTags;
			retValue.childTags = this.childTags;
			retValue.projects = this.projects;
		}
		
		return retValue;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}
	
	public void explicitSetName(final String name)
	{
		this.name = name;
		this.setParamaterToConfigured(NAME_NAME);
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}
	
	public void explicitSetDescription(final String description)
	{
		this.description = description;
		this.setParamaterToConfigured(DESCRIPTION_NAME);
	}

	public BaseRestCollectionV1<RESTCategoryV1> getCategories()
	{
		return categories;
	}

	public void setCategories(final BaseRestCollectionV1<RESTCategoryV1> categories)
	{
		this.categories = categories;
	}
	
	public void explicitSetCategories(final BaseRestCollectionV1<RESTCategoryV1> categories)
	{
		this.categories = categories;
		this.setParamaterToConfigured(CATEGORIES_NAME);
	}

	public BaseRestCollectionV1<RESTTagV1> getParentTags()
	{
		return parentTags;
	}

	public void setParentTags(final BaseRestCollectionV1<RESTTagV1> parentTags)
	{
		this.parentTags = parentTags;
	}
	
	public void explicitSetParentTags(final BaseRestCollectionV1<RESTTagV1> parentTags)
	{
		this.parentTags = parentTags;
		this.setParamaterToConfigured(PARENT_TAGS_NAME);
	}

	public BaseRestCollectionV1<RESTTagV1> getChildTags()
	{
		return childTags;
	}

	public void setChildTags(final BaseRestCollectionV1<RESTTagV1> childTags)
	{
		this.childTags = childTags;
	}
	
	public void explicitSetChildTags(final BaseRestCollectionV1<RESTTagV1> childTags)
	{
		this.childTags = childTags;
		this.setParamaterToConfigured(CHILD_TAGS_NAME);
	}
	
	public BaseRestCollectionV1<RESTProjectV1> getProjects()
	{
		return projects;
	}

	public void setProjects(final BaseRestCollectionV1<RESTProjectV1> projects)
	{
		this.projects = projects;
	}
	
	public void explicitSetProjects(final BaseRestCollectionV1<RESTProjectV1> projects)
	{
		this.projects = projects;
		this.setParamaterToConfigured(PROJECTS_NAME);
	}
}
