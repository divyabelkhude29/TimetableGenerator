package com.timetable.dto.timetablegeneration;

public class TimetableSlotDTO {

	/**
	 * Day of Week
	 * Example:
	 * Monday
	 * Tuesday
	 * Wednesday
	 */
	private String dayOfWeek;

    /**
     * Time Slot
     */
    private Long timeSlotId;

    private String startTime;

    private String endTime;

    /**
     * Course Information
     */
    private Long courseId;

    private String courseName;

    /**
     * Semester Information
     */
    private Long semesterId;

    private Integer semesterNumber;

    /**
     * Academic Section
     */
    private Long academicSectionId;

    private String sectionName;

    /**
     * Subject
     */
    private Long subjectId;

    private String subjectCode;

    private String subjectName;

    /**
     * Faculty
     */
    private Long facultyId;

    private String facultyCode;

    private String facultyName;

    /**
     * Slot Information
     */
    private boolean laboratory;

    private boolean elective;

    private boolean generatedAutomatically;

    public TimetableSlotDTO() {
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public Long getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(Long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Long semesterId) {
        this.semesterId = semesterId;
    }

    public Integer getSemesterNumber() {
        return semesterNumber;
    }

    public void setSemesterNumber(Integer semesterNumber) {
        this.semesterNumber = semesterNumber;
    }

    public Long getAcademicSectionId() {
        return academicSectionId;
    }

    public void setAcademicSectionId(Long academicSectionId) {
        this.academicSectionId = academicSectionId;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(Long subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Long getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(Long facultyId) {
        this.facultyId = facultyId;
    }

    public String getFacultyCode() {
        return facultyCode;
    }

    public void setFacultyCode(String facultyCode) {
        this.facultyCode = facultyCode;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public boolean isLaboratory() {
        return laboratory;
    }

    public void setLaboratory(boolean laboratory) {
        this.laboratory = laboratory;
    }

    public boolean isElective() {
        return elective;
    }

    public void setElective(boolean elective) {
        this.elective = elective;
    }

    public boolean isGeneratedAutomatically() {
        return generatedAutomatically;
    }

    public void setGeneratedAutomatically(boolean generatedAutomatically) {
        this.generatedAutomatically = generatedAutomatically;
    }
}