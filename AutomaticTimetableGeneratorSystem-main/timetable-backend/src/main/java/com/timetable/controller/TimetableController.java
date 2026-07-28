package com.timetable.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.timetable.TimetableRequest;
import com.timetable.dto.timetable.TimetableResponse;
import com.timetable.service.TimetableService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/timetables")
@Validated
@CrossOrigin(origins = "*")
public class TimetableController {

    private final TimetableService timetableService;

    public TimetableController(TimetableService timetableService) {
        this.timetableService = timetableService;
    }

    /**
     * Create Timetable
     */
    @PostMapping
    public ResponseEntity<TimetableResponse> saveTimetable(
            @Valid @RequestBody TimetableRequest request) {

        TimetableResponse response =
                timetableService.saveTimetable(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Timetable
     */
    @PutMapping("/{timetableId}")
    public ResponseEntity<TimetableResponse> updateTimetable(
            @PathVariable Long timetableId,
            @Valid @RequestBody TimetableRequest request) {

        TimetableResponse response =
                timetableService.updateTimetable(
                        timetableId,
                        request);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete Timetable
     */
    @DeleteMapping("/{timetableId}")
    public ResponseEntity<String> deleteTimetable(
            @PathVariable Long timetableId) {

        timetableService.deleteTimetable(timetableId);

        return ResponseEntity.ok(
                "Timetable deleted successfully.");
    }

    /**
     * Get Timetable By ID
     */
    @GetMapping("/{timetableId}")
    public ResponseEntity<TimetableResponse> getTimetableById(
            @PathVariable Long timetableId) {

        return ResponseEntity.ok(
                timetableService.getTimetableById(timetableId));
    }

    /**
     * Get All Timetables
     */
    @GetMapping
    public ResponseEntity<List<TimetableResponse>> getAllTimetables() {

        return ResponseEntity.ok(
                timetableService.getAllTimetables());
    }

    /**
     * Get Timetables By Day
     */
    @GetMapping("/day/{dayOfWeek}")
    public ResponseEntity<List<TimetableResponse>> getTimetablesByDay(
            @PathVariable String dayOfWeek) {

        return ResponseEntity.ok(
                timetableService.getTimetablesByDay(dayOfWeek));
    }

    /**
     * Get Timetables By Classroom
     */
    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<TimetableResponse>> getTimetablesByClassroom(
            @PathVariable Long classroomId) {

        return ResponseEntity.ok(
                timetableService.getTimetablesByClassroom(classroomId));
    }

    /**
     * Get Timetables By Faculty
     */
    @GetMapping("/faculty/{facultyId}")
    public ResponseEntity<List<TimetableResponse>> getTimetablesByFaculty(
            @PathVariable Long facultyId) {

        return ResponseEntity.ok(
                timetableService.getTimetablesByFaculty(facultyId));
    }

    /**
     * Get Timetables By Section
     */
    @GetMapping("/section/{sectionId}")
    public ResponseEntity<List<TimetableResponse>> getTimetablesBySection(
            @PathVariable Long sectionId) {

        return ResponseEntity.ok(
                timetableService.getTimetablesBySection(sectionId));
    }

    /**
     * Get Timetables By Allocation
     */
    @GetMapping("/allocation/{allocationId}")
    public ResponseEntity<List<TimetableResponse>> getTimetablesByAllocation(
            @PathVariable Long allocationId) {

        return ResponseEntity.ok(
                timetableService.getTimetablesByAllocation(allocationId));
    }
}