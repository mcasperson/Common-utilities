package com.redhat.topicindex.rest.entities.interfaces;

public interface ITranslatedTopicStringV1 extends IBaseRESTEntityV1<ITranslatedTopicStringV1>
{
	ITranslatedTopicV1 getTranslatedTopic();
	void setTranslatedTopic(ITranslatedTopicV1 translatedTopic);
	
	String getOriginalString();
	void setOriginalString(String originalString);
	
	String getTranslatedString();
	void setTranslatedString(String translatedString);
}
