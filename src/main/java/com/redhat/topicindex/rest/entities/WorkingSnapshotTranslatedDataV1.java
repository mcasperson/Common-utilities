package com.redhat.topicindex.rest.entities;

import java.util.Date;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

public class WorkingSnapshotTranslatedDataV1 extends BaseRESTEntityV1<WorkingSnapshotTranslatedDataV1>
{
	public static final String TRANSLATEDXML_NAME = "translatedxml";
	public static final String TRANSLATEDXMLRENDERED_NAME = "translatedxmlrendered";
	public static final String TRANSLATIONLOCALE_NAME = "translationlocale";
	public static final String TRANSLATEDSTRINGS_NAME = "translatedstrings_OTM";
	public static final String DATE_NAME = "date";
	public static final String SNAPSHOTTOPIC_NAME = "snapshot";
	
	private SnapshotTopicV1 snapshotTopic;
	private String translatedXml;
	private String translatedXmlRendered;
	private String translationLocale;
	private BaseRestCollectionV1<WorkingSnapshotTranslatedStringV1> translatedStrings;
	private Date updated;
	
	@Override
	public WorkingSnapshotTranslatedDataV1 clone(boolean deepCopy)
	{
		final WorkingSnapshotTranslatedDataV1 retValue = new WorkingSnapshotTranslatedDataV1();
		retValue.translatedXml = this.translatedXml;
		retValue.translatedXmlRendered = this.translatedXmlRendered;
		retValue.translationLocale = this.translationLocale;
		retValue.updated = (Date)updated.clone();
		
		if (deepCopy)
		{
			retValue.snapshotTopic = this.snapshotTopic.clone(deepCopy);
			retValue.translatedStrings = this.translatedStrings.clone(deepCopy);
		}
		else
		{
			retValue.snapshotTopic = this.snapshotTopic;
			retValue.translatedStrings = this.translatedStrings;
		}
		
		return retValue;
	}

	public SnapshotTopicV1 getSnapshotTopic()
	{
		return snapshotTopic;
	}

	public void setSnapshotTopic(final SnapshotTopicV1 snapshotTopic)
	{
		this.snapshotTopic = snapshotTopic;
	}

	public String getTranslatedXml()
	{
		return translatedXml;
	}

	public void setTranslatedXml(final String translatedXml)
	{
		this.translatedXml = translatedXml;
	}
	
	public void setTranslatedXmlExplicit(final String translatedXml)
	{
		this.translatedXml = translatedXml;
		this.setParamaterToConfigured(TRANSLATEDXML_NAME);
	}

	public String getTranslatedXmlRendered()
	{
		return translatedXmlRendered;
	}

	public void setTranslatedXmlRendered(final String translatedXmlRendered)
	{
		this.translatedXmlRendered = translatedXmlRendered;
	}
	
	public void setTranslatedXmlRenderedExplicit(final String translatedXmlRendered)
	{
		this.translatedXmlRendered = translatedXmlRendered;
		this.setParamaterToConfigured(TRANSLATEDXMLRENDERED_NAME);
	}

	public String getTranslationLocale()
	{
		return translationLocale;
	}

	public void setTranslationLocale(final String translationLocale)
	{
		this.translationLocale = translationLocale;
	}
	
	public void setTranslationLocaleExplicit(final String translationLocale)
	{
		this.translationLocale = translationLocale;
		this.setParamaterToConfigured(TRANSLATIONLOCALE_NAME);
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

	public BaseRestCollectionV1<WorkingSnapshotTranslatedStringV1> getTranslatedStrings_OTM()
	{
		return translatedStrings;
	}

	public void setTranslatedStrings_OTM(final BaseRestCollectionV1<WorkingSnapshotTranslatedStringV1> translatedStrings)
	{
		this.translatedStrings = translatedStrings;
	}
	
	public void setTranslatedStringsExplicit_OTM(final BaseRestCollectionV1<WorkingSnapshotTranslatedStringV1> translatedStrings)
	{
		this.translatedStrings = translatedStrings;
		this.setParamaterToConfigured(TRANSLATEDSTRINGS_NAME);
	}
	
	
}
