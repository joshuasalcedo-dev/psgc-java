package io.joshuasalcedo.psgc.model.api;// Package: io.joshuasalcedo.psgc.model.api

import io.joshuasalcedo.psgc.model.exception.InvalidPSGCCodeException;

import java.math.BigDecimal;

// 1. Geographic Level Enum (already created)
public enum GeographicLevel {
    REGION("Reg", "Region"),
    PROVINCE("Prov", "Province"),
    MUNICIPALITY("Mun", "Municipality"),
    BARANGAY("Bgy", "Barangay"),
    DISTRICT("Dist", "District"),
    SUB_MUNICIPALITY("SubMun", "Sub-municipality or Municipal District");

    private final String code;
    private final String description;

    GeographicLevel(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static GeographicLevel fromCode(String code) {
        for (GeographicLevel level : values()) {
            if (level.code.equalsIgnoreCase(code)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Unknown geographic level code: " + code);
    }
}

