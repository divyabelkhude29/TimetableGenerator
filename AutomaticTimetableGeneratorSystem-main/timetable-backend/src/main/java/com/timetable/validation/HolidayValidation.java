package com.timetable.validation;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.timetable.dao.HolidayDAO;
import com.timetable.dto.holiday.HolidayRequest;
import com.timetable.entity.Holiday;
import com.timetable.exception.ResourceNotFoundException;

@Component
public class HolidayValidation {

    private final HolidayDAO holidayDAO;

    public HolidayValidation(HolidayDAO holidayDAO) {
        this.holidayDAO = holidayDAO;
    }

    /**
     * Validate Before Create
     */
    public void validateForCreate(HolidayRequest request) {

        if (holidayDAO.existsByHolidayDate(request.getHolidayDate())) {

            throw new IllegalArgumentException(
                    "Holiday already exists for date : "
                            + request.getHolidayDate());
        }
    }

    /**
     * Validate Before Update
     */
    public void validateForUpdate(
            Long holidayId,
            HolidayRequest request) {

        validateHoliday(holidayId);

        if (holidayDAO.existsByHolidayDateAndNotHolidayId(
                request.getHolidayDate(),
                holidayId)) {

            throw new IllegalArgumentException(
                    "Another holiday already exists for date : "
                            + request.getHolidayDate());
        }
    }

    /**
     * Validate Holiday Exists
     */
    public Holiday validateHoliday(Long holidayId) {

        Holiday holiday = holidayDAO.findById(holidayId);

        if (holiday == null) {

            throw new ResourceNotFoundException(
                    "Holiday not found with ID : "
                            + holidayId);
        }

        return holiday;
    }

    /**
     * Validate Holiday By Date
     */
    public Holiday validateHolidayByDate(
            LocalDate holidayDate) {

        Holiday holiday =
                holidayDAO.findByHolidayDate(holidayDate);

        if (holiday == null) {

            throw new ResourceNotFoundException(
                    "Holiday not found for date : "
                            + holidayDate);
        }

        return holiday;
    }
}