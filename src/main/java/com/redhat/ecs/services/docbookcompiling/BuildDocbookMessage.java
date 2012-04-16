package com.redhat.ecs.services.docbookcompiling;

import com.redhat.ecs.services.docbookcompiling.DocbookBuildingOptions;


public class BuildDocbookMessage
{
	private String query;
	private DocbookBuildingOptions docbookOptions;

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
}
