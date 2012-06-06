package com.redhat.topicindex.rest.entities.interfaces;


/**
 * A REST representation of the BlobConstants database entity
 */
public class RESTBlobConstantV1 extends RESTBaseEntityV1<RESTBlobConstantV1>
{
	public static final String NAME_NAME = "name";
	public static final String VALUE_NAME = "value";
	
	private String name;
	private byte[] value;
	
	@Override
	public RESTBlobConstantV1 clone(boolean deepCopy)
	{
		final RESTBlobConstantV1 retValue = new RESTBlobConstantV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.name = this.name;
		
		if (deepCopy)
		{
			retValue.value = new byte[value.length];
			System.arraycopy(value, 0, retValue.value, 0, value.length);
		}
		else
		{
			retValue.value = value;
		}
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

	public byte[] getValue()
	{
		return value;
	}

	public void setValue(final byte[] value)
	{
		this.value = value;
	}
	
	public void explicitSetValue(final byte[] value)
	{
		this.value = value;
		this.setParamaterToConfigured(VALUE_NAME);
	}
}
