package com.timetable.service;

import java.util.List;

import com.timetable.dto.timetable.TimetableRequest;
import com.timetable.dto.timetable.TimetableResponse;

public interface TimetableService {

    /**
     * Save Timetable
     */
    TimetableResponse saveTimetable(TimetableRequest request);

    /**
     * Update Timetable
     */
    TimetableResponse updateTimetable(
            Long timetableId,
            TimetableRequest request);

    /**
     * Delete Timetable
     */
    void deleteTimetable(Long timetableId);

    /**
     * Get Timetable By ID
     */
    TimetableResponse getTimetableById(Long timetableId);

    /**
     * Get All Timetables
     */
    List<TimetableResponse> getAllTimetables();

    /**
     * Get Timetables By Day
     */
    List<TimetableResponse> getTimetablesByDay(
            String dayOfWeek);

    /**
     * Get Timetables By Classroom
     */
    List<TimetableResponse> getTimetablesByClassroom(
            Long classroomId);

    /**
     * Get Timetables By Faculty
     */
    List<TimetableResponse> getTimetablesByFaculty(
            Long facultyId);

    /**
     * Get Timetables By Section
     */
    List<TimetableResponse> getTimetablesBySection(
            Long sectionId);

    /**
     * Get Timetables By Allocation
     */
    List<TimetableResponse> getTimetablesByAllocation(
            Long allocationId);
}