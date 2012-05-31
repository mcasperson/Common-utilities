package com.redhat.topicindex.rest.entities;

import com.redhat.topicindex.rest.entities.interfaces.ITranslatedTopicStringV1;
import com.redhat.topicindex.rest.entities.interfaces.ITranslatedTopicV1;

public class TranslatedTopicStringV1 extends BaseRESTEntityV1<ITranslatedTopicStringV1> implements ITranslatedTopicStringV1
{
	public static final String ORIGINALSTRING_NAME = "originalstring";
	public static final String TRANSLATEDSTRING_NAME = "translatedstring";
	public static final String TRANSLATEDTOPIC_NAME = "translatedtopic";
	
	private ITranslatedTopicV1 translatedTopic;
	private String originalString;
	private String translatedString;
	
	@Override
	public ITranslatedTopicStringV1 clone(final boolean deepCopy)
	{
		final ITranslatedTopicStringV1 retValue = new TranslatedTopicStringV1();
		
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

	public ITranslatedTopicV1 getTranslatedTopic()
	{
		return translatedTopic;
	}

	public void setTranslatedTopic(final ITranslatedTopicV1 translatedTopic)
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
	
	public void setOriginalStringExplicit(final String originalString)
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
	
	public void setTranslatedStringExplicit(final String translatedString)
	{
		this.translatedString = translatedString;
		this.setParamaterToConfigured(TRANSLATEDSTRING_NAME);
	}
}
