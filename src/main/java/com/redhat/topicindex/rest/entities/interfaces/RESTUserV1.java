package com.redhat.topicindex.rest.entities.interfaces;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;

public class RESTUserV1 extends RESTBaseEntityV1<RESTUserV1>
{
	public static final String NAME_NAME = "name";
	public static final String DESCRIPTION_NAME = "description";
	public static final String ROLES_NAME = "roles";

	private String name;
	private String description;
	private BaseRestCollectionV1<RESTRoleV1> roles = null;
	
	@Override
	public RESTUserV1 clone(boolean deepCopy)
	{
		final RESTUserV1 retValue = new RESTUserV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.name = this.name;
		retValue.description = description;
		
		if (deepCopy)
		{
			retValue.roles = this.roles.clone(deepCopy);
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

	public BaseRestCollectionV1<RESTRoleV1> getRoles()
	{
		return roles;
	}

	public void setRoles(final BaseRestCollectionV1<RESTRoleV1> roles)
	{
		this.roles = roles;
	}
	
	public void explicitSetRoles(final BaseRestCollectionV1<RESTRoleV1> roles)
	{
		this.roles = roles;
		this.setParamaterToConfigured(ROLES_NAME);
	}
}
