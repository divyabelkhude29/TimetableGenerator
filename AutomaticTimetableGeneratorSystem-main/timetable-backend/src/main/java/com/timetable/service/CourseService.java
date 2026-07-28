package com.timetable.service;

import java.util.List;

import com.timetable.dto.course.CourseRequest;
import com.timetable.dto.course.CourseResponse;

public interface CourseService {

    CourseResponse saveCourse(CourseRequest request);

    CourseResponse updateCourse(Long courseId, CourseRequest request);

    void deleteCourse(Long courseId);

    CourseResponse getCourseById(Long courseId);

    List<CourseResponse> getAllCourses();

}