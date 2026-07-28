package com.timetable.dao;

import java.util.List;

import com.timetable.entity.TimetableConstraint;

public interface TimetableConstraintDAO {

    /**
     * Save Timetable Constraint
     */
    TimetableConstraint save(
            TimetableConstraint timetableConstraint);

    /**
     * Update Timetable Constraint
     */
    TimetableConstraint update(
            TimetableConstraint timetableConstraint);

    /**
     * Delete Timetable Constraint
     */
    void delete(Long constraintId);

    /**
     * Find Timetable Constraint By ID
     */
    TimetableConstraint findById(Long constraintId);

    /**
     * Find All Timetable Constraints
     */
    List<TimetableConstraint> findAll();

    /**
     * Find Timetable Constraint By Key
     */
    TimetableConstraint findByConstraintKey(
            String constraintKey);

    /**
     * Check Constraint Key Exists
     */
    boolean existsByConstraintKey(
            String constraintKey);

    /**
     * Check Duplicate Constraint Key During Update
     */
    boolean existsByConstraintKeyAndNotConstraintId(
            String constraintKey,
            Long constraintId);

    /**
     * Get Constraint Value
     * (Used By Timetable Generator)
     */
    String getConstraintValue(
            String constraintKey);

    /**
     * Check Whether Constraint Is Active
     */
    boolean isConstraintActive(
            String constraintKey);
}