package com.timetable.dto.timetable;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TimetableRequest {

    @NotBlank(message = "Day of week is required")
    private String dayOfWeek;

    private Boolean active = true;

    @NotNull(message = "Allocation ID is required")
    private Long allocationId;

    @NotNull(message = "Classroom ID is required")
    private Long classroomId;

    @NotNull(message = "Time Slot ID is required")
    private Long timeSlotId;

    public TimetableRequest() {
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getAllocationId() {
        return allocationId;
    }

    public void setAllocationId(Long allocationId) {
        this.allocationId = allocationId;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }

    public Long getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }
}