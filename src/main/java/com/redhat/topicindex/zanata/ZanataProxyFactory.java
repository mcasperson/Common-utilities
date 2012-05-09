package com.redhat.topicindex.zanata;

import java.net.URI;

import org.jboss.resteasy.client.ClientExecutor;
import org.zanata.rest.dto.VersionInfo;

public class ZanataProxyFactory extends org.zanata.rest.client.ZanataProxyFactory {

	public ZanataProxyFactory(String username, String apiKey, VersionInfo clientApiVersion)
	{
		super(null, username, apiKey, clientApiVersion);
	}

	public ZanataProxyFactory(URI base, String username, String apiKey, VersionInfo clientApiVersion)
	{
		super(base, username, apiKey, null, clientApiVersion, false);
	}
	   
	public ZanataProxyFactory(URI base, String username, String apiKey, VersionInfo clientApiVersion, boolean logHttp)
	{
		super(base, username, apiKey, null, clientApiVersion, logHttp);
	}
	
	public ZanataProxyFactory(URI base, String username, String apiKey, ClientExecutor executor, VersionInfo clientApiVersion,
            boolean logHttp)
	{
		super(base, username, apiKey, executor, clientApiVersion, logHttp);
	}
	
	public IFixedTranslationResources getFixedTranslationResources(String projectSlug, String versionSlug)
	{
		return createProxy(IFixedTranslationResources.class, getFixedTranslationResourcesURI(projectSlug, versionSlug));
	}

	public URI getFixedTranslationResourcesURI(String projectSlug, String versionSlug)
	{
		return super.getTranslationResourcesURI(projectSlug, versionSlug);
	}
}
