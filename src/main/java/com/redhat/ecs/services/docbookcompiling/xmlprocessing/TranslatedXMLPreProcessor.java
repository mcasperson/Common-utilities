package com.redhat.ecs.services.docbookcompiling.xmlprocessing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redhat.ecs.commonstructures.Pair;
import com.redhat.ecs.commonutils.CollectionUtilities;
import com.redhat.ecs.commonutils.DocBookUtilities;
import com.redhat.ecs.commonutils.ExceptionUtilities;
import com.redhat.ecs.commonutils.XMLUtilities;
import com.redhat.ecs.constants.CommonConstants;
import com.redhat.ecs.services.docbookcompiling.DocbookBuilderConstants;
import com.redhat.ecs.services.docbookcompiling.DocbookBuildingOptions;
import com.redhat.ecs.services.docbookcompiling.DocbookUtils;
import com.redhat.ecs.services.docbookcompiling.xmlprocessing.structures.GenericInjectionPoint;
import com.redhat.ecs.services.docbookcompiling.xmlprocessing.structures.GenericInjectionPointDatabase;
import com.redhat.ecs.services.docbookcompiling.xmlprocessing.structures.InjectionListData;
import com.redhat.ecs.services.docbookcompiling.xmlprocessing.structures.InjectionTopicData;
import com.redhat.ecs.services.docbookcompiling.xmlprocessing.structures.TocTopicDatabase;
import com.redhat.ecs.services.docbookcompiling.xmlprocessing.structures.TranslatedTopicErrorDatabase;
import com.redhat.ecs.sort.ExternalListSort;

import com.redhat.topicindex.rest.entities.PropertyTagV1;
import com.redhat.topicindex.rest.entities.TopicV1;
import com.redhat.topicindex.rest.entities.TranslatedTopicDataV1;
import com.redhat.topicindex.rest.sort.TopicTitleSorter;
import com.redhat.topicindex.rest.sort.TopicV1TitleComparator;

/**
 * This class takes the XML from a topic and modifies it to include and injected
 * content.
 */
public class TranslatedXMLPreProcessor
{
	private TranslatedTopicErrorDatabase translatedTopicTitleErrors = new TranslatedTopicErrorDatabase();

	/**
	 * Takes a comma separated list of ints, and returns an array of Integers.
	 * This is used when processing custom injection points.
	 */
	private static List<InjectionTopicData> processTopicIdList(final String list)
	{
		/* find the individual topic ids */
		final String[] topicIDs = list.split(",");

		List<InjectionTopicData> retValue = new ArrayList<InjectionTopicData>(topicIDs.length);

		/* clean the topic ids */
		for (int i = 0; i < topicIDs.length; ++i)
		{
			final String topicId = topicIDs[i].replaceAll(XMLPreProcessor.OPTIONAL_MARKER, "").trim();
			final boolean optional = topicIDs[i].indexOf(XMLPreProcessor.OPTIONAL_MARKER) != -1;

			try
			{
				final InjectionTopicData topicData = new InjectionTopicData(Integer.parseInt(topicId), optional);
				retValue.add(topicData);
			}
			catch (final Exception ex)
			{
				/*
				 * these lists are discovered by a regular expression so we
				 * shouldn't have any trouble here with Integer.parse
				 */
				ExceptionUtilities.handleException(ex);
				retValue.add(new InjectionTopicData(-1, false));
			}
		}

		return retValue;
	}

