package com.timetable.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
        name = "subject_workload",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {"allocation_id"}
                )
        }
)
public class SubjectWorkload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workload_id")
    private Long workloadId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allocation_id", nullable = false)
    @NotNull(message = "Faculty Subject Allocation is required")
    private FacultySubjectAllocation allocation;

    @NotNull(message = "Weekly hours are required")
    @Min(value = 1, message = "Weekly hours must be at least 1")
    @Column(name = "weekly_hours", nullable = false)
    private Integer weeklyHours;

    @NotNull(message = "Theory hours are required")
    @Min(value = 0, message = "Theory hours cannot be negative")
    @Column(name = "theory_hours", nullable = false)
    private Integer theoryHours;

    @NotNull(message = "Practical hours are required")
    @Min(value = 0, message = "Practical hours cannot be negative")
    @Column(name = "practical_hours", nullable = false)
    private Integer practicalHours;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public SubjectWorkload() {
    }

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public Long getWorkloadId() {
        return workloadId;
    }

    public void setWorkloadId(Long workloadId) {
        this.workloadId = workloadId;
    }

    public FacultySubjectAllocation getAllocation() {
        return allocation;
    }

    public void setAllocation(FacultySubjectAllocation allocation) {
        this.allocation = allocation;
    }

    public Integer getWeeklyHours() {
        return weeklyHours;
    }

    public void setWeeklyHours(Integer weeklyHours) {
        this.weeklyHours = weeklyHours;
    }

    public Integer getTheoryHours() {
        return theoryHours;
    }

    public void setTheoryHours(Integer theoryHours) {
        this.theoryHours = theoryHours;
    }

    public Integer getPracticalHours() {
        return practicalHours;
    }

    public void setPracticalHours(Integer practicalHours) {
        this.practicalHours = practicalHours;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}