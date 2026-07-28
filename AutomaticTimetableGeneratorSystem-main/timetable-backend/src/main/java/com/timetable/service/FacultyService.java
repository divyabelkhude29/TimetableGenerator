package com.timetable.service;

import java.util.List;

import com.timetable.dto.faculty.FacultyRequest;
import com.timetable.dto.faculty.FacultyResponse;

public interface FacultyService {

    /**
     * Save Faculty
     */
    FacultyResponse saveFaculty(FacultyRequest request);

    /**
     * Update Faculty
     */
    FacultyResponse updateFaculty(Long facultyId,
                                  FacultyRequest request);

    /**
     * Delete Faculty
     */
    void deleteFaculty(Long facultyId);

    /**
     * Get Faculty By ID
     */
    FacultyResponse getFacultyById(Long facultyId);

    /**
     * Get All Faculties
     */
    List<FacultyResponse> getAllFaculties();

}