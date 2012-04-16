package com.redhat.topicindex.rest.entities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redhat.ecs.commonstructures.NameIDSortMap;
import com.redhat.ecs.commonutils.XMLUtilities;
import com.redhat.ecs.constants.CommonConstants;
import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.sort.TagV1NameComparator;

/**
 * A REST representation of the Topic entity
 */
@XmlRootElement(name = "topic")
public class TopicV1 extends BaseRESTEntityWithPropertiesV1<TopicV1>
{
	public static final String TITLE_NAME = "title";
	public static final String DESCRIPTION_NAME = "description";
	public static final String XML_NAME = "xml";
	public static final String XML_ERRORS_NAME = "xmlErrors";
	public static final String HTML_NAME = "html";
	public static final String TAGS_NAME = "tags";
	public static final String OUTGOING_NAME = "outgoingRelationships";
	public static final String INCOMING_NAME = "incomingRelationships";
	public static final String LOCALE_NAME = "locale";
	public static final String SOURCE_URLS_NAME = "sourceUrls_OTM";
	public static final String BUGZILLABUGS_NAME = "bugzillabugs_OTM";
	public static final String PROPERTIES_NAME = "properties";

	private String title = null;
	private String description = null;
	private String xml = null;
	private String xmlErrors = null;
	private String html = null;
	private Date lastModified = null;
	private Date created = null;
	private Number revision = 0;
	private String locale = null;
	private BaseRestCollectionV1<TagV1> tags = null;
	private BaseRestCollectionV1<TopicV1> outgoingRelationships = null;
	private BaseRestCollectionV1<TopicV1> incomingRelationships = null;
	private BaseRestCollectionV1<TopicSourceUrlV1> sourceUrls = null;
	private BaseRestCollectionV1<BugzillaBugV1> bugzillaBugs = null;

	@XmlElement
	public Number getRevision()
	{
		return revision;
	}

	public void setRevision(final Number revision)
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

	public void setTitleExplicit(final String title)
	{
		this.title = title;
		setParamaterToConfigured(TITLE_NAME);
	}

	@XmlElement
	public String getDescription()
	{
		return description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
	}

