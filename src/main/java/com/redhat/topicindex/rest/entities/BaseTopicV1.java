package com.redhat.topicindex.rest.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlElement;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.redhat.ecs.commonstructures.NameIDSortMap;
import com.redhat.ecs.commonutils.ExceptionUtilities;
import com.redhat.ecs.commonutils.XMLUtilities;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.interfaces.IBaseTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.ICategoryV1;
import com.redhat.topicindex.rest.entities.interfaces.IPropertyTagV1;
import com.redhat.topicindex.rest.entities.interfaces.ITagV1;
import com.redhat.topicindex.rest.entities.interfaces.ITopicSourceUrlV1;
import com.redhat.topicindex.rest.sort.TagV1NameComparator;

public abstract class BaseTopicV1<T extends IBaseTopicV1<T>> extends BaseRESTEntityWithPropertiesV1<T> implements IBaseTopicV1<T>
{
	private String title = null;
	private String xml = null;
	private String xmlErrors = null;
	private String html = null;
	private Integer revision = 0;
	private String locale = null;
	private BaseRestCollectionV1<ITagV1> tags = null;
	private BaseRestCollectionV1<T> outgoingRelationships = null;
	private BaseRestCollectionV1<T> incomingRelationships = null;
	private BaseRestCollectionV1<ITopicSourceUrlV1> sourceUrls = null;
	
	public void cloneInto(final BaseTopicV1<T> clone, final boolean deepCopy)
	{
		super.cloneInto(clone, deepCopy);
		
		clone.title = this.title;
		clone.xml = this.xml;
		clone.xmlErrors = this.xmlErrors;
		clone.html = this.html;
		clone.revision = this.revision;
		clone.locale = this.locale;
		
		if (deepCopy)
		{
			clone.tags = this.tags == null ? null: this.tags.clone(deepCopy);
			clone.outgoingRelationships = this.outgoingRelationships == null ? null : this.outgoingRelationships.clone(deepCopy);
			clone.incomingRelationships = this.incomingRelationships == null ? null : this.incomingRelationships.clone(deepCopy);
			clone.sourceUrls = this.sourceUrls == null ? null : this.sourceUrls.clone(deepCopy);
		}
		else
		{
			clone.tags = this.tags;
			clone.outgoingRelationships = this.outgoingRelationships;
			clone.incomingRelationships = this.incomingRelationships;
			clone.sourceUrls = this.sourceUrls;
		}
	}
	
	@Override
	@XmlElement
	public Integer getRevision()
	{
		return revision;
	}

	@Override
	public void setRevision(final Integer revision)
	{
		this.revision = revision;
	}

	@XmlElement
	public String getTitle()
	{
		return title;
	}

	public void setTitle(final String title)
	{
		this.title = title;
	}
	
	public void explicitSetTitle(final String title)
	{
		setTitle(title);
		setParamaterToConfigured(TITLE_NAME);
	}
	
	@XmlElement
	public String getXml()
	{
		return xml;
	}
	
	public void setXml(final String xml)
	{
		this.xml = xml;
	}
	
	@XmlElement
	public String getHtml()
	{
		return html;
	}

	public void setHtml(final String html)
	{
		this.html = html;
	}
	
	@XmlElement
	public String getLocale()
	{
		return locale;
	}

	public void setLocale(final String locale)
	{
		this.locale = locale;
	}

	public String getXmlErrors()
	{
		return xmlErrors;
	}

	public void setXmlErrors(final String xmlErrors)
	{
		this.xmlErrors = xmlErrors;
	}
	
	@XmlElement
	public BaseRestCollectionV1<ITagV1> getTags()
	{
		return tags;
	}
	
	public void explicitSetTags(final BaseRestCollectionV1<ITagV1> tags)
	{
		setTags(tags);
		setParamaterToConfigured(TAGS_NAME);
	}

	public void setTags(final BaseRestCollectionV1<ITagV1> tags)
	{
		this.tags = tags;
	}
	
	public void addTag(final ITagV1 tag)
	{
		if (this.tags == null)
			this.tags = new BaseRestCollectionV1<ITagV1>();

		this.tags.addItem(tag);
	}
	
	@XmlElement
	public BaseRestCollectionV1<T> getOutgoingRelationships()
	{
		return outgoingRelationships;
	}

	public void setOutgoingRelationships(final BaseRestCollectionV1<T> outgoingRelationships)
	{
		this.outgoingRelationships = outgoingRelationships;
	}
	
	public void explicitSetOutgoingRelationships(final BaseRestCollectionV1<T> outgoingRelationships)
	{
		setOutgoingRelationships(outgoingRelationships);
		setParamaterToConfigured(OUTGOING_NAME);
	}

	@XmlElement
	public BaseRestCollectionV1<T> getIncomingRelationships()
	{
		return incomingRelationships;
	}

	public void setIncomingRelationships(final BaseRestCollectionV1<T> incomingRelationships)
	{
		this.incomingRelationships = incomingRelationships;
	}
	
	public void explicitSetIncomingRelationships(final BaseRestCollectionV1<T> incomingRelationships)
	{
		setIncomingRelationships(incomingRelationships);
		setParamaterToConfigured(INCOMING_NAME);
	}

	@XmlElement
	public BaseRestCollectionV1<ITopicSourceUrlV1> getSourceUrls_OTM()
	{
		return sourceUrls;
	}

	public void setSourceUrls_OTM(final BaseRestCollectionV1<ITopicSourceUrlV1> sourceUrls)
	{
		this.sourceUrls = sourceUrls;		
	}
	
	public void explicitSetSourceUrls_OTM(final BaseRestCollectionV1<ITopicSourceUrlV1> sourceUrls)
	{
		setSourceUrls_OTM(sourceUrls);
		setParamaterToConfigured(SOURCE_URLS_NAME);
	}
	
