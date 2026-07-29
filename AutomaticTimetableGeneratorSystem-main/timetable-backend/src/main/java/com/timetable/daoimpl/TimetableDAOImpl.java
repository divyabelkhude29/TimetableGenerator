package com.timetable.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.timetable.dao.TimetableDAO;
import com.timetable.entity.Classroom;
import com.timetable.entity.Faculty;
import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.Section;
import com.timetable.entity.TimeSlot;
import com.timetable.entity.Timetable;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class TimetableDAOImpl implements TimetableDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save Timetable
     */
    @Override
    public Timetable save(Timetable timetable) {

        entityManager.persist(timetable);

        return timetable;
    }

    /**
     * Update Timetable
     */
    @Override
    public Timetable update(Timetable timetable) {

        return entityManager.merge(timetable);
    }

    /**
     * Delete Timetable
     */
    @Override
    public void delete(Long timetableId) {

        Timetable timetable = findById(timetableId);

        if (timetable != null) {
            entityManager.remove(timetable);
        }
    }

    /**
     * Find By ID
     */
    @Override
    public Timetable findById(Long timetableId) {

        return entityManager.find(Timetable.class, timetableId);
    }

    /**
     * Find All
     */
    @Override
    public List<Timetable> findAll() {

        TypedQuery<Timetable> query =
                entityManager.createQuery(
                        "FROM Timetable t ORDER BY t.dayOfWeek, t.timeSlot.slotOrder",
                        Timetable.class);

        return query.getResultList();
    }

    /**
     * Find By Day
     */
    @Override
    public List<Timetable> findByDay(String dayOfWeek) {

        return entityManager.createQuery(
                "FROM Timetable t WHERE t.dayOfWeek = :day",
                Timetable.class)
                .setParameter("day", dayOfWeek)
                .getResultList();
    }

    /**
     * Find By Classroom
     */
    @Override
    public List<Timetable> findByClassroom(Classroom classroom) {

        return entityManager.createQuery(
                "FROM Timetable t WHERE t.classroom = :classroom",
                Timetable.class)
                .setParameter("classroom", classroom)
                .getResultList();
    }

    /**
     * Find By Faculty
     */
    @Override
    public List<Timetable> findByFaculty(Faculty faculty) {

        return entityManager.createQuery(
                "FROM Timetable t WHERE t.allocation.faculty = :faculty",
                Timetable.class)
                .setParameter("faculty", faculty)
                .getResultList();
    }

    /**
     * Find By Section
     */
    @Override
    public List<Timetable> findBySection(Section section) {

        return entityManager.createQuery(
                "FROM Timetable t WHERE t.allocation.section = :section",
                Timetable.class)
                .setParameter("section", section)
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

        Long count = entityManager.createQuery(
                """
                SELECT COUNT(t)
                FROM Timetable t
                WHERE t.allocation.faculty = :faculty
                AND t.dayOfWeek = :day
                AND t.timeSlot = :slot
                """,
                Long.class)
                .setParameter("faculty", faculty)
                .setParameter("day", dayOfWeek)
                .setParameter("slot", timeSlot)
                .getSingleResult();

        return count > 0;
    }

    /**
     * Classroom Clash
     */
    @Override
    public boolean existsClassroomClash(
            Classroom classroom,
            String dayOfWeek,
            TimeSlot timeSlot) {

        Long count = entityManager.createQuery(
                """
                SELECT COUNT(t)
                FROM Timetable t
                WHERE t.classroom = :classroom
                AND t.dayOfWeek = :day
                AND t.timeSlot = :slot
                """,
                Long.class)
                .setParameter("classroom", classroom)
                .setParameter("day", dayOfWeek)
                .setParameter("slot", timeSlot)
                .getSingleResult();

        return count > 0;
    }

    /**
     * Section Clash
     */
    @Override
    public boolean existsSectionClash(
            Section section,
            String dayOfWeek,
            TimeSlot timeSlot) {

        Long count = entityManager.createQuery(
                """
                SELECT COUNT(t)
                FROM Timetable t
                WHERE t.allocation.section = :section
                AND t.dayOfWeek = :day
                AND t.timeSlot = :slot
                """,
                Long.class)
                .setParameter("section", section)
                .setParameter("day", dayOfWeek)
                .setParameter("slot", timeSlot)
                .getSingleResult();

        return count > 0;
    }

    /**
     * Find By Allocation
     */
    @Override
    public List<Timetable> findByAllocation(
            FacultySubjectAllocation allocation) {

        return entityManager.createQuery(
                "FROM Timetable t WHERE t.allocation = :allocation",
                Timetable.class)
                .setParameter("allocation", allocation)
                .getResultList();
    }

    /**
     * Delete Generated Timetable
     */
    @Override
    public void deleteGeneratedTimetable(
            String academicYear,
            Long semesterId,
            Long sectionId) {

        entityManager.createQuery(
                """
                DELETE FROM Timetable t
                WHERE t.allocation.section.sectionId = :sectionId
                AND t.allocation.semester.semesterId = :semesterId
                AND t.allocation.academicYear = :academicYear
                """)
                .setParameter("sectionId", sectionId)
                .setParameter("semesterId", semesterId)
                .setParameter("academicYear", academicYear)
                .executeUpdate();
    }

    /**
     * Save All
     */
    @Override
    public void saveAll(List<Timetable> timetables) {

        for (int i = 0; i < timetables.size(); i++) {

            entityManager.persist(timetables.get(i));

            if (i % 25 == 0) {
                entityManager.flush();
                entityManager.clear();
            }
        }
    }

    /**
     * Find Generated Timetable
     */
    @Override
    public List<Timetable> findBySectionSemesterAndAcademicYear(
            Long sectionId,
            Long semesterId,
            String academicYear) {

        return entityManager.createQuery(
                """
                FROM Timetable t
                WHERE t.allocation.section.sectionId = :sectionId
                AND t.allocation.semester.semesterId = :semesterId
                AND t.allocation.academicYear = :academicYear
                ORDER BY t.dayOfWeek, t.timeSlot.slotOrder
                """,
                Timetable.class)
                .setParameter("sectionId", sectionId)
                .setParameter("semesterId", semesterId)
                .setParameter("academicYear", academicYear)
                .getResultList();
    }

    /**
     * Delete Generated Timetable
     */
    @Override
    public void deleteBySectionSemesterAndAcademicYear(
            Long sectionId,
            Long semesterId,
            String academicYear) {

        entityManager.createQuery(
                """
                DELETE FROM Timetable t
                WHERE t.allocation.section.sectionId = :sectionId
                AND t.allocation.semester.semesterId = :semesterId
                AND t.allocation.academicYear = :academicYear
                """)
                .setParameter("sectionId", sectionId)
                .setParameter("semesterId", semesterId)
                .setParameter("academicYear", academicYear)
                .executeUpdate();
    }

}