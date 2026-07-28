package com.timetable.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.TimeSlotDAO;
import com.timetable.dto.timeslot.TimeSlotRequest;
import com.timetable.dto.timeslot.TimeSlotResponse;
import com.timetable.entity.TimeSlot;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.mapper.TimeSlotMapper;
import com.timetable.service.TimeSlotService;

@Service
@Transactional
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotDAO timeSlotDAO;

    public TimeSlotServiceImpl(TimeSlotDAO timeSlotDAO) {
        this.timeSlotDAO = timeSlotDAO;
    }

    /**
     * Save Time Slot
     */
    @Override
    public TimeSlotResponse saveTimeSlot(TimeSlotRequest request) {

        if (!request.getEndTime().isAfter(request.getStartTime())) {
            throw new IllegalArgumentException(
                    "End time must be after start time.");
        }

        TimeSlot existing = timeSlotDAO.findByDayOfWeekAndSlotOrder(
                request.getDayOfWeek(),
                request.getSlotOrder());

        if (existing != null) {
            throw new IllegalArgumentException(
                    "Slot order already exists for " + request.getDayOfWeek());
        }

        TimeSlot timeSlot = TimeSlotMapper.toEntity(request);

        TimeSlot savedTimeSlot = timeSlotDAO.save(timeSlot);

        return TimeSlotMapper.toResponse(savedTimeSlot);
    }

    /**
     * Update Time Slot
     */
    @Override
    public TimeSlotResponse updateTimeSlot(Long timeSlotId,
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

        TimeSlotMapper.updateEntity(timeSlot, request);

        TimeSlot updatedTimeSlot = timeSlotDAO.update(timeSlot);

        return TimeSlotMapper.toResponse(updatedTimeSlot);
    }

    /**
     * Delete Time Slot
     */
    @Override
    public void deleteTimeSlot(Long timeSlotId) {

        TimeSlot timeSlot = timeSlotDAO.findById(timeSlotId);

        if (timeSlot == null) {
            throw new ResourceNotFoundException(
                    "Time Slot not found with ID : " + timeSlotId);
        }

        timeSlotDAO.delete(timeSlotId);
    }

    /**
     * Get Time Slot By ID
     */
    @Override
    @Transactional(readOnly = true)
    public TimeSlotResponse getTimeSlotById(Long timeSlotId) {

        TimeSlot timeSlot = timeSlotDAO.findById(timeSlotId);

        if (timeSlot == null) {
            throw new ResourceNotFoundException(
                    "Time Slot not found with ID : " + timeSlotId);
        }

        return TimeSlotMapper.toResponse(timeSlot);
    }

    /**
     * Get All Time Slots
     */
    @Override
    @Transactional(readOnly = true)
    public List<TimeSlotResponse> getAllTimeSlots() {

        return timeSlotDAO.findAll()
                .stream()
                .map(TimeSlotMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Time Slots By Day Of Week
     */
    @Override
    @Transactional(readOnly = true)
    public List<TimeSlotResponse> getTimeSlotsByDayOfWeek(String dayOfWeek) {

        return timeSlotDAO.findByDayOfWeek(dayOfWeek)
                .stream()
                .map(TimeSlotMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Time Slots By Slot Order
     */
    @Override
    @Transactional(readOnly = true)
    public List<TimeSlotResponse> getTimeSlotsBySlotOrder(Integer slotOrder) {

        return timeSlotDAO.findBySlotOrder(slotOrder)
                .stream()
                .map(TimeSlotMapper::toResponse)
                .collect(Collectors.toList());
    }
}