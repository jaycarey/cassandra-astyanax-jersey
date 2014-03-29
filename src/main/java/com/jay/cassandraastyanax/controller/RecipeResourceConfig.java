package com.jay.cassandraastyanax.controller;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.servlet.ServletProperties;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jaycarey
 */
public class RecipeResourceConfig extends ResourceConfig {

    public RecipeResourceConfig() {
        packages("com.jay.cassandraastyanax");

        // Content negotiating - nice for browser debugging.
        Map<String, MediaType> mappings = new HashMap<>();
        mappings.put("", MediaType.TEXT_HTML_TYPE);
        mappings.put("jsp", MediaType.TEXT_HTML_TYPE);
        mappings.put("json", MediaType.APPLICATION_JSON_TYPE);
        mappings.put("xml", MediaType.APPLICATION_XML_TYPE);
        property(ServerProperties.MEDIA_TYPE_MAPPINGS, mappings);

        // MVC Support.
        register(JspMvcFeature.class);
        property(JspMvcFeature.TEMPLATES_BASE_PATH, "/WEB-INF/jsp");
        property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "/WEB-INF/(js|css|images)/.*");

        // Json mapping.
        register(JacksonFeature.class);

        // Exception mapping.
        register(GlobalExceptionMapper.class);
    }
}
