package com.redhat.contentspec.entities;

import com.redhat.contentspec.SpecTopic;
import com.redhat.contentspec.enums.RelationshipType;

/*
 * A class to specify a relationship between two topics.
 */
public class TopicRelationship extends Relationship
{
	private final SpecTopic mainRelationship;
	private final SpecTopic secondaryRelationship;
	
	public TopicRelationship(final SpecTopic mainTopic, final SpecTopic secondaryTopic, final RelationshipType type)
	{
		super(mainTopic.getId(), secondaryTopic.getId(), type);
		this.mainRelationship = mainTopic;
		this.secondaryRelationship = secondaryTopic;
	}
	
	public TopicRelationship(final SpecTopic mainTopic, final SpecTopic secondaryTopic, final RelationshipType type, final String title)
	{
		super(mainTopic.getId(), secondaryTopic.getId(), type, title);
		this.mainRelationship = mainTopic;
		this.secondaryRelationship = secondaryTopic;
	}

	public SpecTopic getSecondaryRelationship()
	{
		return secondaryRelationship;
	}

	public SpecTopic getMainRelationship()
	{
		return mainRelationship;
	}
}
