package com.redhat.topicindex.component.docbookrenderer.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.redhat.ecs.commonutils.CollectionUtilities;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.ComponentBaseTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTBaseTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTTranslatedTopicV1;

/**
 * Provides a central location for storing and adding messages that are
 * generated while compiling to docbook.
 */
public class TopicErrorDatabase<T extends RESTBaseTopicV1<T, U>, U extends BaseRestCollectionV1<T, U>>
{
	public static enum ErrorLevel {ERROR, WARNING};
	public static enum ErrorType {NO_CONTENT, INVALID_INJECTION, INVALID_CONTENT, UNTRANSLATED, 
		NOT_PUSHED_FOR_TRANSLATION, INCOMPLETE_TRANSLATION, INVALID_IMAGES}

	private Map<String, List<TopicErrorData<T, U>>> errors = new HashMap<String, List<TopicErrorData<T, U>>>();

	public int getErrorCount(final String locale)
	{
		return errors.containsKey(locale) ? errors.get(locale).size() : 0;
	}

	public boolean hasItems()
	{
		return errors.size() != 0;
	}
	
	public boolean hasItems(final String locale)
	{
		return errors.containsKey(locale) ? errors.get(locale).size() != 0 : false;
	}

	public void addError(final T topic, final ErrorType errorType, final String error)
	{
		addItem(topic, error, ErrorLevel.ERROR, errorType);
	}

	public void addWarning(final T topic, final ErrorType errorType, final String error)
	{
		addItem(topic, error, ErrorLevel.WARNING, errorType);
	}
	
	public void addError(final T topic, final String error)
	{
		addItem(topic, error, ErrorLevel.ERROR, null);
	}

	public void addWarning(final T topic, final String error)
	{
		addItem(topic, error, ErrorLevel.WARNING, null);
	}
	
	/**
	 * Add a error for a topic that was included in the TOC
	 * @param topic
	 * @param error
	 */
	public void addTocError(final T topic, final ErrorType errorType, final String error)
	{
		addItem(topic, error, ErrorLevel.ERROR, errorType);
	}

	public void addTocWarning(final T topic, final ErrorType errorType, final String error)
	{
		addItem(topic, error, ErrorLevel.WARNING, errorType);
	}

	private void addItem(final T topic, final String item, final ErrorLevel errorLevel, final ErrorType errorType)
	{
		final TopicErrorData<T, U> topicErrorData = addOrGetTopicErrorData(topic);
		/* don't add duplicates */
		if (!(topicErrorData.getErrors().containsKey(errorLevel) && topicErrorData.getErrors().get(errorLevel).contains(item)))
			topicErrorData.addError(item, errorLevel, errorType);
	}

	private TopicErrorData<T, U> getErrorData(final T topic)
	{
		for (final String locale : errors.keySet())
			for (final TopicErrorData<T, U> topicErrorData : errors.get(locale))
			{
				if (ComponentBaseTopicV1.returnIsDummyTopic(topic))
				{
					if (topic.getClass() == RESTTranslatedTopicV1.class && topicErrorData.getTopic() instanceof RESTTranslatedTopicV1)
					{
						if (((RESTTranslatedTopicV1) topicErrorData.getTopic()).getTopicId().equals(((RESTTranslatedTopicV1) topic).getTopicId()))
							return topicErrorData;
					}
				}
				else
				{
					if (topicErrorData.getTopic().getId().equals(topic.getId()))
						return topicErrorData;
				}
			}
		return null;
	}

	private TopicErrorData<T, U> addOrGetTopicErrorData(final T topic)
	{
		TopicErrorData<T, U> topicErrorData = getErrorData(topic);
		if (topicErrorData == null)
		{
			topicErrorData = new TopicErrorData<T, U>();
			topicErrorData.setTopic(topic);
			if (!errors.containsKey(topic.getLocale()))
				errors.put(topic.getLocale(), new ArrayList<TopicErrorData<T, U>>());
			errors.get(topic.getLocale()).add(topicErrorData);
		}
		return topicErrorData;
	}
	
	public List<String> getLocales()
	{
		return CollectionUtilities.toArrayList(errors.keySet());
	}

	public List<TopicErrorData<T, U>> getErrors(final String locale)
	{
		return errors.containsKey(locale) ? errors.get(locale) : null;
	}
	
	public List<TopicErrorData<T, U>> getErrorsOfType(final String locale, final ErrorType errorType)
	{
		final List<TopicErrorData<T, U>> localeErrors = errors.containsKey(locale) ? errors.get(locale) : new ArrayList<TopicErrorData<T, U>>();
		
		final List<TopicErrorData<T, U>> typeErrorDatas = new ArrayList<TopicErrorData<T, U>>();
		for (final TopicErrorData<T, U> errorData : localeErrors)
		{
			if (errorData.hasErrorType(errorType))
				typeErrorDatas.add(errorData);
		}
		
		return typeErrorDatas;
	}

	public void setErrors(final String locale, final List<TopicErrorData<T, U>> errors)
	{
		this.errors.put(locale, errors);
	}
}
