package com.timetable.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

import com.timetable.dto.timetablegeneration.TimetableSlotDTO;
import com.timetable.entity.Classroom;
import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.SubjectWorkload;
import com.timetable.entity.TimeSlot;

@Component
public class CandidateSlotGenerator {

    private static final List<String> WORKING_DAYS =
            Arrays.asList(
                    "MONDAY",
                    "TUESDAY",
                    "WEDNESDAY",
                    "THURSDAY",
                    "FRIDAY");

    public List<TimetableSlotDTO> generateCandidates(
            GenerationContext context) {

        List<TimetableSlotDTO> candidates =
                new ArrayList<>();

        if (context.getAllocations() == null
                || context.getAllocations().isEmpty()) {

            return candidates;
        }

        for (FacultySubjectAllocation allocation
                : context.getAllocations()) {

            SubjectWorkload workload =
                    getWorkload(
                            allocation,
                            context.getWorkloads());

            if (workload == null) {
                continue;
            }

            int requiredHours =
                    workload.getWeeklyHours();

            int allocatedHours = 0;

            outer:

            for (String day : WORKING_DAYS) {

                for (TimeSlot slot
                        : context.getTimeSlots()) {

                    Classroom classroom =
                            getAvailableClassroom(
                                    context.getClassrooms());

                    if (classroom == null) {
                        continue;
                    }

                    TimetableSlotDTO dto =
                            new TimetableSlotDTO();

                    dto.setAllocationId(
                            allocation.getAllocationId());

                    dto.setCourseId(
                            allocation.getSemester()
                                    .getCourse()
                                    .getCourseId());

                    dto.setSemesterId(
                            allocation.getSemester()
                                    .getSemesterId());

                    dto.setAcademicSectionId(
                            allocation.getSection()
                                    .getSectionId());

                    dto.setFacultyId(
                            allocation.getFaculty()
                                    .getFacultyId());

                    dto.setSubjectId(
                            allocation.getSubject()
                                    .getSubjectId());

                    dto.setClassroomId(
                            classroom.getClassroomId());

                    dto.setTimeSlotId(
                            slot.getTimeSlotId());

                    dto.setDayOfWeek(day);

                    dto.setStartTime(
                            slot.getStartTime().toString());

                    dto.setEndTime(
                            slot.getEndTime().toString());

                    dto.setCourseName(
                            allocation.getSemester()
                                    .getCourse()
                                    .getCourseName());

                    dto.setSemesterNumber(
                            allocation.getSemester()
                                    .getSemesterNumber());

                    dto.setSectionName(
                            allocation.getSection()
                                    .getSectionName());

                    dto.setFacultyCode(
                            allocation.getFaculty()
                                    .getFacultyCode());

                    dto.setFacultyName(
                            allocation.getFaculty()
                                    .getFirstName()
                                    + " "
                                    + allocation.getFaculty()
                                    .getLastName());

                    dto.setSubjectCode(
                            allocation.getSubject()
                                    .getSubjectCode());

                    dto.setSubjectName(
                            allocation.getSubject()
                                    .getSubjectName());

                    dto.setGeneratedAutomatically(true);

                    candidates.add(dto);

                    allocatedHours++;

                    if (allocatedHours >= requiredHours) {
                        break outer;
                    }
                }
            }
        }

        context.getCandidateSlots().clear();

        context.getCandidateSlots().addAll(candidates);

        return candidates;
    }

    private SubjectWorkload getWorkload(
            FacultySubjectAllocation allocation,
            List<SubjectWorkload> workloads) {

        if (workloads == null) {
            return null;
        }

        for (SubjectWorkload workload
                : workloads) {

            if (workload.getAllocation()
                    .getAllocationId()
                    .equals(
                            allocation.getAllocationId())) {

                return workload;
            }
        }

        return null;
    }

    private Classroom getAvailableClassroom(
            List<Classroom> classrooms) {

        if (classrooms == null
                || classrooms.isEmpty()) {

            return null;
        }

        return classrooms.get(0);
    }

}