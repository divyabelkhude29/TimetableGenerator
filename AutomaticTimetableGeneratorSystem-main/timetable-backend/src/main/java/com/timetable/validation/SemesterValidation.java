package com.timetable.validation;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.timetable.dao.CourseDAO;
import com.timetable.dao.SemesterDAO;
import com.timetable.dto.semester.SemesterRequest;
import com.timetable.entity.Course;
import com.timetable.entity.Semester;
import com.timetable.exception.ResourceNotFoundException;

@Component
public class SemesterValidation {

    private final SemesterDAO semesterDAO;
    private final CourseDAO courseDAO;

    public SemesterValidation(SemesterDAO semesterDAO,
                              CourseDAO courseDAO) {
        this.semesterDAO = semesterDAO;
        this.courseDAO = courseDAO;
    }

    /**
     * Validate before Save
     */
    public Course validateForSave(SemesterRequest request) {

        Course course = courseDAO.findById(request.getCourseId());

        if (course == null) {
            throw new ResourceNotFoundException(
                    "Course not found with ID : " + request.getCourseId());
        }

        Semester existing = semesterDAO.findBySemesterNumberAndCourse(
                request.getSemesterNumber(),
                course);

        if (existing != null) {
            throw new IllegalArgumentException(
                    "Semester " + request.getSemesterNumber()
                    + " already exists for this course.");
        }

        validateDates(request.getStartDate(), request.getEndDate());

        return course;
    }

    /**
     * Validate before Update
     */
    public Course validateForUpdate(Long semesterId,
                                    SemesterRequest request) {

        Course course = courseDAO.findById(request.getCourseId());

        if (course == null) {
            throw new ResourceNotFoundException(
                    "Course not found with ID : " + request.getCourseId());
        }

        Semester duplicate =
                semesterDAO.findBySemesterNumberAndCourse(
                        request.getSemesterNumber(),
                        course);

        if (duplicate != null &&
                !duplicate.getSemesterId().equals(semesterId)) {

            throw new IllegalArgumentException(
                    "Semester number already exists for this course.");
        }

        validateDates(request.getStartDate(), request.getEndDate());

        return course;
    }

    /**
     * Validate Start Date and End Date
     */
    private void validateDates(LocalDate startDate,
                               LocalDate endDate) {

        if (startDate != null &&
                endDate != null &&
                startDate.isAfter(endDate)) {

            throw new IllegalArgumentException(
                    "Start date cannot be after end date.");
        }
    }
}