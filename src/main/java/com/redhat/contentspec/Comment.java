package com.redhat.contentspec;

/**
 * A class that is used to represent a comment in a Content Specification.
 * 
 * @author lnewson
 * 
 */
public class Comment extends Node
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
			Node node = getParent().getChildNodes().get(nodePos - 1);
			previousNode = node.getStep();
			// If the add node is a level then add the number of nodes it contains
			if (node instanceof Level)
			{
				previousNode = (previousNode == null ? 0 : previousNode) + ((Level) node).getTotalNumberOfChildren();
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
	public Level getParent()
	{
		return (Level) parent;
	}

	/**
	 * Sets the Parent node for the Comment.
	 * 
	 * @param parent
	 *            The parent node for the comment.
	 */
	protected void setParent(Level parent)
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
		final StringBuilder output = new StringBuilder();
		final int indentationSize = parent != null ? getColumn() : 0;
		for (int i = 1; i < indentationSize; i++)
		{
			output.append("  ");
		}
		output.append(text + "\n");
		return output.toString();
	}

}
