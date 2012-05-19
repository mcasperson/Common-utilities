package com.redhat.contentspec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import com.redhat.contentspec.constants.CSConstants;
import com.redhat.contentspec.entities.Relationship;
import com.redhat.contentspec.enums.LevelType;
import com.redhat.contentspec.enums.RelationshipType;
import com.redhat.contentspec.rest.RESTReader;
import com.redhat.contentspec.utils.ContentSpecUtilities;
import com.redhat.topicindex.rest.entities.TopicV1;

/**
 * A class that is used to represent and process a "Process" within a Content Specification.
 * 
 * @author lnewson
 *
 */
public class Process extends Level {
	
	private LinkedHashMap<String, SpecTopic> topics = new LinkedHashMap<String, SpecTopic>();
	private boolean topicsProcessed = false;
	private HashMap<String, ArrayList<Relationship>> relationships = new HashMap<String, ArrayList<Relationship>>();
	private HashMap<String, SpecTopic> targets = new HashMap<String, SpecTopic>();
	private HashMap<String, List<String>> branches = new HashMap<String, List<String>>();
	
	/**
	 * Constructor
	 * 
	 * @param title The Title of the Process.
	 * @param lineNumber The Line Number of Level in the Content Specification.
	 * @param specLine The Content Specification Line that is used to create the Process.
	 */
	public Process(String title, int lineNumber, String specLine) {
		super(title, lineNumber, specLine, LevelType.PROCESS);
	}
	
	/**
	 * Constructor
	 * 
	 * @param title The Title of the Process.
	 */
	public Process(String title) {
		super(title, LevelType.PROCESS);
	}
	
	@Override
	public void appendSpecTopic(SpecTopic specTopic) {
		String topicId = specTopic.getId();
		if (topicId.equals("N") || topicId.matches(CSConstants.DUPLICATE_TOPIC_ID_REGEX) || topicId.matches(CSConstants.CLONED_DUPLICATE_TOPIC_ID_REGEX) || topicId.matches(CSConstants.CLONED_TOPIC_ID_REGEX) || topicId.matches(CSConstants.EXISTING_TOPIC_ID_REGEX)) {
			topicId = Integer.toString(specTopic.getLineNumber()) + "-" + topicId;
		}
		topics.put(topicId, specTopic);
		nodes.add(specTopic);
		specTopic.setParent(this);
	}
	
	@Override
	public void removeSpecTopic(SpecTopic specTopic) {
		String topicId = specTopic.getId();
		if (topicId.equals("N") || topicId.matches(CSConstants.DUPLICATE_TOPIC_ID_REGEX) || topicId.matches(CSConstants.CLONED_DUPLICATE_TOPIC_ID_REGEX) || topicId.matches(CSConstants.CLONED_TOPIC_ID_REGEX) || topicId.matches(CSConstants.EXISTING_TOPIC_ID_REGEX)) {
			topicId = Integer.toString(specTopic.getLineNumber()) + "-" + topicId;
		}
		topics.remove(topicId);
		nodes.remove(specTopic);
		specTopic.setParent(null);
	}
	
	/**
	 * Adds a list of branches for a Unique Content Specification Topic ID.
	 * 
	 * @param topicId The unique Content Specification Topic ID.
	 * @param branchIds The List of branch IDs to add for the topic
	 */
	public void addBranches(String topicId, List<String> branchIds) {
		if (branches.containsKey(topicId)) {
			if (branches.get(topicId) == null) {
				branches.put(topicId, branchIds);
			} else {
				branches.get(topicId).addAll(branchIds);
			}
		} else {
			branches.put(topicId, branchIds);
		}
	}
	
	/**
	 * Adds a branch for a Unique Content Specification Topic ID.
	 * 
	 * @param topicId The unique Content Specification Topic ID.
	 * @param branchId The ID of the Branch to be added
	 */
	public void addBranch(String topicId, String branchId) {
		List<String> branchIds = new ArrayList<String>();
		branchIds.add(branchId);
		addBranches(topicId, branchIds);
	}
	
