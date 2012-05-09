package com.redhat.topicindex.rest.entities;

public class TranslatedTopicStringV1 extends BaseRESTEntityV1<TranslatedTopicStringV1>
{
	public static final String ORIGINALSTRING_NAME = "originalstring";
	public static final String TRANSLATEDSTRING_NAME = "translatedstring";
	public static final String TRANSLATEDTOPIC_NAME = "translatedtopic";
	
	private TranslatedTopicV1 translatedTopic;
	private String originalString;
	private String translatedString;
	
	@Override
	public TranslatedTopicStringV1 clone(final boolean deepCopy)
	{
		final TranslatedTopicStringV1 retValue = new TranslatedTopicStringV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.originalString = this.originalString;
		retValue.translatedString = this.translatedString;
		
		if (deepCopy)
		{
			retValue.translatedTopic = translatedTopic != null ? this.translatedTopic.clone(deepCopy) : null;
		}
		else
		{
			retValue.translatedTopic = this.translatedTopic;
		}
		return retValue;
	}

	public TranslatedTopicV1 getTranslatedTopic()
	{
		return translatedTopic;
	}

	public void setTranslatedTopic(final TranslatedTopicV1 translatedTopic)
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
