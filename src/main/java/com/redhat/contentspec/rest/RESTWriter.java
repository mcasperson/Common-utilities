package com.redhat.contentspec.rest;

import org.apache.log4j.Logger;
import java.util.*;

import com.redhat.contentspec.constants.CSConstants;
import com.redhat.contentspec.rest.utils.RESTCollectionCache;
import com.redhat.contentspec.rest.utils.RESTEntityCache;
import com.redhat.ecs.commonutils.ExceptionUtilities;
import com.redhat.topicindex.rest.collections.RESTCategoryCollectionV1;
import com.redhat.topicindex.rest.collections.RESTPropertyTagCollectionV1;
import com.redhat.topicindex.rest.collections.RESTTagCollectionV1;
import com.redhat.topicindex.rest.collections.RESTTopicCollectionV1;
import com.redhat.topicindex.rest.collections.RESTTopicSourceUrlCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTCategoryV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTPropertyTagV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTTagV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTTopicSourceUrlV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTTopicV1;
import com.redhat.topicindex.rest.sharedinterface.RESTInterfaceV1;

public class RESTWriter
{

	private static final Logger log = Logger.getLogger(RESTWriter.class);

	private final RESTInterfaceV1 client;
	private final RESTReader reader;
	private final RESTEntityCache entityCache;
	private final RESTCollectionCache collectionsCache;

	public RESTWriter(RESTReader reader, RESTInterfaceV1 client, RESTEntityCache cache, RESTCollectionCache collectionsCache)
	{
		this.reader = reader;
		this.client = client;
		this.entityCache = cache;
		this.collectionsCache = collectionsCache;
	}

