package com.timetable.dao;

import java.util.List;

import com.timetable.entity.Course;

public interface CourseDAO {

    /**
     * Save Course
     */
    Course save(Course course);

    /**
     * Update Course
     */
    Course update(Course course);

    /**
     * Delete Course
     */
    void delete(Long courseId);

    /**
     * Find Course By ID
     */
    Course findById(Long courseId);

    /**
     * Find All Courses
     */
    List<Course> findAll();

    /**
     * Find Course By Code
     */
    Course findByCourseCode(String courseCode);

    /**
     * Find Courses By Department
     */
    List<Course> findByDepartmentId(Long departmentId);

    /**
     * Check Course Code Exists
     */
    boolean existsByCourseCode(String courseCode);

	/**
	 * Delete Course
	 */
	void delete(Course course);
}