	public List<Integer> processInjections(final boolean internal, final TranslatedTopicDataV1 translatedTopicData, final TopicV1 topic, final ArrayList<Integer> customInjectionIds, final Document xmlDocument, final TocTopicDatabase database, final DocbookBuildingOptions docbookBuildingOptions, final boolean usedFixedUrls)
	{
		/*
		 * this collection keeps a track of the injection point markers and the
		 * docbook lists that we will be replacing them with
		 */
		final HashMap<Node, InjectionListData> customInjections = new HashMap<Node, InjectionListData>();

		final List<Integer> errorTopics = new ArrayList<Integer>();

		errorTopics.addAll(processInjections(internal, translatedTopicData, topic, customInjectionIds, customInjections, XMLPreProcessor.ORDEREDLIST_INJECTION_POINT, xmlDocument, XMLPreProcessor.CUSTOM_INJECTION_SEQUENCE_RE, null, database, docbookBuildingOptions, usedFixedUrls));
		errorTopics.addAll(processInjections(internal, translatedTopicData, topic, customInjectionIds, customInjections, XMLPreProcessor.XREF_INJECTION_POINT, xmlDocument, XMLPreProcessor.CUSTOM_INJECTION_SINGLE_RE, null, database, docbookBuildingOptions, usedFixedUrls));
		errorTopics.addAll(processInjections(internal, translatedTopicData, topic, customInjectionIds, customInjections, XMLPreProcessor.ITEMIZEDLIST_INJECTION_POINT, xmlDocument, XMLPreProcessor.CUSTOM_INJECTION_LIST_RE, null, database, docbookBuildingOptions, usedFixedUrls));
		errorTopics.addAll(processInjections(internal, translatedTopicData, topic, customInjectionIds, customInjections, XMLPreProcessor.ITEMIZEDLIST_INJECTION_POINT, xmlDocument, XMLPreProcessor.CUSTOM_ALPHA_SORT_INJECTION_LIST_RE, new TopicTitleSorter(), database, docbookBuildingOptions, usedFixedUrls));
		errorTopics.addAll(processInjections(internal, translatedTopicData, topic, customInjectionIds, customInjections, XMLPreProcessor.LIST_INJECTION_POINT, xmlDocument, XMLPreProcessor.CUSTOM_INJECTION_LISTITEMS_RE, null, database, docbookBuildingOptions, usedFixedUrls));

		/*
		 * If we are not ignoring errors, return the list of topics that could
		 * not be injected
		 */
		if (errorTopics.size() != 0 && docbookBuildingOptions != null && !docbookBuildingOptions.getIgnoreMissingCustomInjections())
			return errorTopics;

		/* now make the custom injection point substitutions */
		for (final Node customInjectionCommentNode : customInjections.keySet())
		{
			final InjectionListData injectionListData = customInjections.get(customInjectionCommentNode);
			List<Element> list = null;

			/*
			 * this may not be true if we are not building all related topics
			 */
			if (injectionListData.listItems.size() != 0)
			{
				if (injectionListData.listType == XMLPreProcessor.ORDEREDLIST_INJECTION_POINT)
				{
					list = DocbookUtils.wrapOrderedListItemsInPara(xmlDocument, injectionListData.listItems);
				}
				else if (injectionListData.listType == XMLPreProcessor.XREF_INJECTION_POINT)
				{
					list = injectionListData.listItems.get(0);
				}
				else if (injectionListData.listType == XMLPreProcessor.ITEMIZEDLIST_INJECTION_POINT)
				{
					list = DocbookUtils.wrapItemizedListItemsInPara(xmlDocument, injectionListData.listItems);
				}
				else if (injectionListData.listType == XMLPreProcessor.LIST_INJECTION_POINT)
				{
					list = DocbookUtils.wrapItemsInListItems(xmlDocument, injectionListData.listItems);
				}
			}

			if (list != null)
			{
				for (final Element element : list)
				{
					customInjectionCommentNode.getParentNode().insertBefore(element, customInjectionCommentNode);
				}

				customInjectionCommentNode.getParentNode().removeChild(customInjectionCommentNode);
			}
		}

		return errorTopics;
	}

