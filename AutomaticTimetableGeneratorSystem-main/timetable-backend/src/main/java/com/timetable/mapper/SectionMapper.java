package com.timetable.mapper;

import com.timetable.dto.section.SectionRequest;
import com.timetable.dto.section.SectionResponse;
import com.timetable.entity.Section;

public class SectionMapper {

    private SectionMapper() {
    }

    /**
     * Request DTO -> Entity
     */
    public static Section toEntity(SectionRequest request) {

        Section section = new Section();

        section.setSectionName(request.getSectionName());
        section.setSectionCode(request.getSectionCode());
        section.setAcademicYear(request.getAcademicYear());
        section.setStrength(request.getStrength());
        section.setActive(request.getActive());

        return section;
    }

    /**
     * Update Existing Entity
     */
    public static void updateEntity(Section section,
                                    SectionRequest request) {

        section.setSectionName(request.getSectionName());
        section.setSectionCode(request.getSectionCode());
        section.setAcademicYear(request.getAcademicYear());
        section.setStrength(request.getStrength());
        section.setActive(request.getActive());
    }

    /**
     * Entity -> Response DTO
     */
    public static SectionResponse toResponse(Section section) {

        SectionResponse response = new SectionResponse();

        response.setSectionId(section.getSectionId());
        response.setSectionName(section.getSectionName());
        response.setSectionCode(section.getSectionCode());
        response.setAcademicYear(section.getAcademicYear());
        response.setStrength(section.getStrength());
        response.setActive(section.getActive());

        // Department
        if (section.getDepartment() != null) {
            response.setDepartmentId(
                    section.getDepartment().getDepartmentId());
            response.setDepartmentName(
                    section.getDepartment().getDepartmentName());
        }

        // Course
        if (section.getCourse() != null) {
            response.setCourseId(
                    section.getCourse().getCourseId());
            response.setCourseName(
                    section.getCourse().getCourseName());
        }

        // Semester
        if (section.getSemester() != null) {
            response.setSemesterId(
                    section.getSemester().getSemesterId());
            response.setSemesterNumber(
                    section.getSemester().getSemesterNumber());
        }

        return response;
    }
}