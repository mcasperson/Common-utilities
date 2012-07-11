package com.redhat.contentspec.sort;

import java.util.Comparator;

import com.redhat.contentspec.utils.logging.LogMessage;


/**
 * Used to compare if two LogMessages are different based on their timestamps.
 */
public class LogMessageComparator implements Comparator<LogMessage>
{

	public int compare(final LogMessage msg1, final LogMessage msg2)
	{
		if (msg1.getTimestamp() < msg2.getTimestamp())
		{
			return -1;
		}
		else if (msg1.getTimestamp() > msg2.getTimestamp())
		{
			return 1;
		}
		return 0;
	}
}
