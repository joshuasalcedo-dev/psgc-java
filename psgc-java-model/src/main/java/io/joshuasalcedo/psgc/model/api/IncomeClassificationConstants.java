package io.joshuasalcedo.psgc.model.api;

import java.math.BigDecimal;

// 9. Constants class for Income Classifications
public class IncomeClassificationConstants {
    // Province income thresholds
    public static final BigDecimal PROVINCE_1ST_MIN = new BigDecimal("1500000000");
    public static final BigDecimal PROVINCE_2ND_MIN = new BigDecimal("900000000");
    public static final BigDecimal PROVINCE_3RD_MIN = new BigDecimal("700000000");
    public static final BigDecimal PROVINCE_4TH_MIN = new BigDecimal("500000000");
    
    // City income thresholds
    public static final BigDecimal CITY_1ST_MIN = new BigDecimal("1300000000");
    public static final BigDecimal CITY_2ND_MIN = new BigDecimal("1000000000");
    public static final BigDecimal CITY_3RD_MIN = new BigDecimal("800000000");
    public static final BigDecimal CITY_4TH_MIN = new BigDecimal("500000000");
    
    // Municipality income thresholds
    public static final BigDecimal MUNICIPALITY_1ST_MIN = new BigDecimal("200000000");
    public static final BigDecimal MUNICIPALITY_2ND_MIN = new BigDecimal("160000000");
    public static final BigDecimal MUNICIPALITY_3RD_MIN = new BigDecimal("130000000");
    public static final BigDecimal MUNICIPALITY_4TH_MIN = new BigDecimal("90000000");
}
