package com.timetable.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(
        name = "timetable_constraint",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"constraint_key"})
        }
)
public class TimetableConstraint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "constraint_id")
    private Long constraintId;

    @NotBlank(message = "Constraint name is required")
    @Column(name = "constraint_name", nullable = false, length = 100)
    private String constraintName;

    @NotBlank(message = "Constraint key is required")
    @Column(name = "constraint_key", nullable = false, unique = true, length = 100)
    private String constraintKey;

    @NotBlank(message = "Constraint value is required")
    @Column(name = "constraint_value", nullable = false, length = 100)
    private String constraintValue;

    @Column(name = "description", length = 500)
    private String description;

    @NotNull(message = "Active status is required")
    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public TimetableConstraint() {
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

    public Long getConstraintId() {
        return constraintId;
    }

    public void setConstraintId(Long constraintId) {
        this.constraintId = constraintId;
    }

    public String getConstraintName() {
        return constraintName;
    }

    public void setConstraintName(String constraintName) {
        this.constraintName = constraintName;
    }

    public String getConstraintKey() {
        return constraintKey;
    }

    public void setConstraintKey(String constraintKey) {
        this.constraintKey = constraintKey;
    }

    public String getConstraintValue() {
        return constraintValue;
    }

    public void setConstraintValue(String constraintValue) {
        this.constraintValue = constraintValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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