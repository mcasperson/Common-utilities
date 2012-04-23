package com.redhat.ecs.commonutils;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.redhat.ecs.commonstructures.Pair;
import com.redhat.ecs.commonstructures.StringToNodeCollection;
import com.redhat.ecs.internalsort.EntitySubstitutionBoundaryDataBoundaryStartSort;
import com.redhat.ecs.internaltructures.EntitySubstitutionBoundaryData;

/**
 * A collection of XML related functions. Note to self: See
 * http://www.gnu.org/s/
 * classpathx/jaxp/apidoc/gnu/xml/dom/ls/DomLSSerializer.html for LSSerializer
 * options
 */
public class XMLUtilities
{
	/** The Docbook elements that contain translatable text */
	public static final ArrayList<String> TRANSLATABLE_ELEMENTS = CollectionUtilities.toArrayList(new String[]
	{ "ackno", "bridgehead", "caption", "conftitle", "contrib", "entry", "firstname", "glossterm", "indexterm", "jobtitle", "keyword", "label", "lastname", "lineannotation", "lotentry", "member", "orgdiv", "orgname", "othername", "para", "phrase", "productname", "refclass", "refdescriptor", "refentrytitle",
			"refmiscinfo", "refname", "refpurpose", "releaseinfo", "revremark", "screeninfo", "secondaryie", "seealsoie", "seeie", "seg", "segtitle", "simpara", "subtitle", "surname", "term", "termdef", "tertiaryie", "title", "titleabbrev", "screen", "programlisting", "literallayout" });
	/**
	 * The Docbook elements that contain translatable text, and need to be kept
	 * inline
	 */
	public static final ArrayList<String> INLINE_ELEMENTS = CollectionUtilities.toArrayList(new String[]
	{ "footnote", "citerefentry", "indexterm", "productname", "phrase" });
	/** The Docbook elements that should not have their text reformatted */
	public static final ArrayList<String> VERBATIM_ELEMENTS = CollectionUtilities.toArrayList(new String[]
	{ "screen", "programlisting", "literallayout" });
	/**
	 * The Docbook elements that should be translated only if their parent is
	 * not listed in TRANSLATABLE_ELEMENTS
	 */
	public static final ArrayList<String> TRANSLATABLE_IF_STANDALONE_ELEMENTS = CollectionUtilities.toArrayList(new String[]
	{ "indexterm", "productname", "phrase" });
	public static final String ENCODING_START = "encoding=\"";
	public static final String START_CDATA = "<![CDATA[";
	public static final String END_CDATA_RE = "\\]\\]>";
	public static final String END_CDATA_REPLACE = "]]&gt;";
	public static final String XML_ENTITY_NAMED_GROUP = "name";
	public static final String XML_ENTITY_RE = "\\&(?<" + XML_ENTITY_NAMED_GROUP + ">.*?);";
	public static final String DOCTYPE_START = "<!DOCTYPE";
	public static final String DOCTYPE_END = ">";
	public static final String PREAMBLE_START = "<?xml";
	public static final String PREAMBLE_END = ">";
	public static final String TRAILING_WHITESPACE_RE = "^(?<content>.*?)\\s+$";
	public static final String TRAILING_WHITESPACE_SIMPLE_RE = ".*?\\s+$";
	public static final String PRECEEDING_WHITESPACE_SIMPLE_RE = "^\\s+.*";

	public static final Pattern TRAILING_WHITESPACE_RE_PATTERN = Pattern.compile(TRAILING_WHITESPACE_RE, Pattern.MULTILINE | Pattern.DOTALL);
	public static final Pattern TRAILING_WHITESPACE_SIMPLE_RE_PATTERN = Pattern.compile(TRAILING_WHITESPACE_SIMPLE_RE, Pattern.MULTILINE | Pattern.DOTALL);
	public static final Pattern PRECEEDING_WHITESPACE_SIMPLE_RE_PATTERN = Pattern.compile(PRECEEDING_WHITESPACE_SIMPLE_RE, Pattern.MULTILINE | Pattern.DOTALL);
	
	public static String findEncoding(final String xml)
	{
		final int encodingIndexStart = xml.indexOf(ENCODING_START);
		final int firstLineBreak = xml.indexOf("\n");

		// make sure we found the encoding attribute
		if (encodingIndexStart != -1)
		{
			final int encodingIndexEnd = xml.indexOf("\"", encodingIndexStart + ENCODING_START.length());

			// make sure the encoding attribute was found before the first
			// line break
			if (firstLineBreak == -1 || encodingIndexStart < firstLineBreak)
			{
				// make sure we found the end of the attribute
				if (encodingIndexEnd != -1)
				{
					return xml.substring(encodingIndexStart + ENCODING_START.length(), encodingIndexEnd);
				}
			}
		}

		return null;
	}

