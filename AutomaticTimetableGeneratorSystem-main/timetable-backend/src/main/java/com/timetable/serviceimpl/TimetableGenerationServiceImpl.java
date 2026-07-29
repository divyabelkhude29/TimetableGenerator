package com.timetable.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.algorithm.BacktrackingEngine;
import com.timetable.algorithm.CandidateSlotGenerator;
import com.timetable.algorithm.GenerationContext;
import com.timetable.dao.ClassroomDAO;
import com.timetable.dao.FacultySubjectAllocationDAO;
import com.timetable.dao.SubjectWorkloadDAO;
import com.timetable.dao.TimeSlotDAO;
import com.timetable.dao.TimetableDAO;
import com.timetable.dto.timetablegeneration.GenerateTimetableRequest;
import com.timetable.dto.timetablegeneration.TimetableGenerationResponse;
import com.timetable.dto.timetablegeneration.TimetableSlotDTO;
import com.timetable.entity.Classroom;
import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.TimeSlot;
import com.timetable.entity.Timetable;
import com.timetable.service.TimetableGenerationService;

@Service
@Transactional
public class TimetableGenerationServiceImpl
        implements TimetableGenerationService {

    private final TimetableDAO timetableDAO;

    private final FacultySubjectAllocationDAO allocationDAO;

    private final SubjectWorkloadDAO workloadDAO;

    private final ClassroomDAO classroomDAO;

    private final TimeSlotDAO timeSlotDAO;

    private final CandidateSlotGenerator candidateSlotGenerator;

    private final BacktrackingEngine backtrackingEngine;

    public TimetableGenerationServiceImpl(
            TimetableDAO timetableDAO,
            FacultySubjectAllocationDAO allocationDAO,
            SubjectWorkloadDAO workloadDAO,
            ClassroomDAO classroomDAO,
            TimeSlotDAO timeSlotDAO,
            CandidateSlotGenerator candidateSlotGenerator,
            BacktrackingEngine backtrackingEngine) {

        this.timetableDAO = timetableDAO;
        this.allocationDAO = allocationDAO;
        this.workloadDAO = workloadDAO;
        this.classroomDAO = classroomDAO;
        this.timeSlotDAO = timeSlotDAO;
        this.candidateSlotGenerator = candidateSlotGenerator;
        this.backtrackingEngine = backtrackingEngine;
    }

    @Override
    public TimetableGenerationResponse generateTimetable(
            GenerateTimetableRequest request) {

        long start = System.currentTimeMillis();

        TimetableGenerationResponse response =
                new TimetableGenerationResponse();

        List<String> errors =
                validateGeneration(request);

        if (!errors.isEmpty()) {

            response.setSuccess(false);
            response.setConflicts(errors);
            response.setGeneratedAt(LocalDateTime.now());

            return response;
        }

        if (request.isOverwriteExisting()) {

            deleteGeneratedTimetable(request);

        }

        GenerationContext context =
                buildGenerationContext(request);

        List<TimetableSlotDTO> candidateSlots =
                candidateSlotGenerator.generateCandidates(context);

        List<TimetableSlotDTO> generatedSlots =
                new ArrayList<>();

        backtrackingEngine.reset();

        boolean success =
                backtrackingEngine.generateTimetable(
                        candidateSlots,
                        generatedSlots,
                        0);

        if (!success) {

            response.setSuccess(false);
            response.setMessage("Unable to generate timetable.");
            response.setGeneratedAt(LocalDateTime.now());

            return response;
        }

        saveGeneratedSlots(generatedSlots);

        response.setSuccess(true);
        response.setMessage("Timetable generated successfully.");

        response.setAcademicYear(request.getAcademicYear());
        response.setCourseId(request.getCourseId());
        response.setSemesterId(request.getSemesterId());
        response.setSectionId(request.getSectionId());

        response.setTotalClassesGenerated(
                generatedSlots.size());

        response.setTotalHoursAssigned(
                generatedSlots.size());

        response.setExecutionTimeInMilliseconds(
                System.currentTimeMillis() - start);

        response.setGeneratedAt(LocalDateTime.now());

        return response;
    }

    @Override
    public List<TimetableSlotDTO> previewTimetable(
            GenerateTimetableRequest request) {

        GenerationContext context =
                buildGenerationContext(request);

        List<TimetableSlotDTO> candidateSlots =
                candidateSlotGenerator.generateCandidates(context);

        List<TimetableSlotDTO> generatedSlots =
                new ArrayList<>();

        backtrackingEngine.reset();

        backtrackingEngine.generateTimetable(
                candidateSlots,
                generatedSlots,
                0);

        return generatedSlots;
    }

    @Override
    public List<String> validateGeneration(
            GenerateTimetableRequest request) {

        List<String> errors =
                new ArrayList<>();

        if (request.getAcademicYear() == null
                || request.getAcademicYear().isBlank()) {

            errors.add("Academic Year is required.");
        }

        if (request.getCourseId() == null) {

            errors.add("Course is required.");

        }

        if (request.getSemesterId() == null) {

            errors.add("Semester is required.");

        }

        if (request.getSectionId() == null) {

            errors.add("Section is required.");

        }

        return errors;
    }

    @Override
    public void deleteGeneratedTimetable(
            GenerateTimetableRequest request) {

        timetableDAO.deleteBySectionSemesterAndAcademicYear(
                request.getSectionId(),
                request.getSemesterId(),
                request.getAcademicYear());
    }

    @Override
    public TimetableGenerationResponse regenerateTimetable(
            GenerateTimetableRequest request) {

        deleteGeneratedTimetable(request);

        return generateTimetable(request);
    }

    /**
     * Build generation context.
     */
    private GenerationContext buildGenerationContext(
            GenerateTimetableRequest request) {

        GenerationContext context =
                new GenerationContext();

        context.setAllocations(
                allocationDAO.findBySemesterAndSection(
                        request.getSemesterId(),
                        request.getSectionId()));

        context.setWorkloads(
                workloadDAO.findAll());

        context.setClassrooms(
                classroomDAO.findAll());

        context.setTimeSlots(
                timeSlotDAO.findAll());

        return context;
    }

    /**
     * Save generated timetable.
     */
    private void saveGeneratedSlots(
            List<TimetableSlotDTO> slots) {

        for (TimetableSlotDTO dto : slots) {

        	FacultySubjectAllocation allocation =
        	        allocationDAO.findById(
        	                dto.getAllocationId());

            Classroom classroom =
                    classroomDAO.findById(
                            dto.getClassroomId());

            TimeSlot timeSlot =
                    timeSlotDAO.findById(
                            dto.getTimeSlotId());

            Timetable timetable =
                    new Timetable();

            timetable.setAllocation(allocation);

            timetable.setClassroom(classroom);

            timetable.setTimeSlot(timeSlot);

            timetable.setDayOfWeek(
                    dto.getDayOfWeek());

            timetable.setActive(true);

            timetableDAO.save(timetable);
        }
    }

}