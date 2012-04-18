package com.redhat.ecs.services.docbookcompiling.xmlprocessing;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

import com.redhat.ecs.commonstructures.Pair;
import com.redhat.ecs.commonutils.CollectionUtilities;
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
import com.redhat.ecs.sort.ExternalListSort;

import com.redhat.topicindex.component.docbookrenderer.structures.tocformat.TocFormatBranch;
import com.redhat.topicindex.rest.entities.PropertyTagV1;
import com.redhat.topicindex.rest.entities.TagV1;
import com.redhat.topicindex.rest.entities.TopicV1;
import com.redhat.topicindex.rest.sort.TopicTitleSorter;
import com.redhat.topicindex.rest.sort.TopicV1TitleComparator;

/**
 * This class takes the XML from a topic and modifies it to include and injected
 * content.
 */
public class XMLPreProcessor
{
	/**
	 * Used to identify that an <orderedlist> should be generated for the
	 * injection point
	 */
	protected static final int ORDEREDLIST_INJECTION_POINT = 1;
	/**
	 * Used to identify that an <itemizedlist> should be generated for the
	 * injection point
	 */
	protected static final int ITEMIZEDLIST_INJECTION_POINT = 2;
	/**
	 * Used to identify that an <xref> should be generated for the injection
	 * point
	 */
	protected static final int XREF_INJECTION_POINT = 3;
	/**
	 * Used to identify that an <xref> should be generated for the injection
	 * point
	 */
	protected static final int LIST_INJECTION_POINT = 4;
	/** Identifies a named regular expression group */
	protected static final String TOPICIDS_RE_NAMED_GROUP = "TopicIDs";
	/** This text identifies an option task in a list */
	protected static final String OPTIONAL_MARKER = "OPT:";
	/** The text to be prefixed to a list item if a topic is optional */
	protected static final String OPTIONAL_LIST_PREFIX = "Optional: ";
	/** A regular expression that identifies a topic id */
	protected static final String OPTIONAL_TOPIC_ID_RE = "(" + OPTIONAL_MARKER + "\\s*)?\\d+";
	/** A regular expression that identifies a topic id */
	protected static final String TOPIC_ID_RE = "\\d+";

	/**
	 * A regular expression that matches an InjectSequence custom injection
	 * point
	 */
	protected static final String CUSTOM_INJECTION_SEQUENCE_RE =
	/*
	 * start xml comment and 'InjectSequence:' surrounded by optional white
	 * space
	 */
	"\\s*InjectSequence:\\s*" +
	/*
	 * an optional comma separated list of digit blocks, and at least one digit
	 * block with an optional comma
	 */
	"(?<" + TOPICIDS_RE_NAMED_GROUP + ">(\\s*" + OPTIONAL_TOPIC_ID_RE + "\\s*,)*(\\s*" + OPTIONAL_TOPIC_ID_RE + ",?))" +
	/* xml comment end */
	"\\s*";

	/** A regular expression that matches an InjectList custom injection point */
	protected static final String CUSTOM_INJECTION_LIST_RE =
	/* start xml comment and 'InjectList:' surrounded by optional white space */
	"\\s*InjectList:\\s*" +
	/*
	 * an optional comma separated list of digit blocks, and at least one digit
	 * block with an optional comma
	 */
	"(?<" + TOPICIDS_RE_NAMED_GROUP + ">(\\s*" + OPTIONAL_TOPIC_ID_RE + "\\s*,)*(\\s*" + OPTIONAL_TOPIC_ID_RE + ",?))" +
	/* xml comment end */
	"\\s*";

	protected static final String CUSTOM_INJECTION_LISTITEMS_RE =
	/* start xml comment and 'InjectList:' surrounded by optional white space */
	"\\s*InjectListItems:\\s*" +
	/*
	 * an optional comma separated list of digit blocks, and at least one digit
	 * block with an optional comma
	 */
	"(?<" + TOPICIDS_RE_NAMED_GROUP + ">(\\s*" + OPTIONAL_TOPIC_ID_RE + "\\s*,)*(\\s*" + OPTIONAL_TOPIC_ID_RE + ",?))" +
	/* xml comment end */
	"\\s*";

