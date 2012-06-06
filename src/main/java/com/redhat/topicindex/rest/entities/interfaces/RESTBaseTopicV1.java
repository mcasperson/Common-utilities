package com.redhat.topicindex.rest.entities.interfaces;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

public abstract class RESTBaseTopicV1<T extends RESTBaseEntityWithPropertiesV1<T>> extends RESTBaseEntityWithPropertiesV1<T>
{
	public static final String TITLE_NAME = "title";
	public static final String XML_NAME = "xml";
	public static final String XML_ERRORS_NAME = "xmlErrors";
	public static final String HTML_NAME = "html";
	public static final String TAGS_NAME = "tags";
	public static final String OUTGOING_NAME = "outgoingRelationships";
	public static final String INCOMING_NAME = "incomingRelationships";
	public static final String LOCALE_NAME = "locale";
	public static final String SOURCE_URLS_NAME = "sourceUrls_OTM";
	public static final String PROPERTIES_NAME = "properties";
	
	protected String title = null;
	protected String xml = null;
	protected String xmlErrors = null;
	protected String html = null;
	protected Integer revision = 0;
	protected String locale = null;
	protected BaseRestCollectionV1<RESTTagV1> tags = null;
	protected BaseRestCollectionV1<T> outgoingRelationships = null;
	protected BaseRestCollectionV1<T> incomingRelationships = null;
	protected BaseRestCollectionV1<RESTTopicSourceUrlV1> sourceUrls_OTM = null;
	
	public void cloneInto(final RESTBaseTopicV1<T> clone, final boolean deepCopy)
	{
		super.cloneInto(clone, deepCopy);
		
		clone.title = this.title;
		clone.xml = this.xml;
		clone.xmlErrors = this.xmlErrors;
		clone.html = this.html;
		clone.revision = this.revision;
		clone.locale = this.locale;
		
		if (deepCopy)
		{
			clone.tags = this.tags == null ? null: this.tags.clone(deepCopy);
			clone.outgoingRelationships = this.outgoingRelationships == null ? null : this.outgoingRelationships.clone(deepCopy);
			clone.incomingRelationships = this.incomingRelationships == null ? null : this.incomingRelationships.clone(deepCopy);
			clone.sourceUrls_OTM = this.sourceUrls_OTM == null ? null : this.sourceUrls_OTM.clone(deepCopy);
		}
		else
		{
			clone.tags = this.tags;
			clone.outgoingRelationships = this.outgoingRelationships;
			clone.incomingRelationships = this.incomingRelationships;
			clone.sourceUrls_OTM = this.sourceUrls_OTM;
		}
	}
	
	@Override
	public Integer getRevision()
	{
		return revision;
	}

	@Override
	public void setRevision(final Integer revision)
	{
		this.revision = revision;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(final String title)
	{
		this.title = title;
	}
	
	public void explicitSetTitle(final String title)
	{
		setTitle(title);
		setParamaterToConfigured(TITLE_NAME);
	}
	
	public String getXml()
	{
		return xml;
	}
	
	public void setXml(final String xml)
	{
		this.xml = xml;
	}
	
	public String getHtml()
	{
		return html;
	}

	public void setHtml(final String html)
	{
		this.html = html;
	}
	
	public String getLocale()
	{
		return locale;
	}

	public void setLocale(final String locale)
	{
		this.locale = locale;
	}

	public String getXmlErrors()
	{
		return xmlErrors;
	}

	public void setXmlErrors(final String xmlErrors)
	{
		this.xmlErrors = xmlErrors;
	}
	
	public BaseRestCollectionV1<RESTTagV1> getTags()
	{
		return tags;
	}
	
	public void explicitSetTags(final BaseRestCollectionV1<RESTTagV1> tags)
	{
		setTags(tags);
		setParamaterToConfigured(TAGS_NAME);
	}

	public void setTags(final BaseRestCollectionV1<RESTTagV1> tags)
	{
		this.tags = tags;
	}
	
	public void addTag(final RESTTagV1 tag)
	{
		if (this.tags == null)
			this.tags = new BaseRestCollectionV1<RESTTagV1>();

		this.tags.addItem(tag);
	}
	
	public BaseRestCollectionV1<T> getOutgoingRelationships()
	{
		return outgoingRelationships;
	}

	public void setOutgoingRelationships(final BaseRestCollectionV1<T> outgoingRelationships)
	{
		this.outgoingRelationships = outgoingRelationships;
	}
	
	public void explicitSetOutgoingRelationships(final BaseRestCollectionV1<T> outgoingRelationships)
	{
		setOutgoingRelationships(outgoingRelationships);
		setParamaterToConfigured(OUTGOING_NAME);
	}

	public BaseRestCollectionV1<T> getIncomingRelationships()
	{
		return incomingRelationships;
	}

	public void setIncomingRelationships(final BaseRestCollectionV1<T> incomingRelationships)
	{
		this.incomingRelationships = incomingRelationships;
	}
	
	public void explicitSetIncomingRelationships(final BaseRestCollectionV1<T> incomingRelationships)
	{
		setIncomingRelationships(incomingRelationships);
		setParamaterToConfigured(INCOMING_NAME);
	}

	public BaseRestCollectionV1<RESTTopicSourceUrlV1> getSourceUrls_OTM()
	{
		return sourceUrls_OTM;
	}

	public void setSourceUrls_OTM(final BaseRestCollectionV1<RESTTopicSourceUrlV1> sourceUrls)
	{
		this.sourceUrls_OTM = sourceUrls;		
	}
	
	public void explicitSetSourceUrls_OTM(final BaseRestCollectionV1<RESTTopicSourceUrlV1> sourceUrls)
	{
		setSourceUrls_OTM(sourceUrls);
		setParamaterToConfigured(SOURCE_URLS_NAME);
	}
}
