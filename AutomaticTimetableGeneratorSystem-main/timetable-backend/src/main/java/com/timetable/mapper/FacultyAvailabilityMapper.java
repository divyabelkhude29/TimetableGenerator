package com.timetable.mapper;

import com.timetable.dto.facultyavailability.FacultyAvailabilityRequest;
import com.timetable.dto.facultyavailability.FacultyAvailabilityResponse;
import com.timetable.entity.FacultyAvailability;

public class FacultyAvailabilityMapper {

    private FacultyAvailabilityMapper() {
    }

    /**
     * Request DTO -> Entity
     */
    public static FacultyAvailability toEntity(
            FacultyAvailabilityRequest request) {

        FacultyAvailability availability = new FacultyAvailability();

        availability.setDayOfWeek(request.getDayOfWeek());
        availability.setAvailable(request.getAvailable());

        return availability;
    }

    /**
     * Update Existing Entity
     */
    public static void updateEntity(
            FacultyAvailability availability,
            FacultyAvailabilityRequest request) {

        availability.setDayOfWeek(request.getDayOfWeek());
        availability.setAvailable(request.getAvailable());
    }

    /**
     * Entity -> Response DTO
     */
    public static FacultyAvailabilityResponse toResponse(
            FacultyAvailability availability) {

        FacultyAvailabilityResponse response =
                new FacultyAvailabilityResponse();

        response.setAvailabilityId(
                availability.getAvailabilityId());

        response.setDayOfWeek(
                availability.getDayOfWeek());

        response.setAvailable(
                availability.getAvailable());

        // Faculty Details
        if (availability.getFaculty() != null) {

            response.setFacultyId(
                    availability.getFaculty().getFacultyId());

            response.setFacultyCode(
                    availability.getFaculty().getFacultyCode());

            response.setFacultyName(
                    availability.getFaculty().getFirstName()
                    + " "
                    + availability.getFaculty().getLastName());
        }

        // Time Slot Details
        if (availability.getTimeSlot() != null) {

            response.setTimeSlotId(
                    availability.getTimeSlot().getTimeSlotId());

            response.setStartTime(
                    availability.getTimeSlot()
                            .getStartTime()
                            .toString());

            response.setEndTime(
                    availability.getTimeSlot()
                            .getEndTime()
                            .toString());
        }

        return response;
    }
}