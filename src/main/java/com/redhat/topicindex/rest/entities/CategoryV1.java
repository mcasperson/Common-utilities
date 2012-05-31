package com.redhat.topicindex.rest.entities;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.ICategoryV1;
import com.redhat.topicindex.rest.entities.interfaces.ITagV1;

/**
 * A REST representation of the Category database entity
 */
public class CategoryV1 extends BaseRESTEntityV1<ICategoryV1> implements ICategoryV1
{
	public static final String NAME_NAME = "name";
	public static final String DESCRIPTION_NAME = "description";
	public static final String MUTUALLYEXCLUSIVE_NAME = "mutuallyExclusive";
	public static final String SORT_NAME = "sort";
	public static final String TAGS_NAME = "tags";
	
	private String name = null;
	private String description = null;
	private boolean mutuallyExclusive = false;
	private Integer sort = null;
	private BaseRestCollectionV1<ITagV1> tags = null;
	
	@Override
	public CategoryV1 clone(boolean deepCopy)
	{
		final CategoryV1 retValue = new CategoryV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.name = this.name;
		retValue.description = description;
		retValue.mutuallyExclusive = this.mutuallyExclusive;
		retValue.sort = new Integer(this.sort);
		
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

	public boolean getMutuallyExclusive()
	{
		return mutuallyExclusive;
	}

	public void setMutuallyExclusive(final boolean mutuallyExclusive)
	{
		this.mutuallyExclusive = mutuallyExclusive;
	}
	
	public void setMutuallyExclusiveExplicit(final boolean mutuallyExclusive)
	{
		this.mutuallyExclusive = mutuallyExclusive;
		this.setParamaterToConfigured(MUTUALLYEXCLUSIVE_NAME);
	}

	public Integer getSort()
	{
		return sort;
	}

	public void setSort(final Integer sort)
	{
		this.sort = sort;
	}
	
	public void setSortExplicit(final Integer sort)
	{
		this.sort = sort;
		this.setParamaterToConfigured(SORT_NAME);
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
