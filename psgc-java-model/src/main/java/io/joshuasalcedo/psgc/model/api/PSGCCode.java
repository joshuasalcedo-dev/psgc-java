package io.joshuasalcedo.psgc.model.api;

import io.joshuasalcedo.psgc.model.exception.InvalidPSGCCodeException;

// 6. PSGC Code Model
public class PSGCCode {
    private String oldCode; // 9-digit code
    private String newCode; // 10-digit code
    private String regionCode;
    private String provinceCode;
    private String municipalityCode;
    private String barangayCode;

    // Constructors
    public PSGCCode() {}

    public PSGCCode(String newCode) throws InvalidPSGCCodeException {
        this.newCode = newCode;
        parseNewCode();
    }

    private void parseNewCode() throws InvalidPSGCCodeException {
        if (newCode == null || newCode.length() != 10) {
            throw InvalidPSGCCodeException.invalid10DigitCode(newCode);
        }
        
        // Validate that all characters are digits
        if (!newCode.matches("\\d{10}")) {
            throw InvalidPSGCCodeException.invalid10DigitCode(newCode);
        }
        
        this.regionCode = newCode.substring(0, 2);
        this.provinceCode = newCode.substring(2, 5);
        this.municipalityCode = newCode.substring(5, 7);
        this.barangayCode = newCode.substring(7, 10);
    }

    // Getters and setters
    public String getOldCode() { return oldCode; }
    public void setOldCode(String oldCode) { this.oldCode = oldCode; }
    
    public String getNewCode() { return newCode; }
    public void setNewCode(String newCode) throws InvalidPSGCCodeException { 
        this.newCode = newCode;
        parseNewCode();
    }
    
    public String getRegionCode() { return regionCode; }
    public String getProvinceCode() { return provinceCode; }
    public String getMunicipalityCode() { return municipalityCode; }
    public String getBarangayCode() { return barangayCode; }
}
