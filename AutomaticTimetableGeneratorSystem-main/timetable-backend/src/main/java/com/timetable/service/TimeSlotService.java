package com.timetable.service;

import java.util.List;

import com.timetable.dto.timeslot.TimeSlotRequest;
import com.timetable.dto.timeslot.TimeSlotResponse;

public interface TimeSlotService {

    /**
     * Save Time Slot
     */
    TimeSlotResponse saveTimeSlot(TimeSlotRequest request);

    /**
     * Update Time Slot
     */
    TimeSlotResponse updateTimeSlot(Long timeSlotId,
                                    TimeSlotRequest request);

    /**
     * Delete Time Slot
     */
    void deleteTimeSlot(Long timeSlotId);

    /**
     * Get Time Slot By ID
     */
    TimeSlotResponse getTimeSlotById(Long timeSlotId);

    /**
     * Get All Time Slots
     */
    List<TimeSlotResponse> getAllTimeSlots();

    /**
     * Get Time Slots By Day Of Week
     */
    List<TimeSlotResponse> getTimeSlotsByDayOfWeek(String dayOfWeek);

    /**
     * Get Time Slots By Slot Order
     */
    List<TimeSlotResponse> getTimeSlotsBySlotOrder(Integer slotOrder);

}