package com.redhat.topicindex.rest.sharedinterface;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

import org.jboss.resteasy.plugins.providers.atom.Feed;

import com.redhat.topicindex.rest.exceptions.InternalProcessingException;
import com.redhat.topicindex.rest.exceptions.InvalidParameterException;

/**
 * This interface defines the REST endpoints that are not supported by Errai.
 * @author Matthew Casperson
 *
 */
public interface RESTInterfaceAdvancedV1
{
	@GET
	@Path("/topics/get/atom/{query}")
	@Produces(MediaType.APPLICATION_ATOM_XML)
	@Consumes({ "*" })
	public Feed getATOMTopicsWithQuery(@PathParam("query") PathSegment query, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;
}
