package com.redhat.ecs.services.docbookcompiling;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.redhat.ecs.commonutils.ExceptionUtilities;

/**
 * This class contains the options associated with building the docbook zip
 * file.
 * 
 * Strictly speaking a Filter object will have all these settings in as a
 * collection of FilterOption objects. However, for flexibility, the database
 * stores the docbook building options as key value pairs. This class serves as
 * a kind of middle ground between the key value pairs in the database (and
 * therefore the FilterOption class), and the UI which is bound to the more type
 * safe getter and setter methods.
 * 
 * It is possible that once these options are locked down, they will become
 * fields in a database table, and this class will not be necessary.
 */
public class DocbookBuildingOptions
{
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption
	 * table for the "Process Related Topics" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_PROCESS_RELATED_TOPICS = "Process Related Topics";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption
	 * table for the "Show Remarks" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_SHOW_REMARKS = "Show Remarks";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption
	 * table for the "Dynamic TOC" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_ENABLE_DYNAMIC_TOC = "Dynamic TOC";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption
	 * table for the "Build Narrative" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_BUILD_NARRATIVE = "Build Narrative";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption
	 * table for the "Ignore Missing Injections" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_IGNORE_MISSING_CUSTOM_INJECTIONS = "Ignore Missing Injections";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption
	 * table for the "Suppress Error Page" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_SUPPRESS_ERROR_PAGE = "Suppress Error Page";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption
	 * table for the "Task And Overview Only" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_TASK_AND_OVERVIEW_ONLY = "Task And Overview Only";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption
	 * table for the "Insert Survey Link" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_INSERT_SURVEY_LINK = "Insert Survey Link";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption
	 * table for the "publican.cfg cvs_pkg option" value
	 */
	public static String DOCBOOK_BUILDING_OPTION_CVS_PKG = "publican.cfg cvs_pkg option";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption
	 * table for the "Makefile PRODUCT" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_MAKEFILE_PRODUCT = "Makefile PRODUCT";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption
	 * table for the "Makefile PROD_VERSION" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_MAKEFILE_PROD_VERSION = "Makefile PROD_VERSION";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption
	 * table for the "Makefile PROD_VERSION" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_MAKEFILE_LANG = "Makefile LANG";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption
	 * table for the "Makefile BOOKS" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_MAKEFILE_BOOKS = "Makefile BOOKS";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption
	 * table for the "Makefile VERSION" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_MAKEFILE_VERSION = "Makefile VERSION";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption
	 * table for the "Makefile RELEASE" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_MAKEFILE_RELEASE = "Makefile RELEASE";
	/**
	 * The value assigned to the FilterOptionName field in the FilterOption
	 * table for the "Send To" option
	 */
	public static String DOCBOOK_BUILDING_OPTION_SEND_TO = "Send To";
	/**
	 * A name that can be assigned to a build to identify it
	 */
	public static String DOCBOOK_BUILDING_OPTION_BUILD_NAME = "Build Name";

	private Boolean processRelatedTopics = false;
	private Boolean publicanShowRemarks = false;
	private Boolean enableDynamicTreeToc = true;
	private Boolean buildNarrative = false;
	private Boolean ignoreMissingCustomInjections = true;
	private Boolean suppressErrorsPage = false;
	private Boolean taskAndOverviewOnly = true;
	private Boolean insertSurveyLink = true;
	private String emailTo;

	/** The cvs_pkg option in the publican.cfg file */
	private String cvsPkgOption = "JBoss_Enterprise_Application_Platform-6-web-__LANG__";

	private String makefileProductOption = "JBoss_Enterprise_Application_Platform";
	private String makefileProdVersionOption = "6";
	private String makefileLangOption = "en-US";
	private String makefileBooksOption = "Beta_Documentation";
	private String makefileVersionOption = "1";
	private String makefileReleaseOption = "1";
	
	private String buildName;

