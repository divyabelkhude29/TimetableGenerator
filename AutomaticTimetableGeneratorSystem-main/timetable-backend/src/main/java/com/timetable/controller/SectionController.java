package com.timetable.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.section.SectionRequest;
import com.timetable.dto.section.SectionResponse;
import com.timetable.service.SectionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/sections")
@Validated
@CrossOrigin(origins = "*")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    /**
     * Create Section
     */
    @PostMapping
    public ResponseEntity<SectionResponse> saveSection(
            @Valid @RequestBody SectionRequest request) {

        SectionResponse response = sectionService.saveSection(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Section
     */
    @PutMapping("/{sectionId}")
    public ResponseEntity<SectionResponse> updateSection(
            @PathVariable Long sectionId,
            @Valid @RequestBody SectionRequest request) {

        SectionResponse response =
                sectionService.updateSection(sectionId, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete Section
     */
    @DeleteMapping("/{sectionId}")
    public ResponseEntity<String> deleteSection(
            @PathVariable Long sectionId) {

        sectionService.deleteSection(sectionId);

        return ResponseEntity.ok("Section deleted successfully.");
    }

    /**
     * Get Section By ID
     */
    @GetMapping("/{sectionId}")
    public ResponseEntity<SectionResponse> getSectionById(
            @PathVariable Long sectionId) {

        return ResponseEntity.ok(
                sectionService.getSectionById(sectionId));
    }

    /**
     * Get All Sections
     */
    @GetMapping
    public ResponseEntity<List<SectionResponse>> getAllSections() {

        return ResponseEntity.ok(
                sectionService.getAllSections());
    }

    /**
     * Get Section By Code
     */
    @GetMapping("/code/{sectionCode}")
    public ResponseEntity<SectionResponse> getSectionByCode(
            @PathVariable String sectionCode) {

        return ResponseEntity.ok(
                sectionService.getSectionByCode(sectionCode));
    }

    /**
     * Get Sections By Course
     */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<SectionResponse>> getSectionsByCourse(
            @PathVariable Long courseId) {

        return ResponseEntity.ok(
                sectionService.getSectionsByCourse(courseId));
    }

    /**
     * Get Sections By Semester
     */
    @GetMapping("/semester/{semesterId}")
    public ResponseEntity<List<SectionResponse>> getSectionsBySemester(
            @PathVariable Long semesterId) {

        return ResponseEntity.ok(
                sectionService.getSectionsBySemester(semesterId));
    }
}