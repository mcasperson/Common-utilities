package com.redhat.topicindex.rest.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.redhat.ecs.constants.CommonConstants;
import com.redhat.ecs.services.docbookcompiling.DocbookBuilderConstants;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.IBugzillaBugV1;
import com.redhat.topicindex.rest.entities.interfaces.ITagV1;
import com.redhat.topicindex.rest.entities.interfaces.ITopicSourceUrlV1;
import com.redhat.topicindex.rest.entities.interfaces.ITopicV1;
import com.redhat.topicindex.rest.entities.interfaces.ITranslatedTopicV1;

/**
 * A REST representation of the Topic entity
 */
@XmlRootElement(name = "topic")
public class TopicV1 extends BaseTopicV1<ITopicV1> implements ITopicV1
{	
	private String description = null;
	private Date created = null;
	private Date lastModified = null;
	private BaseRestCollectionV1<IBugzillaBugV1> bugzillaBugs = null;
	private BaseRestCollectionV1<ITranslatedTopicV1> translatedTopics = null;
	
	@Override
	public TopicV1 clone(final boolean deepCopy)
	{
		final TopicV1 retValue = new TopicV1();
		
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



	@XmlElement
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
	
	@XmlElement
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
	
	public String returnSkynetURL()
	{
		return CommonConstants.SERVER_URL + "/TopicIndex/CustomSearchTopicList.seam?topicIds=" + this.getId();
	}

	/**
	 * @return The value to be saved into the Build ID field of any bugzilla
	 *         bugs assigned to this topic.
	 */
	public String returnBugzillaBuildId()
	{
		final SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.FILTER_DISPLAY_DATE_FORMAT);
		return this.getId() + "-" + getRevision() + " " + (this.lastModified == null ? formatter.format(this.lastModified) : formatter.format(new Date())) + " " + getLocale();
	}
	
	@XmlElement
	public BaseRestCollectionV1<IBugzillaBugV1> getBugzillaBugs_OTM()
	{
		return bugzillaBugs;
	}

	public void setBugzillaBugs_OTM(final BaseRestCollectionV1<IBugzillaBugV1> bugzillaBugs)
	{
		this.bugzillaBugs = bugzillaBugs;
	}
	
	public void explicitSetBugzillaBugs_OTM(final BaseRestCollectionV1<IBugzillaBugV1> bugzillaBugs)
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
	
	@XmlElement
	public BaseRestCollectionV1<ITranslatedTopicV1> getTranslatedTopics_OTM()
	{
		return translatedTopics;
	}

	public void setTranslatedTopics_OTM(final BaseRestCollectionV1<ITranslatedTopicV1> translatedTopics)
	{
		this.translatedTopics = translatedTopics;
	}
	
	public void explicitSetTranslatedTopics_OTM(final BaseRestCollectionV1<ITranslatedTopicV1> translatedTopics)
	{
		this.translatedTopics = translatedTopics;
		setParamaterToConfigured(TRANSLATEDTOPICS_NAME);
	}

	public String returnInternalURL() {
		return "Topic.seam?topicTopicId=" + getId() + "&selectedTab=Rendered+View";
	}
	
	public String returnXRefID()
	{
		return "TopicID" + this.getId();
	}

	public String returnErrorXRefID()
	{
		return DocbookBuilderConstants.ERROR_XREF_ID_PREFIX + this.getId();
	}
}
