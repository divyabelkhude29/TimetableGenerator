package com.timetable.dao;

import java.util.List;

import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.SubjectWorkload;

public interface SubjectWorkloadDAO {

    /**
     * Save Subject Workload
     */
    SubjectWorkload save(SubjectWorkload workload);

    /**
     * Update Subject Workload
     */
    SubjectWorkload update(SubjectWorkload workload);

    /**
     * Delete Subject Workload
     */
    void delete(Long workloadId);

    /**
     * Find Subject Workload By ID
     */
    SubjectWorkload findById(Long workloadId);

    /**
     * Find All Subject Workloads
     */
    List<SubjectWorkload> findAll();

    /**
     * Find By Faculty Subject Allocation
     */
    SubjectWorkload findByAllocation(
            FacultySubjectAllocation allocation);

    /**
     * Check Whether Workload Exists For Allocation
     */
    boolean existsByAllocation(
            FacultySubjectAllocation allocation);

    /**
     * Check Whether Another Workload Exists
     * (Used During Update)
     */
    boolean existsByAllocationAndNotWorkloadId(
            FacultySubjectAllocation allocation,
            Long workloadId);
}