package com.timetable.mapper;

import com.timetable.dto.holiday.HolidayRequest;
import com.timetable.dto.holiday.HolidayResponse;
import com.timetable.entity.Holiday;

public class HolidayMapper {

    private HolidayMapper() {
    }

    /**
     * Request DTO -> Entity
     */
    public static Holiday toEntity(HolidayRequest request) {

        Holiday holiday = new Holiday();

        holiday.setHolidayName(request.getHolidayName());
        holiday.setHolidayDate(request.getHolidayDate());
        holiday.setHolidayType(request.getHolidayType());
        holiday.setDescription(request.getDescription());
        holiday.setActive(request.getActive());

        return holiday;
    }

    /**
     * Update Existing Entity
     */
    public static void updateEntity(
            Holiday holiday,
            HolidayRequest request) {

        holiday.setHolidayName(request.getHolidayName());
        holiday.setHolidayDate(request.getHolidayDate());
        holiday.setHolidayType(request.getHolidayType());
        holiday.setDescription(request.getDescription());
        holiday.setActive(request.getActive());
    }

    /**
     * Entity -> Response DTO
     */
    public static HolidayResponse toResponse(Holiday holiday) {

        HolidayResponse response = new HolidayResponse();

        response.setHolidayId(holiday.getHolidayId());
        response.setHolidayName(holiday.getHolidayName());
        response.setHolidayDate(holiday.getHolidayDate());
        response.setHolidayType(holiday.getHolidayType());
        response.setDescription(holiday.getDescription());
        response.setActive(holiday.getActive());

        return response;
    }
}