package com.redhat.topicindex.rest.entities;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTBaseEntityV1;

public class RoleV1 extends RESTBaseEntityV1<RoleV1>
{
	public static final String NAME_NAME = "name";
	public static final String DESCRIPTION_NAME = "description";
	public static final String CHILDROLES_NAME = "childroles";
	public static final String PARENTROLES_NAME = "parentroles";
	public static final String USERS_NAME = "users";
	
	private Integer relationshipId;
	private String relationshipDescription;
	private String name;
	private String description;
	private BaseRestCollectionV1<UserV1> users;
	private BaseRestCollectionV1<RoleV1> childRoles;
	private BaseRestCollectionV1<RoleV1> parentRoles;
	
	@Override
	public RoleV1 clone(boolean deepCopy)
	{
		final RoleV1 retValue = new RoleV1();
		
		this.cloneInto(retValue, deepCopy);
		
		retValue.name = this.name;
		retValue.description = description;
		retValue.relationshipId = new Integer(this.relationshipId);
		retValue.relationshipDescription = this.relationshipDescription;
		
		if (deepCopy)
		{
			retValue.users = this.users.clone(deepCopy);
			retValue.childRoles = this.childRoles.clone(deepCopy);
			retValue.parentRoles = this.parentRoles.clone(deepCopy);
		}
		else
		{
			retValue.users = this.users;
			retValue.childRoles = this.childRoles;
			retValue.parentRoles = this.parentRoles;
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

	public BaseRestCollectionV1<UserV1> getUsers()
	{
		return users;
	}

	public void setUsers(final BaseRestCollectionV1<UserV1> users)
	{
		this.users = users;
	}
	
	public void setUsersExplicit(final BaseRestCollectionV1<UserV1> users)
	{
		this.users = users;
		this.setParamaterToConfigured(USERS_NAME);
	}

	public BaseRestCollectionV1<RoleV1> getChildRoles()
	{
		return childRoles;
	}

	public void setChildRoles(final BaseRestCollectionV1<RoleV1> childRoles)
	{
		this.childRoles = childRoles;
	}
	
	public void setChildRolesExplicit(final BaseRestCollectionV1<RoleV1> childRoles)
	{
		this.childRoles = childRoles;
		this.setParamaterToConfigured(CHILDROLES_NAME);
	}

	public BaseRestCollectionV1<RoleV1> getParentRoles()
	{
		return parentRoles;
	}

	public void setParentRoles(final BaseRestCollectionV1<RoleV1> parentRoles)
	{
		this.parentRoles = parentRoles;
	}
	
	public void setParentRolesExplicit(final BaseRestCollectionV1<RoleV1> parentRoles)
	{
		this.parentRoles = parentRoles;
		this.setParamaterToConfigured(PARENTROLES_NAME);
	}

	public Integer getRelationshipId()
	{
		return relationshipId;
	}

	public void setRelationshipId(final Integer relationshipId)
	{
		this.relationshipId = relationshipId;
	}

	public String getRelationshipDescription()
	{
		return relationshipDescription;
	}

	public void setRelationshipDescription(final String relationshipDescription)
	{
		this.relationshipDescription = relationshipDescription;
	}
}
