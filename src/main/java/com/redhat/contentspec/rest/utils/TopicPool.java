package com.redhat.contentspec.rest.utils;

import java.net.URLEncoder;
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
import com.redhat.topicindex.rest.entities.interfaces.ITopicV1;
import com.redhat.topicindex.rest.exceptions.InternalProcessingException;
import com.redhat.topicindex.rest.exceptions.InvalidParameterException;
import com.redhat.topicindex.rest.expand.ExpandDataDetails;
import com.redhat.topicindex.rest.expand.ExpandDataTrunk;
import com.redhat.topicindex.rest.sharedinterface.RESTInterfaceV1;

public class TopicPool {
	
	private static final Logger log = Logger.getLogger(TopicPool.class);

	private BaseRestCollectionV1<ITopicV1> newTopicPool = new BaseRestCollectionV1<ITopicV1>();
	private BaseRestCollectionV1<ITopicV1> updatedTopicPool = new BaseRestCollectionV1<ITopicV1>();
	private final RESTInterfaceV1 client;
	private final ObjectMapper mapper = new ObjectMapper();
	private boolean initialised = false;
	
	public TopicPool(RESTInterfaceV1 client) {
		this.client = client;
	}
	
	public void addNewTopic(final ITopicV1 topic) {
		newTopicPool.addItem(topic);
	}
	
	public void addUpdatedTopic(final ITopicV1 topic) {
		updatedTopicPool.addItem(topic);
	}
	
	public boolean savePool() {
		if ((newTopicPool.getItems() == null || newTopicPool.getItems().isEmpty()) && (updatedTopicPool.getItems() == null || updatedTopicPool.getItems().isEmpty())) return true;
		try {
			/* We need to expand the Properties collection */
			final ExpandDataTrunk expand = new ExpandDataTrunk();
			final ExpandDataTrunk expandTopic = new ExpandDataTrunk(new ExpandDataDetails("topics"));
			expandTopic.setBranches(CollectionUtilities.toArrayList(new ExpandDataTrunk(new ExpandDataDetails("tags")), new ExpandDataTrunk(new ExpandDataDetails("sourceUrls")), 
					new ExpandDataTrunk(new ExpandDataDetails("properties")), new ExpandDataTrunk(new ExpandDataDetails("outgoingRelationships")),
					new ExpandDataTrunk(new ExpandDataDetails("incomingRelationships"))));
			expand.setBranches(CollectionUtilities.toArrayList(expandTopic));
			
			final String expandString = mapper.writeValueAsString(expand);
			final String expandEncodedString = URLEncoder.encode(expandString, "UTF-8");
			
			// Save the new topics
			if (!(newTopicPool.getItems() == null || newTopicPool.getItems().isEmpty())) {
				BaseRestCollectionV1<ITopicV1> response = client.createJSONTopics(expandEncodedString, newTopicPool);
				// Check that the response isn't empty (ie failed)
				if (response == null) return false;
				if (response.getItems() == null) return false;
				// The response is valid so set it as the pool
				newTopicPool = response;
			}
			
			// Update the existing topics
			if (!(updatedTopicPool.getItems() == null || updatedTopicPool.getItems().isEmpty())) {
				BaseRestCollectionV1<ITopicV1> response = client.updateJSONTopics(expandEncodedString, updatedTopicPool);
				// Check that the response isn't empty (ie failed)
				if (response == null) return false;
				if (response.getItems() == null) return false;
				// The response is valid so set it as the pool
				updatedTopicPool = response;
			}
			initialised = true;
			return true;
		} catch (Exception e) {
			log.error(ExceptionUtilities.getStackTrace(e));
			return false;
		}
	}
	
	public SpecTopic initialiseFromPool(SpecTopic specTopic) {
		if (newTopicPool.getItems() != null && !newTopicPool.getItems().isEmpty()) {
			for (ITopicV1 topic: newTopicPool.getItems()) {
				if (topic.returnProperty(CSConstants.CSP_PROPERTY_ID) != null ) {
					if (topic.returnProperty(CSConstants.CSP_PROPERTY_ID).getValue().equals(Integer.toString(specTopic.getLineNumber()))) {
						specTopic.setDBId(topic.getId());
						return specTopic;
					}
				}
			}
		}
		if (updatedTopicPool.getItems() != null && !updatedTopicPool.getItems().isEmpty()) {
			for (ITopicV1 topic: updatedTopicPool.getItems()) {
				if (topic.returnProperty(CSConstants.CSP_PROPERTY_ID) != null ) {
					if (topic.returnProperty(CSConstants.CSP_PROPERTY_ID).getValue().equals(Integer.toString(specTopic.getLineNumber()))) {
						specTopic.setDBId(topic.getId());
						return specTopic;
					}
				}
			}
		}
		return specTopic;
	}
	
	public boolean isInitialised() {
		return initialised;
	}
	
	public boolean isEmpty() {
		return newTopicPool.getItems() == null ? true : newTopicPool.getItems().isEmpty();
	}

	@SuppressWarnings("serial")
	public void rollbackPool() {
		if (newTopicPool.getItems() == null || newTopicPool.getItems().isEmpty()) return;
		PathSegment path = new PathSegmentImpl("ids", false);
		for (ITopicV1 topic: newTopicPool.getItems()) {
			path.getMatrixParameters().put(   
                    topic.getId().toString(), 
                    new ArrayList<String>(){{add("");}});
		}
		try {
			client.deleteJSONTopics(path, "");
			initialised = false;
		} catch (InvalidParameterException e) {
			log.error(ExceptionUtilities.getStackTrace(e));
		} catch (InternalProcessingException e) {
			log.error(ExceptionUtilities.getStackTrace(e));
		}
		
	}
	
}
