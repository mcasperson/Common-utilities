package com.redhat.ecs.services.docbookcompiling;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.redhat.ecs.commonutils.ExceptionUtilities;
import com.redhat.ecs.constants.CommonConstants;

/**
 * This class contains the options associated with building the docbook zip file.
 * 
 * Strictly speaking a Filter object will have all these settings in as a collection of FilterOption objects. However, for flexibility, the database stores the
 * docbook building options as key value pairs. This class serves as a kind of middle ground between the key value pairs in the database (and therefore the
 * FilterOption class), and the UI which is bound to the more type safe getter and setter methods.
 * 
 * It is possible that once these options are locked down, they will become fields in a database table, and this class will not be necessary.
 */
public class DocbookBuildingOptions
{
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Suppress Content Specification Page" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_SUPPRESS_CONTENT_SPEC_PAGE = "Suppress Content Specification Page";

	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Insert bugzilla links" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_INSERT_BUGZILLA_LINKS = "Insert bugzilla links";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Include untranslated topics" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_INCLUDE_UNTRANSLATED_TOPICS = "Include untranslated topics";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Process Related Topics" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_PROCESS_RELATED_TOPICS = "Process Related Topics";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Show Remarks" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_SHOW_REMARKS = "Show Remarks";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Dynamic TOC" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_ENABLE_DYNAMIC_TOC = "Dynamic TOC";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Build Narrative" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_BUILD_NARRATIVE = "Build Narrative";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Ignore Missing Injections" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_IGNORE_MISSING_CUSTOM_INJECTIONS = "Ignore Missing Injections";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Suppress Error Page" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_SUPPRESS_ERROR_PAGE = "Suppress Error Page";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Task And Overview Only" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_TASK_AND_OVERVIEW_ONLY = "Task And Overview Only";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Insert Survey Link" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_INSERT_SURVEY_LINK = "Insert Survey Link";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "publican.cfg cvs_pkg option" value
	 */
	public static String DOCBOOK_BUILDING_OPTION_CVS_PKG = "publican.cfg cvs_pkg option";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Send To" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_SEND_TO = "Send To";
	/**
	 * A name that can be assigned to a build to identify it
	 */
	public static String DOCBOOK_BUILDING_OPTION_BUILD_NAME = "Build Name";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Book Title" value
	 */
	public static String DOCBOOK_BUILDING_OPTION_BOOK_TITLE = "Book Title";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Book Product" value
	 */
	public static String DOCBOOK_BUILDING_OPTION_BOOK_PRODUCT = "Book Product";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Book Product Version" value
	 */
	public static String DOCBOOK_BUILDING_OPTION_BOOK_PRODUCT_VERSION = "Book Product Version";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Book Edition" value
	 */
	public static String DOCBOOK_BUILDING_OPTION_BOOK_EDITION = "Book Edition";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Book Pubsnumber" value
	 */
	public static String DOCBOOK_BUILDING_OPTION_BOOK_PUBSNUMBER = "Book Pubsnumber";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Book Subtitle" value
	 */
	public static String DOCBOOK_BUILDING_OPTION_BOOK_SUBTITLE = "Book Subtitle";	
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption table for the "Insert editor links" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_INSERT_EDITOR_LINKS = "Insert editor links";

	private Boolean suppressContentSpecPage = false;
	private Boolean insertBugzillaLinks = true;
	private Boolean includeUntranslatedTopics = true;
	private Boolean processRelatedTopics = false;
	private Boolean publicanShowRemarks = false;
	private Boolean enableDynamicTreeToc = true;
	private Boolean buildNarrative = false;
	private Boolean ignoreMissingCustomInjections = true;
	private Boolean suppressErrorsPage = false;
	private Boolean taskAndOverviewOnly = true;
	private Boolean insertSurveyLink = true;
	private String emailTo = null;
	private Boolean insertEditorLinks = false;

	/** The cvs_pkg option in the publican.cfg file */
	private String cvsPkgOption = null;

	private String buildName = null;

	// Book name options
	private String bookTitle = "Book";
	private String bookProduct = "Documentation 0.1";
	private String bookProductVersion = "0.1";
	private String bookEdition = null;
	private String bookPubsnumber = null;
	private String bookSubtitle = null;

