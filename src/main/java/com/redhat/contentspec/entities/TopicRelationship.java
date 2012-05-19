package com.redhat.contentspec.entities;

import com.redhat.contentspec.SpecTopic;
import com.redhat.contentspec.enums.RelationshipType;

/*
 * A class to specify a relationship between two topics.
 */
public class TopicRelationship extends Relationship {

	private SpecTopic mainRelationship;
	private SpecTopic secondaryRelationship;
	
	public TopicRelationship(SpecTopic mainTopic, SpecTopic secondaryTopic, RelationshipType type) {
		super(mainTopic.getId(), secondaryTopic.getId(), type);
		this.mainRelationship = mainTopic;
		this.secondaryRelationship = secondaryTopic;
	}

	public SpecTopic getSecondaryRelationship() {
		return secondaryRelationship;
	}

	public void setSecondaryRelationship(SpecTopic secondaryRelationship) {
		this.secondaryRelationship = secondaryRelationship;
	}

	public SpecTopic getMainRelationship() {
		return mainRelationship;
	}

	public void setMainRelationship(SpecTopic mainRelationship) {
		this.mainRelationship = mainRelationship;
	}
	
}
