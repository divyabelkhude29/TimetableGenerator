package com.timetable.algorithm;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import com.timetable.dto.timetablegeneration.TimetableSlotDTO;
import com.timetable.entity.Holiday;

@Component
public class HolidayChecker {

    public HolidayChecker() {

    }

    /**
     * Check whether the given timetable slot
     * falls on a working day.
     */
    public boolean isWorkingDay(
            TimetableSlotDTO slot) {

        /*
         * TODO
         *
         * Load Holiday records from database
         * and compare against the timetable slot.
         *
         * For now, assume every configured
         * working day is valid.
         */

        return true;
    }

    /**
     * Check whether a specific date
     * is declared as a holiday.
     */
    public boolean isHoliday(
            LocalDate date,
            List<Holiday> holidays) {

        if (date == null || holidays == null) {
            return false;
        }

        for (Holiday holiday : holidays) {

            if (holiday.getHolidayDate().equals(date)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Get holiday description
     * for the given date.
     */
    public String getHolidayReason(
            LocalDate date,
            List<Holiday> holidays) {

        if (date == null || holidays == null) {
            return null;
        }

        for (Holiday holiday : holidays) {

            if (holiday.getHolidayDate().equals(date)) {

                return holiday.getHolidayName();
            }
        }

        return null;
    }

    /**
     * Check whether a holiday
     * already exists.
     */
    public boolean holidayExists(
            LocalDate date,
            List<Holiday> holidays) {

        return isHoliday(date, holidays);
    }

    /**
     * Count holidays
     * between two dates.
     */
    public int countHolidays(
            LocalDate startDate,
            LocalDate endDate,
            List<Holiday> holidays) {

        if (startDate == null
                || endDate == null
                || holidays == null) {

            return 0;
        }

        int count = 0;

        for (Holiday holiday : holidays) {

            LocalDate holidayDate =
                    holiday.getHolidayDate();

            if ((holidayDate.isEqual(startDate)
                    || holidayDate.isAfter(startDate))
                    &&
                    (holidayDate.isEqual(endDate)
                    || holidayDate.isBefore(endDate))) {

                count++;
            }
        }

        return count;
    }

    /**
     * Check whether the timetable slot
     * belongs to a holiday date.
     */
    public boolean isHolidaySlot(
            LocalDate date,
            TimetableSlotDTO slot,
            List<Holiday> holidays) {

        return isHoliday(date, holidays);
    }

}