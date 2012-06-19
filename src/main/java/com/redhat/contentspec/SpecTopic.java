package com.redhat.contentspec;

/**
 * An object to store the contents of a Topic in a Content Specification. It stores the topics name, sequential step number, database ID, unique processed ID,
 * description, an array of urls that relate to the topic and a list of tags. 
 * 
 * @author lnewson
 */

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

import com.redhat.contentspec.constants.CSConstants;
import com.redhat.contentspec.entities.TargetRelationship;
import com.redhat.contentspec.entities.Relationship;
import com.redhat.contentspec.entities.TopicRelationship;
import com.redhat.contentspec.enums.RelationshipType;
import com.redhat.ecs.commonutils.StringUtilities;
import com.redhat.ecs.constants.CommonConstants;
import com.redhat.topicindex.rest.entities.BaseTopicV1;

public class SpecTopic extends SpecNode {
	
	private String id;
	private int DBId = 0;
	private String type;
	private ArrayList<TopicRelationship> topicRelationships = new ArrayList<TopicRelationship>();
	private ArrayList<TargetRelationship> topicTargetRelationships = new ArrayList<TargetRelationship>();
	private ArrayList<TargetRelationship> levelRelationships = new ArrayList<TargetRelationship>();
	private String targetId = null;
	private int preProcessedLineNumber = 0;
	private String title = null;
	private String duplicateId = null;
	private BaseTopicV1<? extends BaseTopicV1<?>> topic = null;
	private Document xmlDocument = null;
	private Integer revision = null;
	
	/**
	 * Constructor
	 * 
	 * @param id The ID for the Content Specification Topic (N, N<ID>, C<ID>, etc...)
	 * @param title The title of the Content Specification Topic.
	 * @param lineNumber The post processed Line Number of the topic.
	 * @param specLine The Content Specification Line that is used to create the Topic.
	 * @param preProcessedLineNumber The Line Number of Topic in the Content Specification.
	 * @param type The Topic Type for this topic (Concept, Task, etc...).
	 */
	public SpecTopic(String id, String title, int lineNumber, String specLine, int preProcessedLineNumber, String type) {
		super(lineNumber, specLine);
		if (id.matches(CSConstants.EXISTING_TOPIC_ID_REGEX)) {
			DBId = Integer.parseInt(id);
		}
		this.id = id;
		this.type = type;
		this.preProcessedLineNumber = preProcessedLineNumber;
		this.title = title;
	}
	
	/**
	 * Constructor
	 * 
	 * @param title The title of the Content Specification Topic.
	 * @param lineNumber The post processed Line Number of the topic.
	 * @param specLine The Content Specification Line that is used to create the Topic.
	 * @param preProcessedLineNumber The Line Number of Topic in the Content Specification.
	 * @param type The Topic Type for this topic (Concept, Task, etc...).
	 */
	public SpecTopic(String title, int lineNumber, String specLine, int preProcessedLineNumber, String type) {
		super(lineNumber, specLine);
		this.preProcessedLineNumber = preProcessedLineNumber;
		this.title = title;
		this.type = type;
	}
	
	/**
	 * Constructor
	 * 
	 * @param DBId The Database ID of a Topic that will be used to create a Content Specification Topic.
	 * @param title The Title of the Content Specification Topic.
	 */
	public SpecTopic(int DBId, String title) {
		super();
		this.id = Integer.toString(DBId);
		this.DBId = DBId;
		this.title = title;
	}
	
	// Start of the basic getter/setter methods for this Topic.
	
	public BaseTopicV1<? extends BaseTopicV1<?>> getTopic() {
		return topic;
	}

	public <T extends BaseTopicV1<T>> void setTopic(BaseTopicV1<T> topic) {
		this.topic = topic;
	}
	
	/**
	 * Set the ID for the Content Specification Topic.
	 * 
	 * @param id The Content Specification Topic ID.
	 */
	public void setId(String id) {
		// Set the DBId as well if it isn't a new id
		if (id.matches(CSConstants.EXISTING_TOPIC_ID_REGEX)) {
			DBId = Integer.parseInt(id);
		}
		this.id = id;
	}
	
