package com.redhat.contentspec.entities;

import com.redhat.contentspec.enums.RelationshipType;

/**
 * A class to hold a basic relationship until it can be processed at a later stage.
 */
public class Relationship
{
	private final String mainRelationshipTopicId;
	private final String secondaryRelationshipTopicId;
	private final String relationshipTitle;
	private final RelationshipType type;

	public Relationship(final String mainId, final String secondaryId, final RelationshipType type)
	{
		this(mainId, secondaryId, type, null);
	}
	
	public Relationship(final String mainId, final String secondaryId, final RelationshipType type, final String title)
	{
		this.mainRelationshipTopicId = mainId;
		this.secondaryRelationshipTopicId = secondaryId;
		this.type = type;
		this.relationshipTitle = title;
	}

	public String getSecondaryRelationshipTopicId()
	{
		return secondaryRelationshipTopicId;
	}

	public String getMainRelationshipTopicId()
	{
		return mainRelationshipTopicId;
	}

	public RelationshipType getType()
	{
		return type;
	}

	public String getRelationshipTitle() {
		return relationshipTitle;
	}
}
