package com.timetable.dto.timetablegeneration;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;


public class GenerateTimetableRequest {

    @NotBlank(message = "Academic Year is required.")
    private String academicYear;

    @NotNull(message = "Course ID is required.")
    private Long courseId;

    @NotNull(message = "Semester ID is required.")
    private Long semesterId;

    @NotNull(message = "Section ID is required.")
    private Long sectionId;

    /**
     * If true, existing timetable entries will be deleted
     * before generating a new timetable.
     */
    private boolean overwriteExisting = false;

    public GenerateTimetableRequest() {
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public boolean isOverwriteExisting() {
        return overwriteExisting;
    }

    public void setOverwriteExisting(boolean overwriteExisting) {
        this.overwriteExisting = overwriteExisting;
    }
}