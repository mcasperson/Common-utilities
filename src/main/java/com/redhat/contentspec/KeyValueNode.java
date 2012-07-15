package com.redhat.contentspec;

public class KeyValueNode<T> extends Node
{
	private final String key;
	private T value = null;
	
	public KeyValueNode(final String key, final T value, final char separator)
	{
		super(key + " " + separator + " " + value);
		this.key = key;
		this.setValue(value);
	}
	
	public KeyValueNode(final String key, final T value)
	{
		this(key, value, '=');
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
		getParent().removeChild(this);
	}

	public String getKey()
	{
		return key;
	}

	public T getValue()
	{
		return value;
	}

	public void setValue(final T value)
	{
		this.value = value;
	}

	@Override
	public ContentSpec getParent()
	{
		return (ContentSpec) parent;
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
	
}
