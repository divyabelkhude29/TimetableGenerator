package com.timetable.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.CourseDAO;
import com.timetable.dao.DepartmentDAO;
import com.timetable.dao.FacultyDAO;
import com.timetable.dao.SemesterDAO;
import com.timetable.dao.SubjectDAO;
import com.timetable.dto.subject.SubjectRequest;
import com.timetable.dto.subject.SubjectResponse;
import com.timetable.entity.Course;
import com.timetable.entity.Department;
import com.timetable.entity.Faculty;
import com.timetable.entity.Semester;
import com.timetable.entity.Subject;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.mapper.SubjectMapper;
import com.timetable.service.SubjectService;

@Service
@Transactional
public class SubjectServiceImpl implements SubjectService {

    private final SubjectDAO subjectDAO;
    private final DepartmentDAO departmentDAO;
    private final CourseDAO courseDAO;
    private final SemesterDAO semesterDAO;
    private final FacultyDAO facultyDAO;

    public SubjectServiceImpl(SubjectDAO subjectDAO,
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
     * Save Subject
     */
    @Override
    public SubjectResponse saveSubject(SubjectRequest request) {

        if (subjectDAO.findBySubjectCode(request.getSubjectCode()) != null) {
            throw new IllegalArgumentException(
                    "Subject Code already exists.");
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

        Faculty faculty = null;

        if (request.getFacultyId() != null) {

            faculty = facultyDAO.findById(request.getFacultyId());

            if (faculty == null) {
                throw new ResourceNotFoundException(
                        "Faculty not found with ID : " + request.getFacultyId());
            }
        }

        Subject subject = SubjectMapper.toEntity(
                request,
                department,
                course,
                semester,
                faculty);

        Subject savedSubject = subjectDAO.save(subject);

        return SubjectMapper.toResponse(savedSubject);
    }

    /**
     * Update Subject
     */
    @Override
    public SubjectResponse updateSubject(Long subjectId,
                                         SubjectRequest request) {

        Subject subject = subjectDAO.findById(subjectId);

        if (subject == null) {
            throw new ResourceNotFoundException(
                    "Subject not found with ID : " + subjectId);
        }

        Subject duplicate = subjectDAO.findBySubjectCode(request.getSubjectCode());

        if (duplicate != null &&
                !duplicate.getSubjectId().equals(subjectId)) {

            throw new IllegalArgumentException(
                    "Subject Code already exists.");
        }

        Department department = departmentDAO.findById(request.getDepartmentId());

        if (department == null) {
            throw new ResourceNotFoundException("Department not found.");
        }

        Course course = courseDAO.findById(request.getCourseId());

        if (course == null) {
            throw new ResourceNotFoundException("Course not found.");
        }

        Semester semester = semesterDAO.findById(request.getSemesterId());

        if (semester == null) {
            throw new ResourceNotFoundException("Semester not found.");
        }

        Faculty faculty = null;

        if (request.getFacultyId() != null) {

            faculty = facultyDAO.findById(request.getFacultyId());

            if (faculty == null) {
                throw new ResourceNotFoundException("Faculty not found.");
            }
        }

        SubjectMapper.updateEntity(
                subject,
                request,
                department,
                course,
                semester,
                faculty);

        Subject updatedSubject = subjectDAO.update(subject);

        return SubjectMapper.toResponse(updatedSubject);
    }

    /**
     * Delete Subject
     */
    @Override
    public void deleteSubject(Long subjectId) {

        Subject subject = subjectDAO.findById(subjectId);

        if (subject == null) {
            throw new ResourceNotFoundException(
                    "Subject not found with ID : " + subjectId);
        }

        subjectDAO.delete(subjectId);
    }

    /**
     * Get Subject By ID
     */
    @Override
    @Transactional(readOnly = true)
    public SubjectResponse getSubjectById(Long subjectId) {

        Subject subject = subjectDAO.findById(subjectId);

        if (subject == null) {
            throw new ResourceNotFoundException(
                    "Subject not found with ID : " + subjectId);
        }

        return SubjectMapper.toResponse(subject);
    }

    /**
     * Get All Subjects
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponse> getAllSubjects() {

        return subjectDAO.findAll()
                .stream()
                .map(SubjectMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Subject By Code
     */
    @Override
    @Transactional(readOnly = true)
    public SubjectResponse getSubjectByCode(String subjectCode) {

        Subject subject = subjectDAO.findBySubjectCode(subjectCode);

        if (subject == null) {
            throw new ResourceNotFoundException("Subject not found.");
        }

        return SubjectMapper.toResponse(subject);
    }

    /**
     * Get Subjects By Department
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponse> getSubjectsByDepartment(Long departmentId) {

        return subjectDAO.findByDepartmentId(departmentId)
                .stream()
                .map(SubjectMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Subjects By Course
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponse> getSubjectsByCourse(Long courseId) {

        return subjectDAO.findByCourseId(courseId)
                .stream()
                .map(SubjectMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Subjects By Semester
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponse> getSubjectsBySemester(Long semesterId) {

        return subjectDAO.findBySemesterId(semesterId)
                .stream()
                .map(SubjectMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Subjects By Faculty
     */
    @Override
    @Transactional(readOnly = true)
    public List<SubjectResponse> getSubjectsByFaculty(Long facultyId) {

        return subjectDAO.findByFacultyId(facultyId)
                .stream()
                .map(SubjectMapper::toResponse)
                .collect(Collectors.toList());
    }
}