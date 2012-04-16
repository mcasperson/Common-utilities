package com.redhat.topicindex.rest.sharedinterface;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;

import org.jboss.resteasy.plugins.providers.atom.Feed;

import com.redhat.topicindex.rest.collections.BaseRestCollectionV1;
import com.redhat.topicindex.rest.entities.BlobConstantV1;
import com.redhat.topicindex.rest.entities.CategoryV1;
import com.redhat.topicindex.rest.entities.ImageV1;
import com.redhat.topicindex.rest.entities.ProjectV1;
import com.redhat.topicindex.rest.entities.PropertyTagV1;
import com.redhat.topicindex.rest.entities.RoleV1;
import com.redhat.topicindex.rest.entities.SnapshotRevisionV1;
import com.redhat.topicindex.rest.entities.SnapshotTopicV1;
import com.redhat.topicindex.rest.entities.SnapshotTranslatedDataV1;
import com.redhat.topicindex.rest.entities.SnapshotTranslatedStringV1;
import com.redhat.topicindex.rest.entities.SnapshotV1;
import com.redhat.topicindex.rest.entities.StringConstantV1;
import com.redhat.topicindex.rest.entities.TagV1;
import com.redhat.topicindex.rest.entities.TopicV1;
import com.redhat.topicindex.rest.entities.UserV1;
import com.redhat.topicindex.rest.entities.WorkingSnapshotTranslatedDataV1;
import com.redhat.topicindex.rest.entities.WorkingSnapshotTranslatedStringV1;
import com.redhat.topicindex.rest.exceptions.InternalProcessingException;
import com.redhat.topicindex.rest.exceptions.InvalidParameterException;
import com.redhat.topicindex.rest.expand.ExpandDataTrunk;

@Path("/1")
public interface RESTInterfaceV1
{
	/* SYSTEM FUNCTIONS */

	/**
	 * Sets the value of the topicindex.rerenderTopic system property, which in
	 * turn defines if topics are rerendered when they are updated.
	 * 
	 * @param enalbed
	 *            true if rendering to to be enabled, false otherwise
	 */
	@PUT
	@Path("/settings/rerenderTopic")
	@Consumes({ "*" })
	public void setRerenderTopic(@QueryParam("enabled") final Boolean enalbed);

