package com.redhat.contentspec;

import com.redhat.contentspec.enums.LevelType;

/**
 * A class that is used to represent an Appendix in a book or Content Specification.  It can contain other Levels (Sections or Topics)
 * or Nodes (Comments).
 * 
 * @author lnewson
 *
 */
public class Appendix extends Level {

	/**
	 * Constructor
	 * 
	 * @param title The title of the Appendix.
	 * @param specLine The Content Specification Line that is used to create the Appendix.
	 * @param lineNumber The Line Number of Appendix in the Content Specification.
	 */
	public Appendix(String title, int lineNumber, String specLine) {
		super(title, lineNumber, specLine, LevelType.APPENDIX);
	}
	
	/**
	 * Constructor
	 * 
	 * @param title The title of the Appendix.
	 */
	public Appendix(String title) {
		super(title, LevelType.APPENDIX);
	}

}
