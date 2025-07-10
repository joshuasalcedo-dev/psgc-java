package io.joshuasalcedo.psgc.model.api;

// 5. Status Enum (for Capital/Poblacion)
public enum LocationStatus {
    CAPITAL("Capital"),
    POBLACION("Pob."),
    BLANK(""); // For regular locations

    private final String value;

    LocationStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static LocationStatus fromValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            return BLANK;
        }
        for (LocationStatus status : values()) {
            if (status.value.equalsIgnoreCase(value.trim())) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown location status: " + value);
    }
}
