package com.redhat.topicindex.rest.entities;

public class SnapshotTranslatedStringV1 extends BaseRESTEntityV1<SnapshotTranslatedStringV1>
{
	public static final String ORIGINALSTRING_NAME = "originalstring";
	public static final String TRANSLATEDSTRING_NAME = "translatedstring";
	public static final String SNAPSHOTTRANSLATEDDATA_NAME = "snapshottranslateddata";
	
	private SnapshotTranslatedDataV1 snapshotTranslatedData;
	private String originalString;
	private String translatedString;

	public String getOriginalString()
	{
		return originalString;
	}

	public void setOriginalString(final String originalString)
	{
		this.originalString = originalString;
	}
	
	public void setOriginalStringExplicit(final String originalString)
	{
		this.originalString = originalString;
		this.setParamaterToConfigured(ORIGINALSTRING_NAME);
	}

	public String getTranslatedString()
	{
		return translatedString;
	}

	public void setTranslatedString(final String translatedString)
	{
		this.translatedString = translatedString;
	}
	
	public void setTranslatedStringExplicit(final String translatedString)
	{
		this.translatedString = translatedString;
		this.setParamaterToConfigured(TRANSLATEDSTRING_NAME);
	}

	public SnapshotTranslatedDataV1 getSnapshotTranslatedData()
	{
		return snapshotTranslatedData;
	}

	public void setSnapshotTranslatedData(final SnapshotTranslatedDataV1 snapshotTranslatedData)
	{
		this.snapshotTranslatedData = snapshotTranslatedData;
	}
}
