package com.redhat.contentspec;

import com.redhat.contentspec.enums.LevelType;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTBaseTopicV1;

/**
 * A class that is used to represent a Chapter in a book or Content Specification. It can contain other Levels (Sections or Topics)
 * or Nodes (Comments).
 * 
 * @author lnewson
 *
 */
public class Chapter<T extends RESTBaseTopicV1<T, U>, U extends BaseRestCollectionV1<T, U>> extends Level<T, U> {

	/**
	 * Constructor
	 * 
	 * @param title The title of the Chapter.
	 * @param specLine The Content Specification Line that is used to create the Chapter.
	 * @param lineNumber The Line Number of Chapter in the Content Specification.
	 */
	public Chapter(String title, int lineNumber, String specLine) {
		super(title, lineNumber, specLine, LevelType.CHAPTER);
	}
	
	/**
	 * Constructor
	 * 
	 * @param title The title of the Chapter.
	 */
	public Chapter(String title) {
		super(title, LevelType.CHAPTER);
	}

}
