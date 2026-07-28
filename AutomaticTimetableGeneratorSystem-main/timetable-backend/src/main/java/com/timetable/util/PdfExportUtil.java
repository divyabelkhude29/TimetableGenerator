package com.timetable.util;

import java.io.ByteArrayOutputStream;

import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.timetable.dto.report.ReportRequest;
import com.timetable.dto.report.TimetableReportResponse;
import com.timetable.dto.timetablegeneration.TimetableSlotDTO;

@Component
public class PdfExportUtil {

    public PdfExportUtil() {

    }

    /**
     * Generate Timetable PDF
     */
    public byte[] exportPdf(
            ReportRequest request,
            TimetableReportResponse report) {

        try {

            ByteArrayOutputStream outputStream =
                    new ByteArrayOutputStream();

            Document document =
                    new Document();

            PdfWriter.getInstance(
                    document,
                    outputStream);

            document.open();

            /*
             * Title
             */
            Font titleFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            18);

            Paragraph title =
                    new Paragraph(
                            "TIMETABLE REPORT",
                            titleFont);

            title.setAlignment(Paragraph.ALIGN_CENTER);

            document.add(title);

            document.add(new Paragraph(" "));

            /*
             * Report Information
             */
            document.add(new Paragraph(
                    "Academic Year ID : "
                            + request.getAcademicYearId()));

            document.add(new Paragraph(
                    "Course ID : "
                            + request.getCourseId()));

            document.add(new Paragraph(
                    "Semester ID : "
                            + request.getSemesterId()));

            document.add(new Paragraph(
                    "Academic Section ID : "
                            + request.getAcademicSectionId()));

            document.add(new Paragraph(
                    "Report Type : "
                            + request.getReportType()));

            document.add(new Paragraph(" "));

            /*
             * Statistics
             */
            document.add(new Paragraph(
                    "Total Classes : "
                            + report.getTotalClasses()));

            document.add(new Paragraph(
                    "Total Faculties : "
                            + report.getTotalFaculties()));

            document.add(new Paragraph(
                    "Total Subjects : "
                            + report.getTotalSubjects()));

            document.add(new Paragraph(
                    "Total Classrooms : "
                            + report.getTotalClassrooms()));

            document.add(new Paragraph(" "));

            /*
             * Timetable Table
             */
            PdfPTable table =
                    new PdfPTable(5);

            table.setWidthPercentage(100);

            addHeader(table, "Day");
            addHeader(table, "Time Slot");
            addHeader(table, "Subject");
            addHeader(table, "Faculty");
            addHeader(table, "Classroom");

            if (report.getTimetable() != null) {

                for (TimetableSlotDTO slot
                        : report.getTimetable()) {

                    table.addCell(
                            value(slot.getDayOfWeek()));

                    table.addCell(
                            value(slot.getTimeSlotId()));

                    table.addCell(
                            value(slot.getSubjectId()));

                    table.addCell(
                            value(slot.getFacultyId()));

                    table.addCell(
                            value(slot.getClass()));
                }
            }

            document.add(table);

            document.close();

            return outputStream.toByteArray();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Unable to generate PDF report.",
                    ex);
        }
    }

    /**
     * Table Header
     */
    private void addHeader(
            PdfPTable table,
            String title) {

        Font font =
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD);

        PdfPCell cell =
                new PdfPCell(
                        new Phrase(title, font));

        table.addCell(cell);
    }

    /**
     * Null-safe Object
     */
    private String value(
            Object value) {

        return value == null
                ? "-"
                : value.toString();
    }

}