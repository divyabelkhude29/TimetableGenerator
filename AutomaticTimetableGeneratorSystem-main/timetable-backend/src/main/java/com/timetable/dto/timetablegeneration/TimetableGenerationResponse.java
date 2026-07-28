package com.timetable.dto.timetablegeneration;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimetableGenerationResponse {

    private boolean success;

    private String message;

    private String academicYear;

    private Long courseId;

    private Long semesterId;
    
    private Long sectionId;

    public String getAcademicYear() {
		return academicYear;
	}

	public void setAcademicYear(String academicYear) {
		this.academicYear = academicYear;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	/**
     * Total timetable entries generated.
     */
    private Integer totalClassesGenerated;

    /**
     * Total workload assigned.
     */
    private Integer totalHoursAssigned;

    /**
     * Time taken to generate timetable.
     */
    private Long executionTimeInMilliseconds;

    /**
     * Generation timestamp.
     */
    private LocalDateTime generatedAt;

    /**
     * Constraint violations (if any).
     */
    private List<String> conflicts = new ArrayList<>();

    public TimetableGenerationResponse() {
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

    public String getAcademicYearId() {
        return academicYear;
    }

    public void setAcademicYearId(String academicYearId) {
        this.academicYear = academicYearId;
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
        return sectionId;
    }

    public void setAcademicSectionId(Long academicSectionId) {
        this.sectionId = academicSectionId;
    }

    public Integer getTotalClassesGenerated() {
        return totalClassesGenerated;
    }

    public void setTotalClassesGenerated(Integer totalClassesGenerated) {
        this.totalClassesGenerated = totalClassesGenerated;
    }

    public Integer getTotalHoursAssigned() {
        return totalHoursAssigned;
    }

    public void setTotalHoursAssigned(Integer totalHoursAssigned) {
        this.totalHoursAssigned = totalHoursAssigned;
    }

    public Long getExecutionTimeInMilliseconds() {
        return executionTimeInMilliseconds;
    }

    public void setExecutionTimeInMilliseconds(Long executionTimeInMilliseconds) {
        this.executionTimeInMilliseconds = executionTimeInMilliseconds;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }

    public void setGeneratedAt(LocalDateTime generatedAt) {
        this.generatedAt = generatedAt;
    }

    public List<String> getConflicts() {
        return conflicts;
    }

    public void setConflicts(List<String> conflicts) {
        this.conflicts = conflicts;
    }
}