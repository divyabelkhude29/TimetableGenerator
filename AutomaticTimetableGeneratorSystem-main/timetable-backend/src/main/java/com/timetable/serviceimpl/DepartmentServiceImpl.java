package com.timetable.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.DepartmentDAO;
import com.timetable.dto.department.DepartmentRequest;
import com.timetable.dto.department.DepartmentResponse;
import com.timetable.entity.Department;
import com.timetable.exception.DuplicateRecordException;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.exception.ValidationException;
import com.timetable.mapper.DepartmentMapper;
import com.timetable.service.DepartmentService;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentDAO departmentDAO;

    private final DepartmentMapper departmentMapper;

    public DepartmentServiceImpl(DepartmentDAO departmentDAO,
                                 DepartmentMapper departmentMapper) {

        this.departmentDAO = departmentDAO;
        this.departmentMapper = departmentMapper;
    }

    /**
     * Create Department
     */
    @Override
    public DepartmentResponse createDepartment(DepartmentRequest request) {

        validateDepartment(request);

        Department department =
                departmentMapper.toEntity(request);

        Department savedDepartment =
                departmentDAO.save(department);

        return departmentMapper.toResponse(savedDepartment);
    }

    /**
     * Get Department By Id
     */
    @Override
    public DepartmentResponse getDepartmentById(Long departmentId) {

        Department department =
                departmentDAO.findById(departmentId);

        if (department == null) {

            throw new ResourceNotFoundException(
                    "Department",
                    departmentId);
        }

        return departmentMapper.toResponse(department);
    }

    /**
     * Get Department By Code
     */
    @Override
    public DepartmentResponse getDepartmentByCode(String departmentCode) {

        Department department =
                departmentDAO.findByDepartmentCode(departmentCode);

        if (department == null) {

            throw new ResourceNotFoundException(
                    "Department",
                    "departmentCode",
                    departmentCode);
        }

        return departmentMapper.toResponse(department);
    }

    /**
     * Get All Departments
     */
    @Override
    public List<DepartmentResponse> getAllDepartments() {

        return departmentMapper.toResponseList(
                departmentDAO.findAll());
    }

    /**
     * Update Department
     */
    @Override
    public DepartmentResponse updateDepartment(Long departmentId,
                                               DepartmentRequest request) {

        Department department =
                departmentDAO.findById(departmentId);

        if (department == null) {

            throw new ResourceNotFoundException(
                    "Department",
                    departmentId);
        }

        validateDepartmentUpdate(request, departmentId);

        departmentMapper.updateEntity(
                department,
                request);

        Department updatedDepartment =
                departmentDAO.update(department);

        return departmentMapper.toResponse(updatedDepartment);
    }

    /**
     * Delete Department
     */
    @Override
    public void deleteDepartment(Long departmentId) {

        Department department =
                departmentDAO.findById(departmentId);

        if (department == null) {

            throw new ResourceNotFoundException(
                    "Department",
                    departmentId);
        }

        departmentDAO.delete(departmentId);
    }

    /**
     * Validate Create Request
     */
    private void validateDepartment(DepartmentRequest request) {

        if (request == null) {

            throw new ValidationException(
                    "Department request cannot be null.");
        }

        if (departmentDAO.existsByDepartmentCode(
                request.getDepartmentCode())) {

            throw new DuplicateRecordException(
                    "Department",
                    "departmentCode",
                    request.getDepartmentCode());
        }

        if (departmentDAO.existsByDepartmentName(
                request.getDepartmentName())) {

            throw new DuplicateRecordException(
                    "Department",
                    "departmentName",
                    request.getDepartmentName());
        }
    }

    /**
     * Validate Update Request
     */
    private void validateDepartmentUpdate(
            DepartmentRequest request,
            Long departmentId) {

        if (request == null) {

            throw new ValidationException(
                    "Department request cannot be null.");
        }

        Department codeDepartment =
                departmentDAO.findByDepartmentCode(
                        request.getDepartmentCode());

        if (codeDepartment != null &&
                !codeDepartment.getDepartmentId()
                        .equals(departmentId)) {

            throw new DuplicateRecordException(
                    "Department",
                    "departmentCode",
                    request.getDepartmentCode());
        }

        Department nameDepartment =
                departmentDAO.findByDepartmentName(
                        request.getDepartmentName());

        if (nameDepartment != null &&
                !nameDepartment.getDepartmentId()
                        .equals(departmentId)) {

            throw new DuplicateRecordException(
                    "Department",
                    "departmentName",
                    request.getDepartmentName());
        }
    }

}