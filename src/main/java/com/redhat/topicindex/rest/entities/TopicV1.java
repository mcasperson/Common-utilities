package com.redhat.topicindex.rest.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import com.redhat.ecs.constants.CommonConstants;
import com.redhat.ecs.services.docbookcompiling.DocbookBuilderConstants;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

/**
 * A REST representation of the Topic entity
 */
@XmlRootElement(name = "topic")
public class TopicV1 extends BaseTopicV1<TopicV1>
{	
	public static final String DESCRIPTION_NAME = "description";
	public static final String BUGZILLABUGS_NAME = "bugzillabugs_OTM";
	public static final String TRANSLATEDTOPICS_NAME = "translatedtopics_OTM";
	
	private String description = null;
	private Date created = null;
	private Date lastModified = null;
	private BaseRestCollectionV1<BugzillaBugV1> bugzillaBugs = null;
	private BaseRestCollectionV1<TranslatedTopicV1> translatedTopics = null;
	
	@Override
	public TopicV1 clone(final boolean deepCopy)
	{
		final TopicV1 retValue = new TopicV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.description = this.description;
		retValue.created = (Date)this.created.clone();
		retValue.lastModified = (Date)lastModified.clone();
		
		if (deepCopy)
		{
			retValue.bugzillaBugs = this.bugzillaBugs.clone(deepCopy);
			retValue.translatedTopics = this.translatedTopics.clone(deepCopy);
		}
		else
		{
			retValue.bugzillaBugs = this.bugzillaBugs;
			retValue.translatedTopics = this.translatedTopics;
		}
		return retValue;
		
	}

	public void setTitleExplicit(final String title)
	{
		setTitle(title);
		setParamaterToConfigured(TITLE_NAME);
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
	
	public void setDescriptionExplicit(final String description)
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

	public void setXmlExplicit(final String xml)
	{
		setXml(xml);
		setParamaterToConfigured(XML_NAME);
	}

	public void setHtmlExplicit(final String html)
	{
		setHtml(html);
		setParamaterToConfigured(HTML_NAME);
	}

	public void setTagsExplicit(final BaseRestCollectionV1<TagV1> tags)
	{
		setTags(tags);
		setParamaterToConfigured(TAGS_NAME);
	}

	public void setOutgoingRelationshipsExplicit(final BaseRestCollectionV1<TopicV1> outgoingRelationships)
	{
		setOutgoingRelationships(outgoingRelationships);
		setParamaterToConfigured(OUTGOING_NAME);
	}

	public void setIncomingRelationshipsExplicit(final BaseRestCollectionV1<TopicV1> incomingRelationships)
	{
		setIncomingRelationships(incomingRelationships);
		setParamaterToConfigured(INCOMING_NAME);
	}

	public Date getLastModified()
	{
		return lastModified;
	}

	public void setLastModified(final Date lastModified)
	{
		this.lastModified = lastModified;
	}
	
	@Override
	@XmlTransient
	@JsonIgnore
	public String getSkynetURL()
	{
		return CommonConstants.SERVER_URL + "/TopicIndex/CustomSearchTopicList.seam?topicIds=" + this.getId();
	}

	/**
	 * @return The value to be saved into the Build ID field of any bugzilla
	 *         bugs assigned to this topic.
	 */
	@Override
	@XmlTransient
	@JsonIgnore
	public String getBugzillaBuildId()
	{
		final SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.FILTER_DISPLAY_DATE_FORMAT);
		return this.getId() + "-" + getRevision() + " " + (this.lastModified == null ? formatter.format(this.lastModified) : formatter.format(new Date())) + " " + getLocale();
	}
	
	public void setSourceUrlsExplicit_OTM(final BaseRestCollectionV1<TopicSourceUrlV1> sourceUrls)
	{
		setSourceUrls_OTM(sourceUrls);
		setParamaterToConfigured(SOURCE_URLS_NAME);
	}
	
	@XmlElement
	public BaseRestCollectionV1<BugzillaBugV1> getBugzillaBugs_OTM()
	{
		return bugzillaBugs;
	}

	public void setBugzillaBugs_OTM(final BaseRestCollectionV1<BugzillaBugV1> bugzillaBugs)
	{
		this.bugzillaBugs = bugzillaBugs;
	}
	
	public void setBugzillaBugsExplicit_OTM(final BaseRestCollectionV1<BugzillaBugV1> bugzillaBugs)
	{
		setBugzillaBugs_OTM(bugzillaBugs);
		setParamaterToConfigured(BUGZILLABUGS_NAME);
	}
	
	public void setLocaleExplicit(final String locale)
	{
		setLocale(locale);
		setParamaterToConfigured(LOCALE_NAME);
	}
	
	public void setXmlErrorsExplicit(final String xmlErrors)
	{
		setXmlErrors(xmlErrors);
		setParamaterToConfigured(XML_ERRORS_NAME);
	}
	
	@XmlElement
	public BaseRestCollectionV1<TranslatedTopicV1> getTranslatedTopics_OTM()
	{
		return translatedTopics;
	}

	public void setTranslatedTopics_OTM(final BaseRestCollectionV1<TranslatedTopicV1> translatedTopics)
	{
		this.translatedTopics = translatedTopics;
	}

	@Override
	@XmlTransient
	@JsonIgnore
	public String getInternalURL() {
		return "Topic.seam?topicTopicId=" + getId() + "&selectedTab=Rendered+View";
	}
	
	@XmlTransient
	@JsonIgnore
	public String getXRefID()
	{
		return "TopicID" + this.getId();
	}
	
	@XmlTransient
	@JsonIgnore
	public String getErrorXRefID()
	{
		return DocbookBuilderConstants.ERROR_XREF_ID_PREFIX + this.getId();
	}
}
