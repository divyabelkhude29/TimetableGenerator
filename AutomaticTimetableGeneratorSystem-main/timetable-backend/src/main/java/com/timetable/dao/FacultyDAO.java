package com.timetable.dao;

import java.util.List;

import com.timetable.entity.Faculty;

public interface FacultyDAO {

    /**
     * Save Faculty
     */
    Faculty save(Faculty faculty);

    /**
     * Update Faculty
     */
    Faculty update(Faculty faculty);

    /**
     * Delete Faculty
     */
    void delete(Long facultyId);

    /**
     * Find Faculty By ID
     */
    Faculty findById(Long facultyId);

    /**
     * Find All Faculties
     */
    List<Faculty> findAll();

    /**
     * Find Faculty By Faculty Code
     */
    Faculty findByFacultyCode(String facultyCode);

    /**
     * Find Faculty By Email
     */
    Faculty findByEmail(String email);

}