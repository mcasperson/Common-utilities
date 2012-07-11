package com.redhat.contentspec.utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;

import com.redhat.contentspec.Chapter;
import com.redhat.contentspec.ContentSpec;
import com.redhat.contentspec.Level;
import com.redhat.contentspec.Section;
import com.redhat.contentspec.SpecTopic;
import com.redhat.contentspec.constants.CSConstants;
import com.redhat.ecs.commonutils.CollectionUtilities;
import com.redhat.ecs.commonutils.ExceptionUtilities;
import com.redhat.ecs.services.docbookcompiling.DocbookBuilderConstants;
import com.redhat.ecs.services.docbookcompiling.DocbookBuildingOptions;
import com.redhat.topicindex.component.docbookrenderer.structures.tocformat.TagRequirements;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.ComponentBaseTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTBaseTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTCategoryV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTTagV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTTranslatedTopicV1;
import com.redhat.topicindex.rest.expand.ExpandDataDetails;
import com.redhat.topicindex.rest.expand.ExpandDataTrunk;
import com.redhat.topicindex.rest.sharedinterface.RESTInterfaceV1;

public class ContentSpecGenerator<T extends RESTBaseTopicV1<T, U>, U extends BaseRestCollectionV1<T, U>>
{
	/** The REST client */
	private final RESTInterfaceV1 restClient;

	/** Jackson object mapper */
	private final ObjectMapper mapper = new ObjectMapper();
	
	public ContentSpecGenerator(final RESTInterfaceV1 restClient)
	{
		this.restClient = restClient;
	}
	

	/**
	 * Generates a Content Specification and fills it in using a set of topics. Once the content
	 * specification is assembled it then removes any empty sections.
	 * 
	 * Note: All topics should be of the same locale.
	 * 
	 * @param clazz The Class of the list of topics. This should be either TopicV1 or TranslatedTopicV1.
	 * @param topics The collection of topics to be used in the generate of the Content Specification.
	 * @param locale The locale of the topics.
	 * @return A ContentSpec object that represents the Content Specification. The toString() method can be used to get the text based version.
	 */
	public ContentSpec generateContentSpecFromTopics(final Class<T> clazz, final BaseRestCollectionV1<T, U> topics, final String locale)
	{
		return this.generateContentSpecFromTopics(clazz, topics, locale, new DocbookBuildingOptions());
	}
	

	/**
	 * Generates a Content Specification and fills it in using a set of topics. Once the content
	 * specification is assembled it then removes any empty sections.
	 * 
	 * Note: All topics should be of the same locale.
	 * 
	 * @param clazz The Class of the list of topics. This should be either TopicV1 or TranslatedTopicV1.
	 * @param topics The collection of topics to be used in the generate of the Content Specification.
	 * @param locale The locale of the topics.
	 * @param docbookBuildingOptions The options that are to be used from a docbook build to generate the content spec.
	 * @return A ContentSpec object that represents the Content Specification. The toString() method can be used to get the text based version.
	 */
	public ContentSpec generateContentSpecFromTopics(final Class<T> clazz, final BaseRestCollectionV1<T, U> topics, final String locale, final DocbookBuildingOptions docbookBuildingOptions)
	{
		final ContentSpec contentSpec = doFormattedTocPass(clazz, topics, locale, docbookBuildingOptions);
		if (contentSpec != null)
		{
			trimEmptySectionsFromContentSpecLevel(contentSpec.getBaseLevel());
		}
		return contentSpec;
	}
	
	/**
	 * Removes any levels from a Content Specification level that contain no content.
	 * 
	 * @param level The level to remove empty sections from.
	 */
	private void trimEmptySectionsFromContentSpecLevel(final Level level)
	{
		if (level == null) return;
		
		final List<Level> childLevels = new LinkedList<Level>(level.getChildLevels());
		for (final Level childLevel : childLevels)
		{
			if (!childLevel.hasSpecTopics())
				level.removeChild(childLevel);
			
			trimEmptySectionsFromContentSpecLevel(childLevel);
		}
	}
	
