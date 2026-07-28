package com.timetable.dto.report;

import jakarta.validation.constraints.NotNull;

public class ReportRequest {

    @NotNull(message = "Academic Year ID is required.")
    private Long academicYearId;

    @NotNull(message = "Course ID is required.")
    private Long courseId;

    @NotNull(message = "Semester ID is required.")
    private Long semesterId;

    @NotNull(message = "Academic Section ID is required.")
    private Long academicSectionId;

    /**
     * Report Type
     *
     * Examples:
     * TIMETABLE
     * FACULTY
     * STUDENT
     * CLASSROOM
     * SUBJECT
     * WORKLOAD
     */
    private String reportType = "TIMETABLE";

    /**
     * Export Format
     *
     * PDF
     * EXCEL
     */
    private String exportFormat = "PDF";

    public ReportRequest() {
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

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getExportFormat() {
        return exportFormat;
    }

    public void setExportFormat(String exportFormat) {
        this.exportFormat = exportFormat;
    }

}