	protected static final String CUSTOM_ALPHA_SORT_INJECTION_LIST_RE =
	/*
	 * start xml comment and 'InjectListAlphaSort:' surrounded by optional white
	 * space
	 */
	"\\s*InjectListAlphaSort:\\s*" +
	/*
	 * an optional comma separated list of digit blocks, and at least one digit
	 * block with an optional comma
	 */
	"(?<" + TOPICIDS_RE_NAMED_GROUP + ">(\\s*" + OPTIONAL_TOPIC_ID_RE + "\\s*,)*(\\s*" + OPTIONAL_TOPIC_ID_RE + ",?))" +
	/* xml comment end */
	"\\s*";

	/** A regular expression that matches an Inject custom injection point */
	protected static final String CUSTOM_INJECTION_SINGLE_RE =
	/* start xml comment and 'Inject:' surrounded by optional white space */
	"\\s*Inject:\\s*" +
	/* one digit block */
	"(?<" + TOPICIDS_RE_NAMED_GROUP + ">(" + OPTIONAL_TOPIC_ID_RE + "))" +
	/* xml comment end */
	"\\s*";

	/** A regular expression that matches an Inject Content Fragment */
	protected static final String INJECT_CONTENT_FRAGMENT_RE =
	/* start xml comment and 'Inject:' surrounded by optional white space */
	"\\s*InjectText:\\s*" +
	/* one digit block */
	"(?<" + TOPICIDS_RE_NAMED_GROUP + ">(" + TOPIC_ID_RE + "))" +
	/* xml comment end */
	"\\s*";

	/** A regular expression that matches an Inject Content Fragment */
	protected static final String INJECT_TITLE_FRAGMENT_RE =
	/* start xml comment and 'Inject:' surrounded by optional white space */
	"\\s*InjectTitle:\\s*" +
	/* one digit block */
	"(?<" + TOPICIDS_RE_NAMED_GROUP + ">(" + TOPIC_ID_RE + "))" +
	/* xml comment end */
	"\\s*";

	/**
	 * The noinject value for the role attribute indicates that an element
	 * should not be included in the Topic Fragment
	 */
	protected static final String NO_INJECT_ROLE = "noinject";

