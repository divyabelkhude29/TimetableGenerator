package com.timetable.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.department.DepartmentRequest;
import com.timetable.dto.department.DepartmentResponse;
import com.timetable.service.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/departments")
@Validated
@CrossOrigin(origins = "*")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * Create Department
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartmentResponse> createDepartment(
            @Valid @RequestBody DepartmentRequest request) {

        DepartmentResponse response =
                departmentService.createDepartment(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Get Department By Id
     */
    @GetMapping("/{departmentId}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<DepartmentResponse> getDepartmentById(
            @PathVariable Long departmentId) {

        return ResponseEntity.ok(
                departmentService.getDepartmentById(departmentId));
    }

    /**
     * Get Department By Code
     */
    @GetMapping("/code/{departmentCode}")
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<DepartmentResponse> getDepartmentByCode(
            @PathVariable String departmentCode) {

        return ResponseEntity.ok(
                departmentService.getDepartmentByCode(departmentCode));
    }

    /**
     * Get All Departments
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','FACULTY')")
    public ResponseEntity<List<DepartmentResponse>> getAllDepartments() {

        return ResponseEntity.ok(
                departmentService.getAllDepartments());
    }

    /**
     * Update Department
     */
    @PutMapping("/{departmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DepartmentResponse> updateDepartment(
            @PathVariable Long departmentId,
            @Valid @RequestBody DepartmentRequest request) {

        return ResponseEntity.ok(
                departmentService.updateDepartment(
                        departmentId,
                        request));
    }

    /**
     * Delete Department
     */
    @DeleteMapping("/{departmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteDepartment(
            @PathVariable Long departmentId) {

        departmentService.deleteDepartment(departmentId);

        return ResponseEntity.ok(
                "Department deleted successfully.");
    }

}