package com.timetable.algorithm;

import java.util.List;

import org.springframework.stereotype.Component;

import com.timetable.dto.timetablegeneration.TimetableSlotDTO;

@Component
public class BacktrackingEngine {

    private final ConstraintChecker constraintChecker;
    private final WorkloadChecker workloadChecker;

    public BacktrackingEngine(
            ConstraintChecker constraintChecker,
            WorkloadChecker workloadChecker) {

        this.constraintChecker = constraintChecker;
        this.workloadChecker = workloadChecker;
    }

    /**
     * Recursive backtracking algorithm.
     *
     * Returns true if a valid timetable
     * can be completed.
     */
    public boolean generateTimetable(
            List<TimetableSlotDTO> candidateSlots,
            List<TimetableSlotDTO> generatedSlots,
            int currentIndex) {

        /*
         * Base Case
         *
         * Every candidate slot has been processed.
         */
        if (currentIndex >= candidateSlots.size()) {

            return true;
        }

        TimetableSlotDTO currentSlot =
                candidateSlots.get(currentIndex);

        /*
         * Check constraints.
         */
        if (constraintChecker.isValid(
                currentSlot,
                generatedSlots)) {

            /*
             * Assign lecture.
             */
            generatedSlots.add(currentSlot);

            workloadChecker.assignLecture(
                    currentSlot.getSubjectId());

            /*
             * Recursive call.
             */
            if (generateTimetable(
                    candidateSlots,
                    generatedSlots,
                    currentIndex + 1)) {

                return true;
            }

            /*
             * Backtrack.
             */
            generatedSlots.remove(currentSlot);

            workloadChecker.removeLecture(
                    currentSlot.getSubjectId());
        }

        /*
         * Skip current slot
         * and continue.
         */
        return generateTimetable(
                candidateSlots,
                generatedSlots,
                currentIndex + 1);
    }

    /**
     * Reset algorithm state
     * before a new generation.
     */
    public void reset() {

        workloadChecker.reset();
    }

}