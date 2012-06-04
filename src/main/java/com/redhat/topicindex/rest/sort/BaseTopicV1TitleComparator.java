package com.redhat.topicindex.rest.sort;

import java.util.Comparator;

import com.redhat.topicindex.rest.entities.interfaces.RESTBaseTopicV1;

public class BaseTopicV1TitleComparator<T extends RESTBaseTopicV1<T>> implements Comparator<T>
{
	public int compare(final T o1, final T o2)
	{
		if (o1 == null && o2 == null)
			return 0;
		if (o1 == null)
			return -1;
		if (o2 == null)
			return 1;
		
		if (o1.getTitle() == null && o2.getTitle() == null)
			return 0;
		if (o1.getTitle() == null)
			return -1;
		if (o2.getTitle() == null)
			return 1;
		
		return o1.getTitle().compareTo(o2.getTitle());
	}
}
