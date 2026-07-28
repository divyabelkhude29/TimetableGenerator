package com.timetable.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.subject.SubjectRequest;
import com.timetable.dto.subject.SubjectResponse;
import com.timetable.service.SubjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/subjects")
@Validated
@CrossOrigin(origins = "*")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    /**
     * Create Subject
     */
    @PostMapping
    public ResponseEntity<SubjectResponse> saveSubject(
            @Valid @RequestBody SubjectRequest request) {

        SubjectResponse response = subjectService.saveSubject(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Subject
     */
    @PutMapping("/{subjectId}")
    public ResponseEntity<SubjectResponse> updateSubject(
            @PathVariable Long subjectId,
            @Valid @RequestBody SubjectRequest request) {

        SubjectResponse response =
                subjectService.updateSubject(subjectId, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete Subject
     */
    @DeleteMapping("/{subjectId}")
    public ResponseEntity<String> deleteSubject(
            @PathVariable Long subjectId) {

        subjectService.deleteSubject(subjectId);

        return ResponseEntity.ok("Subject deleted successfully.");
    }

    /**
     * Get Subject By ID
     */
    @GetMapping("/{subjectId}")
    public ResponseEntity<SubjectResponse> getSubjectById(
            @PathVariable Long subjectId) {

        return ResponseEntity.ok(
                subjectService.getSubjectById(subjectId));
    }

    /**
     * Get All Subjects
     */
    @GetMapping
    public ResponseEntity<List<SubjectResponse>> getAllSubjects() {

        return ResponseEntity.ok(
                subjectService.getAllSubjects());
    }

    /**
     * Get Subject By Code
     */
    @GetMapping("/code/{subjectCode}")
    public ResponseEntity<SubjectResponse> getSubjectByCode(
            @PathVariable String subjectCode) {

        return ResponseEntity.ok(
                subjectService.getSubjectByCode(subjectCode));
    }

    /**
     * Get Subjects By Department
     */
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<SubjectResponse>> getSubjectsByDepartment(
            @PathVariable Long departmentId) {

        return ResponseEntity.ok(
                subjectService.getSubjectsByDepartment(departmentId));
    }

    /**
     * Get Subjects By Course
     */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<SubjectResponse>> getSubjectsByCourse(
            @PathVariable Long courseId) {

        return ResponseEntity.ok(
                subjectService.getSubjectsByCourse(courseId));
    }

    /**
     * Get Subjects By Semester
     */
    @GetMapping("/semester/{semesterId}")
    public ResponseEntity<List<SubjectResponse>> getSubjectsBySemester(
            @PathVariable Long semesterId) {

        return ResponseEntity.ok(
                subjectService.getSubjectsBySemester(semesterId));
    }

    /**
     * Get Subjects By Faculty
     */
    @GetMapping("/faculty/{facultyId}")
    public ResponseEntity<List<SubjectResponse>> getSubjectsByFaculty(
            @PathVariable Long facultyId) {

        return ResponseEntity.ok(
                subjectService.getSubjectsByFaculty(facultyId));
    }

}