	/**
	 * Get the ID for the Content Specification Topic.
	 * 
	 * @return The Topic ID.
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * Get the revision number of the topic that the Spec Topic represents. 
	 * 
	 * @return The revision number for the underlying topic or null if the 
	 * Spec Topic represents the latest copy.
	 */
	public Integer getRevision()
	{
		return revision;
	}

	/**
	 * Set the revision number for the underlying topic that the Spec Topic
	 * represents.
	 * 
	 * @param revision The underlying topic revision number or null if its 
	 * the latest revision.
	 */
	public void setRevision(final Integer revision)
	{
		this.revision = revision;
	}
	
	/**
	 * Gets the Content Specification Unique ID for the topic.
	 * 
	 * Note: The pre processed line number must be set to get the unique id.
	 * 
	 * @return The unique id.
	 */
	public String getUniqueId() {
		if (id.equals("N") || id.matches(CSConstants.DUPLICATE_TOPIC_ID_REGEX) || id.matches(CSConstants.CLONED_DUPLICATE_TOPIC_ID_REGEX) || id.matches(CSConstants.CLONED_TOPIC_ID_REGEX) || id.matches(CSConstants.EXISTING_TOPIC_ID_REGEX)) {
			return Integer.toString(preProcessedLineNumber) + "-" + id;
		} else {
			return id;
		}
	}
	
	/**
	 * Sets the Database ID for the Topic.
	 * 
	 * @param id The Database ID for the Topic.
	 */
	public void setDBId(int id) {
		DBId = id;
	}
	
	/**
	 * Get the database ID for the Content Specification Topic.
	 * 
	 * @return The Topics database ID.
	 */
	public int getDBId() {
		return DBId;
	}
	
	/**
	 * Gets the title of the topic.
	 * 
	 * @return The topics Title.
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title for the topic.
	 * 
	 * @param title The title for the topic.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * Set the Topic Type for the Content Specification Topic.
	 * 
	 * @param type The Topic Type (Concept, Task, etc...).
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * Gets the Content Specification Topic Type
	 * 
	 * @return The Topics Type.
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Gets the pre processed line number for the Content Specification Topic.
	 * 
	 * @return The pre processed line number or null if one wasn't set.
	 */
	public int getPreProcessedLineNumber() {
		return preProcessedLineNumber;
	}
	
	/**
	 * Gets the Target ID for the Content Specification Topic if one exists.
	 * 
	 * @return The Target ID or null if none exist.
	 */
	public String getTargetId() {
		return targetId;
	}

	/**
	 * Set the Target ID for the Content Specification Topic.
	 * 
	 * @param targetId The Target ID for the Topic.
	 */
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
	
	/**
	 * Add a relationship to the topic.
	 * 
	 * @param topic The topic that is to be related to.
	 * @param type The type of the relationship.
	 */
	public void addRelationshipToTopic(SpecTopic topic, RelationshipType type) {
		topicRelationships.add(new TopicRelationship(this, topic, type));
	}
	
	/**
	 * Add a relationship to the topic.
	 * 
	 * @param topic The topic that is to be related to.
	 * @param type The type of the relationship.
	 */
	public void addRelationshipToTarget(SpecTopic topic, RelationshipType type) {
		topicTargetRelationships.add(new TargetRelationship(this, topic, type));
	}
	
	/**
	 * Add a relationship to the topic.
	 * 
	 * @param topic The topic that is to be related to.
	 * @param type The type of the relationship.
	 */
	public void addRelationshipToTarget(Level level, RelationshipType type) {
		levelRelationships.add(new TargetRelationship(this, level, type));
	}
	
	// End of the basic getter/setter methods for this Topic.
	
	/**
	 *  Gets a list of previous relationships for the Topic.
	 */
	public List<Relationship> getPreviousRelationship() {
		ArrayList<Relationship> prevRelationships = new ArrayList<Relationship>();
		for (Relationship r: topicRelationships) {
			if (r.getType() == RelationshipType.PREVIOUS) {
				prevRelationships.add(r);
			}
		}
		for (Relationship r: topicTargetRelationships) {
			if (r.getType() == RelationshipType.PREVIOUS) {
				prevRelationships.add(r);
			}
		}
		return prevRelationships;
	}

