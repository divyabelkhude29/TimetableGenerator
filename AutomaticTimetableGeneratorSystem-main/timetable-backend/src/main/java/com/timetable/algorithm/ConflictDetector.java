package com.timetable.algorithm;

import java.util.List;

import org.springframework.stereotype.Component;

import com.timetable.dto.timetablegeneration.TimetableSlotDTO;

@Component
public class ConflictDetector {

    public ConflictDetector() {

    }

    /**
     * Check if the faculty is already assigned
     * to another class in the same time slot.
     */
    public boolean hasFacultyConflict(
            TimetableSlotDTO candidate,
            List<TimetableSlotDTO> generatedSlots) {

        for (TimetableSlotDTO slot : generatedSlots) {

            if (slot.getFacultyId().equals(candidate.getFacultyId())
                    && slot.getDayOfWeek().equals(candidate.getDayOfWeek())
                    && slot.getTimeSlotId().equals(candidate.getTimeSlotId())) {

                return true;
            }
        }

        return false;
    }

    /**
     * Check if the academic section already has
     * another subject scheduled in the same slot.
     */
    public boolean hasSectionConflict(
            TimetableSlotDTO candidate,
            List<TimetableSlotDTO> generatedSlots) {

        for (TimetableSlotDTO slot : generatedSlots) {

            if (slot.getAcademicSectionId().equals(candidate.getAcademicSectionId())
                    && slot.getDayOfWeek().equals(candidate.getDayOfWeek())
                    && slot.getTimeSlotId().equals(candidate.getTimeSlotId())) {

                return true;
            }
        }

        return false;
    }

    /**
     * Check whether an identical timetable entry
     * already exists.
     */
    public boolean isDuplicateEntry(
            TimetableSlotDTO candidate,
            List<TimetableSlotDTO> generatedSlots) {

        for (TimetableSlotDTO slot : generatedSlots) {

            if (slot.getAcademicSectionId().equals(candidate.getAcademicSectionId())
                    && slot.getDayOfWeek().equals(candidate.getDayOfWeek())
                    && slot.getTimeSlotId().equals(candidate.getTimeSlotId())
                    && slot.getSubjectId().equals(candidate.getSubjectId())
                    && slot.getFacultyId().equals(candidate.getFacultyId())) {

                return true;
            }
        }

        return false;
    }

    /**
     * Detect time slot overlap.
     *
     * Assumes timeSlotId uniquely represents a
     * non-overlapping period.
     */
    public boolean hasTimeOverlap(
            TimetableSlotDTO candidate,
            List<TimetableSlotDTO> generatedSlots) {

        for (TimetableSlotDTO slot : generatedSlots) {

            if (slot.getDayOfWeek().equals(candidate.getDayOfWeek())
                    && slot.getTimeSlotId().equals(candidate.getTimeSlotId())
                    && slot.getAcademicSectionId().equals(candidate.getAcademicSectionId())) {

                return true;
            }
        }

        return false;
    }

    /**
     * Perform all conflict checks.
     */
    public boolean hasConflict(
            TimetableSlotDTO candidate,
            List<TimetableSlotDTO> generatedSlots) {

        return hasFacultyConflict(candidate, generatedSlots)
                || hasSectionConflict(candidate, generatedSlots)
                || isDuplicateEntry(candidate, generatedSlots)
                || hasTimeOverlap(candidate, generatedSlots);
    }

}