package com.timetable.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.TimetableDAO;
import com.timetable.entity.Classroom;
import com.timetable.entity.Faculty;
import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.Section;
import com.timetable.entity.TimeSlot;
import com.timetable.entity.Timetable;

@Repository
@Transactional
public class TimetableDAOImpl implements TimetableDAO {

    private final SessionFactory sessionFactory;

    public TimetableDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get Current Session
     */
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Save Timetable
     */
    @Override
    public Timetable save(Timetable timetable) {

        getCurrentSession().persist(timetable);

        return timetable;
    }

    /**
     * Update Timetable
     */
    @Override
    public Timetable update(Timetable timetable) {

        return (Timetable) getCurrentSession().merge(timetable);
    }

    /**
     * Delete Timetable
     */
    @Override
    public void delete(Long timetableId) {

        Timetable timetable = findById(timetableId);

        if (timetable != null) {
            getCurrentSession().remove(timetable);
        }
    }

    /**
     * Find Timetable By ID
     */
    @Override
    public Timetable findById(Long timetableId) {

        return getCurrentSession().get(Timetable.class, timetableId);
    }

    /**
     * Find All Timetables
     */
    @Override
    public List<Timetable> findAll() {

        return getCurrentSession()
                .createQuery(
                        "FROM Timetable t ORDER BY t.timetableId",
                        Timetable.class)
                .getResultList();
    }

    /**
     * Find By Day
     */
    @Override
    public List<Timetable> findByDay(String dayOfWeek) {

        return getCurrentSession()
                .createQuery(
                        "FROM Timetable t WHERE t.dayOfWeek = :day ORDER BY t.timeSlot.startTime",
                        Timetable.class)
                .setParameter("day", dayOfWeek)
                .getResultList();
    }

    /**
     * Find By Classroom
     */
    @Override
    public List<Timetable> findByClassroom(Classroom classroom) {

        return getCurrentSession()
                .createQuery(
                        "FROM Timetable t WHERE t.classroom = :classroom ORDER BY t.dayOfWeek, t.timeSlot.startTime",
                        Timetable.class)
                .setParameter("classroom", classroom)
                .getResultList();
    }

    /**
     * Find By Faculty
     */
    @Override
    public List<Timetable> findByFaculty(Faculty faculty) {

        return getCurrentSession()
                .createQuery(
                        "FROM Timetable t WHERE t.allocation.faculty = :faculty ORDER BY t.dayOfWeek, t.timeSlot.startTime",
                        Timetable.class)
                .setParameter("faculty", faculty)
                .getResultList();
    }

    /**
     * Find By Section
     */
    @Override
    public List<Timetable> findBySection(Section section) {

        return getCurrentSession()
                .createQuery(
                        "FROM Timetable t WHERE t.allocation.section = :section ORDER BY t.dayOfWeek, t.timeSlot.startTime",
                        Timetable.class)
                .setParameter("section", section)
                .getResultList();
    }

    /**
     * Find By Allocation
     */
    @Override
    public List<Timetable> findByAllocation(FacultySubjectAllocation allocation) {

        return getCurrentSession()
                .createQuery(
                        "FROM Timetable t WHERE t.allocation = :allocation ORDER BY t.dayOfWeek, t.timeSlot.startTime",
                        Timetable.class)
                .setParameter("allocation", allocation)
                .getResultList();
    }

    /**
     * Faculty Clash
     */
    @Override
    public boolean existsFacultyClash(
            Faculty faculty,
            String dayOfWeek,
            TimeSlot timeSlot) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(t) " +
                        "FROM Timetable t " +
                        "WHERE t.allocation.faculty = :faculty " +
                        "AND t.dayOfWeek = :day " +
                        "AND t.timeSlot = :timeSlot",
                        Long.class)
                .setParameter("faculty", faculty)
                .setParameter("day", dayOfWeek)
                .setParameter("timeSlot", timeSlot)
                .uniqueResult();

        return count != null && count > 0;
    }

    /**
     * Classroom Clash
     */
    @Override
    public boolean existsClassroomClash(
            Classroom classroom,
            String dayOfWeek,
            TimeSlot timeSlot) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(t) " +
                        "FROM Timetable t " +
                        "WHERE t.classroom = :classroom " +
                        "AND t.dayOfWeek = :day " +
                        "AND t.timeSlot = :timeSlot",
                        Long.class)
                .setParameter("classroom", classroom)
                .setParameter("day", dayOfWeek)
                .setParameter("timeSlot", timeSlot)
                .uniqueResult();

        return count != null && count > 0;
    }

    /**
     * Section Clash
     */
    @Override
    public boolean existsSectionClash(
            Section section,
            String dayOfWeek,
            TimeSlot timeSlot) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(t) " +
                        "FROM Timetable t " +
                        "WHERE t.allocation.section = :section " +
                        "AND t.dayOfWeek = :day " +
                        "AND t.timeSlot = :timeSlot",
                        Long.class)
                .setParameter("section", section)
                .setParameter("day", dayOfWeek)
                .setParameter("timeSlot", timeSlot)
                .uniqueResult();

        return count != null && count > 0;
    }
}