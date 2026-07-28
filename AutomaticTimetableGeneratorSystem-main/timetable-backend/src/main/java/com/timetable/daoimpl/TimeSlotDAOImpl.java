package com.timetable.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.TimeSlotDAO;
import com.timetable.entity.TimeSlot;

@Repository
@Transactional
public class TimeSlotDAOImpl implements TimeSlotDAO {

    private final SessionFactory sessionFactory;

    public TimeSlotDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get Current Hibernate Session
     */
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Save Time Slot
     */
    @Override
    public TimeSlot save(TimeSlot timeSlot) {

        getCurrentSession().persist(timeSlot);

        return timeSlot;
    }

    /**
     * Update Time Slot
     */
    @Override
    public TimeSlot update(TimeSlot timeSlot) {

        return (TimeSlot) getCurrentSession().merge(timeSlot);
    }

    /**
     * Delete Time Slot
     */
    @Override
    public void delete(Long timeSlotId) {

        TimeSlot timeSlot = findById(timeSlotId);

        if (timeSlot != null) {
            getCurrentSession().remove(timeSlot);
        }
    }

    /**
     * Find Time Slot By ID
     */
    @Override
    public TimeSlot findById(Long timeSlotId) {

        return getCurrentSession().get(TimeSlot.class, timeSlotId);
    }

    /**
     * Find All Time Slots
     */
    @Override
    public List<TimeSlot> findAll() {

        return getCurrentSession()
                .createQuery(
                        "FROM TimeSlot t ORDER BY t.dayOfWeek, t.slotOrder",
                        TimeSlot.class)
                .getResultList();
    }

    /**
     * Find Time Slots By Day Of Week
     */
    @Override
    public List<TimeSlot> findByDayOfWeek(String dayOfWeek) {

        return getCurrentSession()
                .createQuery(
                        "FROM TimeSlot t WHERE t.dayOfWeek = :dayOfWeek ORDER BY t.slotOrder",
                        TimeSlot.class)
                .setParameter("dayOfWeek", dayOfWeek)
                .getResultList();
    }

    /**
     * Find Time Slot By Day Of Week And Slot Order
     */
    @Override
    public TimeSlot findByDayOfWeekAndSlotOrder(String dayOfWeek, Integer slotOrder) {

        List<TimeSlot> slots = getCurrentSession()
                .createQuery(
                        "FROM TimeSlot t WHERE t.dayOfWeek = :dayOfWeek AND t.slotOrder = :slotOrder",
                        TimeSlot.class)
                .setParameter("dayOfWeek", dayOfWeek)
                .setParameter("slotOrder", slotOrder)
                .getResultList();

        return slots.isEmpty() ? null : slots.get(0);
    }

    /**
     * Find Time Slots By Slot Order
     */
    @Override
    public List<TimeSlot> findBySlotOrder(Integer slotOrder) {

        return getCurrentSession()
                .createQuery(
                        "FROM TimeSlot t WHERE t.slotOrder = :slotOrder ORDER BY t.dayOfWeek",
                        TimeSlot.class)
                .setParameter("slotOrder", slotOrder)
                .getResultList();
    }

    /**
     * Check Time Slot Exists
     */
    @Override
    public boolean existsByDayOfWeekAndSlotOrder(String dayOfWeek, Integer slotOrder) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(t) FROM TimeSlot t WHERE t.dayOfWeek = :dayOfWeek AND t.slotOrder = :slotOrder",
                        Long.class)
                .setParameter("dayOfWeek", dayOfWeek)
                .setParameter("slotOrder", slotOrder)
                .uniqueResult();

        return count != null && count > 0;
    }
}