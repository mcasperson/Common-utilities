package com.redhat.contentspec;

import java.util.ArrayList;
import java.util.List;

import com.redhat.ecs.commonutils.StringUtilities;

/**
 * An abstract class that contains the base objects required for a Content Specification Node.
 * 
 * @author lnewson
 *
 */
public abstract class SpecNode extends Node {

	protected List<String> tags = new ArrayList<String>();
	protected List<String> removeTags = new ArrayList<String>();
	protected List<String> sourceUrls = new ArrayList<String>();
	protected String description = null;
	protected String assignedWriter = null;
	
	
	public SpecNode(int lineNumber, String text) {
		super(lineNumber, text);
	}
	
	public SpecNode(String text) {
		super(text);
	}
	
	public SpecNode() {
		super();
	}
	
	/**
	 * Gets the line number that the node is on in a Content Specification.
	 * 
	 * @return The Line Number for the node.
	 */
	public int getLineNumber() {
		return lineNumber;
	}
	
	/**
	 * Gets the text for the node's line.
	 * 
	 * @return The line of text for the node.
	 */
	public String getText() {
		return text;
	}
	
	/**
	 * Sets the text for the node.
	 * 
	 * @param text The nodes text.
	 */
	protected void setText(String text) {
		this.text = text;
	}
	
	/**
	 * Gets the step number of the node in the Content Specification.
	 * 
	 * @return The Step of the node.
	 */
	public abstract Integer getStep();
	
	/**
	 * Gets the column the node starts at.
	 * 
	 * @return The column the node starts at.
	 */
	public Integer getColumn() {
		return parent == null ? 0 : (parent.getColumn() + 1);
	}
	
	/**
	 * Get the parent of the node.
	 * 
	 * @return The nodes parent.
	 */
	public SpecNode getParent() {
		return (SpecNode) parent;
	}
	
	/**
	 * Sets the nodes parent.
	 * 
	 * @param parent The parent node.
	 */
	protected void setParent(SpecNode parent) {
		super.setParent(parent);
	}	
	
	/**
	 * Sets the description for a node.
	 * 
	 * @param desc The description.
	 */
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	/**
	 * Get the description for a node. If useInherited is true then it will check for an inherited description as well.
	 * 
	 * @param useInherited If the function should check for an inherited description
	 * @return The description as a String
	 */
	public String getDescription(boolean useInherited) {
		if  (description == null && parent != null && useInherited)
			return getParent().getDescription(true);
		return description;
	}
	
	/**
	 * Sets the Assigned Writer for this set of options
	 * 
	 * @param writer The writers name that matches to the assigned writer tag in the database
	 */
	public void setAssignedWriter(String writer) {
		this.assignedWriter = writer;
	}
	
	/**
	 * Get the Assigned Writer for a topic. If useInherited is true then it will check for an inherited writer as well.
	 * 
	 * @param useInherited If the function should check for an inherited writer
	 * @return The Assigned Writers name as a String
	 */
	public String getAssignedWriter(boolean useInherited) {
		if  (assignedWriter == null && parent != null && useInherited)
			return getParent().getAssignedWriter(true);
		return assignedWriter;
	}
	
	/**
	 * Sets the set of tags for this set of options
	 * 
	 * @param tags A HashMap of tags. The key in the map is the tags category name and the value is an ArrayList of tags for each category.
	 */
	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	/**
	 * Gets the set of tags for this set of options. If useInherited is true then it will check for inherited options as well.
	 * 
	 * This function also removes the tags from the HashMap for any tag that has a - in front of its name.
	 * 
	 * @param useInherited If the function should check for inherited tags
	 */
	public List<String> getTags(boolean useInherited) {
		List<String> temp = tags;
		// Get the inherited tags
		if (useInherited) {
			if  (tags == null && parent != null) {
				return getParent().getTags(true);
			} else if (parent != null) {
				for (String tagName: getParent().getTags(true)) {
					boolean found = false;
					for (String tempTagName : temp) {
						if (tagName.equals(tempTagName)) {
							found = true;
						}	
					}
					if (!found) {
						temp.add(tagName);
					}
				}
			}
		}
		// Remove the tags that are set to be removed
		List<String> newTags = new ArrayList<String>();
		for (String tagName: tags) {
			List<String> temptags = getRemoveTags(useInherited);
			boolean found = false;
			for (String removeTagName: temptags) {
				if (removeTagName.equals(tagName)) {
					found = true;
				}
			}
			if (!found) {
				newTags.add(tagName);
			}
		}
		temp = newTags;
		return temp;
	}
	
