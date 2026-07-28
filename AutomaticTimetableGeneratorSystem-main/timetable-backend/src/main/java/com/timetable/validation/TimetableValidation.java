package com.timetable.validation;

import org.springframework.stereotype.Component;

import com.timetable.dao.ClassroomDAO;
import com.timetable.dao.FacultySubjectAllocationDAO;
import com.timetable.dao.TimeSlotDAO;
import com.timetable.dao.TimetableDAO;
import com.timetable.dto.timetable.TimetableRequest;
import com.timetable.entity.Classroom;
import com.timetable.entity.Faculty;
import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.Section;
import com.timetable.entity.TimeSlot;
import com.timetable.entity.Timetable;
import com.timetable.exception.ResourceNotFoundException;

@Component
public class TimetableValidation {

    private final TimetableDAO timetableDAO;
    private final FacultySubjectAllocationDAO allocationDAO;
    private final ClassroomDAO classroomDAO;
    private final TimeSlotDAO timeSlotDAO;

    public TimetableValidation(
            TimetableDAO timetableDAO,
            FacultySubjectAllocationDAO allocationDAO,
            ClassroomDAO classroomDAO,
            TimeSlotDAO timeSlotDAO) {

        this.timetableDAO = timetableDAO;
        this.allocationDAO = allocationDAO;
        this.classroomDAO = classroomDAO;
        this.timeSlotDAO = timeSlotDAO;
    }

    /**
     * Validate Before Create
     */
    public void validateForCreate(TimetableRequest request) {

        FacultySubjectAllocation allocation =
                validateAllocation(request.getAllocationId());

        Classroom classroom =
                validateClassroom(request.getClassroomId());

        TimeSlot timeSlot =
                validateTimeSlot(request.getTimeSlotId());

        Faculty faculty = allocation.getFaculty();
        Section section = allocation.getSection();

        if (timetableDAO.existsFacultyClash(
                faculty,
                request.getDayOfWeek(),
                timeSlot)) {

            throw new IllegalArgumentException(
                    "Faculty already has another class during this time slot.");
        }

        if (timetableDAO.existsClassroomClash(
                classroom,
                request.getDayOfWeek(),
                timeSlot)) {

            throw new IllegalArgumentException(
                    "Classroom is already occupied during this time slot.");
        }

        if (timetableDAO.existsSectionClash(
                section,
                request.getDayOfWeek(),
                timeSlot)) {

            throw new IllegalArgumentException(
                    "Section already has another class during this time slot.");
        }
    }

    /**
     * Validate Timetable Exists
     */
    public Timetable validateTimetable(Long timetableId) {

        Timetable timetable = timetableDAO.findById(timetableId);

        if (timetable == null) {
            throw new ResourceNotFoundException(
                    "Timetable not found with ID : " + timetableId);
        }

        return timetable;
    }

    /**
     * Validate Allocation Exists
     */
    public FacultySubjectAllocation validateAllocation(Long allocationId) {

        FacultySubjectAllocation allocation =
                allocationDAO.findById(allocationId);

        if (allocation == null) {
            throw new ResourceNotFoundException(
                    "Allocation not found with ID : " + allocationId);
        }

        return allocation;
    }

    /**
     * Validate Classroom Exists
     */
    public Classroom validateClassroom(Long classroomId) {

        Classroom classroom = classroomDAO.findById(classroomId);

        if (classroom == null) {
            throw new ResourceNotFoundException(
                    "Classroom not found with ID : " + classroomId);
        }

        return classroom;
    }

    /**
     * Validate Time Slot Exists
     */
    public TimeSlot validateTimeSlot(Long timeSlotId) {

        TimeSlot timeSlot = timeSlotDAO.findById(timeSlotId);

        if (timeSlot == null) {
            throw new ResourceNotFoundException(
                    "Time Slot not found with ID : " + timeSlotId);
        }

        return timeSlot;
    }
}