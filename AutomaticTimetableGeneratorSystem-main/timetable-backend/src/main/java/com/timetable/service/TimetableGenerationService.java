package com.timetable.service;

import java.util.List;

import com.timetable.dto.timetablegeneration.GenerateTimetableRequest;
import com.timetable.dto.timetablegeneration.TimetableGenerationResponse;
import com.timetable.dto.timetablegeneration.TimetableSlotDTO;

public interface TimetableGenerationService {

    /**
     * Generate timetable for a specific
     * Academic Year + Course + Semester + Section.
     */
    TimetableGenerationResponse generateTimetable(
            GenerateTimetableRequest request);

    /**
     * Preview generated timetable
     * without saving into database.
     */
    List<TimetableSlotDTO> previewTimetable(
            GenerateTimetableRequest request);

    /**
     * Validate all constraints before generation.
     */
    List<String> validateGeneration(
            GenerateTimetableRequest request);

    /**
     * Delete previously generated timetable.
     */
    void deleteGeneratedTimetable(
            GenerateTimetableRequest request);

    /**
     * Regenerate timetable.
     */
    TimetableGenerationResponse regenerateTimetable(
            GenerateTimetableRequest request);

}