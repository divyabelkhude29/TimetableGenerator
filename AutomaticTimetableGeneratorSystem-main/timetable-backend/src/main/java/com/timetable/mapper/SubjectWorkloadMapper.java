package com.timetable.mapper;

import com.timetable.dto.subjectworkload.SubjectWorkloadRequest;
import com.timetable.dto.subjectworkload.SubjectWorkloadResponse;
import com.timetable.entity.SubjectWorkload;

public class SubjectWorkloadMapper {

    private SubjectWorkloadMapper() {
    }

    /**
     * Request DTO -> Entity
     */
    public static SubjectWorkload toEntity(
            SubjectWorkloadRequest request) {

        SubjectWorkload workload = new SubjectWorkload();

        workload.setWeeklyHours(request.getWeeklyHours());
        workload.setTheoryHours(request.getTheoryHours());
        workload.setPracticalHours(request.getPracticalHours());
        workload.setActive(request.getActive());

        return workload;
    }

    /**
     * Update Existing Entity
     */
    public static void updateEntity(
            SubjectWorkload workload,
            SubjectWorkloadRequest request) {

        workload.setWeeklyHours(request.getWeeklyHours());
        workload.setTheoryHours(request.getTheoryHours());
        workload.setPracticalHours(request.getPracticalHours());
        workload.setActive(request.getActive());
    }

    /**
     * Entity -> Response DTO
     */
    public static SubjectWorkloadResponse toResponse(
            SubjectWorkload workload) {

        SubjectWorkloadResponse response =
                new SubjectWorkloadResponse();

        response.setWorkloadId(workload.getWorkloadId());

        response.setWeeklyHours(workload.getWeeklyHours());
        response.setTheoryHours(workload.getTheoryHours());
        response.setPracticalHours(workload.getPracticalHours());
        response.setActive(workload.getActive());

        if (workload.getAllocation() != null) {

            response.setAllocationId(
                    workload.getAllocation().getAllocationId());

            // Faculty Details
            if (workload.getAllocation().getFaculty() != null) {

                response.setFacultyId(
                        workload.getAllocation()
                                .getFaculty()
                                .getFacultyId());

                response.setFacultyCode(
                        workload.getAllocation()
                                .getFaculty()
                                .getFacultyCode());

                response.setFacultyName(
                        workload.getAllocation()
                                .getFaculty()
                                .getFirstName()
                                + " "
                                + workload.getAllocation()
                                        .getFaculty()
                                        .getLastName());
            }

            // Subject Details
            if (workload.getAllocation().getSubject() != null) {

                response.setSubjectId(
                        workload.getAllocation()
                                .getSubject()
                                .getSubjectId());

                response.setSubjectCode(
                        workload.getAllocation()
                                .getSubject()
                                .getSubjectCode());

                response.setSubjectName(
                        workload.getAllocation()
                                .getSubject()
                                .getSubjectName());
            }

            // Section Details
            if (workload.getAllocation().getSection() != null) {

                response.setSectionId(
                        workload.getAllocation()
                                .getSection()
                                .getSectionId());

                response.setSectionName(
                        workload.getAllocation()
                                .getSection()
                                .getSectionName());
            }
        }

        return response;
    }
}