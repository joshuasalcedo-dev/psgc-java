package io.joshuasalcedo.psgc.model.api;

// 7. Main PSGC Entry Model
public class PSGCEntry {
    private PSGCCode code;
    private String name;
    private String oldName; // For historical tracking
    private GeographicLevel geographicLevel;
    private LGUType lguType;
    private String cityClass; // For cities (CC, HUC, ICC)
    private IncomeClassification incomeClass;
    private boolean retainedIncomeClass; // For downgraded LGUs per RA 11964
    private UrbanRuralClassification urbanRural;
    private Integer population2020;
    private LocationStatus status;
    private String notes; // For special notes about the entry

    // Constructor
    public PSGCEntry() {
        this.code = new PSGCCode();
    }

    // Getters and setters
    public PSGCCode getCode() { return code; }
    public void setCode(PSGCCode code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getOldName() { return oldName; }
    public void setOldName(String oldName) { this.oldName = oldName; }

    public GeographicLevel getGeographicLevel() { return geographicLevel; }
    public void setGeographicLevel(GeographicLevel geographicLevel) { 
        this.geographicLevel = geographicLevel; 
    }

    public LGUType getLguType() { return lguType; }
    public void setLguType(LGUType lguType) { this.lguType = lguType; }

    public String getCityClass() { return cityClass; }
    public void setCityClass(String cityClass) { this.cityClass = cityClass; }

    public IncomeClassification getIncomeClass() { return incomeClass; }
    public void setIncomeClass(IncomeClassification incomeClass) { 
        this.incomeClass = incomeClass; 
    }

    public boolean isRetainedIncomeClass() { return retainedIncomeClass; }
    public void setRetainedIncomeClass(boolean retainedIncomeClass) { 
        this.retainedIncomeClass = retainedIncomeClass; 
    }

    public UrbanRuralClassification getUrbanRural() { return urbanRural; }
    public void setUrbanRural(UrbanRuralClassification urbanRural) { 
        this.urbanRural = urbanRural; 
    }

    public Integer getPopulation2020() { return population2020; }
    public void setPopulation2020(Integer population2020) { 
        this.population2020 = population2020; 
    }

    public LocationStatus getStatus() { return status; }
    public void setStatus(LocationStatus status) { this.status = status; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    // Helper method to determine if this is a city
    public boolean isCity() {
        return lguType == LGUType.CITY || 
               (cityClass != null && !cityClass.isEmpty());
    }

    // Helper method to get full location name with type
    public String getFullName() {
        StringBuilder sb = new StringBuilder();
        
        if (isCity()) {
            sb.append("City of ");
        } else if (lguType == LGUType.MUNICIPALITY) {
            sb.append("Municipality of ");
        } else if (lguType == LGUType.PROVINCE) {
            sb.append("Province of ");
        }
        
        sb.append(name);
        
        if (status == LocationStatus.CAPITAL) {
            sb.append(" (Capital)");
        } else if (status == LocationStatus.POBLACION) {
            sb.append(" (Poblacion)");
        }
        
        return sb.toString();
    }
}
