package com.redhat.ecs.constants;

import java.text.SimpleDateFormat;
import java.util.List;

import com.redhat.ecs.commonutils.CollectionUtilities;

/**
 * This class defines the constants that are used between Skynet and its various
 * components
 */
public class CommonConstants
{
	/** The PropertyTag ID that defines the fixed URL */
	public static final Integer FIXED_URL_PROP_TAG_ID = 20;
	/** The PropertyTag ID that defines the Bugzilla Product */
	public static final Integer BUGZILLA_PRODUCT_PROP_TAG_ID = 21;
	/** The PropertyTag ID that defines the Bugzilla Component */
	public static final Integer BUGZILLA_COMPONENT_PROP_TAG_ID = 23;
	/** The PropertyTag ID that defines the Bugzilla Version */
	public static final Integer BUGZILLA_VERSION_PROP_TAG_ID = 22;
	/** The PropertyTag ID that defines the Bugzilla Product */
	public static final Integer BUGZILLA_KEYWORDS_PROP_TAG_ID = 24;
	/** The Bugzilla Profile Property Tag */
	public final static Integer BUGZILLA_PROFILE_PROPERTY = 5;
	
	/** The Regular Expression the defines the search format for a property tag */
	public static String PROPERTY_TAG_SEARCH_RE = "(?<PropertyTagID>\\d+) (?<PropertyTagValue>.*)";	
	/** The regular expression that matches the Build ID field, without the prefixed topic id */
	public static String BUGZILLA_BUILD_ID_RE = "-[0-9]+ [0-9]{2} [A-Za-z]{3} [0-9]{4} [0-9]{2}:[0-9]{2}";
	/** The regular expression that matches the Build ID field, with the individual fields grouped and named */
	public static String BUGZILLA_BUILD_ID_NAMED_RE = "(?<TopicID>\\d+)-(?<TopicRevision>\\d+) (?<TopicRevisionDay>\\d{2}) (?<TopicRevisionMonth>\\w{3}) (?<TopicRevisionYear>\\d{4}) (?<TopicRevisionHour>\\d{2}):(?<TopicRevisionMinute>\\d{2})\\s*(?<TopicLocale>\\w*)";
	
	/** The default locale */
	public static final String DEFAULT_LOCALE_PROPERTY = "topicIndex.defaultLocale";
	/** The bugzilla url */
	public static final String BUGZILLA_URL_PROPERTY = "topicIndex.bugzillaUrl";
	/** The bugzilla username */
	public static final String BUGZILLA_USERNAME_PROPERTY = "topicIndex.bugzillaUsername";
	/** The bugzilla password */
	public static final String BUGZILLA_PASSWORD_PROPERTY = "topicIndex.bugzillaPassword";
	/**
	 * The system property that identifies this Skynet instance
	 */
	public static final String INSTANCE_NAME_PROPERTY = "topicIndex.instanceName";
	/** The format of the date to be displayed by any date widget */
	public static final String FILTER_DISPLAY_DATE_FORMAT = "dd MMM yyyy HH:mm";
	/** The ISO8601 date format, used for SQL queries */
	public static final String ISO8601_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
	/**
	 * The system property that defines the XML elements that should be
	 * serialized verbatim
	 */
	public static final String VERBATIM_XML_ELEMENTS_SYSTEM_PROPERTY = "topicIndex.verbatimXMLElements";
	/**
	 * The system property that defines the XML elements that should be
	 * serialized inline
	 */
	public static final String INLINE_XML_ELEMENTS_SYSTEM_PROPERTY = "topicIndex.inlineXMLElements";
	/**
	 * The system property that defines the XML elements that should be
	 * serialized inline, but on a new line
	 */
	public static final String CONTENTS_INLINE_XML_ELEMENTS_SYSTEM_PROPERTY = "topicIndex.contentsInlineXMLElements";
	/**
	 * The system property that defines the STOMP message server
	 */
	public static final String STOMP_MESSAGE_SERVER_SYSTEM_PROPERTY = "topicIndex.stompMessageServer";
	/**
	 * The system property that defines the STOMP message server port
	 */
	public static final String STOMP_MESSAGE_SERVER_PORT_SYSTEM_PROPERTY = "topicIndex.stompMessageServerPort";
	/**
	 * The system property that defines the STOMP message server username
	 */
	public static final String STOMP_MESSAGE_SERVER_USER_SYSTEM_PROPERTY = "topicIndex.stompMessageServerUser";
	/**
	 * The system property that defines the STOMP message server password
	 */
	public static final String STOMP_MESSAGE_SERVER_PASS_SYSTEM_PROPERTY = "topicIndex.stompMessageServerPass";
	/**
	 * The system property that defines the STOMP message queue that a service
	 * should listen to
	 */
	public static final String STOMP_MESSAGE_SERVER_QUEUE_SYSTEM_PROPERTY = "topicIndex.stompMessageServerQueue";
	/**
	 * The system property that defines the Skynet REST Server
	 */
	public static final String SKYNET_SERVER_SYSTEM_PROPERTY = "topicIndex.skynetServer";
	/**
	 * The system property that identifies the zanata server to send files to
	 * for translation
	 */
	public static final String ZANATA_SERVER_PROPERTY = "topicIndex.zanataServer";

	/**
	 * The system property that identifies the zanata project name
	 */
	public static final String ZANATA_PROJECT_PROPERTY = "topicIndex.zanataProject";

	/**
	 * The system property that identifies the zanata user name
	 */
	public static final String ZANATA_USERNAME_PROPERTY = "topicIndex.zanataUsername";

	/**
	 * The system property that identifies the zanata project version
	 */
	public static final String ZANATA_PROJECT_VERSION_PROPERTY = "topicIndex.zanataProjectVersion";

	/**
	 * The system property that identifies the zanata API token
	 */
	public static final String ZANATA_TOKEN_PROPERTY = "topicIndex.zanataToken";
	/**
	 * The ZIP file MIME type
	 */
	public static final String ZIP_MIME_TYPE = "application/zip";
	
	/** The encoding of the XML, used when converting a DOM object to a string */
	public static final String XML_ENCODING = "UTF-8";
	
	/**
	 * A collection of the all the locales supported by Java.
	 * Replace all the Underscores (_) to match the normal locale
	 * representation.
	 */
	public static final List<String> LOCALES = CollectionUtilities.replaceStrings(CollectionUtilities.sortAndReturn(CollectionUtilities.toStringArrayList((Object[]) SimpleDateFormat.getAvailableLocales())), "_", "-");

	/**
	 * The URL of the main web Skynet instance. This is used when generating
	 * links in the documentation.
	 */
	public static final String SERVER_URL = "http://skynet.cloud.lab.eng.bne.redhat.com:8080";

	/** The live Skynet URL */
	public static final String FULL_SERVER_URL = "http://skynet.cloud.lab.eng.bne.redhat.com:8080/TopicIndex";
}