	/**
	 * @return An example of a ExpandDataTrunk object serialized to JSON. This
	 *         is for convenience, and has no impact on the database or any
	 *         other processing
	 */
	@GET
	@Path("/expanddatatrunk/get/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public ExpandDataTrunk getJSONExpandTrunkExample() throws InvalidParameterException, InternalProcessingException;

	/* WORKINGSNAPSHOTTRANSLATEDSTING FUNCTIONS */
	/**
	 * @param id
	 *            The WorkingSnapshotTranslatedStringV1 ID
	 * @param expand
	 *            The expansion options
	 * @return A JSON representation of the requested
	 *         WorkingSnapshotTranslatedStringV1 object
	 * @HTTP 400 if the id is not valid
	 * @HTTP 500 if there was an unexpected internal error
	 */
	@GET
	@Path("/workingsnapshottranslatedstring/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public WorkingSnapshotTranslatedStringV1 getJSONWorkingSnapshotTranslatedString(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;
	
	@GET
	@Path("/workingsnapshottranslatedstring/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPWorkingSnapshotTranslatedString(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	/**
	 * @param expand
	 * @return JSON representations of all the WorkingSnapshotTranslatedStringV1
	 *         entities that could be found in the database
	 * @HTTP 500 if there was an unexpected internal error
	 */
	@GET
	@Path("/workingsnapshottranslatedstrings/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<WorkingSnapshotTranslatedStringV1> getJSONWorkingSnapshotTranslatedStrings(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;
	
	@GET
	@Path("/workingsnapshottranslatedstrings/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPWorkingSnapshotTranslatedStrings(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	/**
	 * Updates a single WorkingSnapshotTranslatedStringV1 entity.
	 * 
	 * @param expand
	 *            The expansion options
	 * @param dataObject
	 *            The new details of the WorkingSnapshotTranslatedStringV1
	 *            entity. The id property of the entity needs to be set. In
	 *            addition to setting the properties of the
	 *            WorkingSnapshotTranslatedStringV1 entity, the
	 *            configuredParameters variable needs to also needs to reflect
	 *            those properties that are to be updated. This is to
	 *            distinguish between an unset property (which is ignored), and
	 *            a property that might specifically be set to null.
	 * @return A JSON representation of the WorkingSnapshotTranslatedStringV1
	 *         after is updated
	 * @HTTP 400 if the id is not valid
	 * @HTTP 500 if there was an unexpected internal error
	 */
	@PUT
	@Path("/workingsnapshottranslatedstring/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public WorkingSnapshotTranslatedStringV1 updateJSONWorkingSnapshotTranslatedString(@QueryParam("expand") final String expand, final WorkingSnapshotTranslatedStringV1 dataObject) throws InvalidParameterException, InternalProcessingException;
	
	@PUT
	@Path("/workingsnapshottranslatedstring/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPWorkingSnapshotTranslatedString(@QueryParam("expand") final String expand, final WorkingSnapshotTranslatedStringV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	/**
	 * Updates a collection of WorkingSnapshotTranslatedStringV1 entities.
	 * 
	 * @param expand
	 *            The expansion options
	 * @param dataObjects
	 *            A collection of WorkingSnapshotTranslatedStringV1 entities,
	 *            each with their id property set, each with the new properties
	 *            to be saved, and each with their configuredParameters property
	 *            set.
	 * @return The details of the WorkingSnapshotTranslatedStringV1 entities after they have been updated.
	 * @HTTP 400 if the id is not valid
	 * @HTTP 500 if there was an unexpected internal error
	 */
	@PUT
	@Path("/workingsnapshottranslatedstrings/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BaseRestCollectionV1<WorkingSnapshotTranslatedStringV1> updateJSONWorkingSnapshotTranslatedStrings(@QueryParam("expand") final String expand, final BaseRestCollectionV1<WorkingSnapshotTranslatedStringV1> dataObjects) throws InvalidParameterException, InternalProcessingException;
	
	@PUT
	@Path("/workingsnapshottranslatedstrings/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPWorkingSnapshotTranslatedStrings(@QueryParam("expand") final String expand, final BaseRestCollectionV1<WorkingSnapshotTranslatedStringV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	/**
	 * Deletes a single WorkingSnapshotTranslatedStringV1 entity. 
	 * @param id The id of the entity to be deleted.
	 * @param expand The expansion options.
	 * @return The details of the deleted WorkingSnapshotTranslatedStringV1 entity.
	 * @HTTP 400 if the id is not valid
	 * @HTTP 500 if there was an unexpected internal error
	 */
	@DELETE
	@Path("/workingsnapshottranslatedstring/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public WorkingSnapshotTranslatedStringV1 deleteJSONWorkingSnapshotTranslatedString(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;
	
	@DELETE
	@Path("/workingsnapshottranslatedstring/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPWorkingSnapshotTranslatedString(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	/**
	 * Deletes a collection of WorkingSnapshotTranslatedStringV1 entities. 
	 * @param ids A semicolon separated list of ids to be deleted, starting with the prefix "ids;" e.g. ids;1;13;652
	 * @param expand The expansion options
	 * @return The details of the deleted WorkingSnapshotTranslatedStringV1 entities. 
	 * @HTTP 400 if any of the ids are not valid
	 * @HTTP 500 if there was an unexpected internal error
	 */
	@DELETE
	@Path("/workingsnapshottranslatedstrings/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<WorkingSnapshotTranslatedStringV1> deleteJSONWorkingSnapshotTranslatedStrings(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;
	
	@DELETE
	@Path("/workingsnapshottranslatedstrings/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPWorkingSnapshotTranslatedStrings(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	/* WORKINGSNAPSHOTTRANSLATEDDATA FUNCTIONS */
	/*		JSONP FUNCTIONS */
	@GET
	@Path("/workingsnapshottranslateddata/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPWorkingSnapshotTranslatedData(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/workingsnapshottranslateddatas/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPWorkingSnapshotTranslatedDatas(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/workingsnapshottranslateddata/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPWorkingSnapshotTranslatedData(@QueryParam("expand") final String expand, final WorkingSnapshotTranslatedDataV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/workingsnapshottranslateddatas/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPWorkingSnapshotTranslatedDatas(@QueryParam("expand") final String expand, final BaseRestCollectionV1<WorkingSnapshotTranslatedDataV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/workingsnapshottranslateddata/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPWorkingSnapshotTranslatedData(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/workingsnapshottranslateddatas/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPWorkingSnapshotTranslatedDatas(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	/*		JSON FUNCTIONS */
	@GET
	@Path("/workingsnapshottranslateddata/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public WorkingSnapshotTranslatedDataV1 getJSONWorkingSnapshotTranslatedData(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/workingsnapshottranslateddatas/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<WorkingSnapshotTranslatedDataV1> getJSONWorkingSnapshotTranslatedDatas(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/workingsnapshottranslateddata/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public WorkingSnapshotTranslatedDataV1 updateJSONWorkingSnapshotTranslatedData(@QueryParam("expand") final String expand, final WorkingSnapshotTranslatedDataV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/workingsnapshottranslateddatas/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BaseRestCollectionV1<WorkingSnapshotTranslatedDataV1> updateJSONWorkingSnapshotTranslatedDatas(@QueryParam("expand") final String expand, final BaseRestCollectionV1<WorkingSnapshotTranslatedDataV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/workingsnapshottranslateddata/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public WorkingSnapshotTranslatedDataV1 deleteJSONWorkingSnapshotTranslatedData(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/workingsnapshottranslateddatas/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<WorkingSnapshotTranslatedDataV1> deleteJSONWorkingSnapshotTranslatedDatas(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	/* USER FUNCTIONS */
	/*		JSONP FUNCTIONS */
	@GET
	@Path("/user/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPUser(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/users/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPUsers(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/user/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPUser(@QueryParam("expand") final String expand, final UserV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/users/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPUsers(@QueryParam("expand") final String expand, final BaseRestCollectionV1<UserV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/user/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPUser(@QueryParam("expand") final String expand, final UserV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/users/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPUsers(@QueryParam("expand") final String expand, final BaseRestCollectionV1<UserV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/user/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPUser(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/users/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPUsers(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	/*		JSON FUNCTIONS */
	@GET
	@Path("/user/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public UserV1 getJSONUser(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/users/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<UserV1> getJSONUsers(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/user/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public UserV1 updateJSONUser(@QueryParam("expand") final String expand, final UserV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/users/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BaseRestCollectionV1<UserV1> updateJSONUsers(@QueryParam("expand") final String expand, final BaseRestCollectionV1<UserV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/user/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public UserV1 createJSONUser(@QueryParam("expand") final String expand, final UserV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/users/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseRestCollectionV1<UserV1> createJSONUsers(@QueryParam("expand") final String expand, final BaseRestCollectionV1<UserV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/user/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public UserV1 deleteJSONUser(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/users/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<UserV1> deleteJSONUsers(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	/* STRINGCONSTANT FUNCTIONS */
	/*		JSONP FUNCTIONS */
	@GET
	@Path("/stringconstant/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPStringConstant(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/stringconstants/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPStringConstants(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/stringconstant/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPStringConstant(@QueryParam("expand") final String expand, final StringConstantV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/stringconstants/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPStringConstants(@QueryParam("expand") final String expand, final BaseRestCollectionV1<StringConstantV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/stringconstant/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPStringConstant(@QueryParam("expand") final String expand, final StringConstantV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/stringconstants/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPStringConstants(@QueryParam("expand") final String expand, final BaseRestCollectionV1<StringConstantV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/stringconstant/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPStringConstant(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/stringconstants/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPStringConstants(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	/*		JSON FUNCTIONS */
	@GET
	@Path("/stringconstant/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public StringConstantV1 getJSONStringConstant(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/stringconstants/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<StringConstantV1> getJSONStringConstants(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/stringconstant/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public StringConstantV1 updateJSONStringConstant(@QueryParam("expand") final String expand, final StringConstantV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/stringconstants/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BaseRestCollectionV1<StringConstantV1> updateJSONStringConstants(@QueryParam("expand") final String expand, final BaseRestCollectionV1<StringConstantV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/stringconstant/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public StringConstantV1 createJSONStringConstant(@QueryParam("expand") final String expand, final StringConstantV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/stringconstants/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseRestCollectionV1<StringConstantV1> createJSONStringConstants(@QueryParam("expand") final String expand, final BaseRestCollectionV1<StringConstantV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/stringconstant/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public StringConstantV1 deleteJSONStringConstant(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/stringconstants/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<StringConstantV1> deleteJSONStringConstants(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	/* SNAPSHOT FUNCTIONS */
	/*		JSONP FUNCTIONS */
	@GET
	@Path("/snapshot/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPSnapshot(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/snapshots/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPSnapshots(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshot/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPSnapshot(@QueryParam("expand") final String expand, final SnapshotV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshots/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPSnapshots(@QueryParam("expand") final String expand, final BaseRestCollectionV1<SnapshotV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/snapshot/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPSnapshot(@QueryParam("expand") final String expand, final SnapshotV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/snapshots/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPSnapshots(@QueryParam("expand") final String expand, final BaseRestCollectionV1<SnapshotV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshot/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPSnapshot(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshots/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPSnapshots(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	/*		JSON FUNCTIONS */
	@GET
	@Path("/snapshottranslationdata/get/html/{id}/xmlRendered")
	@Produces(MediaType.TEXT_HTML)
	@Consumes({ "*" })
	public String getHTMLSnapshotTranslationDataXMLRendered(@PathParam("id") final Integer id);

	@GET
	@Path("/snapshot/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public SnapshotV1 getJSONSnapshot(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/snapshots/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<SnapshotV1> getJSONSnapshots(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshot/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public SnapshotV1 updateJSONSnapshot(@QueryParam("expand") final String expand, final SnapshotV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshots/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BaseRestCollectionV1<SnapshotV1> updateJSONSnapshots(@QueryParam("expand") final String expand, final BaseRestCollectionV1<SnapshotV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/snapshot/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public SnapshotV1 createJSONSnapshot(@QueryParam("expand") final String expand, final SnapshotV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/snapshots/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseRestCollectionV1<SnapshotV1> createJSONSnapshots(@QueryParam("expand") final String expand, final BaseRestCollectionV1<SnapshotV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshot/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public SnapshotV1 deleteJSONSnapshot(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshots/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<SnapshotV1> deleteJSONSnapshots(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	/* SNAPSHOTTRANSLATEDSTRING FUNCTIONS */
	/*		JSONP FUNCTIONS */
	@GET
	@Path("/snapshottranslatedstring/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPSnapshotTranslatedString(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/snapshottranslatedstrings/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPSnapshotTranslatedStrings(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshottranslatedstring/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPSnapshotTranslatedString(@QueryParam("expand") final String expand, final SnapshotTranslatedStringV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshottranslatedstrings/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPSnapshotTranslatedStrings(@QueryParam("expand") final String expand, final BaseRestCollectionV1<SnapshotTranslatedStringV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshottranslatedstring/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPSnapshotTranslatedString(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshottranslatedstrings/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPSnapshotTranslatedStrings(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	/*		JSON FUNCTIONS */
	@GET
	@Path("/snapshottranslatedstring/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public SnapshotTranslatedStringV1 getJSONSnapshotTranslatedString(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/snapshottranslatedstrings/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<SnapshotTranslatedStringV1> getJSONSnapshotTranslatedStrings(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshottranslatedstring/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public SnapshotTranslatedStringV1 updateJSONSnapshotTranslatedString(@QueryParam("expand") final String expand, final SnapshotTranslatedStringV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshottranslatedstrings/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BaseRestCollectionV1<SnapshotTranslatedStringV1> updateJSONSnapshotTranslatedStrings(@QueryParam("expand") final String expand, final BaseRestCollectionV1<SnapshotTranslatedStringV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshottranslatedstring/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public SnapshotTranslatedStringV1 deleteJSONSnapshotTranslatedString(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshottranslatedstrings/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<SnapshotTranslatedStringV1> deleteJSONSnapshotTranslatedStrings(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	/* SNAPSHOTTRANSLATEDDATA FUNCTIONS */
	/*		JSONP FUNCTIONS */
	@GET
	@Path("/snapshottranslateddata/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPSnapshotTranslatedData(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/snapshottranslateddatas/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPSnapshotTranslatedDatas(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshottranslateddata/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPSnapshotTranslatedData(@QueryParam("expand") final String expand, final SnapshotTranslatedDataV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshottranslateddatas/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPSnapshotTranslatedDatas(@QueryParam("expand") final String expand, final BaseRestCollectionV1<SnapshotTranslatedDataV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshottranslateddata/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPSnapshotTranslatedData(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshottranslateddatas/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPSnapshotTranslatedDatas(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	/*		JSON FUNCTIONS */	
	@GET
	@Path("/snapshottranslateddata/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public SnapshotTranslatedDataV1 getJSONSnapshotTranslatedData(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/snapshottranslateddatas/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<SnapshotTranslatedDataV1> getJSONSnapshotTranslatedDatas(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshottranslateddata/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public SnapshotTranslatedDataV1 updateJSONSnapshotTranslatedData(@QueryParam("expand") final String expand, final SnapshotTranslatedDataV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshottranslateddatas/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BaseRestCollectionV1<SnapshotTranslatedDataV1> updateJSONSnapshotTranslatedDatas(@QueryParam("expand") final String expand, final BaseRestCollectionV1<SnapshotTranslatedDataV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshottranslateddata/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public SnapshotTranslatedDataV1 deleteJSONSnapshotTranslatedData(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshottranslateddatas/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<SnapshotTranslatedDataV1> deleteJSONSnapshotTranslatedDatas(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	/* SNAPSHOTTOPIC FUNCTIONS */
	/*		JSONP FUNCTIONS */	
	@GET
	@Path("/snapshottopic/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPSnapshotTopic(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/snapshottopics/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPSnapshotTopics(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshottopic/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPSnapshotTopic(@QueryParam("expand") final String expand, final SnapshotTopicV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshottopics/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPSnapshotTopics(@QueryParam("expand") final String expand, final BaseRestCollectionV1<SnapshotTopicV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/snapshottopic/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPSnapshotTopic(@QueryParam("expand") final String expand, final SnapshotTopicV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/snapshottopics/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPSnapshotTopics(@QueryParam("expand") final String expand, final BaseRestCollectionV1<SnapshotTopicV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshottopic/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPSnapshotTopic(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshottopics/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPSnapshotTopics(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	/*		JSON FUNCTIONS */	
	@GET
	@Path("/snapshottopic/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public SnapshotTopicV1 getJSONSnapshotTopic(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/snapshottopics/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<SnapshotTopicV1> getJSONSnapshotTopics(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshottopic/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public SnapshotTopicV1 updateJSONSnapshotTopic(@QueryParam("expand") final String expand, final SnapshotTopicV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshottopics/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BaseRestCollectionV1<SnapshotTopicV1> updateJSONSnapshotTopics(@QueryParam("expand") final String expand, final BaseRestCollectionV1<SnapshotTopicV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/snapshottopic/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public SnapshotTopicV1 createJSONSnapshotTopic(@QueryParam("expand") final String expand, final SnapshotTopicV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/snapshottopics/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseRestCollectionV1<SnapshotTopicV1> createJSONSnapshotTopics(@QueryParam("expand") final String expand, final BaseRestCollectionV1<SnapshotTopicV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshottopic/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public SnapshotTopicV1 deleteJSONSnapshotTopic(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshottopics/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<SnapshotTopicV1> deleteJSONSnapshotTopics(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	/* SNAPSHOTREVISION FUNCTIONS */
	/*		JSON FUNCTIONS */	
	@GET
	@Path("/snapshotrevision/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPSnapshotRevision(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/snapshotrevisions/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPSnapshotRevisions(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshotrevision/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPSnapshotRevision(@QueryParam("expand") final String expand, final SnapshotRevisionV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshotrevisions/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPSnapshotRevisions(@QueryParam("expand") final String expand, final BaseRestCollectionV1<SnapshotRevisionV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshotrevision/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPSnapshotRevision(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshotrevisions/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPSnapshotRevisions(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	/*		JSON FUNCTIONS */	
	@GET
	@Path("/snapshotrevision/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public SnapshotRevisionV1 getJSONSnapshotRevision(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/snapshotrevisions/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<SnapshotRevisionV1> getJSONSnapshotRevisions(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshotrevision/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public SnapshotRevisionV1 updateJSONSnapshotRevision(@QueryParam("expand") final String expand, final SnapshotRevisionV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/snapshotrevisions/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BaseRestCollectionV1<SnapshotRevisionV1> updateJSONSnapshotRevisions(@QueryParam("expand") final String expand, final BaseRestCollectionV1<SnapshotRevisionV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshotrevision/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public SnapshotRevisionV1 deleteJSONSnapshotRevision(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/snapshotrevisions/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<SnapshotRevisionV1> deleteJSONSnapshotRevisions(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	/* ROLE FUNCTIONS */
	/*		JSONP FUNCTIONS */	
	@GET
	@Path("/role/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPRole(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/roles/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPRoles(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/role/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPRole(@QueryParam("expand") final String expand, final RoleV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/roles/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPRoles(@QueryParam("expand") final String expand, final BaseRestCollectionV1<RoleV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/role/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPRole(@QueryParam("expand") final String expand, final RoleV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/roles/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPRoles(@QueryParam("expand") final String expand, final BaseRestCollectionV1<RoleV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/role/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPRole(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/roles/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPRoles(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	/*		JSON FUNCTIONS */	
	@GET
	@Path("/role/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public RoleV1 getJSONRole(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/roles/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<RoleV1> getJSONRoles(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/role/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public RoleV1 updateJSONRole(@QueryParam("expand") final String expand, final RoleV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/roles/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BaseRestCollectionV1<RoleV1> updateJSONRoles(@QueryParam("expand") final String expand, final BaseRestCollectionV1<RoleV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/role/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public RoleV1 createJSONRole(@QueryParam("expand") final String expand, final RoleV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/roles/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseRestCollectionV1<RoleV1> createJSONRoles(@QueryParam("expand") final String expand, final BaseRestCollectionV1<RoleV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/role/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public RoleV1 deleteJSONRole(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/roles/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<RoleV1> deleteJSONRoles(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	/* PROPERYTAG FUNCTIONS */
	/*		JSONP FUNCTIONS */	
	@GET
	@Path("/propertytag/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPPropertyTag(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/propertytags/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPPropertyTags(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/propertytag/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPPropertyTag(@QueryParam("expand") final String expand, final PropertyTagV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/propertytags/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPPropertyTags(@QueryParam("expand") final String expand, final BaseRestCollectionV1<PropertyTagV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/propertytag/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPPropertyTag(@QueryParam("expand") final String expand, final PropertyTagV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/propertytags/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPPropertyTags(@QueryParam("expand") final String expand, final BaseRestCollectionV1<PropertyTagV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/propertytag/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPPropertyTag(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/propertytags/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPPropertyTags(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	/*		JSON FUNCTIONS */	
	@GET
	@Path("/propertytag/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public PropertyTagV1 getJSONPropertyTag(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/propertytags/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<PropertyTagV1> getJSONPropertyTags(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/propertytag/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public PropertyTagV1 updateJSONPropertyTag(@QueryParam("expand") final String expand, final PropertyTagV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/propertytags/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BaseRestCollectionV1<PropertyTagV1> updateJSONPropertyTags(@QueryParam("expand") final String expand, final BaseRestCollectionV1<PropertyTagV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/propertytag/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public PropertyTagV1 createJSONPropertyTag(@QueryParam("expand") final String expand, final PropertyTagV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/propertytags/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseRestCollectionV1<PropertyTagV1> createJSONPropertyTags(@QueryParam("expand") final String expand, final BaseRestCollectionV1<PropertyTagV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/propertytag/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public PropertyTagV1 deleteJSONPropertyTag(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/propertytags/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<PropertyTagV1> deleteJSONPropertyTags(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	/* BLOBCONSTANTS FUNCTIONS */
	/*		JSONP FUNCTIONS */	
	@GET
	@Path("/blobconstant/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPBlobConstant(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/blobconstants/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPBlobConstants(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/blobconstant/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPBlobConstant(@QueryParam("expand") final String expand, final BlobConstantV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/blobconstants/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPBlobConstants(@QueryParam("expand") final String expand, final BaseRestCollectionV1<BlobConstantV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/blobconstant/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPBlobConstant(@QueryParam("expand") final String expand, final BlobConstantV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/blobconstants/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPBlobConstants(@QueryParam("expand") final String expand, final BaseRestCollectionV1<BlobConstantV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/blobconstant/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPBlobConstant(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/blobconstants/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPBlobConstants(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	/*		JSON FUNCTIONS */	
	@GET
	@Path("/blobconstant/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BlobConstantV1 getJSONBlobConstant(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/blobconstants/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<BlobConstantV1> getJSONBlobConstants(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/blobconstant/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BlobConstantV1 updateJSONBlobConstant(@QueryParam("expand") final String expand, final BlobConstantV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/blobconstants/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BaseRestCollectionV1<BlobConstantV1> updateJSONBlobConstants(@QueryParam("expand") final String expand, final BaseRestCollectionV1<BlobConstantV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/blobconstant/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public BlobConstantV1 createJSONBlobConstant(@QueryParam("expand") final String expand, final BlobConstantV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/blobconstants/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseRestCollectionV1<BlobConstantV1> createJSONBlobConstants(@QueryParam("expand") final String expand, final BaseRestCollectionV1<BlobConstantV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/blobconstant/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BlobConstantV1 deleteJSONBlobConstant(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/blobconstants/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<BlobConstantV1> deleteJSONBlobConstants(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	/* PROJECT FUNCTIONS */
	/*		JSONP FUNCTIONS */
	@GET
	@Path("/project/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPProject(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/projects/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPProjects(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/project/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPProject(@QueryParam("expand") final String expand, final ProjectV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/projects/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPProjects(@QueryParam("expand") final String expand, final BaseRestCollectionV1<ProjectV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/project/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPProject(@QueryParam("expand") final String expand, final ProjectV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/projects/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPProjects(@QueryParam("expand") final String expand, final BaseRestCollectionV1<ProjectV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/project/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPProject(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/projects/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPProjects(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	/*		JSON FUNCTIONS */	
	@GET
	@Path("/project/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public ProjectV1 getJSONProject(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/projects/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<ProjectV1> getJSONProjects(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/project/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public ProjectV1 updateJSONProject(@QueryParam("expand") final String expand, final ProjectV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/projects/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BaseRestCollectionV1<ProjectV1> updateJSONProjects(@QueryParam("expand") final String expand, final BaseRestCollectionV1<ProjectV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/project/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public ProjectV1 createJSONProject(@QueryParam("expand") final String expand, final ProjectV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/projects/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseRestCollectionV1<ProjectV1> createJSONProjects(@QueryParam("expand") final String expand, final BaseRestCollectionV1<ProjectV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/project/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public ProjectV1 deleteJSONProject(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/projects/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<ProjectV1> deleteJSONProjects(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	/* TAG FUNCTIONS */
	/*		JSONP FUNCTIONS */	
	@GET
	@Path("/tag/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPTag(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/tags/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPTags(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/tag/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPTag(@QueryParam("expand") final String expand, final TagV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/tags/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPTags(@QueryParam("expand") final String expand, final BaseRestCollectionV1<TagV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/tag/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPTag(@QueryParam("expand") final String expand, final TagV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/tags/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPTags(@QueryParam("expand") final String expand, final BaseRestCollectionV1<TagV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/tag/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPTag(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/tags/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPTags(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	/*		JSON FUNCTIONS */	
	@GET
	@Path("/tag/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public TagV1 getJSONTag(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/tags/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<TagV1> getJSONTags(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/tag/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public TagV1 updateJSONTag(@QueryParam("expand") final String expand, final TagV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/tags/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BaseRestCollectionV1<TagV1> updateJSONTags(@QueryParam("expand") final String expand, final BaseRestCollectionV1<TagV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/tag/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public TagV1 createJSONTag(@QueryParam("expand") final String expand, final TagV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/tags/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseRestCollectionV1<TagV1> createJSONTags(@QueryParam("expand") final String expand, final BaseRestCollectionV1<TagV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/tag/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public TagV1 deleteJSONTag(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/tags/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<TagV1> deleteJSONTags(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	/* CATEGORY FUNCTIONS */
	/*		JSONP FUNCTIONS */
	@GET
	@Path("/category/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPCategory(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/categories/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPCategories(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/category/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPCategory(@QueryParam("expand") final String expand, final CategoryV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/categories/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPCategories(@QueryParam("expand") final String expand, final BaseRestCollectionV1<CategoryV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/category/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPCategory(@QueryParam("expand") final String expand, final CategoryV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/categories/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPCategories(@QueryParam("expand") final String expand, final BaseRestCollectionV1<CategoryV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/category/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPCategory(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/categories/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPCategories(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	/*		JSON FUNCTIONS */	
	@GET
	@Path("/category/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public CategoryV1 getJSONCategory(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/categories/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<CategoryV1> getJSONCategories(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/category/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public CategoryV1 updateJSONCategory(@QueryParam("expand") final String expand, final CategoryV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/categories/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BaseRestCollectionV1<CategoryV1> updateJSONCategories(@QueryParam("expand") final String expand, final BaseRestCollectionV1<CategoryV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/category/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public CategoryV1 createJSONCategory(@QueryParam("expand") final String expand, final CategoryV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/categories/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseRestCollectionV1<CategoryV1> createJSONCategories(@QueryParam("expand") final String expand, final BaseRestCollectionV1<CategoryV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/category/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public CategoryV1 deleteJSONCategory(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/categories/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<CategoryV1> deleteJSONCategories(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	/* IMAGE FUNCTIONS */
	/*		JSONP FUNCTIONS */	
	@GET
	@Path("/image/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPImage(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/images/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPImages(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/image/put/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String updateJSONPImage(@QueryParam("expand") final String expand, final ImageV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/images/put/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String updateJSONPImages(@QueryParam("expand") final String expand, final BaseRestCollectionV1<ImageV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/image/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPImage(@QueryParam("expand") final String expand, final ImageV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/images/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPImages(@QueryParam("expand") final String expand, final BaseRestCollectionV1<ImageV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/image/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPImage(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/images/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPImages(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	/*		JSON FUNCTIONS */	
	@GET
	@Path("/image/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public ImageV1 getJSONImage(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/images/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<ImageV1> getJSONImages(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/image/put/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public ImageV1 updateJSONImage(@QueryParam("expand") final String expand, final ImageV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/images/put/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseRestCollectionV1<ImageV1> updateJSONImages(@QueryParam("expand") final String expand, final BaseRestCollectionV1<ImageV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/image/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public ImageV1 createJSONImage(@QueryParam("expand") final String expand, final ImageV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/images/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseRestCollectionV1<ImageV1> createJSONImages(@QueryParam("expand") final String expand, final BaseRestCollectionV1<ImageV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/image/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public ImageV1 deleteJSONImage(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/images/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<ImageV1> deleteJSONImages(@PathParam("ids") final PathSegment id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	/* TOPIC FUNCTIONS */
	/*		JSONP FUNCTIONS */	
	@GET
	@Path("/topics/get/jsonp/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPTopicsWithQuery(@PathParam("query") PathSegment query, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	@GET
	@Path("/topics/get/jsonp/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPTopics(@QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/topic/get/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String getJSONPTopic(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/topic/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPTopic(@QueryParam("expand") final String expand, final TopicV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/topics/put/jsonp")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public String updateJSONPTopics(@QueryParam("expand") final String expand, final BaseRestCollectionV1<TopicV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/topic/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPTopic(@QueryParam("expand") final String expand, final TopicV1 dataObject, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/topics/post/jsonp")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public String createJSONPTopics(@QueryParam("expand") final String expand, final BaseRestCollectionV1<TopicV1> dataObjects, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/topic/delete/jsonp/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPTopic(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/topics/delete/jsonp/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public String deleteJSONPTopics(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand, @QueryParam("callback") final String callback) throws InvalidParameterException, InternalProcessingException;
	
	/*		JSON FUNCTIONS */	
	@GET
	@Path("/topics/get/json/all")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<TopicV1> getJSONTopics(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/topics/get/atom/{query}")
	@Produces(MediaType.APPLICATION_ATOM_XML)
	@Consumes({ "*" })
	public Feed getATOMTopicsWithQuery(@PathParam("query") PathSegment query, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/topics/get/json/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<TopicV1> getJSONTopicsWithQuery(@PathParam("query") PathSegment query, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;
	
	@GET
	@Path("/topics/get/xml/all")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes({ "*" })
	public BaseRestCollectionV1<TopicV1> getXMLTopics(@QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/topic/get/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public TopicV1 getJSONTopic(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/topic/get/xml/{id}")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes({ "*" })
	public TopicV1 getXMLTopic(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/topic/get/xml/{id}/xml")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes({ "*" })
	public String getXMLTopicXML(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/topic/get/xml/{id}/xmlContainedIn")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes({ "*" })
	public String getXMLTopicXMLContained(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("container") final String containerName) throws InvalidParameterException, InternalProcessingException;

	@GET
	@Path("/topic/get/xml/{id}/xmlNoContainer")
	@Produces(MediaType.TEXT_PLAIN)
	@Consumes({ "*" })
	public String getXMLTopicXMLNoContainer(@PathParam("id") final Integer id, @QueryParam("expand") final String expand, @QueryParam("includeTitle") final Boolean includeTitle) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/topic/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public TopicV1 updateJSONTopic(@QueryParam("expand") final String expand, final TopicV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@PUT
	@Path("/topics/put/json")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public BaseRestCollectionV1<TopicV1> updateJSONTopics(@QueryParam("expand") final String expand, final BaseRestCollectionV1<TopicV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/topic/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public TopicV1 createJSONTopic(@QueryParam("expand") final String expand, final TopicV1 dataObject) throws InvalidParameterException, InternalProcessingException;

	@POST
	@Path("/topics/post/json")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ MediaType.APPLICATION_JSON })
	public BaseRestCollectionV1<TopicV1> createJSONTopics(@QueryParam("expand") final String expand, final BaseRestCollectionV1<TopicV1> dataObjects) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/topic/delete/json/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public TopicV1 deleteJSONTopic(@PathParam("id") final Integer id, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;

	@DELETE
	@Path("/topics/delete/json/{ids}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes({ "*" })
	public BaseRestCollectionV1<TopicV1> deleteJSONTopics(@PathParam("ids") final PathSegment ids, @QueryParam("expand") final String expand) throws InvalidParameterException, InternalProcessingException;
}
