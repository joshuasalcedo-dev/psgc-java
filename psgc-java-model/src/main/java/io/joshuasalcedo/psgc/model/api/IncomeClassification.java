package io.joshuasalcedo.psgc.model.api;

// 3. Income Classification Enum
public enum IncomeClassification {
    FIRST("1st"),
    SECOND("2nd"),
    THIRD("3rd"),
    FOURTH("4th"),
    FIFTH("5th");

    private final String label;

    IncomeClassification(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static IncomeClassification fromLabel(String label) {
        for (IncomeClassification ic : values()) {
            if (ic.label.equalsIgnoreCase(label)) {
                return ic;
            }
        }
        throw new IllegalArgumentException("Unknown income classification: " + label);
    }
}
