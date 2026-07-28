package com.timetable.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.timetable.dto.timetableconstraint.TimetableConstraintRequest;
import com.timetable.dto.timetableconstraint.TimetableConstraintResponse;
import com.timetable.service.TimetableConstraintService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/timetable-constraints")
@Validated
@CrossOrigin(origins = "*")
public class TimetableConstraintController {

    private final TimetableConstraintService timetableConstraintService;

    public TimetableConstraintController(
            TimetableConstraintService timetableConstraintService) {

        this.timetableConstraintService = timetableConstraintService;
    }

    /**
     * Create Timetable Constraint
     */
    @PostMapping
    public ResponseEntity<TimetableConstraintResponse> saveConstraint(
            @Valid @RequestBody TimetableConstraintRequest request) {

        TimetableConstraintResponse response =
                timetableConstraintService.saveConstraint(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    /**
     * Update Timetable Constraint
     */
    @PutMapping("/{constraintId}")
    public ResponseEntity<TimetableConstraintResponse> updateConstraint(
            @PathVariable Long constraintId,
            @Valid @RequestBody TimetableConstraintRequest request) {

        TimetableConstraintResponse response =
                timetableConstraintService.updateConstraint(
                        constraintId,
                        request);

        return ResponseEntity.ok(response);
    }

    /**
     * Delete Timetable Constraint
     */
    @DeleteMapping("/{constraintId}")
    public ResponseEntity<String> deleteConstraint(
            @PathVariable Long constraintId) {

        timetableConstraintService.deleteConstraint(constraintId);

        return ResponseEntity.ok(
                "Timetable Constraint deleted successfully.");
    }

    /**
     * Get Timetable Constraint By ID
     */
    @GetMapping("/{constraintId}")
    public ResponseEntity<TimetableConstraintResponse> getConstraintById(
            @PathVariable Long constraintId) {

        return ResponseEntity.ok(
                timetableConstraintService.getConstraintById(constraintId));
    }

    /**
     * Get All Timetable Constraints
     */
    @GetMapping
    public ResponseEntity<List<TimetableConstraintResponse>> getAllConstraints() {

        return ResponseEntity.ok(
                timetableConstraintService.getAllConstraints());
    }

    /**
     * Get Timetable Constraint By Key
     */
    @GetMapping("/key/{constraintKey}")
    public ResponseEntity<TimetableConstraintResponse> getConstraintByKey(
            @PathVariable String constraintKey) {

        return ResponseEntity.ok(
                timetableConstraintService.getConstraintByKey(constraintKey));
    }
}