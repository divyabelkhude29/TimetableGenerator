package com.timetable.service;

import com.timetable.dto.dashboard.DashboardSummaryResponse;
import com.timetable.dto.dashboard.FacultyDashboardResponse;
import com.timetable.dto.dashboard.StudentDashboardResponse;
import com.timetable.dto.dashboard.TimetableAnalyticsResponse;

public interface DashboardService {

    /**
     * ==========================================
     * ADMIN DASHBOARD
     * ==========================================
     */
    DashboardSummaryResponse getDashboardSummary();

    /**
     * ==========================================
     * FACULTY DASHBOARD
     * ==========================================
     */
    FacultyDashboardResponse getFacultyDashboard(Long facultyId);

    /**
     * ==========================================
     * STUDENT DASHBOARD
     * ==========================================
     */
    StudentDashboardResponse getStudentDashboard(Long studentId);

    /**
     * ==========================================
     * TIMETABLE ANALYTICS
     * ==========================================
     */
    TimetableAnalyticsResponse getTimetableAnalytics();

}