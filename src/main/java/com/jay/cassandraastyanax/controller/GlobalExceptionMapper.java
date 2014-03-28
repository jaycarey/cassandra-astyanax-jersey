package com.jay.cassandraastyanax.controller;

import com.jay.cassandraastyanax.dto.ExceptionDto;
import com.sun.jersey.api.view.Viewable;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Context
    private HttpServletRequest httpServletRequest;

    public GlobalExceptionMapper() {
    }

    public GlobalExceptionMapper(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public Response toResponse(Exception exception) {

        String acceptHeader = httpServletRequest.getHeader(ACCEPT);
        ExceptionDto exceptionDto = new ExceptionDto(exception);

        if (httpServletRequest.getRequestURI().endsWith(".json") || acceptHeader.equals(APPLICATION_JSON)) {
            return Response.serverError().entity(exceptionDto).build();

        } else if (httpServletRequest.getRequestURI().endsWith(".xml") || acceptHeader.equals(APPLICATION_XML)) {
            return Response.serverError().entity(exceptionDto).build();

        } else {
            return Response.serverError().entity(new Viewable("/error", exceptionDto)).build();
        }
    }

}
