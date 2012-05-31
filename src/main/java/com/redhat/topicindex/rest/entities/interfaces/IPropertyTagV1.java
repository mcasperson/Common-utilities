package com.redhat.topicindex.rest.entities.interfaces;

import javax.xml.bind.annotation.XmlSeeAlso;

import com.redhat.topicindex.rest.entities.PropertyTagV1;

@XmlSeeAlso(PropertyTagV1.class)
public interface IPropertyTagV1 extends IBaseRESTEntityV1<IPropertyTagV1>
{
	public static String NAME_NAME = "name";
	public static String DESCRIPTION_NAME = "description";
	public static String VALUE_NAME = "value";
	public static String REGEX_NAME = "regex";
	public static String CANBENULL_NAME = "canbenull";
	public static String ISUNIQUE_NAME = "isunique";
	
	String getName();
	void setName(String name);
	void explicitSetName(String name);
	
	String getDescription();
	void setDescription(String description);
	void explicitSetDescription(String description);
	
	String getValue();
	void setValue(String valid);
	void explicitSetValue(String valid);
	
	boolean getValid();
	void setValid(boolean valid);
	
	String getRegex();
	void setRegex(String regex);
	void explicitSetRegex(String regex);
	
	boolean getCanBeNull();
	void setCanBeNull(boolean canBeNull);
	void explicitSetCanBeNull(boolean canBeNull);
	
	boolean getIsUnique();
	void setIsUnique(boolean isUnique);
	void explicitSetIsUnique(boolean isUnique);
}