	/**
	 * Populates a content specifications level with all topics that match the
	 * criteria required by the TagRequirements.
	 * 
	 * @param topics The list of topics that can be matched to the level requirements.
	 * @param level The level to populate with topics.
	 * @param childRequirements The TagRequirements for this level based on the child requirements from the levels parent.
	 * @param displayRequirements The TagRequirements to display topics at this level.
	 */
	private void populateContentSpecLevel(final BaseRestCollectionV1<T, U> topics, final Level level, final TagRequirements childRequirements, final TagRequirements displayRequirements)
	{
		/*
		 * If this branch has no parent, then it is the top level and we don't
		 * add topics to it
		 */
		if (level.getParent() != null && childRequirements != null && displayRequirements != null && displayRequirements.hasRequirements())
		{
			final TagRequirements requirements = new TagRequirements();
			/* get the tags required to be a child of the parent toc levels */
			requirements.merge(childRequirements);
			/* and add the tags required to be displayed at this level */
			requirements.merge(displayRequirements);

			for (final T topic : topics.getItems())
			{
				boolean doesMatch = true;
				for (final RESTTagV1 andTag : requirements.getMatchAllOf())
				{
					if (!ComponentBaseTopicV1.hasTag(topic, andTag.getId()))
					{
						doesMatch = false;
						break;
					}
				}

				if (doesMatch && requirements.getMatchOneOf().size() != 0)
				{
					for (final ArrayList<RESTTagV1> orBlock : requirements.getMatchOneOf())
					{
						if (orBlock.size() != 0)
						{
							boolean matchesOrBlock = false;
							for (final RESTTagV1 orTag : orBlock)
							{
								if (ComponentBaseTopicV1.hasTag(topic, orTag.getId()))
								{
									matchesOrBlock = true;
									break;
								}
							}

							if (!matchesOrBlock)
							{
								doesMatch = false;
								break;
							}
						}
					}
				}

				if (doesMatch)
				{
					final Integer topicId;
					final String topicTitle;
					if (topic instanceof RESTTranslatedTopicV1)
					{
						topicId = ((RESTTranslatedTopicV1) topic).getTopicId();
						topicTitle = ((RESTTranslatedTopicV1) topic).getTopic().getTitle();
					}
					else
					{
						topicId = topic.getId();
						topicTitle = topic.getTitle();
					}
					
					final SpecTopic specTopic = new SpecTopic(topicId, topicTitle);
					specTopic.setTopic(topic.clone(false));
					level.appendSpecTopic(specTopic);
				}
			}
		}
	}

