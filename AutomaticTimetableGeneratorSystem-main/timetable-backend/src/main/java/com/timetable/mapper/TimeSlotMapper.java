package com.timetable.mapper;

import com.timetable.dto.timeslot.TimeSlotRequest;
import com.timetable.dto.timeslot.TimeSlotResponse;
import com.timetable.entity.TimeSlot;

public class TimeSlotMapper {

    private TimeSlotMapper() {
    }

    /**
     * Request DTO -> Entity
     */
    public static TimeSlot toEntity(TimeSlotRequest request) {

        TimeSlot timeSlot = new TimeSlot();

        timeSlot.setSlotName(request.getSlotName());
        timeSlot.setDayOfWeek(request.getDayOfWeek());
        timeSlot.setStartTime(request.getStartTime());
        timeSlot.setEndTime(request.getEndTime());
        timeSlot.setSlotOrder(request.getSlotOrder());
        timeSlot.setActive(request.getActive());

        return timeSlot;
    }

    /**
     * Update Existing Entity
     */
    public static void updateEntity(TimeSlot timeSlot,
                                    TimeSlotRequest request) {

        timeSlot.setSlotName(request.getSlotName());
        timeSlot.setDayOfWeek(request.getDayOfWeek());
        timeSlot.setStartTime(request.getStartTime());
        timeSlot.setEndTime(request.getEndTime());
        timeSlot.setSlotOrder(request.getSlotOrder());
        timeSlot.setActive(request.getActive());
    }

    /**
     * Entity -> Response DTO
     */
    public static TimeSlotResponse toResponse(TimeSlot timeSlot) {

        TimeSlotResponse response = new TimeSlotResponse();

        response.setTimeSlotId(timeSlot.getTimeSlotId());
        response.setSlotName(timeSlot.getSlotName());
        response.setDayOfWeek(timeSlot.getDayOfWeek());
        response.setStartTime(timeSlot.getStartTime());
        response.setEndTime(timeSlot.getEndTime());
        response.setSlotOrder(timeSlot.getSlotOrder());
        response.setActive(timeSlot.getActive());

        return response;
    }
}