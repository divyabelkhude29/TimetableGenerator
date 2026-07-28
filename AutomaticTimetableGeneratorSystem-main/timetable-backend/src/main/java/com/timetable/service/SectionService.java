package com.timetable.service;

import java.util.List;

import com.timetable.dto.section.SectionRequest;
import com.timetable.dto.section.SectionResponse;

public interface SectionService {

    /**
     * Save Section
     */
    SectionResponse saveSection(SectionRequest request);

    /**
     * Update Section
     */
    SectionResponse updateSection(Long sectionId,
                                  SectionRequest request);

    /**
     * Delete Section
     */
    void deleteSection(Long sectionId);

    /**
     * Get Section By ID
     */
    SectionResponse getSectionById(Long sectionId);

    /**
     * Get All Sections
     */
    List<SectionResponse> getAllSections();

    /**
     * Get Section By Code
     */
    SectionResponse getSectionByCode(String sectionCode);

    /**
     * Get Sections By Course
     */
    List<SectionResponse> getSectionsByCourse(Long courseId);

    /**
     * Get Sections By Semester
     */
    List<SectionResponse> getSectionsBySemester(Long semesterId);

}