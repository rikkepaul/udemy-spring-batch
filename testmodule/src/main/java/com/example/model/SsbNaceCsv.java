package com.example.model;

public class SsbNaceCsv {
    private String code;
    private String parentCode;
    private String level;
    private String name;
    private String shortName;
    private String notes;
    //private String validFrom;
    //private String validTo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /*public String getValidFrom() {
        return validFrom;
    }
    public void setValidFrom(String validFrom) {
        this.validFrom = validFrom;
    }
    public String getValidTo() {
        return validTo;
    }
    public void setValidTo(String validTo) {
        this.validTo = validTo;
    }*/
    @Override
    public String toString() {
        return "SsbNaceV30{" +
                "code='" + code + '\'' +
                ", parentCode='" + parentCode + '\'' +
                ", level='" + level + '\'' +
                ", name='" + name + '\'' +
                ", shortName='" + shortName + '\'' +
                ", notes='" + notes + '\'' +
                // ", validFrom='" + validFrom + '\'' +
                // ", validTo='" + validTo + '\'' +
                '}';
    }
}