	public static String findDocumentType(final String xml)
	{
		final int indexStart = xml.indexOf(DOCTYPE_START);

		// make sure we found the encoding attribute
		if (indexStart != -1)
		{
			final int indexEnd = xml.indexOf(DOCTYPE_END, indexStart + DOCTYPE_START.length());

			// make sure we found the end of the attribute
			if (indexEnd != -1)
			{
				return xml.substring(indexStart, indexEnd + DOCTYPE_END.length());
			}

		}

		return null;
	}

	public static String findPreamble(final String xml)
	{
		final int indexStart = xml.indexOf(PREAMBLE_START);

		// make sure we found the encoding attribute
		if (indexStart != -1)
		{
			final int indexEnd = xml.indexOf(PREAMBLE_END, indexStart + PREAMBLE_START.length());

			// make sure we found the end of the attribute
			if (indexEnd != -1)
			{
				return xml.substring(indexStart, indexEnd + PREAMBLE_END.length());
			}

		}

		return null;
	}

	/**
	 * This function will return a map that contains entity names as keys, and
	 * random integer strings as values. The values are guaranteed not to have
	 * appeared in the original xml.
	 * 
	 * @param xml
	 *            The xml to generate the replacements for
	 * @return a map of entity names to unique random strings
	 */
	private static Map<String, String> calculateEntityReplacements(final String xml)
	{
		final Map<String, String> retValue = new HashMap<String, String>();

		final Random randomGenerator = new Random();

		/* compile the regular expression */
		final Pattern injectionSequencePattern = Pattern.compile(XML_ENTITY_RE);
		/* find any matches */
		final Matcher injectionSequencematcher = injectionSequencePattern.matcher(xml);

		/* loop over the regular expression matches */
		while (injectionSequencematcher.find())
		{
			final String entityName = injectionSequencematcher.group(XML_ENTITY_NAMED_GROUP);

			if (!retValue.containsKey(entityName))
			{
				String randomReplacement;
				do
				{
					randomReplacement = "[" + randomGenerator.nextInt() + "]";
				}
				while (xml.indexOf(randomReplacement) != -1);

				retValue.put(entityName, randomReplacement);
			}
		}

		return retValue;
	}

	/**
	 * This function takes the Map generated by the calculateEntityReplacements
	 * function, and uses those values to replace any entities in the XML string
	 * with their unique random integer replacements. The end results is an XML
	 * string that contains no entities, but contains identifiable strings that
	 * can be used to replace those entities at a later point.
	 * 
	 * @param replacements
	 *            The Map generated by the calculateEntityReplacements function
	 * @param xml
	 *            The XML string to modify
	 * @return The modified XML
	 */
	private static String replaceEntities(final Map<String, String> replacements, final String xml)
	{
		String retValue = xml;
		for (final String entity : replacements.keySet())
			retValue = retValue.replaceAll("\\&" + entity + ";", replacements.get(entity));
		return retValue;
	}

