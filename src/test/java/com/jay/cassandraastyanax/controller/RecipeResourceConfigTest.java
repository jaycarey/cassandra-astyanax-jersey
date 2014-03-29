package com.jay.cassandraastyanax.controller;

import org.glassfish.jersey.server.ServerProperties;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.MediaType;
import java.util.Map;

import static javax.ws.rs.core.MediaType.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author jaycarey
 */
public class RecipeResourceConfigTest {

    private RecipeResourceConfig recipeResourceConfig;

    private Map<String, MediaType> mappings;

    @Before
    @SuppressWarnings("unchecked")
    public void before() throws Exception {
        recipeResourceConfig = new RecipeResourceConfig();

        mappings = (Map<String, MediaType>) recipeResourceConfig.getProperty(ServerProperties.MEDIA_TYPE_MAPPINGS);
    }

    @Test
    public void jsonMapsToApplicationJsonMediaType() throws Exception {

        assertThat(mappings.get("json"), equalTo(APPLICATION_JSON_TYPE));
    }

    @Test
    public void xmlMapsToApplicationXmlMediaType() throws Exception {

        assertThat(mappings.get("xml"), equalTo(APPLICATION_XML_TYPE));
    }

    @Test
    public void jspAndDefaultMapsToTextHtmlMediaType() throws Exception {

        assertThat(mappings.get("jsp"), equalTo(TEXT_HTML_TYPE));
        assertThat(mappings.get(""), equalTo(TEXT_HTML_TYPE));
    }
}
