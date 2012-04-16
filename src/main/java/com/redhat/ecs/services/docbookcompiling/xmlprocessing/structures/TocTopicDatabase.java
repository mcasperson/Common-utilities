package com.redhat.ecs.services.docbookcompiling.xmlprocessing.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redhat.ecs.commonutils.CollectionUtilities;
import com.redhat.topicindex.rest.entities.TagV1;
import com.redhat.topicindex.rest.entities.TopicV1;

/**
 * This class represents all the topics that will go into a docbook build, along
 * with some function to retrieve topics based on a set of tags to match or
 * exclude.
 */
public class TocTopicDatabase
{
	private Map<TopicV1, TopicProcessingData> topics = new HashMap<TopicV1, TopicProcessingData>();

	public void addTopic(final TopicV1 topic)
	{
		if (!containsTopic(topic))
			topics.put(topic, new TopicProcessingData());
	}
	
	public TopicProcessingData getTopicProcessingData(final TopicV1 topic)
	{
		if (containsTopic(topic))
			return topics.get(topic);
		return null;
	}

	public boolean containsTopic(final TopicV1 topic)
	{
		return topics.keySet().contains(topic);
	}

	public boolean containsTopic(final Integer topicId)
	{
		return getTopic(topicId) != null;
	}

	public TopicV1 getTopic(final Integer topicId)
	{
		for (final TopicV1 topic : topics.keySet())
			if (topic.getId().equals(topicId))
				return topic;

		return null;
	}

	public boolean containsTopicsWithTag(final TagV1 tag)
	{
		return getMatchingTopicsFromTag(tag).size() != 0;
	}

	public boolean containsTopicsWithTag(final Integer tag)
	{
		return getMatchingTopicsFromInteger(tag).size() != 0;
	}

	public List<TagV1> getTagsFromCategories(final List<Integer> categoryIds)
	{
		final List<TagV1> retValue = new ArrayList<TagV1>();

		for (final TopicV1 topic : topics.keySet())
		{
			final List<TagV1> topicTags = topic.getTagsInCategoriesByID(categoryIds); 			
			CollectionUtilities.addAllThatDontExist(topicTags, retValue);
		}

		return retValue;
	}

	public List<TopicV1> getMatchingTopicsFromInteger(final List<Integer> matchingTags, final List<Integer> excludeTags, final boolean haveOnlyMatchingTags, final boolean landingPagesOnly)
	{
		assert matchingTags != null : "The matchingTags parameter can not be null";
		assert excludeTags != null : "The excludeTags parameter can not be null";

		final List<TopicV1> topicList = new ArrayList<TopicV1>();

		for (final TopicV1 topic : topics.keySet())
		{
			/* landing pages ahev negative topic ids */
			if (landingPagesOnly && topic.getId() >= 0)
				continue;
			
			/* check to see if the topic has only the matching tags */
			if (haveOnlyMatchingTags && topic.getTags().getItems().size() != matchingTags.size())
				continue;
		
			/* check for matching tags */
			boolean foundMatchingTag = true;
			for (final Integer matchingTag : matchingTags)
			{
				if (!topic.isTaggedWith(matchingTag))
				{
					foundMatchingTag = false;
					break;
				}
			}
			if (!foundMatchingTag)
				continue;

			/* check for excluded tags */
			boolean foundExclusionTag = false;
			for (final Integer excludeTag : excludeTags)
			{
				if (topic.isTaggedWith(excludeTag))
				{
					foundExclusionTag = true;
					break;
				}
			}
			if (foundExclusionTag)
				continue;

			topicList.add(topic);
		}
		
		/* post conditions */
		if (landingPagesOnly)
			assert (topicList.size() == 0 || topicList.size() == 1) : "Found 2 or more landing pages, when 0 or 1 was expected";

		return topicList;
	}

	public List<TopicV1> getMatchingTopicsFromInteger(final Integer matchingTag, final List<Integer> excludeTags, final boolean haveOnlyMatchingTags)
	{
		return getMatchingTopicsFromInteger(CollectionUtilities.toArrayList(matchingTag), excludeTags, haveOnlyMatchingTags, false);
	}

	public List<TopicV1> getMatchingTopicsFromInteger(final Integer matchingTag, final Integer excludeTag, final boolean haveOnlyMatchingTags)
	{
		return getMatchingTopicsFromInteger(matchingTag, CollectionUtilities.toArrayList(excludeTag), haveOnlyMatchingTags);
	}

