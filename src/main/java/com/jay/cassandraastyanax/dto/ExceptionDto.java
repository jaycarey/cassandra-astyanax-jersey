package com.jay.cassandraastyanax.dto;

import java.util.ArrayList;
import java.util.List;

public class ExceptionDto {

    private final String message;

    private final List<String> frames;

    public ExceptionDto(Throwable exception) {
        message = exception.getMessage();
        frames = new ArrayList<>();
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            frames.add(stackTraceElement.toString());
        }
    }

    public String getMessage() {
        return message;
    }

    public List<String> getFrames() {
        return frames;
    }
}
