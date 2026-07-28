package com.timetable.dto.report;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.timetable.dto.timetablegeneration.TimetableSlotDTO;

public class TimetableReportResponse {

    private boolean success;

    private String message;

    private Long academicYearId;

    private Long courseId;

    private Long semesterId;

    private Long academicSectionId;

    /**
     * Total timetable entries.
     */
    private Integer totalClasses;

    /**
     * Total faculty involved.
     */
    private Integer totalFaculties;

    /**
     * Total subjects.
     */
    private Integer totalSubjects;

    /**
     * Total classrooms.
     */
    private Integer totalClassrooms;

    /**
     * Generated timetable entries.
     */
    private List<TimetableSlotDTO> timetable =
            new ArrayList<>();

    /**
     * Generation timestamp.
     */
    private LocalDateTime generatedAt;

    /**
     * Report remarks.
     */
    private List<String> remarks =
            new ArrayList<>();

    public TimetableReportResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(Long academicYearId) {
        this.academicYearId = academicYearId;
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

    public Long getAcademicSectionId() {
        return academicSectionId;
    }

    public void setAcademicSectionId(Long academicSectionId) {
        this.academicSectionId = academicSectionId;
    }

    public Integer getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(Integer totalClasses) {
        this.totalClasses = totalClasses;
    }

    public Integer getTotalFaculties() {
        return totalFaculties;
    }

    public void setTotalFaculties(Integer totalFaculties) {
        this.totalFaculties = totalFaculties;
    }

    public Integer getTotalSubjects() {
        return totalSubjects;
    }

    public void setTotalSubjects(Integer totalSubjects) {
        this.totalSubjects = totalSubjects;
    }

    public Integer getTotalClassrooms() {
        return totalClassrooms;
    }

    public void setTotalClassrooms(Integer totalClassrooms) {
        this.totalClassrooms = totalClassrooms;
    }

    public List<TimetableSlotDTO> getTimetable() {
        return timetable;
    }

    public void setTimetable(List<TimetableSlotDTO> timetable) {
        this.timetable = timetable;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public List<String> getRemarks() {
        return remarks;
    }

    public void setRemarks(List<String> remarks) {
        this.remarks = remarks;
    }

}