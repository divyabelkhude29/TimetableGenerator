package com.timetable.service;

import java.util.List;

import com.timetable.dto.allocation.FacultySubjectAllocationRequest;
import com.timetable.dto.allocation.FacultySubjectAllocationResponse;

public interface FacultySubjectAllocationService {

    /**
     * Save Allocation
     */
    FacultySubjectAllocationResponse saveAllocation(
            FacultySubjectAllocationRequest request);

    /**
     * Update Allocation
     */
    FacultySubjectAllocationResponse updateAllocation(
            Long allocationId,
            FacultySubjectAllocationRequest request);

    /**
     * Delete Allocation
     */
    void deleteAllocation(Long allocationId);

    /**
     * Get Allocation By ID
     */
    FacultySubjectAllocationResponse getAllocationById(
            Long allocationId);

    /**
     * Get All Allocations
     */
    List<FacultySubjectAllocationResponse> getAllAllocations();

    /**
     * Get Allocations By Faculty
     */
    List<FacultySubjectAllocationResponse> getAllocationsByFaculty(
            Long facultyId);

    /**
     * Get Allocations By Subject
     */
    List<FacultySubjectAllocationResponse> getAllocationsBySubject(
            Long subjectId);

    /**
     * Get Allocations By Section
     */
    List<FacultySubjectAllocationResponse> getAllocationsBySection(
            Long sectionId);

    /**
     * Get Allocations By Semester
     */
    List<FacultySubjectAllocationResponse> getAllocationsBySemester(
            Long semesterId);
}