package com.timetable.dao;

import java.util.List;

import com.timetable.entity.Department;

public interface DepartmentDAO {

    Department save(Department department);

    Department update(Department department);

    void delete(Long departmentId);

    Department findById(Long departmentId);

    List<Department> findAll();

    Department findByDepartmentCode(String departmentCode);

    Department findByDepartmentName(String departmentName);

    boolean existsByDepartmentCode(String departmentCode);

    boolean existsByDepartmentName(String departmentName);

}