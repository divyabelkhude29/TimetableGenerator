package com.timetable.daoimpl;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.HolidayDAO;
import com.timetable.entity.Holiday;

@Repository
@Transactional
public class HolidayDAOImpl implements HolidayDAO {

    private final SessionFactory sessionFactory;

    public HolidayDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get Current Session
     */
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Save Holiday
     */
    @Override
    public Holiday save(Holiday holiday) {

        getCurrentSession().persist(holiday);

        return holiday;
    }

    /**
     * Update Holiday
     */
    @Override
    public Holiday update(Holiday holiday) {

        return (Holiday) getCurrentSession().merge(holiday);
    }

    /**
     * Delete Holiday
     */
    @Override
    public void delete(Long holidayId) {

        Holiday holiday = findById(holidayId);

        if (holiday != null) {
            getCurrentSession().remove(holiday);
        }
    }

    /**
     * Find Holiday By ID
     */
    @Override
    public Holiday findById(Long holidayId) {

        return getCurrentSession().get(
                Holiday.class,
                holidayId);
    }

    /**
     * Find All Holidays
     */
    @Override
    public List<Holiday> findAll() {

        return getCurrentSession()
                .createQuery(
                        "FROM Holiday h ORDER BY h.holidayDate",
                        Holiday.class)
                .getResultList();
    }

    /**
     * Find Holiday By Date
     */
    @Override
    public Holiday findByHolidayDate(LocalDate holidayDate) {

        return getCurrentSession()
                .createQuery(
                        "FROM Holiday h WHERE h.holidayDate = :holidayDate",
                        Holiday.class)
                .setParameter("holidayDate", holidayDate)
                .uniqueResult();
    }

    /**
     * Check Holiday Exists By Date
     */
    @Override
    public boolean existsByHolidayDate(LocalDate holidayDate) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(h) FROM Holiday h " +
                        "WHERE h.holidayDate = :holidayDate",
                        Long.class)
                .setParameter("holidayDate", holidayDate)
                .uniqueResult();

        return count != null && count > 0;
    }

    /**
     * Check Duplicate Holiday Date During Update
     */
    @Override
    public boolean existsByHolidayDateAndNotHolidayId(
            LocalDate holidayDate,
            Long holidayId) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(h) FROM Holiday h " +
                        "WHERE h.holidayDate = :holidayDate " +
                        "AND h.holidayId <> :holidayId",
                        Long.class)
                .setParameter("holidayDate", holidayDate)
                .setParameter("holidayId", holidayId)
                .uniqueResult();

        return count != null && count > 0;
    }

    /**
     * Check Whether Date Is Holiday
     */
    @Override
    public boolean isHoliday(LocalDate holidayDate) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(h) FROM Holiday h " +
                        "WHERE h.holidayDate = :holidayDate " +
                        "AND h.active = true",
                        Long.class)
                .setParameter("holidayDate", holidayDate)
                .uniqueResult();

        return count != null && count > 0;
    }
}