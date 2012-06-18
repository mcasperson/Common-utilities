package com.redhat.topicindex.rest.entities.interfaces;

import java.util.Date;

import com.redhat.topicindex.rest.collections.RESTBugzillaBugCollectionV1;
import com.redhat.topicindex.rest.collections.RESTTopicCollectionV1;
import com.redhat.topicindex.rest.collections.RESTTranslatedTopicCollectionV1;

public class RESTTopicV1 extends RESTBaseTopicV1<RESTTopicV1>
{
	public static final String DESCRIPTION_NAME = "description";
	public static final String BUGZILLABUGS_NAME = "bugzillabugs_OTM";
	public static final String TRANSLATEDTOPICS_NAME = "translatedtopics_OTM";
	
	protected String description = null;
	protected Date created = null;
	protected Date lastModified = null;
	protected RESTBugzillaBugCollectionV1 bugzillaBugs_OTM = null;
	protected RESTTranslatedTopicCollectionV1 translatedTopics_OTM = null;
	
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
			if (this.bugzillaBugs_OTM == null)
				retValue.bugzillaBugs_OTM = null;
			else
				this.bugzillaBugs_OTM.cloneInto(retValue.bugzillaBugs_OTM, deepCopy);
			
			if (this.translatedTopics_OTM == null)
				retValue.translatedTopics_OTM = null;
			else
				this.translatedTopics_OTM.cloneInto(retValue.translatedTopics_OTM, deepCopy);
			
			if (this.outgoingRelationships == null)
				retValue.outgoingRelationships = null;
			else
				this.outgoingRelationships.cloneInto(retValue.outgoingRelationships, deepCopy);
			
			if (this.incomingRelationships == null)
				retValue.incomingRelationships = null;
			else
				this.incomingRelationships.cloneInto(retValue.incomingRelationships, deepCopy);
		}
		else
		{
			retValue.bugzillaBugs_OTM = this.bugzillaBugs_OTM;
			retValue.translatedTopics_OTM = this.translatedTopics_OTM;
			retValue.outgoingRelationships = this.outgoingRelationships;
			retValue.incomingRelationships = this.incomingRelationships;
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
	
	public RESTBugzillaBugCollectionV1 getBugzillaBugs_OTM()
	{
		return bugzillaBugs_OTM;
	}

	public void setBugzillaBugs_OTM(final RESTBugzillaBugCollectionV1 bugzillaBugs)
	{
		this.bugzillaBugs_OTM = bugzillaBugs;
	}
	
	public void explicitSetBugzillaBugs_OTM(final RESTBugzillaBugCollectionV1 bugzillaBugs)
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
	
	public RESTTranslatedTopicCollectionV1 getTranslatedTopics_OTM()
	{
		return translatedTopics_OTM;
	}

	public void setTranslatedTopics_OTM(final RESTTranslatedTopicCollectionV1 translatedTopics)
	{
		this.translatedTopics_OTM = translatedTopics;
	}
	
	public void explicitSetTranslatedTopics_OTM(final RESTTranslatedTopicCollectionV1 translatedTopics)
	{
		this.translatedTopics_OTM = translatedTopics;
		setParamaterToConfigured(TRANSLATEDTOPICS_NAME);
	}
	
	public RESTTopicCollectionV1 getOutgoingRelationships()
	{
		return (RESTTopicCollectionV1)outgoingRelationships;
	}

	public void setOutgoingRelationships(final RESTTopicCollectionV1 outgoingRelationships)
	{
		this.outgoingRelationships = outgoingRelationships;
	}
	
	public void explicitSetOutgoingRelationships(final RESTTopicCollectionV1 outgoingRelationships)
	{
		setOutgoingRelationships(outgoingRelationships);
		setParamaterToConfigured(OUTGOING_NAME);
	}

	public RESTTopicCollectionV1 getIncomingRelationships()
	{
		return (RESTTopicCollectionV1)incomingRelationships;
	}

	public void setIncomingRelationships(final RESTTopicCollectionV1 incomingRelationships)
	{
		this.incomingRelationships = incomingRelationships;
	}
	
	public void explicitSetIncomingRelationships(final RESTTopicCollectionV1 incomingRelationships)
	{
		setIncomingRelationships(incomingRelationships);
		setParamaterToConfigured(INCOMING_NAME);
	}
}
