package com.redhat.ecs.commonstructures;

import com.redhat.ecs.commonutils.CollectionUtilities;

/**
 * A simple utility class used to store the essential details of an entity that
 * has an integer primary key, a name, and a sorting order
 */
public class NameIDSortMap implements Comparable<NameIDSortMap>
{
	protected Integer id;
	protected String name;
	protected Integer sort;

	public String getName()
	{
		return name;
	}

	public void setName(final String value)
	{
		name = value;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(final Integer value)
	{
		id = value;
	}

	public Integer getSort()
	{
		return sort;
	}

	public void setSort(final Integer value)
	{
		this.sort = value;
	}

	public NameIDSortMap(final String name, final Integer id, final Integer sort)
	{
		this.name = name;
		this.id = id;
		this.sort = sort;
	}

	public int compareTo(final NameIDSortMap other)
	{
		if (this.equals(other))
			return 0;

		final int sortOrder = CollectionUtilities.getSortOrder(this.sort, other.sort);
		if (sortOrder != 0)
			return sortOrder;

		final int nameOrder = CollectionUtilities.getSortOrder(this.name, other.name);
		if (nameOrder != 0)
			return nameOrder;

		final int idOrder = CollectionUtilities.getSortOrder(this.id, other.id);
		return idOrder;
	}

	@Override
	public boolean equals(final Object other)
	{
		if (!(other instanceof NameIDSortMap))
			return false;

		final NameIDSortMap otherNameIDSortMap = (NameIDSortMap) other;

		return CollectionUtilities.isEqual(this.name, otherNameIDSortMap.name) && 
				CollectionUtilities.isEqual(this.id, otherNameIDSortMap.id) && 
				CollectionUtilities.isEqual(this.sort, otherNameIDSortMap.sort);
	}

	@Override
	public int hashCode()
	{
		int hash = 1;
		hash = hash * 31 + (this.id == null ? 0 : this.id.hashCode());
		hash = hash * 31 + (this.name == null ? 0 : this.name.hashCode());
		hash = hash * 31 + (this.sort == null ? 0 : this.sort.hashCode());
		return hash;
	}
}
