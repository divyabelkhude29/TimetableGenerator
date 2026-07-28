package com.timetable.mapper;

import com.timetable.dto.semester.SemesterRequest;
import com.timetable.dto.semester.SemesterResponse;
import com.timetable.entity.Semester;

public class SemesterMapper {

    /**
     * Request DTO -> Entity
     */
    public static Semester toEntity(SemesterRequest request) {

        Semester semester = new Semester();

        semester.setSemesterNumber(request.getSemesterNumber());
        semester.setSemesterName(request.getSemesterName());
        semester.setAcademicYear(request.getAcademicYear());
        semester.setStartDate(request.getStartDate());
        semester.setEndDate(request.getEndDate());
        semester.setActive(request.getActive());

        return semester;
    }

    /**
     * Entity -> Response DTO
     */
    public static SemesterResponse toResponse(Semester semester) {

        SemesterResponse response = new SemesterResponse();

        response.setSemesterId(semester.getSemesterId());
        response.setSemesterNumber(semester.getSemesterNumber());
        response.setSemesterName(semester.getSemesterName());
        response.setAcademicYear(semester.getAcademicYear());
        response.setStartDate(semester.getStartDate());
        response.setEndDate(semester.getEndDate());
        response.setActive(semester.getActive());

        if (semester.getCourse() != null) {

            response.setCourseId(
                    semester.getCourse().getCourseId());

            response.setCourseCode(
                    semester.getCourse().getCourseCode());

            response.setCourseName(
                    semester.getCourse().getCourseName());
        }

        return response;
    }

    /**
     * Update Existing Entity
     */
    public static void updateEntity(Semester semester,
                                    SemesterRequest request) {

        semester.setSemesterNumber(request.getSemesterNumber());
        semester.setSemesterName(request.getSemesterName());
        semester.setAcademicYear(request.getAcademicYear());
        semester.setStartDate(request.getStartDate());
        semester.setEndDate(request.getEndDate());
        semester.setActive(request.getActive());
    }

}