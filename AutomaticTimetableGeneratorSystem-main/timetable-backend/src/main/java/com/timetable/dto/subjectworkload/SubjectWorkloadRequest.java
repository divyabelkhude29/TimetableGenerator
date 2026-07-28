package com.timetable.dto.subjectworkload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class SubjectWorkloadRequest {

    @NotNull(message = "Faculty Subject Allocation ID is required")
    private Long allocationId;

    @NotNull(message = "Weekly hours are required")
    @Min(value = 1, message = "Weekly hours must be at least 1")
    private Integer weeklyHours;

    @NotNull(message = "Theory hours are required")
    @Min(value = 0, message = "Theory hours cannot be negative")
    private Integer theoryHours;

    @NotNull(message = "Practical hours are required")
    @Min(value = 0, message = "Practical hours cannot be negative")
    private Integer practicalHours;

    @NotNull(message = "Active status is required")
    private Boolean active;

    public SubjectWorkloadRequest() {
    }

    public Long getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(Long allocationId) {
        this.allocationId = allocationId;
    }

    public Integer getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(Integer weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public Integer getTheoryHours() {
        return theoryHours;
    }

    public void setTheoryHours(Integer theoryHours) {
        this.theoryHours = theoryHours;
    }

    public Integer getPracticalHours() {
        return practicalHours;
    }

    public void setPracticalHours(Integer practicalHours) {
        this.practicalHours = practicalHours;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}