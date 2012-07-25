package com.redhat.contentspec;

/**
 * An object to store the contents of a Topic in a Content Specification. It stores the topics name, sequential step number, database ID, unique processed ID,
 * description, an array of urls that relate to the topic and a list of tags. 
 * 
 * @author lnewson
 */

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;

import com.redhat.contentspec.constants.CSConstants;
import com.redhat.contentspec.entities.TargetRelationship;
import com.redhat.contentspec.entities.Relationship;
import com.redhat.contentspec.entities.TopicRelationship;
import com.redhat.contentspec.enums.RelationshipType;
import com.redhat.ecs.commonutils.StringUtilities;
import com.redhat.ecs.constants.CommonConstants;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.ComponentTopicV1;
import com.redhat.topicindex.rest.entities.ComponentTranslatedTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTBaseTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTTranslatedTopicV1;

public class SpecTopic extends SpecNode
{
	private String id;
	private int DBId = 0;
	private String type;
	private List<TopicRelationship> topicRelationships = new ArrayList<TopicRelationship>();
	private List<TargetRelationship> topicTargetRelationships = new ArrayList<TargetRelationship>();
	private List<TargetRelationship> levelRelationships = new ArrayList<TargetRelationship>();
	private List<Relationship> relationships = new LinkedList<Relationship>();
	private String targetId = null;
	private String title = null;
	private String duplicateId = null;
	private RESTBaseTopicV1<?, ?> topic = null;
	private Document xmlDocument = null;
	private Integer revision = null;

