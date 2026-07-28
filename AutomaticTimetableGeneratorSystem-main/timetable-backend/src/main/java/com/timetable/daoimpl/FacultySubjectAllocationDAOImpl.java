package com.timetable.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.FacultySubjectAllocationDAO;
import com.timetable.entity.Faculty;
import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.Section;
import com.timetable.entity.Semester;
import com.timetable.entity.Subject;

@Repository
@Transactional
public class FacultySubjectAllocationDAOImpl implements FacultySubjectAllocationDAO {

    private final SessionFactory sessionFactory;

    public FacultySubjectAllocationDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get Current Hibernate Session
     */
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Save Allocation
     */
    @Override
    public FacultySubjectAllocation save(FacultySubjectAllocation allocation) {

        getCurrentSession().persist(allocation);

        return allocation;
    }

    /**
     * Update Allocation
     */
    @Override
    public FacultySubjectAllocation update(FacultySubjectAllocation allocation) {

        return (FacultySubjectAllocation) getCurrentSession().merge(allocation);
    }

    /**
     * Delete Allocation
     */
    @Override
    public void delete(Long allocationId) {

        FacultySubjectAllocation allocation = findById(allocationId);

        if (allocation != null) {
            getCurrentSession().remove(allocation);
        }
    }

    /**
     * Find Allocation By ID
     */
    @Override
    public FacultySubjectAllocation findById(Long allocationId) {

        return getCurrentSession().get(FacultySubjectAllocation.class, allocationId);
    }

    /**
     * Find All Allocations
     */
    @Override
    public List<FacultySubjectAllocation> findAll() {

        return getCurrentSession()
                .createQuery(
                        "FROM FacultySubjectAllocation fsa ORDER BY fsa.allocationId",
                        FacultySubjectAllocation.class)
                .getResultList();
    }

    /**
     * Find Allocations By Faculty
     */
    @Override
    public List<FacultySubjectAllocation> findByFaculty(Faculty faculty) {

        return getCurrentSession()
                .createQuery(
                        "FROM FacultySubjectAllocation fsa WHERE fsa.faculty = :faculty ORDER BY fsa.allocationId",
                        FacultySubjectAllocation.class)
                .setParameter("faculty", faculty)
                .getResultList();
    }

    /**
     * Find Allocations By Subject
     */
    @Override
    public List<FacultySubjectAllocation> findBySubject(Subject subject) {

        return getCurrentSession()
                .createQuery(
                        "FROM FacultySubjectAllocation fsa WHERE fsa.subject = :subject ORDER BY fsa.allocationId",
                        FacultySubjectAllocation.class)
                .setParameter("subject", subject)
                .getResultList();
    }

    /**
     * Find Allocations By Section
     */
    @Override
    public List<FacultySubjectAllocation> findBySection(Section section) {

        return getCurrentSession()
                .createQuery(
                        "FROM FacultySubjectAllocation fsa WHERE fsa.section = :section ORDER BY fsa.allocationId",
                        FacultySubjectAllocation.class)
                .setParameter("section", section)
                .getResultList();
    }

    /**
     * Find Allocations By Semester
     */
    @Override
    public List<FacultySubjectAllocation> findBySemester(Semester semester) {

        return getCurrentSession()
                .createQuery(
                        "FROM FacultySubjectAllocation fsa WHERE fsa.semester = :semester ORDER BY fsa.allocationId",
                        FacultySubjectAllocation.class)
                .setParameter("semester", semester)
                .getResultList();
    }

    /**
     * Check Duplicate Allocation
     */
    @Override
    public boolean existsAllocation(Faculty faculty,
                                    Subject subject,
                                    Section section,
                                    Semester semester,
                                    String academicYear) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(fsa) " +
                        "FROM FacultySubjectAllocation fsa " +
                        "WHERE fsa.faculty = :faculty " +
                        "AND fsa.subject = :subject " +
                        "AND fsa.section = :section " +
                        "AND fsa.semester = :semester " +
                        "AND fsa.academicYear = :academicYear",
                        Long.class)
                .setParameter("faculty", faculty)
                .setParameter("subject", subject)
                .setParameter("section", section)
                .setParameter("semester", semester)
                .setParameter("academicYear", academicYear)
                .uniqueResult();

        return count != null && count > 0;
    }
}