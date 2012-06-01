package com.redhat.topicindex.rest.entities.interfaces;

import java.util.Date;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;


public interface ITranslatedTopicV1 extends IBaseTopicV1<ITranslatedTopicV1>
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
	
	ITopicV1 getTopic();
	void setTopic(ITopicV1 topic);
	
	Integer getTranslatedTopicId();
	void setTranslatedTopicId(Integer translatedTopicId);
	
	Integer getTopicId();
	void setTopicId(Integer topicId);
	
	Integer getTopicRevision();
	void setTopicRevision(Integer topicRevision);
	
	Integer getTranslationPercentage();
	void setTranslationPercentage(Integer translationPercentage);
	
	Date getHtmlUpdated();
	void setHtmlUpdated(Date htmlUpdated);
	
	BaseRestCollectionV1<ITranslatedTopicStringV1> getTranslatedTopicStrings_OTM();
	void setTranslatedTopicStrings_OTM(BaseRestCollectionV1<ITranslatedTopicStringV1> translatedTopicStrings);
	
	BaseRestCollectionV1<ITranslatedTopicV1> getOutgoingTranslatedRelationships();
	void setOutgoingTranslatedRelationships(BaseRestCollectionV1<ITranslatedTopicV1> outgoingTranslatedRelationships);
	
	BaseRestCollectionV1<ITranslatedTopicV1> getIncomingTranslatedRelationships();
	void setIncomingTranslatedRelationships(BaseRestCollectionV1<ITranslatedTopicV1> incominTranslatedRelationships);
	
	boolean hasBeenPushedForTranslation();
}
