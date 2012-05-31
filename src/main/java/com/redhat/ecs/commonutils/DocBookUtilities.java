package com.redhat.ecs.commonutils;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * A collection of static variables and functions that can be used when working
 * with DocBook
 */
public class DocBookUtilities
{
	/** The name of the section tag */
	public static final String TOPIC_ROOT_NODE_NAME = "section";
	/** The name of the id attribute */
	public static final String TOPIC_ROOT_ID_ATTRIBUTE = "id";
	/** The name of the title tag */
	public static final String TOPIC_ROOT_TITLE_NODE_NAME = "title";
	
	/**
	 * Finds the first title element in a DocBook XML file.
	 * 
	 * @param xml The docbook xml file to find the title from.
	 * @return The first title found in the xml.
	 */
	public static String findTitle(final String xml)
	{
		/* Convert the string to a document to make it easier to get the proper title */
		Document doc = null;
		try {
			doc = XMLUtilities.convertStringToDocument(xml);
		} catch (SAXException ex) {
			ExceptionUtilities.handleException(ex);
		}
		
		return findTitle(doc);
	}
	
	/**
	 * Finds the first title element in a DocBook XML file.
	 * 
	 * @param doc The docbook xml transformed into a DOM Document to find the title from.
	 * @return The first title found in the xml.
	 */
	public static String findTitle(final Document doc)
	{
		if (doc == null) return null;
		
		/* loop through the child nodes until the title element is found */
		final NodeList childNodes = doc.getDocumentElement().getChildNodes();
		for (int i = 0; i < childNodes.getLength(); i++)
		{
			Node node = childNodes.item(i);
			
			/* check if the node is the title and if its parent is the document root element */
			if (node.getNodeName().equals(TOPIC_ROOT_TITLE_NODE_NAME) && node.getParentNode().equals(doc.getDocumentElement()))
			{
				return ((Element) node).getTextContent();
			}
		}

		return null;
	}

	/**
	 * Escapes a title so that it is alphanumeric or has a fullstop, underscore or hyphen only.
	 * It also removes anything from the front of the title that isn't alphanumeric.
	 * 
	 * @param title The title to be escaped
	 * @return The escaped title string.
	 */
	public static String escapeTitle(final String title) {
		return title.replaceAll(" ", "_").replaceAll("^[^A-Za-z0-9]*", "").replaceAll("[^A-Za-z0-9\\._-]", "");
	}
}