	public List<TopicV1> getMatchingTopicsFromInteger(final List<Integer> matchingTags, final Integer excludeTag, final boolean haveOnlyMatchingTags)
	{
		return getMatchingTopicsFromInteger(matchingTags, CollectionUtilities.toArrayList(excludeTag), haveOnlyMatchingTags, false);
	}

	public List<TopicV1> getMatchingTopicsFromInteger(final List<Integer> matchingTags, final List<Integer> excludeTags)
	{
		return getMatchingTopicsFromInteger(matchingTags, excludeTags, false, false);
	}

	public List<TopicV1> getMatchingTopicsFromInteger(final Integer matchingTag, final List<Integer> excludeTags)
	{
		return getMatchingTopicsFromInteger(matchingTag, excludeTags, false);
	}

	public List<TopicV1> getMatchingTopics(final Integer matchingTag, final Integer excludeTag)
	{
		return getMatchingTopicsFromInteger(matchingTag, excludeTag, false);
	}

	public List<TopicV1> getMatchingTopicsFromInteger(final List<Integer> matchingTags, final Integer excludeTag)
	{
		return getMatchingTopicsFromInteger(matchingTags, excludeTag, false);
	}

	public List<TopicV1> getMatchingTopicsFromInteger(final Integer matchingTag)
	{
		return getMatchingTopicsFromInteger(matchingTag, new ArrayList<Integer>(), false);
	}

	public List<TopicV1> getMatchingTopicsFromInteger(final List<Integer> matchingTags)
	{
		return getMatchingTopicsFromInteger(matchingTags, new ArrayList<Integer>(), false, false);
	}

	public List<TopicV1> getTopics()
	{
		return CollectionUtilities.toArrayList(topics.keySet());
	}
	
	public List<TopicV1> getNonLandingPageTopics()
	{
		final List<TopicV1> retValue = new ArrayList<TopicV1>();
		for (final TopicV1 topic : topics.keySet())
			if (topic.getId() >= 0)
				retValue.add(topic);		
		return retValue;
	}

	public void setTopics(final List<TopicV1> topics)
	{
		this.topics = new HashMap<TopicV1, TopicProcessingData>();
		
		for (final TopicV1 topic : topics)
			this.topics.put(topic, new TopicProcessingData());
	}

	public List<TopicV1> getMatchingTopicsFromTag(final List<TagV1> matchingTags, final List<TagV1> excludeTags)
	{
		return getMatchingTopicsFromInteger(convertTagArrayToIntegerArray(matchingTags), convertTagArrayToIntegerArray(excludeTags), false, false);
	}

	public List<TopicV1> getMatchingTopicsFromTag(final TagV1 matchingTag, final List<TagV1> excludeTags)
	{
		if (matchingTag == null)
			return null;

		return getMatchingTopicsFromInteger(matchingTag.getId(), convertTagArrayToIntegerArray(excludeTags), false);
	}

	public List<TopicV1> getMatchingTopicsFromTag(final TagV1 matchingTag, final TagV1 excludeTag)
	{
		if (matchingTag == null || excludeTag == null)
			return null;

		return getMatchingTopicsFromInteger(matchingTag.getId(), excludeTag.getId(), false);
	}

	public List<TopicV1> getMatchingTopicsFromTag(final List<TagV1> matchingTags, final TagV1 excludeTag)
	{
		if (excludeTag == null)
			return null;

		return getMatchingTopicsFromInteger(convertTagArrayToIntegerArray(matchingTags), excludeTag.getId(), false);
	}

	public List<TopicV1> getMatchingTopicsFromTag(final TagV1 matchingTag)
	{
		if (matchingTag == null)
			return null;

		return getMatchingTopicsFromInteger(matchingTag.getId(), new ArrayList<Integer>(), false);
	}

	public List<TopicV1> getMatchingTopicsFromTag(final List<TagV1> matchingTags)
	{
		return getMatchingTopicsFromInteger(convertTagArrayToIntegerArray(matchingTags), new ArrayList<Integer>(), false, false);
	}

	private List<Integer> convertTagArrayToIntegerArray(final List<TagV1> tags)
	{
		final List<Integer> retValue = new ArrayList<Integer>();
		for (final TagV1 tag : tags)
			if (tag != null)
				retValue.add(tag.getId());
		return retValue;
	}
}