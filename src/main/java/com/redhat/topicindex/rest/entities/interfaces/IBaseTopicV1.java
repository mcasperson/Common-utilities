package com.redhat.topicindex.rest.entities.interfaces;

import java.util.List;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

public interface IBaseTopicV1<T extends IBaseRESTEntityWithPropertiesV1<T>> extends IBaseRESTEntityWithPropertiesV1<T>
{
	public static final String TITLE_NAME = "title";
	public static final String XML_NAME = "xml";
	public static final String XML_ERRORS_NAME = "xmlErrors";
	public static final String HTML_NAME = "html";
	public static final String TAGS_NAME = "tags";
	public static final String OUTGOING_NAME = "outgoingRelationships";
	public static final String INCOMING_NAME = "incomingRelationships";
	public static final String LOCALE_NAME = "locale";
	public static final String SOURCE_URLS_NAME = "sourceUrls_OTM";
	public static final String PROPERTIES_NAME = "properties";
	
	String getTitle();
	void setTitle(String title);
	void explicitSetTitle(String title);
	
	String getXml();
	void setXml(String xml);
	void explicitSetXml(String xml);
	
	String getHtml();
	void setHtml(String html);
	void explicitSetHtml(String html);
	
	String getXmlErrors();
	void setXmlErrors(String xmlErrors);
	void explicitSetXmlErrors(String xmlErrors);
	
	String getLocale();
	void setLocale(String locale);
	void explicitSetLocale(String locale);
	
	BaseRestCollectionV1<ITagV1> getTags();
	void setTags(BaseRestCollectionV1<ITagV1> tags);
	void explicitSetTags(BaseRestCollectionV1<ITagV1> tags);
	
	BaseRestCollectionV1<T> getOutgoingRelationships();	
	void setOutgoingRelationships(BaseRestCollectionV1<T> outgoingRelationships);
	void explicitSetOutgoingRelationships(BaseRestCollectionV1<T> outgoingRelationships);
	
	BaseRestCollectionV1<T> getIncomingRelationships();	
	void setIncomingRelationships(BaseRestCollectionV1<T> incomingRelationships);
	void explicitSetIncomingRelationships(BaseRestCollectionV1<T> incomingRelationships);
	
	BaseRestCollectionV1<ITopicSourceUrlV1> getSourceUrls_OTM();
	void setSourceUrls_OTM(BaseRestCollectionV1<ITopicSourceUrlV1> sourceUrls);
	void explicitSetSourceUrls_OTM(BaseRestCollectionV1<ITopicSourceUrlV1> sourceUrls);
	
	List<ITagV1> returnTagsInCategoriesByID(final List<Integer> categories);
	String returnSkynetURL();
	String returnInternalURL();
	String returnXRefID();
	String returnBugzillaBuildId();
	String returnErrorXRefID();
	boolean hasTag(Integer tagId);
	String returnXrefPropertyOrId(final Integer propertyTagId);
	boolean hasRelationshipTo(final Integer id);
	T returnRelatedTopicByID(final Integer id);
}
