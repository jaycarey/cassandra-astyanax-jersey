package com.jay.cassandraastyanax.exception;

/**
 * @author jaycarey
 */
public class SystemException extends RuntimeException {

    public SystemException(String message, Throwable e) {
        super(message, e);
    }
}
