package com.redhat.topicindex.rest.entities;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

/**
 * A REST representation of the Project database entity
 */
public class ProjectV1 extends BaseRESTEntityV1<ProjectV1>
{
	public static final String NAME_NAME = "name";
	public static final String DESCRIPTION_NAME = "description";
	public static final String TAGS_NAME = "tags";
	
	private String name = null;
	private String description = null;
	private BaseRestCollectionV1<TagV1> tags = null;
	
	@Override
	public ProjectV1 clone(boolean deepCopy)
	{
		final ProjectV1 retValue = new ProjectV1();
		retValue.name = this.name;
		retValue.description = description;

		if (deepCopy)
		{
			retValue.tags = this.tags.clone(deepCopy);
		}
		else
		{
			retValue.tags = this.tags;
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

	public BaseRestCollectionV1<TagV1> getTags()
	{
		return tags;
	}

	public void setTags(final BaseRestCollectionV1<TagV1> tags)
	{
		this.tags = tags;
	}
	
	public void setTagsExplicit(final BaseRestCollectionV1<TagV1> tags)
	{
		this.tags = tags;
		this.setParamaterToConfigured(TAGS_NAME);
	}

}
