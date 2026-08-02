package com.timetable.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.FacultyDAO;
import com.timetable.dao.FacultySubjectAllocationDAO;
import com.timetable.dao.SectionDAO;
import com.timetable.dao.SemesterDAO;
import com.timetable.dao.SubjectDAO;
import com.timetable.dto.allocation.FacultySubjectAllocationRequest;
import com.timetable.dto.allocation.FacultySubjectAllocationResponse;
import com.timetable.entity.Faculty;
import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.Section;
import com.timetable.entity.Semester;
import com.timetable.entity.Subject;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.mapper.FacultySubjectAllocationMapper;
import com.timetable.service.FacultySubjectAllocationService;

@Service
@Transactional
public class FacultySubjectAllocationServiceImpl
        implements FacultySubjectAllocationService {

    private final FacultySubjectAllocationDAO allocationDAO;
    private final FacultyDAO facultyDAO;
    private final SubjectDAO subjectDAO;
    private final SectionDAO sectionDAO;
    private final SemesterDAO semesterDAO;

    public FacultySubjectAllocationServiceImpl(
            FacultySubjectAllocationDAO allocationDAO,
            FacultyDAO facultyDAO,
            SubjectDAO subjectDAO,
            SectionDAO sectionDAO,
            SemesterDAO semesterDAO) {

        this.allocationDAO = allocationDAO;
        this.facultyDAO = facultyDAO;
        this.subjectDAO = subjectDAO;
        this.sectionDAO = sectionDAO;
        this.semesterDAO = semesterDAO;
    }

    /**
     * Save Allocation
     */
    @Override
    public FacultySubjectAllocationResponse saveAllocation(
            FacultySubjectAllocationRequest request) {

        Faculty faculty = facultyDAO.findById(request.getFacultyId());

        if (faculty == null) {
            throw new ResourceNotFoundException(
                    "Faculty not found with ID : " + request.getFacultyId());
        }

        Subject subject = subjectDAO.findById(request.getSubjectId());

        if (subject == null) {
            throw new ResourceNotFoundException(
                    "Subject not found with ID : " + request.getSubjectId());
        }

        Section section = sectionDAO.findById(request.getSectionId());

        if (section == null) {
            throw new ResourceNotFoundException(
                    "Section not found with ID : " + request.getSectionId());
        }

        Semester semester = semesterDAO.findById(request.getSemesterId());

        if (semester == null) {
            throw new ResourceNotFoundException(
                    "Semester not found with ID : " + request.getSemesterId());
        }

        if (allocationDAO.existsAllocation(
                faculty,
                subject,
                section,
                semester,
                request.getAcademicYear())) {

            throw new IllegalArgumentException(
                    "Faculty Subject Allocation already exists.");
        }

        FacultySubjectAllocation allocation =
                FacultySubjectAllocationMapper.toEntity(request);

        allocation.setFaculty(faculty);
        allocation.setSubject(subject);
        allocation.setSection(section);
        allocation.setSemester(semester);

        allocation = allocationDAO.save(allocation);

        return FacultySubjectAllocationMapper.toResponse(allocation);
    }

    /**
     * Update Allocation
     */
    @Override
    public FacultySubjectAllocationResponse updateAllocation(
            Long allocationId,
            FacultySubjectAllocationRequest request) {

        FacultySubjectAllocation allocation =
                allocationDAO.findById(allocationId);

        if (allocation == null) {
            throw new ResourceNotFoundException(
                    "Allocation not found with ID : " + allocationId);
        }

        Faculty faculty = facultyDAO.findById(request.getFacultyId());

        if (faculty == null) {
            throw new ResourceNotFoundException(
                    "Faculty not found with ID : " + request.getFacultyId());
        }

        Subject subject = subjectDAO.findById(request.getSubjectId());

        if (subject == null) {
            throw new ResourceNotFoundException(
                    "Subject not found with ID : " + request.getSubjectId());
        }

        Section section = sectionDAO.findById(request.getSectionId());

        if (section == null) {
            throw new ResourceNotFoundException(
                    "Section not found with ID : " + request.getSectionId());
        }

        Semester semester = semesterDAO.findById(request.getSemesterId());

        if (semester == null) {
            throw new ResourceNotFoundException(
                    "Semester not found with ID : " + request.getSemesterId());
        }

        if (allocationDAO.existsAllocation(
                faculty,
                subject,
                section,
                semester,
                request.getAcademicYear())
                && !(allocation.getFaculty().getFacultyId().equals(faculty.getFacultyId())
                && allocation.getSubject().getSubjectId().equals(subject.getSubjectId())
                && allocation.getSection().getSectionId().equals(section.getSectionId())
                && allocation.getSemester().getSemesterId().equals(semester.getSemesterId())
                && allocation.getAcademicYear().equals(request.getAcademicYear()))) {

            throw new IllegalArgumentException(
                    "Faculty Subject Allocation already exists.");
        }

        FacultySubjectAllocationMapper.updateEntity(allocation, request);

        allocation.setFaculty(faculty);
        allocation.setSubject(subject);
        allocation.setSection(section);
        allocation.setSemester(semester);

        allocation = allocationDAO.update(allocation);

        return FacultySubjectAllocationMapper.toResponse(allocation);
    }

    /**
     * Delete Allocation
     */
    @Override
    public void deleteAllocation(Long allocationId) {

        FacultySubjectAllocation allocation =
                allocationDAO.findById(allocationId);

        if (allocation == null) {
            throw new ResourceNotFoundException(
                    "Allocation not found with ID : " + allocationId);
        }

        allocationDAO.delete(allocationId);
    }

    /**
     * Get Allocation By ID
     */
    @Override
    @Transactional(readOnly = true)
    public FacultySubjectAllocationResponse getAllocationById(
            Long allocationId) {

        FacultySubjectAllocation allocation =
                allocationDAO.findById(allocationId);

        if (allocation == null) {
            throw new ResourceNotFoundException(
                    "Allocation not found with ID : " + allocationId);
        }

        return FacultySubjectAllocationMapper.toResponse(allocation);
    }

    /**
     * Get All Allocations
     */
    @Override
    @Transactional(readOnly = true)
    public List<FacultySubjectAllocationResponse> getAllAllocations() {

        return allocationDAO.findAll()
                .stream()
                .map(FacultySubjectAllocationMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Allocations By Faculty
     */
    @Override
    @Transactional(readOnly = true)
    public List<FacultySubjectAllocationResponse> getAllocationsByFaculty(
            Long facultyId) {

        Faculty faculty = facultyDAO.findById(facultyId);

        if (faculty == null) {
            throw new ResourceNotFoundException(
                    "Faculty not found with ID : " + facultyId);
        }

        return allocationDAO.findByFaculty(faculty)
                .stream()
                .map(FacultySubjectAllocationMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Allocations By Subject
     */
    @Override
    @Transactional(readOnly = true)
    public List<FacultySubjectAllocationResponse> getAllocationsBySubject(
            Long subjectId) {

        Subject subject = subjectDAO.findById(subjectId);

        if (subject == null) {
            throw new ResourceNotFoundException(
                    "Subject not found with ID : " + subjectId);
        }

        return allocationDAO.findBySubject(subject)
                .stream()
                .map(FacultySubjectAllocationMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Allocations By Section
     */
    @Override
    @Transactional(readOnly = true)
    public List<FacultySubjectAllocationResponse> getAllocationsBySection(
            Long sectionId) {

        Section section = sectionDAO.findById(sectionId);

        if (section == null) {
            throw new ResourceNotFoundException(
                    "Section not found with ID : " + sectionId);
        }

        return allocationDAO.findBySection(section)
                .stream()
                .map(FacultySubjectAllocationMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Allocations By Semester
     */
    @Override
    @Transactional(readOnly = true)
    public List<FacultySubjectAllocationResponse> getAllocationsBySemester(
            Long semesterId) {

        Semester semester = semesterDAO.findById(semesterId);

        if (semester == null) {
            throw new ResourceNotFoundException(
                    "Semester not found with ID : " + semesterId);
        }

        return allocationDAO.findBySemester(semester)
                .stream()
                .map(FacultySubjectAllocationMapper::toResponse)
                .collect(Collectors.toList());
    }
}