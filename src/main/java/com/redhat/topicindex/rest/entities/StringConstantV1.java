package com.redhat.topicindex.rest.entities;

/**
 * A REST representation of the StringConstants database entity
 */
public class StringConstantV1 extends BaseRESTEntityV1<StringConstantV1>
{
	public static final String ID_NAME = "id";
	public static final String NAME_NAME = "name";
	public static final String VALUE_NAME = "value";
	
	private String name;
	private String value;
	
	@Override
	public StringConstantV1 clone(boolean deepCopy)
	{
		final StringConstantV1 retValue = new StringConstantV1();
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
	
	public void setNameExplicit(final String name)
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
	
	public void setValueExplicit(final String value)
	{
		this.value = value;
		this.setParamaterToConfigured(VALUE_NAME);
	}
}
