package com.timetable.daoimpl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.CourseDAO;
import com.timetable.entity.Course;

@Repository
@Transactional
public class CourseDAOImpl implements CourseDAO {

    private final SessionFactory sessionFactory;

    public CourseDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Returns Current Hibernate Session
     */
    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Save Course
     */
    @Override
    public Course save(Course course) {

        getCurrentSession().persist(course);

        return course;
    }

    /**
     * Update Course
     */
    @Override
    public Course update(Course course) {

        return (Course) getCurrentSession().merge(course);
    }

    /**
     * Delete Course
     */
    @Override
    public void delete(Course course) {

        getCurrentSession().remove(course);
    }

    /**
     * Find Course By ID
     */
    @Override
    public Course findById(Long courseId) {

        return getCurrentSession().get(Course.class, courseId);
    }

    /**
     * Find All Courses
     */
    @Override
    public List<Course> findAll() {

        return getCurrentSession()
                .createQuery(
                        "FROM Course c ORDER BY c.courseName",
                        Course.class)
                .getResultList();
    }

    /**
     * Find Course By Course Code
     */
    @Override
    public Course findByCourseCode(String courseCode) {

        List<Course> courses = getCurrentSession()
                .createQuery(
                        "FROM Course c WHERE c.courseCode = :courseCode",
                        Course.class)
                .setParameter("courseCode", courseCode)
                .getResultList();

        return courses.isEmpty() ? null : courses.get(0);
    }

    /**
     * Find Courses By Department ID
     */
    @Override
    public List<Course> findByDepartmentId(Long departmentId) {

        return getCurrentSession()
                .createQuery(
                        "FROM Course c WHERE c.department.departmentId = :departmentId ORDER BY c.courseName",
                        Course.class)
                .setParameter("departmentId", departmentId)
                .getResultList();
    }

    /**
     * Check Course Code Exists
     */
    @Override
    public boolean existsByCourseCode(String courseCode) {

        Long count = getCurrentSession()
                .createQuery(
                        "SELECT COUNT(c) FROM Course c WHERE c.courseCode = :courseCode",
                        Long.class)
                .setParameter("courseCode", courseCode)
                .uniqueResult();

        return count != null && count > 0;
    }

	@Override
	public void delete(Long courseId) {
		// TODO Auto-generated method stub
		
	}
}