package com.redhat.topicindex.rest.entities;

public class WorkingSnapshotTranslatedStringV1 extends BaseRESTEntityV1<WorkingSnapshotTranslatedStringV1>
{
	public static final String ORIGINALSTRING_NAME = "originalstring";
	public static final String TRANSLATEDSTRING_NAME = "translatedstring";
	public static final String WORKINGSNAPSHOTTRANSLATEDDATA_NAME = "workingsnapshottranslateddata";
	
	private WorkingSnapshotTranslatedDataV1 workingSnapshotTranslatedData;
	private String originalString;
	private String translatedString;
	
	@Override
	public WorkingSnapshotTranslatedStringV1 clone(boolean deepCopy)
	{
		final WorkingSnapshotTranslatedStringV1 retValue = new WorkingSnapshotTranslatedStringV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.originalString = this.originalString;
		retValue.translatedString = translatedString;
		
		if (deepCopy)
		{
			retValue.workingSnapshotTranslatedData = this.workingSnapshotTranslatedData.clone(deepCopy);
		}
		else
		{
			retValue.workingSnapshotTranslatedData = this.workingSnapshotTranslatedData;
		}
		
		return retValue;
	}

	public WorkingSnapshotTranslatedDataV1 getWorkingSnapshotTranslatedData()
	{
		return workingSnapshotTranslatedData;
	}

	public void setWorkingSnapshotTranslatedData(final WorkingSnapshotTranslatedDataV1 workingSnapshotTranslatedData)
	{
		this.workingSnapshotTranslatedData = workingSnapshotTranslatedData;
	}

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
}
