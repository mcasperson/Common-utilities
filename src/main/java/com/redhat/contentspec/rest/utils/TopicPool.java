package com.redhat.contentspec.rest.utils;

import java.util.ArrayList;

import javax.ws.rs.core.PathSegment;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.specimpl.PathSegmentImpl;

import com.redhat.contentspec.SpecTopic;
import com.redhat.contentspec.constants.CSConstants;
import com.redhat.ecs.commonutils.CollectionUtilities;
import com.redhat.ecs.commonutils.ExceptionUtilities;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.collections.RESTTopicCollectionV1;
import com.redhat.topicindex.rest.entities.ComponentBaseRESTEntityWithPropertiesV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTBaseTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTTopicV1;
import com.redhat.topicindex.rest.exceptions.InternalProcessingException;
import com.redhat.topicindex.rest.exceptions.InvalidParameterException;
import com.redhat.topicindex.rest.expand.ExpandDataDetails;
import com.redhat.topicindex.rest.expand.ExpandDataTrunk;
import com.redhat.topicindex.rest.sharedinterface.RESTInterfaceV1;

/**
 * A fairly simple container class to hold a set of topics that need to be updated or created
 * using the REST API. By using the topic pool it allows for all the topics to be updated or created
 * in one REST call and therefore one transaction.
 * 
 * @author lnewson
 *
 * @param <T> The Topic Type eg RESTTranslatedTopicV1 or RESTTopicV1
 * @param <U> The Topics Collection Type eg RESTTranslatedTopicCollectionV1 or RESTTopicCollectionV1
 */
public class TopicPool<T extends RESTBaseTopicV1<T, U>, U extends BaseRestCollectionV1<T, U>>
{

	private static final Logger log = Logger.getLogger(TopicPool.class);

	private RESTTopicCollectionV1 newTopicPool = new RESTTopicCollectionV1();
	private RESTTopicCollectionV1 updatedTopicPool = new RESTTopicCollectionV1();
	private final RESTInterfaceV1 client;
	private final ObjectMapper mapper = new ObjectMapper();
	private boolean initialised = false;

	public TopicPool(final RESTInterfaceV1 client)
	{
		this.client = client;
	}

	/**
	 * Add a topic that is to be created to the topic pool.
	 * 
	 * @param topic The topic to be created.
	 */
	public void addNewTopic(final RESTTopicV1 topic)
	{
		newTopicPool.addItem(topic);
	}

	/**
	 * Add a topic that is to be updated to the topic pool.
	 * 
	 * @param topic The topic to be updated.
	 */
	public void addUpdatedTopic(final RESTTopicV1 topic)
	{
		updatedTopicPool.addItem(topic);
	}

	/**
	 * Saves all the topics in the pool to the database using the REST API.
	 * 
	 * @return True if all the topics in the pool were saved successfully,
	 * otherwise false.
	 */
	public boolean savePool()
	{
		if ((newTopicPool.getItems() == null || newTopicPool.getItems().isEmpty()) && (updatedTopicPool.getItems() == null || updatedTopicPool.getItems().isEmpty()))
			return true;
		try
		{
			/* We need to expand the Properties collection */
			final ExpandDataTrunk expand = new ExpandDataTrunk();
			final ExpandDataTrunk expandTopic = new ExpandDataTrunk(new ExpandDataDetails("topics"));
			expandTopic.setBranches(CollectionUtilities.toArrayList(new ExpandDataTrunk(new ExpandDataDetails("tags")), new ExpandDataTrunk(new ExpandDataDetails("sourceUrls")), new ExpandDataTrunk(new ExpandDataDetails("properties")), new ExpandDataTrunk(new ExpandDataDetails("outgoingRelationships")),
					new ExpandDataTrunk(new ExpandDataDetails("incomingRelationships"))));
			expand.setBranches(CollectionUtilities.toArrayList(expandTopic));

			final String expandString = mapper.writeValueAsString(expand);
			//final String expandEncodedString = URLEncoder.encode(expandString, "UTF-8");

			// Save the new topics
			if (!(newTopicPool.getItems() == null || newTopicPool.getItems().isEmpty()))
			{
				final RESTTopicCollectionV1 response = client.createJSONTopics(expandString, newTopicPool);
				// Check that the response isn't empty (ie failed)
				if (response == null)
					return false;
				if (response.getItems() == null)
					return false;
				// The response is valid so set it as the pool
				newTopicPool = response;
			}

			// Update the existing topics
			if (!(updatedTopicPool.getItems() == null || updatedTopicPool.getItems().isEmpty()))
			{
				final RESTTopicCollectionV1 response = client.updateJSONTopics(expandString, updatedTopicPool);
				// Check that the response isn't empty (ie failed)
				if (response == null)
					return false;
				if (response.getItems() == null)
					return false;
				// The response is valid so set it as the pool
				updatedTopicPool = response;
			}
			initialised = true;
			return true;
		}
		catch (Exception e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
			return false;
		}
	}

