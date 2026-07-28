package com.timetable.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.subjectworkload.SubjectWorkloadRequest;
import com.timetable.dto.subjectworkload.SubjectWorkloadResponse;
import com.timetable.service.SubjectWorkloadService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/subject-workloads")
@Validated
@CrossOrigin(origins = "*")
public class SubjectWorkloadController {

    private final SubjectWorkloadService subjectWorkloadService;

    public SubjectWorkloadController(
            SubjectWorkloadService subjectWorkloadService) {

        this.subjectWorkloadService = subjectWorkloadService;
    }

    /**
     * Create Subject Workload
     */
    @PostMapping
    public ResponseEntity<SubjectWorkloadResponse> saveSubjectWorkload(
            @Valid @RequestBody SubjectWorkloadRequest request) {

        SubjectWorkloadResponse response =
                subjectWorkloadService.saveSubjectWorkload(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Subject Workload
     */
    @PutMapping("/{workloadId}")
    public ResponseEntity<SubjectWorkloadResponse> updateSubjectWorkload(
            @PathVariable Long workloadId,
            @Valid @RequestBody SubjectWorkloadRequest request) {

        SubjectWorkloadResponse response =
                subjectWorkloadService.updateSubjectWorkload(
                        workloadId,
                        request);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete Subject Workload
     */
    @DeleteMapping("/{workloadId}")
    public ResponseEntity<String> deleteSubjectWorkload(
            @PathVariable Long workloadId) {

        subjectWorkloadService.deleteSubjectWorkload(workloadId);

        return ResponseEntity.ok(
                "Subject Workload deleted successfully.");
    }

    /**
     * Get Subject Workload By ID
     */
    @GetMapping("/{workloadId}")
    public ResponseEntity<SubjectWorkloadResponse> getSubjectWorkloadById(
            @PathVariable Long workloadId) {

        return ResponseEntity.ok(
                subjectWorkloadService.getSubjectWorkloadById(
                        workloadId));
    }

    /**
     * Get All Subject Workloads
     */
    @GetMapping
    public ResponseEntity<List<SubjectWorkloadResponse>> getAllSubjectWorkloads() {

        return ResponseEntity.ok(
                subjectWorkloadService.getAllSubjectWorkloads());
    }

    /**
     * Get Subject Workload By Allocation
     */
    @GetMapping("/allocation/{allocationId}")
    public ResponseEntity<SubjectWorkloadResponse> getSubjectWorkloadByAllocation(
            @PathVariable Long allocationId) {

        return ResponseEntity.ok(
                subjectWorkloadService.getSubjectWorkloadByAllocation(
                        allocationId));
    }
}