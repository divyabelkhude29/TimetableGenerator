package com.timetable.dao;

import java.util.List;

import com.timetable.entity.Faculty;
import com.timetable.entity.FacultyAvailability;
import com.timetable.entity.TimeSlot;

public interface FacultyAvailabilityDAO {

    /**
     * Save Faculty Availability
     */
    FacultyAvailability save(FacultyAvailability availability);

    /**
     * Update Faculty Availability
     */
    FacultyAvailability update(FacultyAvailability availability);

    /**
     * Delete Faculty Availability
     */
    void delete(Long availabilityId);

    /**
     * Find Faculty Availability By ID
     */
    FacultyAvailability findById(Long availabilityId);

    /**
     * Find All Faculty Availability Records
     */
    List<FacultyAvailability> findAll();

    /**
     * Find By Faculty
     */
    List<FacultyAvailability> findByFaculty(Faculty faculty);

    /**
     * Find By Day
     */
    List<FacultyAvailability> findByDay(String dayOfWeek);

    /**
     * Find By Faculty And Day
     */
    List<FacultyAvailability> findByFacultyAndDay(
            Faculty faculty,
            String dayOfWeek);

    /**
     * Find By Time Slot
     */
    List<FacultyAvailability> findByTimeSlot(TimeSlot timeSlot);

    /**
     * Find By Faculty, Day And Time Slot
     */
    FacultyAvailability findByFacultyAndDayAndTimeSlot(
            Faculty faculty,
            String dayOfWeek,
            TimeSlot timeSlot);

    /**
     * Check Duplicate Faculty Availability
     */
    boolean existsByFacultyAndDayAndTimeSlot(
            Faculty faculty,
            String dayOfWeek,
            TimeSlot timeSlot);
}