	/**
	 * This function takes a parsed Document, along with the Map generated by
	 * the calculateEntityReplacements function, and restores all the entities.
	 * 
	 * @param replacements
	 *            The Map generated by the calculateEntityReplacements function
	 * @param node
	 *            The node to modify
	 */
	private static void restoreEntities(final Map<String, String> replacements, final Node node)
	{
		if (node == null || replacements == null || replacements.size() == 0)
			return;

		/* make the substitutions for all children nodes */
		final NodeList nodeList = node.getChildNodes();
		final int childrenCount = nodeList.getLength();
		for (int i = 0; i < childrenCount; ++i)
			restoreEntities(replacements, nodeList.item(i));

		/* cdata sections just use a straight text replace */
		if (node.getNodeType() == Node.CDATA_SECTION_NODE)
		{
			for (final Entry<String, String> entityReplacement : replacements.entrySet())
			{
				final String entity = "&" + entityReplacement.getKey() + ";";
				final String markerAsRE = entityReplacement.getValue().replace("[", "\\[").replace("]", "\\]");
				final String textContent = node.getTextContent();
				final String fixedTextContent = textContent.replaceAll(markerAsRE, entity);
				node.setTextContent(fixedTextContent);
			}
		}
		else if (node.getNodeType() == Node.TEXT_NODE)
		{
			/* The list of substitution string boundaries */
			final List<EntitySubstitutionBoundaryData> boundaries = new ArrayList<EntitySubstitutionBoundaryData>();

			/*
			 * find the start and end indexes of all the substitutions in this
			 * text node
			 */
			for (final Entry<String, String> entityReplacement : replacements.entrySet())
			{
				final String entityName = entityReplacement.getKey();
				final String entityPlaceholder = entityReplacement.getValue();

				/* The length of the placeholder string */
				final int entityPlaceholderLength = entityPlaceholder.length();
				/* The text in this node, with the substitutions */
				final String originalText = node.getTextContent();

				int startIndex = 0;
				while ((startIndex = originalText.indexOf(entityPlaceholder, startIndex)) != -1)
				{
					boundaries.add(new EntitySubstitutionBoundaryData(entityName, entityPlaceholder, new Pair<Integer, Integer>(startIndex, startIndex + entityPlaceholderLength - 1)));
					startIndex += entityPlaceholderLength;
				}
			}

			/*
			 * if there are no boundaries, there is no need to do any
			 * substitutions
			 */
			if (boundaries.size() != 0)
			{
				/* Sort based on the start of the boundaries */
				Collections.sort(boundaries, new EntitySubstitutionBoundaryDataBoundaryStartSort());

				/* get the text content of the text node */
				final String originalText = node.getTextContent();

				/* the parent of this node holds only this text node. */
				final Node parentNode = node.getParentNode();

				/*
				 * loop through all the boundaries that define the position of
				 * the substitutions, and replace them with entity reference
				 * nodes.
				 * 
				 * this involves adding a new sequence of text and entity
				 * reference nodes before the existing text node, and then
				 * removing the existing text node.
				 */
				for (int i = 0; i < boundaries.size(); ++i)
				{
					final EntitySubstitutionBoundaryData boundary = boundaries.get(i);
					final EntitySubstitutionBoundaryData lastBoundary = i != 0 ? boundaries.get(i - 1) : null;

					/* the entity node */
					final Node entityNode = parentNode.getOwnerDocument().createEntityReference(boundary.getEntityName());

					/* the first substitution where text proceeds it */
					if (i == 0)
					{
						if (boundary.getBoundary().getFirst() != 0)
						{
							final Node textNode = parentNode.getOwnerDocument().createTextNode(originalText.substring(0, boundary.getBoundary().getFirst()));
							parentNode.insertBefore(textNode, node);
						}

						/* append an entity node after the initial text node */
						parentNode.insertBefore(entityNode, node);
					}
					else
					{
						/*
						 * there is a gap between the last boundary and this
						 * boundary
						 */

						if (lastBoundary.getBoundary().getSecond() + 1 != boundary.getBoundary().getFirst())
						{
							final Node textNode = parentNode.getOwnerDocument().createTextNode(originalText.substring(lastBoundary.getBoundary().getSecond() + 1, boundary.getBoundary().getFirst()));
							parentNode.insertBefore(textNode, node);
						}
					}

					/*
					 * append an entity node after the text node following the
					 * last substitution
					 */
					parentNode.insertBefore(entityNode, node);

					/* the last substitution where text follows it */
					if (i == boundaries.size() - 1)
					{
						/* append an entity node before the last text node */
						parentNode.insertBefore(entityNode, node);

						if (boundary.getBoundary().getSecond() != originalText.length() - 1)
						{
							final Node textNode = parentNode.getOwnerDocument().createTextNode(originalText.substring(boundary.getBoundary().getSecond() + 1));
							parentNode.insertBefore(textNode, node);
						}
					}
				}

				/* finally, remove the existing text node */
				parentNode.removeChild(node);
			}

		}

		// TODO: deal with entities in attributes
	}

	/**
	 * @param xml
	 *            The XML to be converted
	 * @return A Document converted from the supplied XML, or null if the
	 *         supplied XML was invalid
	 */
	public static Document convertStringToDocument(final String xml)
	{
		if (xml == null)
			return null;

		try
		{
			// find the encoding, defaulting to UTF-8
			String encoding = findEncoding(xml);
			if (encoding == null)
				encoding = "UTF-8";

			/*
			 * Xerces does not seem to have any way of simply importing entities
			 * "as is". It will try to expand them, which we don't want. As a
			 * work around the calculateEntityReplacements() function will map
			 * entity names to random substitution markers. These markers are
			 * parsed as plain text (they are in the format "[random_integer]").
			 * The replaceEntities() function will then replace the entity
			 * definitions in the source XML text with these substitution
			 * markers.
			 * 
			 * At this point the XML has no entities, and so Xerces will parse
			 * the string without trying to expand the entities.
			 * 
			 * Once we have a Document object, we run the restoreEntities()
			 * function, which replaces the substitution markers with entity
			 * reference nodes. Xerces does not try to expand entites when
			 * serializing a Document object to a string, nor does it try to
			 * extand entity reference nodes when they are added. In this way we
			 * can parse any XML and retain the entities without having to link
			 * to any DTDs or implement any EntityResolvers.
			 */
			final Map<String, String> replacements = calculateEntityReplacements(xml);
			final String fixedXML = replaceEntities(replacements, xml);

			final DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			builderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			builderFactory.setValidating(false);

			final DocumentBuilder builder = builderFactory.newDocumentBuilder();
			final Document document = builder.parse(new ByteArrayInputStream(fixedXML.getBytes(encoding)));

			restoreEntities(replacements, document.getDocumentElement());

			return document;
		}
		catch (Exception ex)
		{
			ExceptionUtilities.handleException(ex);
		}

		return null;
	}

