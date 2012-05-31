package com.redhat.topicindex.rest.entities.interfaces;

import java.util.List;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

public interface IBaseRESTEntityV1<T extends IBaseRESTEntityV1<T>>
{
	Integer getId();
	void setId(Integer id);
	
	Integer getRevision();
	void setRevision(Integer revision);
	
	List<String> getExpand();
	void setExpand(List<String> expand);
	
	boolean getAddItem();
	void setAddItem(boolean add);
	
	boolean getRemoveItem();
	void setRemoveItem(boolean remove);
	
	BaseRestCollectionV1<T> getRevisions();
	void setRevisions(BaseRestCollectionV1<T> revisions);
	
	List<String> getConfiguredParameters();
	void setConfiguredParameters(List<String> configuredParameters);
	
	String getSelfLink();
	void setSelfLink(String selfLink);
	
	String getEditLink();
	void setEditLink(String editLink);
	
	String getDeleteLink();
	void setDeleteLink(String deleteLink);
	
	String getAddLink();
	void setAddLink(String addLink);
	
	/**
	 * Copies the data held by this instance into another in another instance.
	 * @param clone The source object to copy the data into.
	 * @param deepCopy True if copies are made of objects in collections. False if references are copied.
	 */
	void cloneInto(final IBaseRESTEntityV1<T> clone, final boolean deepCopy);
	
	/**
	 * Returns a cloned copy of this object.
	 * @param deepCopy True if copies are made of objects in collections. False if references are copied.
	 * @return a cloned copy of this object.
	 */
	T clone(final boolean deepCopy);
}
