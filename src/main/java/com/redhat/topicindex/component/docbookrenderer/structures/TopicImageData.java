package com.redhat.topicindex.component.docbookrenderer.structures;

import com.redhat.topicindex.rest.entities.interfaces.RESTBaseTopicV1;

/**
 * This class is used to map an image referenced inside a topic to the topic
 * itself. This is mostly for error reporting purposes.
 */
public class TopicImageData<T extends RESTBaseTopicV1<T>>
{
	private T topic;
	private String imageName;

	public T getTopic()
	{
		return topic;
	}

	public void setTopic(T topic)
	{
		this.topic = topic;
	}

	public String getImageName()
	{
		return imageName;
	}

	public void setImageName(String imageName)
	{
		this.imageName = imageName;
	}

	public TopicImageData(final T topic, final String imageName)
	{
		this.topic = topic;
		this.imageName = imageName;
	}

}
