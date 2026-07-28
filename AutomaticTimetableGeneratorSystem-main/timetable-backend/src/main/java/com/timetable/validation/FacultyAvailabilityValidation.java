package com.timetable.validation;

import org.springframework.stereotype.Component;

import com.timetable.dao.FacultyAvailabilityDAO;
import com.timetable.dao.FacultyDAO;
import com.timetable.dao.TimeSlotDAO;
import com.timetable.dto.facultyavailability.FacultyAvailabilityRequest;
import com.timetable.entity.Faculty;
import com.timetable.entity.FacultyAvailability;
import com.timetable.entity.TimeSlot;
import com.timetable.exception.ResourceNotFoundException;

@Component
public class FacultyAvailabilityValidation {

    private final FacultyAvailabilityDAO facultyAvailabilityDAO;
    private final FacultyDAO facultyDAO;
    private final TimeSlotDAO timeSlotDAO;

    public FacultyAvailabilityValidation(
            FacultyAvailabilityDAO facultyAvailabilityDAO,
            FacultyDAO facultyDAO,
            TimeSlotDAO timeSlotDAO) {

        this.facultyAvailabilityDAO = facultyAvailabilityDAO;
        this.facultyDAO = facultyDAO;
        this.timeSlotDAO = timeSlotDAO;
    }

    /**
     * Validate Before Create
     */
    public void validateForCreate(FacultyAvailabilityRequest request) {

        Faculty faculty = validateFaculty(request.getFacultyId());

        TimeSlot timeSlot = validateTimeSlot(request.getTimeSlotId());

        if (facultyAvailabilityDAO.existsByFacultyAndDayAndTimeSlot(
                faculty,
                request.getDayOfWeek(),
                timeSlot)) {

            throw new IllegalArgumentException(
                    "Faculty availability already exists for the selected day and time slot.");
        }
    }

    /**
     * Validate Faculty Availability Exists
     */
    public FacultyAvailability validateAvailability(Long availabilityId) {

        FacultyAvailability availability =
                facultyAvailabilityDAO.findById(availabilityId);

        if (availability == null) {
            throw new ResourceNotFoundException(
                    "Faculty Availability not found with ID : "
                            + availabilityId);
        }

        return availability;
    }

    /**
     * Validate Faculty Exists
     */
    public Faculty validateFaculty(Long facultyId) {

        Faculty faculty = facultyDAO.findById(facultyId);

        if (faculty == null) {
            throw new ResourceNotFoundException(
                    "Faculty not found with ID : "
                            + facultyId);
        }

        return faculty;
    }

    /**
     * Validate Time Slot Exists
     */
    public TimeSlot validateTimeSlot(Long timeSlotId) {

        TimeSlot timeSlot = timeSlotDAO.findById(timeSlotId);

        if (timeSlot == null) {
            throw new ResourceNotFoundException(
                    "Time Slot not found with ID : "
                            + timeSlotId);
        }

        return timeSlot;
    }
}