	@JsonIgnore
	public boolean isValid()
	{
		if (emailTo == null || emailTo.trim().isEmpty() || !emailTo.matches(CommonConstants.EMAIL_REGEX))
		{
			return false;
		}
			
		if (bookTitle == null || bookTitle.isEmpty())
		{
			return false;
		}
		
		if (bookProduct == null || bookProduct.isEmpty())
		{
			return false;
		}
		
		if (bookProductVersion == null || bookProductVersion.isEmpty()
				|| !bookProductVersion.matches("[0-9]+(.[0-9+](.[0-9]+)?)?"))
		{
			return false;
		}
		
		if (bookEdition != null && !bookEdition.isEmpty() 
				&& !bookEdition.matches("[0-9]+(.[0-9+](.[0-9]+)?)?"))
		{
			return false;
		}
		
		if (bookPubsnumber != null && !bookPubsnumber.isEmpty() 
				&& !bookPubsnumber.matches("[0-9]+"))
		{
			return false;
		}
		
		return true;
	}

	public Boolean getIncludeUntranslatedTopics()
	{
		return includeUntranslatedTopics;
	}

	public void setIncludeUntranslatedTopics(Boolean includeUntranslatedTopics)
	{
		this.includeUntranslatedTopics = includeUntranslatedTopics;
	}

	public void setProcessRelatedTopics(final Boolean processRelatedTopics)
	{
		this.processRelatedTopics = processRelatedTopics;
	}

	public Boolean getProcessRelatedTopics()
	{
		return processRelatedTopics;
	}

	public void setPublicanShowRemarks(final Boolean publicanShowRemarks)
	{
		this.publicanShowRemarks = publicanShowRemarks;
	}

	public Boolean getPublicanShowRemarks()
	{
		return publicanShowRemarks;
	}

	public void setEnableDynamicTreeToc(final Boolean enableDynamicTreeToc)
	{
		this.enableDynamicTreeToc = enableDynamicTreeToc;
	}

	public Boolean getEnableDynamicTreeToc()
	{
		return enableDynamicTreeToc;
	}

	public Boolean getBuildNarrative()
	{
		return buildNarrative;
	}

	public void setBuildNarrative(final Boolean buildNarrative)
	{
		this.buildNarrative = buildNarrative;
	}

	public Boolean getIgnoreMissingCustomInjections()
	{
		return ignoreMissingCustomInjections;
	}

	public void setIgnoreMissingCustomInjections(final Boolean ignoreMissingCustomInjections)
	{
		this.ignoreMissingCustomInjections = ignoreMissingCustomInjections;
	}

	public Boolean getSuppressErrorsPage()
	{
		return suppressErrorsPage;
	}

	public void setSuppressErrorsPage(final Boolean suppressErrorsPage)
	{
		this.suppressErrorsPage = suppressErrorsPage;
	}

	public Boolean getTaskAndOverviewOnly()
	{
		return taskAndOverviewOnly;
	}

	public void setTaskAndOverviewOnly(final Boolean taskAndOverviewOnly)
	{
		this.taskAndOverviewOnly = taskAndOverviewOnly;
	}

	public Boolean getInsertSurveyLink()
	{
		return insertSurveyLink;
	}

	public void setInsertSurveyLink(final Boolean insertSurveyLink)
	{
		this.insertSurveyLink = insertSurveyLink;
	}

	/**
	 * @return A collection of the option names that can be set or retrieved in this class.
	 */
	public static List<String> getOptionNames()
	{
		final List<String> retValue = new ArrayList<String>();

		retValue.add(DOCBOOK_BUILDING_OPTION_BUILD_NARRATIVE);
		retValue.add(DOCBOOK_BUILDING_OPTION_ENABLE_DYNAMIC_TOC);
		retValue.add(DOCBOOK_BUILDING_OPTION_IGNORE_MISSING_CUSTOM_INJECTIONS);
		retValue.add(DOCBOOK_BUILDING_OPTION_INSERT_SURVEY_LINK);
		retValue.add(DOCBOOK_BUILDING_OPTION_PROCESS_RELATED_TOPICS);
		retValue.add(DOCBOOK_BUILDING_OPTION_SHOW_REMARKS);
		retValue.add(DOCBOOK_BUILDING_OPTION_SUPPRESS_ERROR_PAGE);
		retValue.add(DOCBOOK_BUILDING_OPTION_TASK_AND_OVERVIEW_ONLY);
		retValue.add(DOCBOOK_BUILDING_OPTION_CVS_PKG);
		retValue.add(DOCBOOK_BUILDING_OPTION_SEND_TO);
		retValue.add(DOCBOOK_BUILDING_OPTION_BUILD_NAME);
		retValue.add(DOCBOOK_BUILDING_OPTION_INCLUDE_UNTRANSLATED_TOPICS);
		retValue.add(DOCBOOK_BUILDING_OPTION_INSERT_BUGZILLA_LINKS);
		retValue.add(DOCBOOK_BUILDING_OPTION_SUPPRESS_CONTENT_SPEC_PAGE);
		retValue.add(DOCBOOK_BUILDING_OPTION_BOOK_EDITION);
		retValue.add(DOCBOOK_BUILDING_OPTION_BOOK_PRODUCT_VERSION);
		retValue.add(DOCBOOK_BUILDING_OPTION_BOOK_PUBSNUMBER);
		retValue.add(DOCBOOK_BUILDING_OPTION_BOOK_TITLE);
		retValue.add(DOCBOOK_BUILDING_OPTION_BOOK_PRODUCT);
		retValue.add(DOCBOOK_BUILDING_OPTION_BOOK_SUBTITLE);
		retValue.add(DOCBOOK_BUILDING_OPTION_INSERT_EDITOR_LINKS);

		return retValue;
	}

