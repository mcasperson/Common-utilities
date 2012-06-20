package com.redhat.contentspec;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTBaseTopicV1;

/**
 * A class that is used to represent a comment in a Content Specification.
 * 
 * @author lnewson
 * 
 */
public class Comment<T extends RESTBaseTopicV1<T, U>, U extends BaseRestCollectionV1<T, U>> extends Node<T, U>
{

	/**
	 * Constructor
	 * 
	 * @param lineNumber
	 *            The Line Number of the Comment in a Content Specification.
	 * @param comment
	 *            The line of text that represents a comment.
	 */
	public Comment(int lineNumber, String comment)
	{
		super(lineNumber, comment.startsWith("#") ? comment : ("# " + comment));
	}

	/**
	 * Constructor
	 * 
	 * @param comment
	 *            The line of text that represents a comment.
	 */
	public Comment(String comment)
	{
		super(comment.startsWith("#") ? comment : ("# " + comment));
	}

	@Override
	public Integer getStep()
	{
		if (getParent() == null)
			return null;
		Integer previousNode = 0;

		// Get the position of the level in its parents nodes
		Integer nodePos = getParent().getChildNodes().indexOf(this);

		// If the level isn't the first node then get the previous nodes step
		if (nodePos > 0)
		{
			Node<T, U> node = getParent().getChildNodes().get(nodePos - 1);
			previousNode = node.getStep();
			// If the add node is a level then add the number of nodes it contains
			if (node instanceof Level)
			{
				previousNode = (previousNode == null ? 0 : previousNode) + ((Level<T, U>) node).getTotalNumberOfChildren();
			}
			// The node is the first item so use the parent levels step
		}
		else
		{
			previousNode = getParent().getStep();
		}
		// Make sure the previous nodes step isn't 0
		previousNode = previousNode == null ? 0 : previousNode;

		// Add one since we got the previous nodes step
		return previousNode + 1;
	}

	@Override
	public Level<T, U> getParent()
	{
		return (Level<T, U>) parent;
	}

	/**
	 * Sets the Parent node for the Comment.
	 * 
	 * @param parent
	 *            The parent node for the comment.
	 */
	protected void setParent(Level<T, U> parent)
	{
		super.setParent(parent);
	}

	@Override
	public String getText()
	{
		return text;
	}

	@Override
	public String toString()
	{
		String spacer = "";
		for (int i = 1; i < (parent != null ? getColumn() : 0); i++)
		{
			spacer += "  ";
		}
		return spacer + text + "\n";
	}

}
