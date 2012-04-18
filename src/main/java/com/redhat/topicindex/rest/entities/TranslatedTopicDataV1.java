package com.redhat.topicindex.rest.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.redhat.ecs.commonutils.DocBookUtilities;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

public class TranslatedTopicDataV1 extends BaseRESTEntityV1<TranslatedTopicDataV1>
{
	public static final String TRANSLATEDXML_NAME = "translatedxml";
	public static final String TRANSLATEDXMLRENDERED_NAME = "translatedxmlrendered";
	public static final String TRANSLATIONLOCALE_NAME = "translationlocale";
	public static final String TRANSLATEDSTRINGS_NAME = "translatedstrings_OTM";
	public static final String DATE_NAME = "date";
	public static final String TRANSLATIONPERCENTAGE_NAME = "translationpercentage";
	public static final String TRANSLATEDTOPIC_NAME = "translatedtopic";
	public static final String OUTGOING_TRANSLATIONS_NAME = "outgoingrelatedtranslations";
	public static final String INCOMING_TRANSLATIONS_NAME = "incomingrelatedtranslations";
	
	private TranslatedTopicV1 translatedTopic;
	private String translatedXml;
	private String translatedXmlRendered;
	private String translationLocale;
	private BaseRestCollectionV1<TranslatedTopicStringV1> translatedStrings;
	private BaseRestCollectionV1<TranslatedTopicDataV1> outgoingRelatedTranslatedTopicDatas;
	private BaseRestCollectionV1<TranslatedTopicDataV1> incomingRelatedTranslatedTopicDatas;
	private Date updated;
	private Integer translationPercentage;

	public TranslatedTopicV1 getTranslatedTopic()
	{
		return translatedTopic;
	}

	public void setTranslatedTopic(final TranslatedTopicV1 translatedTopic)
	{
		this.translatedTopic = translatedTopic;
	}

	public String getTranslatedXml()
	{
		return translatedXml;
	}

	public void setTranslatedXml(final String translatedXml)
	{
		this.translatedXml = translatedXml;
	}
	
	public void setTranslatedXmlExplicit(final String translatedXml)
	{
		this.translatedXml = translatedXml;
		this.setParamaterToConfigured(TRANSLATEDXML_NAME);
	}

	public String getTranslatedXmlRendered()
	{
		return translatedXmlRendered;
	}

	public void setTranslatedXmlRendered(final String translatedXmlRendered)
	{
		this.translatedXmlRendered = translatedXmlRendered;
	}
	
	public void setTranslatedXmlRenderedExplicit(final String translatedXmlRendered)
	{
		this.translatedXmlRendered = translatedXmlRendered;
		this.setParamaterToConfigured(TRANSLATEDXMLRENDERED_NAME);
	}

	public String getTranslationLocale()
	{
		return translationLocale;
	}

	public void setTranslationLocale(final String translationLocale)
	{
		this.translationLocale = translationLocale;
	}
	
	public void setTranslationLocaleExplicit(final String translationLocale)
	{
		this.translationLocale = translationLocale;
		this.setParamaterToConfigured(TRANSLATIONLOCALE_NAME);
	}

	public Date getUpdated()
	{
		return updated;
	}

	public void setUpdated(final Date updated)
	{
		this.updated = updated;
	}
	
	public void setUpdatedExplicit(final Date updated)
	{
		this.updated = updated;
		this.setParamaterToConfigured(DATE_NAME);
	}
	
	public Integer getTranslationPercentage() {
		return translationPercentage;
	}

	public void setTranslationPercentage(Integer translationPercentage) {
		this.translationPercentage = translationPercentage;
	}
	
	public void setTranslationPercentageExplicit(Integer translationPercentage) {
		this.translationPercentage = translationPercentage;
		this.setParamaterToConfigured(TRANSLATIONPERCENTAGE_NAME);
	}

	public BaseRestCollectionV1<TranslatedTopicStringV1> getTranslatedStrings_OTM()
	{
		return translatedStrings;
	}

	public void setTranslatedStrings_OTM(final BaseRestCollectionV1<TranslatedTopicStringV1> translatedStrings)
	{
		this.translatedStrings = translatedStrings;
	}
	
	public void setTranslatedStringsExplicit_OTM(final BaseRestCollectionV1<TranslatedTopicStringV1> translatedStrings)
	{
		this.translatedStrings = translatedStrings;
		this.setParamaterToConfigured(TRANSLATEDSTRINGS_NAME);
	}
	
	public BaseRestCollectionV1<TranslatedTopicDataV1> getOutgoingRelatedTranslatedTopicData()
	{
		return this.outgoingRelatedTranslatedTopicDatas;
	}
	
	public void setOutgoingRelatedTranslatedTopicData(BaseRestCollectionV1<TranslatedTopicDataV1> outgoingRelatedTranslatedTopicDatas)
	{
		this.outgoingRelatedTranslatedTopicDatas = outgoingRelatedTranslatedTopicDatas;
	}
	
	public BaseRestCollectionV1<TranslatedTopicDataV1> getIncomingRelatedTranslatedTopicData()
	{
		return this.incomingRelatedTranslatedTopicDatas;
	}
	
	public void setIncomingRelatedTranslatedTopicData(BaseRestCollectionV1<TranslatedTopicDataV1> incomingRelatedTranslatedTopicDatas)
	{
		this.incomingRelatedTranslatedTopicDatas = incomingRelatedTranslatedTopicDatas;
	}
	
	@XmlTransient
	@JsonIgnore
	public TranslatedTopicDataV1 getLatestRelatedTranslationDataByTopicID(final int topicId) {
		final BaseRestCollectionV1<TranslatedTopicDataV1> relatedOutgoingTranslatedTopicDatas = getOutgoingRelatedTranslatedTopicData();
		TranslatedTopicDataV1 relatedTranslatedTopicData = null;
		if (relatedOutgoingTranslatedTopicDatas != null && relatedOutgoingTranslatedTopicDatas.getItems() != null)
		{
			/* Loop through the related TranslatedTopicData to find the latest complete translation */
			for (final TranslatedTopicDataV1 translatedTopicData : relatedOutgoingTranslatedTopicDatas.getItems())
			{
				if (translatedTopicData.getTranslatedTopic().getTopicId().equals(topicId)
						/* Check that the translation is complete */
						&& translatedTopicData.getTranslationPercentage() >= 100
						/* Check to see if this TranslatedTopic revision is higher then the current revision */
						&& (relatedTranslatedTopicData == null || relatedTranslatedTopicData.getTranslatedTopic().getTopicRevision() < translatedTopicData.getTranslatedTopic().getTopicRevision()))
					relatedTranslatedTopicData = translatedTopicData;
			}
		}
		return relatedTranslatedTopicData;
	}
	
	@XmlTransient
	@JsonIgnore
	public String getTranslatedTopicTitle()
	{
		return DocBookUtilities.findTitle(this.translatedXml);
	}
}