	static public void processTopicBugzillaLink(final TopicV1 topic, final Document document, final DocbookBuildingOptions docbookBuildingOptions, final String buildName, final String searchTagsUrl)
	{
		/* SIMPLESECT TO HOLD OTHER LINKS */
		final Element bugzillaSection = document.createElement("simplesect");
		document.getDocumentElement().appendChild(bugzillaSection);

		final Element bugzillaSectionTitle = document.createElement("title");
		bugzillaSectionTitle.setTextContent("");
		bugzillaSection.appendChild(bugzillaSectionTitle);

		/* BUGZILLA LINK */
		try
		{
			final String instanceNameProperty = System.getProperty(CommonConstants.INSTANCE_NAME_PROPERTY);
			final String fixedInstanceNameProperty = instanceNameProperty == null ? "Not Defined" : instanceNameProperty;

			final Element bugzillaPara = document.createElement("para");
			bugzillaPara.setAttribute("role", DocbookBuilderConstants.ROLE_CREATE_BUG_PARA);

			final Element bugzillaULink = document.createElement("ulink");

			bugzillaULink.setTextContent("Report a bug");
			
			String specifiedBuildName = "";
			if (docbookBuildingOptions != null && docbookBuildingOptions.getBuildName() != null)
				specifiedBuildName = docbookBuildingOptions.getBuildName();

			/* build up the elements that go into the bugzilla URL */
			String bugzillaProduct = null;
			String bugzillaComponent = null;
			String bugzillaVersion = null;
			String bugzillaKeywords = null;
			String bugzillaAssignedTo = null;
			final String bugzillaEnvironment = URLEncoder.encode("Instance Name: " + fixedInstanceNameProperty + "\nSkynet Build: " + buildName + "\nBuild Filter: " + searchTagsUrl +"\nBuild Name: " + specifiedBuildName, "UTF-8");
			final String bugzillaBuildID = URLEncoder.encode(topic.getBugzillaBuildId(), "UTF-8");

			/* look for the bugzilla options */
			if (topic.getTags() != null && topic.getTags().getItems() != null)
			{
				for (final TagV1 tag : topic.getTags().getItems())
				{
					final PropertyTagV1 bugzillaProductTag = tag.getProperty(CommonConstants.BUGZILLA_PRODUCT_PROP_TAG_ID);
					final PropertyTagV1 bugzillaComponentTag = tag.getProperty(CommonConstants.BUGZILLA_COMPONENT_PROP_TAG_ID);
					final PropertyTagV1 bugzillaKeywordsTag = tag.getProperty(CommonConstants.BUGZILLA_KEYWORDS_PROP_TAG_ID);
					final PropertyTagV1 bugzillaVersionTag = tag.getProperty(CommonConstants.BUGZILLA_VERSION_PROP_TAG_ID);
					final PropertyTagV1 bugzillaAssignedToTag = tag.getProperty(CommonConstants.BUGZILLA_PROFILE_PROPERTY);

					if (bugzillaProduct == null && bugzillaProductTag != null)
						bugzillaProduct = URLEncoder.encode(bugzillaProductTag.getValue(), "UTF-8");

					if (bugzillaComponent == null && bugzillaComponentTag != null)
						bugzillaComponent = URLEncoder.encode(bugzillaComponentTag.getValue(), "UTF-8");

					if (bugzillaKeywords == null && bugzillaKeywordsTag != null)
						bugzillaKeywords = URLEncoder.encode(bugzillaKeywordsTag.getValue(), "UTF-8");

					if (bugzillaVersion == null && bugzillaVersionTag != null)
						bugzillaVersion = URLEncoder.encode(bugzillaVersionTag.getValue(), "UTF-8");

					if (bugzillaAssignedTo == null && bugzillaAssignedToTag != null)
						bugzillaAssignedTo = URLEncoder.encode(bugzillaAssignedToTag.getValue(), "UTF-8");
				}
			}
			
			

			/* build the bugzilla url options */
			String bugzillaURLComponents = "";
			
			/* we need at least a product*/
			if (bugzillaProduct != null)
			{
				bugzillaURLComponents += bugzillaURLComponents.isEmpty() ? "?" : "&amp;";
				bugzillaURLComponents += "product=" + bugzillaProduct;

				if (bugzillaComponent != null)
				{
					bugzillaURLComponents += bugzillaURLComponents.isEmpty() ? "?" : "&amp;";
					bugzillaURLComponents += "component=" + bugzillaComponent;
				}

				if (bugzillaVersion != null)
				{
					bugzillaURLComponents += bugzillaURLComponents.isEmpty() ? "?" : "&amp;";
					bugzillaURLComponents += "version=" + bugzillaVersion;
				}

				if (bugzillaKeywords != null)
				{
					bugzillaURLComponents += bugzillaURLComponents.isEmpty() ? "?" : "&amp;";
					bugzillaURLComponents += "keywords=" + bugzillaKeywords;
				}

				if (bugzillaAssignedTo != null)
				{
					bugzillaURLComponents += bugzillaURLComponents.isEmpty() ? "?" : "&amp;";
					bugzillaURLComponents += "assigned_to=" + bugzillaAssignedTo;
				}

				bugzillaURLComponents += bugzillaURLComponents.isEmpty() ? "?" : "&amp;";
				bugzillaURLComponents += "cf_environment=" + bugzillaEnvironment;

				bugzillaURLComponents += bugzillaURLComponents.isEmpty() ? "?" : "&amp;";
				bugzillaURLComponents += "cf_build_id=" + bugzillaBuildID;
			}

			/* build the bugzilla url with the base components */
			String bugZillaUrl = "https://bugzilla.redhat.com/enter_bug.cgi" + bugzillaURLComponents;

			bugzillaULink.setAttribute("url", bugZillaUrl);

			/*
			 * only add the elements to the XML DOM if there was no exception
			 * (not that there should be one
			 */
			bugzillaSection.appendChild(bugzillaPara);
			bugzillaPara.appendChild(bugzillaULink);
		}
		catch (final Exception ex)
		{
			ExceptionUtilities.handleException(ex);
		}
	}

