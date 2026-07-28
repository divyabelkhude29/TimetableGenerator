package com.timetable.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.timetable.dto.dashboard.DashboardSummaryResponse;
import com.timetable.dto.dashboard.FacultyDashboardResponse;
import com.timetable.dto.dashboard.StudentDashboardResponse;
import com.timetable.dto.dashboard.TimetableAnalyticsResponse;
import com.timetable.service.DashboardService;

@RestController
@RequestMapping("/api/dashboard")
@Validated
@CrossOrigin(origins = "*")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * ==========================================
     * Admin Dashboard
     * ==========================================
     */
    @GetMapping("/admin")
    public ResponseEntity<DashboardSummaryResponse> getDashboardSummary() {

        return ResponseEntity.ok(
                dashboardService.getDashboardSummary());
    }

    /**
     * ==========================================
     * Faculty Dashboard
     * ==========================================
     */
    @GetMapping("/faculty/{facultyId}")
    public ResponseEntity<FacultyDashboardResponse> getFacultyDashboard(
            @PathVariable Long facultyId) {

        return ResponseEntity.ok(
                dashboardService.getFacultyDashboard(facultyId));
    }

    /**
     * ==========================================
     * Student Dashboard
     * ==========================================
     */
    @GetMapping("/student/{studentId}")
    public ResponseEntity<StudentDashboardResponse> getStudentDashboard(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(
                dashboardService.getStudentDashboard(studentId));
    }

    /**
     * ==========================================
     * Timetable Analytics
     * ==========================================
     */
    @GetMapping("/analytics")
    public ResponseEntity<TimetableAnalyticsResponse> getAnalytics() {

        return ResponseEntity.ok(
                dashboardService.getTimetableAnalytics());
    }

}