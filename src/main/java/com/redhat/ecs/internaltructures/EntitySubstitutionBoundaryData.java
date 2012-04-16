package com.redhat.ecs.internaltructures;

import com.redhat.ecs.commonstructures.Pair;

public class EntitySubstitutionBoundaryData
{
	private String substitution;
	private String entityName;
	private Pair<Integer, Integer> boundary;

	public String getSubstitution()
	{
		return substitution;
	}

	public void setSubstitution(String substitution)
	{
		this.substitution = substitution;
	}

	public String getEntityName()
	{
		return entityName;
	}

	public void setEntityName(String entityName)
	{
		this.entityName = entityName;
	}

	public Pair<Integer, Integer> getBoundary()
	{
		return boundary;
	}

	public void setBoundary(Pair<Integer, Integer> boundary)
	{
		this.boundary = boundary;
	}
	
	public EntitySubstitutionBoundaryData(final String entityName, final String substitution, final Pair<Integer, Integer> boundary)
	{
		this.entityName = entityName;
		this.substitution = substitution;
		this.boundary = boundary;
	}
}
