package com.timetable.validation;

import org.springframework.stereotype.Component;

import com.timetable.dao.TimeSlotDAO;
import com.timetable.dto.timeslot.TimeSlotRequest;
import com.timetable.entity.TimeSlot;
import com.timetable.exception.ResourceNotFoundException;

@Component
public class TimeSlotValidation {

    private final TimeSlotDAO timeSlotDAO;

    public TimeSlotValidation(TimeSlotDAO timeSlotDAO) {
        this.timeSlotDAO = timeSlotDAO;
    }

    /**
     * Validate Before Create
     */
    public void validateForCreate(TimeSlotRequest request) {

        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new IllegalArgumentException(
                    "End time must be after start time.");
        }

        if (timeSlotDAO.existsByDayOfWeekAndSlotOrder(
                request.getDayOfWeek(),
                request.getSlotOrder())) {

            throw new IllegalArgumentException(
                    "Slot order already exists for " + request.getDayOfWeek());
        }
    }

    /**
     * Validate Before Update
     */
    public void validateForUpdate(Long timeSlotId,
                                  TimeSlotRequest request) {

        TimeSlot timeSlot = timeSlotDAO.findById(timeSlotId);

        if (timeSlot == null) {
            throw new ResourceNotFoundException(
                    "Time Slot not found with ID : " + timeSlotId);
        }

        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new IllegalArgumentException(
                    "End time must be after start time.");
        }

        TimeSlot duplicate = timeSlotDAO.findByDayOfWeekAndSlotOrder(
                request.getDayOfWeek(),
                request.getSlotOrder());

        if (duplicate != null &&
                !duplicate.getTimeSlotId().equals(timeSlotId)) {

            throw new IllegalArgumentException(
                    "Slot order already exists for " + request.getDayOfWeek());
        }
    }

    /**
     * Validate Time Slot Exists
     */
    public TimeSlot validateTimeSlot(Long timeSlotId) {

        TimeSlot timeSlot = timeSlotDAO.findById(timeSlotId);

        if (timeSlot == null) {
            throw new ResourceNotFoundException(
                    "Time Slot not found with ID : " + timeSlotId);
        }

        return timeSlot;
    }
}