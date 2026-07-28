package com.timetable.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.course.CourseRequest;
import com.timetable.dto.course.CourseResponse;
import com.timetable.service.CourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "*")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /**
     * Create Course
     */
    @PostMapping
    public ResponseEntity<CourseResponse> saveCourse(
            @Valid @RequestBody CourseRequest request) {

        CourseResponse response = courseService.saveCourse(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Update Course
     */
    @PutMapping("/{courseId}")
    public ResponseEntity<CourseResponse> updateCourse(
            @PathVariable Long courseId,
            @Valid @RequestBody CourseRequest request) {

        CourseResponse response =
                courseService.updateCourse(courseId, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete Course
     */
    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(
            @PathVariable Long courseId) {

        courseService.deleteCourse(courseId);

        return ResponseEntity.ok("Course deleted successfully.");
    }

    /**
     * Get Course By ID
     */
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponse> getCourseById(
            @PathVariable Long courseId) {

        CourseResponse response =
                courseService.getCourseById(courseId);

        return ResponseEntity.ok(response);
    }

    /**
     * Get All Courses
     */
    @GetMapping
    public ResponseEntity<List<CourseResponse>> getAllCourses() {

        List<CourseResponse> response =
                courseService.getAllCourses();

        return ResponseEntity.ok(response);
    }

}