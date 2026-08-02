package com.timetable.mapper;

import com.timetable.dto.course.CourseRequest;
import com.timetable.dto.course.CourseResponse;
import com.timetable.entity.Course;
import com.timetable.entity.Department;

public class CourseMapper {

    private CourseMapper() {
    }

    public static Course toEntity(CourseRequest request, Department department) {

        Course course = new Course();

        course.setCourseCode(request.getCourseCode());
        course.setCourseName(request.getCourseName());
        course.setDurationYears(request.getDurationYears());
        course.setTotalSemesters(request.getTotalSemesters());
        course.setDescription(request.getDescription());
        course.setActive(request.getActive());
        course.setDepartment(department);

        return course;
    }

    public static void updateEntity(Course course,
                                    CourseRequest request,
                                    Department department) {

        course.setCourseCode(request.getCourseCode());
        course.setCourseName(request.getCourseName());
        course.setDurationYears(request.getDurationYears());
        course.setTotalSemesters(request.getTotalSemesters());
        course.setDescription(request.getDescription());
        course.setActive(request.getActive());
        course.setDepartment(department);
    }

    public static CourseResponse toResponse(Course course) {

        CourseResponse response = new CourseResponse();

        response.setCourseId(course.getCourseId());
        response.setCourseCode(course.getCourseCode());
        response.setCourseName(course.getCourseName());
        response.setDurationYears(course.getDurationYears());
        response.setTotalSemesters(course.getTotalSemesters());
        response.setDescription(course.getDescription());
        response.setActive(course.getActive());

        if (course.getDepartment() != null) {
            response.setDepartmentId(course.getDepartment().getDepartmentId());
            response.setDepartmentCode(course.getDepartment().getDepartmentCode());
            response.setDepartmentName(course.getDepartment().getDepartmentName());
        }

        return response;
    }
}