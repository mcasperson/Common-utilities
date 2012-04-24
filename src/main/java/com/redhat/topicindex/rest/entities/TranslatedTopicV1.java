package com.redhat.topicindex.rest.entities;

import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

public class TranslatedTopicV1 extends BaseRESTEntityV1<TranslatedTopicV1>
{
	public static final String TOPICID_NAME = "topicid";
	public static final String TOPICREVISION_NAME = "topicrevision";
	public static final String TRANSLATEDTOPICDATA_NAME = "translatedtopicdata_OTM";
	
	private Integer topicId;
	private Integer topicRevision;
	private BaseRestCollectionV1<TranslatedTopicDataV1> translatedTopicData;

	@Override
	public TranslatedTopicV1 clone(final boolean deepCopy)
	{
		final TranslatedTopicV1 retValue = new TranslatedTopicV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.topicId = this.topicId;
		retValue.topicRevision = this.topicRevision;
		
		if (deepCopy)
		{
			retValue.translatedTopicData = this.translatedTopicData != null ? this.translatedTopicData.clone(deepCopy) : null;
		}
		else
		{
			retValue.translatedTopicData = this.translatedTopicData;
		}
		return retValue;
	}
	
	public Integer getTopicId()
	{
		return topicId;
	}

	public void setTopicId(final Integer topicId)
	{
		this.topicId = topicId;
	}
	
	public void setTopicIdExplicit(final Integer topicId)
	{
		this.topicId = topicId;
		this.setParamaterToConfigured(TOPICID_NAME);
	}

	public Integer getTopicRevision()
	{
		return topicRevision;
	}

	public void setTopicRevision(final Integer topicRevision)
	{
		this.topicRevision = topicRevision;
	}
	
	public void setTopicRevisionExplicit(final Integer topicRevision)
	{
		this.topicRevision = topicRevision;
		this.setParamaterToConfigured(TOPICREVISION_NAME);
	}

	public BaseRestCollectionV1<TranslatedTopicDataV1> getTranslatedTopicData_OTM()
	{
		return translatedTopicData;
	}

	public void setTranslatedTopicData_OTM(final BaseRestCollectionV1<TranslatedTopicDataV1> translatedTopicData)
	{
		this.translatedTopicData = translatedTopicData;
	}
	
	public void setTranslatedTopicDataExplicit_OTM(final BaseRestCollectionV1<TranslatedTopicDataV1> translatedTopicData)
	{
		this.translatedTopicData = translatedTopicData;
		this.setParamaterToConfigured(TRANSLATEDTOPICDATA_NAME);
	}
	
	@JsonIgnore
	@XmlTransient
	public String getZanataId()
	{
		return topicId + "-" + topicRevision;
	}
}
