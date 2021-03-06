package com.gefa.fit.boundary.inbound.rest.error.handlers;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

//@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		exception.printStackTrace();
		return Response.serverError().build();
	}

}
