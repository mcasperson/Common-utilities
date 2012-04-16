package com.redhat.ecs.internalsort;

import java.util.Comparator;

import com.redhat.ecs.internaltructures.EntitySubstitutionBoundaryData;

public class EntitySubstitutionBoundaryDataBoundaryStartSort implements Comparator<EntitySubstitutionBoundaryData>
{
	public int compare(final EntitySubstitutionBoundaryData o1, final EntitySubstitutionBoundaryData o2)
	{
		if (o1 == null && o2 == null)
			return 0;
		if (o1 == null)
			return -1;
		if (o2 == null)
			return 1;
		
		if (o1.getBoundary() == null && o2.getBoundary() == null)
			return 0;
		if (o1.getBoundary() == null)
			return -1;
		if (o2.getBoundary() == null)
			return 1;
		
		if (o1.getBoundary().getFirst() == null && o2.getBoundary().getFirst() == null)
			return 0;
		if (o1.getBoundary().getFirst() == null)
			return -1;
		if (o2.getBoundary().getFirst() == null)
			return 1;
		
		return o1.getBoundary().getFirst().compareTo(o2.getBoundary().getFirst());
	}

}
