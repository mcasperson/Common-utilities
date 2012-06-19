package com.redhat.topicindex.rest.entities;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

/**
 * A REST representation of the Tag entity
 */
public class TagV1 extends BaseRESTEntityWithPropertiesV1<TagV1>
{
	public static final String NAME_NAME = "name";
	public static final String DESCRIPTION_NAME = "description";
	public static final String CATEGORIES_NAME = "categories";
	public static final String PARENT_TAGS_NAME = "parenttags";
	public static final String CHILD_TAGS_NAME = "childtags";
	public static final String PROJECTS_NAME = "projects";

	private String name = null;
	private String description = null;
	private BaseRestCollectionV1<CategoryV1> categories = new BaseRestCollectionV1<CategoryV1>();
	private BaseRestCollectionV1<TagV1> parentTags = new BaseRestCollectionV1<TagV1>();
	private BaseRestCollectionV1<TagV1> childTags = new BaseRestCollectionV1<TagV1>();
	private BaseRestCollectionV1<ProjectV1> projects = new BaseRestCollectionV1<ProjectV1>();
	
	@Override
	public TagV1 clone(boolean deepCopy)
	{
		final TagV1 retValue = new TagV1();
		
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
	
	public void setNameExplicit(final String name)
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
	
	public void setDescriptionExplicit(final String description)
	{
		this.description = description;
		this.setParamaterToConfigured(DESCRIPTION_NAME);
	}

	public BaseRestCollectionV1<CategoryV1> getCategories()
	{
		return categories;
	}

	public void setCategories(final BaseRestCollectionV1<CategoryV1> categories)
	{
		this.categories = categories;
	}
	
	public void setCategoriesExplicit(final BaseRestCollectionV1<CategoryV1> categories)
	{
		this.categories = categories;
		this.setParamaterToConfigured(CATEGORIES_NAME);
	}

	public BaseRestCollectionV1<TagV1> getParentTags()
	{
		return parentTags;
	}

	public void setParentTags(final BaseRestCollectionV1<TagV1> parentTags)
	{
		this.parentTags = parentTags;
	}
	
	public void setParentTagsExplicit(final BaseRestCollectionV1<TagV1> parentTags)
	{
		this.parentTags = parentTags;
		this.setParamaterToConfigured(PARENT_TAGS_NAME);
	}

	public BaseRestCollectionV1<TagV1> getChildTags()
	{
		return childTags;
	}

	public void setChildTags(final BaseRestCollectionV1<TagV1> childTags)
	{
		this.childTags = childTags;
	}
	
	public void setChildTagsExplicit(final BaseRestCollectionV1<TagV1> childTags)
	{
		this.childTags = childTags;
		this.setParamaterToConfigured(CHILD_TAGS_NAME);
	}

	@Override
	public boolean equals(final Object other)
	{
		if (other == null)
			return false;
		
		if (!(other instanceof TagV1))
			return false;
		
		final TagV1 otherTag = (TagV1)other;
		
		return this.getId().equals(otherTag.getId());			
	}
	
	public boolean isInCategory(final Integer categoryId)
	{
		if (this.getCategories() != null && this.getCategories().getItems() != null)
		{
			for (final CategoryV1 category : this.getCategories().getItems())
				if (categoryId.equals(category.getId()))
					return true;
		}

		return false;
	}
	
	public Integer getSortForCategory(final Integer id)
	{
		if (this.categories != null && this.categories.getItems() != null)
		{
			for (final CategoryV1 category : this.categories.getItems())
			{
				if (category.getId().equals(id))
					return category.getSort();
			}
		}
		
		return null;
	}

	public BaseRestCollectionV1<ProjectV1> getProjects()
	{
		return projects;
	}

	public void setProjects(final BaseRestCollectionV1<ProjectV1> projects)
	{
		this.projects = projects;
	}
	
	public void setProjectsExplicit(final BaseRestCollectionV1<ProjectV1> projects)
	{
		this.projects = projects;
		this.setParamaterToConfigured(PROJECTS_NAME);
	}
	
}