	@Override
	public int getNumberOfSpecTopics() {
		return topics.size();
	}
	
	/**
	 * Get the branch root nodes ID's for a for specific Topic and that Topics Target ID
	 * 
	 * @param topicId The ID of the topic to search on.
	 * @param topicTargetId The Target ID for the topic to search on.
	 * 
	 * @return A list of all the branch root node ID's for the specified Topic/Target ID.
	 */
	private List<String> getBranchRootIdsForTopicId(String topicId, String topicTargetId) {
		List<String> branchRootIds = new ArrayList<String>();
		for (String branchRootId: branches.keySet()) {
			for (String branchId: branches.get(branchRootId)) {
				if (topicId.matches("((^[0-9]*-)|(^))" + branchId + "$") || branchId.equals(topicTargetId)) branchRootIds.add(branchRootId);
			}
		}
		return branchRootIds.isEmpty() ? null : branchRootIds;
	}

	/**
	 * Check if the topics in the Process have already been processed.
	 * 
	 * @return True if the topics have been processed otherwise false.
	 */
	public boolean isTopicsProcessed() {
		return topicsProcessed;
	}
	
	/**
	 * Get the relationships that lie within the Process. This will return an empty list until after the topics have been processed.
	 * 
	 * @return A list of relatioships within the Process.
	 */
	public HashMap<String, ArrayList<Relationship>> getProcessRelationships() {
		return relationships;
	}
	
	/**
	 * Get a mapping of the Process targets for each Target within the process. This will return an empty list until after the topics have been processed.
	 * 
	 * @return A mapping of Target IDs to Content Specification Topics that exist within the process.
	 */
	public HashMap<String, SpecTopic> getProcessTargets() {
		return targets;
	}
	
	/**
	 * Gets all of the Content Specification Unique Topic ID's that are used in the process.
	 * 
	 * @return A List of Unique Topic ID's.
	 */
	protected List<String> getTopicIds() {
		LinkedList<String> topicIds = new LinkedList<String>();
		Iterator<Entry<String, SpecTopic>> i = topics.entrySet().iterator();
		while (i.hasNext()) {
			topicIds.add(i.next().getKey());
		}
		return topicIds;
	}
	
	@Override
	public LinkedList<SpecTopic> getSpecTopics() {
		LinkedList<SpecTopic> topicList = new LinkedList<SpecTopic>();
		Iterator<Entry<String, SpecTopic>> i = topics.entrySet().iterator();
		while (i.hasNext()) {
			topicList.add(i.next().getValue());
		}
		return topicList;
	}
	
