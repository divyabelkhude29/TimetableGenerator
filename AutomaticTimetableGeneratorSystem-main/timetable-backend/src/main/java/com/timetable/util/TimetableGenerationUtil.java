package com.timetable.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.timetable.dto.timetablegeneration.TimetableSlotDTO;

@Component
public class TimetableGenerationUtil {

    /**
     * Check if slot list is null or empty.
     */
    public boolean isEmpty(List<TimetableSlotDTO> slots) {

        return slots == null || slots.isEmpty();
    }

    /**
     * Clone timetable slots.
     */
    public List<TimetableSlotDTO> cloneSlots(
            List<TimetableSlotDTO> slots) {

        if (slots == null) {

            return new ArrayList<>();
        }

        return new ArrayList<>(slots);
    }

    /**
     * Count total generated classes.
     */
    public int countClasses(
            List<TimetableSlotDTO> slots) {

        if (slots == null) {

            return 0;
        }

        return slots.size();
    }

    /**
     * Check whether two timetable slots
     * refer to the same time slot.
     */
    public boolean isSameTimeSlot(
            TimetableSlotDTO slot1,
            TimetableSlotDTO slot2) {

        if (slot1 == null || slot2 == null) {

            return false;
        }

        return slot1.getDayOfWeek()
                .equalsIgnoreCase(slot2.getDayOfWeek())
                &&
                slot1.getTimeSlotId()
                        .equals(slot2.getTimeSlotId());
    }

    /**
     * Check faculty conflict.
     */
    public boolean hasFacultyConflict(
            TimetableSlotDTO slot1,
            TimetableSlotDTO slot2) {

        if (!isSameTimeSlot(slot1, slot2)) {

            return false;
        }

        return slot1.getFacultyId()
                .equals(slot2.getFacultyId());
    }

    /**
     * Check section conflict.
     */
    public boolean hasSectionConflict(
            TimetableSlotDTO slot1,
            TimetableSlotDTO slot2) {

        if (!isSameTimeSlot(slot1, slot2)) {

            return false;
        }

        return slot1.getAcademicSectionId()
                .equals(slot2.getAcademicSectionId());
    }

    /**
     * Check classroom conflict.
     */
    public boolean hasClassroomConflict(
            TimetableSlotDTO slot1,
            TimetableSlotDTO slot2) {

        if (!isSameTimeSlot(slot1, slot2)) {

            return false;
        }

        if (slot1.getClass() == null
                || slot2.getClass() == null) {

            return false;
        }

        return slot1.getClass()
                .equals(slot2.getClass());
    }

    /**
     * Find duplicate timetable entries.
     */
    public boolean hasDuplicateEntries(
            List<TimetableSlotDTO> slots) {

        if (slots == null) {

            return false;
        }

        Set<String> keys =
                new HashSet<>();

        for (TimetableSlotDTO slot : slots) {

            String key =
                    slot.getAcademicSectionId()
                    + "-"
                    + slot.getDayOfWeek()
                    + "-"
                    + slot.getTimeSlotId();

            if (!keys.add(key)) {

                return true;
            }
        }

        return false;
    }

    /**
     * Count lectures for a subject.
     */
    public int countSubjectLectures(
            List<TimetableSlotDTO> slots,
            Long subjectId) {

        if (slots == null) {

            return 0;
        }

        int count = 0;

        for (TimetableSlotDTO slot : slots) {

            if (slot.getSubjectId()
                    .equals(subjectId)) {

                count++;
            }
        }

        return count;
    }

    /**
     * Count lectures assigned to faculty.
     */
    public int countFacultyLectures(
            List<TimetableSlotDTO> slots,
            Long facultyId) {

        if (slots == null) {

            return 0;
        }

        int count = 0;

        for (TimetableSlotDTO slot : slots) {

            if (slot.getFacultyId()
                    .equals(facultyId)) {

                count++;
            }
        }

        return count;
    }

    /**
     * Remove all timetable slots.
     */
    public void clear(
            List<TimetableSlotDTO> slots) {

        if (slots != null) {

            slots.clear();
        }
    }

}