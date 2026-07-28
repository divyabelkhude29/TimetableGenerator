package com.timetable.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.FacultySubjectAllocationDAO;
import com.timetable.dao.SubjectWorkloadDAO;
import com.timetable.dto.subjectworkload.SubjectWorkloadRequest;
import com.timetable.dto.subjectworkload.SubjectWorkloadResponse;
import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.SubjectWorkload;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.mapper.SubjectWorkloadMapper;
import com.timetable.service.SubjectWorkloadService;

@Service
@Transactional
public class SubjectWorkloadServiceImpl implements SubjectWorkloadService {

    private final SubjectWorkloadDAO subjectWorkloadDAO;
    private final FacultySubjectAllocationDAO allocationDAO;

    public SubjectWorkloadServiceImpl(
            SubjectWorkloadDAO subjectWorkloadDAO,
            FacultySubjectAllocationDAO allocationDAO) {

        this.subjectWorkloadDAO = subjectWorkloadDAO;
        this.allocationDAO = allocationDAO;
    }

    /**
     * Save Subject Workload
     */
    @Override
    public SubjectWorkloadResponse saveSubjectWorkload(
            SubjectWorkloadRequest request) {

        FacultySubjectAllocation allocation =
                allocationDAO.findById(request.getAllocationId());

        if (allocation == null) {
            throw new ResourceNotFoundException(
                    "Faculty Subject Allocation not found with ID : "
                            + request.getAllocationId());
        }

        if (subjectWorkloadDAO.existsByAllocation(allocation)) {
            throw new IllegalArgumentException(
                    "Workload already exists for this Faculty Subject Allocation.");
        }

        if (!request.getWeeklyHours().equals(
                request.getTheoryHours() + request.getPracticalHours())) {

            throw new IllegalArgumentException(
                    "Weekly hours must equal Theory hours + Practical hours.");
        }

        SubjectWorkload workload =
                SubjectWorkloadMapper.toEntity(request);

        workload.setAllocation(allocation);

        workload = subjectWorkloadDAO.save(workload);

        return SubjectWorkloadMapper.toResponse(workload);
    }

    /**
     * Update Subject Workload
     */
    @Override
    public SubjectWorkloadResponse updateSubjectWorkload(
            Long workloadId,
            SubjectWorkloadRequest request) {

        SubjectWorkload workload =
                subjectWorkloadDAO.findById(workloadId);

        if (workload == null) {
            throw new ResourceNotFoundException(
                    "Subject Workload not found with ID : "
                            + workloadId);
        }

        FacultySubjectAllocation allocation =
                allocationDAO.findById(request.getAllocationId());

        if (allocation == null) {
            throw new ResourceNotFoundException(
                    "Faculty Subject Allocation not found with ID : "
                            + request.getAllocationId());
        }

        if (subjectWorkloadDAO.existsByAllocationAndNotWorkloadId(
                allocation,
                workloadId)) {

            throw new IllegalArgumentException(
                    "Another workload already exists for this Faculty Subject Allocation.");
        }

        if (!request.getWeeklyHours().equals(
                request.getTheoryHours() + request.getPracticalHours())) {

            throw new IllegalArgumentException(
                    "Weekly hours must equal Theory hours + Practical hours.");
        }

        SubjectWorkloadMapper.updateEntity(
                workload,
                request);

        workload.setAllocation(allocation);

        workload = subjectWorkloadDAO.update(workload);

        return SubjectWorkloadMapper.toResponse(workload);
    }

    /**
     * Delete Subject Workload
     */
    @Override
    public void deleteSubjectWorkload(Long workloadId) {

        SubjectWorkload workload =
                subjectWorkloadDAO.findById(workloadId);

        if (workload == null) {
            throw new ResourceNotFoundException(
                    "Subject Workload not found with ID : "
                            + workloadId);
        }

        subjectWorkloadDAO.delete(workloadId);
    }

    /**
     * Get Subject Workload By ID
     */
    @Override
    @Transactional(readOnly = true)
    public SubjectWorkloadResponse getSubjectWorkloadById(
            Long workloadId) {

        SubjectWorkload workload =
                subjectWorkloadDAO.findById(workloadId);

        if (workload == null) {
            throw new ResourceNotFoundException(
                    "Subject Workload not found with ID : "
                            + workloadId);
        }

        return SubjectWorkloadMapper.toResponse(workload);
    }

    /**
     * Get All Subject Workloads
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubjectWorkloadResponse> getAllSubjectWorkloads() {

        return subjectWorkloadDAO.findAll()
                .stream()
                .map(SubjectWorkloadMapper::toResponse)
                .collect(Collectors.toList());
    }
    /**
     * Get Subject Workload By Allocation
     */
    @Override
    @Transactional(readOnly = true)
    public SubjectWorkloadResponse getSubjectWorkloadByAllocation(
            Long allocationId) {

        FacultySubjectAllocation allocation =
                allocationDAO.findById(allocationId);

        if (allocation == null) {
            throw new ResourceNotFoundException(
                    "Faculty Subject Allocation not found with ID : "
                            + allocationId);
        }

        SubjectWorkload workload =
                subjectWorkloadDAO.findByAllocation(allocation);

        if (workload == null) {
            throw new ResourceNotFoundException(
                    "Subject Workload not found for Allocation ID : "
                            + allocationId);
        }

        return SubjectWorkloadMapper.toResponse(workload);
    }
}