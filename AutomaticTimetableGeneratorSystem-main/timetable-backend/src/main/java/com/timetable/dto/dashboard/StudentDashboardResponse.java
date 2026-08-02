package com.timetable.dto.dashboard;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.timetable.dto.timetablegeneration.TimetableSlotDTO;

public class StudentDashboardResponse {

    /*
     * Status
     */
    private boolean success;

    private String message;

    /*
     * Student Information
     */
    private Long studentId;

    private String studentName;

    private String enrollmentNumber;

    private Long academicYearId;

    private Long courseId;

    private Long semesterId;

    private Long academicSectionId;

    /*
     * Attendance Statistics
     */
    private Long totalClasses;

    private Long attendedClasses;

    private Long missedClasses;

    private Double attendancePercentage;

    /*
     * Today's Schedule
     */
    private List<TimetableSlotDTO> todaysClasses =
            new ArrayList<>();

    /*
     * Upcoming Classes
     */
    private List<TimetableSlotDTO> upcomingClasses =
            new ArrayList<>();

    /*
     * Notifications
     */
    private List<String> notifications =
            new ArrayList<>();

    /*
     * Generated Time
     */
    private LocalDateTime generatedAt;

    public StudentDashboardResponse() {
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

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getEnrollmentNumber() {
        return enrollmentNumber;
    }

    public void setEnrollmentNumber(String enrollmentNumber) {
        this.enrollmentNumber = enrollmentNumber;
    }

    public Long getAcademicYearId() {
        return academicYearId;
    }

    public void setAcademicYearId(Long academicYearId) {
        this.academicYearId = academicYearId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public Long getAcademicSectionId() {
        return academicSectionId;
    }

    public void setAcademicSectionId(Long academicSectionId) {
        this.academicSectionId = academicSectionId;
    }

    public Long getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(Long totalClasses) {
        this.totalClasses = totalClasses;
    }

    public Long getAttendedClasses() {
        return attendedClasses;
    }

    public void setAttendedClasses(Long attendedClasses) {
        this.attendedClasses = attendedClasses;
    }

    public Long getMissedClasses() {
        return missedClasses;
    }

    public void setMissedClasses(Long missedClasses) {
        this.missedClasses = missedClasses;
    }

    public Double getAttendancePercentage() {
        return attendancePercentage;
    }

    public void setAttendancePercentage(Double attendancePercentage) {
        this.attendancePercentage = attendancePercentage;
    }

    public List<TimetableSlotDTO> getTodaysClasses() {
        return todaysClasses;
    }

    public void setTodaysClasses(List<TimetableSlotDTO> todaysClasses) {
        this.todaysClasses = todaysClasses;
    }

    public List<TimetableSlotDTO> getUpcomingClasses() {
        return upcomingClasses;
    }

    public void setUpcomingClasses(List<TimetableSlotDTO> upcomingClasses) {
        this.upcomingClasses = upcomingClasses;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }
}