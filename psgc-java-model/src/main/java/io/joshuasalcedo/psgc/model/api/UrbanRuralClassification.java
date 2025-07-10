package io.joshuasalcedo.psgc.model.api;

// 4. Urban/Rural Classification Enum
public enum UrbanRuralClassification {
    URBAN("Urban"),
    RURAL("Rural"),
    BLANK(""); // For entries with no classification

    private final String value;

    UrbanRuralClassification(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UrbanRuralClassification fromValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return BLANK;
        }
        for (UrbanRuralClassification urc : values()) {
            if (urc.value.equalsIgnoreCase(value.trim())) {
                return urc;
            }
        }
        throw new IllegalArgumentException("Unknown urban/rural classification: " + value);
    }
}
