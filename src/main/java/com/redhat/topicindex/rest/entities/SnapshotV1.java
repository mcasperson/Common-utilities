package com.redhat.topicindex.rest.entities;

import java.util.Date;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

public class SnapshotV1 extends BaseRESTEntityV1<SnapshotV1>
{
	public static String NAME_NAME = "name";
	public static String DATE_NAME = "date";
	public static String SNAPSHOTTOPICS_NAME = "snapshottopics";
	public static String SNAPSHOTREVISIONS_NAME = "snapshotrevisions";

	private String name;
	private Date date;
	private BaseRestCollectionV1<SnapshotTopicV1> snaphotTopics;
	private BaseRestCollectionV1<SnapshotRevisionV1> snapshotRevisions;
	
	@Override
	public SnapshotV1 clone(boolean deepCopy)
	{
		final SnapshotV1 retValue = new SnapshotV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.name = this.name;
		retValue.date = (Date)date.clone();
		
		if (deepCopy)
		{
			retValue.snaphotTopics = this.snaphotTopics.clone(deepCopy);
			retValue.snapshotRevisions = this.snapshotRevisions.clone(deepCopy);
		}
		else
		{
			retValue.snaphotTopics = this.snaphotTopics;
			retValue.snapshotRevisions = this.snapshotRevisions;
		}
		
		return retValue;
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

	public BaseRestCollectionV1<SnapshotTopicV1> getSnaphotTopics()
	{
		return snaphotTopics;
	}

	public void setSnaphotTopics(final BaseRestCollectionV1<SnapshotTopicV1> snaphotTopics)
	{
		this.snaphotTopics = snaphotTopics;
	}
	
	public void setSnaphotTopicsExplicit(final BaseRestCollectionV1<SnapshotTopicV1> snaphotTopics)
	{
		this.snaphotTopics = snaphotTopics;
		this.setParamaterToConfigured(SNAPSHOTTOPICS_NAME);
	}

	public BaseRestCollectionV1<SnapshotRevisionV1> getSnapshotRevisions_OTM()
	{
		return snapshotRevisions;
	}

	public void setSnapshotRevisions_OTM(final BaseRestCollectionV1<SnapshotRevisionV1> snapshotRevisions)
	{
		this.snapshotRevisions = snapshotRevisions;
	}
	
	public void setSnapshotRevisionsExplicit_OTM(final BaseRestCollectionV1<SnapshotRevisionV1> snapshotRevisions)
	{
		this.snapshotRevisions = snapshotRevisions;
		this.setParamaterToConfigured(SNAPSHOTREVISIONS_NAME);
	}
}
