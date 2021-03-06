package com.redhat.contentspec;

import com.redhat.contentspec.enums.LevelType;

/**
 * A class that is used to represent a Section in a book or Content Specification. It can contain other Levels (Sections or Topics) or Nodes (Comments).
 * 
 * @author lnewson
 * 
 */
public class Section extends Level
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
	public Section(final String title, final int lineNumber, final String specLine)
	{
		super(title, lineNumber, specLine, LevelType.SECTION);
	}

	/**
	 * Constructor
	 * 
	 * @param title
	 *            The title of the Section.
	 */
	public Section(final String title)
	{
		super(title, LevelType.SECTION);
	}
}
