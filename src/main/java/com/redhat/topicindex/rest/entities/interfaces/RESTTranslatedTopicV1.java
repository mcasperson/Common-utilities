package com.redhat.topicindex.rest.entities.interfaces;

import java.util.Date;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

public class RESTTranslatedTopicV1 extends RESTBaseTopicV1<RESTTranslatedTopicV1>
{
	public static final String TOPICID_NAME = "topicid";
	public static final String TOPICREVISION_NAME = "topicrevision";
	public static final String TOPIC_NAME = "topic";
	public static final String TRANSLATEDTOPICSTRING_NAME = "translatedtopicstring_OTM";
	public static final String TRANSLATIONPERCENTAGE_NAME = "translationpercentage";
	public static final String HTML_UPDATED = "htmlUpdated";
	public static final String OUTGOING_NAME = "outgoingTranslatedRelationships";
	public static final String INCOMING_NAME = "incomingTranslatedRelationships";
	public static final String ALL_LATEST_OUTGOING_NAME = "allLatestOutgoingRelationships";
	public static final String ALL_LATEST_INCOMING_NAME = "allLatestIncomingRelationships";
	
	protected RESTTopicV1 topic;
	protected Integer translatedTopicId;
	protected Integer topicId;
	protected Integer topicRevision;
	protected Integer translationPercentage;
	protected Date htmlUpdated;
	protected BaseRestCollectionV1<RESTTranslatedTopicStringV1> translatedTopicStrings = null;
	protected BaseRestCollectionV1<RESTTranslatedTopicV1> outgoingTranslatedRelationships = null;
	protected BaseRestCollectionV1<RESTTranslatedTopicV1> incomingTranslatedRelationships = null;

	@Override
	public RESTTranslatedTopicV1 clone(final boolean deepCopy)
	{
		final RESTTranslatedTopicV1 retValue = new RESTTranslatedTopicV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.setTopicId(this.topicId);
		retValue.setTopicRevision(this.topicRevision);
		retValue.setTranslatedTopicId(this.translatedTopicId);
		retValue.setTranslationPercentage(this.translationPercentage);
		
		if (deepCopy)
		{
			retValue.setTranslatedTopicStrings_OTM(this.getTranslatedTopicStrings_OTM() != null ? this.getTranslatedTopicStrings_OTM().clone(deepCopy) : null);
			retValue.setTopic(this.topic != null ? this.topic.clone(deepCopy) : null);
		}
		else
		{
			retValue.setTranslatedTopicStrings_OTM(this.getTranslatedTopicStrings_OTM());
			retValue.topic = this.topic;
		}
		return retValue;
	}
	
	public Integer getTranslatedTopicId() {
		return translatedTopicId;
	}

	public void setTranslatedTopicId(Integer translatedTopicId) {
		this.translatedTopicId = translatedTopicId;
	}

	public void explicitSetXml(final String xml)
	{
		setXml(xml);
		setParamaterToConfigured(XML_NAME);
	}
	
	public void explicitSetXmlErrors(final String xmlErrors)
	{
		setXmlErrors(xmlErrors);
		setParamaterToConfigured(XML_ERRORS_NAME);
	}

	public void explicitSetHtml(final String html)
	{
		setHtml(html);
		setParamaterToConfigured(HTML_NAME);
	}
	
	public Integer getTopicId()
	{
		return topicId;
	}

	public void setTopicId(final Integer topicId)
	{
		this.topicId = topicId;
	}
	
	public void explicitSetTopicId(final Integer topicId)
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
	
	public RESTTopicV1 getTopic()
	{
		return topic;
	}

	public void setTopic(final RESTTopicV1 topic)
	{
		this.topic = topic;
	}
	
	public void explicitSetTopicRevision(final Integer topicRevision)
	{
		this.topicRevision = topicRevision;
		this.setParamaterToConfigured(TOPICREVISION_NAME);
	}
	
	public Integer getTranslationPercentage() {
		return translationPercentage;
	}

	public void setTranslationPercentage(Integer translationPercentage) {
		this.translationPercentage = translationPercentage;
	}
	
	public void explicitSetTranslationPercentage(Integer translationPercentage) {
		this.translationPercentage = translationPercentage;
		this.setParamaterToConfigured(TRANSLATIONPERCENTAGE_NAME);
	}

	public BaseRestCollectionV1<RESTTranslatedTopicStringV1> getTranslatedTopicStrings_OTM()
	{
		return translatedTopicStrings;
	}

	public void setTranslatedTopicStrings_OTM(final BaseRestCollectionV1<RESTTranslatedTopicStringV1> translatedTopicStrings)
	{
		this.translatedTopicStrings = translatedTopicStrings;
	}
	
	public void explicitSetTranslatedTopicString_OTM(final BaseRestCollectionV1<RESTTranslatedTopicStringV1> translatedTopicStrings)
	{
		this.translatedTopicStrings = translatedTopicStrings;
		this.setParamaterToConfigured(TRANSLATEDTOPICSTRING_NAME);
	}
	
	public Date getHtmlUpdated()
	{
		return htmlUpdated;
	}

	public void setHtmlUpdated(final Date htmlUpdated)
	{
		this.htmlUpdated = htmlUpdated;
	}
	
	public void explicitSetHtmlUpdated(final Date htmlUpdated)
	{
		this.htmlUpdated = htmlUpdated;
		this.setParamaterToConfigured(HTML_UPDATED);
	}
	
	public void explicitSetLocale(final String locale)
	{
		setLocale(locale);
		setParamaterToConfigured(LOCALE_NAME);
	}
	
	public BaseRestCollectionV1<RESTTranslatedTopicV1> getOutgoingTranslatedRelationships()
	{
		return outgoingTranslatedRelationships;
	}
	
	public void setOutgoingTranslatedRelationships(final BaseRestCollectionV1<RESTTranslatedTopicV1> outgoingTranslatedRelationships)
	{
		this.outgoingTranslatedRelationships = outgoingTranslatedRelationships;
	}
	
	public BaseRestCollectionV1<RESTTranslatedTopicV1> getIncomingTranslatedRelationships()
	{
		return incomingTranslatedRelationships;
	}
	
	public void setIncomingTranslatedRelationships(final BaseRestCollectionV1<RESTTranslatedTopicV1> incomingTranslatedRelationships)
	{
		this.incomingTranslatedRelationships = incomingTranslatedRelationships;
	}
}
