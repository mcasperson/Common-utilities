package com.redhat.topicindex.rest.entities.interfaces;

import javax.xml.bind.annotation.XmlSeeAlso;

import com.redhat.topicindex.rest.entities.TranslatedTopicStringV1;

@XmlSeeAlso(TranslatedTopicStringV1.class)
public interface ITranslatedTopicStringV1 extends IBaseRESTEntityV1<ITranslatedTopicStringV1>
{
	public static final String ORIGINALSTRING_NAME = "originalstring";
	public static final String TRANSLATEDSTRING_NAME = "translatedstring";
	public static final String TRANSLATEDTOPIC_NAME = "translatedtopic";
	
	ITranslatedTopicV1 getTranslatedTopic();
	void setTranslatedTopic(ITranslatedTopicV1 translatedTopic);
	
	String getOriginalString();
	void setOriginalString(String originalString);
	
	String getTranslatedString();
	void setTranslatedString(String translatedString);
}