	/**
	 * Sets the list of tags that are to be removed in this set of options
	 * 
	 * @param tags An ArrayList of tags to be removed
	 */
	public void setRemoveTags(List<String> tags) {
		this.removeTags = tags;
	}
	
	/**
	 * Gets an ArrayList of tags that are to be removed for these options. If useInherited is true then it will also add all inherited removeable tags.
	 * 
	 * @param useInherited If the function should check for inherited removable tags
	 * @return An ArrayList of tags
	 */
	public List<String> getRemoveTags(boolean useInherited) {
		List<String> temp = removeTags;
		if (useInherited) {
			if  (removeTags == null && parent != null) {
				return getParent().getRemoveTags(true);
			} else if (parent != null) {
				// Add all of the inherited tags that don't already exist
				List<String> inheritedTags = getParent().getRemoveTags(true);
				for (String tagName: inheritedTags) {
					boolean found = false;
					for (String tempTagName : temp) {
						if (tagName.equals(tempTagName)) {
							found = true;
						}	
					}
					if (!found) {
						temp.add(tagName);
					}
				}
			}
		}
		return temp;
	}
	
	/**
	 * Sets the list of source urls in this node
	 * 
	 * @param sourceUrls An ArrayList of urls
	 */
	public void setSourceUrls(List<String> sourceUrls) {
		this.sourceUrls = sourceUrls;
	}
	
	/**
	 * Get the Source Urls for a node and also checks to make sure the url hasn't already been inherited
	 * 
	 * @return A List of Strings that represent the source urls
	 */
	public List<String> getSourceUrls() {
		List<String> temp = sourceUrls;
		if  (sourceUrls == null && parent != null) {
			return getParent().getSourceUrls();
		} else if (parent != null) {
			for (String url: getParent().getSourceUrls()) {
				if (!temp.contains(url)) {
					temp.add(url);
				}
			}
		}
		return temp;
	}
	
	/**
	 * Adds a tag to the list of tags. If the tag starts with a - then its added to the remove tag list otherwise its added
	 * to the normal tag mapping. Also strips off + & - from the start of tags.
	 * 
	 * @param tagName The name of the Tag to be added.
	 * @return True if the tag was added successfully otherwise false.
	 */
	public boolean addTag(String tagName) {
		String name = StringUtilities.replaceEscapeChars(tagName);
		// Remove the + or - from the tag temporarily to get the tag from the database
		if (tagName.startsWith("-") || tagName.startsWith("+")) {
			name = name.substring(1).trim();
		}
		
		// Check to see which set of tags to add to. The removeTags or additional tags.
		if (tagName.startsWith("-")) {
			if (removeTags.contains(name)) {
				return false;
			} else {
				removeTags.add(name);
			}
		} else {
			if (tags.contains(name)) {
				return false;
			} else {
				tags.add(name);
			}
		}
		return true;
	}
	
	/**
	 * Adds an array of tags to the list of tags for this node
	 * 
	 * @param tagArray A list of tags by name that are to be added.
	 * @return True if all the tags were added successfully otherwise false.
	 */
	public boolean addTags(List<String> tagArray) {
		for (String t: tagArray) {
			return addTag(t);
		}
		return true;
	}
	
	/**
	 * Adds a source URL to the list of URL's for this set of node
	 * 
	 * @param url The URL to be added
	 */
	public void addSourceUrl(String url) {
		if (sourceUrls.contains(url)) return;
		sourceUrls.add(url);
	}
	
	/**
	 * Removes a specific Source URL from the list of URL's
	 * 
	 * @param url The URL to be removed.
	 */
	public void removeSourceUrl(String url) {
		sourceUrls.remove(url);
	}
	
	/**
	 * Gets a string representation of the options in this node. (Tags, Source URL's, Description and Writer)
	 * 
	 * @return The String representation of the options.
	 */
	protected String getOptionsString() {
		ArrayList<String> vars = new ArrayList<String>();
		if (!tags.isEmpty()) {
			vars.addAll(tags);
		}
		if (!removeTags.isEmpty()) {
			for (String removeTag: removeTags) {
				vars.add("-" + removeTag);
			}
		}
		if (!sourceUrls.isEmpty()) {
			for (String url: sourceUrls) {
				vars.add("URL=" +url);
			}
		}
		if (assignedWriter != null) {
			vars.add("Writer = " + assignedWriter);
		}
		if (description != null) {
			vars.add("Description = " + description);
		}
		return StringUtilities.buildString(vars.toArray(new String[vars.size()]), ", ");
	}
	
	/**
	 * Removes the node from its parent.
	 */
	protected abstract void removeParent();
	
	public abstract String getUniqueLinkId(final boolean useFixedUrls);
}
