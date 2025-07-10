package io.joshuasalcedo.psgc.model.exception;

/**
 * Base exception for all PSGC-related errors
 */
public class PSGCException extends Exception {
    
    public PSGCException(String message) {
        super(message);
    }
    
    public PSGCException(String message, Throwable cause) {
        super(message, cause);
    }
}

