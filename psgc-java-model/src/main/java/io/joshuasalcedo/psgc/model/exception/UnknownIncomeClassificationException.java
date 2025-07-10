package io.joshuasalcedo.psgc.model.exception;

/**
 * Exception thrown when an income classification is not recognized
 */
public class UnknownIncomeClassificationException extends PSGCException {
    
    private String unknownClassification;
    
    public UnknownIncomeClassificationException(String unknownClassification) {
        super(String.format("Unknown income classification: '%s'. Valid classifications are: 1st, 2nd, 3rd, 4th, 5th", 
                           unknownClassification));
        this.unknownClassification = unknownClassification;
    }
    
    public String getUnknownClassification() {
        return unknownClassification;
    }
}
