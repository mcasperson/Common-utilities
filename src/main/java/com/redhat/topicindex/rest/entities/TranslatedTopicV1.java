package com.redhat.topicindex.rest.entities;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.redhat.ecs.constants.CommonConstants;
import com.redhat.ecs.services.docbookcompiling.DocbookBuilderConstants;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.ITopicV1;
import com.redhat.topicindex.rest.entities.interfaces.ITranslatedTopicStringV1;
import com.redhat.topicindex.rest.entities.interfaces.ITranslatedTopicV1;

@XmlRootElement(name = "translatedtopic")
public class TranslatedTopicV1 extends BaseTopicV1<ITranslatedTopicV1> implements ITranslatedTopicV1
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
	
	private ITopicV1 topic;
	private Integer translatedTopicId;
	private Integer topicId;
	private Integer topicRevision;
	private Integer translationPercentage;
	private Date htmlUpdated;
	private BaseRestCollectionV1<ITranslatedTopicStringV1> translatedTopicStrings = null;
	private BaseRestCollectionV1<ITranslatedTopicV1> outgoingTranslatedRelationships = null;
	private BaseRestCollectionV1<ITranslatedTopicV1> incomingTranslatedRelationships = null;

	@Override
	public TranslatedTopicV1 clone(final boolean deepCopy)
	{
		final TranslatedTopicV1 retValue = new TranslatedTopicV1();
		
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
	
	@XmlElement
	public Integer getTranslatedTopicId() {
		return translatedTopicId;
	}

	public void setTranslatedTopicId(Integer translatedTopicId) {
		this.translatedTopicId = translatedTopicId;
	}

	public void setXmlExplicit(final String xml)
	{
		setXml(xml);
		setParamaterToConfigured(XML_NAME);
	}
	
	public void setXmlErrorsExplicit(final String xmlErrors)
	{
		setXmlErrors(xmlErrors);
		setParamaterToConfigured(XML_ERRORS_NAME);
	}

	public void setHtmlExplicit(final String html)
	{
		setHtml(html);
		setParamaterToConfigured(HTML_NAME);
	}
	
	@XmlElement
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

	@XmlElement
	public Integer getTopicRevision()
	{
		return topicRevision;
	}

	public void setTopicRevision(final Integer topicRevision)
	{
		this.topicRevision = topicRevision;
	}
	
	@XmlElement
	public ITopicV1 getTopic()
	{
		return topic;
	}

	public void setTopic(final ITopicV1 topic)
	{
		this.topic = topic;
	}
	
	public void setTopicRevisionExplicit(final Integer topicRevision)
	{
		this.topicRevision = topicRevision;
		this.setParamaterToConfigured(TOPICREVISION_NAME);
	}
	
	@XmlElement
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

	@XmlElement
	public BaseRestCollectionV1<ITranslatedTopicStringV1> getTranslatedTopicStrings_OTM()
	{
		return translatedTopicStrings;
	}

	public void setTranslatedTopicStrings_OTM(final BaseRestCollectionV1<ITranslatedTopicStringV1> translatedTopicStrings)
	{
		this.translatedTopicStrings = translatedTopicStrings;
	}
	
	public void setTranslatedTopicStringExplicit_OTM(final BaseRestCollectionV1<ITranslatedTopicStringV1> translatedTopicStrings)
	{
		this.translatedTopicStrings = translatedTopicStrings;
		this.setParamaterToConfigured(TRANSLATEDTOPICSTRING_NAME);
	}
	
	@XmlElement
	public Date getHtmlUpdated()
	{
		return htmlUpdated;
	}

	public void setHtmlUpdated(final Date htmlUpdated)
	{
		this.htmlUpdated = htmlUpdated;
	}
	
	public void setHtmlUpdatedExplicit(final Date htmlUpdated)
	{
		this.htmlUpdated = htmlUpdated;
		this.setParamaterToConfigured(HTML_UPDATED);
	}
	
	public void setLocaleExplicit(final String locale)
	{
		setLocale(locale);
		setParamaterToConfigured(LOCALE_NAME);
	}
	
	@XmlElement
	public BaseRestCollectionV1<ITranslatedTopicV1> getOutgoingTranslatedRelationships()
	{
		return outgoingTranslatedRelationships;
	}
	
	public void setOutgoingTranslatedRelationships(final BaseRestCollectionV1<ITranslatedTopicV1> outgoingTranslatedRelationships)
	{
		this.outgoingTranslatedRelationships = outgoingTranslatedRelationships;
	}
	
	@XmlElement
	public BaseRestCollectionV1<ITranslatedTopicV1> getIncomingTranslatedRelationships()
	{
		return incomingTranslatedRelationships;
	}
	
	public void setIncomingTranslatedRelationships(final BaseRestCollectionV1<ITranslatedTopicV1> incomingTranslatedRelationships)
	{
		this.incomingTranslatedRelationships = incomingTranslatedRelationships;
	}

	@JsonIgnore
	@XmlTransient
	public String getZanataId()
	{
		return topicId + "-" + topicRevision;
	}

	public String returnBugzillaBuildId() {
		return "Translation " + this.getZanataId() + " " + getLocale();
	}

	public String returnSkynetURL() {
		/*
		 * If the topic isn't a dummy then link to the translated counterpart.
		 * If the topic is a dummy URL and the locale doesn't match the 
		 * historical topic's locale then it means that the topic has been
		 * pushed to zanata so link to the original pushed translation.
		 * If neither of these rules apply then link to the standard topic.
		 */
		if (!isDummyTopic())
		{
			return CommonConstants.SERVER_URL + "/TopicIndex/TranslatedTopic.seam?translatedTopicId=" + this.getTranslatedTopicId() + "&amp;locale=" + getLocale();
		}
		else if (hasBeenPushedForTranslation())
		{
			return CommonConstants.SERVER_URL + "/TopicIndex/TranslatedTopic.seam?translatedTopicId=" + this.getPushedTranslationTopicId() + "&amp;locale=" + topic.getLocale();
		}
		else
		{
			return topic.returnSkynetURL();
		}
	}

	public String returnInternalURL() {
		/*
		 * If the topic isn't a dummy then link to the translated counterpart.
		 * If the topic is a dummy URL and the locale doesn't match the 
		 * historical topic's locale then it means that the topic has been
		 * pushed to zanata so link to the original pushed translation.
		 * If neither of these rules apply then link to the standard topic.
		 */
		if (!isDummyTopic())
		{
			return "TranslatedTopic.seam?translatedTopicId=" + getTranslatedTopicId() + "&locale=" + getLocale() + "&selectedTab=Rendered+View";
		}
		else if (hasBeenPushedForTranslation())
		{
			return "TranslatedTopic.seam?translatedTopicId=" + getPushedTranslationTopicId() + "&locale=" + topic.getLocale() + "&selectedTab=Rendered+View";
		}
		else
		{
			return topic.returnInternalURL();
		}
	}
	
	@Override
	@XmlTransient
	@JsonIgnore
	public ITranslatedTopicV1 getRelatedTopicByID(final Integer id)
	{
		ITranslatedTopicV1 relatedTopic = null;
		if (this.getOutgoingRelationships() != null && this.getOutgoingRelationships().getItems() != null)
		{
			for (final ITranslatedTopicV1 topic : getOutgoingRelationships().getItems())
			{
				if (topic.getTopicId().equals(id)
						/* Check that the translation is complete */
						&& topic.getTranslationPercentage() >= 100 
						/* Check that a related topic hasn't been set or the topics 
						 * revision is higher then the current topic revision */
						&& (relatedTopic == null || topic.getTopicRevision() > relatedTopic.getTopicRevision()))
				{
					relatedTopic = topic;
				}
			}
		}
		return relatedTopic;
	}
	
	@XmlTransient
	@JsonIgnore
	public String getXRefID()
	{
		if (!isDummyTopic())
			return "TranslatedTopicID" + this.getId();
		else if (hasBeenPushedForTranslation())
			return "TranslatedTopicID" + getPushedTranslationTopicId();
		else
			return topic.returnXRefID();
	}
	
	@XmlTransient
	@JsonIgnore
	public String getErrorXRefID()
	{
		return DocbookBuilderConstants.ERROR_XREF_ID_PREFIX + getZanataId();
	}
	
	@XmlTransient
	@JsonIgnore
	public boolean hasBeenPushedForTranslation()
	{
		if (!isDummyTopic()) return true;
		
		/* Check that a translation exists that is the same locale as the base topic */
		boolean baseTranslationExists = false;
		if (topic.getTranslatedTopics_OTM() != null && topic.getTranslatedTopics_OTM().getItems() != null)
		{
			for (final ITranslatedTopicV1 translatedTopic: topic.getTranslatedTopics_OTM().getItems())
			{
				if (translatedTopic.getLocale().equals(topic.getLocale()))
					baseTranslationExists = true;
			}
		}
		
		return baseTranslationExists;
	}
	
	@XmlTransient
	@JsonIgnore
	public Integer getPushedTranslationTopicId()
	{
		if (!isDummyTopic()) return translatedTopicId;
		
		/* Check that a translation exists that is the same locale as the base topic */
		if (topic.getTranslatedTopics_OTM() != null && topic.getTranslatedTopics_OTM().getItems() != null)
		{
			for (final ITranslatedTopicV1 translatedTopic: topic.getTranslatedTopics_OTM().getItems())
			{
				if (translatedTopic.getLocale().equals(topic.getLocale()))
					return translatedTopic.getTranslatedTopicId();
			}
		}
		
		return null;
	}
}
