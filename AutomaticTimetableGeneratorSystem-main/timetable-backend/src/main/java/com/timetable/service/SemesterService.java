package com.timetable.service;

import java.util.List;

import com.timetable.dto.semester.SemesterRequest;
import com.timetable.dto.semester.SemesterResponse;

public interface SemesterService {

    /**
     * Save Semester
     */
    SemesterResponse saveSemester(SemesterRequest request);

    /**
     * Update Semester
     */
    SemesterResponse updateSemester(Long semesterId,
                                    SemesterRequest request);

    /**
     * Delete Semester
     */
    void deleteSemester(Long semesterId);

    /**
     * Get Semester By ID
     */
    SemesterResponse getSemesterById(Long semesterId);

    /**
     * Get All Semesters
     */
    List<SemesterResponse> getAllSemesters();

}