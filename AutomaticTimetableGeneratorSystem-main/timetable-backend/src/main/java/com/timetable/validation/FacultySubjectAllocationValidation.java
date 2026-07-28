package com.timetable.validation;

import org.springframework.stereotype.Component;

import com.timetable.dao.FacultyDAO;
import com.timetable.dao.FacultySubjectAllocationDAO;
import com.timetable.dao.SectionDAO;
import com.timetable.dao.SemesterDAO;
import com.timetable.dao.SubjectDAO;
import com.timetable.dto.allocation.FacultySubjectAllocationRequest;
import com.timetable.entity.Faculty;
import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.Section;
import com.timetable.entity.Semester;
import com.timetable.entity.Subject;
import com.timetable.exception.ResourceNotFoundException;

@Component
public class FacultySubjectAllocationValidation {

    private final FacultySubjectAllocationDAO allocationDAO;
    private final FacultyDAO facultyDAO;
    private final SubjectDAO subjectDAO;
    private final SectionDAO sectionDAO;
    private final SemesterDAO semesterDAO;

    public FacultySubjectAllocationValidation(
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
     * Validate Before Create
     */
    public void validateForCreate(FacultySubjectAllocationRequest request) {

        Faculty faculty = validateFaculty(request.getFacultyId());
        Subject subject = validateSubject(request.getSubjectId());
        Section section = validateSection(request.getSectionId());
        Semester semester = validateSemester(request.getSemesterId());

        if (allocationDAO.existsAllocation(
                faculty,
                subject,
                section,
                semester,
                request.getAcademicYear())) {

            throw new IllegalArgumentException(
                    "Faculty Subject Allocation already exists.");
        }
    }

    /**
     * Validate Allocation Exists
     */
    public FacultySubjectAllocation validateAllocation(Long allocationId) {

        FacultySubjectAllocation allocation =
                allocationDAO.findById(allocationId);

        if (allocation == null) {
            throw new ResourceNotFoundException(
                    "Allocation not found with ID : " + allocationId);
        }

        return allocation;
    }

    /**
     * Validate Faculty Exists
     */
    public Faculty validateFaculty(Long facultyId) {

        Faculty faculty = facultyDAO.findById(facultyId);

        if (faculty == null) {
            throw new ResourceNotFoundException(
                    "Faculty not found with ID : " + facultyId);
        }

        return faculty;
    }

    /**
     * Validate Subject Exists
     */
    public Subject validateSubject(Long subjectId) {

        Subject subject = subjectDAO.findById(subjectId);

        if (subject == null) {
            throw new ResourceNotFoundException(
                    "Subject not found with ID : " + subjectId);
        }

        return subject;
    }

    /**
     * Validate Section Exists
     */
    public Section validateSection(Long sectionId) {

        Section section = sectionDAO.findById(sectionId);

        if (section == null) {
            throw new ResourceNotFoundException(
                    "Section not found with ID : " + sectionId);
        }

        return section;
    }

    /**
     * Validate Semester Exists
     */
    public Semester validateSemester(Long semesterId) {

        Semester semester = semesterDAO.findById(semesterId);

        if (semester == null) {
            throw new ResourceNotFoundException(
                    "Semester not found with ID : " + semesterId);
        }

        return semester;
    }
}