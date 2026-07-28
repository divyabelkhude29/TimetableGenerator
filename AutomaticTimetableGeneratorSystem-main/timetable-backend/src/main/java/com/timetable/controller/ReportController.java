package com.timetable.controller;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.report.ReportRequest;
import com.timetable.dto.report.TimetableReportResponse;
import com.timetable.service.ReportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reports")
@Validated
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    public ReportController(
            ReportService reportService) {

        this.reportService = reportService;
    }

    /**
     * Export Timetable as PDF
     */
    @PostMapping("/pdf")
    public ResponseEntity<byte[]> exportPdf(
            @Valid @RequestBody ReportRequest request) {

        byte[] pdf =
                reportService.exportPdf(request);

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.APPLICATION_PDF);

        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename("Timetable_Report.pdf")
                        .build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }

    /**
     * Export Timetable as Excel
     */
    @PostMapping("/excel")
    public ResponseEntity<byte[]> exportExcel(
            @Valid @RequestBody ReportRequest request) {

        byte[] excel =
                reportService.exportExcel(request);

        HttpHeaders headers =
                new HttpHeaders();

        headers.setContentType(
                MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));

        headers.setContentDisposition(
                ContentDisposition.attachment()
                        .filename("Timetable_Report.xlsx")
                        .build());

        return ResponseEntity.ok()
                .headers(headers)
                .body(excel);
    }

    /**
     * View Timetable Report
     */
    @GetMapping("/view/{academicSectionId}")
    public ResponseEntity<TimetableReportResponse> viewReport(
            @PathVariable Long academicSectionId) {

        return ResponseEntity.ok(
                reportService.getTimetableReport(
                        academicSectionId));
    }

    /**
     * View Timetable Statistics
     */
    @GetMapping("/statistics/{academicSectionId}")
    public ResponseEntity<TimetableReportResponse> getStatistics(
            @PathVariable Long academicSectionId) {

        return ResponseEntity.ok(
                reportService.getStatistics(
                        academicSectionId));
    }

}