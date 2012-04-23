package com.redhat.topicindex.component.docbookrenderer.structures.tocformat;

import java.util.ArrayList;
import java.util.List;

import com.redhat.topicindex.rest.entities.TagV1;

public class TagRequirements
{
	private final List<TagV1> matchOneOf = new ArrayList<TagV1>();
	private final List<TagV1> matchAllOf = new ArrayList<TagV1>();

	public List<TagV1> getMatchOneOf()
	{
		return matchOneOf;
	}

	public List<TagV1> getMatchAllOf()
	{
		return matchAllOf;
	}
	
	public TagRequirements(final List<TagV1> matchOneOf, final List<TagV1> matchAllOf)
	{
		if (matchOneOf != null)
			this.matchOneOf.addAll(matchOneOf);
		if (matchAllOf != null)			
			this.matchAllOf.addAll(matchAllOf);
	}
	
	public TagRequirements(final TagV1 matchOneOf, final List<TagV1> matchAllOf)
	{
		if (matchOneOf != null)			
			this.matchOneOf.add(matchOneOf);
		if (matchAllOf != null)			
			this.matchAllOf.addAll(matchAllOf);
	}
	
	public TagRequirements(final List<TagV1> matchOneOf, final TagV1 matchAllOf)
	{
		if (matchOneOf != null)			
			this.matchOneOf.addAll(matchOneOf);
		if (matchAllOf != null)			
			this.matchAllOf.add(matchAllOf);
	}
	
	public TagRequirements(final TagV1 matchOneOf, final TagV1 matchAllOf)
	{
		if (matchOneOf != null)			
			this.matchOneOf.add(matchOneOf);
		if (matchAllOf != null)			
			this.matchAllOf.add(matchAllOf);
	}
	
	public TagRequirements()
	{
		
	}

	public void merge(final TagRequirements other)
	{
		if (other != null)
		{
			this.matchAllOf.addAll(other.matchAllOf);
			this.matchOneOf.addAll(other.matchOneOf);
		}
	}
}
