package com.timetable.validation;

import org.springframework.stereotype.Component;

import com.timetable.dao.DepartmentDAO;
import com.timetable.dao.FacultyDAO;
import com.timetable.dto.faculty.FacultyRequest;
import com.timetable.entity.Department;
import com.timetable.entity.Faculty;
import com.timetable.exception.ResourceNotFoundException;

@Component
public class FacultyValidation {

    private final FacultyDAO facultyDAO;
    private final DepartmentDAO departmentDAO;

    public FacultyValidation(FacultyDAO facultyDAO,
                             DepartmentDAO departmentDAO) {

        this.facultyDAO = facultyDAO;
        this.departmentDAO = departmentDAO;
    }

    /**
     * Validate Faculty Before Save
     */
    public void validateForCreate(FacultyRequest request) {

        Faculty faculty = facultyDAO.findByFacultyCode(request.getFacultyCode());

        if (faculty != null) {
            throw new IllegalArgumentException(
                    "Faculty code already exists : " + request.getFacultyCode());
        }

        Faculty email = facultyDAO.findByEmail(request.getEmail());

        if (email != null) {
            throw new IllegalArgumentException(
                    "Email already exists : " + request.getEmail());
        }

        Department department =
                departmentDAO.findById(request.getDepartmentId());

        if (department == null) {
            throw new ResourceNotFoundException(
                    "Department not found with ID : " + request.getDepartmentId());
        }
    }

    /**
     * Validate Faculty Before Update
     */
    public void validateForUpdate(Long facultyId,
                                  FacultyRequest request) {

        Faculty faculty = facultyDAO.findById(facultyId);

        if (faculty == null) {
            throw new ResourceNotFoundException(
                    "Faculty not found with ID : " + facultyId);
        }

        Faculty duplicateCode =
                facultyDAO.findByFacultyCode(request.getFacultyCode());

        if (duplicateCode != null &&
                !duplicateCode.getFacultyId().equals(facultyId)) {

            throw new IllegalArgumentException(
                    "Faculty code already exists.");
        }

        Faculty duplicateEmail =
                facultyDAO.findByEmail(request.getEmail());

        if (duplicateEmail != null &&
                !duplicateEmail.getFacultyId().equals(facultyId)) {

            throw new IllegalArgumentException(
                    "Email already exists.");
        }

        Department department =
                departmentDAO.findById(request.getDepartmentId());

        if (department == null) {
            throw new ResourceNotFoundException(
                    "Department not found with ID : " + request.getDepartmentId());
        }
    }

}