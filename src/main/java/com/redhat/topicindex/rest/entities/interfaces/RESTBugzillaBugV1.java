package com.redhat.topicindex.rest.entities.interfaces;

public class RESTBugzillaBugV1 extends RESTBaseEntityV1<RESTBugzillaBugV1>
{
	public static final String BUG_ID = "bugzillabugid";
	public static final String BUG_ISOPEN = "bugisopen";
	public static final String BUG_SUMMARY = "bugsummary";
	
	private Integer bugId;
	private Boolean isOpen;
	private String summary;
	
	@Override
	public RESTBugzillaBugV1 clone(boolean deepCopy)
	{
		final RESTBugzillaBugV1 retValue = new RESTBugzillaBugV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.bugId = new Integer(this.bugId);
		retValue.isOpen = new Boolean(this.isOpen);
		retValue.summary = summary;
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