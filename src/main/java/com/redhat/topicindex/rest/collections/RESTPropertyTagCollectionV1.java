package com.redhat.topicindex.rest.collections;

import java.util.ArrayList;
import java.util.List;

import com.redhat.topicindex.rest.entities.interfaces.RESTPropertyTagV1;

/**
 * This is a wrapper class to work around an Errai limitation - https://issues.jboss.org/browse/ERRAI-319
 * @author Matthew Casperson
 *
 */
public class RESTPropertyTagCollectionV1 extends BaseRestCollectionV1<RESTPropertyTagV1, RESTPropertyTagCollectionV1>
{
	private List<RESTPropertyTagV1> items;
	
	@Override
	public List<RESTPropertyTagV1> getItems()
	{
		return this.items;
	}
	
	@Override
	public void setItems(final List<RESTPropertyTagV1> items)
	{
		this.items = items;
	}
	
	/**
	 * PropertyTags are only duplicated when they are unique, have the same ID and the same value.
	 */
	@Override
	protected void ignoreDuplicatedAddRemoveItemRequests()
	{
		if (this.getItems() != null)
		{
			final List<RESTPropertyTagV1> removeChildren = new ArrayList<RESTPropertyTagV1>();
		
			/* on the second loop, remove any items that are marked for both add and remove is separate items */
			for (int i = 0; i < this.getItems().size(); ++i)
			{
				final RESTPropertyTagV1 child1 = this.getItems().get(i);
				
				/* at this point we know that either add1 or remove1 will be true, but not both */
				final boolean add1 = child1.getAddItem();
				final boolean remove1 = child1.getRemoveItem();
				
				/* Loop a second time, looking for duplicates */
				for (int j = i + 1; j < this.getItems().size(); ++j)
				{
					final RESTPropertyTagV1 child2 = this.getItems().get(j);
					
					/* Check the PropertyTags for uniqueness and their value as well as their IDs */
					if ((child1.getIsUnique() && child2.getIsUnique()) && child1.getId().equals(child2.getId()) && child1.getValue().equals(child2.getValue()))
					{
						final boolean add2 = child2.getAddItem();
						final boolean remove2 = child2.getRemoveItem();
						
						/* check for double add, double remove, add and remove, remove and add */
						if ((add1 && add2) || (remove1 && remove2) || (add1 && remove2) || (remove1 && add2))						
							if (!removeChildren.contains(child1))
								removeChildren.add(child1);
					}
				}
			}
		}
	}
}
