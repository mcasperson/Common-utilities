package com.redhat.contentspec.rest;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.ws.rs.core.PathSegment;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.specimpl.PathSegmentImpl;

import com.redhat.contentspec.constants.CSConstants;
import com.redhat.contentspec.entities.*;
import com.redhat.contentspec.rest.utils.RESTCollectionCache;
import com.redhat.contentspec.rest.utils.RESTEntityCache;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.collections.RESTCategoryCollectionV1;
import com.redhat.topicindex.rest.collections.RESTTagCollectionV1;
import com.redhat.topicindex.rest.collections.RESTTopicCollectionV1;
import com.redhat.topicindex.rest.collections.RESTTranslatedTopicCollectionV1;
import com.redhat.topicindex.rest.collections.RESTUserCollectionV1;
import com.redhat.topicindex.rest.entities.ComponentBaseRESTEntityWithPropertiesV1;
import com.redhat.topicindex.rest.entities.ComponentBaseTopicV1;
import com.redhat.topicindex.rest.entities.ComponentTagV1;
import com.redhat.topicindex.rest.entities.ComponentTranslatedTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTBaseTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTCategoryV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTImageV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTTagV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTTopicSourceUrlV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTTranslatedTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTUserV1;
import com.redhat.topicindex.rest.expand.ExpandDataDetails;
import com.redhat.topicindex.rest.expand.ExpandDataTrunk;
import com.redhat.topicindex.rest.sharedinterface.RESTInterfaceV1;
import com.redhat.ecs.commonutils.CollectionUtilities;
import com.redhat.ecs.commonutils.ExceptionUtilities;

public class RESTReader
{
	private final Logger log = Logger.getLogger(RESTReader.class);

	private final RESTInterfaceV1 client;
	private final ObjectMapper mapper = new ObjectMapper();
	private final RESTEntityCache entityCache;
	private final RESTCollectionCache collectionsCache;

	public RESTReader(final RESTInterfaceV1 client, final RESTEntityCache entityCache, final RESTCollectionCache collectionsCache)
	{
		this.client = client;
		this.entityCache = entityCache;
		this.collectionsCache = collectionsCache;
	}

	// CATEGORY QUERIES