	public void setDescriptionExplicit(final String description)
	{
		this.description = description;
		setParamaterToConfigured(DESCRIPTION_NAME);
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

	public void setXmlExplicit(final String xml)
	{
		this.xml = xml;
		setParamaterToConfigured(XML_NAME);
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

	public void setHtmlExplicit(final String html)
	{
		this.html = html;
		setParamaterToConfigured(HTML_NAME);
	}

	@XmlElement
	public BaseRestCollectionV1<TagV1> getTags()
	{
		return tags;
	}

	public void setTags(final BaseRestCollectionV1<TagV1> tags)
	{
		this.tags = tags;
	}

	public void setTagsExplicit(final BaseRestCollectionV1<TagV1> tags)
	{
		this.tags = tags;
		setParamaterToConfigured(TAGS_NAME);
	}

	@XmlElement
	public BaseRestCollectionV1<TopicV1> getOutgoingRelationships()
	{
		return outgoingRelationships;
	}

	public void setOutgoingRelationships(final BaseRestCollectionV1<TopicV1> outgoingRelationships)
	{
		this.outgoingRelationships = outgoingRelationships;
	}

	public void setOutgoingRelationshipsExplicit(final BaseRestCollectionV1<TopicV1> outgoingRelationships)
	{
		this.outgoingRelationships = outgoingRelationships;
		setParamaterToConfigured(OUTGOING_NAME);
	}

	@XmlElement
	public BaseRestCollectionV1<TopicV1> getIncomingRelationships()
	{
		return incomingRelationships;
	}

	public void setIncomingRelationships(final BaseRestCollectionV1<TopicV1> incomingRelationships)
	{
		this.incomingRelationships = incomingRelationships;
	}

	public void setIncomingRelationshipsExplicit(final BaseRestCollectionV1<TopicV1> incomingRelationships)
	{
		this.incomingRelationships = incomingRelationships;
		setParamaterToConfigured(INCOMING_NAME);
	}

	/**
	 * @return the XML contained in a new element, or null if the XML is not
	 *         valid
	 */
	@XmlTransient
	@JsonIgnore
	public String getXMLWithNewContainer(final String containerName)
	{
		assert containerName != null : "The containerName parameter can not be null";

		final Document document = XMLUtilities.convertStringToDocument(this.xml);

		if (document == null)
			return null;

		final Element newElement = document.createElement(containerName);
		final Element documentElement = document.getDocumentElement();

		document.removeChild(documentElement);
		document.appendChild(newElement);
		newElement.appendChild(documentElement);

		return XMLUtilities.convertDocumentToString(document);
	}

	@XmlTransient
	@JsonIgnore
	public String getXMLWithNoContainer(final Boolean includeTitle)
	{
		final Document document = XMLUtilities.convertStringToDocument(this.xml);

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

	public Date getLastModified()
	{
		return lastModified;
	}

	public void setLastModified(final Date lastModified)
	{
		this.lastModified = lastModified;
	}

	public Date getCreated()
	{
		return created;
	}

	public void setCreated(Date created)
	{
		this.created = created;
	}

	@XmlTransient
	@JsonIgnore
	public String getXRefID()
	{
		return "TopicID" + this.getId();
	}
	

	@XmlTransient
	@JsonIgnore
	public String getTopicSkynetURL()
	{
		return CommonConstants.SERVER_URL + "/TopicIndex/CustomSearchTopicList.seam?topicIds=" + this.getId();
	}

	@XmlTransient
	@JsonIgnore
	public boolean isTaggedWith(final Integer tagID)
	{
		if (this.getTags() != null && this.getTags().getItems() != null)
		{
			for (final TagV1 tag : this.getTags().getItems())
			{
				if (tag.getId().equals(tagID))
					return true;
			}
		}

		return false;
	}

	@XmlTransient
	@JsonIgnore
	public TopicV1 getRelatedTopicByID(final Integer id)
	{
		if (this.getOutgoingRelationships() != null && this.getOutgoingRelationships().getItems() != null)
			for (final TopicV1 topic : this.getOutgoingRelationships().getItems())
				if (topic.getId().equals(id))
					return topic;
		return null;
	}

	@XmlTransient
	@JsonIgnore
	public boolean isRelatedTo(final Integer id)
	{
		return getRelatedTopicByID(id) != null;
	}

	@XmlTransient
	@JsonIgnore
	public List<TagV1> getTagsInCategoriesByID(final List<Integer> categories)
	{
		final List<TagV1> retValue = new ArrayList<TagV1>();

		if (this.getTags() != null && this.getTags().getItems() != null)
		{
			for (final Integer categoryId : categories)
			{
				for (final TagV1 tag : this.tags.getItems())
				{
					if (tag.isInCategory(categoryId))
					{
						if (!retValue.contains(tag))
							retValue.add(tag);
					}
				}
			}
		}

		return retValue;
	}

	@XmlTransient
	@JsonIgnore
	public int getTagsInCategory(final Integer categoryId)
	{
		int retValue = 0;

		if (this.getTags() != null && this.getTags().getItems() != null)
		{
			for (final TagV1 tag : this.getTags().getItems())
			{
				if (tag.isInCategory(categoryId))
					++retValue;
			}
		}

		return retValue;
	}

	/**
	 * @return The value to be saved into the Build ID field of any bugzilla
	 *         bugs assigned to this topic.
	 */
	@XmlTransient
	@JsonIgnore
	public String getBugzillaBuildId()
	{
		final SimpleDateFormat formatter = new SimpleDateFormat(CommonConstants.FILTER_DISPLAY_DATE_FORMAT);
		return this.getId() + "-" + this.revision + " " + (this.lastModified == null ? formatter.format(this.lastModified) : formatter.format(new Date())) + " " + this.locale;
	}

	public void addTag(final TagV1 tag)
	{
		if (this.tags == null)
			this.tags = new BaseRestCollectionV1<TagV1>();

		this.tags.addItem(tag);
	}

	@XmlTransient
	@JsonIgnore
	public String getCommaSeparatedTagList()
	{
		final TreeMap<NameIDSortMap, ArrayList<TagV1>> tags = getCategoriesMappedToTags();

		String tagsList = "";
		for (final NameIDSortMap key : tags.keySet())
		{
			// sort alphabetically
			Collections.sort(tags.get(key), new TagV1NameComparator());

			if (tagsList.length() != 0)
				tagsList += " ";

			tagsList += key.getName() + ": ";

			String thisTagList = "";

			for (final TagV1 tag : tags.get(key))
			{
				if (thisTagList.length() != 0)
					thisTagList += ", ";

				thisTagList += tag.getName();
			}

			tagsList += thisTagList + " ";
		}

		return tagsList;
	}

	@XmlTransient
	@JsonIgnore
	private TreeMap<NameIDSortMap, ArrayList<TagV1>> getCategoriesMappedToTags()
	{
		final TreeMap<NameIDSortMap, ArrayList<TagV1>> tags = new TreeMap<NameIDSortMap, ArrayList<TagV1>>();

		if (this.tags != null && this.tags.getItems() != null)
		{
			for (final TagV1 tag : this.tags.getItems())
			{
				if (tag.getCategories() != null && tag.getCategories().getItems() != null)
				{
					final List<CategoryV1> categories = tag.getCategories().getItems();

					if (categories.size() == 0)
					{
						final NameIDSortMap categoryDetails = new NameIDSortMap("Uncatagorised", -1, 0);

						if (!tags.containsKey(categoryDetails))
							tags.put(categoryDetails, new ArrayList<TagV1>());

						tags.get(categoryDetails).add(tag);
					}
					else
					{
						for (final CategoryV1 category : categories)
						{
							final NameIDSortMap categoryDetails = new NameIDSortMap(category.getName(), category.getId(), category.getSort() == null ? 0 : category.getSort());

							if (!tags.containsKey(categoryDetails))
								tags.put(categoryDetails, new ArrayList<TagV1>());

							tags.get(categoryDetails).add(tag);
						}
					}
				}
			}
		}

		return tags;
	}

	@XmlElement
	public BaseRestCollectionV1<TopicSourceUrlV1> getSourceUrls_OTM()
	{
		return sourceUrls;
	}

	public void setSourceUrls_OTM(final BaseRestCollectionV1<TopicSourceUrlV1> sourceUrls)
	{
		this.sourceUrls = sourceUrls;		
	}
	
	public void setSourceUrlsExplicit_OTM(final BaseRestCollectionV1<TopicSourceUrlV1> sourceUrls)
	{
		this.sourceUrls = sourceUrls;
		setParamaterToConfigured(SOURCE_URLS_NAME);
	}

	public String getLocale()
	{
		return locale;
	}

	public void setLocale(final String locale)
	{
		this.locale = locale;
	}
	
	public void setLocaleExplicit(final String locale)
	{
		this.locale = locale;
		setParamaterToConfigured(LOCALE_NAME);
	}

	public String getXmlErrors()
	{
		return xmlErrors;
	}

	public void setXmlErrors(final String xmlErrors)
	{
		this.xmlErrors = xmlErrors;
	}
	
	public void setXmlErrorsExplicit(final String xmlErrors)
	{
		this.xmlErrors = xmlErrors;
		setParamaterToConfigured(XML_ERRORS_NAME);
	}

	public BaseRestCollectionV1<BugzillaBugV1> getBugzillaBugs_OTM()
	{
		return bugzillaBugs;
	}

	public void setBugzillaBugs_OTM(final BaseRestCollectionV1<BugzillaBugV1> bugzillaBugs)
	{
		this.bugzillaBugs = bugzillaBugs;
	}
	
	public void setBugzillaBugsExplicit_OTM(final BaseRestCollectionV1<BugzillaBugV1> bugzillaBugs)
	{
		this.bugzillaBugs = bugzillaBugs;
		setParamaterToConfigured(BUGZILLABUGS_NAME);
	}
}
