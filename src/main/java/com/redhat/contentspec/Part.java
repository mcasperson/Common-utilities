package com.redhat.contentspec;

import com.redhat.contentspec.enums.LevelType;

public class Part extends Level {

	/**
	 * Constructor
	 * 
	 * @param title The title of the Part.
	 * @param specLine The Content Specification Line that is used to create the Part.
	 * @param lineNumber The Line Number of Part in the Content Specification.
	 */
	public Part(String title, int lineNumber, String specLine) {
		super(title, lineNumber, specLine, LevelType.PART);
	}
	
	/**
	 * Constructor
	 * 
	 * @param title The title of the Part.
	 */
	public Part(String title) {
		super(title, LevelType.PART);
	}

}
