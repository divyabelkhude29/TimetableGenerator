package com.timetable.service;

import java.util.List;

import com.timetable.dto.facultyavailability.FacultyAvailabilityRequest;
import com.timetable.dto.facultyavailability.FacultyAvailabilityResponse;

public interface FacultyAvailabilityService {

    /**
     * Save Faculty Availability
     */
    FacultyAvailabilityResponse saveFacultyAvailability(
            FacultyAvailabilityRequest request);

    /**
     * Update Faculty Availability
     */
    FacultyAvailabilityResponse updateFacultyAvailability(
            Long availabilityId,
            FacultyAvailabilityRequest request);

    /**
     * Delete Faculty Availability
     */
    void deleteFacultyAvailability(Long availabilityId);

    /**
     * Get Faculty Availability By ID
     */
    FacultyAvailabilityResponse getFacultyAvailabilityById(
            Long availabilityId);

    /**
     * Get All Faculty Availability
     */
    List<FacultyAvailabilityResponse> getAllFacultyAvailability();

    /**
     * Get Faculty Availability By Faculty
     */
    List<FacultyAvailabilityResponse> getFacultyAvailabilityByFaculty(
            Long facultyId);

    /**
     * Get Faculty Availability By Day
     */
    List<FacultyAvailabilityResponse> getFacultyAvailabilityByDay(
            String dayOfWeek);

    /**
     * Get Faculty Availability By Faculty And Day
     */
    List<FacultyAvailabilityResponse> getFacultyAvailabilityByFacultyAndDay(
            Long facultyId,
            String dayOfWeek);
}