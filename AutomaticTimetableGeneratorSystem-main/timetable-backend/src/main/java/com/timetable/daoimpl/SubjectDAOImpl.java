package com.timetable.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.SubjectDAO;
import com.timetable.entity.Subject;

@Repository
@Transactional
public class SubjectDAOImpl implements SubjectDAO {

    private final SessionFactory sessionFactory;

    public SubjectDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get Current Hibernate Session
     */
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Save Subject
     */
    @Override
    public Subject save(Subject subject) {
        getCurrentSession().persist(subject);
        return subject;
    }

    /**
     * Update Subject
     */
    @Override
    public Subject update(Subject subject) {
        return (Subject) getCurrentSession().merge(subject);
    }

    /**
     * Delete Subject
     */
    @Override
    public void delete(Long subjectId) {

        Subject subject = findById(subjectId);

        if (subject != null) {
            getCurrentSession().remove(subject);
        }
    }

    /**
     * Find Subject By ID
     */
    @Override
    public Subject findById(Long subjectId) {

        return getCurrentSession().get(Subject.class, subjectId);
    }

    /**
     * Find All Subjects
     */
    @Override
    public List<Subject> findAll() {

        return getCurrentSession()
                .createQuery(
                        "FROM Subject s ORDER BY s.subjectName",
                        Subject.class)
                .getResultList();
    }

    /**
     * Find Subject By Subject Code
     */
    @Override
    public Subject findBySubjectCode(String subjectCode) {

        List<Subject> subjects = getCurrentSession()
                .createQuery(
                        "FROM Subject s WHERE s.subjectCode = :subjectCode",
                        Subject.class)
                .setParameter("subjectCode", subjectCode)
                .getResultList();

        return subjects.isEmpty() ? null : subjects.get(0);
    }

    /**
     * Find Subjects By Department
     */
    @Override
    public List<Subject> findByDepartmentId(Long departmentId) {

        return getCurrentSession()
                .createQuery(
                        "FROM Subject s WHERE s.department.departmentId = :departmentId ORDER BY s.subjectName",
                        Subject.class)
                .setParameter("departmentId", departmentId)
                .getResultList();
    }

    /**
     * Find Subjects By Course
     */
    @Override
    public List<Subject> findByCourseId(Long courseId) {

        return getCurrentSession()
                .createQuery(
                        "FROM Subject s WHERE s.course.courseId = :courseId ORDER BY s.subjectName",
                        Subject.class)
                .setParameter("courseId", courseId)
                .getResultList();
    }

    /**
     * Find Subjects By Semester
     */
    @Override
    public List<Subject> findBySemesterId(Long semesterId) {

        return getCurrentSession()
                .createQuery(
                        "FROM Subject s WHERE s.semester.semesterId = :semesterId ORDER BY s.subjectName",
                        Subject.class)
                .setParameter("semesterId", semesterId)
                .getResultList();
    }

    /**
     * Find Subjects By Faculty
     */
    @Override
    public List<Subject> findByFacultyId(Long facultyId) {

        return getCurrentSession()
                .createQuery(
                        "FROM Subject s WHERE s.faculty.facultyId = :facultyId ORDER BY s.subjectName",
                        Subject.class)
                .setParameter("facultyId", facultyId)
                .getResultList();
    }

    /**
     * Check Subject Code Exists
     */
    @Override
    public boolean existsBySubjectCode(String subjectCode) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(s) FROM Subject s WHERE s.subjectCode = :subjectCode",
                        Long.class)
                .setParameter("subjectCode", subjectCode)
                .uniqueResult();

        return count != null && count > 0;
    }
}