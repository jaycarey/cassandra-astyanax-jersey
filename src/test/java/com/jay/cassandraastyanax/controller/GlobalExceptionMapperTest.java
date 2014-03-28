package com.jay.cassandraastyanax.controller;

import com.jay.cassandraastyanax.dto.ExceptionDto;
import com.jay.cassandraastyanax.exception.SystemException;
import com.sun.jersey.api.view.Viewable;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.HttpHeaders.ACCEPT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author jaycarey
 */
public class GlobalExceptionMapperTest {

    private GlobalExceptionMapper globalExceptionMapper;

    @Mock
    private HttpServletRequest mockJsonHttpServletRequest;

    @Mock
    private HttpServletRequest mockXmlHttpServletRequest;

    @Mock
    private HttpServletRequest mockJspHttpServletRequest;

    @Before
    public void before() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(mockJsonHttpServletRequest.getHeader(ACCEPT)).thenReturn(MediaType.APPLICATION_JSON);
        when(mockJsonHttpServletRequest.getRequestURI()).thenReturn("/test.json");
        when(mockXmlHttpServletRequest.getHeader(ACCEPT)).thenReturn(MediaType.APPLICATION_XML);
        when(mockXmlHttpServletRequest.getRequestURI()).thenReturn("/test.xml");
        when(mockJspHttpServletRequest.getHeader(ACCEPT)).thenReturn(MediaType.TEXT_HTML);
        when(mockJspHttpServletRequest.getRequestURI()).thenReturn("/test.jsp");
    }

    @Test
    public void jsonProducesExceptionDto() throws Exception {
        globalExceptionMapper = new GlobalExceptionMapper(mockJsonHttpServletRequest);

        Response response = globalExceptionMapper.toResponse(new SystemException("Test Exception", null));

        ExceptionDto entity = (ExceptionDto) response.getEntity();
        assertThat(entity.getMessage(), equalTo("Test Exception"));
        assertThat(entity.getFrames().get(0), containsString("com.jay.cassandraastyanax.controller.GlobalExceptionMapperTest.jsonProducesExceptionDto(GlobalExceptionMapperTest.java"));
    }

    @Test
    public void xmlProducesExceptionDto() throws Exception {
        globalExceptionMapper = new GlobalExceptionMapper(mockXmlHttpServletRequest);

        Response response = globalExceptionMapper.toResponse(new SystemException("Test Exception", null));

        ExceptionDto entity = (ExceptionDto) response.getEntity();
        assertThat(entity.getMessage(), equalTo("Test Exception"));
        assertThat(entity.getFrames().get(0), containsString("com.jay.cassandraastyanax.controller.GlobalExceptionMapperTest.xmlProducesExceptionDto(GlobalExceptionMapperTest.java"));
    }

    @Test
    public void jspProducesExceptionDto() throws Exception {
        globalExceptionMapper = new GlobalExceptionMapper(mockJspHttpServletRequest);

        Response response = globalExceptionMapper.toResponse(new SystemException("Test Exception", null));

        Viewable viewable = (Viewable) response.getEntity();
        ExceptionDto entity = (ExceptionDto) viewable.getModel();
        assertThat(entity.getMessage(), equalTo("Test Exception"));
        assertThat(entity.getFrames().get(0), containsString("com.jay.cassandraastyanax.controller.GlobalExceptionMapperTest.jspProducesExceptionDto(GlobalExceptionMapperTest.java"));
    }
}
