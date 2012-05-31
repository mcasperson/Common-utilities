package com.redhat.topicindex.rest.entities.interfaces;

public interface IBugzillaBugV1 extends IBaseRESTEntityV1<IBugzillaBugV1>
{
	Integer getBugId();
	void setBugId(Integer bugId);
	
	Boolean getIsOpen();
	void setIsOpen(Boolean isOpen);
	
	String getSummary();
	void setSummary(String summary);
}
