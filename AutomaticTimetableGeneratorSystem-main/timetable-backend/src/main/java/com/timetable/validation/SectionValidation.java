package com.timetable.validation;

import org.springframework.stereotype.Component;

import com.timetable.dao.CourseDAO;
import com.timetable.dao.DepartmentDAO;
import com.timetable.dao.SectionDAO;
import com.timetable.dao.SemesterDAO;
import com.timetable.dto.section.SectionRequest;
import com.timetable.entity.Course;
import com.timetable.entity.Department;
import com.timetable.entity.Section;
import com.timetable.entity.Semester;
import com.timetable.exception.ResourceNotFoundException;

@Component
public class SectionValidation {

    private final SectionDAO sectionDAO;
    private final DepartmentDAO departmentDAO;
    private final CourseDAO courseDAO;
    private final SemesterDAO semesterDAO;

    public SectionValidation(SectionDAO sectionDAO,
                             DepartmentDAO departmentDAO,
                             CourseDAO courseDAO,
                             SemesterDAO semesterDAO) {
        this.sectionDAO = sectionDAO;
        this.departmentDAO = departmentDAO;
        this.courseDAO = courseDAO;
        this.semesterDAO = semesterDAO;
    }

    /**
     * Validate Section Before Create
     */
    public void validateForCreate(SectionRequest request) {

        if (sectionDAO.existsBySectionCode(request.getSectionCode())) {
            throw new IllegalArgumentException(
                    "Section code already exists.");
        }

        validateDepartment(request.getDepartmentId());
        validateCourse(request.getCourseId());
        validateSemester(request.getSemesterId());
    }

    /**
     * Validate Section Before Update
     */
    public void validateForUpdate(Long sectionId,
                                  SectionRequest request) {

        Section section = sectionDAO.findById(sectionId);

        if (section == null) {
            throw new ResourceNotFoundException(
                    "Section not found with ID : " + sectionId);
        }

        Section duplicate =
                sectionDAO.findBySectionCode(request.getSectionCode());

        if (duplicate != null &&
                !duplicate.getSectionId().equals(sectionId)) {

            throw new IllegalArgumentException(
                    "Section code already exists.");
        }

        validateDepartment(request.getDepartmentId());
        validateCourse(request.getCourseId());
        validateSemester(request.getSemesterId());
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
     * Validate Department Exists
     */
    public Department validateDepartment(Long departmentId) {

        Department department = departmentDAO.findById(departmentId);

        if (department == null) {
            throw new ResourceNotFoundException(
                    "Department not found with ID : " + departmentId);
        }

        return department;
    }

    /**
     * Validate Course Exists
     */
    public Course validateCourse(Long courseId) {

        Course course = courseDAO.findById(courseId);

        if (course == null) {
            throw new ResourceNotFoundException(
                    "Course not found with ID : " + courseId);
        }

        return course;
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