	/**
	 * Gets a list of next relationships for the Topic.
	 */
	public List<Relationship> getNextRelationships() {
		ArrayList<Relationship> nextRelationships = new ArrayList<Relationship>();
		for (Relationship r: topicRelationships) {
			if (r.getType() == RelationshipType.NEXT) {
				nextRelationships.add(r);
			}
		}
		for (Relationship r: topicTargetRelationships) {
			if (r.getType() == RelationshipType.NEXT) {
				nextRelationships.add(r);
			}
		}
		return nextRelationships;
	}

	/**
	 * Gets a list of prerequisite relationships for the topic.
	 */
	public List<Relationship> getPrerequisiteRelationships() {
		ArrayList<Relationship> prerequisiteRelationships = new ArrayList<Relationship>();
		for (Relationship r: topicRelationships) {
			if (r.getType() == RelationshipType.PREREQUISITE) {
				prerequisiteRelationships.add(r);
			}
		}
		for (Relationship r: topicTargetRelationships) {
			if (r.getType() == RelationshipType.PREREQUISITE) {
				prerequisiteRelationships.add(r);
			}
		}
		for (Relationship r: levelRelationships) {
			if (r.getType() == RelationshipType.PREREQUISITE) {
				prerequisiteRelationships.add(r);
			}
		}
		return prerequisiteRelationships;
	}

	/**
	 * Gets a list of related relationships for the topic.
	 */
	public List<Relationship> getRelatedRelationships() {
		ArrayList<Relationship> relatedRelationships = new ArrayList<Relationship>();
		for (Relationship r: topicRelationships) {
			if (r.getType() == RelationshipType.RELATED) {
				relatedRelationships.add(r);
			}
		}
		for (Relationship r: topicTargetRelationships) {
			if (r.getType() == RelationshipType.RELATED) {
				relatedRelationships.add(r);
			}
		}
		for (Relationship r: levelRelationships) {
			if (r.getType() == RelationshipType.RELATED) {
				relatedRelationships.add(r);
			}
		}
		return relatedRelationships;
	}
	
	@Override
	public Level getParent() {
		return (Level) parent;
	}
	
	/**
	 * Sets the parent for the Content Specification Topic.
	 * 
	 * @param parent The Level that is the parent of this topic.
	 */
	protected void setParent(Level parent) {
		super.setParent(parent);
	}

	/**
	 * Checks to see if the topic is a new topic based on its ID.
	 * 
	 * @return True if the topic is a new Topic otherwise false.
	 */
	public boolean isTopicANewTopic() {
		return id.matches(CSConstants.NEW_TOPIC_ID_REGEX);
	}
	
	/**
	 * Checks to see if the topic is an existing topic based on its ID.
	 * 
	 * @return True if the topic is a existing Topic otherwise false.
	 */
	public boolean isTopicAnExistingTopic() {
		return id.matches(CSConstants.EXISTING_TOPIC_ID_REGEX);
	}

	/**
	 * Checks to see if the topic is a cloned topic based on its ID.
	 * 
	 * @return True if the topic is a cloned Topic otherwise false.
	 */
	public boolean isTopicAClonedTopic() {
		return id.matches(CSConstants.CLONED_TOPIC_ID_REGEX);
	}
	
	/**
	 * Checks to see if the topic is a duplicated topic based on its ID.
	 * 
	 * @return True if the topic is a duplicated Topic otherwise false.
	 */
	public boolean isTopicADuplicateTopic() {
		return id.matches(CSConstants.DUPLICATE_TOPIC_ID_REGEX);
	}
	
	/**
	 * Checks to see if the topic is a Duplicated Cloned topic based on its ID.
	 * 
	 * @return True if the topic is a Duplicated Cloned Topic otherwise false.
	 */
	public boolean isTopicAClonedDuplicateTopic() {
		return id.matches(CSConstants.CLONED_DUPLICATE_TOPIC_ID_REGEX);
	}
	
