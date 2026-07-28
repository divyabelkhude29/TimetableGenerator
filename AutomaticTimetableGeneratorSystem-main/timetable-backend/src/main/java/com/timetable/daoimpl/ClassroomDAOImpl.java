package com.timetable.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.ClassroomDAO;
import com.timetable.entity.Classroom;

@Repository
@Transactional
public class ClassroomDAOImpl implements ClassroomDAO {

    private final SessionFactory sessionFactory;

    public ClassroomDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get Current Hibernate Session
     */
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Save Classroom
     */
    @Override
    public Classroom save(Classroom classroom) {

        getCurrentSession().persist(classroom);

        return classroom;
    }

    /**
     * Update Classroom
     */
    @Override
    public Classroom update(Classroom classroom) {

        return (Classroom) getCurrentSession().merge(classroom);
    }

    /**
     * Delete Classroom
     */
    @Override
    public void delete(Long classroomId) {

        Classroom classroom = findById(classroomId);

        if (classroom != null) {
            getCurrentSession().remove(classroom);
        }
    }

    /**
     * Find Classroom By ID
     */
    @Override
    public Classroom findById(Long classroomId) {

        return getCurrentSession().get(Classroom.class, classroomId);
    }

    /**
     * Find All Classrooms
     */
    @Override
    public List<Classroom> findAll() {

        return getCurrentSession()
                .createQuery(
                        "FROM Classroom c ORDER BY c.buildingName, c.roomNumber",
                        Classroom.class)
                .getResultList();
    }

    /**
     * Find Classroom By Room Number
     */
    @Override
    public Classroom findByRoomNumber(String roomNumber) {

        List<Classroom> classrooms = getCurrentSession()
                .createQuery(
                        "FROM Classroom c WHERE c.roomNumber = :roomNumber",
                        Classroom.class)
                .setParameter("roomNumber", roomNumber)
                .getResultList();

        return classrooms.isEmpty() ? null : classrooms.get(0);
    }

    /**
     * Check Room Number Exists
     */
    @Override
    public boolean existsByRoomNumber(String roomNumber) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(c) FROM Classroom c WHERE c.roomNumber = :roomNumber",
                        Long.class)
                .setParameter("roomNumber", roomNumber)
                .uniqueResult();

        return count != null && count > 0;
    }

}