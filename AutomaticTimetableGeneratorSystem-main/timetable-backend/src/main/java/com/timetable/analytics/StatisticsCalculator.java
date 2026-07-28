package com.timetable.analytics;

import java.util.List;

import org.springframework.stereotype.Component;

import com.timetable.dto.dashboard.ChartDataDTO;

@Component
public class StatisticsCalculator {

    /**
     * Attendance Percentage
     */
    public double calculateAttendancePercentage(
            long attended,
            long total) {

        return AnalyticsUtil.percentage(
                attended,
                total);
    }

    /**
     * Workload Completion Percentage
     */
    public double calculateWorkloadPercentage(
            long completed,
            long total) {

        return AnalyticsUtil.percentage(
                completed,
                total);
    }

    /**
     * Classroom Utilization
     */
    public double calculateClassroomUtilization(
            long occupied,
            long totalRooms) {

        return AnalyticsUtil.percentage(
                occupied,
                totalRooms);
    }

    /**
     * Faculty Utilization
     */
    public double calculateFacultyUtilization(
            long assignedHours,
            long availableHours) {

        return AnalyticsUtil.percentage(
                assignedHours,
                availableHours);
    }

    /**
     * Student Attendance Average
     */
    public double calculateAverageAttendance(
            List<Double> attendanceList) {

        if (attendanceList == null ||
                attendanceList.isEmpty()) {

            return 0;
        }

        double total = 0;

        for (Double value : attendanceList) {

            if (value != null) {

                total += value;
            }
        }

        return AnalyticsUtil.round(
                total / attendanceList.size());
    }

    /**
     * Average Chart Value
     */
    public double calculateAverageChartValue(
            List<ChartDataDTO> chart) {

        return AnalyticsUtil.averageChartValue(chart);
    }

    /**
     * Total Chart Value
     */
    public long calculateTotalChartValue(
            List<ChartDataDTO> chart) {

        return AnalyticsUtil.totalChartValue(chart);
    }

    /**
     * Maximum Chart Value
     */
    public long calculateMaximumChartValue(
            List<ChartDataDTO> chart) {

        return AnalyticsUtil.maxChartValue(chart);
    }

    /**
     * Minimum Chart Value
     */
    public long calculateMinimumChartValue(
            List<ChartDataDTO> chart) {

        return AnalyticsUtil.minChartValue(chart);
    }

}