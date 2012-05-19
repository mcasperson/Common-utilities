package com.redhat.contentspec.entities;

import com.redhat.contentspec.enums.RelationshipType;

/**
 * A class to hold a basic relationship until it can be processed at a later stage.
 */
public class Relationship {
	
	private final String mainRelationshipTopicId;
	private final String secondaryRelationshipTopicId;
	private final RelationshipType type;
	
	public Relationship(String mainId, String secondaryId, RelationshipType type) {
		this.mainRelationshipTopicId = mainId;
		this.secondaryRelationshipTopicId = secondaryId;
		this.type = type;
	}

	public String getSecondaryRelationshipTopicId() {
		return secondaryRelationshipTopicId;
	}

	public String getMainRelationshipTopicId() {
		return mainRelationshipTopicId;
	}

	public RelationshipType getType() {
		return type;
	}
}
