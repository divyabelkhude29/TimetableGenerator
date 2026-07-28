package com.timetable.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.SubjectWorkloadDAO;
import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.SubjectWorkload;

@Repository
@Transactional
public class SubjectWorkloadDAOImpl implements SubjectWorkloadDAO {

    private final SessionFactory sessionFactory;

    public SubjectWorkloadDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get Current Session
     */
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Save Subject Workload
     */
    @Override
    public SubjectWorkload save(SubjectWorkload workload) {

        getCurrentSession().persist(workload);

        return workload;
    }

    /**
     * Update Subject Workload
     */
    @Override
    public SubjectWorkload update(SubjectWorkload workload) {

        return (SubjectWorkload) getCurrentSession().merge(workload);
    }

    /**
     * Delete Subject Workload
     */
    @Override
    public void delete(Long workloadId) {

        SubjectWorkload workload = findById(workloadId);

        if (workload != null) {
            getCurrentSession().remove(workload);
        }
    }

    /**
     * Find By ID
     */
    @Override
    public SubjectWorkload findById(Long workloadId) {

        return getCurrentSession().get(
                SubjectWorkload.class,
                workloadId);
    }

    /**
     * Find All
     */
    @Override
    public List<SubjectWorkload> findAll() {

        return getCurrentSession()
                .createQuery(
                        "FROM SubjectWorkload sw ORDER BY sw.workloadId",
                        SubjectWorkload.class)
                .getResultList();
    }

    /**
     * Find By Allocation
     */
    @Override
    public SubjectWorkload findByAllocation(
            FacultySubjectAllocation allocation) {

        return getCurrentSession()
                .createQuery(
                        "FROM SubjectWorkload sw WHERE sw.allocation = :allocation",
                        SubjectWorkload.class)
                .setParameter("allocation", allocation)
                .uniqueResult();
    }

    /**
     * Check Workload Exists For Allocation
     */
    @Override
    public boolean existsByAllocation(
            FacultySubjectAllocation allocation) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(sw) FROM SubjectWorkload sw " +
                        "WHERE sw.allocation = :allocation",
                        Long.class)
                .setParameter("allocation", allocation)
                .uniqueResult();

        return count != null && count > 0;
    }

    /**
     * Check Duplicate Allocation During Update
     */
    @Override
    public boolean existsByAllocationAndNotWorkloadId(
            FacultySubjectAllocation allocation,
            Long workloadId) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(sw) FROM SubjectWorkload sw " +
                        "WHERE sw.allocation = :allocation " +
                        "AND sw.workloadId <> :workloadId",
                        Long.class)
                .setParameter("allocation", allocation)
                .setParameter("workloadId", workloadId)
                .uniqueResult();

        return count != null && count > 0;
    }
}