package com.redhat.ecs.commonstructures;

import java.util.ArrayList;

import org.w3c.dom.Node;

import com.redhat.ecs.commonutils.CollectionUtilities;

/**
 * This class is used to map a translation string to collections of XML Nodes.
 * This way the Nodes can be replaced with the XML formed by the translation
 * string.
 */
public class StringToNodeCollection
{
	private String translationString;
	/**
	 * The translationString may be unique, while mapping to several sequences
	 * of nodes
	 */
	private ArrayList<ArrayList<Node>> nodeCollections;

	public ArrayList<ArrayList<Node>> getNodeCollections()
	{
		return nodeCollections;
	}

	public void setNodeCollections(ArrayList<ArrayList<Node>> nodeCollections)
	{
		this.nodeCollections = nodeCollections;
	}

	public String getTranslationString()
	{
		return translationString;
	}

	public void setTranslationString(String translationString)
	{
		this.translationString = translationString;
	}
	
	public StringToNodeCollection()
	{
		
	}
	
	public StringToNodeCollection(final String translationString, final ArrayList<ArrayList<Node>> nodeCollections)
	{
		this.translationString = translationString;
		this.nodeCollections = nodeCollections;
	}
	
	public StringToNodeCollection(final String translationString, final Node node)
	{
		this.translationString = translationString;
		addNode(node);
	}
	
	public StringToNodeCollection(final String translationString)
	{
		this.translationString = translationString;
	}
	
	public StringToNodeCollection addNodeCollection(final ArrayList<Node> nodes)
	{
		if (this.nodeCollections == null)
			this.nodeCollections = new ArrayList<ArrayList<Node>>();
		this.nodeCollections.add(nodes);
		return this;
	}
	
	public StringToNodeCollection addNode(final Node node)
	{
		if (this.nodeCollections == null)
			this.nodeCollections = new ArrayList<ArrayList<Node>>();
		this.nodeCollections.add(CollectionUtilities.toArrayList(new Node[] {node}));
		return this;
	}
}
