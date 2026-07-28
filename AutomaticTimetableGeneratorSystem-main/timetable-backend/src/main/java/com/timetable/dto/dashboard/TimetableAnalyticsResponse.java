package com.timetable.dto.dashboard;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimetableAnalyticsResponse {

    /*
     * Status
     */
    private boolean success;

    private String message;

    /*
     * Timetable Statistics
     */
    private Long totalTimetables;

    // Added field
    private Long generatedTimetables;

    private Long totalClasses;

    private Long totalTheoryClasses;

    private Long totalPracticalClasses;

    private Long totalLabs;

    private Long totalFreeHours;

    /*
     * Faculty Statistics
     */
    private Long totalFaculties;

    private Long facultyWithMaximumWorkload;

    private Long facultyWithMinimumWorkload;

    /*
     * Classroom Statistics
     */
    private Long totalClassrooms;

    private Long occupiedClassrooms;

    private Long availableClassrooms;

    /*
     * Student Statistics
     */
    private Long totalStudents;

    private Double averageAttendance;

    /*
     * Charts
     */
    private List<ChartDataDTO> classesPerDay = new ArrayList<>();

    private List<ChartDataDTO> facultyWorkload = new ArrayList<>();

    private List<ChartDataDTO> classroomUtilization = new ArrayList<>();

    private List<ChartDataDTO> subjectDistribution = new ArrayList<>();

    /*
     * Generated Time
     */
    private LocalDateTime generatedAt;

    public TimetableAnalyticsResponse() {
    }

    // ======================
    // Status
    // ======================

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

    // ======================
    // Timetable Statistics
    // ======================

    public Long getTotalTimetables() {
        return totalTimetables;
    }

    public void setTotalTimetables(Long totalTimetables) {
        this.totalTimetables = totalTimetables;
    }

    public Long getGeneratedTimetables() {
        return generatedTimetables;
    }

    public void setGeneratedTimetables(Long generatedTimetables) {
        this.generatedTimetables = generatedTimetables;
    }

    public Long getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(Long totalClasses) {
        this.totalClasses = totalClasses;
    }

    public Long getTotalTheoryClasses() {
        return totalTheoryClasses;
    }

    public void setTotalTheoryClasses(Long totalTheoryClasses) {
        this.totalTheoryClasses = totalTheoryClasses;
    }

    public Long getTotalPracticalClasses() {
        return totalPracticalClasses;
    }

    public void setTotalPracticalClasses(Long totalPracticalClasses) {
        this.totalPracticalClasses = totalPracticalClasses;
    }

    public Long getTotalLabs() {
        return totalLabs;
    }

    public void setTotalLabs(Long totalLabs) {
        this.totalLabs = totalLabs;
    }

    public Long getTotalFreeHours() {
        return totalFreeHours;
    }

    public void setTotalFreeHours(Long totalFreeHours) {
        this.totalFreeHours = totalFreeHours;
    }

    // ======================
    // Faculty Statistics
    // ======================

    public Long getTotalFaculties() {
        return totalFaculties;
    }

    public void setTotalFaculties(Long totalFaculties) {
        this.totalFaculties = totalFaculties;
    }

    public Long getFacultyWithMaximumWorkload() {
        return facultyWithMaximumWorkload;
    }

    public void setFacultyWithMaximumWorkload(Long facultyWithMaximumWorkload) {
        this.facultyWithMaximumWorkload = facultyWithMaximumWorkload;
    }

    public Long getFacultyWithMinimumWorkload() {
        return facultyWithMinimumWorkload;
    }

    public void setFacultyWithMinimumWorkload(Long facultyWithMinimumWorkload) {
        this.facultyWithMinimumWorkload = facultyWithMinimumWorkload;
    }

    // ======================
    // Classroom Statistics
    // ======================

    public Long getTotalClassrooms() {
        return totalClassrooms;
    }

    public void setTotalClassrooms(Long totalClassrooms) {
        this.totalClassrooms = totalClassrooms;
    }

    public Long getOccupiedClassrooms() {
        return occupiedClassrooms;
    }

    public void setOccupiedClassrooms(Long occupiedClassrooms) {
        this.occupiedClassrooms = occupiedClassrooms;
    }

    public Long getAvailableClassrooms() {
        return availableClassrooms;
    }

    public void setAvailableClassrooms(Long availableClassrooms) {
        this.availableClassrooms = availableClassrooms;
    }

    // ======================
    // Student Statistics
    // ======================

    public Long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Double getAverageAttendance() {
        return averageAttendance;
    }

    public void setAverageAttendance(Double averageAttendance) {
        this.averageAttendance = averageAttendance;
    }

    // ======================
    // Charts
    // ======================

    public List<ChartDataDTO> getClassesPerDay() {
        return classesPerDay;
    }

    public void setClassesPerDay(List<ChartDataDTO> classesPerDay) {
        this.classesPerDay = classesPerDay;
    }

    public List<ChartDataDTO> getFacultyWorkload() {
        return facultyWorkload;
    }

    public void setFacultyWorkload(List<ChartDataDTO> facultyWorkload) {
        this.facultyWorkload = facultyWorkload;
    }

    public List<ChartDataDTO> getClassroomUtilization() {
        return classroomUtilization;
    }

    public void setClassroomUtilization(List<ChartDataDTO> classroomUtilization) {
        this.classroomUtilization = classroomUtilization;
    }

    public List<ChartDataDTO> getSubjectDistribution() {
        return subjectDistribution;
    }

    public void setSubjectDistribution(List<ChartDataDTO> subjectDistribution) {
        this.subjectDistribution = subjectDistribution;
    }

    // ======================
    // Generated Time
    // ======================

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
}