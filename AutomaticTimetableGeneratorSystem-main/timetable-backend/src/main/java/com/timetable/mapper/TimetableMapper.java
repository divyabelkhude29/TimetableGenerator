package com.timetable.mapper;

import com.timetable.dto.timetable.TimetableRequest;
import com.timetable.dto.timetable.TimetableResponse;
import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.Timetable;

public class TimetableMapper {

    private TimetableMapper() {
    }

    /**
     * Request DTO -> Entity
     */
    public static Timetable toEntity(TimetableRequest request) {

        Timetable timetable = new Timetable();

        timetable.setDayOfWeek(request.getDayOfWeek());
        timetable.setActive(request.getActive());

        return timetable;
    }

    /**
     * Update Existing Entity
     */
    public static void updateEntity(
            Timetable timetable,
            TimetableRequest request) {

        timetable.setDayOfWeek(request.getDayOfWeek());
        timetable.setActive(request.getActive());
    }

    /**
     * Entity -> Response DTO
     */
    public static TimetableResponse toResponse(Timetable timetable) {

        TimetableResponse response = new TimetableResponse();

        response.setTimetableId(timetable.getTimetableId());
        response.setDayOfWeek(timetable.getDayOfWeek());
        response.setActive(timetable.getActive());

        // Allocation Details
        FacultySubjectAllocation allocation = timetable.getAllocation();

        if (allocation != null) {

            response.setAllocationId(allocation.getAllocationId());

            response.setAcademicYear(allocation.getAcademicYear());

            // Faculty
            if (allocation.getFaculty() != null) {

                response.setFacultyId(
                        allocation.getFaculty().getFacultyId());

                response.setFacultyName(
                        allocation.getFaculty().getFirstName()
                        + " "
                        + allocation.getFaculty().getLastName());
            }

            // Subject
            if (allocation.getSubject() != null) {

                response.setSubjectId(
                        allocation.getSubject().getSubjectId());

                response.setSubjectName(
                        allocation.getSubject().getSubjectName());
            }

            // Section
            if (allocation.getSection() != null) {

                response.setSectionId(
                        allocation.getSection().getSectionId());

                response.setSectionName(
                        allocation.getSection().getSectionName());
            }

            // Semester
            if (allocation.getSemester() != null) {

                response.setSemesterId(
                        allocation.getSemester().getSemesterId());

                response.setSemesterNumber(
                        allocation.getSemester().getSemesterNumber());
            }
        }

        // Classroom
        if (timetable.getClassroom() != null) {

            response.setClassroomId(
                    timetable.getClassroom().getClassroomId());

            response.setClassroomName(
                    timetable.getClassroom().getRoomNumber());
        }

        // Time Slot
        if (timetable.getTimeSlot() != null) {

            response.setTimeSlotId(
                    timetable.getTimeSlot().getTimeSlotId());

            response.setStartTime(
                    timetable.getTimeSlot().getStartTime().toString());

            response.setEndTime(
                    timetable.getTimeSlot().getEndTime().toString());
        }

        return response;
    }
}