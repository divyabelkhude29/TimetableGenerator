package com.timetable.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.ClassroomDAO;
import com.timetable.dao.FacultySubjectAllocationDAO;
import com.timetable.dao.SectionDAO;
import com.timetable.dao.TimeSlotDAO;
import com.timetable.dao.TimetableDAO;
import com.timetable.dto.timetable.TimetableRequest;
import com.timetable.dto.timetable.TimetableResponse;
import com.timetable.entity.Classroom;
import com.timetable.entity.Faculty;
import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.Section;
import com.timetable.entity.TimeSlot;
import com.timetable.entity.Timetable;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.mapper.TimetableMapper;
import com.timetable.service.TimetableService;

@Service
@Transactional
public class TimetableServiceImpl implements TimetableService {

    private final TimetableDAO timetableDAO;
    private final FacultySubjectAllocationDAO allocationDAO;
    private final ClassroomDAO classroomDAO;
    private final TimeSlotDAO timeSlotDAO;
    private final SectionDAO sectionDAO;

    public TimetableServiceImpl(
            TimetableDAO timetableDAO,
            FacultySubjectAllocationDAO allocationDAO,
            ClassroomDAO classroomDAO,
            TimeSlotDAO timeSlotDAO,
            SectionDAO sectionDAO) {

        this.timetableDAO = timetableDAO;
        this.allocationDAO = allocationDAO;
        this.classroomDAO = classroomDAO;
        this.timeSlotDAO = timeSlotDAO;
        this.sectionDAO = sectionDAO;
    }

    /**
     * Save Timetable
     */
    @Override
    public TimetableResponse saveTimetable(TimetableRequest request) {

        FacultySubjectAllocation allocation =
                allocationDAO.findById(request.getAllocationId());

        if (allocation == null) {
            throw new ResourceNotFoundException(
                    "Allocation not found with ID : "
                            + request.getAllocationId());
        }

        Classroom classroom =
                classroomDAO.findById(request.getClassroomId());

        if (classroom == null) {
            throw new ResourceNotFoundException(
                    "Classroom not found with ID : "
                            + request.getClassroomId());
        }

        TimeSlot timeSlot =
                timeSlotDAO.findById(request.getTimeSlotId());

        if (timeSlot == null) {
            throw new ResourceNotFoundException(
                    "Time Slot not found with ID : "
                            + request.getTimeSlotId());
        }

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

        Timetable timetable = TimetableMapper.toEntity(request);

        timetable.setAllocation(allocation);
        timetable.setClassroom(classroom);
        timetable.setTimeSlot(timeSlot);

        timetable = timetableDAO.save(timetable);

        return TimetableMapper.toResponse(timetable);
    }

    /**
     * Update Timetable
     */
    @Override
    public TimetableResponse updateTimetable(
            Long timetableId,
            TimetableRequest request) {

        Timetable timetable =
                timetableDAO.findById(timetableId);

        if (timetable == null) {
            throw new ResourceNotFoundException(
                    "Timetable not found with ID : "
                            + timetableId);
        }

        FacultySubjectAllocation allocation =
                allocationDAO.findById(request.getAllocationId());

        if (allocation == null) {
            throw new ResourceNotFoundException(
                    "Allocation not found with ID : "
                            + request.getAllocationId());
        }

        Classroom classroom =
                classroomDAO.findById(request.getClassroomId());

        if (classroom == null) {
            throw new ResourceNotFoundException(
                    "Classroom not found with ID : "
                            + request.getClassroomId());
        }

        TimeSlot timeSlot =
                timeSlotDAO.findById(request.getTimeSlotId());

        if (timeSlot == null) {
            throw new ResourceNotFoundException(
                    "Time Slot not found with ID : "
                            + request.getTimeSlotId());
        }

        TimetableMapper.updateEntity(timetable, request);

        timetable.setAllocation(allocation);
        timetable.setClassroom(classroom);
        timetable.setTimeSlot(timeSlot);

        timetable = timetableDAO.update(timetable);

        return TimetableMapper.toResponse(timetable);
    }

    /**
     * Delete Timetable
     */
    @Override
    public void deleteTimetable(Long timetableId) {

        Timetable timetable =
                timetableDAO.findById(timetableId);

        if (timetable == null) {
            throw new ResourceNotFoundException(
                    "Timetable not found with ID : "
                            + timetableId);
        }

        timetableDAO.delete(timetableId);
    }

    /**
     * Get Timetable By ID
     */
    @Override
    @Transactional(readOnly = true)
    public TimetableResponse getTimetableById(Long timetableId) {

        Timetable timetable =
                timetableDAO.findById(timetableId);

        if (timetable == null) {
            throw new ResourceNotFoundException(
                    "Timetable not found with ID : "
                            + timetableId);
        }

        return TimetableMapper.toResponse(timetable);
    }

    /**
     * Get All Timetables
     */
    @Override
    @Transactional(readOnly = true)
    public List<TimetableResponse> getAllTimetables() {

        return timetableDAO.findAll()
                .stream()
                .map(TimetableMapper::toResponse)
                .collect(Collectors.toList());
    }

/**
 * Get Timetables By Day
 */
@Override
@Transactional(readOnly = true)
public List<TimetableResponse> getTimetablesByDay(
        String dayOfWeek) {

    return timetableDAO.findByDay(dayOfWeek)
            .stream()
            .map(TimetableMapper::toResponse)
            .collect(Collectors.toList());
}

/**
 * Get Timetables By Classroom
 */
@Override
@Transactional(readOnly = true)
public List<TimetableResponse> getTimetablesByClassroom(
        Long classroomId) {

    Classroom classroom = classroomDAO.findById(classroomId);

    if (classroom == null) {
        throw new ResourceNotFoundException(
                "Classroom not found with ID : " + classroomId);
    }

    return timetableDAO.findByClassroom(classroom)
            .stream()
            .map(TimetableMapper::toResponse)
            .collect(Collectors.toList());
}

/**
 * Get Timetables By Faculty
 */
@Override
@Transactional(readOnly = true)
public List<TimetableResponse> getTimetablesByFaculty(
        Long facultyId) {

    FacultySubjectAllocation allocation =
            allocationDAO.findById(facultyId);

    if (allocation == null) {
        throw new ResourceNotFoundException(
                "Faculty not found with ID : " + facultyId);
    }

    Faculty faculty = allocation.getFaculty();

    return timetableDAO.findByFaculty(faculty)
            .stream()
            .map(TimetableMapper::toResponse)
            .collect(Collectors.toList());
}

/**
 * Get Timetables By Section
 */
@Override
@Transactional(readOnly = true)
public List<TimetableResponse> getTimetablesBySection(
        Long sectionId) {

    Section section = sectionDAO.findById(sectionId);

    if (section == null) {
        throw new ResourceNotFoundException(
                "Section not found with ID : " + sectionId);
    }

    return timetableDAO.findBySection(section)
            .stream()
            .map(TimetableMapper::toResponse)
            .collect(Collectors.toList());
}

/**
 * Get Timetables By Allocation
 */
@Override
@Transactional(readOnly = true)
public List<TimetableResponse> getTimetablesByAllocation(
        Long allocationId) {

    FacultySubjectAllocation allocation =
            allocationDAO.findById(allocationId);

    if (allocation == null) {
        throw new ResourceNotFoundException(
                "Allocation not found with ID : " + allocationId);
    }

    return timetableDAO.findByAllocation(allocation)
            .stream()
            .map(TimetableMapper::toResponse)
            .collect(Collectors.toList());
}
}