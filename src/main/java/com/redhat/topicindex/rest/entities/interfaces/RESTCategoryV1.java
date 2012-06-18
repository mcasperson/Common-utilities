package com.redhat.topicindex.rest.entities.interfaces;

import com.redhat.topicindex.rest.collections.RESTTagCollectionV1;

public class RESTCategoryV1 extends RESTBaseEntityV1<RESTCategoryV1>
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
	private RESTTagCollectionV1 tags = null;
	
	@Override
	public RESTCategoryV1 clone(boolean deepCopy)
	{
		final RESTCategoryV1 retValue = new RESTCategoryV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.name = this.name;
		retValue.description = description;
		retValue.mutuallyExclusive = this.mutuallyExclusive;
		retValue.sort = new Integer(this.sort);
		
		if (deepCopy)
		{
			if (this.tags == null)
				retValue.tags = null;
			else
				this.tags.cloneInto(retValue.tags, deepCopy);
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

	public boolean getMutuallyExclusive()
	{
		return mutuallyExclusive;
	}

	public void setMutuallyExclusive(final boolean mutuallyExclusive)
	{
		this.mutuallyExclusive = mutuallyExclusive;
	}
	
	public void explicitSetMutuallyExclusive(final boolean mutuallyExclusive)
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

	public RESTTagCollectionV1 getTags()
	{
		return tags;
	}

	public void setTags(final RESTTagCollectionV1 tags)
	{
		this.tags = tags;
	}
	
	public void setTagsExplicit(final RESTTagCollectionV1 tags)
	{
		this.tags = tags;
		this.setParamaterToConfigured(TAGS_NAME);
	}
}
