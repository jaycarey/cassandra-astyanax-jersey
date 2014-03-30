package com.jay.cassandraastyanax.controller;

import com.jay.cassandraastyanax.dto.ExceptionDto;
import com.jay.cassandraastyanax.exception.SystemException;
import org.glassfish.jersey.server.mvc.Viewable;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

/**
 * @author jaycarey
 */
public class GlobalExceptionMapperTest {

    private GlobalExceptionMapper globalExceptionMapper;

    @Before
    public void before() throws Exception {
        globalExceptionMapper = new GlobalExceptionMapper();
    }

    @Test
    public void errorProducesAppropriateExceptionDto() throws Exception {

        Response response = globalExceptionMapper.toResponse(new SystemException("Test Exception", null));

        ExceptionDto entity = (ExceptionDto) ((Viewable) response.getEntity()).getModel();
        assertThat(entity.getMessage(), equalTo("Test Exception"));
        assertThat(entity.getFrames().get(0), containsString("com.jay.cassandraastyanax.controller.GlobalExceptionMapperTest.errorProducesAppropriateExceptionDto(GlobalExceptionMapperTest.java"));
    }
}
