package com.redhat.contentspec.enums;

import com.redhat.contentspec.constants.CSConstants;

/**
 * A Enumerator to describe the levels types available for a level. It also contains the ability to get an Unique ID and title for the LevelType's.
 * 
 * @author lnewson
 *
 */
public enum LevelType
{
	BASE(CSConstants.LEVEL_BASE), CHAPTER(CSConstants.LEVEL_CHAPTER), APPENDIX(CSConstants.LEVEL_APPENDIX), PROCESS(CSConstants.LEVEL_PROCESS), SECTION(CSConstants.LEVEL_SECTION),
	PART(CSConstants.LEVEL_PART);
	
	private final int type;
	
	LevelType(final int id)
	{
		this.type = id;
	}
	
	/**
	 * Get the unique ID for the Enumerator.
	 * 
	 * @return The unique ID.
	 */
	public int getId()
	{
		return type;
	}
	
	/**
	 * Get the title for the Enumerator.
	 * 
	 * @return The title for the Enumerator.
	 */
	public String getTitle()
	{
		switch (this.getId())
		{
		case CSConstants.LEVEL_CHAPTER:
			return CSConstants.CHAPTER;
		case CSConstants.LEVEL_SECTION:
			return CSConstants.SECTION;
		case CSConstants.LEVEL_APPENDIX:
			return CSConstants.APPENDIX;
		case CSConstants.LEVEL_PROCESS:
			return CSConstants.PROCESS;
		case CSConstants.LEVEL_PART:
			return CSConstants.PART;
		default:
			return null;
		}
	}
}
