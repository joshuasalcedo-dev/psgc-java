package io.joshuasalcedo.psgc.model.api;

import java.math.BigDecimal;

// 8. Income Range Model (for storing income thresholds)
public class IncomeRange {
    private LGUType lguType;
    private IncomeClassification classification;
    private BigDecimal minIncome;
    private BigDecimal maxIncome;

    public IncomeRange(LGUType lguType, IncomeClassification classification, 
                      BigDecimal minIncome, BigDecimal maxIncome) {
        this.lguType = lguType;
        this.classification = classification;
        this.minIncome = minIncome;
        this.maxIncome = maxIncome;
    }

    public boolean isInRange(BigDecimal income) {
        if (minIncome == null) {
            return income.compareTo(maxIncome) < 0;
        }
        if (maxIncome == null) {
            return income.compareTo(minIncome) >= 0;
        }
        return income.compareTo(minIncome) >= 0 && income.compareTo(maxIncome) < 0;
    }

    // Getters
    public LGUType getLguType() { return lguType; }
    public IncomeClassification getClassification() { return classification; }
    public BigDecimal getMinIncome() { return minIncome; }
    public BigDecimal getMaxIncome() { return maxIncome; }
}
