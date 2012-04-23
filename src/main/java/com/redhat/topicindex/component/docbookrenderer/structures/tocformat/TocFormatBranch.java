package com.redhat.topicindex.component.docbookrenderer.structures.tocformat;

import java.util.ArrayList;
import java.util.List;

import com.redhat.ecs.constants.CommonConstants;
import com.redhat.topicindex.component.docbookrenderer.structures.TopicErrorDatabase;
import com.redhat.topicindex.rest.entities.TagV1;
import com.redhat.topicindex.rest.entities.TopicV1;

public class TocFormatBranch
{
	/** defines the parent of this branch */
	private final TocFormatBranch parent;
	
	/**
	 * Defines the tag that this branch represents. Will be null for a top level
	 * branch
	 */
	private final TagV1 tag;

	/** Defines the tags that a child must have to exist as a subbranch */
	private final TagRequirements childTags;

	/**
	 * Defines the tags that a topic must have to be listed under this branch.
	 * This is null if no topics are to be listed under this branch.
	 */
	private final TagRequirements displayTags;

	/** Holds any sub branches */
	private final List<TocFormatBranch> children = new ArrayList<TocFormatBranch>();

	/** Holds any topics that should be listed under this branch */
	private final List<TopicV1> topics = new ArrayList<TopicV1>();

	public List<TopicV1> getTopics()
	{
		return topics;
	}

	public TocFormatBranch getParent()
	{
		return parent;
	}

	public List<TocFormatBranch> getChildren()
	{
		return children;
	}

	public TagRequirements getDisplayTags()
	{
		return displayTags;
	}

	public TagRequirements getTagCategoryId()
	{
		return childTags;
	}

	public TagV1 getTagId()
	{
		return tag;
	}
	
	public TocFormatBranch()
	{
		this.parent = null;
		this.tag = null;
		this.childTags = new TagRequirements();
		this.displayTags = new TagRequirements();
	}

	public TocFormatBranch(final TagV1 tag, final TocFormatBranch parent, final TagRequirements childTags, final TagRequirements displayTags)
	{
		this.tag = tag;
		this.childTags = childTags;
		this.displayTags = displayTags;
		this.parent = parent;
	}
	
	public void getDisplayTagsWithParent(final TagRequirements requirements)
	{
		requirements.merge(displayTags);
		requirements.merge(childTags);
		if (parent != null)
			parent.getDisplayTagsWithParent(requirements);
	}
	
	public String getTOCBranchID()
	{
		final StringBuilder retValue = new StringBuilder();
		if (parent != null)
			retValue.append(parent.getTOCBranchID());
		retValue.append("-");
		retValue.append(this.tag.getId());
		return retValue.toString();
	}
	
	public String buildDocbook(final boolean useFixedUrls, final TopicErrorDatabase errorDatabase)
	{
		final StringBuilder docbook = new StringBuilder();
		
		docbook.append(this.parent == null ? "<chapter>" : "<section>");
		docbook.append("<title>");
		docbook.append(this.getTagId().getName());
		docbook.append("</title>");
		
		/* append any child branches */
		for (final TocFormatBranch child :  children)
			docbook.append(child.buildDocbook(useFixedUrls, errorDatabase));
		
		/* Add an xref to each topic that appears under this branch */
		for (final TopicV1 topic : topics)
		{
			String fileName = "";
			if (useFixedUrls)
			{
				fileName = topic.getXrefPropertyOrId(CommonConstants.FIXED_URL_PROP_TAG_ID) + this.getTOCBranchID() + ".xml";
			}
			else
			{
				fileName = "Topic" + topic.getId() + this.getTOCBranchID() + ".xml";
			}
			
			docbook.append("<xi:include href=\"" + fileName + "\" xmlns:xi=\"http://www.w3.org/2001/XInclude\" />\n");
		}
		
		docbook.append(this.parent == null ? "</chapter>" : "</section>");
		
		return docbook.toString();
	}
	
	public int getTopicCount()
	{
		int retValue = this.children.size();
		
		for (final TocFormatBranch child :  children)
			retValue += child.getTopicCount();
		
		return retValue;
	}
	
	public List<TopicV1> getAllTopics()
	{
		final List<TopicV1> retValue = new ArrayList<TopicV1>();
		
		retValue.addAll(this.topics);
		
		for (final TocFormatBranch child :  children)
			retValue.addAll(child.getAllTopics());
		
		return retValue;
	}
	
	public boolean isInToc(final Integer topicId)
	{
		final List<TopicV1> topics = getAllTopics();
		for (final TopicV1 topic : topics)
			if (topic.getId().equals(topicId))
				return true;
		return false;
	}
	
	/**
	 * When inserting an xref to a topic, we need to find the closest topic to link to. This is because a 
	 * topic can appear multiple times in a TOC, and therefore multiple times in a document.
	 * 
	 * @param topicId The topic to find
	 * @param referenceTopic The topic to use a search reference point
	 * @return the xref postfix of the toc that is applied to the topic
	 */
	public String getClosestTopicXrefPostfix(final Integer topicId, final TopicV1 referenceTopic)
	{
		TocFormatBranch branch = this.getBranchThatContainsTopic(referenceTopic);
		
		while (branch != null)
		{
			/* Search that branch first */
			final TopicV1 topicInBranch = branch.getTopicInBranchAndChildren(topicId);
			if (topicInBranch != null)
				return branch.getTOCBranchID();
			
			/* go up to the parent and try again */
			branch = branch.getParent();
		}
		
		return null;
	}
	
	public TocFormatBranch getBranchThatContainsTopic(final TopicV1 topic)
	{
		if (this.children.contains(topic))
			return this;
		for (final TocFormatBranch child : children)
			if (child.getBranchThatContainsTopic(topic) != null)
				return child;
		return null;
	}
	
	public TopicV1 getTopicInBranchAndChildren(final Integer topicId)
	{
		for (final TopicV1 topic : this.topics)
			if (topicId.equals(topic.getId()))
				return topic;
		
		for (final TocFormatBranch child : children)
		{
			final TopicV1 retValue = child.getTopicInBranchAndChildren(topicId);
			if (retValue != null)
				return retValue;
		}
		
		return null;
	}
}
