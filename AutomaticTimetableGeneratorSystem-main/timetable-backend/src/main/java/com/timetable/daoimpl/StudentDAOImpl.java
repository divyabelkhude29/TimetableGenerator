package com.timetable.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.StudentDAO;
import com.timetable.entity.Student;

@Repository
@Transactional
public class StudentDAOImpl implements StudentDAO {

    private final SessionFactory sessionFactory;

    public StudentDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Returns Current Hibernate Session
     */
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Save Student
     */
    @Override
    public Student save(Student student) {

        getCurrentSession().persist(student);

        return student;
    }

    /**
     * Update Student
     */
    @Override
    public Student update(Student student) {

        return (Student) getCurrentSession().merge(student);
    }

    /**
     * Delete Student
     */
    @Override
    public void delete(Long studentId) {

        Student student = findById(studentId);

        if (student != null) {
            getCurrentSession().remove(student);
        }
    }

    /**
     * Find Student By ID
     */
    @Override
    public Student findById(Long studentId) {

        return getCurrentSession().get(Student.class, studentId);
    }

    /**
     * Find All Students
     */
    @Override
    public List<Student> findAll() {

        return getCurrentSession()
                .createQuery(
                        "FROM Student s ORDER BY s.firstName, s.lastName",
                        Student.class)
                .getResultList();
    }

    /**
     * Find Student By Roll Number
     */
    @Override
    public Student findByRollNumber(String rollNumber) {

        List<Student> students = getCurrentSession()
                .createQuery(
                        "FROM Student s WHERE s.rollNumber = :rollNumber",
                        Student.class)
                .setParameter("rollNumber", rollNumber)
                .getResultList();

        return students.isEmpty() ? null : students.get(0);
    }

    /**
     * Find Student By Register Number
     */
    @Override
    public Student findByRegisterNumber(String registerNumber) {

        List<Student> students = getCurrentSession()
                .createQuery(
                        "FROM Student s WHERE s.registerNumber = :registerNumber",
                        Student.class)
                .setParameter("registerNumber", registerNumber)
                .getResultList();

        return students.isEmpty() ? null : students.get(0);
    }

    /**
     * Find Student By Email
     */
    @Override
    public Student findByEmail(String email) {

        List<Student> students = getCurrentSession()
                .createQuery(
                        "FROM Student s WHERE s.email = :email",
                        Student.class)
                .setParameter("email", email)
                .getResultList();

        return students.isEmpty() ? null : students.get(0);
    }

    /**
     * Check Student Exists
     */
    @Override
    public boolean existsById(Long studentId) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(s) FROM Student s WHERE s.studentId = :studentId",
                        Long.class)
                .setParameter("studentId", studentId)
                .uniqueResult();

        return count != null && count > 0;
    }
}