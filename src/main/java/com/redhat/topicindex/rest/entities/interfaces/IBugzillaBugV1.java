package com.redhat.topicindex.rest.entities.interfaces;


public interface IBugzillaBugV1 extends IBaseRESTEntityV1<IBugzillaBugV1>
{
	public static final String BUG_ID = "bugzillabugid";
	public static final String BUG_ISOPEN = "bugisopen";
	public static final String BUG_SUMMARY = "bugsummary";
	
	Integer getBugId();
	void setBugId(Integer bugId);
	
	Boolean getIsOpen();
	void setIsOpen(Boolean isOpen);
	
	String getSummary();
	void setSummary(String summary);
}
