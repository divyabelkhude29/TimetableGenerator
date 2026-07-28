package com.timetable.service;

import java.util.List;

import com.timetable.dto.timetableconstraint.TimetableConstraintRequest;
import com.timetable.dto.timetableconstraint.TimetableConstraintResponse;

public interface TimetableConstraintService {

    /**
     * Save Timetable Constraint
     */
    TimetableConstraintResponse saveConstraint(
            TimetableConstraintRequest request);

    /**
     * Update Timetable Constraint
     */
    TimetableConstraintResponse updateConstraint(
            Long constraintId,
            TimetableConstraintRequest request);

    /**
     * Delete Timetable Constraint
     */
    void deleteConstraint(
            Long constraintId);

    /**
     * Get Timetable Constraint By ID
     */
    TimetableConstraintResponse getConstraintById(
            Long constraintId);

    /**
     * Get All Timetable Constraints
     */
    List<TimetableConstraintResponse> getAllConstraints();

    /**
     * Get Timetable Constraint By Key
     */
    TimetableConstraintResponse getConstraintByKey(
            String constraintKey);

    /**
     * Get Constraint Value
     * (Used by Timetable Generation Algorithm)
     */
    String getConstraintValue(
            String constraintKey);

    /**
     * Check Whether Constraint Is Active
     */
    boolean isConstraintActive(
            String constraintKey);
}