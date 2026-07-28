package com.timetable.dao;

import java.util.List;

import com.timetable.entity.Course;
import com.timetable.entity.Section;
import com.timetable.entity.Semester;

public interface SectionDAO {

    /**
     * Save Section
     */
    Section save(Section section);

    /**
     * Update Section
     */
    Section update(Section section);

    /**
     * Delete Section
     */
    void delete(Long sectionId);

    /**
     * Find Section By ID
     */
    Section findById(Long sectionId);

    /**
     * Find All Sections
     */
    List<Section> findAll();

    /**
     * Find Section By Section Code
     */
    Section findBySectionCode(String sectionCode);

    /**
     * Find Sections By Course
     */
    List<Section> findByCourse(Course course);

    /**
     * Find Sections By Semester
     */
    List<Section> findBySemester(Semester semester);

    /**
     * Check Section Code Exists
     */
    boolean existsBySectionCode(String sectionCode);

}