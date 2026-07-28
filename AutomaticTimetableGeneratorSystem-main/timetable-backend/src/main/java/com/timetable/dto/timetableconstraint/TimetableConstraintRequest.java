package com.timetable.dto.timetableconstraint;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TimetableConstraintRequest {

    @NotBlank(message = "Constraint name is required")
    private String constraintName;

    @NotBlank(message = "Constraint key is required")
    private String constraintKey;

    @NotBlank(message = "Constraint value is required")
    private String constraintValue;

    private String description;

    @NotNull(message = "Active status is required")
    private Boolean active;

    public TimetableConstraintRequest() {
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