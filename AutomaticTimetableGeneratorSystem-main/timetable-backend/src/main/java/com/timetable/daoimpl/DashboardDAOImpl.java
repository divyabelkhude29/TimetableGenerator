package com.timetable.daoimpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.DashboardDAO;
import com.timetable.dto.dashboard.ChartDataDTO;

@Repository
@Transactional(readOnly = true)
public class DashboardDAOImpl implements DashboardDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public DashboardDAOImpl() {
    }

    /*
     * =====================================================
     * MASTER STATISTICS
     * =====================================================
     */

    @Override
    public Long getTotalUsers() {

        return entityManager.createQuery(
                "SELECT COUNT(u) FROM User u",
                Long.class)
                .getSingleResult();
    }

    @Override
    public Long getTotalDepartments() {

        return entityManager.createQuery(
                "SELECT COUNT(d) FROM Department d",
                Long.class)
                .getSingleResult();
    }

    @Override
    public Long getTotalCourses() {

        return entityManager.createQuery(
                "SELECT COUNT(c) FROM Course c",
                Long.class)
                .getSingleResult();
    }

    @Override
    public Long getTotalSemesters() {

        return entityManager.createQuery(
                "SELECT COUNT(s) FROM Semester s",
                Long.class)
                .getSingleResult();
    }

    @Override
    public Long getTotalAcademicYears() {

        return entityManager.createQuery(
                """
                SELECT COUNT(DISTINCT s.academicYear)
                FROM Semester s
                """,
                Long.class)
                .getSingleResult();
    }

    @Override
    public Long getTotalAcademicSections() {

        return entityManager.createQuery(
                "SELECT COUNT(s) FROM Section s",
                Long.class)
                .getSingleResult();
    }

    @Override
    public Long getTotalSubjects() {

        return entityManager.createQuery(
                "SELECT COUNT(s) FROM Subject s",
                Long.class)
                .getSingleResult();
    }

    @Override
    public Long getTotalFaculties() {

        return entityManager.createQuery(
                "SELECT COUNT(f) FROM Faculty f",
                Long.class)
                .getSingleResult();
    }

    @Override
    public Long getTotalStudents() {

        return entityManager.createQuery(
                "SELECT COUNT(s) FROM Student s",
                Long.class)
                .getSingleResult();
    }

    @Override
    public Long getTotalClassrooms() {

        return entityManager.createQuery(
                "SELECT COUNT(c) FROM Classroom c",
                Long.class)
                .getSingleResult();
    }

    /*
     * =====================================================
     * TIMETABLE STATISTICS
     * =====================================================
     */

    @Override
    public Long getTotalTimetables() {

        return entityManager.createQuery(
                "SELECT COUNT(t) FROM Timetable t",
                Long.class)
                .getSingleResult();
    }

    @Override
    public Long getGeneratedTimetables() {

        return entityManager.createQuery(
                """
                SELECT COUNT(t)
                FROM Timetable t
                WHERE t.active = true
                """,
                Long.class)
                .getSingleResult();
    }

    @Override
    public Long getTodaysClasses() {

        String today = LocalDate.now().getDayOfWeek().name();

        return entityManager.createQuery(
                """
                SELECT COUNT(t)
                FROM Timetable t
                WHERE UPPER(t.dayOfWeek) = :today
                """,
                Long.class)
                .setParameter("today", today)
                .getSingleResult();
    }

    @Override
    public Long getFreeClassrooms() {

        Long total = getTotalClassrooms();
        Long occupied = getOccupiedClassrooms();

        return total - occupied;
    }

    @Override
    public Long getOccupiedClassrooms() {

        return entityManager.createQuery(
                """
                SELECT COUNT(DISTINCT t.classroom.classroomId)
                FROM Timetable t
                WHERE t.active = true
                """,
                Long.class)
                .getSingleResult();
    }

    /*
     * =====================================================
     * WORKLOAD STATISTICS
     * =====================================================
     */

    @Override
    public Long getTotalWorkload() {

        Long workload = entityManager.createQuery(
                """
                SELECT COALESCE(SUM(s.hoursPerWeek), 0)
                FROM Subject s
                """,
                Long.class)
                .getSingleResult();

        return workload == null ? 0L : workload;
    }

    @Override
    public Long getCompletedWorkload() {

        return entityManager.createQuery(
                """
                SELECT COUNT(t)
                FROM Timetable t
                WHERE t.active = true
                """,
                Long.class)
                .getSingleResult();
    }

    @Override
    public Long getPendingWorkload() {

        return getTotalWorkload() - getCompletedWorkload();
    }

    /*
     * =====================================================
     * DASHBOARD CHARTS
     * =====================================================
     */

    @Override
    public List<ChartDataDTO> getClassesPerDay() {

        List<ChartDataDTO> chartData = new ArrayList<>();

        List<Object[]> results = entityManager.createQuery(
                """
                SELECT t.dayOfWeek,
                       COUNT(t)
                FROM Timetable t
                GROUP BY t.dayOfWeek
                ORDER BY t.dayOfWeek
                """,
                Object[].class)
                .getResultList();

        for (Object[] row : results) {
            chartData.add(new ChartDataDTO(
                    String.valueOf(row[0]),
                    ((Number) row[1]).longValue()));
        }

        return chartData;
    }

    @Override
    public List<ChartDataDTO> getFacultyWorkloadChart() {

        List<ChartDataDTO> chartData = new ArrayList<>();

        List<Object[]> results = entityManager.createQuery(
                """
                SELECT CONCAT(f.firstName, ' ', f.lastName),
                       COUNT(t)
                FROM Timetable t
                JOIN t.allocation a
                JOIN a.faculty f
                GROUP BY f.firstName, f.lastName
                ORDER BY COUNT(t) DESC
                """,
                Object[].class)
                .getResultList();

        for (Object[] row : results) {
            chartData.add(new ChartDataDTO(
                    String.valueOf(row[0]),
                    ((Number) row[1]).longValue()));
        }

        return chartData;
    }

    @Override
    public List<ChartDataDTO> getClassroomUtilizationChart() {

        List<ChartDataDTO> chartData = new ArrayList<>();

        List<Object[]> results = entityManager.createQuery(
                """
                SELECT c.roomNumber,
                       COUNT(t)
                FROM Timetable t
                JOIN t.classroom c
                GROUP BY c.roomNumber
                ORDER BY COUNT(t) DESC
                """,
                Object[].class)
                .getResultList();

        for (Object[] row : results) {
            chartData.add(new ChartDataDTO(
                    String.valueOf(row[0]),
                    ((Number) row[1]).longValue()));
        }

        return chartData;
    }

    @Override
    public List<ChartDataDTO> getSubjectDistributionChart() {

        List<ChartDataDTO> chartData = new ArrayList<>();

        List<Object[]> results = entityManager.createQuery(
                """
                SELECT s.subjectName,
                       COUNT(t)
                FROM Timetable t
                JOIN t.allocation a
                JOIN a.subject s
                GROUP BY s.subjectName
                ORDER BY COUNT(t) DESC
                """,
                Object[].class)
                .getResultList();

        for (Object[] row : results) {
            chartData.add(new ChartDataDTO(
                    String.valueOf(row[0]),
                    ((Number) row[1]).longValue()));
        }

        return chartData;
    }
}