	/**
	 * Adds some debug information and links to the end of the topic
	 */
	static public void processTopicAdditionalInfo(final TopicV1 topic, final Document document, final DocbookBuildingOptions docbookBuildingOptions, final String buildName, final String searchTagsUrl)
	{
		/* SIMPLESECT TO HOLD OTHER LINKS */
		final Element bugzillaSection = document.createElement("simplesect");
		document.getDocumentElement().appendChild(bugzillaSection);

		final Element bugzillaSectionTitle = document.createElement("title");
		bugzillaSectionTitle.setTextContent("");
		bugzillaSection.appendChild(bugzillaSectionTitle);

		processTopicBugzillaLink(topic, document, docbookBuildingOptions, buildName, searchTagsUrl);

		// SURVEY LINK
		if (docbookBuildingOptions != null && docbookBuildingOptions.getInsertSurveyLink())
		{
			final Element surveyPara = document.createElement("para");
			surveyPara.setAttribute("role", DocbookBuilderConstants.ROLE_CREATE_BUG_PARA);
			bugzillaSection.appendChild(surveyPara);

			final Text startSurveyText = document.createTextNode("Thank you for evaluating the new documentation format for JBoss Enterprise Application Platform. Let us know what you think by taking a short ");
			surveyPara.appendChild(startSurveyText);

			final Element surveyULink = document.createElement("ulink");
			surveyPara.appendChild(surveyULink);
			surveyULink.setTextContent("survey");
			surveyULink.setAttribute("url", "https://www.keysurvey.com/survey/380730/106f/");

			final Text endSurveyText = document.createTextNode(".");
			surveyPara.appendChild(endSurveyText);
		}

		/* searchTagsUrl will be null for internal (i.e. HTML rendering) builds */
		if (searchTagsUrl != null)
		{
			// VIEW IN SKYNET

			final Element skynetElement = document.createElement("remark");
			skynetElement.setAttribute("role", DocbookBuilderConstants.ROLE_VIEW_IN_SKYNET_PARA);
			bugzillaSection.appendChild(skynetElement);

			final Element skynetLinkULink = document.createElement("ulink");
			skynetElement.appendChild(skynetLinkULink);
			skynetLinkULink.setTextContent("View in Skynet");
			skynetLinkULink.setAttribute("url", topic.getTopicSkynetURL());

			// SKYNET VERSION

			final Element buildVersionElement = document.createElement("remark");
			buildVersionElement.setAttribute("role", DocbookBuilderConstants.ROLE_BUILD_VERSION_PARA);
			bugzillaSection.appendChild(buildVersionElement);

			final Element skynetVersionElementULink = document.createElement("ulink");
			buildVersionElement.appendChild(skynetVersionElementULink);
			skynetVersionElementULink.setTextContent("Built with " + buildName);
			skynetVersionElementULink.setAttribute("url", searchTagsUrl);
		}
	}

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
			final String topicId = topicIDs[i].replaceAll(OPTIONAL_MARKER, "").trim();
			final boolean optional = topicIDs[i].indexOf(OPTIONAL_MARKER) != -1;

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

