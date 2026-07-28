package com.timetable.service;

import java.util.List;

import com.timetable.dto.subject.SubjectRequest;
import com.timetable.dto.subject.SubjectResponse;

public interface SubjectService {

    /**
     * Save Subject
     */
    SubjectResponse saveSubject(SubjectRequest request);

    /**
     * Update Subject
     */
    SubjectResponse updateSubject(Long subjectId, SubjectRequest request);

    /**
     * Delete Subject
     */
    void deleteSubject(Long subjectId);

    /**
     * Get Subject By ID
     */
    SubjectResponse getSubjectById(Long subjectId);

    /**
     * Get All Subjects
     */
    List<SubjectResponse> getAllSubjects();

    /**
     * Get Subject By Subject Code
     */
    SubjectResponse getSubjectByCode(String subjectCode);

    /**
     * Get Subjects By Department
     */
    List<SubjectResponse> getSubjectsByDepartment(Long departmentId);

    /**
     * Get Subjects By Course
     */
    List<SubjectResponse> getSubjectsByCourse(Long courseId);

    /**
     * Get Subjects By Semester
     */
    List<SubjectResponse> getSubjectsBySemester(Long semesterId);

    /**
     * Get Subjects By Faculty
     */
    List<SubjectResponse> getSubjectsByFaculty(Long facultyId);
}