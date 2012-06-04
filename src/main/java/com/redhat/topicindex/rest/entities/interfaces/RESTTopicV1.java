package com.redhat.topicindex.rest.entities.interfaces;

import java.util.Date;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

public class RESTTopicV1 extends RESTBaseTopicV1<RESTTopicV1>
{
	public static final String DESCRIPTION_NAME = "description";
	public static final String BUGZILLABUGS_NAME = "bugzillabugs_OTM";
	public static final String TRANSLATEDTOPICS_NAME = "translatedtopics_OTM";
	
	protected String description = null;
	protected Date created = null;
	protected Date lastModified = null;
	protected BaseRestCollectionV1<RESTBugzillaBugV1> bugzillaBugs = null;
	protected BaseRestCollectionV1<RESTTranslatedTopicV1> translatedTopics = null;
	
	@Override
	public RESTTopicV1 clone(final boolean deepCopy)
	{
		final RESTTopicV1 retValue = new RESTTopicV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.description = this.description;
		retValue.created = this.created == null ? null : (Date)this.created.clone();
		retValue.lastModified = this.lastModified == null ? null : (Date)lastModified.clone();
		
		if (deepCopy)
		{
			retValue.bugzillaBugs = this.bugzillaBugs == null ? null : this.bugzillaBugs.clone(deepCopy);
			retValue.translatedTopics = this.translatedTopics == null ? null : this.translatedTopics.clone(deepCopy);
		}
		else
		{
			retValue.bugzillaBugs = this.bugzillaBugs;
			retValue.translatedTopics = this.translatedTopics;
		}
		return retValue;
		
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}
	
	public void explicitSetDescription(final String description)
	{
		setDescription(description);
		setParamaterToConfigured(DESCRIPTION_NAME);
	}
	
	public Date getCreated()
	{
		return created;
	}

	public void setCreated(Date created)
	{
		this.created = created;
	}

	public void explicitSetXml(final String xml)
	{
		setXml(xml);
		setParamaterToConfigured(XML_NAME);
	}

	public void explicitSetHtml(final String html)
	{
		setHtml(html);
		setParamaterToConfigured(HTML_NAME);
	}
	
	public void explicitSetLocale(final String locale)
	{
		setLocale(locale);
		setParamaterToConfigured(LOCALE_NAME);
	}

	public Date getLastModified()
	{
		return lastModified;
	}

	public void setLastModified(final Date lastModified)
	{
		this.lastModified = lastModified;
	}
	
	public BaseRestCollectionV1<RESTBugzillaBugV1> getBugzillaBugs_OTM()
	{
		return bugzillaBugs;
	}

	public void setBugzillaBugs_OTM(final BaseRestCollectionV1<RESTBugzillaBugV1> bugzillaBugs)
	{
		this.bugzillaBugs = bugzillaBugs;
	}
	
	public void explicitSetBugzillaBugs_OTM(final BaseRestCollectionV1<RESTBugzillaBugV1> bugzillaBugs)
	{
		setBugzillaBugs_OTM(bugzillaBugs);
		setParamaterToConfigured(BUGZILLABUGS_NAME);
	}
	
	public void setLocaleExplicit(final String locale)
	{
		setLocale(locale);
		setParamaterToConfigured(LOCALE_NAME);
	}
	
	public void explicitSetXmlErrors(final String xmlErrors)
	{
		setXmlErrors(xmlErrors);
		setParamaterToConfigured(XML_ERRORS_NAME);
	}
	
	public BaseRestCollectionV1<RESTTranslatedTopicV1> getTranslatedTopics_OTM()
	{
		return translatedTopics;
	}

	public void setTranslatedTopics_OTM(final BaseRestCollectionV1<RESTTranslatedTopicV1> translatedTopics)
	{
		this.translatedTopics = translatedTopics;
	}
	
	public void explicitSetTranslatedTopics_OTM(final BaseRestCollectionV1<RESTTranslatedTopicV1> translatedTopics)
	{
		this.translatedTopics = translatedTopics;
		setParamaterToConfigured(TRANSLATEDTOPICS_NAME);
	}
	

}
