package com.redhat.topicindex.rest.entities.jacksonutils;

import java.io.IOException;

import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.Module;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

import com.redhat.topicindex.rest.entities.BugzillaBugV1;
import com.redhat.topicindex.rest.entities.CategoryV1;
import com.redhat.topicindex.rest.entities.ProjectV1;
import com.redhat.topicindex.rest.entities.PropertyTagV1;
import com.redhat.topicindex.rest.entities.TagV1;
import com.redhat.topicindex.rest.entities.TopicSourceUrlV1;
import com.redhat.topicindex.rest.entities.TopicV1;
import com.redhat.topicindex.rest.entities.TranslatedTopicStringV1;
import com.redhat.topicindex.rest.entities.TranslatedTopicV1;
import com.redhat.topicindex.rest.entities.interfaces.IBugzillaBugV1;
import com.redhat.topicindex.rest.entities.interfaces.ICategoryV1;
import com.redhat.topicindex.rest.entities.interfaces.IProjectV1;
import com.redhat.topicindex.rest.entities.interfaces.IPropertyTagV1;
import com.redhat.topicindex.rest.entities.interfaces.ITagV1;
import com.redhat.topicindex.rest.entities.interfaces.ITopicSourceUrlV1;
import com.redhat.topicindex.rest.entities.interfaces.ITopicV1;
import com.redhat.topicindex.rest.entities.interfaces.ITranslatedTopicStringV1;
import com.redhat.topicindex.rest.entities.interfaces.ITranslatedTopicV1;

/**
 * 
 * @author Matthew Casperson
 * 
 *         This JAX-RS provider is used to map the REST object interfaces to concrete classes, allowing for automatic deserialisation. See
 *         http://docs.jboss.org/resteasy/docs/2.3.4.Final/userguide /html_single/#Content_Marshalling_with__Provider_classes for more info, and
 *         http://wiki.fasterxml.com/JacksonFAQ#Deserializing_Abstract_types for more info on Jackson deserialisation of abstract types.
 * 
 *         Use ResteasyProviderFactory.getInstance().registerProvider(JacksonContextResolver.class); when running a RESTEasy client.
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class JacksonContextResolver implements ContextResolver<ObjectMapper>
{
	private static final String MODULE_NAME = "JacksonMixins";
	private static final String SNAPSHOT_INFO = "SNAPSHOT";
	private ObjectMapper objectMapper;

	public JacksonContextResolver() throws JsonGenerationException, JsonMappingException, IOException
	{
		objectMapper = new ObjectMapper();
		objectMapper.registerModule(mapInterfaces());
	}

	private Module mapInterfaces()
	{
		/* maven/OGSi style version */
		final Version version = new Version(1, 0, 0, SNAPSHOT_INFO);
		final SimpleModule module = new SimpleModule(MODULE_NAME, version);

		module.addAbstractTypeMapping(IBugzillaBugV1.class, BugzillaBugV1.class);
		module.addAbstractTypeMapping(ICategoryV1.class, CategoryV1.class);
		module.addAbstractTypeMapping(IProjectV1.class, ProjectV1.class);
		module.addAbstractTypeMapping(IPropertyTagV1.class, PropertyTagV1.class);
		module.addAbstractTypeMapping(ITagV1.class, TagV1.class);
		module.addAbstractTypeMapping(ITopicSourceUrlV1.class, TopicSourceUrlV1.class);
		module.addAbstractTypeMapping(ITopicV1.class, TopicV1.class);
		module.addAbstractTypeMapping(ITranslatedTopicStringV1.class, TranslatedTopicStringV1.class);
		module.addAbstractTypeMapping(ITranslatedTopicV1.class, TranslatedTopicV1.class);

		return module;
	}

	public ObjectMapper getContext(Class<?> type)
	{
		return objectMapper;
	}
}
