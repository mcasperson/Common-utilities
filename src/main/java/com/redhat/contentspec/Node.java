package com.redhat.contentspec;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.RESTBaseTopicV1;

/**
 * A very basic class that represents the lowest form of a Node in a Content Specification.
 * 
 * @author lnewson
 * 
 */
public abstract class Node<T extends RESTBaseTopicV1<T, U>, U extends BaseRestCollectionV1<T, U>>
{
	protected final int lineNumber;
	protected String text;
	protected Node<T, U> parent;

	public Node(int lineNumber, String text)
	{
		this.lineNumber = lineNumber;
		this.text = text;
	}

	public Node(String text)
	{
		this.lineNumber = -1;
		this.text = text;
	}

	public Node()
	{
		this.lineNumber = -1;
		this.text = null;
	}

	/**
	 * Gets the line number that the node is on in a Content Specification.
	 * 
	 * @return The Line Number for the node.
	 */
	public int getLineNumber()
	{
		return lineNumber;
	}

	/**
	 * Gets the text for the node's line.
	 * 
	 * @return The line of text for the node.
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * Sets the text for the node.
	 * 
	 * @param text
	 *            The nodes text.
	 */
	protected void setText(String text)
	{
		this.text = text;
	}

	/**
	 * Gets the step of the node in the Content Specification.
	 * 
	 * @return The Step of the node.
	 */
	public abstract Integer getStep();

	/**
	 * Get the parent of the node.
	 * 
	 * @return The nodes parent.
	 */
	public Node<T, U> getParent()
	{
		return parent;
	}

	/**
	 * Sets the nodes parent.
	 * 
	 * @param parent
	 *            The parent node.
	 */
	protected void setParent(Node<T, U> parent)
	{
		this.parent = parent;
	}

	/**
	 * Gets the column the node starts at.
	 * 
	 * @return The column the node starts at.
	 */
	public Integer getColumn()
	{
		return parent == null ? 0 : (parent.getColumn() + 1);
	}
}