	/**
	 * Uses the technology, common names and concerns to build a basic content specification and then
	 * adds topics that match each levels criteria into the content specification.
	 * 
	 * @param clazz The Class of the list of topics. This should be either TopicV1 or TranslatedTopicV1.
	 * @param topics The collection of topics to be used in the generate of the Content Specification.
	 * @param locale The locale of the topics.
	 * @param docbookBuildingOptions The options that are to be used from a docbook build to generate the content spec.
	 * @return A ContentSpec object that represents the assembled Content Specification. The toString() method can be used to get the text based version.
	 */
	private ContentSpec doFormattedTocPass(final Class<T> clazz, final BaseRestCollectionV1<T, U> topics, final String locale, final DocbookBuildingOptions docbookBuildingOptions)
	{
		try
		{
			/* The return value is a content specification. The 
			 * content specification defines the structure and 
			 * contents of the TOC.
			 */
			final ContentSpec retValue = new ContentSpec();
			
			/* Setup the basic content specification data */
			retValue.setTitle(docbookBuildingOptions.getBookTitle());
			retValue.setBrand("JBoss-EAP6");
			retValue.setProduct(docbookBuildingOptions.getBookProduct());
			retValue.setVersion(docbookBuildingOptions.getBookProductVersion());
			retValue.setEdition(docbookBuildingOptions.getBookEdition() == null || docbookBuildingOptions.getBookEdition().isEmpty() ? null : docbookBuildingOptions.getBookEdition());
			retValue.setSubtitle(docbookBuildingOptions.getBookSubtitle() == null || docbookBuildingOptions.getBookSubtitle().isEmpty() ? null : docbookBuildingOptions.getBookSubtitle());
			retValue.setPubsNumber(docbookBuildingOptions.getBookPubsnumber() == null || docbookBuildingOptions.getBookPubsnumber().isEmpty() ? 1 : Integer.parseInt(docbookBuildingOptions.getBookPubsnumber()));
			retValue.setDtd("Docbook 4.5");
			retValue.setOutputStyle(CSConstants.SKYNET_OUTPUT_FORMAT);
			retValue.setCopyrightHolder("Red Hat, Inc");
			retValue.setInjectSurveyLinks(docbookBuildingOptions.getInsertSurveyLink() == null ? false : docbookBuildingOptions.getInsertSurveyLink());
			
			if (clazz == RESTTranslatedTopicV1.class)
				retValue.setLocale(locale);

			/* Create an expand block for the tag parent tags */
			final ExpandDataTrunk parentTags = new ExpandDataTrunk(new ExpandDataDetails("parenttags"));
			parentTags.setBranches(CollectionUtilities.toArrayList(new ExpandDataTrunk(new ExpandDataDetails("categories"))));

			final ExpandDataTrunk childTags = new ExpandDataTrunk(new ExpandDataDetails("childtags"));

			final ExpandDataTrunk expandTags = new ExpandDataTrunk(new ExpandDataDetails("tags"));
			expandTags.setBranches(CollectionUtilities.toArrayList(parentTags, childTags));

			final ExpandDataTrunk expand = new ExpandDataTrunk();
			expand.setBranches(CollectionUtilities.toArrayList(expandTags));

			final String expandString = mapper.writeValueAsString(expand);
			//final String expandEncodedString = URLEncoder.encode(expandString, "UTF-8");

			/* Get the technology and common names categories */
			final RESTCategoryV1 technologyCategroy = restClient.getJSONCategory(DocbookBuilderConstants.TECHNOLOGY_CATEGORY_ID, expandString);
			final RESTCategoryV1 commonNamesCategory = restClient.getJSONCategory(DocbookBuilderConstants.COMMON_NAME_CATEGORY_ID, expandString);

			/*
			 * The top level TOC elements are made up of the technology and
			 * common name tags that are not encompassed by another tag. So here
			 * we get the tags out of the tech and common names categories, and
			 * pull outthose that are not encompassed.
			 */
			final List<RESTTagV1> topLevelTags = new ArrayList<RESTTagV1>();
			for (final RESTCategoryV1 category : new RESTCategoryV1[]
			{ technologyCategroy, commonNamesCategory })
			{
				if (category.getTags().getItems() != null)
				{
					for (final RESTTagV1 tag : category.getTags().getItems())
					{
						boolean isEmcompassed = false;
						for (final RESTTagV1 parentTag : tag.getParentTags().getItems())
						{
							for (final RESTCategoryV1 parentTagCategory : parentTag.getCategories().getItems())
							{
								if (parentTagCategory.getId() == DocbookBuilderConstants.TECHNOLOGY_CATEGORY_ID || parentTagCategory.getId() == DocbookBuilderConstants.COMMON_NAME_CATEGORY_ID)
								{
									isEmcompassed = true;
									break;
								}
							}
	
							if (isEmcompassed)
								break;
						}
	
						/*
						 * This tag is not encompassed by any other tech or common
						 * name tags, so it is a candidate to appear on the top
						 * level of the TOC
						 */
						if (!isEmcompassed)
						{
							topLevelTags.add(tag);
						}
					}
				}
			}

			/* Create an expand block for the tag parent tags */
			final ExpandDataTrunk concernCategoryExpand = new ExpandDataTrunk();
			final ExpandDataTrunk concernCategoryExpandTags = new ExpandDataTrunk(new ExpandDataDetails("tags"));
			concernCategoryExpand.setBranches(CollectionUtilities.toArrayList(concernCategoryExpandTags));

			final String concernCategoryExpandString = mapper.writeValueAsString(concernCategoryExpand);
			//final String concernCategoryExpandStringEncoded = URLEncoder.encode(concernCategoryExpandString, "UTF-8");

			/* Get the technology and common names categories */
			final RESTCategoryV1 concernCategory = restClient.getJSONCategory(DocbookBuilderConstants.CONCERN_CATEGORY_ID, concernCategoryExpandString);

			/* Get the task reference and concept tag*/
			final RESTTagV1 referenceTag = restClient.getJSONTag(DocbookBuilderConstants.REFERENCE_TAG_ID, "");
			final RESTTagV1 conceptTag = restClient.getJSONTag(DocbookBuilderConstants.CONCEPT_TAG_ID, "");
			final RESTTagV1 conceptualOverviewTag = restClient.getJSONTag(DocbookBuilderConstants.CONCEPTUALOVERVIEW_TAG_ID, "");
			final RESTTagV1 taskTag = restClient.getJSONTag(DocbookBuilderConstants.TASK_TAG_ID, "");

			/* add TocFormatBranch objects for each top level tag */
			for (final RESTTagV1 tag : topLevelTags)
			{
				/*
				 * Create the top level tag. This level is represented by the
				 * tags that are not encompased, and includes any topic that has
				 * that tag or any tag that is encompassed by this tag.
				 */
				final TagRequirements topLevelBranchTags = new TagRequirements((RESTTagV1) null, new ArrayList<RESTTagV1>()
				{
					private static final long serialVersionUID = 7499166852563779981L;

					{
						add(tag);
						addAll(tag.getChildTags().getItems());
					}
				});

				final Chapter topLevelTagChapter = new Chapter(tag.getName());
				retValue.appendChapter(topLevelTagChapter);
				
				populateContentSpecLevel(topics, topLevelTagChapter, topLevelBranchTags, null);

				for (final RESTTagV1 concernTag : concernCategory.getTags().getItems())
				{
					/*
					 * the second level of the toc are the concerns, which will
					 * display the tasks and conceptual overviews beneath them
					 */
					final TagRequirements concernLevelChildTags = new TagRequirements(concernTag, (RESTTagV1) null);
					concernLevelChildTags.merge(topLevelBranchTags);
					final TagRequirements concernLevelDisplayTags = new TagRequirements((RESTTagV1) null, CollectionUtilities.toArrayList(conceptualOverviewTag, taskTag));
					
					final Section concernSection = new Section(concernTag.getName());
					topLevelTagChapter.appendChild(concernSection);
					
					populateContentSpecLevel(topics, concernSection, concernLevelChildTags, concernLevelDisplayTags);
					
					/*
					 * the third levels of the TOC are the concept and reference
					 * topics
					 */
					final Section conceptSection = new Section(conceptTag.getName());
					final Section referenceSection = new Section(referenceTag.getName());
					
					if (concernSection.getChildNodes().isEmpty())
						concernSection.appendChild(referenceSection);
					else
						concernSection.insertBefore(referenceSection, concernSection.getFirstSpecNode());
					concernSection.insertBefore(conceptSection, referenceSection);
					
					populateContentSpecLevel(topics, conceptSection, concernLevelChildTags, new TagRequirements(conceptTag, (RESTTagV1) null));
					populateContentSpecLevel(topics, referenceSection, concernLevelChildTags, new TagRequirements(referenceTag, (RESTTagV1) null));

				}
			}

			return retValue;
		}
		catch (final Exception ex)
		{
			ExceptionUtilities.handleException(ex);
			return null;
		}
	}
}
