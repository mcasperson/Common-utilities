package com.redhat.topicindex.rest.collections;

import java.util.ArrayList;
import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTBaseEntityV1;

/**
 * The base class for all collections used in the REST interface.
 */
public class BaseRestCollectionV1<T extends RESTBaseEntityV1<T>>
{
	private Integer size = 0;
	private String expand = null;
	private Integer startExpandIndex = null;
	private Integer endExpandIndex = null;
	protected List<T> items = null;

	public void cloneInto(final BaseRestCollectionV1<T> dest, final boolean deepCopy)
	{
		dest.size = this.size;
		dest.expand = this.expand;
		dest.startExpandIndex = this.startExpandIndex;
		dest.endExpandIndex = this.endExpandIndex;

		if (this.items != null)
		{
			dest.items = new ArrayList<T>();
			if (deepCopy)
			{
				for (final T item : this.items)
					dest.items.add(item.clone(deepCopy));
			}
			else
			{
				dest.items.addAll(this.items);
			}
		}
	}

	public Integer getSize()
	{
		return size;
	}

	public void setSize(final Integer size)
	{
		this.size = size;
	}

	public String getExpand()
	{
		return expand;
	}

	public void setExpand(final String expand)
	{
		this.expand = expand;
	}

	/**
	 * Errai has a limitation with nested parameterized types being returned by a Jackson REST interface. See https://issues.jboss.org/browse/ERRAI-319 for more
	 * details. However, it is still useful to be able to access the collection held by this object in a generic way. So this method uses a nonstandard naming
	 * convention to prevent it from being read by any serialization routines.
	 * 
	 * @return The collection of objects held by this collection.
	 */
	public List<T> returnItems()
	{
		return items;
	}

	public void defineItems(final List<T> items)
	{
		this.items = items;
	}

	public Integer getStartExpandIndex()
	{
		return startExpandIndex;
	}

	public void setStartExpandIndex(final Integer startExpandIndex)
	{
		this.startExpandIndex = startExpandIndex;
	}

	public Integer getEndExpandIndex()
	{
		return endExpandIndex;
	}

	public void setEndExpandIndex(final Integer endExpandIndex)
	{
		this.endExpandIndex = endExpandIndex;
	}

	public void addItem(final T item)
	{
		if (this.items == null)
			this.items = new ArrayList<T>();
		this.items.add(item);
	}
}
