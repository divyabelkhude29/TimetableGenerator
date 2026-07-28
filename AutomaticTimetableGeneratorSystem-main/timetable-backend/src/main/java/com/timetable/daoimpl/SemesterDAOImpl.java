package com.timetable.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.timetable.dao.SemesterDAO;
import com.timetable.entity.Course;
import com.timetable.entity.Semester;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class SemesterDAOImpl implements SemesterDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save Semester
     */
    @Override
    public Semester save(Semester semester) {

        entityManager.persist(semester);

        return semester;
    }

    /**
     * Update Semester
     */
    @Override
    public Semester update(Semester semester) {

        return entityManager.merge(semester);
    }

    /**
     * Delete Semester
     */
    @Override
    public void delete(Long semesterId) {

        Semester semester = findById(semesterId);

        if (semester != null) {
            entityManager.remove(semester);
        }
    }

    /**
     * Find Semester By ID
     */
    @Override
    public Semester findById(Long semesterId) {

        return entityManager.find(Semester.class, semesterId);
    }

    /**
     * Find All Semesters
     */
    @Override
    public List<Semester> findAll() {

        TypedQuery<Semester> query =
                entityManager.createQuery(
                        "FROM Semester s ORDER BY s.semesterNumber",
                        Semester.class);

        return query.getResultList();
    }

    /**
     * Find Semester by Semester Number and Course
     */
    @Override
    public Semester findBySemesterNumberAndCourse(Integer semesterNumber,
                                                  Course course) {

        List<Semester> list = entityManager.createQuery(
                "FROM Semester s WHERE s.semesterNumber = :semesterNumber "
              + "AND s.course = :course",
                Semester.class)
                .setParameter("semesterNumber", semesterNumber)
                .setParameter("course", course)
                .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * Find Semesters By Course
     */
    @Override
    public List<Semester> findByCourse(Course course) {

        return entityManager.createQuery(
                "FROM Semester s WHERE s.course = :course "
              + "ORDER BY s.semesterNumber",
                Semester.class)
                .setParameter("course", course)
                .getResultList();
    }

}