	/*
	 * Gets a specific category tuple from the database as specified by the
	 * categories ID.
	 */
	public RESTCategoryV1 getCategoryById(final int id)
	{
		try
		{
			if (entityCache.containsKeyValue(RESTCategoryV1.class, id))
			{
				return entityCache.get(RESTCategoryV1.class, id);
			}
			else
			{
				final RESTCategoryV1 category = client.getJSONCategory(id, null);
				entityCache.add(category);
				return category;
			}
		}
		catch (Exception e)
		{
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Gets a List of all categories tuples for a specified name.
	 */
	public List<RESTCategoryV1> getCategoriesByName(final String name)
	{
		final List<RESTCategoryV1> output = new ArrayList<RESTCategoryV1>();

		try
		{
			BaseRestCollectionV1<RESTCategoryV1, RESTCategoryCollectionV1> categories = collectionsCache.get(RESTCategoryV1.class, RESTCategoryCollectionV1.class);
			if (categories.getItems() == null)
			{
				/* We need to expand the Categories collection */
				final ExpandDataTrunk expand = new ExpandDataTrunk();
				expand.setBranches(CollectionUtilities.toArrayList(new ExpandDataTrunk(new ExpandDataDetails("categories"))));

				final String expandString = mapper.writeValueAsString(expand);
				//final String expandEncodedString = URLEncoder.encode(expandString, "UTF-8");

				categories = client.getJSONCategories(expandString);
				collectionsCache.add(RESTCategoryV1.class, categories);
			}

			if (categories != null)
			{
				for (RESTCategoryV1 cat : categories.getItems())
				{
					if (cat.getName().equals(name))
					{
						output.add(cat);
					}
				}
			}

			return output;
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return null;
	}

	/*
	 * Gets a Category item assuming that tags can only have one category
	 */
	public RESTCategoryV1 getCategoryByTagId(final int tagId)
	{
		final RESTTagV1 tag = getTagById(tagId);
		if (tag == null)
			return null;

		return tag.getCategories().getItems().size() > 0 ? tag.getCategories().getItems().get(0) : null;
	}

	// TAG QUERIES

	/*
	 * Gets a specific tag tuple from the database as specified by the tags ID.
	 */
	public RESTTagV1 getTagById(final int id)
	{
		try
		{
			if (entityCache.containsKeyValue(RESTTagV1.class, id))
			{
				return entityCache.get(RESTTagV1.class, id);
			}
			else
			{
				/*
				 * We need to expand the Categories collection in most cases so
				 * expand it anyway
				 */
				final ExpandDataTrunk expand = new ExpandDataTrunk();
				expand.setBranches(CollectionUtilities.toArrayList(new ExpandDataTrunk(new ExpandDataDetails("categories")), new ExpandDataTrunk(new ExpandDataDetails("properties"))));

				final String expandString = mapper.writeValueAsString(expand);
				//final String expandEncodedString = URLEncoder.encode(expandString, "UTF-8");
				
				final RESTTagV1 tag = client.getJSONTag(id, expandString);
				entityCache.add(tag);
				return tag;
			}
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return null;
	}

	/*
	 * Gets a List of all tag tuples for a specified name.
	 */
	public List<RESTTagV1> getTagsByName(final String name)
	{
		final List<RESTTagV1> output = new ArrayList<RESTTagV1>();

		try
		{

			BaseRestCollectionV1<RESTTagV1, RESTTagCollectionV1> tags = collectionsCache.get(RESTTagV1.class, RESTTagCollectionV1.class);
			if (tags.getItems() == null)
			{
				/* We need to expand the Tags & Categories collection */
				final ExpandDataTrunk expand = new ExpandDataTrunk();
				final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails("tags"));
				expandTags.setBranches(CollectionUtilities.toArrayList(new ExpandDataTrunk(new ExpandDataDetails("categories"))));
				expand.setBranches(CollectionUtilities.toArrayList(expandTags));

				final String expandString = mapper.writeValueAsString(expand);
				//final String expandEncodedString = URLEncoder.encode(expandString, "UTF-8");

				tags = client.getJSONTags(expandString);
				collectionsCache.add(RESTTagV1.class, tags);
			}

			// Iterate through the list of tags and check if the tag is a Type
			// and matches the name.
			if (tags != null)
			{
				for (final RESTTagV1 tag : tags.getItems())
				{
					if (tag.getName().equals(name))
					{
						output.add(tag);
					}
				}
			}

			return output;
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return null;
	}

	/*
	 * Gets a List of Tag tuples for a specified its TopicID relationship
	 * through TopicToTag.
	 */
	public List<RESTTagV1> getTagsByTopicId(final int topicId)
	{
		final RESTTopicV1 topic;
		if (entityCache.containsKeyValue(RESTTopicV1.class, topicId))
		{
			topic = entityCache.get(RESTTopicV1.class, topicId);
		}
		else
		{
			topic = getTopicById(topicId, null);
		}

		return topic == null ? null : topic.getTags().getItems();
	}

	// TOPIC QUERIES

	/*
	 * Gets a specific tag tuple from the database as specified by the tags ID.
	 */
	public RESTTopicV1 getTopicById(final int id, final Integer rev)
	{
		return getTopicById(id, rev, false);
	}
	
	/*
	 * Gets a specific tag tuple from the database as specified by the tags ID.
	 */
	public RESTTopicV1 getTopicById(final int id, final Integer rev, final boolean expandTranslations)
	{
		try
		{
			final RESTTopicV1 topic;
			if (entityCache.containsKeyValue(RESTTopicV1.class, id, rev))
			{
				topic = entityCache.get(RESTTopicV1.class, id, rev);
			}
			else
			{
				/* We need to expand the all the items in the topic collection */
				final ExpandDataTrunk expand = new ExpandDataTrunk();
				final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails("tags"));
				final ExpandDataTrunk expandTopicTranslations = new ExpandDataTrunk(new ExpandDataDetails(RESTTopicV1.TRANSLATEDTOPICS_NAME));
				expandTags.setBranches(CollectionUtilities.toArrayList(new ExpandDataTrunk(new ExpandDataDetails("categories")), new ExpandDataTrunk(new ExpandDataDetails("properties"))));
				expand.setBranches(CollectionUtilities.toArrayList(expandTags, new ExpandDataTrunk(new ExpandDataDetails("sourceUrls")), new ExpandDataTrunk(new ExpandDataDetails("properties")),
						new ExpandDataTrunk(new ExpandDataDetails("outgoingRelationships")), new ExpandDataTrunk(new ExpandDataDetails("incomingRelationships"))));

				if (expandTranslations)
				{
					expand.getBranches().add(expandTopicTranslations);
				}
				
				final String expandString = mapper.writeValueAsString(expand);
				//final String expandEncodedString = URLEncoder.encode(expandString, "UTF-8");
				if (rev == null)
				{
					topic = client.getJSONTopic(id, expandString);
					entityCache.add(topic);
				}
				else
				{
					topic = client.getJSONTopicRevision(id, rev, expandString);
					entityCache.add(topic, true);
				}
			}
			return topic;
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return null;
	}

	/*
	 * Gets a collection of topics based on the list of ids passed.
	 */
	public RESTTopicCollectionV1 getTopicsByIds(final List<Integer> ids, final boolean expandTranslations)
	{
		if (ids.isEmpty())
			return null;

		try
		{
			final RESTTopicCollectionV1 topics = new RESTTopicCollectionV1();
			final StringBuffer urlVars = new StringBuffer("query;topicIds=");
			//final String encodedComma = URLEncoder.encode(",", "UTF-8");

			for (Integer id : ids)
			{
				if (!entityCache.containsKeyValue(RESTTopicV1.class, id))
				{
					urlVars.append(id + ",");
				}
				else
				{
					topics.addItem(entityCache.get(RESTTopicV1.class, id));
				}
			}

			String query = urlVars.toString();

			/* Get the missing topics from the REST interface */
			if (query.length() != "query;topicIds=".length())
			{
				query = query.substring(0, query.length() - 1);

				PathSegment path = new PathSegmentImpl(query, false);
				
				final ExpandDataDetails expandDetails = new ExpandDataDetails("topics");
				expandDetails.setShowSize(true);
				expandDetails.setEnd(0);
				final ExpandDataTrunk topicsExpandSize = new ExpandDataTrunk(expandDetails);
				final ExpandDataTrunk expandSize = new ExpandDataTrunk();

				expandSize.setBranches(CollectionUtilities.toArrayList(topicsExpandSize));
				
				final String expandDetailsString = mapper.writeValueAsString(expandSize);
				final RESTTopicCollectionV1 downloadedTopicsSize = client.getJSONTopicsWithQuery(path, expandDetailsString);
				
				/* Load the topics in groups to save memory when unmarshalling */
				final int numTopics = downloadedTopicsSize.getSize();
				for (int i = 0; i <= numTopics; i = i + 100)
				{
					/* We need to expand the all the items in the topic collection */
					final ExpandDataTrunk expand = new ExpandDataTrunk();
					final ExpandDataDetails expandTopicDetails = new ExpandDataDetails("topics");
					expandTopicDetails.setStart(i);
					expandTopicDetails.setEnd(i + 100);
					final ExpandDataTrunk topicsExpand = new ExpandDataTrunk(expandTopicDetails);
					final ExpandDataTrunk tags = new ExpandDataTrunk(new ExpandDataDetails("tags"));
					final ExpandDataTrunk properties = new ExpandDataTrunk(new ExpandDataDetails(RESTBaseTopicV1.PROPERTIES_NAME));
					final ExpandDataTrunk categories = new ExpandDataTrunk(new ExpandDataDetails("categories"));
					final ExpandDataTrunk parentTags = new ExpandDataTrunk(new ExpandDataDetails("parenttags"));
					final ExpandDataTrunk outgoingRelationships = new ExpandDataTrunk(new ExpandDataDetails("outgoingRelationships"));
					final ExpandDataTrunk expandTranslatedTopics = new ExpandDataTrunk(new ExpandDataDetails(RESTTopicV1.TRANSLATEDTOPICS_NAME));

					/* We need to expand the categories collection on the topic tags */
					tags.setBranches(CollectionUtilities.toArrayList(categories, parentTags, properties));
					if (expandTranslations)
					{
						outgoingRelationships.setBranches(CollectionUtilities.toArrayList(tags, properties, expandTranslatedTopics));
						topicsExpand.setBranches(CollectionUtilities.toArrayList(tags, outgoingRelationships, properties, new ExpandDataTrunk(new ExpandDataDetails("sourceUrls")), expandTranslatedTopics));
					}
					else
					{
						outgoingRelationships.setBranches(CollectionUtilities.toArrayList(tags, properties));
						topicsExpand.setBranches(CollectionUtilities.toArrayList(tags, outgoingRelationships, properties, new ExpandDataTrunk(new ExpandDataDetails("sourceUrls"))));
					}
					
					expand.setBranches(CollectionUtilities.toArrayList(topicsExpand));

					final String expandString = mapper.writeValueAsString(expand);
					//final String expandEncodedString = URLEncoder.encode(expandString, "UTF-8");
					
					final RESTTopicCollectionV1 downloadedTopics = client.getJSONTopicsWithQuery(path, expandString);
					entityCache.add(downloadedTopics);

					/* Transfer the downloaded data to the current topic list */
					if (downloadedTopics != null && downloadedTopics.getItems() != null)
					{
						for (final RESTTopicV1 item : downloadedTopics.getItems())
						{
							topics.addItem(item);
						}
					}
				}
			}

			return topics;
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return null;
	}

	/*
	 * Gets a list of Revision's from the TopicIndex database for a specific
	 * topic
	 */
	public List<Object[]> getTopicRevisionsById(final Integer topicId)
	{
		final List<Object[]> results = new ArrayList<Object[]>();
		try
		{
			final List<String> additionalKeys = CollectionUtilities.toArrayList("revisions", "topic" + topicId);
			final BaseRestCollectionV1<RESTTopicV1, RESTTopicCollectionV1> topicRevisions;
			if (collectionsCache.containsKey(RESTTopicV1.class, additionalKeys))
			{
				topicRevisions = collectionsCache.get(RESTTopicV1.class, RESTTopicCollectionV1.class, additionalKeys);
			}
			else
			{
				/* We need to expand the Revisions collection */
				final ExpandDataTrunk expand = new ExpandDataTrunk();
				final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails("tags"));
				final ExpandDataTrunk expandRevs = new ExpandDataTrunk(new ExpandDataDetails("revisions"));
				expandTags.setBranches(CollectionUtilities.toArrayList(new ExpandDataTrunk(new ExpandDataDetails("categories"))));
				expandRevs.setBranches(CollectionUtilities.toArrayList(expandTags, new ExpandDataTrunk(new ExpandDataDetails("sourceUrls")), 
						new ExpandDataTrunk(new ExpandDataDetails("properties")), new ExpandDataTrunk(new ExpandDataDetails("outgoingRelationships")), 
						new ExpandDataTrunk(new ExpandDataDetails("incomingRelationships"))));
				expand.setBranches(CollectionUtilities.toArrayList(expandRevs));

				final String expandString = mapper.writeValueAsString(expand);
				//final String expandEncodedString = URLEncoder.encode(expandString, "UTF-8");

				final RESTTopicV1 topic = client.getJSONTopic(topicId, expandString);
				collectionsCache.add(RESTTopicV1.class, topic.getRevisions(), additionalKeys, true);
				topicRevisions = topic.getRevisions();
			}

			// Create the custom revisions list
			if (topicRevisions != null && topicRevisions.getItems() != null)
			{
				for (final RESTTopicV1 topicRev : topicRevisions.getItems())
				{
					Object[] revision = new Object[2];
					revision[0] = topicRev.getRevision();
					revision[1] = topicRev.getLastModified();
					results.add(revision);
				}
			}
			return results;
		}
		catch (Exception e)
		{
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * Gets a List of TopicSourceUrl tuples for a specified its TopicID
	 * relationship through TopicToTopicSourceUrl.
	 */
	public List<RESTTopicSourceUrlV1> getSourceUrlsByTopicId(final int topicId)
	{
		final RESTTopicV1 topic;
		if (entityCache.containsKeyValue(RESTTopicV1.class, topicId))
		{
			topic = entityCache.get(RESTTopicV1.class, topicId);
		}
		else
		{
			topic = getTopicById(topicId, null);
		}
		return topic == null ? null : topic.getSourceUrls_OTM().getItems();
	}

	// TRANSLATED TOPICS QUERIES

	/*
	 * Gets a collection of translated topics based on the list of topic ids
	 * passed.
	 */
	public RESTTranslatedTopicCollectionV1 getTranslatedTopicsByTopicIds(final List<Integer> ids, final String locale)
	{
		if (ids.isEmpty())
			return null;

		try
		{
			final RESTTranslatedTopicCollectionV1 topics = new RESTTranslatedTopicCollectionV1();
			final StringBuffer urlVars = new StringBuffer("query;latestTranslations=true;topicIds=");
			//final String encodedComma = URLEncoder.encode(",", "UTF-8");

			for (final Integer id : ids)
			{
				if (!entityCache.containsKeyValue(RESTTranslatedTopicV1.class, id) && !entityCache.containsKeyValue(RESTTranslatedTopicV1.class, (id * -1)))
				{
					urlVars.append(id + ",");
				}
				else if (entityCache.containsKeyValue(RESTTranslatedTopicV1.class, (id * -1)))
				{
					topics.addItem(entityCache.get(RESTTranslatedTopicV1.class, (id * -1)));
				}
				else
				{
					topics.addItem(entityCache.get(RESTTranslatedTopicV1.class, id));
				}
			}

			String query = urlVars.toString();

			if (query.length() != "query;latestTranslations=true;topicIds=".length())
			{
				query = query.substring(0, query.length() - 1);

				/* Add the locale to the query if one was passed */
				if (locale != null && !locale.isEmpty())
					query += ";locale1=" + locale + "1";

				PathSegment path = new PathSegmentImpl(query, false);

				/*
				 * We need to expand the all the items in the translatedtopic
				 * collection
				 */
				final ExpandDataTrunk expand = new ExpandDataTrunk();

				final ExpandDataTrunk translatedTopicsExpand = new ExpandDataTrunk(new ExpandDataDetails("translatedtopics"));
				final ExpandDataTrunk topicExpandTranslatedTopics = new ExpandDataTrunk(new ExpandDataDetails(RESTTopicV1.TRANSLATEDTOPICS_NAME));
				final ExpandDataTrunk tags = new ExpandDataTrunk(new ExpandDataDetails("tags"));
				final ExpandDataTrunk properties = new ExpandDataTrunk(new ExpandDataDetails(RESTBaseTopicV1.PROPERTIES_NAME));
				final ExpandDataTrunk categories = new ExpandDataTrunk(new ExpandDataDetails("categories"));
				final ExpandDataTrunk parentTags = new ExpandDataTrunk(new ExpandDataDetails("parenttags"));
				final ExpandDataTrunk outgoingRelationships = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedTopicV1.ALL_LATEST_OUTGOING_NAME));
				final ExpandDataTrunk topicsExpand = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedTopicV1.TOPIC_NAME));

				/* We need to expand the categories collection on the topic tags */
				tags.setBranches(CollectionUtilities.toArrayList(categories, parentTags, properties));
				outgoingRelationships.setBranches(CollectionUtilities.toArrayList(tags, properties, topicsExpand));

				topicsExpand.setBranches(CollectionUtilities.toArrayList(topicExpandTranslatedTopics));

				translatedTopicsExpand.setBranches(CollectionUtilities.toArrayList(tags, outgoingRelationships, properties, topicsExpand));

				expand.setBranches(CollectionUtilities.toArrayList(translatedTopicsExpand));

				final String expandString = mapper.writeValueAsString(expand);
				//final String expandEncodedString = URLEncoder.encode(expandString, "UTF-8");
				
				final RESTTranslatedTopicCollectionV1 downloadedTopics = client.getJSONTranslatedTopicsWithQuery(path, expandString);
				entityCache.add(downloadedTopics);

				/* Transfer the downloaded data to the current topic list */
				if (downloadedTopics != null && downloadedTopics.getItems() != null)
				{
					for (final RESTTranslatedTopicV1 item : downloadedTopics.getItems())
					{
						entityCache.add(item, item.getTopicId(), false);
						topics.addItem(item);
					}
				}
			}

			return topics;
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return null;
	}
	
	/*
	 * Gets a collection of translated topics based on the list of topic ids
	 * passed.
	 */
	public RESTTranslatedTopicCollectionV1 getTranslatedTopicsByZanataIds(final List<Integer> ids, final String locale)
	{
		if (ids.isEmpty())
			return null;

		try
		{
			final RESTTranslatedTopicCollectionV1 topics = new RESTTranslatedTopicCollectionV1();
			final StringBuffer urlVars = new StringBuffer("query;latestTranslations=true;zanataIds=");
			final String encodedComma = URLEncoder.encode(",", "UTF-8");

			for (Integer id : ids)
			{
				if (!entityCache.containsKeyValue(RESTTranslatedTopicV1.class, id))
				{
					urlVars.append(id + encodedComma);
				}
				else
				{
					topics.addItem(entityCache.get(RESTTranslatedTopicV1.class, id));
				}
			}

			String query = urlVars.toString();

			if (query.length() != "query;latestTranslations=true;zanataIds=".length())
			{
				query = query.substring(0, query.length() - encodedComma.length());

				/* Add the locale to the query if one was passed */
				if (locale != null && !locale.isEmpty())
					query += ";locale1=" + locale + "1";

				PathSegment path = new PathSegmentImpl(query, false);

				/*
				 * We need to expand the all the items in the translatedtopic
				 * collection
				 */
				final ExpandDataTrunk expand = new ExpandDataTrunk();

				final ExpandDataTrunk translatedTopicsExpand = new ExpandDataTrunk(new ExpandDataDetails("translatedtopics"));
				final ExpandDataTrunk topicExpandTranslatedTopics = new ExpandDataTrunk(new ExpandDataDetails(RESTTopicV1.TRANSLATEDTOPICS_NAME));
				final ExpandDataTrunk tags = new ExpandDataTrunk(new ExpandDataDetails("tags"));
				final ExpandDataTrunk properties = new ExpandDataTrunk(new ExpandDataDetails(RESTBaseTopicV1.PROPERTIES_NAME));
				final ExpandDataTrunk categories = new ExpandDataTrunk(new ExpandDataDetails("categories"));
				final ExpandDataTrunk parentTags = new ExpandDataTrunk(new ExpandDataDetails("parenttags"));
				final ExpandDataTrunk outgoingRelationships = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedTopicV1.ALL_LATEST_OUTGOING_NAME));
				final ExpandDataTrunk topicsExpand = new ExpandDataTrunk(new ExpandDataDetails(RESTTranslatedTopicV1.TOPIC_NAME));

				/* We need to expand the categories collection on the topic tags */
				tags.setBranches(CollectionUtilities.toArrayList(categories, parentTags, properties));
				outgoingRelationships.setBranches(CollectionUtilities.toArrayList(tags, properties, topicsExpand));

				topicsExpand.setBranches(CollectionUtilities.toArrayList(topicExpandTranslatedTopics));

				translatedTopicsExpand.setBranches(CollectionUtilities.toArrayList(tags, outgoingRelationships, properties, topicsExpand));

				expand.setBranches(CollectionUtilities.toArrayList(translatedTopicsExpand));

				final String expandString = mapper.writeValueAsString(expand);
				//final String expandEncodedString = URLEncoder.encode(expandString, "UTF-8");
				
				final RESTTranslatedTopicCollectionV1 downloadedTopics = client.getJSONTranslatedTopicsWithQuery(path, expandString);
				entityCache.add(downloadedTopics);

				/* Transfer the downloaded data to the current topic list */
				if (downloadedTopics != null && downloadedTopics.getItems() != null)
				{
					for (final RESTTranslatedTopicV1 item : downloadedTopics.getItems())
					{
						entityCache.add(item, ComponentTranslatedTopicV1.returnZanataId(item), false);
						topics.addItem(item);
					}
				}
			}

			return topics;
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return null;
	}

	/*
	 * Gets a translated topic based on a topic id and locale
	 */
	public RESTTranslatedTopicV1 getTranslatedTopicByTopicId(final Integer id, final String locale)
	{
		try
		{
			final RESTTranslatedTopicCollectionV1 topics = getTranslatedTopicsByTopicIds(CollectionUtilities.toArrayList(id), locale);

			return topics != null && topics.getItems() != null && topics.getItems().size() == 1 ? topics.getItems().get(0) : null;
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return null;
	}

	// USER QUERIES

	/*
	 * Gets a List of all User tuples for a specified name.
	 */
	public List<RESTUserV1> getUsersByName(final String userName)
	{
		final List<RESTUserV1> output = new ArrayList<RESTUserV1>();

		try
		{
			final BaseRestCollectionV1<RESTUserV1, RESTUserCollectionV1> users;
			if (collectionsCache.containsKey(RESTUserV1.class))
			{
				users = collectionsCache.get(RESTUserV1.class, RESTUserCollectionV1.class);
			}
			else
			{
				/* We need to expand the Users collection */
				final ExpandDataTrunk expand = new ExpandDataTrunk();
				expand.setBranches(CollectionUtilities.toArrayList(new ExpandDataTrunk(new ExpandDataDetails("users"))));

				final String expandString = mapper.writeValueAsString(expand);
				//final String expandEncodedString = URLEncoder.encode(expandString, "UTF-8");
				
				users = client.getJSONUsers(expandString);
				collectionsCache.add(RESTUserV1.class, users);
			}

			if (users != null)
			{
				for (RESTUserV1 user : users.getItems())
				{
					if (user.getName().equals(userName))
					{
						output.add(user);
					}
				}
			}

			return output;
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return null;
	}

	/*
	 * Gets a specific User tuple from the database as specified by the tags ID.
	 */
	public RESTUserV1 getUserById(final int id)
	{
		try
		{
			if (entityCache.containsKeyValue(RESTUserV1.class, id))
			{
				return entityCache.get(RESTUserV1.class, id);
			}
			else
			{
				final RESTUserV1 user = client.getJSONUser(id, null);
				entityCache.add(user);
				return user;
			}
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return null;
	}

	// CONTENT SPEC QUERIES

	/*
	 * Gets a ContentSpec tuple for a specified id.
	 */
	public RESTTopicV1 getContentSpecById(final int id, final Integer rev)
	{
		final RESTTopicV1 cs = getTopicById(id, rev, false);
		if (cs == null)
			return null;
		
		final List<RESTTagV1> topicTypes = ComponentBaseTopicV1.returnTagsInCategoriesByID(cs, CollectionUtilities.toArrayList(CSConstants.TYPE_CATEGORY_ID));
		for (final RESTTagV1 type : topicTypes)
		{
			if (type.getId().equals(CSConstants.CONTENT_SPEC_TAG_ID))
			{
				return cs;
			}
		}
		return null;
	}
	
	/*
	 * Gets a ContentSpec tuple for a specified id.
	 */
	public RESTTranslatedTopicV1 getTranslatedContentSpecById(final int id, final Integer rev, final String locale)
	{
		if (locale == null) return null;
		final RESTTopicV1 cs = getTopicById(id, rev, true);
		if (cs == null)
			return null;
		
		final List<RESTTagV1> topicTypes = ComponentBaseTopicV1.returnTagsInCategoriesByID(cs, CollectionUtilities.toArrayList(CSConstants.TYPE_CATEGORY_ID));
		for (final RESTTagV1 type : topicTypes)
		{
			if (type.getId().equals(CSConstants.CONTENT_SPEC_TAG_ID))
			{
				for (final RESTTranslatedTopicV1 topic : cs.getTranslatedTopics_OTM().getItems())
				{
					if (topic.getLocale().equals(locale))
						return topic;
				}
			}
		}
		return null;
	}

	/*
	 * Gets a list of Revision's from the CSProcessor database for a specific
	 * content spec
	 */
	public List<Object[]> getContentSpecRevisionsById(final Integer csId)
	{
		final List<Object[]> results = new ArrayList<Object[]>();
		try
		{
			final List<String> additionalKeys = CollectionUtilities.toArrayList("revision", "topic" + csId);
			final BaseRestCollectionV1<RESTTopicV1, RESTTopicCollectionV1> topicRevisions;
			if (collectionsCache.containsKey(RESTTopicV1.class, additionalKeys))
			{
				topicRevisions = collectionsCache.get(RESTTopicV1.class, RESTTopicCollectionV1.class, additionalKeys);
			}
			else
			{
				/* We need to expand the Revisions collection */
				final ExpandDataTrunk expand = new ExpandDataTrunk();
				final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails("tags"));
				final ExpandDataTrunk expandRevs = new ExpandDataTrunk(new ExpandDataDetails("revisions"));
				expandTags.setBranches(CollectionUtilities.toArrayList(new ExpandDataTrunk(new ExpandDataDetails("categories"))));
				expandRevs.setBranches(CollectionUtilities.toArrayList(expandTags, new ExpandDataTrunk(new ExpandDataDetails("sourceUrls")), new ExpandDataTrunk(new ExpandDataDetails("properties")), new ExpandDataTrunk(new ExpandDataDetails("outgoingRelationships")), new ExpandDataTrunk(new ExpandDataDetails(
						"incomingRelationships"))));
				expand.setBranches(CollectionUtilities.toArrayList(expandTags, expandRevs));

				final String expandString = mapper.writeValueAsString(expand);
				//final String expandEncodedString = URLEncoder.encode(expandString, "UTF-8");

				final RESTTopicV1 topic = client.getJSONTopic(csId, expandString);
				// Check that the topic is a content spec
				if (!ComponentBaseTopicV1.hasTag(topic, CSConstants.CONTENT_SPEC_TAG_ID))
					return null;

				// Add the content spec revisions to the cache
				collectionsCache.add(RESTTopicV1.class, topic.getRevisions(), additionalKeys, true);
				topicRevisions = topic.getRevisions();
			}

			// Create the unique array from the revisions
			if (topicRevisions != null && topicRevisions.getItems() != null)
			{
				for (RESTTopicV1 topicRev : topicRevisions.getItems())
				{
					Object[] revision = new Object[2];
					revision[0] = topicRev.getRevision();
					revision[1] = topicRev.getLastModified();
					results.add(revision);
				}
			}
			return results;
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return null;
	}

	/*
	 * Gets a list of all content specifications in the database or the first 50
	 * if limit is set
	 */
	public List<RESTTopicV1> getContentSpecs(Integer startPos, Integer limit)
	{
		final List<RESTTopicV1> results = new ArrayList<RESTTopicV1>();

		try
		{
			final BaseRestCollectionV1<RESTTopicV1, RESTTopicCollectionV1> topics;

			// Set the startPos and limit to zero if they are null
			startPos = startPos == null ? 0 : startPos;
			limit = limit == null ? 0 : limit;

			final List<String> additionalKeys = CollectionUtilities.toArrayList("start-" + startPos, "end-" + (startPos + limit));
			if (collectionsCache.containsKey(RESTTopicV1.class, additionalKeys))
			{
				topics = collectionsCache.get(RESTTopicV1.class, RESTTopicCollectionV1.class, additionalKeys);
			}
			else
			{
				/* We need to expand the topics collection */
				final ExpandDataTrunk expand = new ExpandDataTrunk();
				ExpandDataDetails expandDataDetails = new ExpandDataDetails("topics");
				if (startPos != 0 && startPos != null)
				{
					expandDataDetails.setStart(startPos);
				}
				if (limit != 0 && limit != null)
				{
					expandDataDetails.setEnd(startPos + limit);
				}

				final ExpandDataTrunk expandTopics = new ExpandDataTrunk(expandDataDetails);
				final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails("tags"));
				expandTags.setBranches(CollectionUtilities.toArrayList(new ExpandDataTrunk(new ExpandDataDetails("categories"))));
				expandTopics.setBranches(CollectionUtilities.toArrayList(expandTags, new ExpandDataTrunk(new ExpandDataDetails("properties"))));

				expand.setBranches(CollectionUtilities.toArrayList(expandTopics));

				final String expandString = mapper.writeValueAsString(expand);
				//final String expandEncodedString = URLEncoder.encode(expandString, "UTF-8");

				PathSegment path = new PathSegmentImpl("query;tag" + CSConstants.CONTENT_SPEC_TAG_ID + "=1;", false);
				topics = client.getJSONTopicsWithQuery(path, expandString);
				collectionsCache.add(RESTTopicV1.class, topics, additionalKeys);
			}

			return topics.getItems();
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return results;
	}

	/*
	 * Gets a list of Revision's from the CSProcessor database for a specific
	 * content spec
	 */
	public Integer getLatestCSRevById(final Integer csId)
	{
		final RESTTopicV1 cs = getTopicById(csId, null, false);
		if (cs != null)
		{
			return cs.getRevision();
		}
		return null;
	}

	/*
	 * Get the Pre Processed Content Specification for a ID and Revision
	 */
	public RESTTopicV1 getPreContentSpecById(final Integer id, final Integer revision)
	{
		final RESTTopicV1 cs = getContentSpecById(id, revision);
		final List<Object[]> specRevisions = getContentSpecRevisionsById(id);

		if (specRevisions == null)
			return null;

		// Create a sorted set of revision ids that are less the the current
		// revision
		final SortedSet<Integer> sortedSpecRevisions = new TreeSet<Integer>();
		for (final Object[] specRev : specRevisions)
		{
			if ((Integer) specRev[0] <= cs.getRevision())
			{
				sortedSpecRevisions.add((Integer) specRev[0]);
			}
		}

		if (sortedSpecRevisions.size() == 0)
			return null;

		// Find the Pre Content Spec from the revisions
		RESTTopicV1 preContentSpec = null;
		Integer specRev = sortedSpecRevisions.last();
		while (specRev != null)
		{
			final RESTTopicV1 contentSpecRev = getContentSpecById(id, specRev);
			if (ComponentBaseRESTEntityWithPropertiesV1.<RESTTopicV1, RESTTopicCollectionV1>returnProperty(contentSpecRev, CSConstants.CSP_TYPE_PROPERTY_TAG_ID) != null && ComponentBaseRESTEntityWithPropertiesV1.returnProperty(contentSpecRev, CSConstants.CSP_TYPE_PROPERTY_TAG_ID).getValue().equals(CSConstants.CSP_PRE_PROCESSED_STRING))
			{
				preContentSpec = contentSpecRev;
				break;
			}
			specRev = sortedSpecRevisions.headSet(specRev).isEmpty() ? null : sortedSpecRevisions.headSet(specRev).last();
		}
		return preContentSpec;
	}

	/*
	 * Get the Pre Processed Content Specification for a ID and Revision
	 */
	public RESTTopicV1 getPostContentSpecById(final Integer id, final Integer revision)
	{
		final RESTTopicV1 cs = getContentSpecById(id, revision);
		final List<Object[]> specRevisions = getContentSpecRevisionsById(id);

		if (specRevisions == null)
			return null;

		// Create a sorted set of revision ids that are less the the current
		// revision
		final SortedSet<Integer> sortedSpecRevisions = new TreeSet<Integer>();
		for (final Object[] specRev : specRevisions)
		{
			if ((Integer) specRev[0] <= cs.getRevision())
			{
				sortedSpecRevisions.add((Integer) specRev[0]);
			}
		}

		if (sortedSpecRevisions.size() == 0)
			return null;

		// Find the Pre Content Spec from the revisions
		RESTTopicV1 postContentSpec = null;
		Integer specRev = sortedSpecRevisions.last();
		while (specRev != null)
		{
			final RESTTopicV1 contentSpecRev = getContentSpecById(id, specRev);
			if (ComponentBaseRESTEntityWithPropertiesV1.returnProperty(contentSpecRev, CSConstants.CSP_TYPE_PROPERTY_TAG_ID) != null && ComponentBaseRESTEntityWithPropertiesV1.returnProperty(contentSpecRev, CSConstants.CSP_TYPE_PROPERTY_TAG_ID).getValue().equals(CSConstants.CSP_POST_PROCESSED_STRING))
			{
				postContentSpec = contentSpecRev;
				break;
			}
			specRev = sortedSpecRevisions.headSet(specRev).isEmpty() ? null : sortedSpecRevisions.headSet(specRev).last();
		}
		return postContentSpec;
	}

	// MISC QUERIES

	/*
	 * Gets a List of all type tuples for a specified name.
	 */
	public RESTTagV1 getTypeByName(final String name)
	{
		final List<RESTTagV1> tags = getTagsByName(name);

		// Iterate through the list of tags and check if the tag is a Type and
		// matches the name.
		if (tags != null)
		{
			for (final RESTTagV1 tag : tags)
			{
				if (ComponentTagV1.containedInCategory(tag, CSConstants.TYPE_CATEGORY_ID) && tag.getName().equals(name))
				{
					return tag;
				}
			}
		}
		return null;
	}

	/*
	 * Gets an Image File for a specific ID
	 */
	public RESTImageV1 getImageById(final int id)
	{
		try
		{
			return client.getJSONImage(id, null);
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return null;
	}

	// AUTHOR INFORMATION QUERIES

	/*
	 * Gets the Author Tag for a specific topic
	 */
	public RESTTagV1 getAuthorForTopic(final int topicId, final Integer rev)
	{
		if (rev == null)
		{
			final List<RESTTagV1> tags = this.getTagsByTopicId(topicId);

			if (tags != null)
			{
				for (RESTTagV1 tag : tags)
				{
					if (ComponentTagV1.containedInCategory(tag, CSConstants.WRITER_CATEGORY_ID))
						return tag;
				}
			}
		}
		else
		{
			final RESTTopicV1 topic = this.getTopicById(topicId, rev);
			if (topic != null)
			{
				for (RESTTopicV1 topicRevision : topic.getRevisions().getItems())
				{
					if (topicRevision.getRevision().equals(rev))
					{
						List<RESTTagV1> writerTags = ComponentBaseTopicV1.returnTagsInCategoriesByID(topicRevision, CollectionUtilities.toArrayList(CSConstants.WRITER_CATEGORY_ID));
						if (writerTags.size() == 1)
							return writerTags.get(0);
						break;
					}
				}
			}
		}
		return null;
	}

	/*
	 * Gets the Author Information for a specific author
	 */
	public AuthorInformation getAuthorInformation(final Integer authorId)
	{
		final AuthorInformation authInfo = new AuthorInformation();
		authInfo.setAuthorId(authorId);
		final RESTTagV1 tag = getTagById(authorId);
		if (tag != null && ComponentBaseRESTEntityWithPropertiesV1.<RESTTagV1, RESTTagCollectionV1>returnProperty(tag, CSConstants.FIRST_NAME_PROPERTY_TAG_ID) != null && ComponentBaseRESTEntityWithPropertiesV1.returnProperty(tag, CSConstants.LAST_NAME_PROPERTY_TAG_ID) != null)
		{
			authInfo.setFirstName(ComponentBaseRESTEntityWithPropertiesV1.returnProperty(tag, CSConstants.FIRST_NAME_PROPERTY_TAG_ID).getValue());
			authInfo.setLastName(ComponentBaseRESTEntityWithPropertiesV1.returnProperty(tag, CSConstants.LAST_NAME_PROPERTY_TAG_ID).getValue());
			if (ComponentBaseRESTEntityWithPropertiesV1.returnProperty(tag, CSConstants.EMAIL_PROPERTY_TAG_ID) != null)
			{
				authInfo.setEmail(ComponentBaseRESTEntityWithPropertiesV1.returnProperty(tag, CSConstants.EMAIL_PROPERTY_TAG_ID).getValue());
			}
			if (ComponentBaseRESTEntityWithPropertiesV1.returnProperty(tag, CSConstants.ORGANIZATION_PROPERTY_TAG_ID) != null)
			{
				authInfo.setOrganization(ComponentBaseRESTEntityWithPropertiesV1.returnProperty(tag, CSConstants.ORGANIZATION_PROPERTY_TAG_ID).getValue());
			}
			if (ComponentBaseRESTEntityWithPropertiesV1.returnProperty(tag, CSConstants.ORG_DIVISION_PROPERTY_TAG_ID) != null)
			{
				authInfo.setOrgDivision(ComponentBaseRESTEntityWithPropertiesV1.returnProperty(tag, CSConstants.ORG_DIVISION_PROPERTY_TAG_ID).getValue());
			}
			return authInfo;
		}
		return null;
	}

	/*
	 * Gets a list of all content specifications in the database
	 */
	public int getNumberOfContentSpecs()
	{
		final List<RESTTopicV1> contentSpecs = getContentSpecs(0, 0);
		return contentSpecs.size();
	}
}
