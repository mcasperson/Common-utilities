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
	public Comment(final int lineNumber, final String comment)
	{
		super(lineNumber, comment.trim().startsWith("#") ? comment : ("# " + comment));
	}

	/**
	 * Constructor
	 * 
	 * @param comment
	 *            The line of text that represents a comment.
	 */
	public Comment(final String comment)
	{
		super(comment.trim().startsWith("#") ? comment : ("# " + comment));
	}

	@Override
	public Integer getStep()
	{
		final Node parent = getParent();
		if (parent == null)
			return null;
		Integer previousNode = 0;
		
		if (parent instanceof Level)
		{			
			// Get the position of the level in its parents nodes
			final Integer nodePos = ((Level) parent).getChildNodes().indexOf(this);
			
			// If the level isn't the first node then get the previous nodes step
			if (nodePos > 0)
			{
				final Node node = ((Level) parent).getChildNodes().get(nodePos - 1);
				previousNode = node.getStep();
				// If the add node is a level then add the number of nodes it contains
				if (node instanceof Level)
				{
					previousNode = (previousNode == null ? 0 : previousNode) + ((Level)node).getTotalNumberOfChildren();
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
		else
		{
			return null;
		}
	}

	/**
	 * Sets the Parent node for the Comment.
	 * 
	 * @param parent
	 *            The parent node for the comment.
	 */
	protected void setParent(final Level parent)
	{
		super.setParent(parent);
	}
	
	/**
	 * Sets the Parent node for the Comment.
	 * 
	 * @param parent The parent node for the comment.
	 */
	protected void setParent(final ContentSpec parent)
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
		return getText() + "\n";
	}
	
	@Override
	protected void removeParent()
	{
		final Node parent = getParent();
		if (parent instanceof Level)
			((Level) parent).removeComment(this);
		else
			((ContentSpec) parent).removeComment(this);
		super.setParent(null);
	}

}
