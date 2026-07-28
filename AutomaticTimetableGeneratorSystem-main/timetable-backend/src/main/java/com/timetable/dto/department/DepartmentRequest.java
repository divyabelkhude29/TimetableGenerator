package com.timetable.dto.department;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class DepartmentRequest {

    @NotBlank(message = "Department code is required.")
    @Size(min = 2, max = 10,
            message = "Department code must be between 2 and 10 characters.")
    private String departmentCode;

    @NotBlank(message = "Department name is required.")
    @Size(min = 3, max = 100,
            message = "Department name must be between 3 and 100 characters.")
    private String departmentName;

    @Size(max = 100,
            message = "HOD name cannot exceed 100 characters.")
    private String hodName;

    @Size(max = 255,
            message = "Description cannot exceed 255 characters.")
    private String description;

    private Boolean active;

    public DepartmentRequest() {
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getHodName() {
        return hodName;
    }

    public void setHodName(String hodName) {
        this.hodName = hodName;
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