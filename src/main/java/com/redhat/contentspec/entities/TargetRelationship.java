package com.redhat.contentspec.entities;

import com.redhat.contentspec.Level;
import com.redhat.contentspec.SpecNode;
import com.redhat.contentspec.SpecTopic;
import com.redhat.contentspec.enums.RelationshipType;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTBaseTopicV1;

/**
 * A class to specify a relationship between a topic and a level.
 */
public class TargetRelationship<T extends RESTBaseTopicV1<T, U>, U extends BaseRestCollectionV1<T, U>> extends Relationship
{

	private final SpecTopic<T, U> topic;
	private final SpecNode<T, U> secondaryNode;

	public TargetRelationship(final SpecTopic<T, U> topic, final Level<T, U> level, final RelationshipType type)
	{
		super(topic.getId(), level.getTargetId(), type);
		this.topic = topic;
		this.secondaryNode = level;
	}

	public TargetRelationship(final SpecTopic<T, U> topic, final SpecTopic<T, U> secondaryTopic, final RelationshipType type)
	{
		super(topic.getId(), secondaryTopic.getTargetId(), type);
		this.topic = topic;
		this.secondaryNode = secondaryTopic;
	}

	public SpecTopic<T, U> getTopic()
	{
		return topic;
	}

	public SpecNode<T, U> getSecondaryElement()
	{
		return secondaryNode;
	}

}
