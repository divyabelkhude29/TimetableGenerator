package com.timetable.validation;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.timetable.dao.CourseDAO;
import com.timetable.dto.course.CourseRequest;
import com.timetable.entity.Course;
import com.timetable.exception.ValidationException;

@Component
public class CourseValidation {

    private final CourseDAO courseDAO;

    public CourseValidation(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    /**
     * Validate Course for Create
     */
    public void validateForCreate(CourseRequest request) {

        validateRequest(request);

        Course existingCourse = courseDAO.findByCourseCode(request.getCourseCode());

        if (existingCourse != null) {
            throw new IllegalArgumentException(
                    "Course already exists with code: " + request.getCourseCode());
        }
    }

    /**
     * Validate Course for Update
     */
    public void validateForUpdate(Long courseId, CourseRequest request) {

        validateRequest(request);

        Course existingCourse = courseDAO.findByCourseCode(request.getCourseCode());

        if (existingCourse != null &&
                !existingCourse.getCourseId().equals(courseId)) {

            throw new IllegalArgumentException(
                    "Course code already exists: " + request.getCourseCode());
        }
    }

    /**
     * Common Validation
     */
    private void validateRequest(CourseRequest request) {

        if (request == null) {
            throw new ValidationException("Course request cannot be null.");
        }

        if (!StringUtils.hasText(request.getCourseCode())) {
            throw new ValidationException("Course code is required.");
        }

        if (!StringUtils.hasText(request.getCourseName())) {
            throw new ValidationException("Course name is required.");
        }

        if (request.getDurationYears() == null) {
            throw new ValidationException("Duration is required.");
        }

        if (request.getDurationYears() <= 0) {
            throw new ValidationException("Duration must be greater than zero.");
        }
    }

}