	/*
	 * Gets the list of Topic to Topic relationships where the main Topic matches the topic parameter.
	 * 
	 * @param topicId The topic object of the main topic to be found.
	 * @return An ArrayList of TopicRelationship's where the main topic matches the topic or an empty array if none are found.
	 */
	public List<TopicRelationship> getTopicRelationships() {
		ArrayList<TopicRelationship> relationships = new ArrayList<TopicRelationship>(topicRelationships);
		for (TargetRelationship relationship : topicTargetRelationships) {
            relationships.add(new TopicRelationship(relationship.getTopic(), (SpecTopic)relationship.getSecondaryElement(), relationship.getType()));
		}
		return relationships;
	}
	
	/**
	 * Gets the list of Topic to Level relationships where the Topic matches the topic parameter.
	 * 
	 * @return A List of LevelRelationship's where the Topic matches the topic or an empty array if none are found.
	 */
	public List<TargetRelationship> getLevelRelationships() {
		return levelRelationships;
	}
	
	public List<TopicRelationship> getRelatedTopicRelationships() {
        ArrayList<TopicRelationship> relationships = new ArrayList<TopicRelationship>();
        for (TopicRelationship relationship : topicRelationships) {
                if (relationship.getType() == RelationshipType.RELATED) {
                        relationships.add(relationship);
                }
        }
        for (TargetRelationship relationship : topicTargetRelationships) {
            if (relationship.getType() == RelationshipType.RELATED) {
            	relationships.add(new TopicRelationship(relationship.getTopic(), (SpecTopic)relationship.getSecondaryElement(), relationship.getType()));
            }
		}
        return relationships;
	}
	
	public List<TargetRelationship> getRelatedLevelRelationships() {
        ArrayList<TargetRelationship> relationships = new ArrayList<TargetRelationship>();
        for (TargetRelationship relationship : levelRelationships) {
                if (relationship.getType() == RelationshipType.RELATED) {
                        relationships.add(relationship);
                }
        }
        return relationships;
	}
	
	public List<TopicRelationship> getPrerequisiteTopicRelationships() {
        ArrayList<TopicRelationship> relationships = new ArrayList<TopicRelationship>();
        for (TopicRelationship relationship : topicRelationships) {
                if (relationship.getType() == RelationshipType.PREREQUISITE) {
                        relationships.add(relationship);
                }
        }
        for (TargetRelationship relationship : topicTargetRelationships) {
            if (relationship.getType() == RelationshipType.PREREQUISITE) {
            	relationships.add(new TopicRelationship(relationship.getTopic(), (SpecTopic)relationship.getSecondaryElement(), relationship.getType()));
            }
		}
        return relationships;
	}
	
	public List<TargetRelationship> getPrerequisiteLevelRelationships() {
        ArrayList<TargetRelationship> relationships = new ArrayList<TargetRelationship>();
        for (TargetRelationship relationship : levelRelationships) {
                if (relationship.getType() == RelationshipType.PREREQUISITE) {
                        relationships.add(relationship);
                }
        }
        return relationships;
	}
	
	public List<TopicRelationship> getNextTopicRelationships() {
		ArrayList<TopicRelationship> relationships = new ArrayList<TopicRelationship>();
		for (TopicRelationship relationship : topicRelationships) {
                if (relationship.getType() == RelationshipType.NEXT) {
                	relationships.add(relationship);
                }
        }
		for (TargetRelationship relationship : topicTargetRelationships) {
            if (relationship.getType() == RelationshipType.NEXT) {
            	relationships.add(new TopicRelationship(relationship.getTopic(), (SpecTopic)relationship.getSecondaryElement(), relationship.getType()));
            }
		}
        return relationships;
	}
	
	public List<TopicRelationship> getPrevTopicRelationships() {
		ArrayList<TopicRelationship> relationships = new ArrayList<TopicRelationship>();
        for (TopicRelationship relationship : topicRelationships) {
                if (relationship.getType() == RelationshipType.PREVIOUS) {
                    relationships.add(relationship);
                }
        }
        for (TargetRelationship relationship : topicTargetRelationships) {
            if (relationship.getType() == RelationshipType.PREVIOUS) {
            	relationships.add(new TopicRelationship(relationship.getTopic(), (SpecTopic)relationship.getSecondaryElement(), relationship.getType()));
            }
		}
        return relationships;
	}
	
