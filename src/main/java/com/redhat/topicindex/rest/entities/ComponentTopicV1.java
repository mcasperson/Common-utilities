package com.redhat.topicindex.rest.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.redhat.ecs.constants.CommonConstants;
import com.redhat.ecs.services.docbookcompiling.DocbookBuilderConstants;
import com.redhat.topicindex.rest.collections.RESTTopicCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTPropertyTagV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTTopicV1;

/**
 * This component contains methods that can be applied against topics
 * @author Matthew Casperson
 */
public class ComponentTopicV1 extends ComponentBaseTopicV1<RESTTopicV1, RESTTopicCollectionV1>
{
	final RESTTopicV1 source;
	
	public ComponentTopicV1(final RESTTopicV1 source)
	{
		super(source);
		this.source = source;
	}
	
	@Override
	public String returnSkynetURL()
	{
		return returnSkynetURL(source);
	}
	
	static public String returnSkynetURL(final RESTTopicV1 source)
	{
		return CommonConstants.SERVER_URL + "/TopicIndex/CustomSearchTopicList.seam?topicIds=" + source.getId();
	}

	/**
	 * @return The value to be saved into the Build ID field of any bugzilla bugs assigned to this topic.
	 */
	@Override
	public String returnBugzillaBuildId()
	{
		return returnBugzillaBuildId(source);
	}
	
	static public String returnBugzillaBuildId(final RESTTopicV1 source)
	{
		final SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.FILTER_DISPLAY_DATE_FORMAT);
		return source.getId() + "-" + source.getRevision() + " " + (source.getLastModified() == null ? formatter.format(source.getLastModified()) : formatter.format(new Date())) + " " + source.getLocale();
	}

	@Override
	public String returnInternalURL()
	{
		return returnInternalURL(source);
	}
	
	static public String returnInternalURL(final RESTTopicV1 source)
	{
		return "Topic.seam?topicTopicId=" + source.getId() + "&selectedTab=Rendered+View";
	}
	
	@Override
	public RESTTopicV1 returnRelatedTopicByID(final Integer id)
	{
		return returnRelatedTopicByID(source, id);
	}
	
	static public RESTTopicV1 returnRelatedTopicByID(final RESTTopicV1 source, final Integer id)
	{
		if (source.getOutgoingRelationships() != null && source.getOutgoingRelationships().getItems() != null)
			for (final RESTTopicV1 topic : source.getOutgoingRelationships().getItems())
				if (topic.getId().equals(id))
					return topic;
		return null;
	}
	
	@Override
	public boolean hasRelationshipTo(final Integer id)
	{
		return hasRelationshipTo(source, id);
	}
	
	static public boolean hasRelationshipTo(final RESTTopicV1 source, final Integer id)
	{
		return returnRelatedTopicByID(source, id) != null;
	}
	
	@Override
	public String returnErrorXRefID()
	{
		return returnErrorXRefID(source);
	}
	
	static public  String returnErrorXRefID(final RESTTopicV1 source)
	{
		return DocbookBuilderConstants.ERROR_XREF_ID_PREFIX + source.getId();
	}

	@Override
	public String returnXRefID()
	{
		return returnXRefID(source);
	}
	
	static public String returnXRefID(final RESTTopicV1 source)
	{
		return "TopicID" + source.getId();
	}
	

	@Override
	public String returnXrefPropertyOrId(final Integer propertyTagId)
	{
		return returnXrefPropertyOrId(source, propertyTagId);
	}
	
	static public String returnXrefPropertyOrId(final RESTTopicV1 source, final Integer propertyTagId)
	{
		final RESTPropertyTagV1 propTag = returnProperty(source, propertyTagId);
		if (propTag != null)
		{
			return propTag.getValue();
		}
		else
		{
			return returnXRefID(source);
		}
	}
	
	@Override
	public String returnEditorURL()
	{
		return returnEditorURL(source);
	}
	
	static public String returnEditorURL(final RESTTopicV1 source)
	{
		return CommonConstants.SERVER_URL + "/TopicIndex/TopicEdit.seam?topicTopicId=" + source.getId();
	}
}
