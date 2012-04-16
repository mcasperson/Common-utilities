package com.redhat.ecs.services.docbookcompiling.xmlprocessing.structures;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;

/**
 * This class defines a DocBook list that was created when processing the custom
 * injection points defined in a topic XML file
 */
public class InjectionListData
{
	/**
	 * The elements that are to be inserted into the DocBook XML in place of the
	 * comment. There may be more than one element, like in the case where an
	 * xref tag is preceded by a emphasis tag.
	 */
	public List<List<Element>> listItems = new ArrayList<List<Element>>();
	/** defines the type of list (sequential, itemized etc) */
	public int listType = -1;

	/**
	 * @param listItems A list that contains the lists of XML elements to
	 *            replace the comment
	 * @param listType The type of list, as defined by the static variables in
	 *            the DocbookBuilder class
	 */
	public InjectionListData(final List<List<Element>> listItems, final int listType)
	{
		this.listItems = listItems;
		this.listType = listType;
	}
}