	/**
	 * Processes a processes topics and creates the targets and relationships
	 * 
	 * @param specTopics A mapping of all the topics in a content specification to their unique ids
	 * @param topicTargets The topic targets that already exist in a content specification
	 * @param reader A DBReader object that is used to access database objects via the REST Interface
	 * @return True if everything loaded successfully otherwise false
	 */
	public boolean processTopics(HashMap<String, SpecTopic> specTopics, HashMap<String, SpecTopic> topicTargets, RESTReader reader) {
		boolean successfullyLoaded = true;
		SpecTopic prevTopic = null;
		String prevTopicTargetId = null;
		int count = 1;
		LinkedList<String> processTopics = new LinkedList<String>(this.getTopicIds());
		for (String topicId: processTopics) {
			String nonUniqueId = topicId.replaceAll("^[0-9]+-", "");
			SpecTopic specTopic = topics.get(topicId);
			
			// If the topic is an existing or cloned topic then use the database information
			if (nonUniqueId.matches(CSConstants.EXISTING_TOPIC_ID_REGEX) || nonUniqueId.matches(CSConstants.CLONED_TOPIC_ID_REGEX) || nonUniqueId.matches(CSConstants.CLONED_DUPLICATE_TOPIC_ID_REGEX)) {
				// Get the topic information from the database
				TopicV1 topic;
				if (nonUniqueId.matches(CSConstants.CLONED_TOPIC_ID_REGEX)) {
					topic = reader.getTopicById(Integer.parseInt(nonUniqueId.substring(1)), null);
				} else if (nonUniqueId.matches(CSConstants.CLONED_DUPLICATE_TOPIC_ID_REGEX)) {
					topic = reader.getTopicById(Integer.parseInt(nonUniqueId.substring(2)), null);
				} else {
					topic = reader.getTopicById(Integer.parseInt(nonUniqueId), null);
				}
				if (topic != null) {
					// Add relationships if the topic is a task
					if (topic.isTaggedWith(CSConstants.TASK_TAG_ID)) {
						String topicTargetId;
						// Create a target if one doesn't already exist
						if (specTopic.getTargetId() == null) {
							// Create a randomly generated target id using the process topic count
							topicTargetId = ContentSpecUtilities.generateRandomTargetId(specTopic.getLineNumber(), count);
							// Check that the topic id doesn't already exist. If it does then keep generating random numbers until a unique one is found
							while (topicTargets.containsKey(topicTargetId)) {
								topicTargetId = ContentSpecUtilities.generateRandomTargetId(specTopic.getLineNumber(), count);
							}
							specTopic.setTargetId(topicTargetId);
							targets.put(topicTargetId, specTopic);
						} else {
							topicTargetId = specTopic.getTargetId();
						}
						createProcessRelationships(prevTopic, topicId, prevTopicTargetId, topicTargetId);
						prevTopicTargetId = topicTargetId;
						
						// Set the current topic as the previous topic
						prevTopic = specTopic;
					}
				} else {
					successfullyLoaded = false;
				}
			} else {
				// Not an existing or cloned topic
				// The Topic is a duplicated topic so get the type from the original topic
				String type = specTopic.getType();
				if (nonUniqueId.matches(CSConstants.DUPLICATE_TOPIC_ID_REGEX)) {
					if (specTopics.get("N" + nonUniqueId.substring(1)) == null) continue;
					type = specTopics.get("N" + nonUniqueId.substring(1)).getType();
				}
				// Add relationships if the topic is a task
				if (type.equals("Task")) {
					String topicTargetId;
					// Create a target if one doesn't already exist
					if (specTopic.getTargetId() == null) {
						// Create a randomly generated target id using the process topic count
						topicTargetId = ContentSpecUtilities.generateRandomTargetId(specTopic.getLineNumber(), count);
						// Check that the topic id doesn't already exist. If it does then keep generating random numbers until a unique one is found
						while (topicTargets.containsKey(topicTargetId)) {
							topicTargetId = ContentSpecUtilities.generateRandomTargetId(specTopic.getLineNumber(), count);
						}
						//targets.put(topicTargetId, topics.get(topicId));
						specTopic.setTargetId(topicTargetId);
						targets.put(topicTargetId, specTopic);
					} else {
						topicTargetId = specTopic.getTargetId();
					}
					createProcessRelationships(prevTopic, topicId, prevTopicTargetId, topicTargetId);
					prevTopicTargetId = topicTargetId;
					
					// Set the current topic as the previous topic
					prevTopic = specTopic;
				}
			}
			count++;
		}
		return successfullyLoaded;
	}
	
