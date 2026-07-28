package com.timetable.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.timeslot.TimeSlotRequest;
import com.timetable.dto.timeslot.TimeSlotResponse;
import com.timetable.service.TimeSlotService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/timeslots")
@Validated
@CrossOrigin(origins = "*")
public class TimeSlotController {

    private final TimeSlotService timeSlotService;

    public TimeSlotController(TimeSlotService timeSlotService) {
        this.timeSlotService = timeSlotService;
    }

    /**
     * Create Time Slot
     */
    @PostMapping
    public ResponseEntity<TimeSlotResponse> saveTimeSlot(
            @Valid @RequestBody TimeSlotRequest request) {

        TimeSlotResponse response = timeSlotService.saveTimeSlot(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Time Slot
     */
    @PutMapping("/{timeSlotId}")
    public ResponseEntity<TimeSlotResponse> updateTimeSlot(
            @PathVariable Long timeSlotId,
            @Valid @RequestBody TimeSlotRequest request) {

        TimeSlotResponse response =
                timeSlotService.updateTimeSlot(timeSlotId, request);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete Time Slot
     */
    @DeleteMapping("/{timeSlotId}")
    public ResponseEntity<String> deleteTimeSlot(
            @PathVariable Long timeSlotId) {

        timeSlotService.deleteTimeSlot(timeSlotId);

        return ResponseEntity.ok("Time Slot deleted successfully.");
    }

    /**
     * Get Time Slot By ID
     */
    @GetMapping("/{timeSlotId}")
    public ResponseEntity<TimeSlotResponse> getTimeSlotById(
            @PathVariable Long timeSlotId) {

        return ResponseEntity.ok(
                timeSlotService.getTimeSlotById(timeSlotId));
    }

    /**
     * Get All Time Slots
     */
    @GetMapping
    public ResponseEntity<List<TimeSlotResponse>> getAllTimeSlots() {

        return ResponseEntity.ok(
                timeSlotService.getAllTimeSlots());
    }

    /**
     * Get Time Slots By Day Of Week
     */
    @GetMapping("/day/{dayOfWeek}")
    public ResponseEntity<List<TimeSlotResponse>> getTimeSlotsByDayOfWeek(
            @PathVariable String dayOfWeek) {

        return ResponseEntity.ok(
                timeSlotService.getTimeSlotsByDayOfWeek(dayOfWeek));
    }

    /**
     * Get Time Slots By Slot Order
     */
    @GetMapping("/order/{slotOrder}")
    public ResponseEntity<List<TimeSlotResponse>> getTimeSlotsBySlotOrder(
            @PathVariable Integer slotOrder) {

        return ResponseEntity.ok(
                timeSlotService.getTimeSlotsBySlotOrder(slotOrder));
    }
}