package com.jay.cassandraastyanax.controller;

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

    @Before
    public void before() throws Exception {
        recipeResourceConfig = new RecipeResourceConfig();
    }

    @Test
    public void jsonMapsToApplicationJsonMediaType() throws Exception {
        Map<String, MediaType> mappings = recipeResourceConfig.getMediaTypeMappings();

        assertThat(mappings.get("json"), equalTo(APPLICATION_JSON_TYPE));
    }

    @Test
    public void xmlMapsToApplicationXmlMediaType() throws Exception {
        Map<String, MediaType> mappings = recipeResourceConfig.getMediaTypeMappings();

        assertThat(mappings.get("xml"), equalTo(APPLICATION_XML_TYPE));
    }

    @Test
    public void jspAndDefaultMapsToTextHtmlMediaType() throws Exception {
        Map<String, MediaType> mappings = recipeResourceConfig.getMediaTypeMappings();

        assertThat(mappings.get("jsp"), equalTo(TEXT_HTML_TYPE));
        assertThat(mappings.get(""), equalTo(TEXT_HTML_TYPE));
    }
}
