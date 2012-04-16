package com.redhat.ecs.services.docbookcompiling.xmlprocessing.structures;

import org.w3c.dom.Document;

/**
 * This class holds the data that is used while processing a topic into docbook
 * for inclusion in the zip file
 */
public class TopicProcessingData
{
	private Document xmlDocument;

	public Document getXmlDocument()
	{
		return xmlDocument;
	}

	public void setXmlDocument(final Document xmlDocument)
	{
		this.xmlDocument = xmlDocument;
	}
}
