package com.jay.cassandraastyanax.controller;

import com.sun.jersey.api.core.PackagesResourceConfig;

import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jaycarey
 */
public class MediaTypeMappings extends PackagesResourceConfig {

    public MediaTypeMappings() {
        super("com.jay.cassandraastyanax");
    }

    @Override
    public Map<String, MediaType> getMediaTypeMappings() {
        Map<String, MediaType> mappings = new HashMap<>();
        mappings.put("", MediaType.TEXT_HTML_TYPE);
        mappings.put("jsp", MediaType.TEXT_HTML_TYPE);
        mappings.put("json", MediaType.APPLICATION_JSON_TYPE);
        mappings.put("xml", MediaType.APPLICATION_XML_TYPE);
        return mappings;
    }
}
