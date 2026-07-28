package com.timetable.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.timetable.dao.DashboardDAO;
import com.timetable.dto.dashboard.DashboardSummaryResponse;
import com.timetable.serviceimpl.DashboardServiceImpl;

public class DashboardServiceTest {

    private DashboardDAO dashboardDAO;

    private DashboardServiceImpl service;

    @BeforeEach
    void setup() {

        dashboardDAO =
                Mockito.mock(DashboardDAO.class);

        service =
                new DashboardServiceImpl(
                        dashboardDAO);

        when(dashboardDAO.getTotalUsers())
                .thenReturn(50L);

        when(dashboardDAO.getTotalDepartments())
                .thenReturn(5L);

        when(dashboardDAO.getTotalCourses())
                .thenReturn(6L);

        when(dashboardDAO.getTotalSemesters())
                .thenReturn(8L);

        when(dashboardDAO.getTotalAcademicYears())
                .thenReturn(2L);

        when(dashboardDAO.getTotalAcademicSections())
                .thenReturn(12L);

        when(dashboardDAO.getTotalSubjects())
                .thenReturn(60L);

        when(dashboardDAO.getTotalFaculties())
                .thenReturn(20L);

        when(dashboardDAO.getTotalStudents())
                .thenReturn(300L);

        when(dashboardDAO.getTotalClassrooms())
                .thenReturn(25L);

        when(dashboardDAO.getTotalTimetables())
                .thenReturn(15L);

        when(dashboardDAO.getGeneratedTimetables())
                .thenReturn(15L);

        when(dashboardDAO.getTodaysClasses())
                .thenReturn(45L);

        when(dashboardDAO.getFreeClassrooms())
                .thenReturn(5L);

        when(dashboardDAO.getOccupiedClassrooms())
                .thenReturn(20L);

        when(dashboardDAO.getTotalWorkload())
                .thenReturn(200L);

        when(dashboardDAO.getCompletedWorkload())
                .thenReturn(150L);

        when(dashboardDAO.getPendingWorkload())
                .thenReturn(50L);
    }

    @Test
    void testDashboardSummary() {

        DashboardSummaryResponse response =
                service.getDashboardSummary();

        assertNotNull(response);
    }

}