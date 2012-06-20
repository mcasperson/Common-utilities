package com.redhat.contentspec.rest.utils;

import java.util.HashMap;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.ComponentTranslatedTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTBaseEntityV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTTranslatedTopicV1;

public class RESTEntityCache
{

	private HashMap<Class<?>, HashMap<String, RESTBaseEntityV1<?, ?>>> singleEntities = new HashMap<Class<?>, HashMap<String, RESTBaseEntityV1<?, ?>>>();

	public <T extends RESTBaseEntityV1<T, U>, U extends BaseRestCollectionV1<T, U>> void add(BaseRestCollectionV1<T, U> value)
	{
		add(value, false);
	}

	public <T extends RESTBaseEntityV1<T, U>, U extends BaseRestCollectionV1<T, U>> void add(BaseRestCollectionV1<T, U> value, boolean isRevisions)
	{
		if (value != null && value.getItems() != null)
		{
			for (T item : value.getItems())
			{
				if (item.getClass() == RESTTranslatedTopicV1.class)
				{
					add(item, ((RESTTranslatedTopicV1) item).getTopicId(), isRevisions);
					add(item, ComponentTranslatedTopicV1.returnZanataId(((RESTTranslatedTopicV1) item)), isRevisions);
				}
				else
				{
					add(item, isRevisions);
				}
			}
		}
	}

	public <T extends RESTBaseEntityV1<T, U>, U extends BaseRestCollectionV1<T, U>> boolean containsKeyValue(Class<T> clazz, Integer id, Number revision)
	{
		if (singleEntities.containsKey(clazz))
			return revision == null ? singleEntities.get(clazz).containsKey(clazz.getSimpleName() + "-" + id) : singleEntities.get(clazz).containsKey(clazz.getSimpleName() + "-" + id + "-" + revision);
		else
			return false;
	}

	public <T extends RESTBaseEntityV1<T, U>, U extends BaseRestCollectionV1<T, U>> boolean containsKeyValue(Class<T> clazz, Integer id)
	{
		return containsKeyValue(clazz, id, null);
	}

	public <T extends RESTBaseEntityV1<T, U>, U extends BaseRestCollectionV1<T, U>> void add(T value)
	{
		add(value, false);
	}

	public <T extends RESTBaseEntityV1<T, U>, U extends BaseRestCollectionV1<T, U>> void add(T value, final Number id, boolean isRevision)
	{
		add(value, id.toString(), isRevision);
	}

	public <T extends RESTBaseEntityV1<T, U>, U extends BaseRestCollectionV1<T, U>> void add(T value, final String id, boolean isRevision)
	{
		// Add the map if one doesn't exist for the current class
		if (!singleEntities.containsKey(value.getClass()))
		{
			singleEntities.put(value.getClass(), new HashMap<String, RESTBaseEntityV1<?, ?>>());
		}

		// Add the entity
		if (isRevision)
			singleEntities.get(value.getClass()).put(value.getClass().getSimpleName() + "-" + id + "-" + value.getRevision(), value);
		else
			singleEntities.get(value.getClass()).put(value.getClass().getSimpleName() + "-" + id, value);

		// Add any revisions to the cache
		add(value.getRevisions(), true);
	}

	public <T extends RESTBaseEntityV1<T, U>, U extends BaseRestCollectionV1<T, U>> void add(T value, boolean isRevision)
	{
		add(value, value.getId().toString(), isRevision);
	}

	public <T extends RESTBaseEntityV1<T, U>, U extends BaseRestCollectionV1<T, U>> BaseRestCollectionV1<T, U> get(Class<T> clazz, Class<U> collectionClass)
	{
		try
		{
			BaseRestCollectionV1<T, U> values = collectionClass.newInstance();
			if (singleEntities.containsKey(clazz))
			{
				for (String key : singleEntities.get(clazz).keySet())
				{
					values.addItem(clazz.cast(singleEntities.get(clazz).get(key)));
				}
			}
			return values;
		}
		catch (final Exception ex)
		{
			return null;
		}
	}

	public <T extends RESTBaseEntityV1<T, U>, U extends BaseRestCollectionV1<T, U>> T get(Class<T> clazz, Integer id)
	{
		return get(clazz, id, null);
	}

	public <T extends RESTBaseEntityV1<T, U>, U extends BaseRestCollectionV1<T, U>> T get(Class<T> clazz, Integer id, Number revision)
	{
		if (!containsKeyValue(clazz, id, revision))
			return null;
		return clazz.cast(revision == null ? singleEntities.get(clazz).get(clazz.getSimpleName() + "-" + id) : singleEntities.get(clazz).get(clazz.getSimpleName() + "-" + id + "-" + revision));
	}

	public <T extends RESTBaseEntityV1<T, U>, U extends BaseRestCollectionV1<T, U>> void expire(Class<T> clazz, Integer id)
	{
		expire(clazz, id, null);
	}

	public <T extends RESTBaseEntityV1<T, U>, U extends BaseRestCollectionV1<T, U>> void expire(Class<T> clazz, Integer id, Number revision)
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
