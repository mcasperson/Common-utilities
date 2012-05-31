package com.redhat.topicindex.rest.entities;

import com.redhat.topicindex.rest.entities.interfaces.IPropertyTagV1;

/**
 * A REST representation of the PropertyTag database entity
 */
public class PropertyTagV1 extends BaseRESTEntityV1<IPropertyTagV1> implements IPropertyTagV1
{
	public static String NAME_NAME = "name";
	public static String DESCRIPTION_NAME = "description";
	public static String VALUE_NAME = "value";
	public static String REGEX_NAME = "regex";
	public static String CANBENULL_NAME = "canbenull";
	public static String ISUNIQUE_NAME = "isunique";
	
	private String name;
	private String description;
	private String value;
	private boolean valid;
	private String regex;
	private boolean canBeNull;
	private boolean isUnique;
	
	@Override
	public PropertyTagV1 clone(boolean deepCopy)
	{
		final PropertyTagV1 retValue = new PropertyTagV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.name = this.name;
		retValue.description = description;
		retValue.value = this.value;
		retValue.valid = this.valid;
		retValue.regex = this.regex;
		retValue.canBeNull = this.canBeNull;
		retValue.isUnique = this.isUnique;
		
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

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}
	
	public void setDescriptionExplicit(final String description)
	{
		this.description = description;
		this.setParamaterToConfigured(DESCRIPTION_NAME);
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(final String value)
	{
		this.value = value;
	}
	
	public void setValueExplicit(final String value)
	{
		this.value = value;
		this.setParamaterToConfigured(VALUE_NAME);
	}

	public boolean getValid()
	{
		return valid;
	}

	public void setValid(final boolean valid)
	{
		this.valid = valid;
	}

	public String getRegex()
	{
		return regex;
	}

	public void setRegex(final String regex)
	{
		this.regex = regex;
	}
	
	public void setRegexExplicit(final String regex)
	{
		this.regex = regex;
		this.setParamaterToConfigured(REGEX_NAME);
	}

	public boolean getCanBeNull()
	{
		return canBeNull;
	}

	public void setCanBeNull(final boolean canBeNull)
	{
		this.canBeNull = canBeNull;
	}
	
	public void setCanBeNullExplicit(final boolean canBeNull)
	{
		this.canBeNull = canBeNull;
		this.setParamaterToConfigured(CANBENULL_NAME);
	}

	public boolean getIsUnique()
	{
		return isUnique;
	}

	public void setIsUnique(boolean isUnique)
	{
		this.isUnique = isUnique;
	}
	
	public void setUniqueExplicit(boolean isUnique)
	{
		this.isUnique = isUnique;
		this.setParamaterToConfigured(ISUNIQUE_NAME);
	}
}
