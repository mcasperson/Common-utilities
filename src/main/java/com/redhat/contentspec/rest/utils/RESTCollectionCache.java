package com.redhat.contentspec.rest.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.redhat.ecs.commonutils.StringUtilities;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.IBaseRESTEntityV1;

public class RESTCollectionCache
{

	private final RESTEntityCache entityCache;
	private final HashMap<String, BaseRestCollectionV1<? extends IBaseRESTEntityV1<?>>> collections = new HashMap<String, BaseRestCollectionV1<? extends IBaseRESTEntityV1<?>>>();

	public RESTCollectionCache(RESTEntityCache entityCache)
	{
		this.entityCache = entityCache;
	}

	public <T extends IBaseRESTEntityV1<T>> void add(Class<T> clazz, BaseRestCollectionV1<T> value)
	{
		add(clazz, value, null);
	}

	public <T extends IBaseRESTEntityV1<T>> void add(Class<T> clazz, BaseRestCollectionV1<T> value, List<String> additionalKeys)
	{
		add(clazz, value, additionalKeys, false);
	}

	public <T extends IBaseRESTEntityV1<T>> void add(Class<T> clazz, BaseRestCollectionV1<T> value, List<String> additionalKeys, boolean isRevisions)
	{
		String key = clazz.getSimpleName();
		if (additionalKeys != null && !additionalKeys.isEmpty())
		{
			key += "-" + StringUtilities.buildString(additionalKeys.toArray(new String[additionalKeys.size()]), "-");
		}
		entityCache.add(value, isRevisions);
		collections.put(key, value);
	}

	public <T extends IBaseRESTEntityV1<T>> boolean containsKey(Class<T> clazz)
	{
		return containsKey(clazz, null);
	}

	public <T extends IBaseRESTEntityV1<T>> boolean containsKey(Class<T> clazz, List<String> additionalKeys)
	{
		String key = clazz.getSimpleName();
		if (additionalKeys != null && !additionalKeys.isEmpty())
		{
			key += "-" + StringUtilities.buildString(additionalKeys.toArray(new String[additionalKeys.size()]), "-");
		}
		return collections.containsKey(key);
	}

	public <T extends IBaseRESTEntityV1<T>> BaseRestCollectionV1<T> get(Class<T> clazz)
	{
		return get(clazz, new ArrayList<String>());
	}

	@SuppressWarnings("unchecked")
	public <T extends IBaseRESTEntityV1<T>> BaseRestCollectionV1<T> get(Class<T> clazz, List<String> additionalKeys)
	{
		String key = clazz.getSimpleName();
		if (additionalKeys != null && !additionalKeys.isEmpty())
		{
			key += "-" + StringUtilities.buildString(additionalKeys.toArray(new String[additionalKeys.size()]), "-");
		}
		return containsKey(clazz, additionalKeys) ? (BaseRestCollectionV1<T>) collections.get(key) : new BaseRestCollectionV1<T>();
	}

	public <T extends IBaseRESTEntityV1<T>> void expire(Class<T> clazz)
	{
		collections.remove(clazz.getSimpleName());
	}

	public <T extends IBaseRESTEntityV1<T>> void expire(Class<T> clazz, List<String> additionalKeys)
	{
		collections.remove(clazz.getSimpleName() + "-" + StringUtilities.buildString(additionalKeys.toArray(new String[additionalKeys.size()]), "-"));
		expireByRegex("^" + clazz.getSimpleName() + ".*");
	}

	public void expireByRegex(String regex)
	{
		for (String key : collections.keySet())
		{
			if (key.matches(regex))
				collections.remove(key);
		}
	}
}