	/*
	 * Writes a Category tuple to the database using the data provided.
	 */
	public Integer createCategory(boolean mutuallyExclusive, String name)
	{
		Integer insertId = null;
		try
		{
			RESTCategoryV1 category = new RESTCategoryV1();
			category.explicitSetMutuallyExclusive(mutuallyExclusive);
			category.explicitSetName(name);

			category = client.createJSONCategory(null, category);
			insertId = category.getId();
			collectionsCache.expire(RESTCategoryV1.class);
		}
		catch (Exception e)
		{
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		return insertId;
	}

	/*
	 * Writes a Tag tuple to the database using the data provided.
	 */
	public Integer createTag(String name, String description, RESTCategoryCollectionV1 categories)
	{
		Integer insertId = null;
		try
		{
			RESTTagV1 tag = new RESTTagV1();
			tag.explicitSetName(name);
			tag.explicitSetDescription(description);
			for (RESTCategoryV1 category : categories.getItems())
			{
				category.setAddItem(true);
			}
			tag.explicitSetCategories(categories);

			tag = client.createJSONTag(null, tag);
			insertId = tag.getId();
			collectionsCache.expire(RESTTagV1.class);
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return insertId;
	}

	/*
	 * Writes a Topic tuple to the database using the data provided.
	 */
	public Integer createTopic(String title, String text, String description, Date timestamp)
	{
		return createTopic(title, text, description, timestamp, null, null, null, null, null);
	}

	/*
	 * Writes a Topic tuple to the database using the data provided.
	 */
	public Integer createTopic(String title, String text, String description, Date timestamp, RESTTopicSourceUrlCollectionV1 sourceUrls, RESTTopicCollectionV1 incomingRelationships, RESTTopicCollectionV1 outgoingRelationships, RESTTagCollectionV1 tags,
			RESTPropertyTagCollectionV1 properties)
	{
		Integer insertId = null;
		try
		{

			RESTTopicV1 topic = new RESTTopicV1();

			topic.explicitSetTitle(title);
			topic.explicitSetDescription(description);
			if (timestamp != null)
			{
				topic.setCreated(timestamp);
			}
			if (text != null)
			{
				topic.explicitSetXml(text);
			}
			if (!incomingRelationships.getItems().isEmpty())
			{
				for (RESTTopicV1 incomingRelationship : incomingRelationships.getItems())
				{
					incomingRelationship.setAddItem(true);
				}
				topic.explicitSetIncomingRelationships(incomingRelationships);
			}
			if (!outgoingRelationships.getItems().isEmpty())
			{
				for (RESTTopicV1 outgoingRelationship : outgoingRelationships.getItems())
				{
					outgoingRelationship.setAddItem(true);
				}
				topic.explicitSetOutgoingRelationships(outgoingRelationships);
			}
			if (!tags.getItems().isEmpty())
			{
				for (RESTTagV1 tag : tags.getItems())
				{
					tag.setAddItem(true);
				}
				topic.explicitSetTags(tags);
			}
			if (!properties.getItems().isEmpty())
			{
				for (RESTPropertyTagV1 tag : properties.getItems())
				{
					tag.setAddItem(true);
				}
				topic.explicitSetProperties(properties);
			}
			if (!sourceUrls.getItems().isEmpty())
			{
				for (RESTTopicSourceUrlV1 sourceUrl : sourceUrls.getItems())
				{
					sourceUrl.setAddItem(true);
				}
				topic.explicitSetSourceUrls_OTM(sourceUrls);
			}

			topic = client.createJSONTopic(null, topic);
			insertId = topic.getId();
			collectionsCache.expire(RESTTopicV1.class);
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return insertId;
	}

	/*
	 * Updates a Topic tuple in the database using the data provided.
	 */
	public Integer updateTopic(Integer topicId, String title, String text, String description, Date timestamp)
	{
		return updateTopic(topicId, title, text, description, timestamp, null, null, null, null, null);
	}

	/*
	 * Updates a Topic tuple in the database using the data provided.
	 */
	public Integer updateTopic(Integer topicId, String title, String text, String description, Date timestamp, RESTTopicSourceUrlCollectionV1 sourceUrls, RESTTopicCollectionV1 incomingRelationships, RESTTopicCollectionV1 outgoingRelationships, RESTTagCollectionV1 tags,
			RESTPropertyTagCollectionV1 properties)
	{
		Integer insertId = null;
		try
		{

			RESTTopicV1 topic = reader.getTopicById(topicId, null);

			if (title != null)
			{
				topic.explicitSetTitle(title);
			}
			if (description != null)
			{
				topic.explicitSetDescription(description);
			}
			if (timestamp != null)
			{
				topic.setCreated(timestamp);
			}
			if (text != null)
			{
				topic.explicitSetXml(text);
			}
			if (!incomingRelationships.getItems().isEmpty())
			{
				for (RESTTopicV1 incomingRelationship : incomingRelationships.getItems())
				{
					incomingRelationship.setAddItem(true);
				}
				topic.explicitSetIncomingRelationships(incomingRelationships);
			}
			if (!outgoingRelationships.getItems().isEmpty())
			{
				for (RESTTopicV1 outgoingRelationship : outgoingRelationships.getItems())
				{
					outgoingRelationship.setAddItem(true);
				}
				topic.explicitSetOutgoingRelationships(outgoingRelationships);
			}
			if (!tags.getItems().isEmpty())
			{
				for (RESTTagV1 tag : tags.getItems())
				{
					tag.setAddItem(true);
				}
				topic.explicitSetTags(tags);
			}
			if (!properties.getItems().isEmpty())
			{
				for (RESTPropertyTagV1 tag : properties.getItems())
				{
					tag.setAddItem(true);
				}
				topic.explicitSetProperties(properties);
			}
			if (!sourceUrls.getItems().isEmpty())
			{
				for (RESTTopicSourceUrlV1 sourceUrl : sourceUrls.getItems())
				{
					sourceUrl.setAddItem(true);
				}
				topic.explicitSetSourceUrls_OTM(sourceUrls);
			}

			topic = client.createJSONTopic(null, topic);
			insertId = topic.getId();
			entityCache.expire(RESTTopicV1.class, insertId);
			collectionsCache.expire(RESTTopicV1.class);
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return insertId;
	}

	/*
	 * Writes a ContentSpecs tuple to the database using the data provided.
	 */
	public Integer createContentSpec(String title, String preContentSpec, String dtd, String createdBy)
	{
		try
		{
			RESTTopicV1 contentSpec = new RESTTopicV1();
			contentSpec.explicitSetTitle(title);
			contentSpec.explicitSetXml(preContentSpec);

			// Create the Added By, Content Spec Type and DTD property tags
			RESTPropertyTagCollectionV1 properties = new RESTPropertyTagCollectionV1();
			RESTPropertyTagV1 addedBy = client.getJSONPropertyTag(CSConstants.ADDED_BY_PROPERTY_TAG_ID, null);
			addedBy.explicitSetValue(createdBy);
			addedBy.setAddItem(true);

			RESTPropertyTagV1 typePropertyTag = client.getJSONPropertyTag(CSConstants.CSP_TYPE_PROPERTY_TAG_ID, null);
			typePropertyTag.explicitSetValue(CSConstants.CSP_PRE_PROCESSED_STRING);
			typePropertyTag.setAddItem(true);

			RESTPropertyTagV1 dtdPropertyTag = client.getJSONPropertyTag(CSConstants.DTD_PROPERTY_TAG_ID, null);
			dtdPropertyTag.explicitSetValue(dtd);
			dtdPropertyTag.setAddItem(true);

			properties.addItem(addedBy);
			properties.addItem(dtdPropertyTag);
			properties.addItem(typePropertyTag);

			contentSpec.explicitSetProperties(properties);

			// Add the Content Specification Type Tag
			RESTTagCollectionV1 tags = new RESTTagCollectionV1();
			RESTTagV1 typeTag = client.getJSONTag(CSConstants.CONTENT_SPEC_TAG_ID, null);
			typeTag.setAddItem(true);
			tags.addItem(typeTag);

			contentSpec.explicitSetTags(tags);

			contentSpec = client.createJSONTopic("", contentSpec);
			if (contentSpec != null)
				return contentSpec.getId();
			collectionsCache.expire(RESTTopicV1.class);
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return null;
	}

	/*
	 * Updates a ContentSpecs tuple from the database using the data provided.
	 */
	public boolean updateContentSpec(Integer id, String title, String preContentSpec, String dtd)
	{
		try
		{
			RESTTopicV1 contentSpec = reader.getContentSpecById(id, null);

			if (contentSpec == null)
				return false;

			// Change the title if it's different
			if (!contentSpec.getTitle().equals(title))
			{
				contentSpec.explicitSetTitle(title);
			}

			contentSpec.explicitSetXml(preContentSpec);

			// Update the Content Spec Type and DTD property tags
			RESTPropertyTagCollectionV1 properties = contentSpec.getProperties();
			if (properties.getItems() != null && !properties.getItems().isEmpty())
			{

				boolean newDTD = false;

				// Loop through and remove any Type or DTD tags if they don't
				// match
				for (RESTPropertyTagV1 property : properties.getItems())
				{
					if (property.getId().equals(CSConstants.CSP_TYPE_PROPERTY_TAG_ID))
					{
						property.setRemoveItem(true);
					}
					else if (property.getId().equals(CSConstants.DTD_PROPERTY_TAG_ID))
					{
						if (!property.getValue().equals(dtd))
						{
							property.setRemoveItem(true);
							newDTD = true;
						}
					}
				}

				// The property tag should never match a pre tag
				RESTPropertyTagV1 typePropertyTag = client.getJSONPropertyTag(CSConstants.CSP_TYPE_PROPERTY_TAG_ID, null);
				typePropertyTag.explicitSetValue(CSConstants.CSP_PRE_PROCESSED_STRING);
				typePropertyTag.setAddItem(true);

				properties.addItem(typePropertyTag);

				// If the DTD has changed then it needs to be re-added
				if (newDTD)
				{
					RESTPropertyTagV1 dtdPropertyTag = client.getJSONPropertyTag(CSConstants.DTD_PROPERTY_TAG_ID, null);
					dtdPropertyTag.explicitSetValue(dtd);
					dtdPropertyTag.setAddItem(true);

					properties.addItem(dtdPropertyTag);
				}
			}

			contentSpec.explicitSetProperties(properties);

			contentSpec = client.updateJSONTopic("", contentSpec);
			if (contentSpec != null)
			{
				entityCache.expire(RESTTopicV1.class, id);
				collectionsCache.expire(RESTTopicV1.class);
				return true;
			}
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return false;
	}

	/*
	 * Writes a ContentSpecs tuple to the database using the data provided.
	 */
	public boolean updatePostContentSpec(Integer id, String postContentSpec)
	{
		try
		{
			RESTTopicV1 contentSpec = reader.getContentSpecById(id, null);
			if (contentSpec == null)
				return false;

			contentSpec.explicitSetXml(postContentSpec);

			// Update Content Spec Type
			RESTPropertyTagCollectionV1 properties = contentSpec.getProperties();
			if (properties.getItems() != null && !properties.getItems().isEmpty())
			{

				// Loop through and remove the type
				for (RESTPropertyTagV1 property : properties.getItems())
				{
					if (property.getId().equals(CSConstants.CSP_TYPE_PROPERTY_TAG_ID))
					{
						property.setRemoveItem(true);
					}
				}

				RESTPropertyTagV1 typePropertyTag = client.getJSONPropertyTag(CSConstants.CSP_TYPE_PROPERTY_TAG_ID, null);
				typePropertyTag.explicitSetValue(CSConstants.CSP_POST_PROCESSED_STRING);
				typePropertyTag.setAddItem(true);

				properties.addItem(typePropertyTag);

				contentSpec.explicitSetProperties(properties);
			}

			contentSpec = client.updateJSONTopic("", contentSpec);
			if (contentSpec != null)
			{
				entityCache.expire(RESTTopicV1.class, id);
				collectionsCache.expire(RESTTopicV1.class);
				return true;
			}
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return false;
	}

	/*
	 * Delete a Content Specification from the database.
	 */
	public boolean deleteContentSpec(Integer id)
	{
		try
		{
			client.deleteJSONTopic(id, null);
			entityCache.expire(RESTTopicV1.class, id);
			collectionsCache.expire(RESTTopicV1.class);
			return true;
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return false;
	}
}