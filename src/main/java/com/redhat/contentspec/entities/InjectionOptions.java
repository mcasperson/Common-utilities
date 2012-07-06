package com.redhat.contentspec.entities;

import java.util.ArrayList;

public class InjectionOptions
{
	private UserType clientType = UserType.NONE;
	private UserType contentSpecType = UserType.NONE;
	private final ArrayList<String> strictTopicTypes = new ArrayList<String>();
	public static enum UserType {NONE, OFF, STRICT, ON};
	
	public InjectionOptions()
	{
	}
	
	public InjectionOptions(UserType clientSetting)
	{
		clientType = clientSetting;
	}
	
	public boolean isInjectionAllowed()
	{
		if ((contentSpecType == UserType.OFF || contentSpecType == UserType.NONE) 
				&& (clientType == UserType.OFF || clientType == UserType.NONE)) return false;
		return true;
	}
	
	public boolean isInjectionAllowedForType(final String typeName)
	{
		if (isInjectionAllowed())
		{
			if (clientType == UserType.STRICT || contentSpecType == UserType.STRICT)
			{
				boolean found = false;
				for (String type: strictTopicTypes)
				{
					if (type.equals(typeName))
					{
						found = true;
						break;
					}
				}
				return found;
			}
			else
			{
				return true;
			}
		}
		else
		{
			return false;
		}
	}

	public UserType getClientType()
	{
		return clientType;
	}

	public void setClientType(final UserType clientType)
	{
		this.clientType = clientType;
	}

	public UserType getContentSpecType()
	{
		return contentSpecType;
	}

	public void setContentSpecType(final UserType contentSpecType)
	{
		this.contentSpecType = contentSpecType;
	}
	
	public void addStrictTopicType(final String typeName)
	{
		boolean found = false;
		for (String type: strictTopicTypes)
		{
			if (type.equals(typeName))
			{
				found = true;
				break;
			}
		}
		if (!found) strictTopicTypes.add(typeName);
	}
	
	public void addStrictTopicTypes(final ArrayList<String> types)
	{
		if (types == null) return;
		for (String type: types)
		{
			addStrictTopicType(type);
		}
	}
	
	public ArrayList<String> getStrictTopicTypes()
	{
		return strictTopicTypes;
	}
}
