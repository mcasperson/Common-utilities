package com.redhat.topicindex.rest.entities.interfaces;

import com.redhat.topicindex.rest.collections.RESTRoleCollectionV1;
import com.redhat.topicindex.rest.collections.RESTUserCollectionV1;

public class RESTUserV1 extends RESTBaseEntityV1<RESTUserV1, RESTUserCollectionV1>
{
	public static final String NAME_NAME = "name";
	public static final String DESCRIPTION_NAME = "description";
	public static final String ROLES_NAME = "roles";

	private String name;
	private String description;
	private RESTRoleCollectionV1 roles = null;
	/** A list of the Envers revision numbers */
	private RESTUserCollectionV1 revisions = null;
	
	@Override
	public RESTUserCollectionV1 getRevisions()
	{
		return revisions;
	}

	@Override
	public void setRevisions(final RESTUserCollectionV1 revisions)
	{
		this.revisions = revisions;
	}
	
	@Override
	public RESTUserV1 clone(boolean deepCopy)
	{
		final RESTUserV1 retValue = new RESTUserV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.name = this.name;
		retValue.description = description;
		
		if (deepCopy)
		{
			if (this.roles == null)
				retValue.roles = null;
			else
				this.roles.cloneInto(retValue.roles, deepCopy);
		}
		else
		{
			retValue.roles = this.roles;
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

	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}
	
	public void explicitSetDescription(final String description)
	{
		this.description = description;
		this.setParamaterToConfigured(DESCRIPTION_NAME);
	}

	public RESTRoleCollectionV1 getRoles()
	{
		return roles;
	}

	public void setRoles(final RESTRoleCollectionV1 roles)
	{
		this.roles = roles;
	}
	
	public void explicitSetRoles(final RESTRoleCollectionV1 roles)
	{
		this.roles = roles;
		this.setParamaterToConfigured(ROLES_NAME);
	}
}
