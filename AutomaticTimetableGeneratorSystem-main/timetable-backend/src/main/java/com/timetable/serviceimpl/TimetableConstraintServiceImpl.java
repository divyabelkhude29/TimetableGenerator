package com.timetable.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.TimetableConstraintDAO;
import com.timetable.dto.timetableconstraint.TimetableConstraintRequest;
import com.timetable.dto.timetableconstraint.TimetableConstraintResponse;
import com.timetable.entity.TimetableConstraint;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.mapper.TimetableConstraintMapper;
import com.timetable.service.TimetableConstraintService;

@Service
@Transactional
public class TimetableConstraintServiceImpl
        implements TimetableConstraintService {

    private final TimetableConstraintDAO timetableConstraintDAO;

    public TimetableConstraintServiceImpl(
            TimetableConstraintDAO timetableConstraintDAO) {

        this.timetableConstraintDAO = timetableConstraintDAO;
    }

    /**
     * Save Timetable Constraint
     */
    @Override
    public TimetableConstraintResponse saveConstraint(
            TimetableConstraintRequest request) {

        if (timetableConstraintDAO.existsByConstraintKey(
                request.getConstraintKey())) {

            throw new IllegalArgumentException(
                    "Constraint already exists with key : "
                            + request.getConstraintKey());
        }

        TimetableConstraint constraint =
                TimetableConstraintMapper.toEntity(request);

        constraint = timetableConstraintDAO.save(constraint);

        return TimetableConstraintMapper.toResponse(constraint);
    }

    /**
     * Update Timetable Constraint
     */
    @Override
    public TimetableConstraintResponse updateConstraint(
            Long constraintId,
            TimetableConstraintRequest request) {

        TimetableConstraint constraint =
                timetableConstraintDAO.findById(constraintId);

        if (constraint == null) {

            throw new ResourceNotFoundException(
                    "Timetable Constraint not found with ID : "
                            + constraintId);
        }

        if (timetableConstraintDAO
                .existsByConstraintKeyAndNotConstraintId(
                        request.getConstraintKey(),
                        constraintId)) {

            throw new IllegalArgumentException(
                    "Another constraint already exists with key : "
                            + request.getConstraintKey());
        }

        TimetableConstraintMapper.updateEntity(
                constraint,
                request);

        constraint =
                timetableConstraintDAO.update(constraint);

        return TimetableConstraintMapper.toResponse(
                constraint);
    }

    /**
     * Delete Timetable Constraint
     */
    @Override
    public void deleteConstraint(Long constraintId) {

        TimetableConstraint constraint =
                timetableConstraintDAO.findById(constraintId);

        if (constraint == null) {

            throw new ResourceNotFoundException(
                    "Timetable Constraint not found with ID : "
                            + constraintId);
        }

        timetableConstraintDAO.delete(constraintId);
    }

    /**
     * Get Timetable Constraint By ID
     */
    @Override
    @Transactional(readOnly = true)
    public TimetableConstraintResponse getConstraintById(
            Long constraintId) {

        TimetableConstraint constraint =
                timetableConstraintDAO.findById(constraintId);

        if (constraint == null) {

            throw new ResourceNotFoundException(
                    "Timetable Constraint not found with ID : "
                            + constraintId);
        }

        return TimetableConstraintMapper.toResponse(
                constraint);
    }

    /**
     * Get All Timetable Constraints
     */
    @Override
    @Transactional(readOnly = true)
    public List<TimetableConstraintResponse>
            getAllConstraints() {

        return timetableConstraintDAO.findAll()
                .stream()
                .map(TimetableConstraintMapper::toResponse)
                .collect(Collectors.toList());
    }
    /**
     * Get Timetable Constraint By Key
     */
    @Override
    @Transactional(readOnly = true)
    public TimetableConstraintResponse getConstraintByKey(
            String constraintKey) {

        TimetableConstraint constraint =
                timetableConstraintDAO.findByConstraintKey(
                        constraintKey);

        if (constraint == null) {

            throw new ResourceNotFoundException(
                    "Timetable Constraint not found with key : "
                            + constraintKey);
        }

        return TimetableConstraintMapper.toResponse(
                constraint);
    }

    /**
     * Get Constraint Value
     */
    @Override
    @Transactional(readOnly = true)
    public String getConstraintValue(
            String constraintKey) {

        String value =
                timetableConstraintDAO.getConstraintValue(
                        constraintKey);

        if (value == null) {

            throw new ResourceNotFoundException(
                    "Active constraint not found with key : "
                            + constraintKey);
        }

        return value;
    }

    /**
     * Check Whether Constraint Is Active
     */
    @Override
    @Transactional(readOnly = true)
    public boolean isConstraintActive(
            String constraintKey) {

        return timetableConstraintDAO.isConstraintActive(
                constraintKey);
    }
}