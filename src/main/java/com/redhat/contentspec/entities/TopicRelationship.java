package com.redhat.contentspec.entities;

import com.redhat.contentspec.SpecTopic;
import com.redhat.contentspec.enums.RelationshipType;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTBaseTopicV1;

/*
 * A class to specify a relationship between two topics.
 */
public class TopicRelationship<T extends RESTBaseTopicV1<T, U>, U extends BaseRestCollectionV1<T, U>> extends Relationship {

	private SpecTopic<T, U> mainRelationship;
	private SpecTopic<T, U> secondaryRelationship;
	
	public TopicRelationship(SpecTopic<T, U> mainTopic, SpecTopic<T, U> secondaryTopic, RelationshipType type) {
		super(mainTopic.getId(), secondaryTopic.getId(), type);
		this.mainRelationship = mainTopic;
		this.secondaryRelationship = secondaryTopic;
	}

	public SpecTopic<T, U> getSecondaryRelationship() {
		return secondaryRelationship;
	}

	public void setSecondaryRelationship(SpecTopic<T, U> secondaryRelationship) {
		this.secondaryRelationship = secondaryRelationship;
	}

	public SpecTopic<T, U> getMainRelationship() {
		return mainRelationship;
	}

	public void setMainRelationship(SpecTopic<T, U> mainRelationship) {
		this.mainRelationship = mainRelationship;
	}
	
}
