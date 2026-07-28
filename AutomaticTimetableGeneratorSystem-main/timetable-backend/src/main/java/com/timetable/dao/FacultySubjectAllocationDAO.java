package com.timetable.dao;

import java.util.List;

import com.timetable.entity.Faculty;
import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.Section;
import com.timetable.entity.Semester;
import com.timetable.entity.Subject;

public interface FacultySubjectAllocationDAO {

    /**
     * Save Allocation
     */
    FacultySubjectAllocation save(FacultySubjectAllocation allocation);

    /**
     * Update Allocation
     */
    FacultySubjectAllocation update(FacultySubjectAllocation allocation);

    /**
     * Delete Allocation
     */
    void delete(Long allocationId);

    /**
     * Find Allocation By ID
     */
    FacultySubjectAllocation findById(Long allocationId);

    /**
     * Find All Allocations
     */
    List<FacultySubjectAllocation> findAll();

    /**
     * Find Allocations By Faculty
     */
    List<FacultySubjectAllocation> findByFaculty(Faculty faculty);

    /**
     * Find Allocations By Subject
     */
    List<FacultySubjectAllocation> findBySubject(Subject subject);

    /**
     * Find Allocations By Section
     */
    List<FacultySubjectAllocation> findBySection(Section section);

    /**
     * Find Allocations By Semester
     */
    List<FacultySubjectAllocation> findBySemester(Semester semester);

    /**
     * Check Duplicate Allocation
     */
    boolean existsAllocation(Faculty faculty,
                             Subject subject,
                             Section section,
                             Semester semester,
                             String academicYear);
}