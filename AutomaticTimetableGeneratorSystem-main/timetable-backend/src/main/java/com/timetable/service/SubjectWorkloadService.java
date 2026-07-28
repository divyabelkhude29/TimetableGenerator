package com.timetable.service;

import java.util.List;

import com.timetable.dto.subjectworkload.SubjectWorkloadRequest;
import com.timetable.dto.subjectworkload.SubjectWorkloadResponse;

public interface SubjectWorkloadService {

    /**
     * Save Subject Workload
     */
    SubjectWorkloadResponse saveSubjectWorkload(
            SubjectWorkloadRequest request);

    /**
     * Update Subject Workload
     */
    SubjectWorkloadResponse updateSubjectWorkload(
            Long workloadId,
            SubjectWorkloadRequest request);

    /**
     * Delete Subject Workload
     */
    void deleteSubjectWorkload(Long workloadId);

    /**
     * Get Subject Workload By ID
     */
    SubjectWorkloadResponse getSubjectWorkloadById(
            Long workloadId);

    /**
     * Get All Subject Workloads
     */
    List<SubjectWorkloadResponse> getAllSubjectWorkloads();

    /**
     * Get Subject Workload By Allocation
     */
    SubjectWorkloadResponse getSubjectWorkloadByAllocation(
            Long allocationId);
}