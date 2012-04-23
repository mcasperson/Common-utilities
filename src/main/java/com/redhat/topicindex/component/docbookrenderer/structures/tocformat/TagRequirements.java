package com.redhat.topicindex.component.docbookrenderer.structures.tocformat;

import java.util.ArrayList;
import java.util.List;

import com.redhat.topicindex.rest.entities.TagV1;

/**
 * This class defines the tags that a topic needs to have in order to be
 * displayed in a particular TOC level
 */
public class TagRequirements
{
	/** One of these tags needs to be present */
	private final List<TagV1> matchOneOf = new ArrayList<TagV1>();
	/** All of these tags needs to be present */
	private final List<TagV1> matchAllOf = new ArrayList<TagV1>();

	public List<TagV1> getMatchOneOf()
	{
		return matchOneOf;
	}

	public List<TagV1> getMatchAllOf()
	{
		return matchAllOf;
	}

	public TagRequirements(final List<TagV1> matchAllOf, final List<TagV1> matchOneOf)
	{
		if (matchOneOf != null)
			this.matchOneOf.addAll(matchOneOf);
		if (matchAllOf != null)
			this.matchAllOf.addAll(matchAllOf);
	}

	public TagRequirements(final List<TagV1> matchAllOf, final TagV1 matchOneOf)
	{
		if (matchOneOf != null)
			this.matchOneOf.add(matchOneOf);
		if (matchAllOf != null)
			this.matchAllOf.addAll(matchAllOf);
	}

	public TagRequirements(final TagV1 matchAllOf, final List<TagV1> matchOneOf)
	{
		if (matchOneOf != null)
			this.matchOneOf.addAll(matchOneOf);
		if (matchAllOf != null)
			this.matchAllOf.add(matchAllOf);
	}

	public TagRequirements(final TagV1 matchAllOf, final TagV1 matchOneOf)
	{
		if (matchOneOf != null)
			this.matchOneOf.add(matchOneOf);
		if (matchAllOf != null)
			this.matchAllOf.add(matchAllOf);
	}

	public TagRequirements()
	{

	}

	/**
	 * This method will merge the tag information stored in another
	 * TagRequirements object with the tag information stored in this object.
	 * 
	 * @param other
	 *            the other TagRequirements object to merge with
	 */
	public void merge(final TagRequirements other)
	{
		if (other != null)
		{
			this.matchAllOf.addAll(other.matchAllOf);
			this.matchOneOf.addAll(other.matchOneOf);
		}
	}
	
	public boolean hasRequirements()
	{
		return this.matchAllOf.size() != 0 && this.matchOneOf.size() != 0;
	}
}
