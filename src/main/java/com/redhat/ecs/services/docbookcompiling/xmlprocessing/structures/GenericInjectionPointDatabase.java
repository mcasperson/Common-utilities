package com.redhat.ecs.services.docbookcompiling.xmlprocessing.structures;

import java.util.ArrayList;
import java.util.List;

import com.redhat.ecs.commonstructures.Pair;
import com.redhat.topicindex.rest.entities.TopicV1;

/**
 * Provides a way to manage a collection of GenericInjectionPoint objects.
 */
public class GenericInjectionPointDatabase
{
	private List<GenericInjectionPoint> injectionPoints = new ArrayList<GenericInjectionPoint>();
	
	public GenericInjectionPoint getInjectionPoint(final Pair<Integer, String> tagDetails)
	{
		return getInjectionPoint(tagDetails.getFirst());
	}
	
	public GenericInjectionPoint getInjectionPoint(final Integer tagId)
	{
		for (final GenericInjectionPoint genericInjectionPoint : injectionPoints)
		{
			if (genericInjectionPoint.getCategoryIDAndName().getFirst().equals(tagId))
				return genericInjectionPoint;
		}
		
		return null;
	}
	
	public void addInjectionTopic(final Pair<Integer, String> tagDetails, final TopicV1 topic)
	{
		GenericInjectionPoint genericInjectionPoint = getInjectionPoint(tagDetails);
		if (genericInjectionPoint == null)
		{
			genericInjectionPoint = new GenericInjectionPoint(tagDetails);
			injectionPoints.add(genericInjectionPoint);
		}
		genericInjectionPoint.addTopic(topic);
	}

	public List<GenericInjectionPoint> getInjectionPoints()
	{
		return injectionPoints;
	}

	public void setInjectionPoints(List<GenericInjectionPoint> injectionPoints)
	{
		this.injectionPoints = injectionPoints;
	}
}