	@Override
	public Integer getStep() {
		if (getParent() == null) return null;
		Integer previousNode = 0;
		
		// Get the position of the level in its parents nodes
		Integer nodePos = getParent().nodes.indexOf(this);
		
		// If the level isn't the first node then get the previous nodes step
		if (nodePos > 0) {
			Node node = getParent().nodes.get(nodePos - 1);
			previousNode = node.getStep();
			// If the add node is a level then add the number of nodes it contains
			if (node instanceof Level) {
				previousNode = (previousNode == null ? 0 : previousNode) + ((Level)node).getTotalNumberOfChildren();
			}
		// The node is the first item so use the parent levels step
		} else {
			previousNode = getParent().getStep();
		}
		// Make sure the previous nodes step isn't 0
		previousNode = previousNode == null ? 0 : previousNode;
		
		// Add one since we got the previous nodes step
		return previousNode + 1;
	}
	
	@Override
	public String getText()
	{
		String output = "";
		if (DBId == 0) {
			String options = getOptionsString();
			output += title + " [" + id + ", " + type + (options.equals("") ? "" : (", " + options)) +  "]";
		} else {
			output += title + " [" + id + (revision == null ? "" : (", rev: " + revision)) + "]";
		}
		if (!getRelatedRelationships().isEmpty()) {
			ArrayList<String> relatedIds = new ArrayList<String>();
			for (Relationship related: getRelatedRelationships()) {
				relatedIds.add(related.getSecondaryRelationshipTopicId());
			}
			output += " [R: " + StringUtilities.buildString(relatedIds.toArray(new String[0]), ", ") + "]";
		}
		if (!getPrerequisiteRelationships().isEmpty()) {
			ArrayList<String> relatedIds = new ArrayList<String>();
			for (Relationship related: getPrerequisiteRelationships()) {
				relatedIds.add(related.getSecondaryRelationshipTopicId());
			}
			output += " [P: " + StringUtilities.buildString(relatedIds.toArray(new String[0]), ", ") + "]";
		}
		setText(output);
		return output;
	}
	
	@Override
	public String toString() {
		String spacer = "";
		for (int i = 1; i < (parent != null ? getColumn() : 0); i++) {
			spacer += "  ";
		}
		return spacer + getText() + "\n";
	}

	@Override
	protected void removeParent() {
		getParent().removeChild(this);
		setParent(null);
	}
	
	/**
	 * Finds the closest node in the contents of a level
	 * 
	 * @param topic The node we need to find the closest match for
	 * @return
	 */
	public SpecTopic getClosestTopic(final SpecTopic topic, final boolean checkParentNode)
	{
		/*
		 * Check this topic to see if it is the topic we are looking for
		 */
		if (this == topic || this.getId().equals(topic.getId())) return this;
		
		/*
		 * If we still haven't found the closest node then check this
		 * nodes parents.
		 */
		if (getParent() != null)
			return getParent().getClosestTopic(topic, checkParentNode);
		
		return null;
	}
	
	public SpecTopic getClosestTopicByDBId(final Integer DBId, final boolean checkParentNode)
	{
		/*
		 * Check this topic to see if it is the topic we are looking for
		 */
		if (this.DBId == DBId) return this;
		
		/*
		 * If we still haven't found the closest node then check this
		 * nodes parents.
		 */
		if (getParent() != null)
			return getParent().getClosestTopicByDBId(DBId, checkParentNode);
		
		return null;
	}
	
	public String getUniqueLinkId(final boolean useFixedUrls)
	{
		final String topicXRefId;
		if (useFixedUrls)
			topicXRefId = topic.getXrefPropertyOrId(CommonConstants.FIXED_URL_PROP_TAG_ID);
		else
			topicXRefId = topic.getXRefID();
			
		return topicXRefId + (duplicateId == null ? "" : ("-" + duplicateId));
	}

	public String getDuplicateId() {
		return duplicateId;
	}

	public void setDuplicateId(final String duplicateId) {
		this.duplicateId = duplicateId;
	}

	public Document getXmlDocument() {
		return xmlDocument;
	}

	public void setXmlDocument(final Document xmlDocument) {
		this.xmlDocument = xmlDocument;
	}
}
