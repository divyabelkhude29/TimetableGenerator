package com.timetable.dto.timetableconstraint;

public class TimetableConstraintResponse {

    private Long constraintId;

    private String constraintName;

    private String constraintKey;

    private String constraintValue;

    private String description;

    private Boolean active;

    public TimetableConstraintResponse() {
    }

    public Long getConstraintId() {
        return constraintId;
    }

    public void setConstraintId(Long constraintId) {
        this.constraintId = constraintId;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public String getConstraintKey() {
        return constraintKey;
    }

    public void setConstraintKey(String constraintKey) {
        this.constraintKey = constraintKey;
    }

    public String getConstraintValue() {
        return constraintValue;
    }

    public void setConstraintValue(String constraintValue) {
        this.constraintValue = constraintValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}