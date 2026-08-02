package com.timetable.service;

import java.util.List;

import com.timetable.dto.department.DepartmentRequest;
import com.timetable.dto.department.DepartmentResponse;

public interface DepartmentService {

    DepartmentResponse createDepartment(DepartmentRequest request);

    DepartmentResponse getDepartmentById(Long departmentId);

    DepartmentResponse getDepartmentByCode(String departmentCode);

    List<DepartmentResponse> getAllDepartments();

    DepartmentResponse updateDepartment(Long departmentId,
                                        DepartmentRequest request);

    void deleteDepartment(Long departmentId);

}