	public List<Integer> processInjections(final boolean internal, final TranslatedTopicDataV1 translatedTopicData, final TopicV1 topic, final ArrayList<Integer> customInjectionIds, final HashMap<Node, InjectionListData> customInjections, final int injectionPointType, final Document xmlDocument, final String regularExpression,
			final ExternalListSort<Integer, TopicV1, InjectionTopicData> sortComparator, final TocTopicDatabase database, final DocbookBuildingOptions docbookBuildingOptions, final boolean usedFixedUrls)
	{
		final List<Integer> retValue = new ArrayList<Integer>();

		if (xmlDocument == null)
			return retValue;

		/* loop over all of the comments in the document */
		for (final Node comment : XMLUtilities.getComments(xmlDocument))
		{
			final String commentContent = comment.getNodeValue();

			/* compile the regular expression */
			final Pattern injectionSequencePattern = Pattern.compile(regularExpression);
			/* find any matches */
			final Matcher injectionSequencematcher = injectionSequencePattern.matcher(commentContent);

			/* loop over the regular expression matches */
			while (injectionSequencematcher.find())
			{
				/*
				 * get the list of topics from the named group in the regular
				 * expression match
				 */
				final String reMatch = injectionSequencematcher.group(XMLPreProcessor.TOPICIDS_RE_NAMED_GROUP);

				/* make sure we actually found a matching named group */
				if (reMatch != null)
				{
					/* get the sequence of ids */
					final List<InjectionTopicData> sequenceIDs = processTopicIdList(reMatch);

					/*
					 * get the outgoing relationships
					 */
					final List<TopicV1> relatedTopics = (topic.getOutgoingRelationships() == null || topic.getOutgoingRelationships().getItems() == null) ? new ArrayList<TopicV1>() : topic.getOutgoingRelationships().getItems();
					
					/*
					 * Create a TocTopicDatabase to hold the related topics. The
					 * TocTopicDatabase provides a convenient way to access
					 * these topics
					 */
					TocTopicDatabase relatedTopicsDatabase = new TocTopicDatabase();
					relatedTopicsDatabase.setTopics(relatedTopics);

					/* sort the InjectionTopicData list if required */
					if (sortComparator != null)
					{
						sortComparator.sort(relatedTopics, sequenceIDs);
					}

					/* loop over all the topic ids in the injection point */
					for (final InjectionTopicData sequenceID : sequenceIDs)
					{
						/*
						 * topics that are injected into custom injection points
						 * are excluded from the generic related topic lists at
						 * the beginning and end of a topic. adding the topic id
						 * here means that when it comes time to generate the
						 * generic related topic lists, we can skip this topic
						 */
						customInjectionIds.add(sequenceID.topicId);

						/*
						 * Pull the topic out of the list of related topics
						 */
						final TopicV1 relatedTopic = relatedTopicsDatabase.getTopic(sequenceID.topicId);
						
						/*
						 * See if the topic is also available in the main
						 * database (if the main database is available)
						 */
						final boolean isInDatabase = database == null ? true : database.getTopic(sequenceID.topicId) != null;

						/*
						 * It is possible that the topic id referenced in the
						 * injection point has not been related, or has not been
						 * included in the list of topics to process. This is a
						 * validity error
						 */
						if (relatedTopic != null && isInDatabase)
						{
							/*
							 * build our list
							 */
							List<List<Element>> list = new ArrayList<List<Element>>();

							/*
							 * each related topic is added to a string, which is
							 * stored in the customInjections collection. the
							 * customInjections key is the custom injection text
							 * from the source xml. this allows us to match the
							 * xrefs we are generating for the related topic
							 * with the text in the xml file that these xrefs
							 * will eventually replace
							 */
							if (customInjections.containsKey(comment))
								list = customInjections.get(comment).listItems;

							/* wrap the xref up in a listitem */
							if (sequenceID.optional)
							{
								if (internal)
								{
									final TranslatedTopicDataV1 relatedTranslatedTopicData = translatedTopicData.getLatestRelatedTranslationDataByTopicID(relatedTopic.getId());
									
									if (relatedTranslatedTopicData != null)
									{
										final String url = getURLToInternalTranslatedTopic(relatedTranslatedTopicData.getTranslatedTopic().getId(), relatedTranslatedTopicData.getTranslationLocale());
										
										/* Get the related translated data object and the title from the XML */
										final String relatedTitle = DocBookUtilities.findTitle(relatedTranslatedTopicData.getTranslatedXml());
										
										list.add(DocbookUtils.buildEmphasisPrefixedULink(xmlDocument, XMLPreProcessor.OPTIONAL_LIST_PREFIX, url, relatedTitle));
									}
									else
									{
										final String url = getURLToInternalTopic(relatedTopic.getId());
										
										final String relatedTopicTitle = "[" + relatedTopic.getLocale() + "] " + relatedTopic.getTitle() + "*";
										
										list.add(DocbookUtils.buildEmphasisPrefixedULink(xmlDocument, XMLPreProcessor.OPTIONAL_LIST_PREFIX, url, relatedTopicTitle));
										
										/* Create an error note that is added at the bottom of the topic */
									}
								}
								else
								{
									if (usedFixedUrls)
									{
										final PropertyTagV1 propTag = relatedTopic.getProperty(CommonConstants.FIXED_URL_PROP_TAG_ID);
										if (propTag != null)
										{
											list.add(DocbookUtils.buildEmphasisPrefixedXRef(xmlDocument, XMLPreProcessor.OPTIONAL_LIST_PREFIX, propTag.getValue()));
										}
										else
										{
											list.add(DocbookUtils.buildEmphasisPrefixedXRef(xmlDocument, XMLPreProcessor.OPTIONAL_LIST_PREFIX, relatedTopic.getXRefID()));
										}
									}
									else
									{
										list.add(DocbookUtils.buildEmphasisPrefixedXRef(xmlDocument, XMLPreProcessor.OPTIONAL_LIST_PREFIX, relatedTopic.getXRefID()));
									}
								}
							}
							else
							{
								if (internal)
								{
									final TranslatedTopicDataV1 relatedTranslatedTopicData = translatedTopicData.getLatestRelatedTranslationDataByTopicID(relatedTopic.getId());
									
									if (relatedTranslatedTopicData != null)
									{
										final String url = getURLToInternalTranslatedTopic(relatedTranslatedTopicData.getTranslatedTopic().getId(), relatedTranslatedTopicData.getTranslationLocale());
										
										/* Get the related translated data object and the title from the XML */
										final String relatedTitle = DocBookUtilities.findTitle(relatedTranslatedTopicData.getTranslatedXml());
										
										list.add(DocbookUtils.buildULink(xmlDocument, url, relatedTitle));
									}
									else
									{
										final String url = getURLToInternalTopic(relatedTopic.getId());
										
										final String relatedTopicTitle = "[" + relatedTopic.getLocale() + "] " + relatedTopic.getTitle() + "*";
										
										list.add(DocbookUtils.buildULink(xmlDocument, url, relatedTopicTitle));
										
										translatedTopicTitleErrors.addTitle(relatedTopic.getTitle());
									}
								}
								else
								{
									if (usedFixedUrls)
									{
										final PropertyTagV1 propTag = relatedTopic.getProperty(CommonConstants.FIXED_URL_PROP_TAG_ID);
										if (propTag != null)
										{
											list.add(DocbookUtils.buildXRef(xmlDocument, propTag.getValue()));
										}
										else
										{
											list.add(DocbookUtils.buildXRef(xmlDocument, relatedTopic.getXRefID()));
										}
									}
									else
									{
										list.add(DocbookUtils.buildXRef(xmlDocument, relatedTopic.getXRefID()));
									}
								}
							}

							/*
							 * save the changes back into the customInjections
							 * collection
							 */
							customInjections.put(comment, new InjectionListData(list, injectionPointType));
						}
						else
						{
							retValue.add(sequenceID.topicId);
						}
					}
				}
			}
		}

		return retValue;
	}

