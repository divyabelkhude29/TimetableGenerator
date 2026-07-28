package com.timetable.algorithm;

import java.util.List;

import org.springframework.stereotype.Component;

import com.timetable.dto.timetablegeneration.TimetableSlotDTO;
import com.timetable.entity.FacultyAvailability;

@Component
public class FacultyAvailabilityChecker {

    public FacultyAvailabilityChecker() {

    }

    /**
     * Check whether the faculty is available
     * for the given timetable slot.
     *
     * Database lookup will be added later.
     */
    public boolean isFacultyAvailable(
            TimetableSlotDTO slot) {

        /*
         * TODO
         * Load FacultyAvailability records
         * from the database using FacultyAvailabilityDAO.
         */

        return true;
    }

    /**
     * Check availability using loaded records.
     */
    public boolean isFacultyAvailable(
            TimetableSlotDTO slot,
            List<FacultyAvailability> availabilityList) {

        if (availabilityList == null || availabilityList.isEmpty()) {

            // No availability configured.
            // Assume faculty is available.
            return true;
        }

        for (FacultyAvailability availability : availabilityList) {

            // Faculty mismatch
            if (!availability.getFaculty()
                    .getFacultyId()
                    .equals(slot.getFacultyId())) {

                continue;
            }

            // Day mismatch
            if (!availability.getDayOfWeek()
                    .equalsIgnoreCase(slot.getDayOfWeek())) {

                continue;
            }

            // Time Slot mismatch
            if (!availability.getTimeSlot()
                    .getTimeSlotId()
                    .equals(slot.getTimeSlotId())) {

                continue;
            }

            // Matching availability record found
            return Boolean.TRUE.equals(
                    availability.getAvailable());
        }

        // No matching record found.
        // Assume faculty is available.
        return true;
    }

    /**
     * Check whether faculty has
     * at least one available slot.
     */
    public boolean hasAnyAvailability(
            List<FacultyAvailability> availabilityList) {

        if (availabilityList == null
                || availabilityList.isEmpty()) {

            return false;
        }

        for (FacultyAvailability availability : availabilityList) {

            if (Boolean.TRUE.equals(
                    availability.getAvailable())) {

                return true;
            }
        }

        return false;
    }

    /**
     * Count total available slots.
     */
    public int countAvailableSlots(
            List<FacultyAvailability> availabilityList) {

        if (availabilityList == null) {

            return 0;
        }

        int count = 0;

        for (FacultyAvailability availability : availabilityList) {

            if (Boolean.TRUE.equals(
                    availability.getAvailable())) {

                count++;
            }
        }

        return count;
    }

}