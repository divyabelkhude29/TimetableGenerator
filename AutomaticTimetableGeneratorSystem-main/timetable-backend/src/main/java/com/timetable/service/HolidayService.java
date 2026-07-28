package com.timetable.service;

import java.time.LocalDate;
import java.util.List;

import com.timetable.dto.holiday.HolidayRequest;
import com.timetable.dto.holiday.HolidayResponse;

public interface HolidayService {

    /**
     * Save Holiday
     */
    HolidayResponse saveHoliday(
            HolidayRequest request);

    /**
     * Update Holiday
     */
    HolidayResponse updateHoliday(
            Long holidayId,
            HolidayRequest request);

    /**
     * Delete Holiday
     */
    void deleteHoliday(Long holidayId);

    /**
     * Get Holiday By ID
     */
    HolidayResponse getHolidayById(
            Long holidayId);

    /**
     * Get All Holidays
     */
    List<HolidayResponse> getAllHolidays();

    /**
     * Get Holiday By Date
     */
    HolidayResponse getHolidayByDate(
            LocalDate holidayDate);

    /**
     * Check Whether Date Is Holiday
     * (Used By Timetable Generator)
     */
    boolean isHoliday(
            LocalDate holidayDate);
}