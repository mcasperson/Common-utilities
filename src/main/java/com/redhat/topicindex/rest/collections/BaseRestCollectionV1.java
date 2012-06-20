package com.redhat.topicindex.rest.collections;

import java.util.ArrayList;
import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTBaseEntityV1;

/**
 * @author Matthew Casperson
 *
 * @param <T> The REST entity type
 * @param <U> The REST Collection type 
 */
abstract public class BaseRestCollectionV1<T extends RESTBaseEntityV1<T, U>, U extends BaseRestCollectionV1<T, U>>
{
	private Integer size = 0;
	private String expand = null;
	private Integer startExpandIndex = null;
	private Integer endExpandIndex = null;
	
	abstract public List<T> getItems();
	abstract public void setItems(final List<T> items);

	public void cloneInto(final BaseRestCollectionV1<T, U> dest, final boolean deepCopy)
	{
		dest.size = this.size;
		dest.expand = this.expand;
		dest.startExpandIndex = this.startExpandIndex;
		dest.endExpandIndex = this.endExpandIndex;

		if (this.getItems() != null)
		{
			dest.setItems(new ArrayList<T>());
			if (deepCopy)
			{
				for (final T item : this.getItems())
					dest.getItems().add(item.clone(deepCopy));
			}
			else
			{
				dest.getItems().addAll(this.getItems());
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
		if (this.getItems() == null)
			this.setItems(new ArrayList<T>());
		this.getItems().add(item);
	}
}
