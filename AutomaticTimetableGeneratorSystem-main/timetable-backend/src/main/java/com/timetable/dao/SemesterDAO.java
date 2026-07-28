package com.timetable.dao;

import java.util.List;

import com.timetable.entity.Course;
import com.timetable.entity.Semester;

public interface SemesterDAO {

    /**
     * Save Semester
     */
    Semester save(Semester semester);

    /**
     * Update Semester
     */
    Semester update(Semester semester);

    /**
     * Delete Semester
     */
    void delete(Long semesterId);

    /**
     * Find Semester By ID
     */
    Semester findById(Long semesterId);

    /**
     * Find All Semesters
     */
    List<Semester> findAll();

    /**
     * Find Semester by Semester Number and Course
     */
    Semester findBySemesterNumberAndCourse(Integer semesterNumber, Course course);

    /**
     * Find All Semesters of a Course
     */
    List<Semester> findByCourse(Course course);

}