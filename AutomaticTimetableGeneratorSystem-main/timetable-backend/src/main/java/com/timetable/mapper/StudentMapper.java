package com.timetable.mapper;

import com.timetable.dto.student.StudentRequest;
import com.timetable.dto.student.StudentResponse;
import com.timetable.entity.Course;
import com.timetable.entity.Department;
import com.timetable.entity.Semester;
import com.timetable.entity.Student;

public class StudentMapper {

    private StudentMapper() {
    }

    /**
     * Convert Request DTO to Entity
     */
    public static Student toEntity(StudentRequest request,
                                   Department department,
                                   Course course,
                                   Semester semester) {

        Student student = new Student();

        student.setRollNumber(request.getRollNumber());
        student.setRegisterNumber(request.getRegisterNumber());

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());

        student.setGender(request.getGender());
        student.setDateOfBirth(request.getDateOfBirth());

        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setAddress(request.getAddress());

        student.setAdmissionYear(request.getAdmissionYear());
        student.setCurrentYear(request.getCurrentYear());

        student.setDepartment(department);
        student.setCourse(course);
        student.setSemester(semester);

        student.setActive(request.getActive());

        return student;
    }

    /**
     * Update Existing Entity
     */
    public static void updateEntity(Student student,
                                    StudentRequest request,
                                    Department department,
                                    Course course,
                                    Semester semester) {

        student.setRollNumber(request.getRollNumber());
        student.setRegisterNumber(request.getRegisterNumber());

        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());

        student.setGender(request.getGender());
        student.setDateOfBirth(request.getDateOfBirth());

        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setAddress(request.getAddress());

        student.setAdmissionYear(request.getAdmissionYear());
        student.setCurrentYear(request.getCurrentYear());

        student.setDepartment(department);
        student.setCourse(course);
        student.setSemester(semester);

        student.setActive(request.getActive());
    }

    /**
     * Convert Entity to Response DTO
     */
    public static StudentResponse toResponse(Student student) {

        StudentResponse response = new StudentResponse();

        response.setStudentId(student.getStudentId());

        response.setRollNumber(student.getRollNumber());
        response.setRegisterNumber(student.getRegisterNumber());

        response.setFirstName(student.getFirstName());
        response.setLastName(student.getLastName());

        response.setGender(student.getGender());
        response.setDateOfBirth(student.getDateOfBirth());

        response.setEmail(student.getEmail());
        response.setPhone(student.getPhone());
        response.setAddress(student.getAddress());

        response.setAdmissionYear(student.getAdmissionYear());
        response.setCurrentYear(student.getCurrentYear());

        response.setActive(student.getActive());

        if (student.getDepartment() != null) {

            response.setDepartmentId(student.getDepartment().getDepartmentId());
            response.setDepartmentCode(student.getDepartment().getDepartmentCode());
            response.setDepartmentName(student.getDepartment().getDepartmentName());
        }

        if (student.getCourse() != null) {

            response.setCourseId(student.getCourse().getCourseId());
            response.setCourseCode(student.getCourse().getCourseCode());
            response.setCourseName(student.getCourse().getCourseName());
        }

        if (student.getSemester() != null) {

            response.setSemesterId(student.getSemester().getSemesterId());
            response.setSemesterNumber(student.getSemester().getSemesterNumber());
            response.setSemesterName(student.getSemester().getSemesterName());
        }

        return response;
    }
}