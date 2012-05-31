package com.redhat.contentspec.utils;

import java.net.URLEncoder;
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
import com.redhat.topicindex.rest.entities.TranslatedTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.IBaseTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.ICategoryV1;
import com.redhat.topicindex.rest.entities.interfaces.ITagV1;
import com.redhat.topicindex.rest.entities.interfaces.ITranslatedTopicV1;
import com.redhat.topicindex.rest.expand.ExpandDataDetails;
import com.redhat.topicindex.rest.expand.ExpandDataTrunk;
import com.redhat.topicindex.rest.sharedinterface.RESTInterfaceV1;

public class ContentSpecGenerator
{
	/** The REST client */
	private final RESTInterfaceV1 restClient;

	/** Jackson object mapper */
	private final ObjectMapper mapper = new ObjectMapper();
	
	public ContentSpecGenerator(final RESTInterfaceV1 restClient)
	{
		this.restClient = restClient;
	}
	
	public <T extends IBaseTopicV1<T>> ContentSpec generateContentSpecFromTopics(final Class<T> clazz, final BaseRestCollectionV1<T> topics, final String locale)
	{
		return this.generateContentSpecFromTopics(clazz, topics, locale, new DocbookBuildingOptions());
	}
	
	public <T extends IBaseTopicV1<T>> ContentSpec generateContentSpecFromTopics(final Class<T> clazz, final BaseRestCollectionV1<T> topics, final String locale, final DocbookBuildingOptions docbookBuildingOptions)
	{
		final ContentSpec contentSpec = doFormattedTocPass(clazz, topics, locale, docbookBuildingOptions);
		trimEmptySectionsFromContentSpecLevel(contentSpec.getBaseLevel());
		return contentSpec;
	}
	
	private void trimEmptySectionsFromContentSpecLevel(final Level level)
	{
		final List<Level> childLevels = new LinkedList<Level>(level.getChildLevels());
		for (final Level childLevel : childLevels)
		{
			if (!childLevel.hasSpecTopics())
				level.removeChild(childLevel);
			
			trimEmptySectionsFromContentSpecLevel(childLevel);
		}
	}
	
	private <T extends IBaseTopicV1<T>> void populateTocLevel(final BaseRestCollectionV1<T> topics, final Level level, final TagRequirements childRequirements, final TagRequirements displayRequirements)
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
				for (final ITagV1 andTag : requirements.getMatchAllOf())
				{
					if (!topic.hasTag(andTag.getId()))
					{
						doesMatch = false;
						break;
					}
				}

