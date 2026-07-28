package com.timetable.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.CourseDAO;
import com.timetable.dao.DepartmentDAO;
import com.timetable.dao.SemesterDAO;
import com.timetable.dao.StudentDAO;
import com.timetable.dto.student.StudentRequest;
import com.timetable.dto.student.StudentResponse;
import com.timetable.entity.Course;
import com.timetable.entity.Department;
import com.timetable.entity.Semester;
import com.timetable.entity.Student;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.mapper.StudentMapper;
import com.timetable.service.StudentService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentDAO studentDAO;
    private final DepartmentDAO departmentDAO;
    private final CourseDAO courseDAO;
    private final SemesterDAO semesterDAO;

    public StudentServiceImpl(StudentDAO studentDAO,
                              DepartmentDAO departmentDAO,
                              CourseDAO courseDAO,
                              SemesterDAO semesterDAO) {

        this.studentDAO = studentDAO;
        this.departmentDAO = departmentDAO;
        this.courseDAO = courseDAO;
        this.semesterDAO = semesterDAO;
    }

    /**
     * Save Student
     */
    @Override
    public StudentResponse saveStudent(StudentRequest request) {

        if (studentDAO.findByRollNumber(request.getRollNumber()) != null) {
            throw new IllegalArgumentException("Roll Number already exists.");
        }

        if (studentDAO.findByRegisterNumber(request.getRegisterNumber()) != null) {
            throw new IllegalArgumentException("Register Number already exists.");
        }

        if (studentDAO.findByEmail(request.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists.");
        }

        Department department = departmentDAO.findById(request.getDepartmentId());

        if (department == null) {
            throw new ResourceNotFoundException(
                    "Department not found with ID : " + request.getDepartmentId());
        }

        Course course = courseDAO.findById(request.getCourseId());

        if (course == null) {
            throw new ResourceNotFoundException(
                    "Course not found with ID : " + request.getCourseId());
        }

        Semester semester = semesterDAO.findById(request.getSemesterId());

        if (semester == null) {
            throw new ResourceNotFoundException(
                    "Semester not found with ID : " + request.getSemesterId());
        }

        Student student = StudentMapper.toEntity(
                request,
                department,
                course,
                semester);

        Student savedStudent = studentDAO.save(student);

        return StudentMapper.toResponse(savedStudent);
    }

    /**
     * Update Student
     */
    @Override
    public StudentResponse updateStudent(Long studentId,
                                         StudentRequest request) {

        Student student = studentDAO.findById(studentId);

        if (student == null) {
            throw new ResourceNotFoundException(
                    "Student not found with ID : " + studentId);
        }

        Student rollStudent = studentDAO.findByRollNumber(request.getRollNumber());

        if (rollStudent != null &&
                !rollStudent.getStudentId().equals(studentId)) {

            throw new IllegalArgumentException("Roll Number already exists.");
        }

        Student regStudent =
                studentDAO.findByRegisterNumber(request.getRegisterNumber());

        if (regStudent != null &&
                !regStudent.getStudentId().equals(studentId)) {

            throw new IllegalArgumentException("Register Number already exists.");
        }

        Student emailStudent =
                studentDAO.findByEmail(request.getEmail());

        if (emailStudent != null &&
                !emailStudent.getStudentId().equals(studentId)) {

            throw new IllegalArgumentException("Email already exists.");
        }

        Department department =
                departmentDAO.findById(request.getDepartmentId());

        if (department == null) {
            throw new ResourceNotFoundException(
                    "Department not found.");
        }

        Course course =
                courseDAO.findById(request.getCourseId());

        if (course == null) {
            throw new ResourceNotFoundException(
                    "Course not found.");
        }

        Semester semester =
                semesterDAO.findById(request.getSemesterId());

        if (semester == null) {
            throw new ResourceNotFoundException(
                    "Semester not found.");
        }

        StudentMapper.updateEntity(
                student,
                request,
                department,
                course,
                semester);

        Student updatedStudent = studentDAO.update(student);

        return StudentMapper.toResponse(updatedStudent);
    }

    /**
     * Delete Student
     */
    @Override
    public void deleteStudent(Long studentId) {

        Student student = studentDAO.findById(studentId);

        if (student == null) {
            throw new ResourceNotFoundException(
                    "Student not found with ID : " + studentId);
        }

        studentDAO.delete(studentId);
    }

    /**
     * Get Student By ID
     */
    @Override
    @Transactional(readOnly = true)
    public StudentResponse getStudentById(Long studentId) {

        Student student = studentDAO.findById(studentId);

        if (student == null) {
            throw new ResourceNotFoundException(
                    "Student not found with ID : " + studentId);
        }

        return StudentMapper.toResponse(student);
    }

    /**
     * Get All Students
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getAllStudents() {

        return studentDAO.findAll()
                .stream()
                .map(StudentMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Student By Roll Number
     */
    @Override
    @Transactional(readOnly = true)
    public StudentResponse getStudentByRollNumber(String rollNumber) {

        Student student = studentDAO.findByRollNumber(rollNumber);

        if (student == null) {
            throw new ResourceNotFoundException(
                    "Student not found.");
        }

        return StudentMapper.toResponse(student);
    }

    /**
     * Get Students By Department
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getStudentsByDepartment(Long departmentId) {

        return studentDAO.findAll()
                .stream()
                .filter(s -> s.getDepartment().getDepartmentId().equals(departmentId))
                .map(StudentMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Students By Course
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getStudentsByCourse(Long courseId) {

        return studentDAO.findAll()
                .stream()
                .filter(s -> s.getCourse().getCourseId().equals(courseId))
                .map(StudentMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Students By Semester
     */
    @Override
    @Transactional(readOnly = true)
    public List<StudentResponse> getStudentsBySemester(Long semesterId) {

        return studentDAO.findAll()
                .stream()
                .filter(s -> s.getSemester().getSemesterId().equals(semesterId))
                .map(StudentMapper::toResponse)
                .collect(Collectors.toList());
    }
}