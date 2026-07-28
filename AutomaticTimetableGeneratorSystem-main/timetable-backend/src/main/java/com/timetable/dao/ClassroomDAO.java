package com.timetable.dao;

import java.util.List;

import com.timetable.entity.Classroom;

public interface ClassroomDAO {

    /**
     * Save Classroom
     */
    Classroom save(Classroom classroom);

    /**
     * Update Classroom
     */
    Classroom update(Classroom classroom);

    /**
     * Delete Classroom
     */
    void delete(Long classroomId);

    /**
     * Find Classroom By ID
     */
    Classroom findById(Long classroomId);

    /**
     * Find All Classrooms
     */
    List<Classroom> findAll();

    /**
     * Find Classroom By Room Number
     */
    Classroom findByRoomNumber(String roomNumber);

    /**
     * Check Room Number Exists
     */
    boolean existsByRoomNumber(String roomNumber);

}