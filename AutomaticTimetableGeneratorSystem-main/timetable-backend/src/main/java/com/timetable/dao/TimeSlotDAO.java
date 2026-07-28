package com.timetable.dao;

import java.util.List;

import com.timetable.entity.TimeSlot;

public interface TimeSlotDAO {

    /**
     * Save Time Slot
     */
    TimeSlot save(TimeSlot timeSlot);

    /**
     * Update Time Slot
     */
    TimeSlot update(TimeSlot timeSlot);

    /**
     * Delete Time Slot
     */
    void delete(Long timeSlotId);

    /**
     * Find Time Slot By ID
     */
    TimeSlot findById(Long timeSlotId);

    /**
     * Find All Time Slots
     */
    List<TimeSlot> findAll();

    /**
     * Find Time Slots By Day Of Week
     */
    List<TimeSlot> findByDayOfWeek(String dayOfWeek);

    /**
     * Find Time Slot By Day Of Week And Slot Order
     */
    TimeSlot findByDayOfWeekAndSlotOrder(String dayOfWeek, Integer slotOrder);

    /**
     * Find Time Slots By Slot Order
     */
    List<TimeSlot> findBySlotOrder(Integer slotOrder);

    /**
     * Check Time Slot Exists
     */
    boolean existsByDayOfWeekAndSlotOrder(String dayOfWeek, Integer slotOrder);

}