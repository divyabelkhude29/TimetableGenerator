package com.timetable.mapper;

import com.timetable.dto.subject.SubjectRequest;
import com.timetable.dto.subject.SubjectResponse;
import com.timetable.entity.Course;
import com.timetable.entity.Department;
import com.timetable.entity.Faculty;
import com.timetable.entity.Semester;
import com.timetable.entity.Subject;

public class SubjectMapper {

    private SubjectMapper() {
    }

    /**
     * Request DTO -> Entity
     */
    public static Subject toEntity(SubjectRequest request,
                                   Department department,
                                   Course course,
                                   Semester semester,
                                   Faculty faculty) {

        Subject subject = new Subject();

        subject.setSubjectCode(request.getSubjectCode());
        subject.setSubjectName(request.getSubjectName());
        subject.setCredits(request.getCredits());
        subject.setHoursPerWeek(request.getHoursPerWeek());
        subject.setSubjectType(request.getSubjectType());
        subject.setActive(request.getActive());

        subject.setDepartment(department);
        subject.setCourse(course);
        subject.setSemester(semester);
        subject.setFaculty(faculty);

        return subject;
    }

    /**
     * Update Existing Entity
     */
    public static void updateEntity(Subject subject,
                                    SubjectRequest request,
                                    Department department,
                                    Course course,
                                    Semester semester,
                                    Faculty faculty) {

        subject.setSubjectCode(request.getSubjectCode());
        subject.setSubjectName(request.getSubjectName());
        subject.setCredits(request.getCredits());
        subject.setHoursPerWeek(request.getHoursPerWeek());
        subject.setSubjectType(request.getSubjectType());
        subject.setActive(request.getActive());

        subject.setDepartment(department);
        subject.setCourse(course);
        subject.setSemester(semester);
        subject.setFaculty(faculty);
    }

    /**
     * Entity -> Response DTO
     */
    public static SubjectResponse toResponse(Subject subject) {

        SubjectResponse response = new SubjectResponse();

        response.setSubjectId(subject.getSubjectId());
        response.setSubjectCode(subject.getSubjectCode());
        response.setSubjectName(subject.getSubjectName());
        response.setCredits(subject.getCredits());
        response.setHoursPerWeek(subject.getHoursPerWeek());
        response.setSubjectType(subject.getSubjectType());
        response.setActive(subject.getActive());

        // Department
        if (subject.getDepartment() != null) {
            response.setDepartmentId(subject.getDepartment().getDepartmentId());
            response.setDepartmentCode(subject.getDepartment().getDepartmentCode());
            response.setDepartmentName(subject.getDepartment().getDepartmentName());
        }

        // Course
        if (subject.getCourse() != null) {
            response.setCourseId(subject.getCourse().getCourseId());
            response.setCourseCode(subject.getCourse().getCourseCode());
            response.setCourseName(subject.getCourse().getCourseName());
        }

        // Semester
        if (subject.getSemester() != null) {
            response.setSemesterId(subject.getSemester().getSemesterId());
            response.setSemesterNumber(subject.getSemester().getSemesterNumber());
            response.setSemesterName(subject.getSemester().getSemesterName());
        }

        // Faculty
        if (subject.getFaculty() != null) {
            response.setFacultyId(subject.getFaculty().getFacultyId());
            response.setFacultyCode(subject.getFaculty().getFacultyCode());

            String facultyName = subject.getFaculty().getFirstName();

            if (subject.getFaculty().getLastName() != null &&
                !subject.getFaculty().getLastName().isBlank()) {

                facultyName += " " + subject.getFaculty().getLastName();
            }

            response.setFacultyName(facultyName);
        }

        return response;
    }
}