	/**
	 * @return the XML contained in a new element, or null if the XML is not
	 *         valid
	 */
	public String returnXMLWithNewContainer(final String containerName)
	{
		assert containerName != null : "The containerName parameter can not be null";

		Document document = null;
		try {
			document = XMLUtilities.convertStringToDocument(this.xml);
		} catch (SAXException ex) {
			ExceptionUtilities.handleException(ex);
		}

		if (document == null)
			return null;

		final Element newElement = document.createElement(containerName);
		final Element documentElement = document.getDocumentElement();

		document.removeChild(documentElement);
		document.appendChild(newElement);
		newElement.appendChild(documentElement);

		return XMLUtilities.convertDocumentToString(document);
	}

	public String returnXMLWithNoContainer(final Boolean includeTitle)
	{
		Document document = null;
		try {
			document = XMLUtilities.convertStringToDocument(this.xml);
		} catch (SAXException ex) {
			ExceptionUtilities.handleException(ex);
		}

		if (document == null)
			return null;

		String retValue = "";

		final NodeList nodes = document.getDocumentElement().getChildNodes();

		for (int i = 0; i < nodes.getLength(); ++i)
		{
			final Node node = nodes.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE)
			{

				if (includeTitle != null && !includeTitle)
				{
					if (node.getNodeName().equals("title"))
					{
						continue;
					}
				}

				retValue += XMLUtilities.convertNodeToString(node, true);
			}
		}

		return retValue;

	}
	
	public String returnXrefPropertyOrId(final Integer propertyTagId)
	{
		final IPropertyTagV1 propTag = this.returnProperty(propertyTagId);
		if (propTag != null)
		{
			return propTag.getValue();
		}
		else
		{
			return this.returnXRefID();
		}
	}
	
	@JsonIgnore
	public String getCommaSeparatedTagList()
	{
		final TreeMap<NameIDSortMap, ArrayList<ITagV1>> tags = getCategoriesMappedToTags();

		String tagsList = "";
		for (final NameIDSortMap key : tags.keySet())
		{
			// sort alphabetically
			Collections.sort(tags.get(key), new TagV1NameComparator());

			if (tagsList.length() != 0)
				tagsList += " ";

			tagsList += key.getName() + ": ";

			String thisTagList = "";

			for (final ITagV1 tag : tags.get(key))
			{
				if (thisTagList.length() != 0)
					thisTagList += ", ";

				thisTagList += tag.getName();
			}

			tagsList += thisTagList + " ";
		}

		return tagsList;
	}

	@JsonIgnore
	private TreeMap<NameIDSortMap, ArrayList<ITagV1>> getCategoriesMappedToTags()
	{
		final TreeMap<NameIDSortMap, ArrayList<ITagV1>> tags = new TreeMap<NameIDSortMap, ArrayList<ITagV1>>();

		if (this.tags != null && this.tags.getItems() != null)
		{
			for (final ITagV1 tag : this.tags.getItems())
			{
				if (tag.getCategories() != null && tag.getCategories().getItems() != null)
				{
					final List<ICategoryV1> categories = tag.getCategories().getItems();

					if (categories.size() == 0)
					{
						final NameIDSortMap categoryDetails = new NameIDSortMap("Uncatagorised", -1, 0);

						if (!tags.containsKey(categoryDetails))
							tags.put(categoryDetails, new ArrayList<ITagV1>());

						tags.get(categoryDetails).add(tag);
					}
					else
					{
						for (final ICategoryV1 category : categories)
						{
							final NameIDSortMap categoryDetails = new NameIDSortMap(category.getName(), category.getId(), category.getSort() == null ? 0 : category.getSort());

							if (!tags.containsKey(categoryDetails))
								tags.put(categoryDetails, new ArrayList<ITagV1>());

							tags.get(categoryDetails).add(tag);
						}
					}
				}
			}
		}

		return tags;
	}
	
	public List<ITagV1> returnTagsInCategoriesByID(final List<Integer> categories)
	{
		final List<ITagV1> retValue = new ArrayList<ITagV1>();

		if (this.getTags() != null && this.getTags().getItems() != null)
		{
			for (final Integer categoryId : categories)
			{
				for (final ITagV1 tag : this.tags.getItems())
				{
					if (tag.containedInCatgeory(categoryId))
					{
						if (!retValue.contains(tag))
							retValue.add(tag);
					}
				}
			}
		}

		return retValue;
	}

	@JsonIgnore
	public int getTagsInCategory(final Integer categoryId)
	{
		int retValue = 0;

		if (this.getTags() != null && this.getTags().getItems() != null)
		{
			for (final ITagV1 tag : this.getTags().getItems())
			{
				if (tag.containedInCatgeory(categoryId))
					++retValue;
			}
		}

		return retValue;
	}
	
	public T returnRelatedTopicByID(final Integer id)
	{
		if (this.getOutgoingRelationships() != null && this.getOutgoingRelationships().getItems() != null)
			for (final T topic : this.getOutgoingRelationships().getItems())
				if (topic.getId().equals(id))
					return topic;
		return null;
	}

	public boolean hasRelationshipTo(final Integer id)
	{
		return returnRelatedTopicByID(id) != null;
	}
	

	public boolean hasTag(final Integer tagID)
	{
		if (this.getTags() != null && this.getTags().getItems() != null)
		{
			for (final ITagV1 tag : this.getTags().getItems())
			{
				if (tag.getId().equals(tagID))
					return true;
			}
		}

		return false;
	}
	
	@JsonIgnore
	public boolean isDummyTopic()
	{
		return getId() == null || getId() < 0;
	}
	

}