				if (doesMatch && requirements.getMatchOneOf().size() != 0)
				{
					for (final ArrayList<ITagV1> orBlock : requirements.getMatchOneOf())
					{
						if (orBlock.size() != 0)
						{
							boolean matchesOrBlock = false;
							for (final ITagV1 orTag : orBlock)
							{
								if (topic.hasTag(orTag.getId()))
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
					if (topic instanceof TranslatedTopicV1)
					{
						topicId = ((TranslatedTopicV1) topic).getTopicId();
						topicTitle = ((TranslatedTopicV1) topic).getTopic().getTitle();
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

	private <T extends IBaseTopicV1<T>> ContentSpec doFormattedTocPass(final Class<T> clazz, final BaseRestCollectionV1<T> topics, final String locale, final DocbookBuildingOptions docbookBuildingOptions)
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
			retValue.setPubsNumber(docbookBuildingOptions.getBookPubsnumber());
			retValue.setDtd("Docbook 4.5");
			retValue.setOutputStyle(CSConstants.SKYNET_OUTPUT_FORMAT);
			retValue.setCopyrightHolder("Red Hat, Inc");
			
			if (clazz == ITranslatedTopicV1.class)
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
			final String expandEncodedString = URLEncoder.encode(expandString, "UTF-8");

			/* Get the technology and common names categories */
			final ICategoryV1 technologyCategroy = restClient.getJSONCategory(DocbookBuilderConstants.TECHNOLOGY_CATEGORY_ID, expandEncodedString);
			final ICategoryV1 commonNamesCategory = restClient.getJSONCategory(DocbookBuilderConstants.COMMON_NAME_CATEGORY_ID, expandEncodedString);

			/*
			 * The top level TOC elements are made up of the technology and
			 * common name tags that are not encompassed by another tag. So here
			 * we get the tags out of the tech and common names categories, and
			 * pull outthose that are not encompassed.
			 */
			final List<ITagV1> topLevelTags = new ArrayList<ITagV1>();
			for (final ICategoryV1 category : new ICategoryV1[]
			{ technologyCategroy, commonNamesCategory })
			{
				for (final ITagV1 tag : category.getTags().getItems())
				{
					boolean isEmcompassed = false;
					for (final ITagV1 parentTag : tag.getParentTags().getItems())
					{
						for (final ICategoryV1 parentTagCategory : parentTag.getCategories().getItems())
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

			/* Create an expand block for the tag parent tags */
			final ExpandDataTrunk concernCategoryExpand = new ExpandDataTrunk();
			final ExpandDataTrunk concernCategoryExpandTags = new ExpandDataTrunk(new ExpandDataDetails("tags"));
			concernCategoryExpand.setBranches(CollectionUtilities.toArrayList(concernCategoryExpandTags));

			final String concernCategoryExpandString = mapper.writeValueAsString(concernCategoryExpand);
			final String concernCategoryExpandStringEncoded = URLEncoder.encode(concernCategoryExpandString, "UTF-8");

			/* Get the technology and common names categories */
			final ICategoryV1 concernCategory = restClient.getJSONCategory(DocbookBuilderConstants.CONCERN_CATEGORY_ID, concernCategoryExpandStringEncoded);

			/* Get the task reference and concept tag*/
			final ITagV1 referenceTag = restClient.getJSONTag(DocbookBuilderConstants.REFERENCE_TAG_ID, "");
			final ITagV1 conceptTag = restClient.getJSONTag(DocbookBuilderConstants.CONCEPT_TAG_ID, "");
			final ITagV1 conceptualOverviewTag = restClient.getJSONTag(DocbookBuilderConstants.CONCEPTUALOVERVIEW_TAG_ID, "");
			final ITagV1 taskTag = restClient.getJSONTag(DocbookBuilderConstants.TASK_TAG_ID, "");

			/* add TocFormatBranch objects for each top level tag */
			for (final ITagV1 tag : topLevelTags)
			{
				/*
				 * Create the top level tag. This level is represented by the
				 * tags that are not encompased, and includes any topic that has
				 * that tag or any tag that is encompassed by this tag.
				 */
				final TagRequirements topLevelBranchTags = new TagRequirements((ITagV1) null, new ArrayList<ITagV1>()
				{
					private static final long serialVersionUID = 7499166852563779981L;

					{
						add(tag);
						addAll(tag.getChildTags().getItems());
					}
				});

				final Chapter topLevelTagChapter = new Chapter(tag.getName());
				retValue.appendChapter(topLevelTagChapter);
				
				populateTocLevel(topics, topLevelTagChapter, topLevelBranchTags, null);

				for (final ITagV1 concernTag : concernCategory.getTags().getItems())
				{
					/*
					 * the second level of the toc are the concerns, which will
					 * display the tasks and conceptual overviews beneath them
					 */
					final TagRequirements concernLevelChildTags = new TagRequirements(concernTag, (ITagV1) null);
					concernLevelChildTags.merge(topLevelBranchTags);
					final TagRequirements concernLevelDisplayTags = new TagRequirements((ITagV1) null, CollectionUtilities.toArrayList(conceptualOverviewTag, taskTag));
					
					final Section concernSection = new Section(concernTag.getName());
					topLevelTagChapter.appendChild(concernSection);
					
					populateTocLevel(topics, concernSection, concernLevelChildTags, concernLevelDisplayTags);
					
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
					
					populateTocLevel(topics, conceptSection, concernLevelChildTags, new TagRequirements(conceptTag, (ITagV1) null));
					populateTocLevel(topics, referenceSection, concernLevelChildTags, new TagRequirements(referenceTag, (ITagV1) null));
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