	public List<Integer> processGenericInjections(final boolean internal,final TranslatedTopicDataV1 translatedTopicData, final TopicV1 topic, final Document xmlDocument, final ArrayList<Integer> customInjectionIds, final List<Pair<Integer, String>> topicTypeTagIDs, final TocTopicDatabase database, final DocbookBuildingOptions docbookBuildingOptions,
			final boolean usedFixedUrls)
	{
		final List<Integer> errors = new ArrayList<Integer>();

		if (xmlDocument == null)
			return errors;

		/*
		 * this collection will hold the lists of related topics
		 */
		final GenericInjectionPointDatabase relatedLists = new GenericInjectionPointDatabase();

		/* wrap each related topic in a listitem tag */
		if (topic.getOutgoingRelationships() != null && topic.getOutgoingRelationships().getItems() != null)
		{
			for (final TopicV1 relatedTopic : topic.getOutgoingRelationships().getItems())
			{
				/* make sure the topic is available to be linked to */
				if (database != null && database.getTopic(relatedTopic.getId()) == null)
				{
					if ((docbookBuildingOptions != null && !docbookBuildingOptions.getIgnoreMissingCustomInjections()))
						errors.add(relatedTopic.getId());
				}
				else
				{
					/*
					 * don't process those topics that were injected into custom
					 * injection points
					 */
					if (!customInjectionIds.contains(relatedTopic.getId()))
					{
						// loop through the topic type tags
						for (final Pair<Integer, String> primaryTopicTypeTag : topicTypeTagIDs)
						{
							/*
							 * see if we have processed a related topic with one
							 * of the topic type tags this may never be true if
							 * not processing all related topics
							 */
							if (relatedTopic.isTaggedWith(primaryTopicTypeTag.getFirst()))
							{
								relatedLists.addInjectionTopic(primaryTopicTypeTag, relatedTopic);

								break;
							}
						}
					}
				}
			}
		}

		insertGenericInjectionLinks(internal, translatedTopicData, topic, xmlDocument, relatedLists, database, docbookBuildingOptions, usedFixedUrls);

		return errors;
	}

