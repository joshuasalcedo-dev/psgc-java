package io.joshuasalcedo.psgc.model.exception;

/**
 * Exception thrown when a PSGC code is invalid or malformed
 */
public class InvalidPSGCCodeException extends PSGCException {
    
    private String invalidCode;
    private String expectedFormat;
    
    public InvalidPSGCCodeException(String invalidCode, String expectedFormat, String message) {
        super(message);
        this.invalidCode = invalidCode;
        this.expectedFormat = expectedFormat;
    }
    
    public InvalidPSGCCodeException(String invalidCode, String expectedFormat) {
        this(invalidCode, expectedFormat, 
             String.format("Invalid PSGC code: '%s'. Expected format: %s", 
                          invalidCode, expectedFormat));
    }
    
    public InvalidPSGCCodeException(String message) {
        super(message);
    }
    
    public String getInvalidCode() {
        return invalidCode;
    }
    
    public String getExpectedFormat() {
        return expectedFormat;
    }
    
    /**
     * Factory method for 10-digit code validation errors
     */
    public static InvalidPSGCCodeException invalid10DigitCode(String code) {
        if (code == null) {
            return new InvalidPSGCCodeException("null", "10-digit numeric code", 
                "PSGC code cannot be null");
        }
        if (code.length() != 10) {
            return new InvalidPSGCCodeException(code, "10-digit numeric code", 
                String.format("Invalid 10-digit PSGC code: '%s'. Expected exactly 10 digits, but got %d", 
                    code, code.length()));
        }
        return new InvalidPSGCCodeException(code, "10-digit numeric code", 
            String.format("Invalid 10-digit PSGC code: '%s'. Code must contain only numeric digits", code));
    }
    
    /**
     * Factory method for 9-digit code validation errors
     */
    public static InvalidPSGCCodeException invalid9DigitCode(String code) {
        if (code == null) {
            return new InvalidPSGCCodeException("null", "9-digit numeric code", 
                "PSGC code cannot be null");
        }
        if (code.length() != 9) {
            return new InvalidPSGCCodeException(code, "9-digit numeric code", 
                String.format("Invalid 9-digit PSGC code: '%s'. Expected exactly 9 digits, but got %d", 
                    code, code.length()));
        }
        return new InvalidPSGCCodeException(code, "9-digit numeric code", 
            String.format("Invalid 9-digit PSGC code: '%s'. Code must contain only numeric digits", code));
    }
}
