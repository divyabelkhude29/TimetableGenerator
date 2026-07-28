package com.timetable.dto.dashboard;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.timetable.dto.timetablegeneration.TimetableSlotDTO;

public class FacultyDashboardResponse {

    /*
     * Status
     */
    private boolean success;

    private String message;

    /*
     * Faculty Information
     */
    private Long facultyId;

    private String facultyName;

    private String departmentName;

    /*
     * Workload Statistics
     */
    private Integer totalWeeklyHours;

    private Integer completedHours;

    private Integer remainingHours;

    /*
     * Lecture Statistics
     */
    private Integer todaysLectures;

    private Integer weeklyLectures;

    /*
     * Assigned Subjects
     */
    private List<String> assignedSubjects =
            new ArrayList<>();

    /*
     * Today's Timetable
     */
    private List<TimetableSlotDTO> todaysSchedule =
            new ArrayList<>();

    /*
     * Weekly Timetable
     */
    private List<TimetableSlotDTO> weeklySchedule =
            new ArrayList<>();

    /*
     * Faculty Charts
     */
    private List<ChartDataDTO> workloadChart =
            new ArrayList<>();

    /*
     * Generated Time
     */
    private LocalDateTime generatedAt;

    public FacultyDashboardResponse() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getTotalWeeklyHours() {
        return totalWeeklyHours;
    }

    public void setTotalWeeklyHours(Integer totalWeeklyHours) {
        this.totalWeeklyHours = totalWeeklyHours;
    }

    public Integer getCompletedHours() {
        return completedHours;
    }

    public void setCompletedHours(Integer completedHours) {
        this.completedHours = completedHours;
    }

    public Integer getRemainingHours() {
        return remainingHours;
    }

    public void setRemainingHours(Integer remainingHours) {
        this.remainingHours = remainingHours;
    }

    public Integer getTodaysLectures() {
        return todaysLectures;
    }

    public void setTodaysLectures(Integer todaysLectures) {
        this.todaysLectures = todaysLectures;
    }

    public Integer getWeeklyLectures() {
        return weeklyLectures;
    }

    public void setWeeklyLectures(Integer weeklyLectures) {
        this.weeklyLectures = weeklyLectures;
    }

    public List<String> getAssignedSubjects() {
        return assignedSubjects;
    }

    public void setAssignedSubjects(List<String> assignedSubjects) {
        this.assignedSubjects = assignedSubjects;
    }

    public List<TimetableSlotDTO> getTodaysSchedule() {
        return todaysSchedule;
    }

    public void setTodaysSchedule(List<TimetableSlotDTO> todaysSchedule) {
        this.todaysSchedule = todaysSchedule;
    }

    public List<TimetableSlotDTO> getWeeklySchedule() {
        return weeklySchedule;
    }

    public void setWeeklySchedule(List<TimetableSlotDTO> weeklySchedule) {
        this.weeklySchedule = weeklySchedule;
    }

    public List<ChartDataDTO> getWorkloadChart() {
        return workloadChart;
    }

    public void setWorkloadChart(List<ChartDataDTO> workloadChart) {
        this.workloadChart = workloadChart;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

}