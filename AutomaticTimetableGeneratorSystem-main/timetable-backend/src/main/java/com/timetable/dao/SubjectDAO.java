package com.timetable.dao;

import java.util.List;

import com.timetable.entity.Subject;

public interface SubjectDAO {

    /**
     * Save Subject
     */
    Subject save(Subject subject);

    /**
     * Update Subject
     */
    Subject update(Subject subject);

    /**
     * Delete Subject
     */
    void delete(Long subjectId);

    /**
     * Find Subject By ID
     */
    Subject findById(Long subjectId);

    /**
     * Find All Subjects
     */
    List<Subject> findAll();

    /**
     * Find Subject By Subject Code
     */
    Subject findBySubjectCode(String subjectCode);

    /**
     * Find Subjects By Department
     */
    List<Subject> findByDepartmentId(Long departmentId);

    /**
     * Find Subjects By Course
     */
    List<Subject> findByCourseId(Long courseId);

    /**
     * Find Subjects By Semester
     */
    List<Subject> findBySemesterId(Long semesterId);

    /**
     * Find Subjects By Faculty
     */
    List<Subject> findByFacultyId(Long facultyId);

    /**
     * Check Subject Code Exists
     */
    boolean existsBySubjectCode(String subjectCode);
}