package com.timetable.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.classroom.ClassroomRequest;
import com.timetable.dto.classroom.ClassroomResponse;
import com.timetable.service.ClassroomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/classrooms")
@Validated
@CrossOrigin(origins = "*")
public class ClassroomController {

    private final ClassroomService classroomService;

    public ClassroomController(ClassroomService classroomService) {
        this.classroomService = classroomService;
    }

    /**
     * Create Classroom
     */
    @PostMapping
    public ResponseEntity<ClassroomResponse> saveClassroom(
            @Valid @RequestBody ClassroomRequest request) {

        ClassroomResponse response = classroomService.saveClassroom(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Classroom
     */
    @PutMapping("/{classroomId}")
    public ResponseEntity<ClassroomResponse> updateClassroom(
            @PathVariable Long classroomId,
            @Valid @RequestBody ClassroomRequest request) {

        ClassroomResponse response =
                classroomService.updateClassroom(classroomId, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete Classroom
     */
    @DeleteMapping("/{classroomId}")
    public ResponseEntity<String> deleteClassroom(
            @PathVariable Long classroomId) {

        classroomService.deleteClassroom(classroomId);

        return ResponseEntity.ok("Classroom deleted successfully.");
    }

    /**
     * Get Classroom By ID
     */
    @GetMapping("/{classroomId}")
    public ResponseEntity<ClassroomResponse> getClassroomById(
            @PathVariable Long classroomId) {

        return ResponseEntity.ok(
                classroomService.getClassroomById(classroomId));
    }

    /**
     * Get All Classrooms
     */
    @GetMapping
    public ResponseEntity<List<ClassroomResponse>> getAllClassrooms() {

        return ResponseEntity.ok(
                classroomService.getAllClassrooms());
    }

    /**
     * Get Classroom By Room Number
     */
    @GetMapping("/room/{roomNumber}")
    public ResponseEntity<ClassroomResponse> getClassroomByRoomNumber(
            @PathVariable String roomNumber) {

        return ResponseEntity.ok(
                classroomService.getClassroomByRoomNumber(roomNumber));
    }
}