package com.timetable.validation;

import org.springframework.stereotype.Component;

import com.timetable.dao.CourseDAO;
import com.timetable.dao.DepartmentDAO;
import com.timetable.dao.SemesterDAO;
import com.timetable.dao.StudentDAO;
import com.timetable.dto.student.StudentRequest;
import com.timetable.entity.Course;
import com.timetable.entity.Department;
import com.timetable.entity.Semester;
import com.timetable.entity.Student;
import com.timetable.exception.ResourceNotFoundException;

@Component
public class StudentValidation {

    private final StudentDAO studentDAO;
    private final DepartmentDAO departmentDAO;
    private final CourseDAO courseDAO;
    private final SemesterDAO semesterDAO;

    public StudentValidation(StudentDAO studentDAO,
                             DepartmentDAO departmentDAO,
                             CourseDAO courseDAO,
                             SemesterDAO semesterDAO) {

        this.studentDAO = studentDAO;
        this.departmentDAO = departmentDAO;
        this.courseDAO = courseDAO;
        this.semesterDAO = semesterDAO;
    }

    /**
     * Validate Student Before Create
     */
    public void validateForCreate(StudentRequest request) {

        if (studentDAO.findByRollNumber(request.getRollNumber()) != null) {
            throw new IllegalArgumentException(
                    "Roll Number already exists.");
        }

        if (studentDAO.findByRegisterNumber(request.getRegisterNumber()) != null) {
            throw new IllegalArgumentException(
                    "Register Number already exists.");
        }

        if (studentDAO.findByEmail(request.getEmail()) != null) {
            throw new IllegalArgumentException(
                    "Email already exists.");
        }

        validateDepartment(request.getDepartmentId());
        validateCourse(request.getCourseId());
        validateSemester(request.getSemesterId());
    }

    /**
     * Validate Student Before Update
     */
    public void validateForUpdate(Long studentId,
                                  StudentRequest request) {

        Student student = studentDAO.findById(studentId);

        if (student == null) {
            throw new ResourceNotFoundException(
                    "Student not found with ID : " + studentId);
        }

        Student roll = studentDAO.findByRollNumber(request.getRollNumber());

        if (roll != null &&
                !roll.getStudentId().equals(studentId)) {

            throw new IllegalArgumentException(
                    "Roll Number already exists.");
        }

        Student register =
                studentDAO.findByRegisterNumber(request.getRegisterNumber());

        if (register != null &&
                !register.getStudentId().equals(studentId)) {

            throw new IllegalArgumentException(
                    "Register Number already exists.");
        }

        Student email =
                studentDAO.findByEmail(request.getEmail());

        if (email != null &&
                !email.getStudentId().equals(studentId)) {

            throw new IllegalArgumentException(
                    "Email already exists.");
        }

        validateDepartment(request.getDepartmentId());
        validateCourse(request.getCourseId());
        validateSemester(request.getSemesterId());
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
}