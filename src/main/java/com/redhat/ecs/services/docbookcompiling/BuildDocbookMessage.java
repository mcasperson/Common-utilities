package com.redhat.ecs.services.docbookcompiling;

import com.redhat.ecs.services.docbookcompiling.DocbookBuildingOptions;
import com.redhat.topicindex.zanata.ZanataDetails;

public class BuildDocbookMessage
{
	private String query;
	private DocbookBuildingOptions docbookOptions;
	private int entityType;
	private ZanataDetails zanataDetails;

	public String getQuery()
	{
		return query;
	}

	public void setQuery(final String query)
	{
		this.query = query;
	}

	public DocbookBuildingOptions getDocbookOptions()
	{
		return docbookOptions;
	}

	public void setDocbookOptions(final DocbookBuildingOptions docbookOptions)
	{
		this.docbookOptions = docbookOptions;
	}

	public int getEntityType() {
		return entityType;
	}

	public void setEntityType(int entityType) {
		this.entityType = entityType;
	}

	public ZanataDetails getZanataDetails()
	{
		return zanataDetails;
	}

	public void setZanataDetails(final ZanataDetails zanataDetails)
	{
		this.zanataDetails = zanataDetails;
	}
}
