package com.timetable.serviceimpl;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.DashboardDAO;
import com.timetable.dto.dashboard.DashboardSummaryResponse;
import com.timetable.dto.dashboard.FacultyDashboardResponse;
import com.timetable.dto.dashboard.StudentDashboardResponse;
import com.timetable.dto.dashboard.TimetableAnalyticsResponse;
import com.timetable.service.DashboardService;

@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final DashboardDAO dashboardDAO;

    public DashboardServiceImpl(DashboardDAO dashboardDAO) {
        this.dashboardDAO = dashboardDAO;
    }

    /*
     * ==========================================
     * ADMIN DASHBOARD
     * ==========================================
     */
    @Override
    public DashboardSummaryResponse getDashboardSummary() {

        DashboardSummaryResponse response = new DashboardSummaryResponse();

        response.setSuccess(true);
        response.setMessage("Dashboard loaded successfully.");

        // Master Statistics
        response.setTotalUsers(dashboardDAO.getTotalUsers());
        response.setTotalDepartments(dashboardDAO.getTotalDepartments());
        response.setTotalCourses(dashboardDAO.getTotalCourses());
        response.setTotalSemesters(dashboardDAO.getTotalSemesters());
        response.setTotalAcademicYears(dashboardDAO.getTotalAcademicYears());
        response.setTotalAcademicSections(dashboardDAO.getTotalAcademicSections());
        response.setTotalSubjects(dashboardDAO.getTotalSubjects());
        response.setTotalFaculties(dashboardDAO.getTotalFaculties());
        response.setTotalStudents(dashboardDAO.getTotalStudents());
        response.setTotalClassrooms(dashboardDAO.getTotalClassrooms());

        // Timetable Statistics
        response.setTotalTimetables(dashboardDAO.getTotalTimetables());
        response.setGeneratedTimetables(dashboardDAO.getGeneratedTimetables());
        response.setTodaysClasses(dashboardDAO.getTodaysClasses());
        response.setFreeClassrooms(dashboardDAO.getFreeClassrooms());
        response.setOccupiedClassrooms(dashboardDAO.getOccupiedClassrooms());

        // Workload Statistics
        response.setTotalWorkload(dashboardDAO.getTotalWorkload());
        response.setCompletedWorkload(dashboardDAO.getCompletedWorkload());
        response.setPendingWorkload(dashboardDAO.getPendingWorkload());

        // Charts
        response.setClassesPerDay(dashboardDAO.getClassesPerDay());
        response.setFacultyWorkload(dashboardDAO.getFacultyWorkloadChart());
        response.setClassroomUtilization(dashboardDAO.getClassroomUtilizationChart());
        response.setSubjectDistribution(dashboardDAO.getSubjectDistributionChart());

        response.setGeneratedAt(LocalDateTime.now());

        return response;
    }

    /*
     * ==========================================
     * FACULTY DASHBOARD
     * ==========================================
     */
    @Override
    public FacultyDashboardResponse getFacultyDashboard(Long facultyId) {

        FacultyDashboardResponse response = new FacultyDashboardResponse();

        response.setSuccess(true);
        response.setMessage("Faculty dashboard loaded successfully.");
        response.setGeneratedAt(LocalDateTime.now());

        return response;
    }

    /*
     * ==========================================
     * STUDENT DASHBOARD
     * ==========================================
     */
    @Override
    public StudentDashboardResponse getStudentDashboard(Long studentId) {

        StudentDashboardResponse response = new StudentDashboardResponse();

        response.setSuccess(true);
        response.setMessage("Student dashboard loaded successfully.");
        response.setGeneratedAt(LocalDateTime.now());

        return response;
    }

    /*
     * ==========================================
     * ANALYTICS
     * ==========================================
     */
    @Override
    public TimetableAnalyticsResponse getTimetableAnalytics() {

        TimetableAnalyticsResponse response = new TimetableAnalyticsResponse();

        response.setSuccess(true);
        response.setMessage("Analytics loaded successfully.");

        response.setTotalTimetables(dashboardDAO.getTotalTimetables());

        // Fixed here
        response.setGeneratedTimetables(dashboardDAO.getGeneratedTimetables());

        response.setClassesPerDay(dashboardDAO.getClassesPerDay());
        response.setFacultyWorkload(dashboardDAO.getFacultyWorkloadChart());
        response.setClassroomUtilization(dashboardDAO.getClassroomUtilizationChart());
        response.setSubjectDistribution(dashboardDAO.getSubjectDistributionChart());

        response.setGeneratedAt(LocalDateTime.now());

        return response;
    }
}