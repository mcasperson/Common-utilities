package com.redhat.topicindex.rest.collections;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.redhat.topicindex.rest.entities.BaseRESTEntityV1;

/**
 * The base class for all collections used in the REST interface.
 */
public class BaseRestCollectionV1<T extends BaseRESTEntityV1<T>>
{
	private Integer size = 0;
	private String expand = null;
	private Integer startExpandIndex = null;
	private Integer endExpandIndex = null;
	private List<T> items = null;
	
	public BaseRestCollectionV1<T> clone(final boolean deepCopy)
	{
		final BaseRestCollectionV1<T> retValue  = new BaseRestCollectionV1<T>();
		retValue.size = this.size;
		retValue.expand = this.expand;
		retValue.startExpandIndex = this.startExpandIndex;
		retValue.endExpandIndex = this.endExpandIndex;
		
		if (this.items != null)
		{
			retValue.items = new ArrayList<T>();
			if (deepCopy)
			{
				for (final T item : this.items)
					retValue.items.add(item.clone(deepCopy));
			}
			else
			{
				retValue.items.addAll(this.items);
			}
		}
		return retValue;
	}
	
	@XmlElement
	public Integer getSize()
	{
		return size;
	}

	public void setSize(final Integer size)
	{
		this.size = size;
	}

	@XmlElement
	public String getExpand()
	{
		return expand;
	}

	public void setExpand(final String expand)
	{
		this.expand = expand;
	}

	@XmlElement
	public List<T> getItems()
	{
		return items;
	}

	public void setItems(final List<T> items)
	{
		this.items = items;
	}

	@XmlElement
	public Integer getStartExpandIndex()
	{
		return startExpandIndex;
	}

	public void setStartExpandIndex(final Integer startExpandIndex)
	{
		this.startExpandIndex = startExpandIndex;
	}

	@XmlElement
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
