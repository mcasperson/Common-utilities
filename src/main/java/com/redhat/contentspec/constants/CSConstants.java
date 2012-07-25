package com.redhat.contentspec.constants;

public class CSConstants {
	
	public static final String BUGZILLA_URL_PROPERTY = "contentSpec.bugzillaUrl";
	
	// Relationship Types
	public static final Integer RELATIONSHIP_TYPE_ID	= 1;
	public static final Integer PREREQUISITE_TYPE_ID	= 2;
	public static final Integer NEXT_TYPE_ID			= 3;
	public static final Integer PREVIOUS_TYPE_ID		= 4;

	// Level Type Constants
	public static final int LEVEL_CHAPTER 	= 1;
	public static final int LEVEL_SECTION 	= 2;
	public static final int LEVEL_APPENDIX 	= 3;
	public static final int LEVEL_BASE 		= 4;
	public static final int LEVEL_PROCESS 	= 5;
	public static final int LEVEL_PART	 	= 6;
	
	// Other
	public static final int CSP_PROPERTY_ID			= 15;
	public static final String CSP_PRE_PROCESSED_STRING = "Pre Processed";
	public static final String CSP_POST_PROCESSED_STRING = "Post Processed";

	// Output format constants
	public static final String SKYNET_OUTPUT_FORMAT = "Cloud";
	public static final String CSP_OUTPUT_FORMAT = "Narrative";
	
	public static final Integer TYPE_CATEGORY_ID = 4;
	public static final Integer TECHNOLOGY_CATEGORY_ID = 3;
	public static final Integer RELEASE_CATEGORY_ID = 15;
	public static final Integer WRITER_CATEGORY_ID = 12;
	public static final Integer COMMON_NAME_CATEGORY_ID = 17;
	public static final String TECHNOLOGY_CATEGORY_NAME = "Technologies";
	public static final Integer CONCERN_CATEGORY_ID = 2;
	public static final String CONCERN_CATEGORY_NAME = "Concerns";
	public static final Integer LIFECYCLE_CATEGORY_ID = 5;
	public static final Integer RH_INTERNAL_TAG_ID = 315;
	
	public static final String NEW_TOPIC_ID_REGEX 			= "^N[0-9]*$";
	public static final String POST_NEW_TOPIC_ID_REGEX 		= "^[0-9]+N[0-9]*$";
	public static final String EXISTING_TOPIC_ID_REGEX 		= "^[0-9]+$";
	public static final String CLONED_TOPIC_ID_REGEX 		= "^C[0-9]+$";
	public static final String DUPLICATE_TOPIC_ID_REGEX 	= "^X[0-9]+$";
	public static final String CLONED_DUPLICATE_TOPIC_ID_REGEX 	= "^XC[0-9]+$";
	public static final String ALL_TOPIC_ID_REGEX 			= "(^N[0-9]*$)|(^[0-9]+$)|(^X[0-9]+$)|(^C[0-9]+$)|(^XC[0-9]+$)";
	
	public static final String CHAPTER = "Chapter";
	public static final String APPENDIX = "Appendix";
	public static final String SECTION = "Section";
	public static final String PROCESS = "Process";
	public static final String PART = "Part";
	
	/** The Concept tag ID */
	public static final Integer CONCEPT_TAG_ID = 5;
	/** The Concept tag name */
	public static final String CONCEPT_TAG_NAME = "Concept";
	/** The Conceptual Overview tag ID */
	public static final Integer CONCEPTUALOVERVIEW_TAG_ID = 93;
	/** The Conceptual Overview tag name */
	public static final String CONCEPTUALOVERVIEW_TAG_NAME = "Overview";
	/** The Reference tag ID */
	public static final Integer REFERENCE_TAG_ID = 6;
	/** The Reference tag name */
	public static final String REFERENCE_TAG_NAME = "Reference";
	/** The Task tag ID */
	public static final Integer TASK_TAG_ID = 4;
	/** The Task tag name */
	public static final String TASK_TAG_NAME = "Task";
	/** The Written tag ID */
	public static final Integer WRITTEN_TAG_ID = 19;
	/** The Tag Description tag ID */
	public static final Integer TAG_DESCRIPTION_TAG_ID = 215;
	/** The Home tag ID */
	public static final Integer HOME_TAG_ID = 216;
	/** The Content Specification tag ID */
	public static final Integer CONTENT_SPEC_TAG_ID = 268;
	/** The Content Specification tag name */
	public static final String CONTENT_SPEC_TAG_NAME = "Content Specfication";
	
	/** The Added By Property Tag ID */
	public static final Integer ADDED_BY_PROPERTY_TAG_ID = 14;
	/** The First Name Property Tag ID */
	public static final Integer FIRST_NAME_PROPERTY_TAG_ID = 1;
	/** The Last Name Property Tag ID */
	public static final Integer LAST_NAME_PROPERTY_TAG_ID = 2;
	/** The Email Address Property Tag ID */
	public static final Integer EMAIL_PROPERTY_TAG_ID = 3;
	/** The Organization Property Tag ID */
	public static final Integer ORGANIZATION_PROPERTY_TAG_ID = 18;
	/** The Organization Division Property Tag ID */
	public static final Integer ORG_DIVISION_PROPERTY_TAG_ID = 19;
	/** The DTD Property Tag ID */
	public static final Integer DTD_PROPERTY_TAG_ID = 16;
	/** The Content Specification Type Property Tag ID */
	public static final Integer CSP_TYPE_PROPERTY_TAG_ID = 17;
	/** The Content Specification Read-Only Property Tag ID */
	public static final Integer CSP_READ_ONLY_PROPERTY_TAG_ID = 25;
	
	/** The initial name for a snapshot revision */
	public static final String INITIAL_SNAPSHOT_REVISION_NAME = "Initial Untranslated Revision";

}
