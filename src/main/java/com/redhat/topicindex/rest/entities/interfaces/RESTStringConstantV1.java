package com.redhat.topicindex.rest.entities.interfaces;


/**
 * A REST representation of the StringConstants database entity
 */
public class RESTStringConstantV1 extends RESTBaseEntityV1<RESTStringConstantV1>
{
	public static final String ID_NAME = "id";
	public static final String NAME_NAME = "name";
	public static final String VALUE_NAME = "value";
	
	private String name;
	private String value;
	
	@Override
	public RESTStringConstantV1 clone(boolean deepCopy)
	{
		final RESTStringConstantV1 retValue = new RESTStringConstantV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.name = this.name;
		retValue.value = value;
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
	
	public void explicitSetName(final String name)
	{
		this.name = name;
		this.setParamaterToConfigured(NAME_NAME);
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(final String value)
	{
		this.value = value;
	}
	
	public void explicitSetValue(final String value)
	{
		this.value = value;
		this.setParamaterToConfigured(VALUE_NAME);
	}
}