	/**
	 * Converts a Document to a String
	 * 
	 * @param doc
	 *            The Document to be converted
	 * @return The String representation of the Document
	 */
	public static String convertDocumentToString(final Document doc, final String encoding)
	{
		String retValue = convertDocumentToString(doc);

		/*
		 * The encoding used is the encoding of the DOMString type, i.e. UTF-16
		 * (http://www.w3.org/TR/DOM-Level-3-LS/load-save.html#LS-LSSerializer-
		 * writeToString). However, we need to use UTF-8
		 * (https://bugzilla.redhat.com/show_bug.cgi?id=735904). So do a simple
		 * text replacement.
		 */

		final String docEncoding = findEncoding(retValue);
		if (docEncoding != null)
			retValue = retValue.replace(docEncoding, encoding);

		return retValue;
	}
	
	/**
	 * Convert an XML document to a string.
	 * @param doc The Document to be converted
	 * @param encoding The encoding of the XML
	 * @param entityDec Any additional XML entity declarations
	 * @return The String representation of the XML Document
	 */
	public static String convertDocumentToString(final Document doc, final String encoding, final String entityDec)
	{
		String retValue = convertDocumentToString(doc, encoding);
		
		final String docEncoding = findPreamble(retValue);
		if (docEncoding != null)
			retValue = retValue.replace(docEncoding, docEncoding + "\n" + entityDec);
		
		return retValue;
	}

	/**
	 * Converts a Document to a String
	 * 
	 * @param doc
	 *            The Document to be converted
	 * @return The String representation of the Document
	 */
	public static String convertDocumentToString(final Document doc)
	{
		final DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
		final LSSerializer lsSerializer = domImplementation.createLSSerializer();
		// lsSerializer.getDomConfig().setParameter("format-pretty-print",
		// Boolean.TRUE);
		final String xml = lsSerializer.writeToString(doc);

		return xml;
	}

	private static void appendIndent(final StringBuffer stringBuffer, final boolean tabIndent, final int indentLevel, final int indentCount)
	{
		final char indent = tabIndent ? '\t' : ' ';

		final int totalIndentCount = indentLevel * indentCount;

		stringBuffer.append("\n");
		for (int i = 0; i < totalIndentCount; ++i)
			stringBuffer.append(indent);
	}

