package com.timetable.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.timetablegeneration.GenerateTimetableRequest;
import com.timetable.dto.timetablegeneration.TimetableGenerationResponse;
import com.timetable.dto.timetablegeneration.TimetableSlotDTO;
import com.timetable.service.TimetableGenerationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/timetable-generation")
@Validated
@CrossOrigin(origins = "*")
public class TimetableGenerationController {

    private final TimetableGenerationService timetableGenerationService;

    public TimetableGenerationController(
            TimetableGenerationService timetableGenerationService) {

        this.timetableGenerationService = timetableGenerationService;
    }

    /**
     * Generate Timetable
     */
    @PostMapping("/generate")
    public ResponseEntity<TimetableGenerationResponse> generateTimetable(
            @Valid @RequestBody GenerateTimetableRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(timetableGenerationService.generateTimetable(request));
    }

    /**
     * Preview Timetable
     */
    @PostMapping("/preview")
    public ResponseEntity<List<TimetableSlotDTO>> previewTimetable(
            @Valid @RequestBody GenerateTimetableRequest request) {

        return ResponseEntity.ok(
                timetableGenerationService.previewTimetable(request));
    }

    /**
     * Validate Timetable Generation
     */
    @PostMapping("/validate")
    public ResponseEntity<List<String>> validateGeneration(
            @Valid @RequestBody GenerateTimetableRequest request) {

        return ResponseEntity.ok(
                timetableGenerationService.validateGeneration(request));
    }

    /**
     * Regenerate Timetable
     */
    @PostMapping("/regenerate")
    public ResponseEntity<TimetableGenerationResponse> regenerateTimetable(
            @Valid @RequestBody GenerateTimetableRequest request) {

        return ResponseEntity.ok(
                timetableGenerationService.regenerateTimetable(request));
    }

    /**
     * Delete Generated Timetable
     */
    @DeleteMapping
    public ResponseEntity<String> deleteGeneratedTimetable(
            @Valid @RequestBody GenerateTimetableRequest request) {

        timetableGenerationService.deleteGeneratedTimetable(request);

        return ResponseEntity.ok(
                "Generated timetable deleted successfully.");
    }

}