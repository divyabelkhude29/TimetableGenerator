package com.timetable.mapper;

import com.timetable.dto.timetableconstraint.TimetableConstraintRequest;
import com.timetable.dto.timetableconstraint.TimetableConstraintResponse;
import com.timetable.entity.TimetableConstraint;

public class TimetableConstraintMapper {

    private TimetableConstraintMapper() {
    }

    /**
     * Request DTO -> Entity
     */
    public static TimetableConstraint toEntity(
            TimetableConstraintRequest request) {

        TimetableConstraint constraint = new TimetableConstraint();

        constraint.setConstraintName(request.getConstraintName());
        constraint.setConstraintKey(request.getConstraintKey());
        constraint.setConstraintValue(request.getConstraintValue());
        constraint.setDescription(request.getDescription());
        constraint.setActive(request.getActive());

        return constraint;
    }

    /**
     * Update Existing Entity
     */
    public static void updateEntity(
            TimetableConstraint constraint,
            TimetableConstraintRequest request) {

        constraint.setConstraintName(request.getConstraintName());
        constraint.setConstraintKey(request.getConstraintKey());
        constraint.setConstraintValue(request.getConstraintValue());
        constraint.setDescription(request.getDescription());
        constraint.setActive(request.getActive());
    }

    /**
     * Entity -> Response DTO
     */
    public static TimetableConstraintResponse toResponse(
            TimetableConstraint constraint) {

        TimetableConstraintResponse response =
                new TimetableConstraintResponse();

        response.setConstraintId(constraint.getConstraintId());
        response.setConstraintName(constraint.getConstraintName());
        response.setConstraintKey(constraint.getConstraintKey());
        response.setConstraintValue(constraint.getConstraintValue());
        response.setDescription(constraint.getDescription());
        response.setActive(constraint.getActive());

        return response;
    }
}