	public static String convertNodeToString(final Node startNode, final boolean includeElementName)
	{
		return convertNodeToString(startNode, includeElementName, true, false, new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), true, 0, 0);
	}

	public static String convertNodeToString(final Node startNode, final List<String> verbatimElements, final List<String> inlineElements, final List<String> contentsInlineElements, final boolean tabIndent)
	{
		return convertNodeToString(startNode, true, false, false, verbatimElements, inlineElements, contentsInlineElements, tabIndent, 1, 0);
	}

	/**
	 * Converts a Node to a String.
	 * 
	 * @param node
	 *            The Node to be converted
	 * @param includeElementName
	 *            true if the string should include the name of the node, or
	 *            false if it is just to include the contents of the node
	 * @return The String representation of the Node
	 */
	public static String convertNodeToString(final Node startNode, final boolean includeElementName, final boolean verbatim, final boolean inline, final List<String> verbatimElements, final List<String> inlineElements, final List<String> contentsInlineElements, final boolean tabIndent, final int indentCount,
			final int indentLevel)
	{
		/* Find out if this node is a document */
		final Node node = startNode instanceof Document ? ((Document) startNode).getDocumentElement() : startNode;

		final String nodeName = node.getNodeName();
		final short nodeType = node.getNodeType();
		final StringBuffer stringBuffer = new StringBuffer();

		/*
		 * Find out if the previous node was a comment (excluding any empty text
		 * nodes). Also find out if this is the first node in the parent.
		 */
		boolean previousNodeWasComment = false;
		Node previousNode = startNode.getPreviousSibling();
		while (previousNode != null)
		{
			if ((previousNode.getNodeType() == Node.TEXT_NODE && previousNode.getNodeValue().trim().isEmpty()))
			{
				previousNode = previousNode.getPreviousSibling();
				continue;
			}

			if (previousNode.getNodeType() == Node.COMMENT_NODE)
			{
				previousNodeWasComment = true;
				break;
			}

			break;
		}

		/* Find out of this node is the document root node */
		final boolean documentRoot = node.getOwnerDocument().getDocumentElement() == node;

		final boolean firstNode = node.getPreviousSibling() == null;

		if (Node.CDATA_SECTION_NODE == nodeType)
		{
			final StringBuffer retValue = new StringBuffer();

			if (!verbatim && !inline)
				appendIndent(retValue, tabIndent, indentLevel, indentCount);

			if (includeElementName)
				retValue.append("<![CDATA[");
			retValue.append(node.getNodeValue());
			if (includeElementName)
				retValue.append("]]>");

			return retValue.toString();
		}

		if (Node.COMMENT_NODE == nodeType)
		{
			final StringBuffer retValue = new StringBuffer();

			if (!verbatim && !inline)
				appendIndent(retValue, tabIndent, indentLevel, indentCount);

			if (includeElementName)
				retValue.append("<!--");
			retValue.append(node.getNodeValue());
			if (includeElementName)
				retValue.append("-->");

			return retValue.toString();
		}

		if (Node.TEXT_NODE == nodeType)
		{
			if (!verbatim)
			{
				String trimmedNodeValue = cleanText(node.getNodeValue());

				if (!trimmedNodeValue.trim().isEmpty())
				{
					final StringBuffer retValue = new StringBuffer();

					/*
					 * if this is the first text node, remove all preceeding
					 * whitespace, and then add the indent
					 */
					final boolean firstNotInlinedTextNode = !inline && firstNode;
					if (firstNotInlinedTextNode)
					{
						appendIndent(retValue, tabIndent, indentLevel, indentCount);
					}

					/*
					 * Remove any white space at the begining and end of the
					 * text, save for one space
					 */
					final boolean startedWithWhiteSpace = StringUtilities.startsWithWhitespace(trimmedNodeValue);
					final boolean endedWithWhitespace = StringUtilities.endsWithWhitespace(trimmedNodeValue);

					while (StringUtilities.startsWithWhitespace(trimmedNodeValue))
					{
						trimmedNodeValue = trimmedNodeValue.substring(1);
					}

					while (StringUtilities.endsWithWhitespace(trimmedNodeValue))
					{
						trimmedNodeValue = trimmedNodeValue.substring(0, trimmedNodeValue.length() - 1);
					}

					if (startedWithWhiteSpace && !firstNotInlinedTextNode)
						trimmedNodeValue = " " + trimmedNodeValue;

					if (endedWithWhitespace)
						trimmedNodeValue += " ";

					retValue.append(trimmedNodeValue);

					return retValue.toString();
				}

				return new String();
			}
			else
			{
				return node.getNodeValue();
			}
		}

		if (Node.ENTITY_REFERENCE_NODE == nodeType)
		{
			final StringBuffer retValue = new StringBuffer();
			if (includeElementName)
				retValue.append("&");
			retValue.append(node.getNodeName());
			if (includeElementName)
				retValue.append(";");

			return retValue.toString();
		}

		/* open the tag */
		if (includeElementName)
		{

			if (!verbatim && !documentRoot && ((!inline && !inlineElements.contains(nodeName)) || previousNodeWasComment || (firstNode && !inline)))
				appendIndent(stringBuffer, tabIndent, indentLevel, indentCount);

			stringBuffer.append('<').append(nodeName);

			/* add attributes */
			final NamedNodeMap attrs = node.getAttributes();
			if (attrs != null)
			{
				for (int i = 0; i < attrs.getLength(); i++)
				{
					final Node attr = attrs.item(i);
					stringBuffer.append(' ').append(attr.getNodeName()).append("=\"").append(attr.getNodeValue()).append("\"");
				}
			}
		}

		/* deal with children */
		final NodeList children = node.getChildNodes();
		if (children.getLength() == 0)
		{
			final String nodeTextContent = node.getTextContent();
			if (nodeTextContent.length() == 0)
			{
				if (includeElementName)
					stringBuffer.append("/>");
			}
			else
			{
				stringBuffer.append(nodeTextContent);

				/* indent */
				if (!verbatim && !inline && !inlineElements.contains(nodeName))
					appendIndent(stringBuffer, tabIndent, indentLevel, indentCount);

				/* close that tag */
				if (includeElementName)
					stringBuffer.append("</").append(nodeName).append('>');
			}
		}
		else
		{
			if (includeElementName)
				stringBuffer.append(">");

			final boolean inlineMyChildren = inline || inlineElements.contains(nodeName) || contentsInlineElements.contains(nodeName);
			final boolean verbatimMyChildren = verbatim || verbatimElements.contains(nodeName);

			for (int i = 0; i < children.getLength(); ++i)
			{
				final String childToString = convertNodeToString(children.item(i), true, verbatimMyChildren, inlineMyChildren, verbatimElements, inlineElements, contentsInlineElements, tabIndent, indentCount, indentLevel + 1);
				if (childToString.length() != 0)
					stringBuffer.append(childToString);
			}

			/* close that tag */
			if (includeElementName)
			{
				/* indent */
				if (!verbatimMyChildren && !inlineMyChildren)
					appendIndent(stringBuffer, tabIndent, indentLevel, indentCount);

				stringBuffer.append("</").append(nodeName).append('>');
			}
		}

		return stringBuffer.toString();
	}

	/**
	 * Scans a node and all of its children for nodes of a particular type.
	 * 
	 * @param parent
	 *            The parent node
	 * @param nodeName
	 *            The node name to search for
	 * @return a List of all the nodes found matching the nodeName under the
	 *         parent
	 */
	public static List<Node> getNodes(final Node parent, final String nodeName)
	{
		final List<Node> nodes = new ArrayList<Node>();
		final NodeList children = parent.getChildNodes();
		for (int i = 0; i < children.getLength(); ++i)
		{
			final Node child = children.item(i);

			if (child.getNodeName().equals(nodeName))
			{
				nodes.add(child);
			}
			else
			{
				nodes.addAll(getNodes(child, nodeName));
			}
		}
		return nodes;
	}

	public static List<Node> getComments(final Node parent)
	{
		return getNodes(parent, "#comment");
	}

	public static List<StringToNodeCollection> getTranslatableStrings(final Document xml, final boolean allowDuplicates)
	{
		if (xml == null)
			return null;

		final List<StringToNodeCollection> retValue = new ArrayList<StringToNodeCollection>();

		final NodeList nodes = xml.getDocumentElement().getChildNodes();
		for (int i = 0; i < nodes.getLength(); ++i)
		{
			final Node node = nodes.item(i);
			getTranslatableStringsFromNode(node, retValue, allowDuplicates);
		}

		return retValue;
	}

	private static boolean doesElementContainTranslatableContent(final Node node)
	{
		final NodeList children = node.getChildNodes();
		if (children != null)
		{
			/* check to see if any of the children are translatable nodes */
			for (int j = 0; j < children.getLength(); ++j)
			{
				final Node child = children.item(j);
				final String childName = child.getNodeName();

				/* this child node is itself translatable, so return true */
				if (TRANSLATABLE_ELEMENTS.contains(childName))
					return true;
			}

			/*
			 * now check to see if any of the child have children that are
			 * translatable
			 */
			for (int j = 0; j < children.getLength(); ++j)
			{
				final Node child = children.item(j);
				final NodeList grandChildren = child.getChildNodes();
				for (int k = 0; k < grandChildren.getLength(); ++k)
				{
					final Node grandChild = grandChildren.item(k);
					final boolean result = doesElementContainTranslatableContent(grandChild);
					if (result)
						return true;
				}
			}
		}

		return false;
	}

	private static void getTranslatableStringsFromNode(final Node node, final List<StringToNodeCollection> translationStrings, final boolean allowDuplicates)
	{
		if (node == null || translationStrings == null)
			return;

		final String nodeName = node.getNodeName();
		final String nodeParentName = node.getParentNode() != null ? node.getParentNode().getNodeName() : null;

		final boolean textElement = node.getNodeType() == Node.TEXT_NODE;
		final boolean translatableElement = TRANSLATABLE_ELEMENTS.contains(nodeName);
		final boolean standaloneElement = TRANSLATABLE_IF_STANDALONE_ELEMENTS.contains(nodeName);
		final boolean translatableParentElement = TRANSLATABLE_ELEMENTS.contains(nodeParentName);
		final boolean inlineElement = INLINE_ELEMENTS.contains(nodeName);
		final boolean verbatimElement = VERBATIM_ELEMENTS.contains(nodeName);

		/*
		 * this element has translatable strings if:
		 * 
		 * 1. a text node
		 * 
		 * OR
		 * 
		 * 2. a translatableElement
		 * 
		 * 3. a standaloneElement without a translatableParentElement
		 * 
		 * 4. not a standaloneElement and not an inlineElement
		 */

		if (textElement || (translatableElement && ((standaloneElement && !translatableParentElement) || (!standaloneElement && !inlineElement))))
		{
			final NodeList children = node.getChildNodes();
			final boolean hasChildren = children == null || children.getLength() != 0;

			/* dump the node if it has no children */
			if (!hasChildren)
			{
				final String nodeText = convertNodeToString(node, false);
				final String cleanedNodeText = cleanTranslationText(nodeText, true, true);

				if (verbatimElement)
				{
					addTranslationToNodeDetailsToCollection(nodeText, node, allowDuplicates, translationStrings);
				}
				else if (!cleanedNodeText.isEmpty())
				{
					addTranslationToNodeDetailsToCollection(cleanedNodeText, node, allowDuplicates, translationStrings);
				}

			}
			/*
			 * dump all child nodes until we hit one that itself contains a
			 * translatable element. in effect the translation strings can
			 * contain up to one level of xml elements.
			 */
			else
			{
				ArrayList<Node> nodes = new ArrayList<Node>();
				String translatableString = "";

				final int childrenLength = children.getLength();
				for (int i = 0; i < childrenLength; ++i)
				{
					final Node child = children.item(i);

					/*
					 * does this child have another level of translatable tags?
					 */
					boolean containsTranslatableTags = doesElementContainTranslatableContent(child);

					/*
					 * if so, save the string we have been building up, process
					 * the child, and start building up a new string
					 */
					if (containsTranslatableTags)
					{
						if (nodes.size() != 0)
						{
							/*
							 * We have found a child node that itself contains
							 * some translatable children. In this case we
							 * create a new translatable string. It is possible
							 * that the translatableString has some
							 * insignificant trailing whitespace, because the
							 * call to the cleanTranslationText function in the
							 * else statement below has assumed that the node
							 * being processed was not the last one in the
							 * translatable string, making the trailing
							 * whitespace important. So we clean up the trailing
							 * whitespace here.
							 */

							final Matcher matcher = TRAILING_WHITESPACE_RE_PATTERN.matcher(translatableString);
							if (matcher.matches())
								translatableString = matcher.group("content");

							addTranslationToNodeDetailsToCollection(translatableString, nodes, allowDuplicates, translationStrings);

							translatableString = "";
							nodes = new ArrayList<Node>();
						}

						getTranslatableStringsFromNode(child, translationStrings, allowDuplicates);
					}
					else
					{
						final String childName = child.getNodeName();
						final String childText = convertNodeToString(child, true);

						final String cleanedChildText = cleanTranslationText(childText, i == 0, i == childrenLength - 1);
						final boolean isVerbatimNode = VERBATIM_ELEMENTS.contains(childName);

						final String thisTranslatableString = isVerbatimNode ? childText : cleanedChildText;

						translatableString += thisTranslatableString;
						nodes.add(child);
					}

				}

				/* save the last translated string */
				if (nodes.size() != 0)
				{
					addTranslationToNodeDetailsToCollection(translatableString, nodes, allowDuplicates, translationStrings);

					translatableString = "";
					nodes = new ArrayList<Node>();
				}
			}
		}
		else
		{
			/* if we hit a non-translatable element, process its children */
			final NodeList nodeList = node.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); ++i)
			{
				final Node child = nodeList.item(i);
				getTranslatableStringsFromNode(child, translationStrings, allowDuplicates);
			}
		}
	}

	public static void replaceTranslatedStrings(final Document xml, final Map<String, String> translations)
	{
		if (xml == null || translations == null || translations.size() == 0)
			return;

		/*
		 * Get the translation strings and the nodes that the string maps to. We
		 * assume that the xml being provided here is an exacth match for the
		 * xml that was supplied to getTranslatableStrings originally, which we
		 * then assume matches the strings supplied as the keys in the
		 * translations parameter.
		 */
		final List<StringToNodeCollection> stringToNodeCollections = getTranslatableStrings(xml, false);

		if (stringToNodeCollections == null || stringToNodeCollections.size() == 0)
			return;

		for (final StringToNodeCollection stringToNodeCollection : stringToNodeCollections)
		{
			final String originalString = stringToNodeCollection.getTranslationString();
			final ArrayList<ArrayList<Node>> nodeCollections = stringToNodeCollection.getNodeCollections();

			if (nodeCollections != null && nodeCollections.size() != 0)
			{
				if (translations.containsKey(originalString))
				{
					final String translation = translations.get(originalString);

					/* wrap the returned translation in a root element */
					final String wrappedTranslation = "<tempRoot>" + translation + "</tempRoot>";

					/* convert the wrapped translation into an XML document */
					final Document translationDocument = convertStringToDocument(wrappedTranslation);

					/* was the conversion successful */
					if (translationDocument != null)
					{
						for (final ArrayList<Node> nodes : nodeCollections)
						{
							if (nodes != null && nodes.size() != 0)
							{
								/*
								 * All nodes in a collection should share the
								 * same parent
								 */
								final Node parent = nodes.get(0).getParentNode();

								if (parent != null)
								{
									/*
									 * Start by inserting the nodes created when
									 * we converted the translated text into
									 * XML. Do it in reverse order, because
									 * that's the easiest solution for appending
									 * to the start of the element in the
									 * original order.
									 */
									final NodeList translatedChildren = translationDocument.getDocumentElement().getChildNodes();
									for (int i = translatedChildren.getLength() - 1; i >= 0; --i)
									{
										/*
										 * import the node from the translated
										 * xml "fragment"
										 */
										final Node translatedNode = xml.importNode(translatedChildren.item(i), true);
										/*
										 * insert it into the xml doc to be
										 * translated
										 */
										parent.insertBefore(translatedNode, parent.getFirstChild());
									}

									/*
									 * remove the original nodes that the
									 * translated text came from
									 */
									for (final Node node : nodes)
									{
										if (parent == node.getParentNode())
											parent.removeChild(node);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	private static StringToNodeCollection findExistingText(final String text, final List<StringToNodeCollection> translationStrings)
	{
		for (final StringToNodeCollection stringToNodeCollection : translationStrings)
		{
			if (stringToNodeCollection.getTranslationString().equals(text))
				return stringToNodeCollection;
		}

		return null;
	}

	private static void addTranslationToNodeDetailsToCollection(final String text, final Node node, final boolean allowDuplicates, final List<StringToNodeCollection> translationStrings)
	{
		final ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(node);
		addTranslationToNodeDetailsToCollection(text, nodes, allowDuplicates, translationStrings);
	}

	private static void addTranslationToNodeDetailsToCollection(final String text, final ArrayList<Node> nodes, final boolean allowDuplicates, final List<StringToNodeCollection> translationStrings)
	{

		if (allowDuplicates)
		{
			translationStrings.add(new StringToNodeCollection(text).addNodeCollection(nodes));
		}
		else
		{
			final StringToNodeCollection stringToNodeCollection = findExistingText(text, translationStrings);

			if (stringToNodeCollection == null)
				translationStrings.add(new StringToNodeCollection(text).addNodeCollection(nodes));
			else
				stringToNodeCollection.addNodeCollection(nodes);
		}
	}

	/** Cleans a string for presentation to a translator */
	private static String cleanTranslationText(final String input, final boolean removeWhitespaceFromStart, final boolean removeWhitespaceFromEnd)
	{
		String retValue = cleanText(input);

		final boolean hasStartWhiteSpace = PRECEEDING_WHITESPACE_SIMPLE_RE_PATTERN.matcher(input).matches();
		final boolean hasEndWhiteSpace = TRAILING_WHITESPACE_SIMPLE_RE_PATTERN.matcher(input).matches();

		retValue = retValue.trim();

		/*
		 * When presenting the contents of a childless XML node to the
		 * translator, there is no need for white space padding. When building
		 * up a translatable string from a succession of text nodes, whitespace
		 * becomes important.
		 */
		if (!removeWhitespaceFromStart)
		{
			if (hasStartWhiteSpace)
				retValue = " " + retValue;
		}

		if (!removeWhitespaceFromEnd)
		{
			if (hasEndWhiteSpace)
				retValue += " ";
		}

		return retValue;
	}

	/** Cleans a string for of insignificant whitespace */
	private static String cleanText(final String input)
	{
		/* get rid of line breaks */
		String retValue = input.replaceAll("\\r\\n|\\r|\\n|\\t", " ");
		/* get rid of double spaces */
		while (retValue.indexOf("  ") != -1)
			retValue = retValue.replaceAll("  ", " ");

		return retValue;
	}

	/**
	 * CDATA sections can not have a "]]>" in them. This method takes the input
	 * and wraps it up in one or more CDATA sections, converting any "]]>"
	 * strings into "]]&gt;".
	 */
	public static String wrapStringInCDATA(final String input)
	{
		final StringBuffer retValue = new StringBuffer("<![CDATA[");
		retValue.append(input.replaceAll(END_CDATA_RE, END_CDATA_RE + END_CDATA_REPLACE + START_CDATA));
		retValue.append("]]>");
		return retValue.toString();
	}
}
