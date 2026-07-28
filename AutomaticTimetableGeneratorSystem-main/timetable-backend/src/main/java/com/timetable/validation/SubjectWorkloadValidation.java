package com.timetable.validation;

import org.springframework.stereotype.Component;

import com.timetable.dao.FacultySubjectAllocationDAO;
import com.timetable.dao.SubjectWorkloadDAO;
import com.timetable.dto.subjectworkload.SubjectWorkloadRequest;
import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.SubjectWorkload;
import com.timetable.exception.ResourceNotFoundException;

@Component
public class SubjectWorkloadValidation {

    private final SubjectWorkloadDAO subjectWorkloadDAO;
    private final FacultySubjectAllocationDAO allocationDAO;

    public SubjectWorkloadValidation(
            SubjectWorkloadDAO subjectWorkloadDAO,
            FacultySubjectAllocationDAO allocationDAO) {

        this.subjectWorkloadDAO = subjectWorkloadDAO;
        this.allocationDAO = allocationDAO;
    }

    /**
     * Validate Before Create
     */
    public void validateForCreate(SubjectWorkloadRequest request) {

        FacultySubjectAllocation allocation =
                validateAllocation(request.getAllocationId());

        if (subjectWorkloadDAO.existsByAllocation(allocation)) {

            throw new IllegalArgumentException(
                    "Workload already exists for this Faculty Subject Allocation.");
        }

        validateHours(request);
    }

    /**
     * Validate Before Update
     */
    public void validateForUpdate(
            Long workloadId,
            SubjectWorkloadRequest request) {

        validateWorkload(workloadId);

        FacultySubjectAllocation allocation =
                validateAllocation(request.getAllocationId());

        if (subjectWorkloadDAO.existsByAllocationAndNotWorkloadId(
                allocation,
                workloadId)) {

            throw new IllegalArgumentException(
                    "Another workload already exists for this Faculty Subject Allocation.");
        }

        validateHours(request);
    }

    /**
     * Validate Subject Workload Exists
     */
    public SubjectWorkload validateWorkload(Long workloadId) {

        SubjectWorkload workload =
                subjectWorkloadDAO.findById(workloadId);

        if (workload == null) {

            throw new ResourceNotFoundException(
                    "Subject Workload not found with ID : "
                            + workloadId);
        }

        return workload;
    }

    /**
     * Validate Allocation Exists
     */
    public FacultySubjectAllocation validateAllocation(
            Long allocationId) {

        FacultySubjectAllocation allocation =
                allocationDAO.findById(allocationId);

        if (allocation == null) {

            throw new ResourceNotFoundException(
                    "Faculty Subject Allocation not found with ID : "
                            + allocationId);
        }

        return allocation;
    }

    /**
     * Validate Weekly Hours
     */
    public void validateHours(
            SubjectWorkloadRequest request) {

        int total =
                request.getTheoryHours()
                + request.getPracticalHours();

        if (!request.getWeeklyHours().equals(total)) {

            throw new IllegalArgumentException(
                    "Weekly hours must be equal to Theory hours + Practical hours.");
        }
    }
}