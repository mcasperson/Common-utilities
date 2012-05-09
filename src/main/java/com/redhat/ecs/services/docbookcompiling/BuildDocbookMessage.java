package com.redhat.ecs.services.docbookcompiling;

import com.redhat.ecs.services.docbookcompiling.DocbookBuildingOptions;


public class BuildDocbookMessage
{
	private String query;
	private DocbookBuildingOptions docbookOptions;
	private int entityType;

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
}
