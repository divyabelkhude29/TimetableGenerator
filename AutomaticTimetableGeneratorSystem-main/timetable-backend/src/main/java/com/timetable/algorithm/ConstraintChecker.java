package com.timetable.algorithm;

import java.util.List;

import org.springframework.stereotype.Component;

import com.timetable.dto.timetablegeneration.TimetableSlotDTO;

@Component
public class ConstraintChecker {

    private final FacultyAvailabilityChecker facultyAvailabilityChecker;
    private final HolidayChecker holidayChecker;
    private final WorkloadChecker workloadChecker;
    private final ConflictDetector conflictDetector;

    public ConstraintChecker(
            FacultyAvailabilityChecker facultyAvailabilityChecker,
            HolidayChecker holidayChecker,
            WorkloadChecker workloadChecker,
            ConflictDetector conflictDetector) {

        this.facultyAvailabilityChecker = facultyAvailabilityChecker;
        this.holidayChecker = holidayChecker;
        this.workloadChecker = workloadChecker;
        this.conflictDetector = conflictDetector;
    }

    /**
     * Validate all hard constraints.
     */
    public boolean isValid(
            TimetableSlotDTO slot,
            List<TimetableSlotDTO> generatedSlots) {

        /*
         * Holiday Check
         */
        if (!holidayChecker.isWorkingDay(slot)) {
            return false;
        }

        /*
         * Faculty Availability
         */
        if (!facultyAvailabilityChecker.isFacultyAvailable(slot)) {
            return false;
        }

        /*
         * Faculty Clash
         */
        if (conflictDetector.hasFacultyConflict(
                slot,
                generatedSlots)) {

            return false;
        }

        /*
         * Section Clash
         */
        if (conflictDetector.hasSectionConflict(
                slot,
                generatedSlots)) {

            return false;
        }

        /*
         * Subject Workload
         */
        if (!workloadChecker.canAssign(slot)) {
            return false;
        }

        /*
         * Lunch Break
         */
        if (isLunchBreak(slot)) {
            return false;
        }

        /*
         * Maximum Lectures Per Day
         */
        if (!withinDailyLimit(
                slot,
                generatedSlots)) {

            return false;
        }

        /*
         * Consecutive Lecture Check
         */
        if (!withinConsecutiveLimit(
                slot,
                generatedSlots)) {

            return false;
        }

        return true;
    }

    /**
     * Lunch Break Validation
     */
    private boolean isLunchBreak(
            TimetableSlotDTO slot) {

        /*
         * Example:
         * 12:30 - 1:30
         */

        return false;
    }

    /**
     * Maximum Daily Lecture Validation
     */
    private boolean withinDailyLimit(
            TimetableSlotDTO slot,
            List<TimetableSlotDTO> generatedSlots) {

        /*
         * Count lectures already
         * assigned for this section
         * on this working day.
         */

        return true;
    }

    /**
     * Consecutive Lecture Validation
     */
    private boolean withinConsecutiveLimit(
            TimetableSlotDTO slot,
            List<TimetableSlotDTO> generatedSlots) {

        /*
         * Example:
         * Max 3 consecutive lectures.
         */

        return true;
    }

}