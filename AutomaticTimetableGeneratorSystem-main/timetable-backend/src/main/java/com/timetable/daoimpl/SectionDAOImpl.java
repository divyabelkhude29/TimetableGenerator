package com.timetable.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.SectionDAO;
import com.timetable.entity.Course;
import com.timetable.entity.Section;
import com.timetable.entity.Semester;

@Repository
@Transactional
public class SectionDAOImpl implements SectionDAO {

    private final SessionFactory sessionFactory;

    public SectionDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Get Current Hibernate Session
     */
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Save Section
     */
    @Override
    public Section save(Section section) {

        getCurrentSession().persist(section);

        return section;
    }

    /**
     * Update Section
     */
    @Override
    public Section update(Section section) {

        return (Section) getCurrentSession().merge(section);
    }

    /**
     * Delete Section
     */
    @Override
    public void delete(Long sectionId) {

        Section section = findById(sectionId);

        if (section != null) {
            getCurrentSession().remove(section);
        }
    }

    /**
     * Find Section By ID
     */
    @Override
    public Section findById(Long sectionId) {

        return getCurrentSession().get(Section.class, sectionId);
    }

    /**
     * Find All Sections
     */
    @Override
    public List<Section> findAll() {

        return getCurrentSession()
                .createQuery(
                        "FROM Section s ORDER BY s.sectionName",
                        Section.class)
                .getResultList();
    }

    /**
     * Find Section By Section Code
     */
    @Override
    public Section findBySectionCode(String sectionCode) {

        List<Section> sections = getCurrentSession()
                .createQuery(
                        "FROM Section s WHERE s.sectionCode = :sectionCode",
                        Section.class)
                .setParameter("sectionCode", sectionCode)
                .getResultList();

        return sections.isEmpty() ? null : sections.get(0);
    }

    /**
     * Find Sections By Course
     */
    @Override
    public List<Section> findByCourse(Course course) {

        return getCurrentSession()
                .createQuery(
                        "FROM Section s WHERE s.course = :course ORDER BY s.sectionName",
                        Section.class)
                .setParameter("course", course)
                .getResultList();
    }

    /**
     * Find Sections By Semester
     */
    @Override
    public List<Section> findBySemester(Semester semester) {

        return getCurrentSession()
                .createQuery(
                        "FROM Section s WHERE s.semester = :semester ORDER BY s.sectionName",
                        Section.class)
                .setParameter("semester", semester)
                .getResultList();
    }

    /**
     * Check Section Code Exists
     */
    @Override
    public boolean existsBySectionCode(String sectionCode) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(s) FROM Section s WHERE s.sectionCode = :sectionCode",
                        Long.class)
                .setParameter("sectionCode", sectionCode)
                .uniqueResult();

        return count != null && count > 0;
    }
}