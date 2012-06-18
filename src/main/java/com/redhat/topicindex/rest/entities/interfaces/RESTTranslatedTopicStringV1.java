package com.redhat.topicindex.rest.entities.interfaces;

import com.redhat.topicindex.rest.collections.RESTTranslatedTopicStringCollectionV1;

public class RESTTranslatedTopicStringV1 extends RESTBaseEntityV1<RESTTranslatedTopicStringV1, RESTTranslatedTopicStringCollectionV1>
{
	public static final String ORIGINALSTRING_NAME = "originalstring";
	public static final String TRANSLATEDSTRING_NAME = "translatedstring";
	public static final String TRANSLATEDTOPIC_NAME = "translatedtopic";
	
	private RESTTranslatedTopicV1 translatedTopic;
	private String originalString;
	private String translatedString;
	/** A list of the Envers revision numbers */
	private RESTTranslatedTopicStringCollectionV1 revisions = null;
	
	@Override
	public RESTTranslatedTopicStringCollectionV1 getRevisions()
	{
		return revisions;
	}

	@Override
	public void setRevisions(final RESTTranslatedTopicStringCollectionV1 revisions)
	{
		this.revisions = revisions;
	}
	
	@Override
	public RESTTranslatedTopicStringV1 clone(final boolean deepCopy)
	{
		final RESTTranslatedTopicStringV1 retValue = new RESTTranslatedTopicStringV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.setOriginalString(this.originalString);
		retValue.setTranslatedString(this.translatedString);
		
		if (deepCopy)
		{
			retValue.setTranslatedTopic(translatedTopic != null ? this.translatedTopic.clone(deepCopy) : null);
		}
		else
		{
			retValue.setTranslatedTopic(this.translatedTopic);
		}
		return retValue;
	}

	public RESTTranslatedTopicV1 getTranslatedTopic()
	{
		return translatedTopic;
	}

	public void setTranslatedTopic(final RESTTranslatedTopicV1 translatedTopic)
	{
		this.translatedTopic = translatedTopic;
	}

	public String getOriginalString()
	{
		return originalString;
	}

	public void setOriginalString(final String originalString)
	{
		this.originalString = originalString;
	}
	
	public void explicitSetOriginalString(final String originalString)
	{
		this.originalString = originalString;
		this.setParamaterToConfigured(ORIGINALSTRING_NAME);
	}

	public String getTranslatedString()
	{
		return translatedString;
	}

	public void setTranslatedString(final String translatedString)
	{
		this.translatedString = translatedString;
	}
	
	public void explicitSetTranslatedString(final String translatedString)
	{
		this.translatedString = translatedString;
		this.setParamaterToConfigured(TRANSLATEDSTRING_NAME);
	}
}
