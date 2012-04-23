package com.redhat.topicindex.component.docbookrenderer.structures.tocformat;

import java.util.ArrayList;
import java.util.List;

import com.redhat.ecs.constants.CommonConstants;
import com.redhat.topicindex.component.docbookrenderer.structures.TopicErrorDatabase;
import com.redhat.topicindex.rest.entities.PropertyTagV1;
import com.redhat.topicindex.rest.entities.TagV1;
import com.redhat.topicindex.rest.entities.TopicV1;

public class TocFormatBranch
{
	private TocFormatBranch parent;
	
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

	public void setParent(TocFormatBranch parent)
	{
		this.parent = parent;
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

	public TocFormatBranch(final TagV1 tag, final TagRequirements childTags, final TagRequirements displayTags)
	{
		this.tag = tag;
		this.childTags = childTags;
		this.displayTags = displayTags;
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
		if (retValue.length() != 0)
			retValue.append("-");
		retValue.append(this.tag.getId());
		return retValue.toString();
	}
	
	public String buildDocbook(final boolean useFixedUrls, final TopicErrorDatabase errorDatabase)
	{
		final StringBuilder docbook = new StringBuilder();
		docbook.append("<section>");
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
				final PropertyTagV1 propTag = topic.getProperty(CommonConstants.FIXED_URL_PROP_TAG_ID);
				if (propTag != null)
				{
					fileName = propTag.getValue() + ".xml";
				}
				else
				{
					errorDatabase.addError(topic, "Topic does not have the fixed url property tag.");
					fileName = "Topic" + topic.getId() + ".xml";
				}
			}
			else
			{
				fileName = "Topic" + topic.getId() + ".xml";
			}
			
			docbook.append("<xi:include href=\"" + fileName + "\" xmlns:xi=\"http://www.w3.org/2001/XInclude\" />\n");
		}
		
		docbook.append("</section>");
		
		return docbook.toString();
	}
}