	public String getFieldValue(final String fieldName)
	{
		if (fieldName == null)
			return null;

		final String fixedFieldName = fieldName.trim();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BUILD_NARRATIVE))
			return this.getBuildNarrative() == null ? null : this.getBuildNarrative().toString();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_ENABLE_DYNAMIC_TOC))
			return this.getEnableDynamicTreeToc() == null ? null : this.getEnableDynamicTreeToc().toString();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_IGNORE_MISSING_CUSTOM_INJECTIONS))
			return this.getIgnoreMissingCustomInjections() == null ? null : this.getIgnoreMissingCustomInjections().toString();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_INSERT_SURVEY_LINK))
			return this.getInsertSurveyLink() == null ? null : this.getInsertSurveyLink().toString();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_PROCESS_RELATED_TOPICS))
			return this.getProcessRelatedTopics() == null ? null : this.getProcessRelatedTopics().toString();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_SHOW_REMARKS))
			return this.getPublicanShowRemarks() == null ? null : this.getPublicanShowRemarks().toString();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_SUPPRESS_ERROR_PAGE))
			return this.getSuppressErrorsPage() == null ? null : this.getSuppressErrorsPage().toString();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_TASK_AND_OVERVIEW_ONLY))
			return this.getTaskAndOverviewOnly() == null ? null : this.getTaskAndOverviewOnly().toString();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_CVS_PKG))
			return this.getCvsPkgOption() == null ? null : this.getCvsPkgOption();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_SEND_TO))
			return this.getEmailTo();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BUILD_NAME))
			return this.getBuildName();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_INCLUDE_UNTRANSLATED_TOPICS))
			return this.getIncludeUntranslatedTopics() == null ? null : this.getIncludeUntranslatedTopics().toString();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_INSERT_BUGZILLA_LINKS))
			return this.getInsertBugzillaLinks() == null ? null : this.getInsertBugzillaLinks().toString();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_SUPPRESS_CONTENT_SPEC_PAGE))
			return this.getSuppressContentSpecPage() == null ? null : this.getSuppressContentSpecPage().toString();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BOOK_TITLE))
			return this.getBookTitle();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BOOK_PRODUCT))
			return this.getBookProduct();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BOOK_PRODUCT_VERSION))
			return this.getBookProductVersion();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BOOK_EDITION))
			return this.getBookEdition();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BOOK_PUBSNUMBER))
			return this.getBookPubsnumber() == null ? "" : this.getBookPubsnumber().toString();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BOOK_SUBTITLE))
			return this.getBookSubtitle();
		
		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_INSERT_EDITOR_LINKS))
			return this.getInsertEditorLinks() == null ? null : this.insertEditorLinks.toString();

		return null;
	}

	public void setFieldValue(final String fieldName, final String fieldValue)
	{
		if (fieldName == null)
			return;

		final String fixedFieldName = fieldName.trim();

		try
		{
			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BUILD_NARRATIVE))
				this.setBuildNarrative(Boolean.parseBoolean(fieldValue));

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_ENABLE_DYNAMIC_TOC))
				this.setEnableDynamicTreeToc(Boolean.parseBoolean(fieldValue));

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_IGNORE_MISSING_CUSTOM_INJECTIONS))
				this.setIgnoreMissingCustomInjections(Boolean.parseBoolean(fieldValue));

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_INSERT_SURVEY_LINK))
				this.setInsertSurveyLink(Boolean.parseBoolean(fieldValue));

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_PROCESS_RELATED_TOPICS))
				this.setProcessRelatedTopics(Boolean.parseBoolean(fieldValue));

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_SHOW_REMARKS))
				this.setPublicanShowRemarks(Boolean.parseBoolean(fieldValue));

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_SUPPRESS_ERROR_PAGE))
				this.setSuppressErrorsPage(Boolean.parseBoolean(fieldValue));

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_TASK_AND_OVERVIEW_ONLY))
				this.setTaskAndOverviewOnly(Boolean.parseBoolean(fieldValue));

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_CVS_PKG))
				this.setCvsPkgOption(fieldValue);

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_SEND_TO))
				this.setEmailTo(fieldValue);

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BUILD_NAME))
				this.setBuildName(fieldValue);

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_INCLUDE_UNTRANSLATED_TOPICS))
				this.setIncludeUntranslatedTopics(Boolean.parseBoolean(fieldValue));

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_INSERT_BUGZILLA_LINKS))
				this.setInsertBugzillaLinks(Boolean.parseBoolean(fieldValue));

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_SUPPRESS_CONTENT_SPEC_PAGE))
				this.setInsertBugzillaLinks(Boolean.parseBoolean(fieldValue));

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BOOK_TITLE))
				this.setBookTitle(fieldValue);

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BOOK_PRODUCT))
				this.setBookProduct(fieldValue);

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BOOK_PRODUCT_VERSION))
				this.setBookProductVersion(fieldValue);

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BOOK_EDITION))
				this.setBookEdition(fieldValue);
			
			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_INSERT_EDITOR_LINKS))
				this.setInsertEditorLinks(Boolean.parseBoolean(fieldValue));

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BOOK_PUBSNUMBER))
			{
				this.setBookPubsnumber(fieldValue);
			}

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BOOK_SUBTITLE))
				this.setBookSubtitle(fieldValue);

		}
		catch (final Exception ex)
		{
			ExceptionUtilities.handleException(ex);
		}
	}

	public String getCvsPkgOption()
	{
		return cvsPkgOption;
	}

	public void setCvsPkgOption(final String cvsPkgOption)
	{
		this.cvsPkgOption = cvsPkgOption;
	}

	public String getEmailTo()
	{
		return emailTo;
	}

	public void setEmailTo(final String emailTo)
	{
		this.emailTo = emailTo;
	}

	public String getBuildName()
	{
		return buildName;
	}

	public void setBuildName(final String buildName)
	{
		this.buildName = buildName;
	}

	public Boolean getInsertBugzillaLinks()
	{
		return insertBugzillaLinks;
	}

	public void setInsertBugzillaLinks(Boolean insertBugzillaLinks)
	{
		this.insertBugzillaLinks = insertBugzillaLinks;
	}

	public Boolean getSuppressContentSpecPage()
	{
		return suppressContentSpecPage;
	}

	public void setSuppressContentSpecPage(Boolean suppressContentSpecPage)
	{
		this.suppressContentSpecPage = suppressContentSpecPage;
	}

	public String getBookTitle()
	{
		return bookTitle;
	}

	public void setBookTitle(final String bookTitle)
	{
		this.bookTitle = bookTitle;
	}

	public String getBookProduct()
	{
		return bookProduct;
	}

	public void setBookProduct(final String bookProduct)
	{
		this.bookProduct = bookProduct;
	}

	public String getBookProductVersion()
	{
		return bookProductVersion;
	}

	public void setBookProductVersion(final String bookProductVersion)
	{
		this.bookProductVersion = bookProductVersion;
	}

	public String getBookEdition()
	{
		return bookEdition;
	}

	public void setBookEdition(final String bookEdition)
	{
		this.bookEdition = bookEdition;
	}

	public String getBookPubsnumber()
	{
		return bookPubsnumber;
	}

	public void setBookPubsnumber(final String bookPubsnumber)
	{
		this.bookPubsnumber = bookPubsnumber;
	}

	public String getBookSubtitle()
	{
		return bookSubtitle;
	}

	public void setBookSubtitle(final String bookSubtitle)
	{
		this.bookSubtitle = bookSubtitle;
	}

	public Boolean getInsertEditorLinks()
	{
		return insertEditorLinks;
	}

	public void setInsertEditorLinks(final Boolean insertEditorLinks)
	{
		this.insertEditorLinks = insertEditorLinks;
	}
}
