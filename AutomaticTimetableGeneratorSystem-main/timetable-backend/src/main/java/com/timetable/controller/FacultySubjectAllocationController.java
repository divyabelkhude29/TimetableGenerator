package com.timetable.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.allocation.FacultySubjectAllocationRequest;
import com.timetable.dto.allocation.FacultySubjectAllocationResponse;
import com.timetable.service.FacultySubjectAllocationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/allocations")
@Validated
@CrossOrigin(origins = "*")
public class FacultySubjectAllocationController {

    private final FacultySubjectAllocationService allocationService;

    public FacultySubjectAllocationController(
            FacultySubjectAllocationService allocationService) {
        this.allocationService = allocationService;
    }

    /**
     * Create Allocation
     */
    @PostMapping
    public ResponseEntity<FacultySubjectAllocationResponse> saveAllocation(
            @Valid @RequestBody FacultySubjectAllocationRequest request) {

        FacultySubjectAllocationResponse response =
                allocationService.saveAllocation(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Allocation
     */
    @PutMapping("/{allocationId}")
    public ResponseEntity<FacultySubjectAllocationResponse> updateAllocation(
            @PathVariable Long allocationId,
            @Valid @RequestBody FacultySubjectAllocationRequest request) {

        FacultySubjectAllocationResponse response =
                allocationService.updateAllocation(allocationId, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete Allocation
     */
    @DeleteMapping("/{allocationId}")
    public ResponseEntity<String> deleteAllocation(
            @PathVariable Long allocationId) {

        allocationService.deleteAllocation(allocationId);

        return ResponseEntity.ok("Faculty Subject Allocation deleted successfully.");
    }

    /**
     * Get Allocation By ID
     */
    @GetMapping("/{allocationId}")
    public ResponseEntity<FacultySubjectAllocationResponse> getAllocationById(
            @PathVariable Long allocationId) {

        return ResponseEntity.ok(
                allocationService.getAllocationById(allocationId));
    }

    /**
     * Get All Allocations
     */
    @GetMapping
    public ResponseEntity<List<FacultySubjectAllocationResponse>> getAllAllocations() {

        return ResponseEntity.ok(
                allocationService.getAllAllocations());
    }

    /**
     * Get Allocations By Faculty
     */
    @GetMapping("/faculty/{facultyId}")
    public ResponseEntity<List<FacultySubjectAllocationResponse>> getAllocationsByFaculty(
            @PathVariable Long facultyId) {

        return ResponseEntity.ok(
                allocationService.getAllocationsByFaculty(facultyId));
    }

    /**
     * Get Allocations By Subject
     */
    @GetMapping("/subject/{subjectId}")
    public ResponseEntity<List<FacultySubjectAllocationResponse>> getAllocationsBySubject(
            @PathVariable Long subjectId) {

        return ResponseEntity.ok(
                allocationService.getAllocationsBySubject(subjectId));
    }

    /**
     * Get Allocations By Section
     */
    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<FacultySubjectAllocationResponse>> getAllocationsBySection(
            @PathVariable Long sectionId) {

        return ResponseEntity.ok(
                allocationService.getAllocationsBySection(sectionId));
    }

    /**
     * Get Allocations By Semester
     */
    @GetMapping("/semester/{semesterId}")
    public ResponseEntity<List<FacultySubjectAllocationResponse>> getAllocationsBySemester(
            @PathVariable Long semesterId) {

        return ResponseEntity.ok(
                allocationService.getAllocationsBySemester(semesterId));
    }
}