package com.timetable.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.FacultyAvailabilityDAO;
import com.timetable.dao.FacultyDAO;
import com.timetable.dao.TimeSlotDAO;
import com.timetable.dto.facultyavailability.FacultyAvailabilityRequest;
import com.timetable.dto.facultyavailability.FacultyAvailabilityResponse;
import com.timetable.entity.Faculty;
import com.timetable.entity.FacultyAvailability;
import com.timetable.entity.TimeSlot;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.mapper.FacultyAvailabilityMapper;
import com.timetable.service.FacultyAvailabilityService;

@Service
@Transactional
public class FacultyAvailabilityServiceImpl implements FacultyAvailabilityService {

    private final FacultyAvailabilityDAO facultyAvailabilityDAO;
    private final FacultyDAO facultyDAO;
    private final TimeSlotDAO timeSlotDAO;

    public FacultyAvailabilityServiceImpl(
            FacultyAvailabilityDAO facultyAvailabilityDAO,
            FacultyDAO facultyDAO,
            TimeSlotDAO timeSlotDAO) {

        this.facultyAvailabilityDAO = facultyAvailabilityDAO;
        this.facultyDAO = facultyDAO;
        this.timeSlotDAO = timeSlotDAO;
    }

    /**
     * Save Faculty Availability
     */
    @Override
    public FacultyAvailabilityResponse saveFacultyAvailability(
            FacultyAvailabilityRequest request) {

        Faculty faculty = facultyDAO.findById(request.getFacultyId());

        if (faculty == null) {
            throw new ResourceNotFoundException(
                    "Faculty not found with ID : " + request.getFacultyId());
        }

        TimeSlot timeSlot = timeSlotDAO.findById(request.getTimeSlotId());

        if (timeSlot == null) {
            throw new ResourceNotFoundException(
                    "Time Slot not found with ID : " + request.getTimeSlotId());
        }

        if (facultyAvailabilityDAO.existsByFacultyAndDayAndTimeSlot(
                faculty,
                request.getDayOfWeek(),
                timeSlot)) {

            throw new IllegalArgumentException(
                    "Faculty availability already exists for this day and time slot.");
        }

        FacultyAvailability availability =
                FacultyAvailabilityMapper.toEntity(request);

        availability.setFaculty(faculty);
        availability.setTimeSlot(timeSlot);

        availability = facultyAvailabilityDAO.save(availability);

        return FacultyAvailabilityMapper.toResponse(availability);
    }

    /**
     * Update Faculty Availability
     */
    @Override
    public FacultyAvailabilityResponse updateFacultyAvailability(
            Long availabilityId,
            FacultyAvailabilityRequest request) {

        FacultyAvailability availability =
                facultyAvailabilityDAO.findById(availabilityId);

        if (availability == null) {
            throw new ResourceNotFoundException(
                    "Faculty Availability not found with ID : " + availabilityId);
        }

        Faculty faculty = facultyDAO.findById(request.getFacultyId());

        if (faculty == null) {
            throw new ResourceNotFoundException(
                    "Faculty not found with ID : " + request.getFacultyId());
        }

        TimeSlot timeSlot = timeSlotDAO.findById(request.getTimeSlotId());

        if (timeSlot == null) {
            throw new ResourceNotFoundException(
                    "Time Slot not found with ID : " + request.getTimeSlotId());
        }

        FacultyAvailabilityMapper.updateEntity(
                availability,
                request);

        availability.setFaculty(faculty);
        availability.setTimeSlot(timeSlot);

        availability = facultyAvailabilityDAO.update(availability);

        return FacultyAvailabilityMapper.toResponse(availability);
    }

    /**
     * Delete Faculty Availability
     */
    @Override
    public void deleteFacultyAvailability(Long availabilityId) {

        FacultyAvailability availability =
                facultyAvailabilityDAO.findById(availabilityId);

        if (availability == null) {
            throw new ResourceNotFoundException(
                    "Faculty Availability not found with ID : " + availabilityId);
        }

        facultyAvailabilityDAO.delete(availabilityId);
    }

    /**
     * Get Faculty Availability By ID
     */
    @Override
    @Transactional(readOnly = true)
    public FacultyAvailabilityResponse getFacultyAvailabilityById(
            Long availabilityId) {

        FacultyAvailability availability =
                facultyAvailabilityDAO.findById(availabilityId);

        if (availability == null) {
            throw new ResourceNotFoundException(
                    "Faculty Availability not found with ID : " + availabilityId);
        }

        return FacultyAvailabilityMapper.toResponse(availability);
    }

    /**
     * Get All Faculty Availability
     */
    @Override
    @Transactional(readOnly = true)
    public List<FacultyAvailabilityResponse> getAllFacultyAvailability() {

        return facultyAvailabilityDAO.findAll()
                .stream()
                .map(FacultyAvailabilityMapper::toResponse)
                .collect(Collectors.toList());
    }
    /**
     * Get Faculty Availability By Faculty
     */
    @Override
    @Transactional(readOnly = true)
    public List<FacultyAvailabilityResponse> getFacultyAvailabilityByFaculty(
            Long facultyId) {

        Faculty faculty = facultyDAO.findById(facultyId);

        if (faculty == null) {
            throw new ResourceNotFoundException(
                    "Faculty not found with ID : " + facultyId);
        }

        return facultyAvailabilityDAO.findByFaculty(faculty)
                .stream()
                .map(FacultyAvailabilityMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Faculty Availability By Day
     */
    @Override
    @Transactional(readOnly = true)
    public List<FacultyAvailabilityResponse> getFacultyAvailabilityByDay(
            String dayOfWeek) {

        return facultyAvailabilityDAO.findByDay(dayOfWeek)
                .stream()
                .map(FacultyAvailabilityMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Faculty Availability By Faculty And Day
     */
    @Override
    @Transactional(readOnly = true)
    public List<FacultyAvailabilityResponse> getFacultyAvailabilityByFacultyAndDay(
            Long facultyId,
            String dayOfWeek) {

        Faculty faculty = facultyDAO.findById(facultyId);

        if (faculty == null) {
            throw new ResourceNotFoundException(
                    "Faculty not found with ID : " + facultyId);
        }

        return facultyAvailabilityDAO
                .findByFacultyAndDay(faculty, dayOfWeek)
                .stream()
                .map(FacultyAvailabilityMapper::toResponse)
                .collect(Collectors.toList());
    }
}