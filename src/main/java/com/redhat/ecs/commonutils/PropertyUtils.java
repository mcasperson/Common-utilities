package com.redhat.ecs.commonutils;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import java.util.Properties;

public class PropertyUtils
{
	final static private Map<String, Properties> loadedProperties = new HashMap<String, Properties>();

	public static String getProperty(final String fileName, final String property, final Class<?> classWithResources)
	{
		if (!loadedProperties.containsKey(fileName))
		{
			try
			{
				final InputStream inputStream = classWithResources.getResourceAsStream(fileName);
				final Properties properties = new Properties();
				properties.load(inputStream);
				loadedProperties.put(fileName, properties);
			}
			catch (final Exception ex)
			{
				ExceptionUtilities.handleException(ex);
			}
		}

		if (loadedProperties.containsKey(fileName))
			return loadedProperties.get(fileName).getProperty(property);
		
		return null;
	}
}
