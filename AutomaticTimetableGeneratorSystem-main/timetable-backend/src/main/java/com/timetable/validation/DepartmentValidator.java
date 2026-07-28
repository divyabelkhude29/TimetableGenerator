package com.timetable.validation;

import org.springframework.stereotype.Component;

import com.timetable.dao.DepartmentDAO;
import com.timetable.dto.department.DepartmentRequest;
import com.timetable.entity.Department;
import com.timetable.exception.DuplicateRecordException;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.exception.ValidationException;

@Component
public class DepartmentValidator {

    private final DepartmentDAO departmentDAO;

    public DepartmentValidator(DepartmentDAO departmentDAO) {
        this.departmentDAO = departmentDAO;
    }

    /**
     * Validate Create Request
     */
    public void validateForCreate(DepartmentRequest request) {

        if (request == null) {
            throw new ValidationException("Department request cannot be null.");
        }

        validateFields(request);

        if (departmentDAO.existsByDepartmentCode(request.getDepartmentCode())) {
            throw new DuplicateRecordException(
                    "Department",
                    "departmentCode",
                    request.getDepartmentCode());
        }

        if (departmentDAO.existsByDepartmentName(request.getDepartmentName())) {
            throw new DuplicateRecordException(
                    "Department",
                    "departmentName",
                    request.getDepartmentName());
        }
    }

    /**
     * Validate Update Request
     */
    public void validateForUpdate(Long departmentId,
                                  DepartmentRequest request) {

        if (request == null) {
            throw new ValidationException("Department request cannot be null.");
        }

        validateFields(request);

        Department codeDepartment =
                departmentDAO.findByDepartmentCode(request.getDepartmentCode());

        if (codeDepartment != null &&
                !codeDepartment.getDepartmentId().equals(departmentId)) {

            throw new DuplicateRecordException(
                    "Department",
                    "departmentCode",
                    request.getDepartmentCode());
        }

        Department nameDepartment =
                departmentDAO.findByDepartmentName(request.getDepartmentName());

        if (nameDepartment != null &&
                !nameDepartment.getDepartmentId().equals(departmentId)) {

            throw new DuplicateRecordException(
                    "Department",
                    "departmentName",
                    request.getDepartmentName());
        }
    }

    /**
     * Validate Department Exists
     */
    public Department validateDepartmentExists(Long departmentId) {

        Department department = departmentDAO.findById(departmentId);

        if (department == null) {
            throw new ResourceNotFoundException(
                    "Department",
                    departmentId);
        }

        return department;
    }

    /**
     * Common Field Validation
     */
    private void validateFields(DepartmentRequest request) {

        if (request.getDepartmentCode() == null ||
                request.getDepartmentCode().trim().isEmpty()) {

            throw new ValidationException(
                    "departmentCode",
                    "Department code is required.");
        }

        if (request.getDepartmentName() == null ||
                request.getDepartmentName().trim().isEmpty()) {

            throw new ValidationException(
                    "departmentName",
                    "Department name is required.");
        }

        if (request.getDepartmentCode().length() < 2 ||
                request.getDepartmentCode().length() > 10) {

            throw new ValidationException(
                    "departmentCode",
                    "Department code must be between 2 and 10 characters.");
        }

        if (request.getDepartmentName().length() < 3 ||
                request.getDepartmentName().length() > 100) {

            throw new ValidationException(
                    "departmentName",
                    "Department name must be between 3 and 100 characters.");
        }

        if (request.getHodName() != null &&
                request.getHodName().length() > 100) {

            throw new ValidationException(
                    "hodName",
                    "HOD name cannot exceed 100 characters.");
        }

        if (request.getDescription() != null &&
                request.getDescription().length() > 255) {

            throw new ValidationException(
                    "description",
                    "Description cannot exceed 255 characters.");
        }
    }
}