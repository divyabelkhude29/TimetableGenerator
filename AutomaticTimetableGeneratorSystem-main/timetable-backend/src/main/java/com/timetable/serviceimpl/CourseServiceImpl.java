package com.timetable.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.CourseDAO;
import com.timetable.dao.DepartmentDAO;
import com.timetable.dto.course.CourseRequest;
import com.timetable.dto.course.CourseResponse;
import com.timetable.entity.Course;
import com.timetable.entity.Department;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.mapper.CourseMapper;
import com.timetable.service.CourseService;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseDAO courseDAO;
    private final DepartmentDAO departmentDAO;

    public CourseServiceImpl(CourseDAO courseDAO,
                             DepartmentDAO departmentDAO) {
        this.courseDAO = courseDAO;
        this.departmentDAO = departmentDAO;
    }

    /**
     * Save Course
     */
    @Override
    public CourseResponse saveCourse(CourseRequest request) {

        Course existingCourse =
                courseDAO.findByCourseCode(request.getCourseCode());

        if (existingCourse != null) {
            throw new IllegalArgumentException(
                    "Course already exists with code : "
                            + request.getCourseCode());
        }

        Department department =
                departmentDAO.findById(request.getDepartmentId());

        if (department == null) {
            throw new ResourceNotFoundException(
                    "Department not found with ID : "
                            + request.getDepartmentId());
        }

        Course course = CourseMapper.toEntity(request, department);
        course.setDepartment(department);

        Course savedCourse = courseDAO.save(course);

        return CourseMapper.toResponse(savedCourse);
    }

    /**
     * Update Course
     */
    @Override
    public CourseResponse updateCourse(Long courseId,
                                       CourseRequest request) {

        Course course = courseDAO.findById(courseId);

        if (course == null) {
            throw new ResourceNotFoundException(
                    "Course not found with ID : " + courseId);
        }

        Course duplicate =
                courseDAO.findByCourseCode(request.getCourseCode());

        if (duplicate != null &&
                !duplicate.getCourseId().equals(courseId)) {

            throw new IllegalArgumentException(
                    "Course code already exists.");
        }

        Department department =
                departmentDAO.findById(request.getDepartmentId());

        if (department == null) {
            throw new ResourceNotFoundException(
                    "Department not found with ID : "
                            + request.getDepartmentId());
        }

        CourseMapper.updateEntity(course, request, department);

        course.setDepartment(department);

        Course updatedCourse = courseDAO.update(course);

        return CourseMapper.toResponse(updatedCourse);
    }

    /**
     * Delete Course
     */
    @Override
    public void deleteCourse(Long courseId) {

        Course course = courseDAO.findById(courseId);

        if (course == null) {
            throw new ResourceNotFoundException(
                    "Course not found with ID : " + courseId);
        }

        courseDAO.delete(courseId);
    }

    /**
     * Get Course By ID
     */
    @Override
    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Long courseId) {

        Course course = courseDAO.findById(courseId);

        if (course == null) {
            throw new ResourceNotFoundException(
                    "Course not found with ID : " + courseId);
        }

        return CourseMapper.toResponse(course);
    }

    /**
     * Get All Courses
     */
    @Override
    @Transactional(readOnly = true)
    public List<CourseResponse> getAllCourses() {

        return courseDAO.findAll()
                .stream()
                .map(CourseMapper::toResponse)
                .toList();
    }
}