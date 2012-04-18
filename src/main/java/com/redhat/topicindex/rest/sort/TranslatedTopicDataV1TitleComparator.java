package com.redhat.topicindex.rest.sort;

import java.util.Comparator;

import com.redhat.topicindex.rest.entities.TranslatedTopicDataV1;

public class TranslatedTopicDataV1TitleComparator implements Comparator<TranslatedTopicDataV1>
{
	public int compare(final TranslatedTopicDataV1 o1, final TranslatedTopicDataV1 o2)
	{
		if (o1 == null && o2 == null)
			return 0;
		if (o1 == null)
			return -1;
		if (o2 == null)
			return 1;
		
		if (o1.getTranslatedTopicTitle() == null && o2.getTranslatedTopicTitle() == null)
			return 0;
		if (o1.getTranslatedTopicTitle() == null)
			return -1;
		if (o2.getTranslatedTopicTitle() == null)
			return 1;
		
		return o1.getTranslatedTopicTitle().compareTo(o2.getTranslatedTopicTitle());
	}
}
