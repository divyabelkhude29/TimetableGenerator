package com.timetable.serviceimpl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dto.report.ReportRequest;
import com.timetable.dto.report.TimetableReportResponse;
import com.timetable.exception.ReportGenerationException;
import com.timetable.service.ReportService;
import com.timetable.util.ExcelExportUtil;
import com.timetable.util.PdfExportUtil;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final PdfExportUtil pdfExportUtil;

    private final ExcelExportUtil excelExportUtil;

    public ReportServiceImpl(
            PdfExportUtil pdfExportUtil,
            ExcelExportUtil excelExportUtil) {

        this.pdfExportUtil = pdfExportUtil;
        this.excelExportUtil = excelExportUtil;
    }

    /**
     * Export Timetable as PDF
     */
    @Override
    public byte[] exportPdf(
            ReportRequest request) {

        try {

            TimetableReportResponse report =
                    getTimetableReport(
                            request.getAcademicSectionId());

            return pdfExportUtil.exportPdf(
                    request,
                    report);

        } catch (Exception ex) {

            throw new ReportGenerationException(
                    "Unable to generate PDF report.",
                    ex);
        }
    }

    /**
     * Export Timetable as Excel
     */
    @Override
    public byte[] exportExcel(
            ReportRequest request) {

        try {

            TimetableReportResponse report =
                    getTimetableReport(
                            request.getAcademicSectionId());

            return excelExportUtil.exportExcel(
                    request,
                    report);

        } catch (Exception ex) {

            throw new ReportGenerationException(
                    "Unable to generate Excel report.",
                    ex);
        }
    }

    /**
     * View Timetable Report
     */
    @Override
    public TimetableReportResponse getTimetableReport(
            Long academicSectionId) {

        TimetableReportResponse response =
                new TimetableReportResponse();

        response.setSuccess(true);

        response.setMessage(
                "Timetable report generated successfully.");

        response.setAcademicSectionId(
                academicSectionId);

        /*
         * TODO
         * Load actual timetable data from database.
         */

        response.setTotalClasses(0);

        response.setTotalFaculties(0);

        response.setTotalSubjects(0);

        response.setTotalClassrooms(0);

        response.setGeneratedAt(
                LocalDateTime.now());

        return response;
    }

    /**
     * Timetable Statistics
     */
    @Override
    public TimetableReportResponse getStatistics(
            Long academicSectionId) {

        TimetableReportResponse response =
                new TimetableReportResponse();

        response.setSuccess(true);

        response.setMessage(
                "Timetable statistics generated successfully.");

        response.setAcademicSectionId(
                academicSectionId);

        /*
         * TODO
         * Calculate actual statistics from database.
         */

        response.setTotalClasses(0);

        response.setTotalFaculties(0);

        response.setTotalSubjects(0);

        response.setTotalClassrooms(0);

        response.getRemarks().add(
                "Statistics generation is currently using placeholder values.");

        response.setGeneratedAt(
                LocalDateTime.now());

        return response;
    }

}