	/**
	 * Initialises a content spec topic using the REST topics that exist
	 * within this pool. The topic pool must be saved and initialised before
	 * this call will work.
	 * 
	 * @param specTopic
	 * @return
	 */
	public SpecTopic initialiseFromPool(final SpecTopic specTopic)
	{
		if (initialised)
		{
			if (newTopicPool.getItems() != null && !newTopicPool.getItems().isEmpty())
			{
				for (final RESTTopicV1 topic : newTopicPool.getItems())
				{
					if (ComponentBaseRESTEntityWithPropertiesV1.returnProperty(topic, CSConstants.CSP_PROPERTY_ID) != null)
					{
						if (ComponentBaseRESTEntityWithPropertiesV1.returnProperty(topic, CSConstants.CSP_PROPERTY_ID).getValue().equals(Integer.toString(specTopic.getLineNumber())))
						{
							specTopic.setDBId(topic.getId());
							return specTopic;
						}
					}
				}
			}
			if (updatedTopicPool.getItems() != null && !updatedTopicPool.getItems().isEmpty())
			{
				for (final RESTTopicV1 topic : updatedTopicPool.getItems())
				{
					if (ComponentBaseRESTEntityWithPropertiesV1.returnProperty(topic, CSConstants.CSP_PROPERTY_ID) != null)
					{
						if (ComponentBaseRESTEntityWithPropertiesV1.returnProperty(topic, CSConstants.CSP_PROPERTY_ID).getValue().equals(Integer.toString(specTopic.getLineNumber())))
						{
							specTopic.setDBId(topic.getId());
							return specTopic;
						}
					}
				}
			}
		}
		return specTopic;
	}

	/**
	 * Checks to see if the topic pool has been saved and initialised against
	 * the REST Interface.
	 * 
	 * @return True if the topics have been saved and initialised.
	 */
	public boolean isInitialised()
	{
		return initialised;
	}

	/**
	 * Checks to see if the topic pool is empty.
	 * 
	 * @return True if the pool is empty otherwise false.
	 */
	public boolean isEmpty()
	{
		return newTopicPool.getItems() == null ? true : newTopicPool.getItems().isEmpty();
	}

	/**
	 * Rolls back any new topics that were created. Since existing topics are stored 
	 * in revision data when edited we can't roll back that data properly.
	 */
	@SuppressWarnings("serial")
	public void rollbackPool()
	{
		if (newTopicPool.getItems() == null || newTopicPool.getItems().isEmpty())
			return;
		final PathSegment path = new PathSegmentImpl("ids", false);
		for (final RESTTopicV1 topic : newTopicPool.getItems())
		{
			path.getMatrixParameters().put(topic.getId().toString(), new ArrayList<String>()
			{
				{
					add("");
				}
			});
		}
		try
		{
			client.deleteJSONTopics(path, "");
			initialised = false;
		}
		catch (InvalidParameterException e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		catch (InternalProcessingException e)
		{
			log.error(ExceptionUtilities.getStackTrace(e));
		}

	}

}
