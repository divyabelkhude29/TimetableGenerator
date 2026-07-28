package com.timetable.dao;

import java.time.LocalDate;
import java.util.List;

import com.timetable.entity.Holiday;

public interface HolidayDAO {

    /**
     * Save Holiday
     */
    Holiday save(Holiday holiday);

    /**
     * Update Holiday
     */
    Holiday update(Holiday holiday);

    /**
     * Delete Holiday
     */
    void delete(Long holidayId);

    /**
     * Find Holiday By ID
     */
    Holiday findById(Long holidayId);

    /**
     * Find All Holidays
     */
    List<Holiday> findAll();

    /**
     * Find Holiday By Date
     */
    Holiday findByHolidayDate(LocalDate holidayDate);

    /**
     * Check Whether Holiday Exists For Date
     */
    boolean existsByHolidayDate(LocalDate holidayDate);

    /**
     * Check Whether Another Holiday Exists
     * (Used During Update)
     */
    boolean existsByHolidayDateAndNotHolidayId(
            LocalDate holidayDate,
            Long holidayId);

    /**
     * Check Whether Date Is Holiday
     * (Used By Timetable Generator)
     */
    boolean isHoliday(LocalDate holidayDate);
}