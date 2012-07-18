package com.redhat.ecs.commonutils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.xalan.processor.TransformerFactoryImpl;

public class XSLTUtilities
{
	private static Map<String, Templates> templates = new ConcurrentHashMap<String, Templates>();

	public static String transformXML(final String xml, final String xsl, final String xslSystemId, final Map<String, byte[]> resources) throws TransformerException
	{
		return transformXML(xml, xsl, xslSystemId, resources, new HashMap<String, String>());
	}

	public static String transformXML(final String xml, final String xsl, final String xslSystemId, final Map<String, byte[]> resources, final Map<String, String> globalParameters) throws TransformerException
	{
		if (xml == null || xml.trim().length() == 0)
			return null;

		if (xsl == null || xsl.trim().length() == 0)
			return null;

		if (resources == null)
			return null;

		try
		{
			final ByteArrayInputStream xmlStream = new ByteArrayInputStream(xml.getBytes("UTF-8"));
			final ByteArrayInputStream xslStream = new ByteArrayInputStream(xsl.getBytes("UTF-8"));
			final ByteArrayOutputStream retValueStream = new ByteArrayOutputStream();

			/* http://xml.apache.org/xalan-j/usagepatterns.html#basic */

			Templates template = null;
			synchronized (templates)
			{
				if (templates.containsKey(xslSystemId))
				{
					template = templates.get(xslSystemId);
				}
				else
				{
					System.out.println("Initialising Templates for " + xslSystemId);

					/*
					 * Instantiate a TransformerFactory. make sure to get a
					 * org.apache.xalan.processor.TransformerFactoryImpl instead
					 * of the default
					 * org.apache.xalan.xsltc.trax.TransformerFactoryImpl. The
					 * latter doesn't work for docbook xsl.
					 */
					final TransformerFactory transformerFactory = TransformerFactory.newInstance("org.apache.xalan.processor.TransformerFactoryImpl", null);

					/*
					 * Set the URIResolver that will handle request to external
					 * resources
					 */
					transformerFactory.setURIResolver(new XSLTResolver(resources));

					/*
					 * see http://nlp.stanford.edu/nlp/javadoc/xalan-docs/
					 * extensionslib .html#nodeinfo
					 */
					transformerFactory.setAttribute(TransformerFactoryImpl.FEATURE_SOURCE_LOCATION, Boolean.TRUE);
					// transformerFactory.setAttribute(TransformerFactoryImpl.FEATURE_INCREMENTAL,
					// Boolean.TRUE);

					final StreamSource xslStreamSource = new StreamSource(xslStream);
					xslStreamSource.setSystemId(xslSystemId);

					/* save the template */
					templates.put(xslSystemId, transformerFactory.newTemplates(xslStreamSource));
					
					System.out.println("Done Initialising Templates for " + xslSystemId);
				}

				template = templates.get(xslSystemId);
			}

			/*
			 * Use the TransformerFactory to process the stylesheet Source and
			 * generate a Transformer.
			 */
			final Transformer transformer = template.newTransformer();

			/* set the global variables */
			if (globalParameters != null)
				for (final String varibleName : globalParameters.keySet())
					transformer.setParameter(varibleName, globalParameters.get(varibleName));

			/*
			 * Use the Transformer to transform an XML Source and send the
			 * output to a Result object.
			 */
			transformer.transform(new StreamSource(xmlStream), new StreamResult(retValueStream));

			return retValueStream.toString();

		}
		catch (final TransformerException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			ExceptionUtilities.handleException(ex);
		}

		return null;
	}
}

/**
 * A class to get the various xsl resources that might be imported.
 */
class XSLTResolver implements URIResolver
{
	private Map<String, byte[]> resources;

	public XSLTResolver(final Map<String, byte[]> resources)
	{
		this.resources = resources;
	}

	public Source resolve(final String href, final String base) throws TransformerException
	{
		try
		{
			String fileLocation = "";

			if (base != null)
			{
				final URL baseUrl = new URL(base);
				final URL absoluteUrl = new URL(baseUrl, href);
				fileLocation = absoluteUrl.toExternalForm();
			}
			else
			{
				fileLocation = href;
			}

			if (resources != null && resources.containsKey(fileLocation))
			{
				final StreamSource source = new StreamSource(new ByteArrayInputStream(resources.get(fileLocation)));
				source.setSystemId(fileLocation);
				return source;
			}
		}
		catch (final Exception ex)
		{
			ExceptionUtilities.handleException(ex);
		}

		System.out.println("Did not find resource. href: \"" + href + "\" base: \"" + base + "\"");
		throw new TransformerException("Could not find the resource " + href);
	}
}
