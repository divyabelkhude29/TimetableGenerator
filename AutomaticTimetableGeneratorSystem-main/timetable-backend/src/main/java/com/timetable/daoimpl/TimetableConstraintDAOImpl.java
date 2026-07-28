package com.timetable.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.TimetableConstraintDAO;
import com.timetable.entity.TimetableConstraint;

@Repository
@Transactional
public class TimetableConstraintDAOImpl
        implements TimetableConstraintDAO {

    private final SessionFactory sessionFactory;

    public TimetableConstraintDAOImpl(
            SessionFactory sessionFactory) {

        this.sessionFactory = sessionFactory;
    }

    /**
     * Get Current Session
     */
    private Session getCurrentSession() {

        return sessionFactory.getCurrentSession();
    }

    /**
     * Save Timetable Constraint
     */
    @Override
    public TimetableConstraint save(
            TimetableConstraint timetableConstraint) {

        getCurrentSession().persist(timetableConstraint);

        return timetableConstraint;
    }

    /**
     * Update Timetable Constraint
     */
    @Override
    public TimetableConstraint update(
            TimetableConstraint timetableConstraint) {

        return (TimetableConstraint) getCurrentSession()
                .merge(timetableConstraint);
    }

    /**
     * Delete Timetable Constraint
     */
    @Override
    public void delete(Long constraintId) {

        TimetableConstraint constraint =
                findById(constraintId);

        if (constraint != null) {
            getCurrentSession().remove(constraint);
        }
    }

    /**
     * Find Timetable Constraint By ID
     */
    @Override
    public TimetableConstraint findById(Long constraintId) {

        return getCurrentSession().get(
                TimetableConstraint.class,
                constraintId);
    }

    /**
     * Find All Timetable Constraints
     */
    @Override
    public List<TimetableConstraint> findAll() {

        return getCurrentSession()
                .createQuery(
                        "FROM TimetableConstraint tc ORDER BY tc.constraintName",
                        TimetableConstraint.class)
                .getResultList();
    }

    /**
     * Find Timetable Constraint By Key
     */
    @Override
    public TimetableConstraint findByConstraintKey(
            String constraintKey) {

        return getCurrentSession()
                .createQuery(
                        "FROM TimetableConstraint tc " +
                        "WHERE tc.constraintKey = :constraintKey",
                        TimetableConstraint.class)
                .setParameter("constraintKey", constraintKey)
                .uniqueResult();
    }

    /**
     * Check Constraint Key Exists
     */
    @Override
    public boolean existsByConstraintKey(
            String constraintKey) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(tc) FROM TimetableConstraint tc " +
                        "WHERE tc.constraintKey = :constraintKey",
                        Long.class)
                .setParameter("constraintKey", constraintKey)
                .uniqueResult();

        return count != null && count > 0;
    }

    /**
     * Check Duplicate Constraint Key During Update
     */
    @Override
    public boolean existsByConstraintKeyAndNotConstraintId(
            String constraintKey,
            Long constraintId) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(tc) FROM TimetableConstraint tc " +
                        "WHERE tc.constraintKey = :constraintKey " +
                        "AND tc.constraintId <> :constraintId",
                        Long.class)
                .setParameter("constraintKey", constraintKey)
                .setParameter("constraintId", constraintId)
                .uniqueResult();

        return count != null && count > 0;
    }

    /**
     * Get Constraint Value
     */
    @Override
    public String getConstraintValue(
            String constraintKey) {

        return getCurrentSession()
                .createQuery(
                        "SELECT tc.constraintValue " +
                        "FROM TimetableConstraint tc " +
                        "WHERE tc.constraintKey = :constraintKey " +
                        "AND tc.active = true",
                        String.class)
                .setParameter("constraintKey", constraintKey)
                .uniqueResult();
    }

    /**
     * Check Whether Constraint Is Active
     */
    @Override
    public boolean isConstraintActive(
            String constraintKey) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(tc) FROM TimetableConstraint tc " +
                        "WHERE tc.constraintKey = :constraintKey " +
                        "AND tc.active = true",
                        Long.class)
                .setParameter("constraintKey", constraintKey)
                .uniqueResult();

        return count != null && count > 0;
    }
}