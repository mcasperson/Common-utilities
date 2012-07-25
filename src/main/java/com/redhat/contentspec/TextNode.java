package com.redhat.contentspec;

public class TextNode extends Node
{
	public TextNode(final int lineNumber, final String text)
	{
		super(lineNumber, text);
	}
	
	public TextNode(final String text)
	{
		super(text);
	}
	
	@Override
	public Integer getStep()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void removeParent()
	{
		if (parent instanceof ContentSpec)
		{
			((ContentSpec) parent).removeChild(this);
		}
		else if (parent instanceof Level)
		{
			((Level) parent).removeChild(this);
		}
		this.parent = null;
	}

	@Override
	public String toString()
	{
		return getText();
	}
}