	/**
	 * Constructor
	 * 
	 * @param id
	 *            The ID for the Content Specification Topic (N, N<ID>, C<ID>, etc...)
	 * @param title
	 *            The title of the Content Specification Topic.
	 * @param lineNumber
	 *            The post processed Line Number of the topic.
	 * @param specLine
	 *            The Content Specification Line that is used to create the Topic.
	 * @param preProcessedLineNumber
	 *            The Line Number of Topic in the Content Specification.
	 * @param type
	 *            The Topic Type for this topic (Concept, Task, etc...).
	 */
	public SpecTopic(final String id, final String title, final int lineNumber, final String specLine, final String type)
	{
		super(lineNumber, specLine);
		if (id.matches(CSConstants.EXISTING_TOPIC_ID_REGEX))
		{
			DBId = Integer.parseInt(id);
		}
		this.id = id;
		this.type = type;
		this.title = title;
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 *            The title of the Content Specification Topic.
	 * @param lineNumber
	 *            The post processed Line Number of the topic.
	 * @param specLine
	 *            The Content Specification Line that is used to create the Topic.
	 * @param preProcessedLineNumber
	 *            The Line Number of Topic in the Content Specification.
	 * @param type
	 *            The Topic Type for this topic (Concept, Task, etc...).
	 */
	public SpecTopic(final String title, final int lineNumber, final String specLine, final String type)
	{
		super(lineNumber, specLine);
		this.title = title;
		this.type = type;
	}

	/**
	 * Constructor
	 * 
	 * @param DBId
	 *            The Database ID of a Topic that will be used to create a Content Specification Topic.
	 * @param title
	 *            The Title of the Content Specification Topic.
	 */
	public SpecTopic(int DBId, String title)
	{
		super();
		this.id = Integer.toString(DBId);
		this.DBId = DBId;
		this.title = title;
	}

	// Start of the basic getter/setter methods for this Topic.

	/**
	 * Get the underlying topic that this Spec Topic represents.
	 * 
	 * @return The underlying topic if it has been set otherwise null.
	 */
	public RESTBaseTopicV1<?, ?> getTopic()
	{
		return topic;
	}

	/**
	 * Set the underlying topic that this spec topic represents.
	 * 
	 * @param topic The underlying topic.
	 */
	public <T extends RESTBaseTopicV1<T, U>, U extends BaseRestCollectionV1<T, U>> void setTopic(final RESTBaseTopicV1<T, U> topic)
	{
		this.topic = topic;
	}

	/**
	 * Set the ID for the Content Specification Topic.
	 * 
	 * @param id
	 *            The Content Specification Topic ID.
	 */
	public void setId(final String id)
	{
		// Set the DBId as well if it isn't a new id
		if (id.matches(CSConstants.EXISTING_TOPIC_ID_REGEX))
		{
			DBId = Integer.parseInt(id);
		}
		this.id = id;
	}

	/**
	 * Get the ID for the Content Specification Topic.
	 * 
	 * @return The Topic ID.
	 */
	public String getId()
	{
		return id;
	}
	
	/**
	 * Gets the Content Specification Unique ID for the topic.
	 * 
	 * Note: The pre processed line number must be set to get the unique id.
	 * 
	 * @return The unique id.
	 */
	public String getUniqueId()
	{
		if (id.equals("N") || id.matches(CSConstants.DUPLICATE_TOPIC_ID_REGEX) || id.matches(CSConstants.CLONED_DUPLICATE_TOPIC_ID_REGEX) || id.matches(CSConstants.CLONED_TOPIC_ID_REGEX) || id.matches(CSConstants.EXISTING_TOPIC_ID_REGEX))
		{
			return Integer.toString(getLineNumber()) + "-" + id;
		}
		else
		{
			return id;
		}
	}

	/**
	 * Sets the Database ID for the Topic.
	 * 
	 * @param id
	 *            The Database ID for the Topic.
	 */
	public void setDBId(int id)
	{
		DBId = id;
	}

	/**
	 * Get the database ID for the Content Specification Topic.
	 * 
	 * @return The Topics database ID.
	 */
	public int getDBId()
	{
		return DBId;
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
	 * Gets the title of the topic.
	 * 
	 * @return The topics Title.
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * Sets the title for the topic.
	 * 
	 * @param title
	 *            The title for the topic.
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * Set the Topic Type for the Content Specification Topic.
	 * 
	 * @param type
	 *            The Topic Type (Concept, Task, etc...).
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * Gets the Content Specification Topic Type
	 * 
	 * @return The Topics Type.
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Gets the Target ID for the Content Specification Topic if one exists.
	 * 
	 * @return The Target ID or null if none exist.
	 */
	public String getTargetId()
	{
		return targetId;
	}

	/**
	 * Set the Target ID for the Content Specification Topic.
	 * 
	 * @param targetId The Target ID for the Topic.
	 */
	public void setTargetId(final String targetId)
	{
		this.targetId = targetId;
	}

	/**
	 * Add a relationship to the topic.
	 * 
	 * @param topic The topic that is to be related to.
	 * @param type The type of the relationship.
	 */
	public void addRelationshipToTopic(final SpecTopic topic, final RelationshipType type)
	{
		final TopicRelationship relationship = new TopicRelationship(this, topic, type);
		topicRelationships.add(relationship);
		relationships.add(relationship);
	}
	
	/**
	 * Add a relationship to the topic.
	 * 
	 * @param topic The topic that is to be related to.
	 * @param type The type of the relationship.
	 * @param title The title of the topic to be related to.
	 */
	public void addRelationshipToTopic(final SpecTopic topic, final RelationshipType type, final String title)
	{
		final TopicRelationship relationship = new TopicRelationship(this, topic, type, title); 
		topicRelationships.add(relationship);
		relationships.add(relationship);
	}

	/**
	 * Add a relationship to the topic.
	 * 
	 * @param topic The topic that is to be related to.
	 * @param type The type of the relationship.
	 */
	public void addRelationshipToTarget(final SpecTopic topic, final RelationshipType type)
	{
		final TargetRelationship relationship = new TargetRelationship(this, topic, type);
		topicTargetRelationships.add(relationship);
		relationships.add(relationship);
	}
	
	/**
	 * Add a relationship to the topic.
	 * 
	 * @param topic The topic that is to be related to.
	 * @param type The type of the relationship.
	 * @param title The title of the topic to be related to.
	 */
	public void addRelationshipToTarget(final SpecTopic topic, final RelationshipType type, final String title)
	{
		final TargetRelationship relationship = new TargetRelationship(this, topic, type, title);
		topicTargetRelationships.add(relationship);
		relationships.add(relationship);
	}

	/**
	 * Add a relationship to the topic.
	 * 
	 * @param topic The topic that is to be related to.
	 * @param type The type of the relationship.
	 */
	public void addRelationshipToTarget(final Level level, final RelationshipType type)
	{
		final TargetRelationship relationship = new TargetRelationship(this, level, type);
		levelRelationships.add(relationship);
		relationships.add(relationship);
	}
	
	/**
	 * Add a relationship to the topic.
	 * 
	 * @param topic The topic that is to be related to.
	 * @param type The type of the relationship.
	 * @param title The title of the topic to be related to.
	 */
	public void addRelationshipToTarget(final Level level, final RelationshipType type, final String title)
	{
		final TargetRelationship relationship = new TargetRelationship(this, level, type, title);
		levelRelationships.add(relationship);
		relationships.add(relationship);
	}

	// End of the basic getter/setter methods for this Topic.

	/**
	 * Gets a list of previous relationships for the Topic.
	 */
	public List<Relationship> getPreviousRelationship()
	{
		final List<Relationship> prevRelationships = new LinkedList<Relationship>();
		for (final Relationship r : relationships)
		{
			if (r.getType() == RelationshipType.PREVIOUS)
			{
				prevRelationships.add(r);
			}
		}
		return prevRelationships;
	}

	/**
	 * Gets a list of next relationships for the Topic.
	 */
	public List<Relationship> getNextRelationships()
	{
		final List<Relationship> nextRelationships = new LinkedList<Relationship>();
		for (final Relationship r : relationships)
		{
			if (r.getType() == RelationshipType.NEXT)
			{
				nextRelationships.add(r);
			}
		}
		return nextRelationships;
	}

	/**
	 * Gets a list of prerequisite relationships for the topic.
	 */
	public List<Relationship> getPrerequisiteRelationships()
	{
		final List<Relationship> prerequisiteRelationships = new LinkedList<Relationship>();
		for (final Relationship r : relationships)
		{
			if (r.getType() == RelationshipType.PREREQUISITE)
			{
				prerequisiteRelationships.add(r);
			}
		}
		return prerequisiteRelationships;
	}

	/**
	 * Gets a list of related relationships for the topic.
	 */
	public List<Relationship> getRelatedRelationships()
	{
		final List<Relationship> relatedRelationships = new LinkedList<Relationship>();
		for (final Relationship r : relationships)
		{
			if (r.getType() == RelationshipType.RELATED)
			{
				relatedRelationships.add(r);
			}
		}
		return relatedRelationships;
	}
	
	/**
	 * Gets a list of link-list relationships for the topic.
	 */
	public List<Relationship> getLinkListRelationships()
	{
		final List<Relationship> linkListRelationships = new LinkedList<Relationship>();
		for (final Relationship r : relationships)
		{
			if (r.getType() == RelationshipType.LINKLIST)
			{
				linkListRelationships.add(r);
			}
		}
		return linkListRelationships;
	}

	@Override
	public Level getParent()
	{
		return (Level) parent;
	}

	/**
	 * Sets the parent for the Content Specification Topic.
	 * 
	 * @param parent
	 *            The Level that is the parent of this topic.
	 */
	protected void setParent(Level parent)
	{
		super.setParent(parent);
	}

	/**
	 * Checks to see if the topic is a new topic based on its ID.
	 * 
	 * @return True if the topic is a new Topic otherwise false.
	 */
	public boolean isTopicANewTopic()
	{
		return id.matches(CSConstants.NEW_TOPIC_ID_REGEX);
	}

	/**
	 * Checks to see if the topic is an existing topic based on its ID.
	 * 
	 * @return True if the topic is a existing Topic otherwise false.
	 */
	public boolean isTopicAnExistingTopic()
	{
		return id.matches(CSConstants.EXISTING_TOPIC_ID_REGEX);
	}

	/**
	 * Checks to see if the topic is a cloned topic based on its ID.
	 * 
	 * @return True if the topic is a cloned Topic otherwise false.
	 */
	public boolean isTopicAClonedTopic()
	{
		return id.matches(CSConstants.CLONED_TOPIC_ID_REGEX);
	}

	/**
	 * Checks to see if the topic is a duplicated topic based on its ID.
	 * 
	 * @return True if the topic is a duplicated Topic otherwise false.
	 */
	public boolean isTopicADuplicateTopic()
	{
		return id.matches(CSConstants.DUPLICATE_TOPIC_ID_REGEX);
	}

	/**
	 * Checks to see if the topic is a Duplicated Cloned topic based on its ID.
	 * 
	 * @return True if the topic is a Duplicated Cloned Topic otherwise false.
	 */
	public boolean isTopicAClonedDuplicateTopic()
	{
		return id.matches(CSConstants.CLONED_DUPLICATE_TOPIC_ID_REGEX);
	}

	/**
	 * Gets the list of Topic to Topic relationships where the main Topic matches the topic parameter.
	 * 
	 * @param topicId The topic object of the main topic to be found.
	 * 
	 * @return An ArrayList of TopicRelationship's where the main topic matches the topic or an empty array if none are found.
	 */
	public List<TopicRelationship> getTopicRelationships()
	{
		ArrayList<TopicRelationship> relationships = new ArrayList<TopicRelationship>(topicRelationships);
		for (final TargetRelationship relationship : topicTargetRelationships)
		{
			relationships.add(new TopicRelationship(relationship.getTopic(), (SpecTopic) relationship.getSecondaryElement(), relationship.getType()));
		}
		return relationships;
	}

	/**
	 * Gets the list of Topic to Level relationships where the Topic matches the topic parameter.
	 * 
	 * @return A List of LevelRelationship's where the Topic matches the topic or an empty array if none are found.
	 */
	public List<TargetRelationship> getLevelRelationships()
	{
		return levelRelationships;
	}

	/**
	 * Gets the list of Topic Relationships for this topic whose type is "RELATED".
	 * 
	 * @return A list of related topic relationships
	 */
	public List<TopicRelationship> getRelatedTopicRelationships()
	{
		final ArrayList<TopicRelationship> relationships = new ArrayList<TopicRelationship>();
		/* Check the topic to topic relationships for related relationships */
		for (final TopicRelationship relationship : topicRelationships)
		{
			if (relationship.getType() == RelationshipType.RELATED)
			{
				relationships.add(relationship);
			}
		}
		/* Check the topic to target relationships for related relationships */
		for (final TargetRelationship relationship : topicTargetRelationships)
		{
			if (relationship.getType() == RelationshipType.RELATED)
			{
				relationships.add(new TopicRelationship(relationship.getTopic(), (SpecTopic) relationship.getSecondaryElement(), relationship.getType()));
			}
		}
		return relationships;
	}

	/**
	 * Gets the list of Level Relationships for this topic whose type is "RELATED".
	 * 
	 * @return A list of related level relationships
	 */
	public List<TargetRelationship> getRelatedLevelRelationships()
	{
		final ArrayList<TargetRelationship> relationships = new ArrayList<TargetRelationship>();
		for (final TargetRelationship relationship : levelRelationships)
		{
			if (relationship.getType() == RelationshipType.RELATED)
			{
				relationships.add(relationship);
			}
		}
		return relationships;
	}
	
	/**
	 * Gets the list of Topic Relationships for this topic whose type is "PREREQUISITE".
	 * 
	 * @return A list of prerequisite topic relationships
	 */
	public List<TopicRelationship> getPrerequisiteTopicRelationships()
	{
		final ArrayList<TopicRelationship> relationships = new ArrayList<TopicRelationship>();
		for (final TopicRelationship relationship : topicRelationships)
		{
			if (relationship.getType() == RelationshipType.PREREQUISITE)
			{
				relationships.add(relationship);
			}
		}
		for (final TargetRelationship relationship : topicTargetRelationships)
		{
			if (relationship.getType() == RelationshipType.PREREQUISITE)
			{
				relationships.add(new TopicRelationship(relationship.getTopic(), (SpecTopic) relationship.getSecondaryElement(), relationship.getType()));
			}
		}
		return relationships;
	}

	/**
	 * Gets the list of Level Relationships for this topic whose type is "PREREQUISITE".
	 * 
	 * @return A list of prerequisite level relationships
	 */
	public List<TargetRelationship> getPrerequisiteLevelRelationships()
	{
		final ArrayList<TargetRelationship> relationships = new ArrayList<TargetRelationship>();
		for (final TargetRelationship relationship : levelRelationships)
		{
			if (relationship.getType() == RelationshipType.PREREQUISITE)
			{
				relationships.add(relationship);
			}
		}
		return relationships;
	}
	
	/**
	 * Gets the list of Topic Relationships for this topic whose type is "LINKLIST".
	 * 
	 * @return A list of link list topic relationships
	 */
	public List<TopicRelationship> getLinkListTopicRelationships()
	{
		final ArrayList<TopicRelationship> relationships = new ArrayList<TopicRelationship>();
		for (final TopicRelationship relationship : topicRelationships)
		{
			if (relationship.getType() == RelationshipType.LINKLIST)
			{
				relationships.add(relationship);
			}
		}
		for (final TargetRelationship relationship : topicTargetRelationships)
		{
			if (relationship.getType() == RelationshipType.LINKLIST)
			{
				relationships.add(new TopicRelationship(relationship.getTopic(), (SpecTopic) relationship.getSecondaryElement(), relationship.getType()));
			}
		}
		return relationships;
	}

	/**
	 * Gets the list of Level Relationships for this topic whose type is "LINKLIST".
	 * 
	 * @return A list of link list level relationships
	 */
	public List<TargetRelationship> getLinkListLevelRelationships()
	{
		final ArrayList<TargetRelationship> relationships = new ArrayList<TargetRelationship>();
		for (final TargetRelationship relationship : levelRelationships)
		{
			if (relationship.getType() == RelationshipType.LINKLIST)
			{
				relationships.add(relationship);
			}
		}
		return relationships;
	}

	/**
	 * Gets the list of Topic Relationships for this topic whose type is "NEXT".
	 * 
	 * @return A list of next topic relationships
	 */
	public List<TopicRelationship> getNextTopicRelationships()
	{
		ArrayList<TopicRelationship> relationships = new ArrayList<TopicRelationship>();
		for (TopicRelationship relationship : topicRelationships)
		{
			if (relationship.getType() == RelationshipType.NEXT)
			{
				relationships.add(relationship);
			}
		}
		for (TargetRelationship relationship : topicTargetRelationships)
		{
			if (relationship.getType() == RelationshipType.NEXT)
			{
				relationships.add(new TopicRelationship(relationship.getTopic(), (SpecTopic) relationship.getSecondaryElement(), relationship.getType()));
			}
		}
		return relationships;
	}
	
	/**
	 * Gets the list of Topic Relationships for this topic whose type is "PREVIOUS".
	 * 
	 * @return A list of previous topic relationships
	 */
	public List<TopicRelationship> getPrevTopicRelationships()
	{
		ArrayList<TopicRelationship> relationships = new ArrayList<TopicRelationship>();
		for (TopicRelationship relationship : topicRelationships)
		{
			if (relationship.getType() == RelationshipType.PREVIOUS)
			{
				relationships.add(relationship);
			}
		}
		for (TargetRelationship relationship : topicTargetRelationships)
		{
			if (relationship.getType() == RelationshipType.PREVIOUS)
			{
				relationships.add(new TopicRelationship(relationship.getTopic(), (SpecTopic) relationship.getSecondaryElement(), relationship.getType()));
			}
		}
		return relationships;
	}

	@Override
	public Integer getStep()
	{
		if (getParent() == null)
			return null;
		Integer previousNode = 0;

		// Get the position of the level in its parents nodes
		Integer nodePos = getParent().nodes.indexOf(this);

		// If the level isn't the first node then get the previous nodes step
		if (nodePos > 0)
		{
			Node node = getParent().nodes.get(nodePos - 1);
			previousNode = node.getStep();
			// If the add node is a level then add the number of nodes it contains
			if (node instanceof Level)
			{
				previousNode = (previousNode == null ? 0 : previousNode) + ((Level) node).getTotalNumberOfChildren();
			}
			// The node is the first item so use the parent levels step
		}
		else
		{
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
		final StringBuilder output = new StringBuilder();
		if (this.isTopicANewTopic())
		{
			final String options = getOptionsString();
			output.append((title == null ? "" : title) + " [" + id + ", " + type + (options.equals("") ? "" : (", " + options)) + "]");
		}
		else
		{
			final String options = getOptionsString();
			output.append((title == null ? "" : title) + " [" + id + (revision == null ? "" : (", rev: " + revision)) + (options.equals("") ? "" : (", " + options)) + "]");
		}
		
		if (targetId != null && !((parent instanceof Process) && targetId.matches("^T" + this.getLineNumber() + "0[0-9]+$")))
		{
			output.append(" [" + targetId + "]");
		}
		
		if (!getRelatedRelationships().isEmpty())
		{
			final List<String> relatedIds = new ArrayList<String>();
			for (final Relationship related : getRelatedRelationships())
			{
				relatedIds.add(related.getSecondaryRelationshipTopicId());
			}
			output.append(" [R: " + StringUtilities.buildString(relatedIds.toArray(new String[0]), ", ") + "]");
		}
		
		if (!getPrerequisiteRelationships().isEmpty())
		{
			final List<String> relatedIds = new ArrayList<String>();
			for (final Relationship related : getPrerequisiteRelationships())
			{
				relatedIds.add(related.getSecondaryRelationshipTopicId());
			}
			output.append(" [P: " + StringUtilities.buildString(relatedIds.toArray(new String[0]), ", ") + "]");
		}
		
		setText(output.toString());
		return text;
	}

	@Override
	public String toString()
	{
		final StringBuilder spacer = new StringBuilder();
		final int indentationSize = parent != null ? getColumn() : 0;
		for (int i = 1; i < indentationSize; i++)
		{
			spacer.append("  ");
		}
		return spacer + getText() + "\n";
	}

	@Override
	protected void removeParent()
	{
		getParent().removeChild(this);
		setParent(null);
	}

	/**
	 * Finds the closest node in the contents of a level
	 * 
	 * @param topic
	 *            The node we need to find the closest match for
	 * @return
	 */
	public SpecTopic getClosestTopic(final SpecTopic topic, final boolean checkParentNode)
	{
		/*
		 * Check this topic to see if it is the topic we are looking for
		 */
		if (this == topic || this.getId().equals(topic.getId()))
			return this;

		/*
		 * If we still haven't found the closest node then check this nodes parents.
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
		if (this.DBId == DBId)
			return this;

		/*
		 * If we still haven't found the closest node then check this nodes parents.
		 */
		if (getParent() != null)
			return getParent().getClosestTopicByDBId(DBId, checkParentNode);

		return null;
	}

	@Override
	public String getUniqueLinkId(final boolean useFixedUrls)
	{
		final String topicXRefId;
		if (topic instanceof RESTTranslatedTopicV1)
		{
			if (useFixedUrls)
				topicXRefId = ComponentTranslatedTopicV1.returnXrefPropertyOrId((RESTTranslatedTopicV1) topic, CommonConstants.FIXED_URL_PROP_TAG_ID);
			else
			{
				topicXRefId = ComponentTranslatedTopicV1.returnXRefID((RESTTranslatedTopicV1) topic);
			}
		}
		else
		{
			if (useFixedUrls)
				topicXRefId = ComponentTopicV1.returnXrefPropertyOrId((RESTTopicV1) topic, CommonConstants.FIXED_URL_PROP_TAG_ID);
			else
			{
				topicXRefId = ComponentTopicV1.returnXRefID((RESTTopicV1) topic);
			}
		}

		return topicXRefId + (duplicateId == null ? "" : ("-" + duplicateId));
	}

	public String getDuplicateId()
	{
		return duplicateId;
	}

	public void setDuplicateId(final String duplicateId)
	{
		this.duplicateId = duplicateId;
	}

	public Document getXmlDocument()
	{
		return xmlDocument;
	}

	public void setXmlDocument(final Document xmlDocument)
	{
		this.xmlDocument = xmlDocument;
	}
}
