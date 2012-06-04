package com.redhat.topicindex.rest.sort;

import java.util.Comparator;

import com.redhat.topicindex.rest.entities.interfaces.RESTTagV1;

public class TagV1NameComparator implements Comparator<RESTTagV1>
{
	public int compare(final RESTTagV1 o1, final RESTTagV1 o2) 
	{
		if (o1 == null && o2 == null)
			return 0;
		if (o1 == null)
			return -1;
		if (o2 == null)
			return 1;
		
		if (o1.getName() == null && o2.getName() == null)
			return 0;
		if (o1.getName() == null)
			return -1;
		if (o2.getName() == null)
			return 1;
		
		return o1.getName().compareTo(o2.getName());
	}

}