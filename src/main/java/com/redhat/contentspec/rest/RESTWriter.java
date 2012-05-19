package com.redhat.contentspec.rest;

import org.apache.log4j.Logger;
import java.util.*;

import com.redhat.contentspec.constants.CSConstants;
import com.redhat.contentspec.rest.utils.RESTCollectionCache;
import com.redhat.contentspec.rest.utils.RESTEntityCache;
import com.redhat.ecs.commonutils.ExceptionUtilities;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.*;
import com.redhat.topicindex.rest.sharedinterface.RESTInterfaceV1;

public class RESTWriter {
	
	private static final Logger log = Logger.getLogger(RESTWriter.class);
	
	private final RESTInterfaceV1 client;
	private final RESTReader reader;
	private final RESTEntityCache entityCache;
	private final RESTCollectionCache collectionsCache;
	
	public RESTWriter(RESTReader reader, RESTInterfaceV1 client, RESTEntityCache cache, RESTCollectionCache collectionsCache) {
		this.reader = reader;
		this.client = client;
		this.entityCache = cache;
		this.collectionsCache = collectionsCache;
	}
	
	/*
	 * Writes a Category tuple to the database using the data provided.
	 */
	public Integer createCategory(boolean mutuallyExclusive, String name) {
		Integer insertId = null;
		try {
			CategoryV1 category = new CategoryV1();
			category.setMutuallyExclusiveExplicit(mutuallyExclusive);
			category.setNameExplicit(name);
			
			category = client.createJSONCategory(null, category);
			insertId = category.getId();
			collectionsCache.expire(CategoryV1.class);
		} catch(Exception e) {
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		return insertId;
	}
	
	/*
	 * Writes a Tag tuple to the database using the data provided.
	 */
	public Integer createTag(String name, String description, BaseRestCollectionV1<CategoryV1> categories) {
		Integer insertId = null;
		try {
			TagV1 tag = new TagV1();
			tag.setNameExplicit(name);
			tag.setDescriptionExplicit(description);
			for (CategoryV1 category: categories.getItems()) {
				category.setAddItem(true);
			}
			tag.setCategoriesExplicit(categories);
			
			tag = client.createJSONTag(null, tag);
			insertId = tag.getId();
			collectionsCache.expire(TagV1.class);
		} catch(Exception e) {
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return insertId;
	}
	
	/*
	 * Writes a Topic tuple to the database using the data provided.
	 */
	public Integer createTopic(String title, String text, String description, Date timestamp) {
		return createTopic(title, text, description, timestamp, null, null, null, null, null);
	}
	
	/*
	 * Writes a Topic tuple to the database using the data provided.
	 */
	public Integer createTopic(String title, String text, String description, Date timestamp, 
			BaseRestCollectionV1<TopicSourceUrlV1> sourceUrls,
			BaseRestCollectionV1<TopicV1> incomingRelationships,
			BaseRestCollectionV1<TopicV1> outgoingRelationships,
			BaseRestCollectionV1<TagV1> tags,
			BaseRestCollectionV1<PropertyTagV1> properties) {
		Integer insertId = null;
		try {

			TopicV1 topic = new TopicV1();

		  	topic.setTitleExplicit(title);
		  	topic.setDescriptionExplicit(description);
		  	if (timestamp != null) {
		  		topic.setCreated(timestamp);
		  	}
		  	if (text != null) {
		  		topic.setXmlExplicit(text);
		  	}
		  	if (!incomingRelationships.getItems().isEmpty()) {
		  		for (TopicV1 incomingRelationship: incomingRelationships.getItems()) {
		  			incomingRelationship.setAddItem(true);
		  		}
		  		topic.setIncomingRelationshipsExplicit(incomingRelationships);
		  	}
		  	if (!outgoingRelationships.getItems().isEmpty()) {
		  		for (TopicV1 outgoingRelationship: outgoingRelationships.getItems()) {
		  			outgoingRelationship.setAddItem(true);
		  		}
		  		topic.setOutgoingRelationshipsExplicit(outgoingRelationships);
		  	}
		  	if (!tags.getItems().isEmpty()) {
		  		for (TagV1 tag: tags.getItems()) {
		  			tag.setAddItem(true);
		  		}
		  		topic.setTagsExplicit(tags);
		  	}
		  	if (!properties.getItems().isEmpty()) {
		  		for (PropertyTagV1 tag: properties.getItems()) {
		  			tag.setAddItem(true);
		  		}
		  		topic.setPropertiesExplicit(properties);
		  	}
		  	if (!sourceUrls.getItems().isEmpty()) {
		  		for (TopicSourceUrlV1 sourceUrl: sourceUrls.getItems()) {
		  			sourceUrl.setAddItem(true);
		  		}
		  		topic.setSourceUrlsExplicit_OTM(sourceUrls);
		  	}
		  	
		  	topic = client.createJSONTopic(null, topic);
		  	insertId = topic.getId();
		  	collectionsCache.expire(TopicV1.class);
		} catch(Exception e) {
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return insertId;
	}
	
	/*
	 * Updates a Topic tuple in the database using the data provided.
	 */
	public Integer updateTopic(Integer topicId, String title, String text, String description, Date timestamp) {
		return updateTopic(topicId, title, text, description, timestamp, null, null, null, null, null);
	}
	
	/*
	 * Updates a Topic tuple in the database using the data provided.
	 */
	public Integer updateTopic(Integer topicId, String title, String text, String description, Date timestamp, 
			BaseRestCollectionV1<TopicSourceUrlV1> sourceUrls,
			BaseRestCollectionV1<TopicV1> incomingRelationships,
			BaseRestCollectionV1<TopicV1> outgoingRelationships,
			BaseRestCollectionV1<TagV1> tags,
			BaseRestCollectionV1<PropertyTagV1> properties) {
		Integer insertId = null;
		try {

			TopicV1 topic = reader.getTopicById(topicId, null);

			if (title != null) {
				topic.setTitleExplicit(title);
			}
			if (description != null) {
				topic.setDescriptionExplicit(description);
			}
		  	if (timestamp != null) {
		  		topic.setCreated(timestamp);
		  	}
		  	if (text != null) {
		  		topic.setXmlExplicit(text);
		  	}
		  	if (!incomingRelationships.getItems().isEmpty()) {
		  		for (TopicV1 incomingRelationship: incomingRelationships.getItems()) {
		  			incomingRelationship.setAddItem(true);
		  		}
		  		topic.setIncomingRelationshipsExplicit(incomingRelationships);
		  	}
		  	if (!outgoingRelationships.getItems().isEmpty()) {
		  		for (TopicV1 outgoingRelationship: outgoingRelationships.getItems()) {
		  			outgoingRelationship.setAddItem(true);
		  		}
		  		topic.setOutgoingRelationshipsExplicit(outgoingRelationships);
		  	}
		  	if (!tags.getItems().isEmpty()) {
		  		for (TagV1 tag: tags.getItems()) {
		  			tag.setAddItem(true);
		  		}
		  		topic.setTagsExplicit(tags);
		  	}
		  	if (!properties.getItems().isEmpty()) {
		  		for (PropertyTagV1 tag: properties.getItems()) {
		  			tag.setAddItem(true);
		  		}
		  		topic.setPropertiesExplicit(properties);
		  	}
		  	if (!sourceUrls.getItems().isEmpty()) {
		  		for (TopicSourceUrlV1 sourceUrl: sourceUrls.getItems()) {
		  			sourceUrl.setAddItem(true);
		  		}
		  		topic.setSourceUrlsExplicit_OTM(sourceUrls);
		  	}
		  	
		  	topic = client.createJSONTopic(null, topic);
		  	insertId = topic.getId();
		  	entityCache.expire(TopicV1.class, insertId);
		  	collectionsCache.expire(TopicV1.class);
		} catch(Exception e) {
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return insertId;
	}
	
	/*
	 * Writes a ContentSpecs tuple to the database using the data provided.
	 */
	public Integer createContentSpec(String title, String preContentSpec, String dtd, String createdBy) {
		try {
			TopicV1 contentSpec = new TopicV1();
			contentSpec.setTitleExplicit(title);
			contentSpec.setXmlExplicit(preContentSpec);
			
			// Create the Added By, Content Spec Type and DTD property tags
			BaseRestCollectionV1<PropertyTagV1> properties = new BaseRestCollectionV1<PropertyTagV1>();
			PropertyTagV1 addedBy = client.getJSONPropertyTag(CSConstants.ADDED_BY_PROPERTY_TAG_ID, null);
			addedBy.setValueExplicit(createdBy);
			addedBy.setAddItem(true);
			
			PropertyTagV1 typePropertyTag = client.getJSONPropertyTag(CSConstants.CSP_TYPE_PROPERTY_TAG_ID, null);
			typePropertyTag.setValueExplicit(CSConstants.CSP_PRE_PROCESSED_STRING);
			typePropertyTag.setAddItem(true);
			
			PropertyTagV1 dtdPropertyTag = client.getJSONPropertyTag(CSConstants.DTD_PROPERTY_TAG_ID, null);
			dtdPropertyTag.setValueExplicit(dtd);
			dtdPropertyTag.setAddItem(true);
			
			properties.addItem(addedBy);
			properties.addItem(dtdPropertyTag);
			properties.addItem(typePropertyTag);
			
			contentSpec.setPropertiesExplicit(properties);
			
			// Add the Content Specification Type Tag
			BaseRestCollectionV1<TagV1> tags = new BaseRestCollectionV1<TagV1>();
			TagV1 typeTag = client.getJSONTag(CSConstants.CONTENT_SPEC_TAG_ID, null);
			typeTag.setAddItem(true);
			tags.addItem(typeTag);
			
			contentSpec.setTagsExplicit(tags);
			
			contentSpec = client.createJSONTopic("", contentSpec);
			if (contentSpec != null) return contentSpec.getId();
			collectionsCache.expire(TopicV1.class);
		} catch (Exception e) {
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return null;
	}
	
	/*
	 * Updates a ContentSpecs tuple from the database using the data provided.
	 */
	public boolean updateContentSpec(Integer id, String title, String preContentSpec, String dtd) {
		try {
			TopicV1 contentSpec = reader.getContentSpecById(id, null);
			
			if (contentSpec == null) return false;
			
			// Change the title if it's different
			if (!contentSpec.getTitle().equals(title)) {
				contentSpec.setTitleExplicit(title);
			}
			
			contentSpec.setXmlExplicit(preContentSpec);
			
			// Update the Content Spec Type and DTD property tags
			BaseRestCollectionV1<PropertyTagV1> properties = contentSpec.getProperties();
			if (properties.getItems() != null && !properties.getItems().isEmpty()) {
				
				boolean newDTD = false;
				
				// Loop through and remove any Type or DTD tags if they don't match
				for (PropertyTagV1 property: properties.getItems()) {
					if (property.getId().equals(CSConstants.CSP_TYPE_PROPERTY_TAG_ID)) {
						property.setRemoveItem(true);
					} else if (property.getId().equals(CSConstants.DTD_PROPERTY_TAG_ID)) {
						if (!property.getValue().equals(dtd)) {
							property.setRemoveItem(true);
							newDTD = true;
						}
					}
				}
				
				// The property tag should never match a pre tag
				PropertyTagV1 typePropertyTag = client.getJSONPropertyTag(CSConstants.CSP_TYPE_PROPERTY_TAG_ID, null);
				typePropertyTag.setValueExplicit(CSConstants.CSP_PRE_PROCESSED_STRING);
				typePropertyTag.setAddItem(true);
				
				properties.addItem(typePropertyTag);
				
				// If the DTD has changed then it needs to be re-added
				if (newDTD) {
					PropertyTagV1 dtdPropertyTag = client.getJSONPropertyTag(CSConstants.DTD_PROPERTY_TAG_ID, null);
					dtdPropertyTag.setValueExplicit(dtd);
					dtdPropertyTag.setAddItem(true);
					
					properties.addItem(dtdPropertyTag);
				}		
			}
			
			contentSpec.setPropertiesExplicit(properties);
			
			contentSpec = client.updateJSONTopic("", contentSpec);
			if (contentSpec != null) {
				entityCache.expire(TopicV1.class, id);
				collectionsCache.expire(TopicV1.class);
				return true;
			}
		} catch (Exception e) {
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return false;
	}
	
	/*
	 * Writes a ContentSpecs tuple to the database using the data provided.
	 */
	public boolean updatePostContentSpec(Integer id, String postContentSpec) {
		try {
			TopicV1 contentSpec = reader.getContentSpecById(id, null);
			if (contentSpec == null) return false;
			
			contentSpec.setXmlExplicit(postContentSpec);
			
			// Update Content Spec Type
			BaseRestCollectionV1<PropertyTagV1> properties = contentSpec.getProperties();
			if (properties.getItems() != null && !properties.getItems().isEmpty()) {
				
				// Loop through and remove the type
				for (PropertyTagV1 property: properties.getItems()) {
					if (property.getId().equals(CSConstants.CSP_TYPE_PROPERTY_TAG_ID)) {
						property.setRemoveItem(true);
					}
				}
				
				PropertyTagV1 typePropertyTag = client.getJSONPropertyTag(CSConstants.CSP_TYPE_PROPERTY_TAG_ID, null);
				typePropertyTag.setValueExplicit(CSConstants.CSP_POST_PROCESSED_STRING);
				typePropertyTag.setAddItem(true);
				
				properties.addItem(typePropertyTag);
				
				contentSpec.setPropertiesExplicit(properties);
			}
			
			contentSpec = client.updateJSONTopic("", contentSpec);
			if (contentSpec != null) {
				entityCache.expire(TopicV1.class, id);
				collectionsCache.expire(TopicV1.class);
				return true;
			}
		} catch (Exception e) {
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return false;
	}
	
	/*
	 * Delete a Content Specification from the database.
	 */
	public boolean deleteContentSpec(Integer id) {
		try {
			client.deleteJSONTopic(id, null);
			entityCache.expire(TopicV1.class, id);
			collectionsCache.expire(TopicV1.class);
			return true;
		} catch (Exception e) {
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		return false;
	}
	
	/*
	 * Writes a Category tuple to the database using the data provided.
	 */
	/*@SuppressWarnings("serial")
	public Integer createTopicSnapshot(int id, String name, Set<Integer> topicIds) {
		Integer insertId = null;
		BaseRestCollectionV1<SnapshotTopicV1> newSnapshotTopics = new BaseRestCollectionV1<SnapshotTopicV1>();
		try {
			SnapshotV1 snapshot = new SnapshotV1();
			final Date date = new Date();
			final String systemLocale = CSConstants.DEFAULT_LOCALE;
			BaseRestCollectionV1<SnapshotTopicV1> snapshotTopics = new BaseRestCollectionV1<SnapshotTopicV1>();
			for (Integer topicId: topicIds) {
				
				// Get the latest revision for the topic
				Integer topicRev = (Integer)reader.getTopicById(topicId, null).getRevision();
				
				// Check if a snapshotTopic already exists for that revision
				SnapshotTopicV1 snapshotTopic = reader.getSnapshotTopicByTopicAndRevId(topicId,topicRev);
				
				// If no snapshotTopic exists then create the snapshot topic
				if (snapshotTopic == null) {
					snapshotTopic = new SnapshotTopicV1();
					snapshotTopic.setTopicIdExplicit(topicId);
					snapshotTopic.setTopicRevisionExplicit(topicRev);
					
					// Create the base snapshot translation data
					BaseRestCollectionV1<WorkingSnapshotTranslatedDataV1> snapshotTranslations = new BaseRestCollectionV1<WorkingSnapshotTranslatedDataV1>();
					WorkingSnapshotTranslatedDataV1 snapshotTranslationData = new WorkingSnapshotTranslatedDataV1();
					snapshotTranslationData.setAddItem(true);
					snapshotTranslationData.setTranslationLocaleExplicit(systemLocale == null ? "en-US" : systemLocale);
					snapshotTranslationData.setTranslatedXmlExplicit(reader.getTopicById(snapshotTopic.getTopicId(), null).getXml());
					snapshotTranslations.addItem(snapshotTranslationData);
					
					// Add the base snapshot translation data to the topic
					snapshotTopic.setWorkingTranslatedDataExplicit_OTM(snapshotTranslations);
					
					// Add the snapshot topic to the collection of topics
					newSnapshotTopics.addItem(snapshotTopic);
				} else {
					snapshotTopics.addItem(snapshotTopic);
				}
			}
			
			final ExpandDataTrunk expand = new ExpandDataTrunk();
			expand.setBranches(CollectionUtilities.toArrayList(new ExpandDataTrunk(new ExpandDataDetails("snapshottopics"))));
			final String expandString = mapper.writeValueAsString(expand);
			final String expandEncodedString = URLEncoder.encode(expandString, "UTF-8");
			
			// Save the new snapshot topics if any exist
			if (newSnapshotTopics.getItems() != null && !newSnapshotTopics.getItems().isEmpty()) {
				newSnapshotTopics = client.createJSONSnapshotTopics(expandEncodedString, newSnapshotTopics);
				
				for (SnapshotTopicV1 newSnapshotTopic :newSnapshotTopics.getItems()) {
					
					// Move the new topics into the base snapshot topics collection
					snapshotTopics.addItem(newSnapshotTopic);
				}
			}
			
			// Setup the topics so that they will be added to a snapshot
			for (SnapshotTopicV1 snapshotTopic: snapshotTopics.getItems()) {
				snapshotTopic.setAddItem(true);
			}
			
			// Create the base snapshot revision
			BaseRestCollectionV1<SnapshotRevisionV1> snapshotRevisions = new BaseRestCollectionV1<SnapshotRevisionV1>();
			SnapshotRevisionV1 snapshotRevision = new SnapshotRevisionV1();
			snapshotRevision.setAddItem(true);
			snapshotRevision.setDateExplicit(date);
			snapshotRevision.setNameExplicit(CSConstants.INITIAL_SNAPSHOT_REVISION_NAME);
			snapshotRevisions.addItem(snapshotRevision);
			
			// Create the snapshot
			snapshot.setSnaphotTopicsExplicit(snapshotTopics);
			snapshot.setNameExplicit(name);
			snapshot.setDateExplicit(date);
			snapshot.setSnapshotRevisionsExplicit_OTM(snapshotRevisions);
			
			// Save the snapshot
			snapshot = client.createJSONSnapshot(expandEncodedString, snapshot);

		  	insertId = snapshot.getId();
		  	entityCache.expire("SnapshotTopics");
		} catch(Exception e) {
			log.error(ExceptionUtilities.getStackTrace(e));
			// Clean up the data on the TopicIndex server
			if (newSnapshotTopics.getItems() != null && !newSnapshotTopics.getItems().isEmpty()) {
				PathSegment path = new PathSegmentImpl("ids", false);
				PathSegment translationDataPath = new PathSegmentImpl("ids", false);
				for (SnapshotTopicV1 topic: newSnapshotTopics.getItems()) {
					// Add the id for this topic to the path of deleted ids
					path.getMatrixParameters().put(   
		                    topic.getId().toString(), 
		                    new ArrayList<String>(){{add("");}});
					
					// Add all of the Working Snapshot Translation data attributes to the delete path
					for (WorkingSnapshotTranslatedDataV1 translationData: topic.getWorkingTranslatedData_OTM().getItems()) {
						translationDataPath.getMatrixParameters().put(   
			                    translationData.getId().toString(), 
			                    new ArrayList<String>(){{add("");}});
					}
				}
				
				// Delete the created data from the server
				try {
					client.deleteJSONSnapshotTopics(path, "");
					client.deleteJSONWorkingSnapshotTranslatedDatas(translationDataPath, "");
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			return null;
		}
		return insertId;
	}*/
	
}