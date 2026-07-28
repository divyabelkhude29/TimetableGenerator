package com.timetable.mapper;

import com.timetable.dto.faculty.FacultyRequest;
import com.timetable.dto.faculty.FacultyResponse;
import com.timetable.entity.Department;
import com.timetable.entity.Faculty;

public class FacultyMapper {

    private FacultyMapper() {
    }

    /**
     * Request DTO -> Entity
     */
    public static Faculty toEntity(FacultyRequest request) {

        Faculty faculty = new Faculty();

        faculty.setFacultyCode(request.getFacultyCode());
        faculty.setFirstName(request.getFirstName());
        faculty.setLastName(request.getLastName());
        faculty.setEmail(request.getEmail());
        faculty.setPhone(request.getPhone());
        faculty.setDesignation(request.getDesignation());
        faculty.setQualification(request.getQualification());
        faculty.setExperienceYears(request.getExperienceYears());
        faculty.setActive(request.getActive());

        Department department = new Department();
        department.setDepartmentId(request.getDepartmentId());
        faculty.setDepartment(department);

        return faculty;
    }

    /**
     * Entity -> Response DTO
     */
    public static FacultyResponse toResponse(Faculty faculty) {

        FacultyResponse response = new FacultyResponse();

        response.setFacultyId(faculty.getFacultyId());
        response.setFacultyCode(faculty.getFacultyCode());
        response.setFirstName(faculty.getFirstName());
        response.setLastName(faculty.getLastName());
        response.setEmail(faculty.getEmail());
        response.setPhone(faculty.getPhone());
        response.setDesignation(faculty.getDesignation());
        response.setQualification(faculty.getQualification());
        response.setExperienceYears(faculty.getExperienceYears());
        response.setActive(faculty.getActive());

        if (faculty.getDepartment() != null) {

            response.setDepartmentId(
                    faculty.getDepartment().getDepartmentId());

            response.setDepartmentCode(
                    faculty.getDepartment().getDepartmentCode());

            response.setDepartmentName(
                    faculty.getDepartment().getDepartmentName());
        }

        return response;
    }

    /**
     * Update Existing Entity
     */
    public static void updateEntity(
            Faculty faculty,
            FacultyRequest request) {

        faculty.setFacultyCode(request.getFacultyCode());
        faculty.setFirstName(request.getFirstName());
        faculty.setLastName(request.getLastName());
        faculty.setEmail(request.getEmail());
        faculty.setPhone(request.getPhone());
        faculty.setDesignation(request.getDesignation());
        faculty.setQualification(request.getQualification());
        faculty.setExperienceYears(request.getExperienceYears());
        faculty.setActive(request.getActive());

        Department department = new Department();
        department.setDepartmentId(request.getDepartmentId());

        faculty.setDepartment(department);
    }

}