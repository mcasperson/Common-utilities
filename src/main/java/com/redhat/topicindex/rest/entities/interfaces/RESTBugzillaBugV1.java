package com.redhat.topicindex.rest.entities.interfaces;

import com.redhat.topicindex.rest.collections.RESTBugzillaBugCollectionV1;

public class RESTBugzillaBugV1 extends RESTBaseEntityV1<RESTBugzillaBugV1, RESTBugzillaBugCollectionV1>
{
	public static final String BUG_ID = "bugzillabugid";
	public static final String BUG_ISOPEN = "bugisopen";
	public static final String BUG_SUMMARY = "bugsummary";
	
	private Integer bugId = null;
	private Boolean isOpen = null;
	private String summary = null;
	/** A list of the Envers revision numbers */
	private RESTBugzillaBugCollectionV1 revisions = null;
	
	@Override
	public RESTBugzillaBugCollectionV1 getRevisions()
	{
		return revisions;
	}

	@Override
	public void setRevisions(final RESTBugzillaBugCollectionV1 revisions)
	{
		this.revisions = revisions;
	}
	
	@Override
	public RESTBugzillaBugV1 clone(boolean deepCopy)
	{
		final RESTBugzillaBugV1 retValue = new RESTBugzillaBugV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.bugId = this.bugId == null ? null : new Integer(this.bugId);
		retValue.isOpen = this.isOpen == null ? null : new Boolean(this.isOpen);
		retValue.summary = summary;
		
		
		if (deepCopy)
		{
			if (this.revisions != null)
			{
				retValue.revisions = new RESTBugzillaBugCollectionV1();
				this.revisions.cloneInto(retValue.revisions, deepCopy);
			}			
		}
		else
		{
			retValue.revisions = this.revisions;
		}
		
		return retValue;
	}

	public Integer getBugId()
	{
		return bugId;
	}

	public void setBugId(final Integer bugId)
	{
		this.bugId = bugId;
	}
	
	public void setBugIdExplicit(final Integer bugId)
	{
		this.bugId = bugId;
		this.setParamaterToConfigured(BUG_ID);
	}

	public Boolean getIsOpen()
	{
		return isOpen;
	}

	public void setIsOpen(final Boolean isOpen)
	{
		this.isOpen = isOpen;
	}
	
	public void setIsOpenExplicit(final Boolean isOpen)
	{
		this.isOpen = isOpen;
		this.setParamaterToConfigured(BUG_ISOPEN);
	}

	public String getSummary()
	{
		return summary;
	}

	public void setSummary(final String summary)
	{
		this.summary = summary;
	}
	
	public void setSummaryExplicit(final String summary)
	{
		this.summary = summary;
		this.setParamaterToConfigured(BUG_SUMMARY);
	}



}
