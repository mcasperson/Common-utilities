package com.redhat.topicindex.rest.entities.interfaces;

import com.redhat.topicindex.rest.collections.RESTBlobConstantCollectionV1;


/**
 * A REST representation of the BlobConstants database entity
 */
public class RESTBlobConstantV1 extends RESTBaseEntityV1<RESTBlobConstantV1, RESTBlobConstantCollectionV1>
{
	public static final String NAME_NAME = "name";
	public static final String VALUE_NAME = "value";
	
	private String name;
	private byte[] value;
	/** A list of the Envers revision numbers */
	private RESTBlobConstantCollectionV1 revisions = null;
	
	@Override
	public RESTBlobConstantCollectionV1 getRevisions()
	{
		return revisions;
	}

	@Override
	public void setRevisions(final RESTBlobConstantCollectionV1 revisions)
	{
		this.revisions = revisions;
	}
	
	@Override
	public RESTBlobConstantV1 clone(boolean deepCopy)
	{
		final RESTBlobConstantV1 retValue = new RESTBlobConstantV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.name = this.name;
		
		if (deepCopy)
		{
			retValue.value = new byte[value.length];
			if (value != null)
			{
				System.arraycopy(value, 0, retValue.value, 0, value.length);
			}
			else
			{
				retValue.value = null;
			}
			
			if (this.getRevisions() == null)
				retValue.revisions = null;
			else
			{
				retValue.revisions = new RESTBlobConstantCollectionV1();
				this.revisions.cloneInto(retValue.revisions, deepCopy);
			}			
		}
		else
		{
			retValue.value = value;
			retValue.revisions = this.revisions;
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
