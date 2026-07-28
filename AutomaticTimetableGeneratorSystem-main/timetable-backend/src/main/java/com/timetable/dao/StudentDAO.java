package com.timetable.dao;

import java.util.List;

import com.timetable.entity.Student;

public interface StudentDAO {

    /**
     * Save Student
     */
    Student save(Student student);

    /**
     * Update Student
     */
    Student update(Student student);

    /**
     * Delete Student
     */
    void delete(Long studentId);

    /**
     * Find Student By ID
     */
    Student findById(Long studentId);

    /**
     * Find Student By Roll Number
     */
    Student findByRollNumber(String rollNumber);

    /**
     * Find Student By Register Number
     */
    Student findByRegisterNumber(String registerNumber);

    /**
     * Find Student By Email
     */
    Student findByEmail(String email);

    /**
     * Get All Students
     */
    List<Student> findAll();

    /**
     * Check Student Exists
     */
    boolean existsById(Long studentId);
}