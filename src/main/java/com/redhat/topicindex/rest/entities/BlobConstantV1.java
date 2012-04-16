package com.redhat.topicindex.rest.entities;

/**
 * A REST representation of the BlobConstants database entity
 */
public class BlobConstantV1 extends BaseRESTEntityV1<BlobConstantV1>
{
	public static final String NAME_NAME = "name";
	public static final String VALUE_NAME = "value";
	
	private String name;
	private byte[] value;

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

	public byte[] getValue()
	{
		return value;
	}

	public void setValue(final byte[] value)
	{
		this.value = value;
	}
	
	public void setValueExplicit(final byte[] value)
	{
		this.value = value;
		this.setParamaterToConfigured(VALUE_NAME);
	}
}
