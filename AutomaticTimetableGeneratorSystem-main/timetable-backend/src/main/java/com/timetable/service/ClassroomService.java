package com.timetable.service;

import java.util.List;

import com.timetable.dto.classroom.ClassroomRequest;
import com.timetable.dto.classroom.ClassroomResponse;

public interface ClassroomService {

    /**
     * Save Classroom
     */
    ClassroomResponse saveClassroom(ClassroomRequest request);

    /**
     * Update Classroom
     */
    ClassroomResponse updateClassroom(Long classroomId, ClassroomRequest request);

    /**
     * Delete Classroom
     */
    void deleteClassroom(Long classroomId);

    /**
     * Get Classroom By ID
     */
    ClassroomResponse getClassroomById(Long classroomId);

    /**
     * Get All Classrooms
     */
    List<ClassroomResponse> getAllClassrooms();

    /**
     * Get Classroom By Room Number
     */
    ClassroomResponse getClassroomByRoomNumber(String roomNumber);
}