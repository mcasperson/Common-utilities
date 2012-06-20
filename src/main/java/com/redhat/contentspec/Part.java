package com.redhat.contentspec;

import com.redhat.contentspec.enums.LevelType;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTBaseTopicV1;

public class Part<T extends RESTBaseTopicV1<T, U>, U extends BaseRestCollectionV1<T, U>> extends Level<T, U>
{

	/**
	 * Constructor
	 * 
	 * @param title
	 *            The title of the Part.
	 * @param specLine
	 *            The Content Specification Line that is used to create the Part.
	 * @param lineNumber
	 *            The Line Number of Part in the Content Specification.
	 */
	public Part(String title, int lineNumber, String specLine)
	{
		super(title, lineNumber, specLine, LevelType.PART);
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 *            The title of the Part.
	 */
	public Part(String title)
	{
		super(title, LevelType.PART);
	}

}
