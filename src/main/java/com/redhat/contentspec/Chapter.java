package com.redhat.contentspec;

import com.redhat.contentspec.enums.LevelType;

/**
 * A class that is used to represent a Chapter in a book or Content Specification. It can contain other Levels (Sections or Topics)
 * or Nodes (Comments).
 * 
 * @author lnewson
 *
 */
public class Chapter extends Level
{
	/**
	 * Constructor
	 * 
	 * @param title The title of the Chapter.
	 * @param specLine The Content Specification Line that is used to create the Chapter.
	 * @param lineNumber The Line Number of Chapter in the Content Specification.
	 */
	public Chapter(final String title, final int lineNumber, final String specLine)
	{
		super(title, lineNumber, specLine, LevelType.CHAPTER);
	}
	
	/**
	 * Constructor
	 * 
	 * @param title The title of the Chapter.
	 */
	public Chapter(final String title)
	{
		super(title, LevelType.CHAPTER);
	}

}
