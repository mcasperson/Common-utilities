package com.redhat.contentspec;

import com.redhat.contentspec.enums.LevelType;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTBaseTopicV1;

/**
 * A class that is used to represent a Section in a book or Content Specification. It can contain other Levels (Sections or Topics) or Nodes (Comments).
 * 
 * @author lnewson
 * 
 */
public class Section<T extends RESTBaseTopicV1<T, U>, U extends BaseRestCollectionV1<T, U>> extends Level<T, U>
{

	/**
	 * Constructor
	 * 
	 * @param title
	 *            The title of the Section.
	 * @param specLine
	 *            The Content Specification Line that is used to create the Section.
	 * @param lineNumber
	 *            The Line Number of Section in the Content Specification.
	 */
	public Section(String title, int lineNumber, String specLine)
	{
		super(title, lineNumber, specLine, LevelType.SECTION);
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 *            The title of the Section.
	 */
	public Section(String title)
	{
		super(title, LevelType.SECTION);
	}
}
