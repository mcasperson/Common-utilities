package com.redhat.contentspec.entities;

import java.util.ArrayList;

import com.redhat.ecs.commonutils.StringUtilities;

public class InjectionOptions {

	private UserType clientType = UserType.NONE;
	private UserType contentSpecType = UserType.NONE;
	private ArrayList<String> strictTopicTypes = new ArrayList<String>();
	public static enum UserType {NONE, OFF, STRICT, ON};
	
	public InjectionOptions() {
	}
	
	public InjectionOptions(UserType clientSetting) {
		clientType = clientSetting;
	}
	
	public boolean isInjectionAllowed() {
		if ((contentSpecType == UserType.OFF || contentSpecType == UserType.NONE) 
				&& (clientType == UserType.OFF || clientType == UserType.NONE)) return false;
		return true;
	}
	
	public boolean isInjectionAllowedForType(String typeName) {
		if (isInjectionAllowed()) {
			if (clientType == UserType.STRICT || contentSpecType == UserType.STRICT) {
				boolean found = false;
				for (String type: strictTopicTypes) {
					if (type.equals(typeName)) {
						found = true;
						break;
					}
				}
				return found;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}

	public UserType getClientType() {
		return clientType;
	}

	public void setClientType(UserType clientType) {
		this.clientType = clientType;
	}

	public UserType getContentSpecType() {
		return contentSpecType;
	}

	public void setContentSpecType(UserType contentSpecType) {
		this.contentSpecType = contentSpecType;
	}
	
	public void addStrictTopicType(String typeName) {
		boolean found = false;
		for (String type: strictTopicTypes) {
			if (type.equals(typeName)) {
				found = true;
				break;
			}
		}
		if (!found) strictTopicTypes.add(typeName);
	}
	
	public void addStrictTopicTypes(ArrayList<String> types) {
		if (types == null) return;
		for (String type: types) {
			addStrictTopicType(type);
		}
	}
	
	public ArrayList<String> getStrictTopicTypes() {
		return strictTopicTypes;
	}
	
	@Override
	public String toString()
	{
		String output = "";
		if (getContentSpecType() == InjectionOptions.UserType.STRICT)
		{
			output += "on [" + StringUtilities.buildString(getStrictTopicTypes().toArray(new String[0]), ", ") + "]";
		}
		else if (getContentSpecType() == InjectionOptions.UserType.ON)
		{
			output += "on";
		}
		else if (getContentSpecType() == InjectionOptions.UserType.OFF)
		{
			output += "off";
		}
		return output;
	}
}
