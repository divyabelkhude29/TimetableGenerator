package com.timetable.validation;

import org.springframework.stereotype.Component;

import com.timetable.dao.TimetableConstraintDAO;
import com.timetable.dto.timetableconstraint.TimetableConstraintRequest;
import com.timetable.entity.TimetableConstraint;
import com.timetable.exception.ResourceNotFoundException;

@Component
public class TimetableConstraintValidation {

    private final TimetableConstraintDAO timetableConstraintDAO;

    public TimetableConstraintValidation(
            TimetableConstraintDAO timetableConstraintDAO) {

        this.timetableConstraintDAO = timetableConstraintDAO;
    }

    /**
     * Validate Before Create
     */
    public void validateForCreate(
            TimetableConstraintRequest request) {

        if (timetableConstraintDAO.existsByConstraintKey(
                request.getConstraintKey())) {

            throw new IllegalArgumentException(
                    "Constraint already exists with key : "
                            + request.getConstraintKey());
        }
    }

    /**
     * Validate Before Update
     */
    public void validateForUpdate(
            Long constraintId,
            TimetableConstraintRequest request) {

        validateConstraint(constraintId);

        if (timetableConstraintDAO
                .existsByConstraintKeyAndNotConstraintId(
                        request.getConstraintKey(),
                        constraintId)) {

            throw new IllegalArgumentException(
                    "Another constraint already exists with key : "
                            + request.getConstraintKey());
        }
    }

    /**
     * Validate Constraint Exists
     */
    public TimetableConstraint validateConstraint(
            Long constraintId) {

        TimetableConstraint constraint =
                timetableConstraintDAO.findById(constraintId);

        if (constraint == null) {

            throw new ResourceNotFoundException(
                    "Timetable Constraint not found with ID : "
                            + constraintId);
        }

        return constraint;
    }

    /**
     * Validate Constraint By Key
     */
    public TimetableConstraint validateConstraintByKey(
            String constraintKey) {

        TimetableConstraint constraint =
                timetableConstraintDAO.findByConstraintKey(
                        constraintKey);

        if (constraint == null) {

            throw new ResourceNotFoundException(
                    "Timetable Constraint not found with key : "
                            + constraintKey);
        }

        return constraint;
    }
}