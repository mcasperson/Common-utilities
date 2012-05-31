package com.redhat.topicindex.rest.entities;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.IProjectV1;
import com.redhat.topicindex.rest.entities.interfaces.ITagV1;

/**
 * A REST representation of the Project database entity
 */
public class ProjectV1 extends BaseRESTEntityV1<IProjectV1> implements IProjectV1
{
	private String name = null;
	private String description = null;
	private BaseRestCollectionV1<ITagV1> tags = null;
	
	@Override
	public IProjectV1 clone(boolean deepCopy)
	{
		final ProjectV1 retValue = new ProjectV1();
		
		this.cloneInto(retValue, deepCopy);
		
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

	public BaseRestCollectionV1<ITagV1> getTags()
	{
		return tags;
	}

	public void setTags(final BaseRestCollectionV1<ITagV1> tags)
	{
		this.tags = tags;
	}
	
	public void setTagsExplicit(final BaseRestCollectionV1<ITagV1> tags)
	{
		this.tags = tags;
		this.setParamaterToConfigured(TAGS_NAME);
	}

}
