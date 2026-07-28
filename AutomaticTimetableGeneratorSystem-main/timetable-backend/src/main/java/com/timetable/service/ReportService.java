package com.timetable.service;

import com.timetable.dto.report.ReportRequest;
import com.timetable.dto.report.TimetableReportResponse;

public interface ReportService {

    /**
     * Generate PDF report.
     *
     * @param request Report Request
     * @return PDF as byte array
     */
    byte[] exportPdf(
            ReportRequest request);

    /**
     * Generate Excel report.
     *
     * @param request Report Request
     * @return Excel as byte array
     */
    byte[] exportExcel(
            ReportRequest request);

    /**
     * View timetable report.
     *
     * @param academicSectionId Academic Section ID
     * @return Timetable Report
     */
    TimetableReportResponse getTimetableReport(
            Long academicSectionId);

    /**
     * Get timetable statistics.
     *
     * @param academicSectionId Academic Section ID
     * @return Statistics Report
     */
    TimetableReportResponse getStatistics(
            Long academicSectionId);

}