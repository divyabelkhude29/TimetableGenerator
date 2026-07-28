package com.timetable.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.student.StudentRequest;
import com.timetable.dto.student.StudentResponse;
import com.timetable.service.StudentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/students")
@Validated
@CrossOrigin(origins = "*")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /**
     * Create Student
     */
    @PostMapping
    public ResponseEntity<StudentResponse> saveStudent(
            @Valid @RequestBody StudentRequest request) {

        StudentResponse response = studentService.saveStudent(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Student
     */
    @PutMapping("/{studentId}")
    public ResponseEntity<StudentResponse> updateStudent(
            @PathVariable Long studentId,
            @Valid @RequestBody StudentRequest request) {

        StudentResponse response =
                studentService.updateStudent(studentId, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete Student
     */
    @DeleteMapping("/{studentId}")
    public ResponseEntity<String> deleteStudent(
            @PathVariable Long studentId) {

        studentService.deleteStudent(studentId);

        return ResponseEntity.ok("Student deleted successfully.");
    }

    /**
     * Get Student By ID
     */
    @GetMapping("/{studentId}")
    public ResponseEntity<StudentResponse> getStudentById(
            @PathVariable Long studentId) {

        StudentResponse response =
                studentService.getStudentById(studentId);

        return ResponseEntity.ok(response);
    }

    /**
     * Get All Students
     */
    @GetMapping
    public ResponseEntity<List<StudentResponse>> getAllStudents() {

        return ResponseEntity.ok(
                studentService.getAllStudents());
    }

    /**
     * Get Student By Roll Number
     */
    @GetMapping("/roll/{rollNumber}")
    public ResponseEntity<StudentResponse> getStudentByRollNumber(
            @PathVariable String rollNumber) {

        return ResponseEntity.ok(
                studentService.getStudentByRollNumber(rollNumber));
    }

    /**
     * Get Students By Department
     */
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<StudentResponse>> getStudentsByDepartment(
            @PathVariable Long departmentId) {

        return ResponseEntity.ok(
                studentService.getStudentsByDepartment(departmentId));
    }

    /**
     * Get Students By Course
     */
    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<StudentResponse>> getStudentsByCourse(
            @PathVariable Long courseId) {

        return ResponseEntity.ok(
                studentService.getStudentsByCourse(courseId));
    }

    /**
     * Get Students By Semester
     */
    @GetMapping("/semester/{semesterId}")
    public ResponseEntity<List<StudentResponse>> getStudentsBySemester(
            @PathVariable Long semesterId) {

        return ResponseEntity.ok(
                studentService.getStudentsBySemester(semesterId));
    }
}