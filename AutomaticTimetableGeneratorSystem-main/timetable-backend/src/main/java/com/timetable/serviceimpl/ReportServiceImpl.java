package com.timetable.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dto.report.ReportRequest;
import com.timetable.dto.report.TimetableReportResponse;
import com.timetable.dto.timetable.TimetableResponse;
import com.timetable.dto.timetablegeneration.TimetableSlotDTO;
import com.timetable.exception.ReportGenerationException;
import com.timetable.service.ReportService;
import com.timetable.service.TimetableService;
import com.timetable.util.ExcelExportUtil;
import com.timetable.util.PdfExportUtil;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {

    private final PdfExportUtil pdfExportUtil;

    private final ExcelExportUtil excelExportUtil;

    private final TimetableService timetableService;

    public ReportServiceImpl(
            PdfExportUtil pdfExportUtil,
            ExcelExportUtil excelExportUtil,
            TimetableService timetableService) {

        this.pdfExportUtil = pdfExportUtil;
        this.excelExportUtil = excelExportUtil;
        this.timetableService = timetableService;
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
         * Load the actual, persisted timetable entries
         * for this section.
         */
        List<TimetableResponse> entries =
                timetableService.getTimetablesBySection(
                        academicSectionId);

        List<TimetableSlotDTO> slots =
                entries.stream()
                        .map(this::toSlotDTO)
                        .collect(Collectors.toList());

        response.setTimetable(slots);

        if (!entries.isEmpty()) {

            response.setSemesterId(
                    entries.get(0).getSemesterId());
        }

        response.setTotalClasses(
                entries.size());

        response.setTotalFaculties(
                (int) entries.stream()
                        .map(TimetableResponse::getFacultyId)
                        .distinct()
                        .count());

        response.setTotalSubjects(
                (int) entries.stream()
                        .map(TimetableResponse::getSubjectId)
                        .distinct()
                        .count());

        response.setTotalClassrooms(
                (int) entries.stream()
                        .map(TimetableResponse::getClassroomId)
                        .distinct()
                        .count());

        response.setGeneratedAt(
                LocalDateTime.now());

        return response;
    }

    /**
     * Convert a persisted timetable entry into a report slot.
     */
    private TimetableSlotDTO toSlotDTO(
            TimetableResponse entry) {

        TimetableSlotDTO slot =
                new TimetableSlotDTO();

        slot.setDayOfWeek(entry.getDayOfWeek());
        slot.setTimeSlotId(entry.getTimeSlotId());
        slot.setStartTime(entry.getStartTime());
        slot.setEndTime(entry.getEndTime());
        slot.setSemesterId(entry.getSemesterId());
        slot.setSemesterNumber(entry.getSemesterNumber());
        slot.setAcademicSectionId(entry.getSectionId());
        slot.setSectionName(entry.getSectionName());
        slot.setSubjectId(entry.getSubjectId());
        slot.setSubjectName(entry.getSubjectName());
        slot.setFacultyId(entry.getFacultyId());
        slot.setFacultyName(entry.getFacultyName());
        slot.setClassroomId(entry.getClassroomId());
        slot.setClassroomName(entry.getClassroomName());
        slot.setAllocationId(entry.getAllocationId());

        return slot;
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

        List<TimetableResponse> entries =
                timetableService.getTimetablesBySection(
                        academicSectionId);

        response.setTotalClasses(
                entries.size());

        response.setTotalFaculties(
                (int) entries.stream()
                        .map(TimetableResponse::getFacultyId)
                        .distinct()
                        .count());

        response.setTotalSubjects(
                (int) entries.stream()
                        .map(TimetableResponse::getSubjectId)
                        .distinct()
                        .count());

        response.setTotalClassrooms(
                (int) entries.stream()
                        .map(TimetableResponse::getClassroomId)
                        .distinct()
                        .count());

        response.setGeneratedAt(
                LocalDateTime.now());

        return response;
    }

}