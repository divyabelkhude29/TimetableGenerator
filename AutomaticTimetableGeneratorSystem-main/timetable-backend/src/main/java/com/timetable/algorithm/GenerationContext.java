package com.timetable.algorithm;

import java.util.ArrayList;
import java.util.List;

import com.timetable.dto.timetablegeneration.GenerateTimetableRequest;
import com.timetable.dto.timetablegeneration.TimetableSlotDTO;
import com.timetable.entity.Classroom;
import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.SubjectWorkload;
import com.timetable.entity.TimeSlot;

public class GenerationContext {

    /*
     * Generation Request
     */
    private GenerateTimetableRequest request;

    /*
     * Database Data
     */
    private List<FacultySubjectAllocation> allocations;
    private List<SubjectWorkload> workloads;
    private List<Classroom> classrooms;
    private List<TimeSlot> timeSlots;

    /*
     * Algorithm Data
     */
    private List<TimetableSlotDTO> candidateSlots;
    private List<TimetableSlotDTO> generatedSlots;

    public GenerationContext() {

        allocations = new ArrayList<>();
        workloads = new ArrayList<>();
        classrooms = new ArrayList<>();
        timeSlots = new ArrayList<>();

        candidateSlots = new ArrayList<>();
        generatedSlots = new ArrayList<>();
    }

    public GenerateTimetableRequest getRequest() {
        return request;
    }

    public void setRequest(GenerateTimetableRequest request) {
        this.request = request;
    }

    public List<FacultySubjectAllocation> getAllocations() {
        return allocations;
    }

    public void setAllocations(
            List<FacultySubjectAllocation> allocations) {

        this.allocations = allocations;
    }

    public List<SubjectWorkload> getWorkloads() {
        return workloads;
    }

    public void setWorkloads(
            List<SubjectWorkload> workloads) {

        this.workloads = workloads;
    }

    public List<Classroom> getClassrooms() {
        return classrooms;
    }

    public void setClassrooms(
            List<Classroom> classrooms) {

        this.classrooms = classrooms;
    }

    public List<TimeSlot> getTimeSlots() {
        return timeSlots;
    }

    public void setTimeSlots(
            List<TimeSlot> timeSlots) {

        this.timeSlots = timeSlots;
    }

    public List<TimetableSlotDTO> getCandidateSlots() {
        return candidateSlots;
    }

    public void setCandidateSlots(
            List<TimetableSlotDTO> candidateSlots) {

        this.candidateSlots = candidateSlots;
    }

    public List<TimetableSlotDTO> getGeneratedSlots() {
        return generatedSlots;
    }

    public void setGeneratedSlots(
            List<TimetableSlotDTO> generatedSlots) {

        this.generatedSlots = generatedSlots;
    }

}