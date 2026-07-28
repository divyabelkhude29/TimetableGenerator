package com.timetable.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.FacultyAvailabilityDAO;
import com.timetable.entity.Faculty;
import com.timetable.entity.FacultyAvailability;
import com.timetable.entity.TimeSlot;

@Repository
@Transactional
public class FacultyAvailabilityDAOImpl implements FacultyAvailabilityDAO {

    private final SessionFactory sessionFactory;

    public FacultyAvailabilityDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get Current Session
     */
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Save Faculty Availability
     */
    @Override
    public FacultyAvailability save(FacultyAvailability availability) {

        getCurrentSession().persist(availability);

        return availability;
    }

    /**
     * Update Faculty Availability
     */
    @Override
    public FacultyAvailability update(FacultyAvailability availability) {

        return (FacultyAvailability) getCurrentSession().merge(availability);
    }

    /**
     * Delete Faculty Availability
     */
    @Override
    public void delete(Long availabilityId) {

        FacultyAvailability availability = findById(availabilityId);

        if (availability != null) {
            getCurrentSession().remove(availability);
        }
    }

    /**
     * Find By ID
     */
    @Override
    public FacultyAvailability findById(Long availabilityId) {

        return getCurrentSession().get(
                FacultyAvailability.class,
                availabilityId);
    }

    /**
     * Find All
     */
    @Override
    public List<FacultyAvailability> findAll() {

        return getCurrentSession()
                .createQuery(
                        "FROM FacultyAvailability fa ORDER BY fa.availabilityId",
                        FacultyAvailability.class)
                .getResultList();
    }

    /**
     * Find By Faculty
     */
    @Override
    public List<FacultyAvailability> findByFaculty(Faculty faculty) {

        return getCurrentSession()
                .createQuery(
                        "FROM FacultyAvailability fa WHERE fa.faculty = :faculty ORDER BY fa.dayOfWeek, fa.timeSlot.startTime",
                        FacultyAvailability.class)
                .setParameter("faculty", faculty)
                .getResultList();
    }

    /**
     * Find By Day
     */
    @Override
    public List<FacultyAvailability> findByDay(String dayOfWeek) {

        return getCurrentSession()
                .createQuery(
                        "FROM FacultyAvailability fa WHERE fa.dayOfWeek = :day ORDER BY fa.timeSlot.startTime",
                        FacultyAvailability.class)
                .setParameter("day", dayOfWeek)
                .getResultList();
    }

    /**
     * Find By Faculty And Day
     */
    @Override
    public List<FacultyAvailability> findByFacultyAndDay(
            Faculty faculty,
            String dayOfWeek) {

        return getCurrentSession()
                .createQuery(
                        "FROM FacultyAvailability fa WHERE fa.faculty = :faculty AND fa.dayOfWeek = :day ORDER BY fa.timeSlot.startTime",
                        FacultyAvailability.class)
                .setParameter("faculty", faculty)
                .setParameter("day", dayOfWeek)
                .getResultList();
    }

    /**
     * Find By Time Slot
     */
    @Override
    public List<FacultyAvailability> findByTimeSlot(TimeSlot timeSlot) {

        return getCurrentSession()
                .createQuery(
                        "FROM FacultyAvailability fa WHERE fa.timeSlot = :timeSlot ORDER BY fa.dayOfWeek",
                        FacultyAvailability.class)
                .setParameter("timeSlot", timeSlot)
                .getResultList();
    }

    /**
     * Find By Faculty, Day And Time Slot
     */
    @Override
    public FacultyAvailability findByFacultyAndDayAndTimeSlot(
            Faculty faculty,
            String dayOfWeek,
            TimeSlot timeSlot) {

        return getCurrentSession()
                .createQuery(
                        "FROM FacultyAvailability fa WHERE fa.faculty = :faculty AND fa.dayOfWeek = :day AND fa.timeSlot = :timeSlot",
                        FacultyAvailability.class)
                .setParameter("faculty", faculty)
                .setParameter("day", dayOfWeek)
                .setParameter("timeSlot", timeSlot)
                .uniqueResult();
    }

    /**
     * Check Duplicate Faculty Availability
     */
    @Override
    public boolean existsByFacultyAndDayAndTimeSlot(
            Faculty faculty,
            String dayOfWeek,
            TimeSlot timeSlot) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(fa) FROM FacultyAvailability fa " +
                        "WHERE fa.faculty = :faculty " +
                        "AND fa.dayOfWeek = :day " +
                        "AND fa.timeSlot = :timeSlot",
                        Long.class)
                .setParameter("faculty", faculty)
                .setParameter("day", dayOfWeek)
                .setParameter("timeSlot", timeSlot)
                .uniqueResult();

        return count != null && count > 0;
    }
}