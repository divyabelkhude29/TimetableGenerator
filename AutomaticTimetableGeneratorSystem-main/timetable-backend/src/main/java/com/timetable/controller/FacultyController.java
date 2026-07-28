package com.timetable.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.faculty.FacultyRequest;
import com.timetable.dto.faculty.FacultyResponse;
import com.timetable.service.FacultyService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/faculties")
@Validated
@CrossOrigin(origins = "*")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    /**
     * Create Faculty
     */
    @PostMapping
    public ResponseEntity<FacultyResponse> createFaculty(
            @Valid @RequestBody FacultyRequest request) {

        FacultyResponse response = facultyService.saveFaculty(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Faculty
     */
    @PutMapping("/{facultyId}")
    public ResponseEntity<FacultyResponse> updateFaculty(
            @PathVariable Long facultyId,
            @Valid @RequestBody FacultyRequest request) {

        FacultyResponse response =
                facultyService.updateFaculty(facultyId, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete Faculty
     */
    @DeleteMapping("/{facultyId}")
    public ResponseEntity<String> deleteFaculty(
            @PathVariable Long facultyId) {

        facultyService.deleteFaculty(facultyId);

        return ResponseEntity.ok("Faculty deleted successfully.");
    }

    /**
     * Get Faculty By ID
     */
    @GetMapping("/{facultyId}")
    public ResponseEntity<FacultyResponse> getFacultyById(
            @PathVariable Long facultyId) {

        FacultyResponse response =
                facultyService.getFacultyById(facultyId);

        return ResponseEntity.ok(response);
    }

    /**
     * Get All Faculties
     */
    @GetMapping
    public ResponseEntity<List<FacultyResponse>> getAllFaculties() {

        List<FacultyResponse> response =
                facultyService.getAllFaculties();

        return ResponseEntity.ok(response);
    }
}