package com.timetable.util;

import java.io.ByteArrayOutputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.timetable.dto.report.ReportRequest;
import com.timetable.dto.report.TimetableReportResponse;
import com.timetable.dto.timetablegeneration.TimetableSlotDTO;

@Component
public class ExcelExportUtil {

    public ExcelExportUtil() {

    }

    /**
     * Export Timetable to Excel (.xlsx)
     */
    public byte[] exportExcel(
            ReportRequest request,
            TimetableReportResponse report) {

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream =
                     new ByteArrayOutputStream()) {

            Sheet sheet =
                    workbook.createSheet("Timetable Report");

            int rowIndex = 0;

            /*
             * Report Title
             */
            Row titleRow =
                    sheet.createRow(rowIndex++);

            titleRow.createCell(0)
                    .setCellValue("TIMETABLE REPORT");

            rowIndex++;

            /*
             * Report Information
             */
            Row row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue("Academic Year ID");
            row.createCell(1).setCellValue(request.getAcademicYearId());

            row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue("Course ID");
            row.createCell(1).setCellValue(request.getCourseId());

            row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue("Semester ID");
            row.createCell(1).setCellValue(request.getSemesterId());

            row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue("Academic Section ID");
            row.createCell(1).setCellValue(request.getAcademicSectionId());

            row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue("Report Type");
            row.createCell(1).setCellValue(request.getReportType());

            rowIndex++;

            /*
             * Statistics
             */
            row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue("Total Classes");
            row.createCell(1).setCellValue(report.getTotalClasses());

            row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue("Total Faculties");
            row.createCell(1).setCellValue(report.getTotalFaculties());

            row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue("Total Subjects");
            row.createCell(1).setCellValue(report.getTotalSubjects());

            row = sheet.createRow(rowIndex++);
            row.createCell(0).setCellValue("Total Classrooms");
            row.createCell(1).setCellValue(report.getTotalClassrooms());

            rowIndex++;

            /*
             * Table Header
             */
            Row header =
                    sheet.createRow(rowIndex++);

            header.createCell(0).setCellValue("Day");
            header.createCell(1).setCellValue("Time Slot");
            header.createCell(2).setCellValue("Subject");
            header.createCell(3).setCellValue("Faculty");
            header.createCell(4).setCellValue("Classroom");

            /*
             * Timetable Data
             */
            if (report.getTimetable() != null) {

                for (TimetableSlotDTO slot
                        : report.getTimetable()) {

                    Row data =
                            sheet.createRow(rowIndex++);

                    data.createCell(0)
                            .setCellValue(value(slot.getDayOfWeek()));

                    data.createCell(1)
                            .setCellValue(value(slot.getTimeSlotId()));

                    data.createCell(2)
                            .setCellValue(value(slot.getSubjectId()));

                    data.createCell(3)
                            .setCellValue(value(slot.getFacultyId()));

                    data.createCell(4)
                            .setCellValue(value(slot.getClass()));
                }
            }

            /*
             * Auto-size Columns
             */
            for (int i = 0; i < 5; i++) {

                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);

            return outputStream.toByteArray();

        } catch (Exception ex) {

            throw new RuntimeException(
                    "Unable to generate Excel report.",
                    ex);
        }
    }

    /**
     * Null-safe conversion
     */
    private String value(Object value) {

        return value == null
                ? "-"
                : value.toString();
    }

}