	/**
	 * The generic injection points are placed in well defined locations within
	 * a topics xml structure. This function takes the list of related topics
	 * and the topic type tags that are associated with them and injects them
	 * into the xml document.
	 */
	private void insertGenericInjectionLinks(final boolean internal, final TranslatedTopicDataV1 translatedTopicData, final TopicV1 topic, final Document xmlDoc, final GenericInjectionPointDatabase relatedLists, final TocTopicDatabase database, final DocbookBuildingOptions docbookBuildingOptions, final boolean usedFixedUrls)
	{
		/* all related topics are placed before the first simplesect */
		final NodeList nodes = xmlDoc.getDocumentElement().getChildNodes();
		Node simplesectNode = null;
		for (int i = 0; i < nodes.getLength(); ++i)
		{
			final Node node = nodes.item(i);
			if (node.getNodeType() == 1 && node.getNodeName().equals("simplesect"))
			{
				simplesectNode = node;
				break;
			}
		}

		/*
		 * place the topics at the end of the topic. They will appear in the
		 * reverse order as the call to toArrayList()
		 */
		for (final Integer topTag : CollectionUtilities.toArrayList(DocbookBuilderConstants.REFERENCE_TAG_ID, DocbookBuilderConstants.TASK_TAG_ID, DocbookBuilderConstants.CONCEPT_TAG_ID, DocbookBuilderConstants.CONCEPTUALOVERVIEW_TAG_ID))
		{	
			for (final GenericInjectionPoint genericInjectionPoint : relatedLists.getInjectionPoints())
			{
				if (genericInjectionPoint.getCategoryIDAndName().getFirst() == topTag)
				{
					final List<TopicV1> relatedTopics = genericInjectionPoint.getTopics();

					/* don't add an empty list */
					if (relatedTopics.size() != 0)
					{
						final Node itemizedlist = DocbookUtils.createRelatedTopicItemizedList(xmlDoc, "Related " + genericInjectionPoint.getCategoryIDAndName().getSecond() + "s");

						Collections.sort(relatedTopics, new TopicV1TitleComparator());

						for (final TopicV1 relatedTopic : relatedTopics)
						{
							if (internal)
							{
								/* Try and find the related translation data to inject */
								final TranslatedTopicDataV1 relatedTranslatedTopicData = translatedTopicData.getLatestRelatedTranslationDataByTopicID(relatedTopic.getId());
								
								/*
								 * If there is related translation data found then add it normally.
								 * If no data is found then use the english title and make a note
								 * at the bottom of the topic XML.
								 */
								if (relatedTranslatedTopicData != null)
								{
									final String relatedTopicTitle = DocBookUtilities.findTitle(relatedTranslatedTopicData.getTranslatedXml());
									
									final String url = getURLToInternalTranslatedTopic(relatedTranslatedTopicData.getTranslatedTopic().getId(), relatedTranslatedTopicData.getTranslationLocale());
								
									DocbookUtils.createRelatedTopicULink(xmlDoc, url, relatedTopicTitle, itemizedlist);
								}
								else
								{
									final String relatedTopicTitle = "[" + relatedTopic.getLocale() + "] " + relatedTopic.getTitle() + "*";
									
									DocbookUtils.createRelatedTopicULink(xmlDoc, getURLToInternalTopic(relatedTopic.getId()), relatedTopicTitle, itemizedlist);
									
									translatedTopicTitleErrors.addTitle(relatedTopic.getTitle());
								}
							}
							else
							{
								if (usedFixedUrls)
								{
									final PropertyTagV1 propTag = relatedTopic.getProperty(CommonConstants.FIXED_URL_PROP_TAG_ID);
									if (propTag != null)
									{
										DocbookUtils.createRelatedTopicXRef(xmlDoc, propTag.getValue(), itemizedlist);
									}
									else
									{
										DocbookUtils.createRelatedTopicXRef(xmlDoc, relatedTopic.getXRefID(), itemizedlist);
									}
								}
								else
								{
									DocbookUtils.createRelatedTopicXRef(xmlDoc, relatedTopic.getXRefID(), itemizedlist);
								}
							}

						}

						if (simplesectNode != null)
							xmlDoc.getDocumentElement().insertBefore(itemizedlist, simplesectNode);
						else
							xmlDoc.getDocumentElement().appendChild(itemizedlist);
					}
				}
			}
		}
	}

