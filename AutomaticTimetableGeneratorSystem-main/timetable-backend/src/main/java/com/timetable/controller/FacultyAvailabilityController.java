package com.timetable.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.facultyavailability.FacultyAvailabilityRequest;
import com.timetable.dto.facultyavailability.FacultyAvailabilityResponse;
import com.timetable.service.FacultyAvailabilityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/faculty-availability")
@Validated
@CrossOrigin(origins = "*")
public class FacultyAvailabilityController {

    private final FacultyAvailabilityService facultyAvailabilityService;

    public FacultyAvailabilityController(
            FacultyAvailabilityService facultyAvailabilityService) {

        this.facultyAvailabilityService = facultyAvailabilityService;
    }

    /**
     * Create Faculty Availability
     */
    @PostMapping
    public ResponseEntity<FacultyAvailabilityResponse> saveFacultyAvailability(
            @Valid @RequestBody FacultyAvailabilityRequest request) {

        FacultyAvailabilityResponse response =
                facultyAvailabilityService.saveFacultyAvailability(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Faculty Availability
     */
    @PutMapping("/{availabilityId}")
    public ResponseEntity<FacultyAvailabilityResponse> updateFacultyAvailability(
            @PathVariable Long availabilityId,
            @Valid @RequestBody FacultyAvailabilityRequest request) {

        FacultyAvailabilityResponse response =
                facultyAvailabilityService.updateFacultyAvailability(
                        availabilityId,
                        request);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete Faculty Availability
     */
    @DeleteMapping("/{availabilityId}")
    public ResponseEntity<String> deleteFacultyAvailability(
            @PathVariable Long availabilityId) {

        facultyAvailabilityService.deleteFacultyAvailability(availabilityId);

        return ResponseEntity.ok(
                "Faculty Availability deleted successfully.");
    }

    /**
     * Get Faculty Availability By ID
     */
    @GetMapping("/{availabilityId}")
    public ResponseEntity<FacultyAvailabilityResponse> getFacultyAvailabilityById(
            @PathVariable Long availabilityId) {

        return ResponseEntity.ok(
                facultyAvailabilityService.getFacultyAvailabilityById(
                        availabilityId));
    }

    /**
     * Get All Faculty Availability
     */
    @GetMapping
    public ResponseEntity<List<FacultyAvailabilityResponse>> getAllFacultyAvailability() {

        return ResponseEntity.ok(
                facultyAvailabilityService.getAllFacultyAvailability());
    }

    /**
     * Get Faculty Availability By Faculty
     */
    @GetMapping("/faculty/{facultyId}")
    public ResponseEntity<List<FacultyAvailabilityResponse>> getFacultyAvailabilityByFaculty(
            @PathVariable Long facultyId) {

        return ResponseEntity.ok(
                facultyAvailabilityService.getFacultyAvailabilityByFaculty(
                        facultyId));
    }

    /**
     * Get Faculty Availability By Day
     */
    @GetMapping("/day/{dayOfWeek}")
    public ResponseEntity<List<FacultyAvailabilityResponse>> getFacultyAvailabilityByDay(
            @PathVariable String dayOfWeek) {

        return ResponseEntity.ok(
                facultyAvailabilityService.getFacultyAvailabilityByDay(
                        dayOfWeek));
    }

    /**
     * Get Faculty Availability By Faculty And Day
     */
    @GetMapping("/faculty/{facultyId}/day/{dayOfWeek}")
    public ResponseEntity<List<FacultyAvailabilityResponse>> getFacultyAvailabilityByFacultyAndDay(
            @PathVariable Long facultyId,
            @PathVariable String dayOfWeek) {

        return ResponseEntity.ok(
                facultyAvailabilityService
                        .getFacultyAvailabilityByFacultyAndDay(
                                facultyId,
                                dayOfWeek));
    }
}