package com.redhat.topicindex.rest.entities;

public class TranslatedTopicStringV1 extends BaseRESTEntityV1<TranslatedTopicStringV1>
{
	public static final String ORIGINALSTRING_NAME = "originalstring";
	public static final String TRANSLATEDSTRING_NAME = "translatedstring";
	public static final String TRANSLATEDTOPICDATA_NAME = "translatedtopicdata";
	
	private TranslatedTopicDataV1 translatedTopicData;
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
			retValue.translatedTopicData = translatedTopicData != null ? this.translatedTopicData.clone(deepCopy) : null;
		}
		else
		{
			retValue.translatedTopicData = this.translatedTopicData;
		}
		return retValue;
	}

	public TranslatedTopicDataV1 getTranslatedTopicData()
	{
		return translatedTopicData;
	}

	public void setTranslatedTopicData(final TranslatedTopicDataV1 translatedTopicData)
	{
		this.translatedTopicData = translatedTopicData;
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
