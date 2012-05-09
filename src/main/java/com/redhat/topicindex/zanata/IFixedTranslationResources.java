package com.redhat.topicindex.zanata;

import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.client.ClientResponse;
import org.zanata.rest.RestConstant;
import org.zanata.rest.client.ITranslationResources;
import org.zanata.rest.dto.resource.Resource;

@Produces({ MediaType.APPLICATION_XML })
@Consumes({ MediaType.APPLICATION_XML })
public interface IFixedTranslationResources extends ITranslationResources {

	@POST
	public ClientResponse<String> post(@HeaderParam(RestConstant.HEADER_USERNAME) String username, @HeaderParam(RestConstant.HEADER_API_KEY) String apikey, Resource messageBody, @QueryParam("ext") Set<String> extensions, @QueryParam("copyTrans") @DefaultValue("true") boolean copytrans);

	@DELETE
	@Path("{id}")
	public ClientResponse<String> deleteResource(@HeaderParam(RestConstant.HEADER_USERNAME) String username, @HeaderParam(RestConstant.HEADER_API_KEY) String apikey, @PathParam("id") String idNoSlash);
}