	@JsonIgnore
	public boolean isValid()
	{
		return makefileProductOption != null && !makefileProductOption.trim().isEmpty() && makefileProdVersionOption != null && !makefileProdVersionOption.trim().isEmpty() && makefileLangOption != null && !makefileLangOption.trim().isEmpty() && makefileBooksOption != null && !makefileBooksOption.trim().isEmpty()
				&& makefileVersionOption != null && !makefileVersionOption.trim().isEmpty() && makefileReleaseOption != null && !makefileReleaseOption.trim().isEmpty() && emailTo != null && !emailTo.trim().isEmpty();
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
	 * @return A collection of the option names that can be set or retrieved in
	 *         this class.
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
		retValue.add(DOCBOOK_BUILDING_OPTION_MAKEFILE_PRODUCT);
		retValue.add(DOCBOOK_BUILDING_OPTION_MAKEFILE_PROD_VERSION);
		retValue.add(DOCBOOK_BUILDING_OPTION_MAKEFILE_LANG);
		retValue.add(DOCBOOK_BUILDING_OPTION_MAKEFILE_BOOKS);
		retValue.add(DOCBOOK_BUILDING_OPTION_MAKEFILE_VERSION);
		retValue.add(DOCBOOK_BUILDING_OPTION_MAKEFILE_RELEASE);
		retValue.add(DOCBOOK_BUILDING_OPTION_SEND_TO);
		retValue.add(DOCBOOK_BUILDING_OPTION_BUILD_NAME);
		
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

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_MAKEFILE_PRODUCT))
			return this.getMakefileProductOption() == null ? null : this.getMakefileProductOption();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_MAKEFILE_PROD_VERSION))
			return this.getMakefileProdVersionOption() == null ? null : this.getMakefileProdVersionOption();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_MAKEFILE_LANG))
			return this.getMakefileLangOption() == null ? null : this.getMakefileLangOption();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_MAKEFILE_BOOKS))
			return this.getMakefileBooksOption() == null ? null : this.getMakefileBooksOption();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_MAKEFILE_VERSION))
			return this.getMakefileVersionOption() == null ? null : this.getMakefileVersionOption();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_MAKEFILE_RELEASE))
			return this.getMakefileReleaseOption() == null ? null : this.getMakefileReleaseOption();

		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_SEND_TO))
			return this.getEmailTo();
		
		if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BUILD_NAME))
			return this.getBuildName();

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

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_MAKEFILE_PRODUCT))
				this.setMakefileProductOption(fieldValue);

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_MAKEFILE_PROD_VERSION))
				this.setMakefileProdVersionOption(fieldValue);

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_MAKEFILE_LANG))
				this.setMakefileLangOption(fieldValue);

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_MAKEFILE_BOOKS))
				this.setMakefileBooksOption(fieldValue);

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_MAKEFILE_VERSION))
				this.setMakefileVersionOption(fieldValue);

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_MAKEFILE_RELEASE))
				this.setMakefileReleaseOption(fieldValue);

			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_SEND_TO))
				this.setEmailTo(fieldValue);
			
			if (fixedFieldName.equalsIgnoreCase(DOCBOOK_BUILDING_OPTION_BUILD_NAME))
				this.setBuildName(fieldValue);

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

	public String getMakefileProductOption()
	{
		return makefileProductOption;
	}

	public void setMakefileProductOption(final String makefileProductOption)
	{
		this.makefileProductOption = makefileProductOption;
	}

	public String getMakefileProdVersionOption()
	{
		return makefileProdVersionOption;
	}

	public void setMakefileProdVersionOption(final String makefileProdVersionOption)
	{
		this.makefileProdVersionOption = makefileProdVersionOption;
	}

	public String getMakefileLangOption()
	{
		return makefileLangOption;
	}

	public void setMakefileLangOption(final String makefileLangOption)
	{
		this.makefileLangOption = makefileLangOption;
	}

	public String getMakefileBooksOption()
	{
		return makefileBooksOption;
	}

	public void setMakefileBooksOption(final String makefileooksOption)
	{
		this.makefileBooksOption = makefileooksOption;
	}

	public String getMakefileVersionOption()
	{
		return makefileVersionOption;
	}

	public void setMakefileVersionOption(final String makefileVersionOption)
	{
		this.makefileVersionOption = makefileVersionOption;
	}

	public String getMakefileReleaseOption()
	{
		return makefileReleaseOption;
	}

	public void setMakefileReleaseOption(final String makefileReleaseOption)
	{
		this.makefileReleaseOption = makefileReleaseOption;
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
}
