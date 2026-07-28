package com.timetable.dto.dashboard;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DashboardSummaryResponse {

    /*
     * Status
     */
    private boolean success;

    private String message;

    /*
     * Master Statistics
     */
    private Long totalUsers;

    private Long totalDepartments;

    private Long totalCourses;

    private Long totalSemesters;

    private Long totalAcademicYears;

    private Long totalAcademicSections;

    private Long totalSubjects;

    private Long totalFaculties;

    private Long totalStudents;

    private Long totalClassrooms;

    /*
     * Timetable Statistics
     */
    private Long totalTimetables;

    private Long generatedTimetables;

    private Long todaysClasses;

    private Long freeClassrooms;

    private Long occupiedClassrooms;

    /*
     * Workload Statistics
     */
    private Long totalWorkload;

    private Long completedWorkload;

    private Long pendingWorkload;

    /*
     * Dashboard Charts
     */
    private List<ChartDataDTO> classesPerDay =
            new ArrayList<>();

    private List<ChartDataDTO> facultyWorkload =
            new ArrayList<>();

    private List<ChartDataDTO> classroomUtilization =
            new ArrayList<>();

    private List<ChartDataDTO> subjectDistribution =
            new ArrayList<>();

    /*
     * Generated Time
     */
    private LocalDateTime generatedAt;

    public DashboardSummaryResponse() {
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

    public Long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public Long getTotalDepartments() {
        return totalDepartments;
    }

    public void setTotalDepartments(Long totalDepartments) {
        this.totalDepartments = totalDepartments;
    }

    public Long getTotalCourses() {
        return totalCourses;
    }

    public void setTotalCourses(Long totalCourses) {
        this.totalCourses = totalCourses;
    }

    public Long getTotalSemesters() {
        return totalSemesters;
    }

    public void setTotalSemesters(Long totalSemesters) {
        this.totalSemesters = totalSemesters;
    }

    public Long getTotalAcademicYears() {
        return totalAcademicYears;
    }

    public void setTotalAcademicYears(Long totalAcademicYears) {
        this.totalAcademicYears = totalAcademicYears;
    }

    public Long getTotalAcademicSections() {
        return totalAcademicSections;
    }

    public void setTotalAcademicSections(Long totalAcademicSections) {
        this.totalAcademicSections = totalAcademicSections;
    }

    public Long getTotalSubjects() {
        return totalSubjects;
    }

    public void setTotalSubjects(Long totalSubjects) {
        this.totalSubjects = totalSubjects;
    }

    public Long getTotalFaculties() {
        return totalFaculties;
    }

    public void setTotalFaculties(Long totalFaculties) {
        this.totalFaculties = totalFaculties;
    }

    public Long getTotalStudents() {
        return totalStudents;
    }

    public void setTotalStudents(Long totalStudents) {
        this.totalStudents = totalStudents;
    }

    public Long getTotalClassrooms() {
        return totalClassrooms;
    }

    public void setTotalClassrooms(Long totalClassrooms) {
        this.totalClassrooms = totalClassrooms;
    }

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

    public Long getTodaysClasses() {
        return todaysClasses;
    }

    public void setTodaysClasses(Long todaysClasses) {
        this.todaysClasses = todaysClasses;
    }

    public Long getFreeClassrooms() {
        return freeClassrooms;
    }

    public void setFreeClassrooms(Long freeClassrooms) {
        this.freeClassrooms = freeClassrooms;
    }

    public Long getOccupiedClassrooms() {
        return occupiedClassrooms;
    }

    public void setOccupiedClassrooms(Long occupiedClassrooms) {
        this.occupiedClassrooms = occupiedClassrooms;
    }

    public Long getTotalWorkload() {
        return totalWorkload;
    }

    public void setTotalWorkload(Long totalWorkload) {
        this.totalWorkload = totalWorkload;
    }

    public Long getCompletedWorkload() {
        return completedWorkload;
    }

    public void setCompletedWorkload(Long completedWorkload) {
        this.completedWorkload = completedWorkload;
    }

    public Long getPendingWorkload() {
        return pendingWorkload;
    }

    public void setPendingWorkload(Long pendingWorkload) {
        this.pendingWorkload = pendingWorkload;
    }

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

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
}