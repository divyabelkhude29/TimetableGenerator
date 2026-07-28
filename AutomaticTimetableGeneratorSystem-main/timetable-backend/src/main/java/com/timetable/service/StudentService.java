package com.timetable.service;

import java.util.List;

import com.timetable.dto.student.StudentRequest;
import com.timetable.dto.student.StudentResponse;

public interface StudentService {

    /**
     * Create Student
     */
    StudentResponse saveStudent(StudentRequest request);

    /**
     * Update Student
     */
    StudentResponse updateStudent(Long studentId, StudentRequest request);

    /**
     * Delete Student
     */
    void deleteStudent(Long studentId);

    /**
     * Get Student By ID
     */
    StudentResponse getStudentById(Long studentId);

    /**
     * Get All Students
     */
    List<StudentResponse> getAllStudents();

    /**
     * Get Student By Roll Number
     */
    StudentResponse getStudentByRollNumber(String rollNumber);

    /**
     * Get Students By Department
     */
    List<StudentResponse> getStudentsByDepartment(Long departmentId);

    /**
     * Get Students By Course
     */
    List<StudentResponse> getStudentsByCourse(Long courseId);

    /**
     * Get Students By Semester
     */
    List<StudentResponse> getStudentsBySemester(Long semesterId);
}