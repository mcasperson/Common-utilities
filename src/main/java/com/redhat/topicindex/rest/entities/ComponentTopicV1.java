package com.redhat.topicindex.rest.entities;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.redhat.ecs.constants.CommonConstants;
import com.redhat.topicindex.rest.entities.interfaces.RESTTopicV1;

/**
 * This component contains methods that can be applied against topics
 * @author Matthew Casperson
 */
public class ComponentTopicV1 extends ComponentBaseTopicV1<RESTTopicV1>
{
	final RESTTopicV1 source;
	
	public ComponentTopicV1(final RESTTopicV1 source)
	{
		super(source);
		this.source = source;
	}
	
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
	public String returnBugzillaBuildId()
	{
		return returnBugzillaBuildId(source);
	}
	
	static public String returnBugzillaBuildId(final RESTTopicV1 source)
	{
		final SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.FILTER_DISPLAY_DATE_FORMAT);
		return source.getId() + "-" + source.getRevision() + " " + (source.getLastModified() == null ? formatter.format(source.getLastModified()) : formatter.format(new Date())) + " " + source.getLocale();
	}


}
