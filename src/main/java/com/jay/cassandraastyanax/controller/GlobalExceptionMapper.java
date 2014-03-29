package com.jay.cassandraastyanax.controller;

import com.jay.cassandraastyanax.dto.ExceptionDto;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        return Response.serverError()
                .entity(new Viewable("/error", new ExceptionDto(exception)))
                .build();
    }

}
