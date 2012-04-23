package com.redhat.topicindex.rest.entities;

import java.util.Date;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

public class SnapshotRevisionV1 extends BaseRESTEntityV1<SnapshotRevisionV1>
{
	public static final String SNAPSHOT_NAME = "snapshot";
	public static final String NAME_NAME = "name";
	public static final String DATE_NAME = "date";
	public static final String SNAPSHOTTRANSLATEDDATAENTITIES_NAME = "snapshottranslateddataentities_OTM";

	private SnapshotV1 snapshot;
	private String name;
	private Date date;
	private BaseRestCollectionV1<SnapshotTranslatedDataV1> snapshotTranslatedDataEntities;
	
	@Override
	public SnapshotRevisionV1 clone(boolean deepCopy)
	{
		final SnapshotRevisionV1 retValue = new SnapshotRevisionV1();
		retValue.name = this.name;
		retValue.date = (Date)this.date.clone();
		
		if (deepCopy)
		{
			retValue.snapshot = this.snapshot.clone(deepCopy);
			retValue.snapshotTranslatedDataEntities = this.snapshotTranslatedDataEntities.clone(deepCopy);
		}
		else
		{
			retValue.snapshot = this.snapshot;
			retValue.snapshotTranslatedDataEntities = this.snapshotTranslatedDataEntities;
		}
		
		return retValue;
	}

	public SnapshotV1 getSnapshot()
	{
		return snapshot;
	}

	public void setSnapshot(final SnapshotV1 snapshot)
	{
		this.snapshot = snapshot;
	}

	public String getName()
	{
		return name;
	}

	public void setName(final String name)
	{
		this.name = name;
	}
	
	public void setNameExplicit(final String name)
	{
		this.name = name;
		this.setParamaterToConfigured(NAME_NAME);
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(final Date date)
	{
		this.date = date;
	}
	
	public void setDateExplicit(final Date date)
	{
		this.date = date;
		this.setParamaterToConfigured(DATE_NAME);
	}

	public BaseRestCollectionV1<SnapshotTranslatedDataV1> getSnapshotTranslatedDataEntities_OTM()
	{
		return snapshotTranslatedDataEntities;
	}

	public void setSnapshotTranslatedDataEntities_OTM(final BaseRestCollectionV1<SnapshotTranslatedDataV1> snapshotTranslatedDataEntities)
	{
		this.snapshotTranslatedDataEntities = snapshotTranslatedDataEntities;
	}
	
	public void setSnapshotTranslatedDataEntitiesExplicit_OTM(final BaseRestCollectionV1<SnapshotTranslatedDataV1> snapshotTranslatedDataEntities)
	{
		this.snapshotTranslatedDataEntities = snapshotTranslatedDataEntities;
		this.setParamaterToConfigured(SNAPSHOTTRANSLATEDDATAENTITIES_NAME);
	}

}
