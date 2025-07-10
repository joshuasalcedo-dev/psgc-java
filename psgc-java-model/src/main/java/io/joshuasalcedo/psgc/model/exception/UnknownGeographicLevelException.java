package io.joshuasalcedo.psgc.model.exception;

/**
 * Exception thrown when a geographic level code is not recognized
 */
public class UnknownGeographicLevelException extends PSGCException {
    
    private String unknownCode;
    
    public UnknownGeographicLevelException(String unknownCode) {
        super(String.format("Unknown geographic level code: '%s'. Valid codes are: Reg, Prov, Mun, Bgy, Dist, SubMun", 
                           unknownCode));
        this.unknownCode = unknownCode;
    }
    
    public String getUnknownCode() {
        return unknownCode;
    }
}