	/**
	 * Creates the relationships between topics for the process
	 * 
	 * @param prevTopic The previous topic in the process.
	 * @param topicId The Unique ID of the topic.
	 * @param prevTopicTargetID The Target ID of the previous topic.
	 * @param topicTargetId The Target ID of the topic.
	 * @return True if the relationship was created successfully otherwise false.
	 */
	private boolean createProcessRelationships(SpecTopic prevTopic, String topicId, String prevTopicTargetId, String topicTargetId)
	{
		// Get the id of the parent branching node(s) for this topic for the 
		List<String> branchRootIds = getBranchRootIdsForTopicId(topicId, topicTargetId);
		if (branchRootIds != null)
		{
			for (String branchRootId: branchRootIds)
			{
				// Add this topic to the previous topics next relationship
				int count = 0;
				SpecTopic relatedTopic = null;
				
				// Get the parent topic and count if more then one is found
				for (String specTopicId: topics.keySet())
				{
					if (specTopicId.matches("((^[0-9]*-)|(^))" + branchRootId + "$"))
					{
						relatedTopic = topics.get(specTopicId);
						count++;
					}
				}
				
				// If more then one topic is found the branch isn't valid
				if (count > 1)
				{
					return false;
				}
				// If one topic is found then add the relationships
				else if (count == 1)
				{
					if (!(relatedTopic.getId().matches(CSConstants.NEW_TOPIC_ID_REGEX) && !relatedTopic.getId().equals("N")))
					{
						branchRootId = relatedTopic.getLineNumber() + "-" + relatedTopic.getId();
					}
					
					// Create the ArrayList for the topic if one doesn't exist
					if (!relationships.containsKey(topicId)) relationships.put(topicId, new ArrayList<Relationship>());
					if (!relationships.containsKey(branchRootId)) relationships.put(branchRootId, new ArrayList<Relationship>());
					
					// Create the relationship and add it to the parent topic
					Relationship nextRelationship = new Relationship(branchRootId, topicTargetId, RelationshipType.NEXT);
					relationships.get(branchRootId).add(nextRelationship);
					
					// If the previous topic is the parent topic and this topic only has one parent branch then add a previous link
					if (branchRootIds.size() == 1 && prevTopicTargetId != null && prevTopic == relatedTopic)
					{
						// Create the unique Id's that are used when processing
						String uniquePrevTopicId = prevTopic.getId();
						if (!(prevTopic.getId().matches(CSConstants.NEW_TOPIC_ID_REGEX) && !prevTopic.getId().equals("N")))
						{
							uniquePrevTopicId = prevTopic.getLineNumber() + "-" + prevTopic.getId();
						}
						
						// Create the ArrayList for the previous topic if one doesn't exist
						if (!relationships.containsKey(uniquePrevTopicId)) relationships.put(uniquePrevTopicId, new ArrayList<Relationship>());
					
						// Add the previous relationship for this topic
						Relationship prevRelationship = new Relationship(topicId, prevTopicTargetId, RelationshipType.PREVIOUS);
						relationships.get(topicId).add(prevRelationship);
					}
				}
			}
		}
		else
		{
			if (prevTopicTargetId != null) 
			{
				// Create the unique Id's that are used when processing
				String uniquePrevTopicId = prevTopic.getId();
				if (!(prevTopic.getId().matches(CSConstants.NEW_TOPIC_ID_REGEX) && !prevTopic.getId().equals("N"))) {
					uniquePrevTopicId = prevTopic.getLineNumber() + "-" + prevTopic.getId();
				}
				// Create the ArrayList for the topic if one doesn't exist
				if (!relationships.containsKey(topicId)) relationships.put(topicId, new ArrayList<Relationship>());
				if (!relationships.containsKey(uniquePrevTopicId)) relationships.put(uniquePrevTopicId, new ArrayList<Relationship>());
			
				// Add the previous relationship for this topic
				Relationship prevRelationship = new Relationship(topicId, prevTopicTargetId, RelationshipType.PREVIOUS);
				relationships.get(topicId).add(prevRelationship);
			
				Relationship nextRelationship = new Relationship(uniquePrevTopicId, topicTargetId, RelationshipType.NEXT);
				relationships.get(uniquePrevTopicId).add(nextRelationship);
			}
		}
		return true;
	}
	
	@Override
	public String toString()
	{
		if (hasSpecTopics())
		{
			String spacer = "";
			for (int i = 1; i < getColumn(); i++) {
				spacer += "  ";
			}
			String output = spacer + getText() + "\n";
			
			for (Node node: nodes) {
				output += node.toString();
			}
			return output;
		}
		else
		{
			return "";
		}
	}
}
