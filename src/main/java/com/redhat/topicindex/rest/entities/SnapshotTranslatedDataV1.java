package com.redhat.topicindex.rest.entities;

import java.util.Date;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

/**
 * A REST representation of the SnapshotTranslatedData entity
 */
public class SnapshotTranslatedDataV1 extends BaseRESTEntityV1<SnapshotTranslatedDataV1>
{
	public static final String SNAPSHOTTOPIC_NAME = "snapshottopic";
	public static final String SNAPSHOTREVISION_NAME = "snapshotrevision";
	public static final String XML_NAME = "xml";
	public static final String RENDEREDXML_NAME = "renderedxml";
	public static final String LOCALE_NAME = "locale";
	public static final String DATE_NAME = "date";
	public static final String TRANSLATEDSTRINGS_NAME = "translatedstrings_OTM";
	
	private SnapshotTopicV1 snapshotTopic;
	private SnapshotRevisionV1 snapshotRevision;
	private String xml;
	private String renderedXml;
	private String locale;
	private Date updated;
	private BaseRestCollectionV1<SnapshotTranslatedStringV1> translatedStrings;
	
	@Override
	public SnapshotTranslatedDataV1 clone(boolean deepCopy)
	{
		final SnapshotTranslatedDataV1 retValue = new SnapshotTranslatedDataV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.xml = this.xml;
		retValue.renderedXml = renderedXml;
		retValue.locale = this.locale;
		retValue.updated = (Date)this.updated.clone();
		
		if (deepCopy)
		{
			retValue.translatedStrings = this.translatedStrings.clone(deepCopy);
			retValue.snapshotTopic = this.snapshotTopic.clone(deepCopy);
			retValue.snapshotRevision = this.snapshotRevision.clone(deepCopy);
		}
		else
		{
			retValue.translatedStrings = this.translatedStrings;
			retValue.snapshotTopic = this.snapshotTopic;
			retValue.snapshotRevision = this.snapshotRevision;
		}
		
		return retValue;
	}
	
	public String getXml()
	{
		return xml;
	}

	public void setXml(final String xml)
	{
		this.xml = xml;
	}
	
	public void setXmlExplicit(final String xml)
	{
		this.xml = xml;
		this.setParamaterToConfigured(XML_NAME);
	}

	public String getRenderedXml()
	{
		return renderedXml;
	}

	public void setRenderedXml(final String renderedXml)
	{
		this.renderedXml = renderedXml;
	}
	
	public void setRenderedXmlExplicit(final String renderedXml)
	{
		this.renderedXml = renderedXml;
		this.setParamaterToConfigured(RENDEREDXML_NAME);
	}

	public String getLocale()
	{
		return locale;
	}

	public void setLocale(final String locale)
	{
		this.locale = locale;
	}
	
	public void setLocaleExplicit(final String locale)
	{
		this.locale = locale;
		this.setParamaterToConfigured(LOCALE_NAME);
	}

	public SnapshotTopicV1 getSnapshotTopic()
	{
		return snapshotTopic;
	}

	public void setSnapshotTopic(final SnapshotTopicV1 snapshotTopic)
	{
		this.snapshotTopic = snapshotTopic;
	}

	public SnapshotRevisionV1 getSnapshotRevision()
	{
		return snapshotRevision;
	}

	public void setSnapshotRevision(final SnapshotRevisionV1 snapshotRevision)
	{
		this.snapshotRevision = snapshotRevision;
	}
	
	public void setSnapshotRevisionExplicit(final SnapshotRevisionV1 snapshotRevision)
	{
		this.snapshotRevision = snapshotRevision;
		this.setParamaterToConfigured(SNAPSHOTREVISION_NAME);
	}

	public Date getUpdated()
	{
		return updated;
	}

	public void setUpdated(final Date updated)
	{
		this.updated = updated;
	}
	
	public void setUpdatedExplicit(final Date updated)
	{
		this.updated = updated;
		this.setParamaterToConfigured(DATE_NAME);
	}

	public BaseRestCollectionV1<SnapshotTranslatedStringV1> getTranslatedStrings_OTM()
	{
		return translatedStrings;
	}

	public void setTranslatedStrings_OTM(final BaseRestCollectionV1<SnapshotTranslatedStringV1> translatedStrings)
	{
		this.translatedStrings = translatedStrings;
	}
	
	public void setTranslatedStringsExplicit_OTM(final BaseRestCollectionV1<SnapshotTranslatedStringV1> translatedStrings)
	{
		this.translatedStrings = translatedStrings;
		this.setParamaterToConfigured(TRANSLATEDSTRINGS_NAME);
	}
	
	
}
