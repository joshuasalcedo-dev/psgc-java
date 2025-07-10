package io.joshuasalcedo.psgc.model.exception;

/**
 * Exception thrown when parsing PSGC data fails
 */
public class PSGCParsingException extends PSGCException {
    
    private int rowNumber;
    private String columnName;
    private String cellValue;
    
    public PSGCParsingException(String message) {
        super(message);
    }
    
    public PSGCParsingException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public PSGCParsingException(int rowNumber, String columnName, String cellValue, String message) {
        super(String.format("Error parsing row %d, column '%s' with value '%s': %s", 
                           rowNumber, columnName, cellValue, message));
        this.rowNumber = rowNumber;
        this.columnName = columnName;
        this.cellValue = cellValue;
    }
    
    public PSGCParsingException(int rowNumber, String columnName, String cellValue, String message, Throwable cause) {
        super(String.format("Error parsing row %d, column '%s' with value '%s': %s", 
                           rowNumber, columnName, cellValue, message), cause);
        this.rowNumber = rowNumber;
        this.columnName = columnName;
        this.cellValue = cellValue;
    }
    
    public int getRowNumber() {
        return rowNumber;
    }
    
    public String getColumnName() {
        return columnName;
    }
    
    public String getCellValue() {
        return cellValue;
    }
}
