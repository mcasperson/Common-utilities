package com.redhat.topicindex.rest.entities.interfaces;

import java.util.Date;

import javax.xml.bind.annotation.XmlSeeAlso;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.TopicV1;

@XmlSeeAlso(TopicV1.class)
public interface ITopicV1 extends IBaseTopicV1<ITopicV1>
{
	public static final String DESCRIPTION_NAME = "description";
	public static final String BUGZILLABUGS_NAME = "bugzillabugs_OTM";
	public static final String TRANSLATEDTOPICS_NAME = "translatedtopics_OTM";
	
	String getDescription();
	void setDescription(String description);
	void explicitSetDescription(String description);
	
	Date getCreated();
	void setCreated(Date created);
	
	Date getLastModified();
	void setLastModified(Date lastModified);
	
	BaseRestCollectionV1<IBugzillaBugV1> getBugzillaBugs_OTM();
	void setBugzillaBugs_OTM(BaseRestCollectionV1<IBugzillaBugV1> bugzillaBugs);
	void explicitSetBugzillaBugs_OTM(BaseRestCollectionV1<IBugzillaBugV1> bugzillaBugs);
	
	BaseRestCollectionV1<ITranslatedTopicV1> getTranslatedTopics_OTM();
	void setTranslatedTopics_OTM(BaseRestCollectionV1<ITranslatedTopicV1> translatedTopics);
	void explicitSetTranslatedTopics_OTM(BaseRestCollectionV1<ITranslatedTopicV1> translatedTopics);
}
