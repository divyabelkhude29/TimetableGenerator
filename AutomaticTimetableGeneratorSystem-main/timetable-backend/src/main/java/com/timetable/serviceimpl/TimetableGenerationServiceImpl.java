package com.timetable.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dto.timetablegeneration.GenerateTimetableRequest;
import com.timetable.dto.timetablegeneration.TimetableGenerationResponse;
import com.timetable.dto.timetablegeneration.TimetableSlotDTO;
import com.timetable.service.TimetableGenerationService;

@Service
@Transactional
public class TimetableGenerationServiceImpl
        implements TimetableGenerationService {

    public TimetableGenerationServiceImpl() {

    }

    /**
     * Generate Timetable
     */
    @Override
    public TimetableGenerationResponse generateTimetable(
            GenerateTimetableRequest request) {

        long startTime = System.currentTimeMillis();

        TimetableGenerationResponse response =
                new TimetableGenerationResponse();

        // Validate Request
        List<String> errors = validateGeneration(request);

        if (!errors.isEmpty()) {

            response.setSuccess(false);
            response.setMessage("Validation failed.");

            response.setConflicts(errors);
            response.setGeneratedAt(LocalDateTime.now());

            return response;
        }

        /*
         * Delete existing timetable
         * if overwrite option is enabled.
         */
        if (request.isOverwriteExisting()) {

            deleteGeneratedTimetable(request);

        }

        /*
         * Generate timetable.
         * (Currently placeholder implementation)
         */
        List<TimetableSlotDTO> generatedSlots =
                previewTimetable(request);

        /*
         * TODO
         * Save generatedSlots into Timetable table.
         */

        response.setSuccess(true);
        response.setMessage("Timetable generated successfully.");

        response.setAcademicYear(request.getAcademicYear());
        response.setCourseId(request.getCourseId());
        response.setSemesterId(request.getSemesterId());
        response.setSectionId(request.getSectionId());

        response.setTotalClassesGenerated(
                generatedSlots.size());

        response.setTotalHoursAssigned(
                generatedSlots.size());

        response.setExecutionTimeInMilliseconds(
                System.currentTimeMillis() - startTime);

        response.setGeneratedAt(LocalDateTime.now());

        return response;
    }

    /**
     * Preview Timetable
     */
    @Override
    public List<TimetableSlotDTO> previewTimetable(
            GenerateTimetableRequest request) {

        List<TimetableSlotDTO> generatedSlots =
                new ArrayList<>();

        /*
         * TODO
         * Call timetable generation algorithm here.
         */

        return generatedSlots;
    }

    /**
     * Validate Request
     */
    @Override
    public List<String> validateGeneration(
            GenerateTimetableRequest request) {

        List<String> errors =
                new ArrayList<>();

        if (request.getAcademicYear() == null
                || request.getAcademicYear().isBlank()) {

            errors.add("Academic Year is required.");
        }

        if (request.getCourseId() == null) {

            errors.add("Course is required.");

        }

        if (request.getSemesterId() == null) {

            errors.add("Semester is required.");

        }

        if (request.getSectionId() == null) {

            errors.add("Section is required.");

        }

        return errors;
    }

    /**
     * Delete Existing Timetable
     */
    @Override
    public void deleteGeneratedTimetable(
            GenerateTimetableRequest request) {

        /*
         * TODO
         * Delete timetable records from database
         * for the given:
         *
         * Academic Year
         * Course
         * Semester
         * Section
         */

    }

    /**
     * Regenerate Timetable
     */
    @Override
    public TimetableGenerationResponse regenerateTimetable(
            GenerateTimetableRequest request) {

        deleteGeneratedTimetable(request);

        return generateTimetable(request);

    }

}