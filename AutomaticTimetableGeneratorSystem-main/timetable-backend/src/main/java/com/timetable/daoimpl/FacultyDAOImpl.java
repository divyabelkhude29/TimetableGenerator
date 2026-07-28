package com.timetable.daoimpl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.timetable.dao.FacultyDAO;
import com.timetable.entity.Faculty;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class FacultyDAOImpl implements FacultyDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Save Faculty
     */
    @Override
    public Faculty save(Faculty faculty) {

        entityManager.persist(faculty);

        return faculty;
    }

    /**
     * Update Faculty
     */
    @Override
    public Faculty update(Faculty faculty) {

        return entityManager.merge(faculty);
    }

    /**
     * Delete Faculty
     */
    @Override
    public void delete(Long facultyId) {

        Faculty faculty = findById(facultyId);

        if (faculty != null) {
            entityManager.remove(faculty);
        }
    }

    /**
     * Find Faculty By ID
     */
    @Override
    public Faculty findById(Long facultyId) {

        return entityManager.find(Faculty.class, facultyId);
    }

    /**
     * Find All Faculties
     */
    @Override
    public List<Faculty> findAll() {

        TypedQuery<Faculty> query = entityManager.createQuery(
                "FROM Faculty f ORDER BY f.facultyCode",
                Faculty.class);

        return query.getResultList();
    }

    /**
     * Find Faculty By Faculty Code
     */
    @Override
    public Faculty findByFacultyCode(String facultyCode) {

        List<Faculty> list = entityManager.createQuery(
                "FROM Faculty f WHERE f.facultyCode = :facultyCode",
                Faculty.class)
                .setParameter("facultyCode", facultyCode)
                .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * Find Faculty By Email
     */
    @Override
    public Faculty findByEmail(String email) {

        List<Faculty> list = entityManager.createQuery(
                "FROM Faculty f WHERE f.email = :email",
                Faculty.class)
                .setParameter("email", email)
                .getResultList();

        return list.isEmpty() ? null : list.get(0);
    }

}