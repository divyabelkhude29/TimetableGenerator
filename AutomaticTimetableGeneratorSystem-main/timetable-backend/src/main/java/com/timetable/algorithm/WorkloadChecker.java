package com.timetable.algorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.timetable.dto.timetablegeneration.TimetableSlotDTO;
import com.timetable.entity.SubjectWorkload;

@Component
public class WorkloadChecker {

    /**
     * Subject ID -> Assigned Lecture Count
     */
    private final Map<Long, Integer> assignedWorkload =
            new HashMap<>();

    public WorkloadChecker() {

    }

    /**
     * TODO
     * Database lookup will be added later.
     */
    public boolean canAssign(
            TimetableSlotDTO slot) {

        return true;
    }

    /**
     * Check workload using loaded SubjectWorkload.
     */
    public boolean canAssign(
            TimetableSlotDTO slot,
            SubjectWorkload workload) {

        if (workload == null) {
            return false;
        }

        int assigned =
                assignedWorkload.getOrDefault(
                        slot.getSubjectId(),
                        0);

        return assigned < workload.getWeeklyHours();
    }

    /**
     * Increment workload after assignment.
     */
    public void assignLecture(
            Long subjectId) {

        assignedWorkload.put(
                subjectId,
                assignedWorkload.getOrDefault(subjectId, 0) + 1);
    }

    /**
     * Remove workload during backtracking.
     */
    public void removeLecture(
            Long subjectId) {

        int assigned =
                assignedWorkload.getOrDefault(
                        subjectId,
                        0);

        if (assigned <= 1) {

            assignedWorkload.remove(subjectId);

        } else {

            assignedWorkload.put(
                    subjectId,
                    assigned - 1);
        }
    }

    /**
     * Current assigned workload.
     */
    public int getAssignedWorkload(
            Long subjectId) {

        return assignedWorkload.getOrDefault(
                subjectId,
                0);
    }

    /**
     * Check whether workload is completed.
     */
    public boolean isCompleted(
            SubjectWorkload workload) {

        if (workload == null) {
            return false;
        }

        /*
         * SubjectWorkload
         *      ↓
         * FacultySubjectAllocation
         *      ↓
         * Subject
         */
        Long subjectId =
                workload.getAllocation()
                        .getSubject()
                        .getSubjectId();

        int assigned =
                assignedWorkload.getOrDefault(
                        subjectId,
                        0);

        return assigned >= workload.getWeeklyHours();
    }

    /**
     * Reset tracker before generation.
     */
    public void reset() {

        assignedWorkload.clear();
    }

    /**
     * Verify every workload is satisfied.
     */
    public boolean verifyAllCompleted(
            List<SubjectWorkload> workloads) {

        if (workloads == null || workloads.isEmpty()) {

            return true;
        }

        for (SubjectWorkload workload : workloads) {

            if (!isCompleted(workload)) {

                return false;
            }
        }

        return true;
    }

}