	private String getURLToInternalTranslatedTopic(final Integer translatedTopicId, final String locale)
	{
		return "TranslatedTopic.seam?translatedTopicId=" + translatedTopicId + "&locale=" + locale + "&selectedTab=Rendered+View";
	}
	
	private String getURLToInternalTopic(final Integer topicId)
	{
		return "Topic.seam?topicTopicId=" + topicId + "&selectedTab=Rendered+View";
	}

	public List<Integer> processTopicContentFragments(final TranslatedTopicDataV1 translatedTopicData, final TopicV1 topic, final Document xmlDocument, final DocbookBuildingOptions docbookBuildingOptions)
	{
		final List<Integer> retValue = new ArrayList<Integer>();

		if (xmlDocument == null)
			return retValue;

		final Map<Node, ArrayList<Node>> replacements = new HashMap<Node, ArrayList<Node>>();

		/* loop over all of the comments in the document */
		for (final Node comment : XMLUtilities.getComments(xmlDocument))
		{
			final String commentContent = comment.getNodeValue();

			/* compile the regular expression */
			final Pattern injectionSequencePattern = Pattern.compile(XMLPreProcessor.INJECT_CONTENT_FRAGMENT_RE);
			/* find any matches */
			final Matcher injectionSequencematcher = injectionSequencePattern.matcher(commentContent);

			/* loop over the regular expression matches */
			while (injectionSequencematcher.find())
			{
				/*
				 * get the list of topics from the named group in the regular
				 * expression match
				 */
				final String reMatch = injectionSequencematcher.group(XMLPreProcessor.TOPICIDS_RE_NAMED_GROUP);

				/* make sure we actually found a matching named group */
				if (reMatch != null)
				{
					try
					{
						if (!replacements.containsKey(comment))
							replacements.put(comment, new ArrayList<Node>());

						final Integer topicID = Integer.parseInt(reMatch);

						/*
						 * make sure the topic we are trying to inject has been
						 * related
						 */
						if (topic.isRelatedTo(topicID))
						{
							final TranslatedTopicDataV1 relatedTranslatedTopicData = translatedTopicData.getLatestRelatedTranslationDataByTopicID(topicID);
							final Document relatedTopicXML = XMLUtilities.convertStringToDocument(relatedTranslatedTopicData.getTranslatedXml());
							if (relatedTopicXML != null)
							{
								final Node relatedTopicDocumentElement = relatedTopicXML.getDocumentElement();
								final Node importedXML = xmlDocument.importNode(relatedTopicDocumentElement, true);

								/* ignore the section title */
								final NodeList sectionChildren = importedXML.getChildNodes();
								for (int i = 0; i < sectionChildren.getLength(); ++i)
								{
									final Node node = sectionChildren.item(i);
									if (node.getNodeName().equals("title"))
									{
										importedXML.removeChild(node);
										break;
									}
								}

								/* remove all with a role="noinject" attribute */
								XMLPreProcessor.removeNoInjectElements(importedXML);

								/*
								 * importedXML is a now section with no title,
								 * and no child elements with the noinject value
								 * on the role attribute. We now add its
								 * children to the Array in the replacements
								 * Map.
								 */

								final NodeList remainingChildren = importedXML.getChildNodes();
								for (int i = 0; i < remainingChildren.getLength(); ++i)
								{
									final Node child = remainingChildren.item(i);
									replacements.get(comment).add(child);
								}
							}
						}
						else if (docbookBuildingOptions != null && !docbookBuildingOptions.getIgnoreMissingCustomInjections())
						{
							retValue.add(Integer.parseInt(reMatch));
						}
					}
					catch (final Exception ex)
					{
						ExceptionUtilities.handleException(ex);
					}
				}
			}
		}

		/*
		 * The replacements map now has a keyset of the comments mapped to a
		 * collection of nodes that the comment will be replaced with
		 */

		for (final Node comment : replacements.keySet())
		{
			final ArrayList<Node> replacementNodes = replacements.get(comment);
			for (final Node replacementNode : replacementNodes)
				comment.getParentNode().insertBefore(replacementNode, comment);
			comment.getParentNode().removeChild(comment);
		}

		return retValue;
	}

