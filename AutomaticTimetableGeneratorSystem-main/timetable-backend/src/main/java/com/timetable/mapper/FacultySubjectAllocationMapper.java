package com.timetable.mapper;

import com.timetable.dto.allocation.FacultySubjectAllocationRequest;
import com.timetable.dto.allocation.FacultySubjectAllocationResponse;
import com.timetable.entity.FacultySubjectAllocation;

public class FacultySubjectAllocationMapper {

    private FacultySubjectAllocationMapper() {
    }

    /**
     * Convert Request DTO to Entity
     */
    public static FacultySubjectAllocation toEntity(
            FacultySubjectAllocationRequest request) {

        FacultySubjectAllocation allocation =
                new FacultySubjectAllocation();

        allocation.setAcademicYear(request.getAcademicYear());
        allocation.setActive(request.getActive());

        return allocation;
    }

    /**
     * Update Existing Entity
     */
    public static void updateEntity(
            FacultySubjectAllocation allocation,
            FacultySubjectAllocationRequest request) {

        allocation.setAcademicYear(request.getAcademicYear());
        allocation.setActive(request.getActive());
    }

    /**
     * Convert Entity to Response DTO
     */
    public static FacultySubjectAllocationResponse toResponse(
            FacultySubjectAllocation allocation) {

        FacultySubjectAllocationResponse response =
                new FacultySubjectAllocationResponse();

        response.setAllocationId(allocation.getAllocationId());
        response.setAcademicYear(allocation.getAcademicYear());
        response.setActive(allocation.getActive());

        // Faculty
        if (allocation.getFaculty() != null) {

            response.setFacultyId(
                    allocation.getFaculty().getFacultyId());

            response.setFacultyName(
                    allocation.getFaculty().getFirstName()
                            + " "
                            + allocation.getFaculty().getLastName());
        }

        // Subject
        if (allocation.getSubject() != null) {

            response.setSubjectId(
                    allocation.getSubject().getSubjectId());

            response.setSubjectName(
                    allocation.getSubject().getSubjectName());
        }

        // Section
        if (allocation.getSection() != null) {

            response.setSectionId(
                    allocation.getSection().getSectionId());

            response.setSectionName(
                    allocation.getSection().getSectionName());
        }

        // Semester
        if (allocation.getSemester() != null) {

            response.setSemesterId(
                    allocation.getSemester().getSemesterId());

            response.setSemesterNumber(
                    allocation.getSemester().getSemesterNumber());
        }

        return response;
    }
}