package com.redhat.topicindex.rest.entities;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

public class SnapshotTopicV1 extends BaseRESTEntityV1<SnapshotTopicV1>
{
	public static final String TOPICID_NAME = "topicid";
	public static final String TOPICREVISION_NAME = "topicrevision";
	public static final String WORKINGTRANSLATEDDATA_NAME = "workingtranslateddata_OTM";
	
	private Integer topicId;
	private Integer topicRevision;
	private BaseRestCollectionV1<WorkingSnapshotTranslatedDataV1> workingTranslatedData;
	
	@Override
	public SnapshotTopicV1 clone(boolean deepCopy)
	{
		final SnapshotTopicV1 retValue = new SnapshotTopicV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.topicId = new Integer(this.topicId);
		retValue.topicRevision = new Integer(this.topicRevision);
		
		if (deepCopy)
		{
			retValue.workingTranslatedData = this.workingTranslatedData.clone(deepCopy);
		}
		else
		{
			retValue.workingTranslatedData = this.workingTranslatedData;
		}
		
		return retValue;
	}

	public Integer getTopicId()
	{
		return topicId;
	}

	public void setTopicId(final Integer topicId)
	{
		this.topicId = topicId;
	}
	
	public void setTopicIdExplicit(final Integer topicId)
	{
		this.topicId = topicId;
		this.setParamaterToConfigured(TOPICID_NAME);
	}

	public Integer getTopicRevision()
	{
		return topicRevision;
	}

	public void setTopicRevision(final Integer topicRevision)
	{
		this.topicRevision = topicRevision;
	}
	
	public void setTopicRevisionExplicit(final Integer topicRevision)
	{
		this.topicRevision = topicRevision;
		this.setParamaterToConfigured(TOPICREVISION_NAME);
	}

	public BaseRestCollectionV1<WorkingSnapshotTranslatedDataV1> getWorkingTranslatedData_OTM()
	{
		return workingTranslatedData;
	}

	public void setWorkingTranslatedData_OTM(final BaseRestCollectionV1<WorkingSnapshotTranslatedDataV1> workingTranslatedData)
	{
		this.workingTranslatedData = workingTranslatedData;
	}
	
	public void setWorkingTranslatedDataExplicit_OTM(final BaseRestCollectionV1<WorkingSnapshotTranslatedDataV1> workingTranslatedData)
	{
		this.workingTranslatedData = workingTranslatedData;
		this.setParamaterToConfigured(WORKINGTRANSLATEDDATA_NAME);
	}
}
