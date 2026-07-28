package com.timetable.algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import com.timetable.dto.timetablegeneration.GenerateTimetableRequest;
import com.timetable.dto.timetablegeneration.TimetableSlotDTO;
import com.timetable.entity.TimeSlot;

@Component
public class CandidateSlotBuilder {

    public CandidateSlotBuilder() {

    }

    /**
     * Build candidate timetable slots.
     */
    public List<TimetableSlotDTO> buildSlots(
            GenerateTimetableRequest request,
            List<TimeSlot> timeSlots) {

        List<TimetableSlotDTO> slots = new ArrayList<>();

        if (timeSlots == null || timeSlots.isEmpty()) {
            return slots;
        }

        timeSlots.sort(
                Comparator.comparing(TimeSlot::getSlotOrder));

        for (TimeSlot timeSlot : timeSlots) {

            TimetableSlotDTO slot =
                    new TimetableSlotDTO();

            slot.setDayOfWeek(
                    timeSlot.getDayOfWeek());

            slot.setTimeSlotId(
                    timeSlot.getTimeSlotId());

            slot.setStartTime(
                    timeSlot.getStartTime().toString());

            slot.setEndTime(
                    timeSlot.getEndTime().toString());

            slot.setCourseId(
                    request.getCourseId());

            slot.setSemesterId(
                    request.getSemesterId());

            slot.setAcademicSectionId(
                    request.getSectionId());

            slot.setGeneratedAutomatically(true);

            slots.add(slot);
        }

        return slots;
    }

    public int countSlots(List<TimetableSlotDTO> slots) {

        if (slots == null) {
            return 0;
        }

        return slots.size();
    }

    public boolean hasSlots(List<TimetableSlotDTO> slots) {

        return slots != null && !slots.isEmpty();
    }

}