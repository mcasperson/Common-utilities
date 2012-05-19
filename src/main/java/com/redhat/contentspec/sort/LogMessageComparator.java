package com.redhat.contentspec.sort;

import java.util.Comparator;

import com.redhat.contentspec.utils.logging.LogMessage;


/**
 * Used to compare if two LogMessages are different based on their timestamps.
 */
@SuppressWarnings("rawtypes")
public class LogMessageComparator implements Comparator {

	public int compare(Object arg1, Object arg2) {
		LogMessage msg1 = (LogMessage)arg1;
		LogMessage msg2 = (LogMessage)arg2;
		if (msg1.getTimestamp() < msg2.getTimestamp()) {
			return -1;
		} else if (msg1.getTimestamp() > msg2.getTimestamp()) {
			return 1;
		}
		return 0;
	}
}
