package com.timetable.dao;

import java.util.List;

import com.timetable.dto.dashboard.ChartDataDTO;

public interface DashboardDAO {

    /*
     * ======================================
     * MASTER STATISTICS
     * ======================================
     */

    Long getTotalUsers();

    Long getTotalDepartments();

    Long getTotalCourses();

    Long getTotalSemesters();

    /**
     * Academic Year is stored as String in your project.
     * Returns the number of distinct academic years.
     */
    Long getTotalAcademicYears();

    /**
     * Sections table is your Academic Sections table.
     */
    Long getTotalAcademicSections();

    Long getTotalSubjects();

    Long getTotalFaculties();

    Long getTotalStudents();

    Long getTotalClassrooms();

    /*
     * ======================================
     * TIMETABLE STATISTICS
     * ======================================
     */

    Long getTotalTimetables();

    Long getGeneratedTimetables();

    Long getTodaysClasses();

    Long getFreeClassrooms();

    Long getOccupiedClassrooms();

    /*
     * ======================================
     * WORKLOAD STATISTICS
     * ======================================
     */

    Long getTotalWorkload();

    Long getCompletedWorkload();

    Long getPendingWorkload();

    /*
     * ======================================
     * DASHBOARD CHARTS
     * ======================================
     */

    List<ChartDataDTO> getClassesPerDay();

    List<ChartDataDTO> getFacultyWorkloadChart();

    List<ChartDataDTO> getClassroomUtilizationChart();

    List<ChartDataDTO> getSubjectDistributionChart();

}