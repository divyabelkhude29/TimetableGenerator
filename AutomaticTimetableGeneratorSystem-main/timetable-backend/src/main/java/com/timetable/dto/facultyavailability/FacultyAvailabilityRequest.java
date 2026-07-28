package com.timetable.dto.facultyavailability;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class FacultyAvailabilityRequest {

    @NotNull(message = "Faculty ID is required")
    private Long facultyId;

    @NotBlank(message = "Day of week is required")
    private String dayOfWeek;

    @NotNull(message = "Time Slot ID is required")
    private Long timeSlotId;

    @NotNull(message = "Availability status is required")
    private Boolean available;

    public FacultyAvailabilityRequest() {
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Long getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}