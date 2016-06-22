package edu.jak.dummymailers.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import edu.jak.dummymailers.util.Constants;

//@Path("/email")
public class EmailResource {
	// @Context
	// private HttpServletRequest servletRequest;
	//
	// public EmailResource(){
	// super();
	// }
	//	
	// @GET
	// private Response getEmails(@Context UriInfo uriInfo){
	// MultivaluedMap<String, String> uriParams = uriInfo.getQueryParameters();
	// String startCount = uriParams.getFirst(Constants.START_COUNT);
	// String endCount = uriParams.getFirst(Constants.END_COUNT);
	// Response response = Response.ok("Hello Dude!").build();
	// return response;
	// }
}
