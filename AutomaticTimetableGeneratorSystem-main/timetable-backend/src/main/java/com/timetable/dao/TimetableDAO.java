package com.timetable.dao;

import java.util.List;

import com.timetable.entity.Classroom;
import com.timetable.entity.Faculty;
import com.timetable.entity.FacultySubjectAllocation;
import com.timetable.entity.Section;
import com.timetable.entity.TimeSlot;
import com.timetable.entity.Timetable;

public interface TimetableDAO {

    /**
     * Save Timetable
     */
    Timetable save(Timetable timetable);

    /**
     * Update Timetable
     */
    Timetable update(Timetable timetable);

    /**
     * Delete Timetable
     */
    void delete(Long timetableId);

    /**
     * Find Timetable By ID
     */
    Timetable findById(Long timetableId);

    /**
     * Find All Timetables
     */
    List<Timetable> findAll();

    /**
     * Find Timetables By Day
     */
    List<Timetable> findByDay(String dayOfWeek);

    /**
     * Find Timetables By Classroom
     */
    List<Timetable> findByClassroom(Classroom classroom);

    /**
     * Find Timetables By Faculty
     */
    List<Timetable> findByFaculty(Faculty faculty);

    /**
     * Find Timetables By Section
     */
    List<Timetable> findBySection(Section section);

    /**
     * Check Faculty Clash
     */
    boolean existsFacultyClash(
            Faculty faculty,
            String dayOfWeek,
            TimeSlot timeSlot);

    /**
     * Check Classroom Clash
     */
    boolean existsClassroomClash(
            Classroom classroom,
            String dayOfWeek,
            TimeSlot timeSlot);

    /**
     * Check Section Clash
     */
    boolean existsSectionClash(
            Section section,
            String dayOfWeek,
            TimeSlot timeSlot);

    /**
     * Find Timetables By Allocation
     */
    List<Timetable> findByAllocation(
            FacultySubjectAllocation allocation);
    
    
    void deleteGeneratedTimetable(
            String academicYear,
            Long semesterId,
            Long sectionId);

    void saveAll(
            List<Timetable> timetables);
    
    List<Timetable> findBySectionSemesterAndAcademicYear(
            Long sectionId,
            Long semesterId,
            String academicYear);

    void deleteBySectionSemesterAndAcademicYear(
            Long sectionId,
            Long semesterId,
            String academicYear);
}