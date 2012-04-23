package com.redhat.topicindex.component.docbookrenderer.structures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redhat.topicindex.rest.entities.TopicV1;

/**
 * Stores information on the errors and warnings that were detected in a topic.
 */
public class TopicErrorData
{
	private TopicV1 topic;
	private Map<Integer, ArrayList<String>> errors = new HashMap<Integer, ArrayList<String>>();

	public TopicV1 getTopic()
	{
		return topic;
	}

	public void setTopic(TopicV1 topic)
	{
		this.topic = topic;
	}

	public Map<Integer, ArrayList<String>> getErrors()
	{
		return errors;
	}

	public void setErrors(Map<Integer, ArrayList<String>> errors)
	{
		this.errors = errors;
	}
	
	public void addError(final String item, final Integer level)
	{
		if (!errors.containsKey(level))
			errors.put(level, new ArrayList<String>());
		errors.get(level).add(item);
	}
	
	public boolean hasItemsOfType(final Integer level)
	{
		return errors.containsKey(level);
	}
	
	public List<String> getItemsOfType(final Integer level)
	{
		if (hasItemsOfType(level))
			return errors.get(level);
		return Collections.emptyList();
	}
}
