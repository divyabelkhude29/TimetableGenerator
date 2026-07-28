package com.timetable.validation;

import org.springframework.stereotype.Component;

import com.timetable.dao.CourseDAO;
import com.timetable.dao.DepartmentDAO;
import com.timetable.dao.FacultyDAO;
import com.timetable.dao.SemesterDAO;
import com.timetable.dao.SubjectDAO;
import com.timetable.dto.subject.SubjectRequest;
import com.timetable.entity.Course;
import com.timetable.entity.Department;
import com.timetable.entity.Faculty;
import com.timetable.entity.Semester;
import com.timetable.entity.Subject;
import com.timetable.exception.ResourceNotFoundException;

@Component
public class SubjectValidation {

    private final SubjectDAO subjectDAO;
    private final DepartmentDAO departmentDAO;
    private final CourseDAO courseDAO;
    private final SemesterDAO semesterDAO;
    private final FacultyDAO facultyDAO;

    public SubjectValidation(SubjectDAO subjectDAO,
                             DepartmentDAO departmentDAO,
                             CourseDAO courseDAO,
                             SemesterDAO semesterDAO,
                             FacultyDAO facultyDAO) {

        this.subjectDAO = subjectDAO;
        this.departmentDAO = departmentDAO;
        this.courseDAO = courseDAO;
        this.semesterDAO = semesterDAO;
        this.facultyDAO = facultyDAO;
    }

    /**
     * Validate Before Create
     */
    public void validateForCreate(SubjectRequest request) {

        if (subjectDAO.findBySubjectCode(request.getSubjectCode()) != null) {
            throw new IllegalArgumentException(
                    "Subject Code already exists.");
        }

        validateDepartment(request.getDepartmentId());
        validateCourse(request.getCourseId());
        validateSemester(request.getSemesterId());

        if (request.getFacultyId() != null) {
            validateFaculty(request.getFacultyId());
        }
    }

    /**
     * Validate Before Update
     */
    public void validateForUpdate(Long subjectId,
                                  SubjectRequest request) {

        Subject subject = subjectDAO.findById(subjectId);

        if (subject == null) {
            throw new ResourceNotFoundException(
                    "Subject not found with ID : " + subjectId);
        }

        Subject duplicate =
                subjectDAO.findBySubjectCode(request.getSubjectCode());

        if (duplicate != null &&
                !duplicate.getSubjectId().equals(subjectId)) {

            throw new IllegalArgumentException(
                    "Subject Code already exists.");
        }

        validateDepartment(request.getDepartmentId());
        validateCourse(request.getCourseId());
        validateSemester(request.getSemesterId());

        if (request.getFacultyId() != null) {
            validateFaculty(request.getFacultyId());
        }
    }

    /**
     * Validate Department
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
     * Validate Course
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
     * Validate Semester
     */
    public Semester validateSemester(Long semesterId) {

        Semester semester = semesterDAO.findById(semesterId);

        if (semester == null) {
            throw new ResourceNotFoundException(
                    "Semester not found with ID : " + semesterId);
        }

        return semester;
    }

    /**
     * Validate Faculty
     */
    public Faculty validateFaculty(Long facultyId) {

        Faculty faculty = facultyDAO.findById(facultyId);

        if (faculty == null) {
            throw new ResourceNotFoundException(
                    "Faculty not found with ID : " + facultyId);
        }

        return faculty;
    }
}