	public static List<Integer> processInjections(final TocFormatBranch toc, final TopicV1 topic, final ArrayList<Integer> customInjectionIds, final Document xmlDocument, final DocbookBuildingOptions docbookBuildingOptions, final boolean usedFixedUrls)
	{
		/*
		 * this collection keeps a track of the injection point markers and the
		 * docbook lists that we will be replacing them with
		 */
		final HashMap<Node, InjectionListData> customInjections = new HashMap<Node, InjectionListData>();

		final List<Integer> errorTopics = new ArrayList<Integer>();

		errorTopics.addAll(processInjections(toc, topic, customInjectionIds, customInjections, ORDEREDLIST_INJECTION_POINT, xmlDocument, CUSTOM_INJECTION_SEQUENCE_RE, null, docbookBuildingOptions, usedFixedUrls));
		errorTopics.addAll(processInjections(toc, topic, customInjectionIds, customInjections, XREF_INJECTION_POINT, xmlDocument, CUSTOM_INJECTION_SINGLE_RE, null, docbookBuildingOptions, usedFixedUrls));
		errorTopics.addAll(processInjections(toc, topic, customInjectionIds, customInjections, ITEMIZEDLIST_INJECTION_POINT, xmlDocument, CUSTOM_INJECTION_LIST_RE, null, docbookBuildingOptions, usedFixedUrls));
		errorTopics.addAll(processInjections(toc, topic, customInjectionIds, customInjections, ITEMIZEDLIST_INJECTION_POINT, xmlDocument, CUSTOM_ALPHA_SORT_INJECTION_LIST_RE, new TopicTitleSorter(), docbookBuildingOptions, usedFixedUrls));
		errorTopics.addAll(processInjections(toc, topic, customInjectionIds, customInjections, LIST_INJECTION_POINT, xmlDocument, CUSTOM_INJECTION_LISTITEMS_RE, null, docbookBuildingOptions, usedFixedUrls));

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
				if (injectionListData.listType == ORDEREDLIST_INJECTION_POINT)
				{
					list = DocbookUtils.wrapOrderedListItemsInPara(xmlDocument, injectionListData.listItems);
				}
				else if (injectionListData.listType == XREF_INJECTION_POINT)
				{
					list = injectionListData.listItems.get(0);
				}
				else if (injectionListData.listType == ITEMIZEDLIST_INJECTION_POINT)
				{
					list = DocbookUtils.wrapItemizedListItemsInPara(xmlDocument, injectionListData.listItems);
				}
				else if (injectionListData.listType == LIST_INJECTION_POINT)
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

	public static List<Integer> processInjections(final TocFormatBranch toc, final TopicV1 topic, final ArrayList<Integer> customInjectionIds, final HashMap<Node, InjectionListData> customInjections, final int injectionPointType, final Document xmlDocument, final String regularExpression,
			final ExternalListSort<Integer, TopicV1, InjectionTopicData> sortComparator, final DocbookBuildingOptions docbookBuildingOptions, final boolean usedFixedUrls)
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
				final String reMatch = injectionSequencematcher.group(TOPICIDS_RE_NAMED_GROUP);

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
						final boolean isInDatabase = toc == null ? true : toc.isInToc(sequenceID.topicId);

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

							/* if the toc is null, we are building an internal page */
							if (toc == null)
							{
								final String url = getURLToInternalTopic(relatedTopic.getId());
								if (sequenceID.optional)
								{
									list.add(DocbookUtils.buildEmphasisPrefixedULink(xmlDocument, OPTIONAL_LIST_PREFIX, url, relatedTopic.getTitle()));
								}
								else
								{
									list.add(DocbookUtils.buildULink(xmlDocument, url, relatedTopic.getTitle()));
								}
							}
							else
							{
								final String xrefPostfix = toc.getClosestTopicXrefPostfix(relatedTopic.getId(), topic);
								if (xrefPostfix != null)
								{								
									if (sequenceID.optional)
									{
										if (usedFixedUrls)
										{
											list.add(DocbookUtils.buildEmphasisPrefixedXRef(xmlDocument, OPTIONAL_LIST_PREFIX, relatedTopic.getXrefPropertyOrId(CommonConstants.FIXED_URL_PROP_TAG_ID) + xrefPostfix));
										}
										else
										{
											list.add(DocbookUtils.buildEmphasisPrefixedXRef(xmlDocument, OPTIONAL_LIST_PREFIX, relatedTopic.getXRefID() + xrefPostfix));
										}
									}
									else
									{
										if (usedFixedUrls)
										{
											list.add(DocbookUtils.buildXRef(xmlDocument, relatedTopic.getXrefPropertyOrId(CommonConstants.FIXED_URL_PROP_TAG_ID) + xrefPostfix));										
										}
										else
										{
											list.add(DocbookUtils.buildXRef(xmlDocument, relatedTopic.getXRefID() + xrefPostfix));
										}
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

	public static List<Integer> processGenericInjections(final TocFormatBranch toc, final TopicV1 topic, final Document xmlDocument, final ArrayList<Integer> customInjectionIds, final List<Pair<Integer, String>> topicTypeTagIDs, final DocbookBuildingOptions docbookBuildingOptions,
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
				if (toc != null && !toc.isInToc(relatedTopic.getId()))
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

		insertGenericInjectionLinks(toc, topic, xmlDocument, relatedLists, docbookBuildingOptions, usedFixedUrls);

		return errors;
	}

	/**
	 * The generic injection points are placed in well defined locations within
	 * a topics xml structure. This function takes the list of related topics
	 * and the topic type tags that are associated with them and injects them
	 * into the xml document.
	 */
	private static void insertGenericInjectionLinks(final TocFormatBranch toc, final TopicV1 topic, final Document xmlDoc, final GenericInjectionPointDatabase relatedLists, final DocbookBuildingOptions docbookBuildingOptions, final boolean usedFixedUrls)
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
							if (toc == null)
							{
								DocbookUtils.createRelatedTopicULink(xmlDoc, getURLToInternalTopic(relatedTopic.getId()), relatedTopic.getTitle(), itemizedlist);
							}
							else
							{
								final String xrefPostfix = toc.getClosestTopicXrefPostfix(relatedTopic.getId(), topic);
								if (usedFixedUrls)
								{
									DocbookUtils.createRelatedTopicXRef(xmlDoc, relatedTopic.getXrefPropertyOrId(CommonConstants.FIXED_URL_PROP_TAG_ID) + xrefPostfix, itemizedlist);
									
								}
								else
								{
									DocbookUtils.createRelatedTopicXRef(xmlDoc, relatedTopic.getXRefID() + xrefPostfix, itemizedlist);
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

	private static String getURLToInternalTopic(final Integer topicId)
	{
		return "Topic.seam?topicTopicId=" + topicId + "&selectedTab=Rendered+View";
	}

	public static void processInternalImageFiles(final Document xmlDoc)
	{
		if (xmlDoc == null)
			return;

		final List<Node> imageDataNodes = XMLUtilities.getNodes(xmlDoc.getDocumentElement(), "imagedata");
		for (final Node imageDataNode : imageDataNodes)
		{
			final NamedNodeMap attributes = imageDataNode.getAttributes();
			final Node filerefAttribute = attributes.getNamedItem("fileref");
			if (filerefAttribute != null)
			{
				String imageId = filerefAttribute.getTextContent();
				imageId = imageId.replace("images/", "");
				final int periodIndex = imageId.lastIndexOf(".");
				if (periodIndex != -1)
					imageId = imageId.substring(0, periodIndex);

				/*
				 * at this point imageId should be an integer that is the id of
				 * the image uploaded in skynet. We will leave the validation of
				 * imageId to the ImageFileDisplay class.
				 */

				filerefAttribute.setTextContent("ImageFileDisplay.seam?imageFileId=" + imageId);
			}
		}
	}

	public static List<Integer> processTopicContentFragments(final TopicV1 topic, final Document xmlDocument, final DocbookBuildingOptions docbookBuildingOptions)
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
			final Pattern injectionSequencePattern = Pattern.compile(INJECT_CONTENT_FRAGMENT_RE);
			/* find any matches */
			final Matcher injectionSequencematcher = injectionSequencePattern.matcher(commentContent);

			/* loop over the regular expression matches */
			while (injectionSequencematcher.find())
			{
				/*
				 * get the list of topics from the named group in the regular
				 * expression match
				 */
				final String reMatch = injectionSequencematcher.group(TOPICIDS_RE_NAMED_GROUP);

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
							final TopicV1 relatedTopic = topic.getRelatedTopicByID(topicID);
							final Document relatedTopicXML = XMLUtilities.convertStringToDocument(relatedTopic.getXml());
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
								removeNoInjectElements(importedXML);

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

	protected static void removeNoInjectElements(final Node parent)
	{
		final NodeList childrenNodes = parent.getChildNodes();
		final ArrayList<Node> removeNodes = new ArrayList<Node>();

		for (int i = 0; i < childrenNodes.getLength(); ++i)
		{
			final Node node = childrenNodes.item(i);
			final NamedNodeMap attributes = node.getAttributes();
			if (attributes != null)
			{
				final Node roleAttribute = attributes.getNamedItem("role");
				if (roleAttribute != null)
				{
					final String[] roles = roleAttribute.getTextContent().split(",");
					for (final String role : roles)
					{
						if (role.equals(NO_INJECT_ROLE))
						{
							removeNodes.add(node);
							break;
						}
					}
				}
			}
		}

		for (final Node removeNode : removeNodes)
			parent.removeChild(removeNode);

		final NodeList remainingChildrenNodes = parent.getChildNodes();

		for (int i = 0; i < remainingChildrenNodes.getLength(); ++i)
		{
			final Node child = remainingChildrenNodes.item(i);
			removeNoInjectElements(child);
		}
	}

	public static List<Integer> processTopicTitleFragments(final TopicV1 topic, final Document xmlDocument, final DocbookBuildingOptions docbookBuildingOptions)
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
			final Pattern injectionSequencePattern = Pattern.compile(INJECT_TITLE_FRAGMENT_RE);
			/* find any matches */
			final Matcher injectionSequencematcher = injectionSequencePattern.matcher(commentContent);

			/* loop over the regular expression matches */
			while (injectionSequencematcher.find())
			{
				/*
				 * get the list of topics from the named group in the regular
				 * expression match
				 */
				final String reMatch = injectionSequencematcher.group(TOPICIDS_RE_NAMED_GROUP);

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
							final TopicV1 relatedTopic = topic.getRelatedTopicByID(topicID);
							final Element titleNode = xmlDocument.createElement("title");
							titleNode.setTextContent(relatedTopic.getTitle());
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

	public static String processDocumentType(final String xml)
	{
		assert xml != null : "The xml parameter can not be null";

		if (XMLUtilities.findDocumentType(xml) == null)
		{
			final String preamble = XMLUtilities.findPreamble(xml);
			final String fixedPreamble = preamble == null ? "" : preamble + "\n";
			final String fixedXML = preamble == null ? xml : xml.replace(preamble, "");

			return fixedPreamble + "<!DOCTYPE section PUBLIC \"-//OASIS//DTD DocBook XML V4.5//EN\" \"http://www.oasis-open.org/docbook/xml/4.5/docbookx.dtd\" []>\n" + fixedXML;
		}

		return xml;
	}

}
