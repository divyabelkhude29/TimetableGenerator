package com.timetable.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.semester.SemesterRequest;
import com.timetable.dto.semester.SemesterResponse;
import com.timetable.service.SemesterService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/semesters")
@Validated
@CrossOrigin(origins = "*")
public class SemesterController {

    private final SemesterService semesterService;

    public SemesterController(SemesterService semesterService) {
        this.semesterService = semesterService;
    }

    /**
     * Create Semester
     */
    @PostMapping
    public ResponseEntity<SemesterResponse> createSemester(
            @Valid @RequestBody SemesterRequest request) {

        SemesterResponse response = semesterService.saveSemester(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Get Semester By ID
     */
    @GetMapping("/{semesterId}")
    public ResponseEntity<SemesterResponse> getSemesterById(
            @PathVariable Long semesterId) {

        SemesterResponse response =
                semesterService.getSemesterById(semesterId);

        return ResponseEntity.ok(response);
    }

    /**
     * Get All Semesters
     */
    @GetMapping
    public ResponseEntity<List<SemesterResponse>> getAllSemesters() {

        List<SemesterResponse> response =
                semesterService.getAllSemesters();

        return ResponseEntity.ok(response);
    }

    /**
     * Update Semester
     */
    @PutMapping("/{semesterId}")
    public ResponseEntity<SemesterResponse> updateSemester(
            @PathVariable Long semesterId,
            @Valid @RequestBody SemesterRequest request) {

        SemesterResponse response =
                semesterService.updateSemester(semesterId, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete Semester
     */
    @DeleteMapping("/{semesterId}")
    public ResponseEntity<String> deleteSemester(
            @PathVariable Long semesterId) {

        semesterService.deleteSemester(semesterId);

        return ResponseEntity.ok("Semester deleted successfully.");
    }

}