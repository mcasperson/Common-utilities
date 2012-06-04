package com.redhat.contentspec.rest.utils;

import java.util.HashMap;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTBaseEntityV1;

public class RESTEntityCache
{

	private HashMap<Class<?>, HashMap<String, RESTBaseEntityV1<?>>> singleEntities = new HashMap<Class<?>, HashMap<String, RESTBaseEntityV1<?>>>();

	public <T extends RESTBaseEntityV1<T>> void add(BaseRestCollectionV1<T> value)
	{
		add(value, false);
	}

	public <T extends RESTBaseEntityV1<T>> void add(BaseRestCollectionV1<T> value, boolean isRevisions)
	{
		if (value != null && value.getItems() != null)
		{
			for (T item : value.getItems())
			{
				add(item, isRevisions);
			}
		}
	}

	public <T extends RESTBaseEntityV1<T>> boolean containsKeyValue(Class<T> clazz, Integer id, Number revision)
	{
		if (singleEntities.containsKey(clazz))
			return revision == null ? singleEntities.get(clazz).containsKey(clazz.getSimpleName() + "-" + id) : singleEntities.get(clazz).containsKey(clazz.getSimpleName() + "-" + id + "-" + revision);
		else
			return false;
	}

	public <T extends RESTBaseEntityV1<T>> boolean containsKeyValue(Class<T> clazz, Integer id)
	{
		return containsKeyValue(clazz, id, null);
	}

	public <T extends RESTBaseEntityV1<T>> void add(T value)
	{
		add(value, false);
	}

	public <T extends RESTBaseEntityV1<T>> void add(T value, boolean isRevision)
	{
		// Add the map if one doesn't exist for the current class
		if (!singleEntities.containsKey(value.getClass()))
		{
			singleEntities.put(value.getClass(), new HashMap<String, RESTBaseEntityV1<?>>());
		}

		// Add the entity
		if (isRevision)
			singleEntities.get(value.getClass()).put(value.getClass().getSimpleName() + "-" + value.getId() + "-" + value.getRevision(), value);
		else
			singleEntities.get(value.getClass()).put(value.getClass().getSimpleName() + "-" + value.getId(), value);

		// Add any revisions to the cache
		add(value.getRevisions(), true);
	}

	public <T extends RESTBaseEntityV1<T>> BaseRestCollectionV1<T> get(Class<T> clazz)
	{
		BaseRestCollectionV1<T> values = new BaseRestCollectionV1<T>();
		if (singleEntities.containsKey(clazz))
		{
			for (String key : singleEntities.get(clazz).keySet())
			{
				values.addItem(clazz.cast(singleEntities.get(clazz).get(key)));
			}
		}
		return values;
	}

	public <T extends RESTBaseEntityV1<T>> T get(Class<T> clazz, Integer id)
	{
		return get(clazz, id, null);
	}

	public <T extends RESTBaseEntityV1<T>> T get(Class<T> clazz, Integer id, Number revision)
	{
		if (!containsKeyValue(clazz, id, revision))
			return null;
		return clazz.cast(revision == null ? singleEntities.get(clazz).get(clazz.getSimpleName() + "-" + id) : singleEntities.get(clazz).get(clazz.getSimpleName() + "-" + id + "-" + revision));
	}

	public <T extends RESTBaseEntityV1<T>> void expire(Class<T> clazz, Integer id)
	{
		expire(clazz, id, null);
	}

	public <T extends RESTBaseEntityV1<T>> void expire(Class<T> clazz, Integer id, Number revision)
	{
		String keyValue = revision == null ? (clazz.getSimpleName() + "-" + id) : (clazz.getSimpleName() + "-" + id + "-" + revision);
		if (singleEntities.containsKey(clazz))
		{
			if (singleEntities.get(clazz).containsKey(keyValue))
			{
				singleEntities.get(clazz).remove(keyValue);
			}
		}
	}

	public void expireByRegex(String regex)
	{
		for (Class<?> clazz : singleEntities.keySet())
		{
			for (String key : singleEntities.get(clazz).keySet())
			{
				if (key.matches(regex))
					singleEntities.remove(key);
			}
		}
	}
}