	public List<Integer> processTopicTitleFragments(final TranslatedTopicDataV1 translatedTopicData, final TopicV1 topic, final Document xmlDocument, final DocbookBuildingOptions docbookBuildingOptions)
	{
		final List<Integer> retValue = new ArrayList<Integer>();

		if (xmlDocument == null)
			return retValue;

		final Map<Node, Node> replacements = new HashMap<Node, Node>();

		/* loop over all of the comments in the document */
		for (final Node comment : XMLUtilities.getComments(xmlDocument))
		{
			final String commentContent = comment.getNodeValue();

			/* compile the regular expression */
			final Pattern injectionSequencePattern = Pattern.compile(XMLPreProcessor.INJECT_TITLE_FRAGMENT_RE);
			/* find any matches */
			final Matcher injectionSequencematcher = injectionSequencePattern.matcher(commentContent);

			/* loop over the regular expression matches */
			while (injectionSequencematcher.find())
			{
				/*
				 * get the list of topics from the named group in the regular
				 * expression match
				 */
				final String reMatch = injectionSequencematcher.group(XMLPreProcessor.TOPICIDS_RE_NAMED_GROUP);

				/* make sure we actually found a matching named group */
				if (reMatch != null)
				{
					try
					{
						if (!replacements.containsKey(comment))
							replacements.put(comment, null);

						final Integer topicID = Integer.parseInt(reMatch);

						/*
						 * make sure the topic we are trying to inject has been
						 * related
						 */
						if (topic.isRelatedTo(topicID))
						{
							final TranslatedTopicDataV1 relatedTranslatedTopicData = translatedTopicData.getLatestRelatedTranslationDataByTopicID(topicID);
							final Element titleNode = xmlDocument.createElement("title");
							if (relatedTranslatedTopicData.getTranslatedTopicTitle() != null)
							{
								titleNode.setTextContent(relatedTranslatedTopicData.getTranslatedTopicTitle());
							}
							else
							{
								final TopicV1 relatedTopic = topic.getRelatedTopicByID(topicID);
								titleNode.setTextContent(relatedTopic.getTitle());
								
								translatedTopicTitleErrors.addTitle(relatedTopic.getTitle());
							}
							replacements.put(comment, titleNode);
						}
						else if (docbookBuildingOptions != null && !docbookBuildingOptions.getIgnoreMissingCustomInjections())
						{
							retValue.add(Integer.parseInt(reMatch));
						}
					}
					catch (final Exception ex)
					{
						ExceptionUtilities.handleException(ex);
					}
				}
			}
		}

		/* swap the comment nodes with the new title nodes */
		for (final Node comment : replacements.keySet())
		{
			final Node title = replacements.get(comment);
			comment.getParentNode().insertBefore(title, comment);
			comment.getParentNode().removeChild(comment);
		}

		return retValue;
	}
	
	/**
	 * Add the list of Translated Topics who referenced 
	 * another topic that hasn't been translated.
	 */
	public void processTitleErrors(final Document xmlDoc)
	{
		/* Check that there are errors */
		if (translatedTopicTitleErrors.getErrorTitles().isEmpty()) return;
		
		/* Create the itemized list to hold the translations */
		final Element errorSection = xmlDoc.createElement("itemizedlist");
		errorSection.setAttribute("mark", "asterisk");
		xmlDoc.getDocumentElement().appendChild(errorSection);
		
		/* Create the title for the list */
		final Element errorSectionTitle = xmlDoc.createElement("title");
		errorSectionTitle.setTextContent("The following links in this topic reference untranslated resources:");
		errorSection.appendChild(errorSectionTitle);
		
		/* Add all of the topic titles that had errors to the list */
		for (String title: translatedTopicTitleErrors.getErrorTitles())
		{
			Element errorListItem = xmlDoc.createElement("listitem");
			Element errorTitlePara = xmlDoc.createElement("para");
			errorTitlePara.setTextContent("* " + title);
			errorListItem.appendChild(errorTitlePara);
			errorSection.appendChild(errorListItem);
		}
	}

}
