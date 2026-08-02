package com.timetable.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.holiday.HolidayRequest;
import com.timetable.dto.holiday.HolidayResponse;
import com.timetable.service.HolidayService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/holidays")
@Validated
@CrossOrigin(origins = "*")
public class HolidayController {

    private final HolidayService holidayService;

    public HolidayController(HolidayService holidayService) {
        this.holidayService = holidayService;
    }

    /**
     * Create Holiday
     */
    @PostMapping
    public ResponseEntity<HolidayResponse> saveHoliday(
            @Valid @RequestBody HolidayRequest request) {

        HolidayResponse response =
                holidayService.saveHoliday(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Holiday
     */
    @PutMapping("/{holidayId}")
    public ResponseEntity<HolidayResponse> updateHoliday(
            @PathVariable Long holidayId,
            @Valid @RequestBody HolidayRequest request) {

        HolidayResponse response =
                holidayService.updateHoliday(
                        holidayId,
                        request);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete Holiday
     */
    @DeleteMapping("/{holidayId}")
    public ResponseEntity<String> deleteHoliday(
            @PathVariable Long holidayId) {

        holidayService.deleteHoliday(holidayId);

        return ResponseEntity.ok(
                "Holiday deleted successfully.");
    }

    /**
     * Get Holiday By ID
     */
    @GetMapping("/{holidayId}")
    public ResponseEntity<HolidayResponse> getHolidayById(
            @PathVariable Long holidayId) {

        return ResponseEntity.ok(
                holidayService.getHolidayById(holidayId));
    }

    /**
     * Get All Holidays
     */
    @GetMapping
    public ResponseEntity<List<HolidayResponse>> getAllHolidays() {

        return ResponseEntity.ok(
                holidayService.getAllHolidays());
    }

    /**
     * Get Holiday By Date
     */
    @GetMapping("/date/{holidayDate}")
    public ResponseEntity<HolidayResponse> getHolidayByDate(
            @PathVariable LocalDate holidayDate) {

        return ResponseEntity.ok(
                holidayService.getHolidayByDate(holidayDate));
    }
}