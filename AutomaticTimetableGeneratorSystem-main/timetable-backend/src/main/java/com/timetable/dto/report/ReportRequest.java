package com.timetable.dto.report;

import jakarta.validation.constraints.NotNull;

public class ReportRequest {

    /*
     * Optional - the system does not currently expose a
     * standalone Academic Year entity/ID, so this is not
     * required. Kept for forward compatibility.
     */
    private Long academicYearId;

    /*
     * Optional - informational only, the report is generated
     * from academicSectionId.
     */
    private Long courseId;

    /*
     * Optional - informational only, the report is generated
     * from academicSectionId.
     */
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