package com.timetable.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.timetable.dto.department.DepartmentRequest;
import com.timetable.dto.department.DepartmentResponse;
import com.timetable.entity.Department;

@Component
public class DepartmentMapper {

    /**
     * Convert DepartmentRequest to Department Entity
     */
    public Department toEntity(DepartmentRequest request) {

        if (request == null) {
            return null;
        }

        Department department = new Department();

        department.setDepartmentCode(request.getDepartmentCode());
        department.setDepartmentName(request.getDepartmentName());
        department.setHodName(request.getHodName());
        department.setDescription(request.getDescription());
        department.setActive(request.getActive());

        return department;
    }

    /**
     * Convert Department Entity to DepartmentResponse
     */
    public DepartmentResponse toResponse(Department department) {

        if (department == null) {
            return null;
        }

        DepartmentResponse response = new DepartmentResponse();

        response.setDepartmentId(department.getDepartmentId());
        response.setDepartmentCode(department.getDepartmentCode());
        response.setDepartmentName(department.getDepartmentName());
        response.setHodName(department.getHodName());
        response.setDescription(department.getDescription());
        response.setActive(department.getActive());
        response.setCreatedAt(department.getCreatedAt());
        response.setUpdatedAt(department.getUpdatedAt());

        return response;
    }

    /**
     * Convert List<Department> to List<DepartmentResponse>
     */
    public List<DepartmentResponse> toResponseList(List<Department> departments) {

        return departments.stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Update Existing Department Entity
     */
    public void updateEntity(Department department,
                             DepartmentRequest request) {

        department.setDepartmentCode(request.getDepartmentCode());
        department.setDepartmentName(request.getDepartmentName());
        department.setHodName(request.getHodName());
        department.setDescription(request.getDescription());

        if (request.getActive() != null) {
            department.setActive(request.getActive());
        }
    }

}