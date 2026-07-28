package com.timetable.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.CourseDAO;
import com.timetable.dao.DepartmentDAO;
import com.timetable.dao.SectionDAO;
import com.timetable.dao.SemesterDAO;
import com.timetable.dto.section.SectionRequest;
import com.timetable.dto.section.SectionResponse;
import com.timetable.entity.Course;
import com.timetable.entity.Department;
import com.timetable.entity.Section;
import com.timetable.entity.Semester;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.mapper.SectionMapper;
import com.timetable.service.SectionService;

@Service
@Transactional
public class SectionServiceImpl implements SectionService {

    private final SectionDAO sectionDAO;
    private final DepartmentDAO departmentDAO;
    private final CourseDAO courseDAO;
    private final SemesterDAO semesterDAO;

    public SectionServiceImpl(SectionDAO sectionDAO,
                              DepartmentDAO departmentDAO,
                              CourseDAO courseDAO,
                              SemesterDAO semesterDAO) {

        this.sectionDAO = sectionDAO;
        this.departmentDAO = departmentDAO;
        this.courseDAO = courseDAO;
        this.semesterDAO = semesterDAO;
    }

    /**
     * Save Section
     */
    @Override
    public SectionResponse saveSection(SectionRequest request) {

        if (sectionDAO.existsBySectionCode(request.getSectionCode())) {
            throw new IllegalArgumentException(
                    "Section code already exists.");
        }

        Department department =
                departmentDAO.findById(request.getDepartmentId());

        if (department == null) {
            throw new ResourceNotFoundException(
                    "Department not found with ID : "
                            + request.getDepartmentId());
        }

        Course course =
                courseDAO.findById(request.getCourseId());

        if (course == null) {
            throw new ResourceNotFoundException(
                    "Course not found with ID : "
                            + request.getCourseId());
        }

        Semester semester =
                semesterDAO.findById(request.getSemesterId());

        if (semester == null) {
            throw new ResourceNotFoundException(
                    "Semester not found with ID : "
                            + request.getSemesterId());
        }

        Section section = SectionMapper.toEntity(request);

        section.setDepartment(department);
        section.setCourse(course);
        section.setSemester(semester);

        Section savedSection = sectionDAO.save(section);

        return SectionMapper.toResponse(savedSection);
    }

    /**
     * Update Section
     */
    @Override
    public SectionResponse updateSection(Long sectionId,
                                         SectionRequest request) {

        Section section = sectionDAO.findById(sectionId);

        if (section == null) {
            throw new ResourceNotFoundException(
                    "Section not found with ID : " + sectionId);
        }

        Section duplicate =
                sectionDAO.findBySectionCode(request.getSectionCode());

        if (duplicate != null &&
                !duplicate.getSectionId().equals(sectionId)) {

            throw new IllegalArgumentException(
                    "Section code already exists.");
        }

        Department department =
                departmentDAO.findById(request.getDepartmentId());

        if (department == null) {
            throw new ResourceNotFoundException(
                    "Department not found with ID : "
                            + request.getDepartmentId());
        }

        Course course =
                courseDAO.findById(request.getCourseId());

        if (course == null) {
            throw new ResourceNotFoundException(
                    "Course not found with ID : "
                            + request.getCourseId());
        }

        Semester semester =
                semesterDAO.findById(request.getSemesterId());

        if (semester == null) {
            throw new ResourceNotFoundException(
                    "Semester not found with ID : "
                            + request.getSemesterId());
        }

        SectionMapper.updateEntity(section, request);

        section.setDepartment(department);
        section.setCourse(course);
        section.setSemester(semester);

        Section updatedSection = sectionDAO.update(section);

        return SectionMapper.toResponse(updatedSection);
    }

    /**
     * Delete Section
     */
    @Override
    public void deleteSection(Long sectionId) {

        Section section = sectionDAO.findById(sectionId);

        if (section == null) {
            throw new ResourceNotFoundException(
                    "Section not found with ID : " + sectionId);
        }

        sectionDAO.delete(sectionId);
    }

    /**
     * Get Section By ID
     */
    @Override
    @Transactional(readOnly = true)
    public SectionResponse getSectionById(Long sectionId) {

        Section section = sectionDAO.findById(sectionId);

        if (section == null) {
            throw new ResourceNotFoundException(
                    "Section not found with ID : " + sectionId);
        }

        return SectionMapper.toResponse(section);
    }

    /**
     * Get All Sections
     */
    @Override
    @Transactional(readOnly = true)
    public List<SectionResponse> getAllSections() {

        return sectionDAO.findAll()
                .stream()
                .map(SectionMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Section By Code
     */
    @Override
    @Transactional(readOnly = true)
    public SectionResponse getSectionByCode(String sectionCode) {

        Section section =
                sectionDAO.findBySectionCode(sectionCode);

        if (section == null) {
            throw new ResourceNotFoundException(
                    "Section not found with code : " + sectionCode);
        }

        return SectionMapper.toResponse(section);
    }

    /**
     * Get Sections By Course
     */
    @Override
    @Transactional(readOnly = true)
    public List<SectionResponse> getSectionsByCourse(Long courseId) {

        Course course = courseDAO.findById(courseId);

        if (course == null) {
            throw new ResourceNotFoundException(
                    "Course not found with ID : " + courseId);
        }

        return sectionDAO.findByCourse(course)
                .stream()
                .map(SectionMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Sections By Semester
     */
    @Override
    @Transactional(readOnly = true)
    public List<SectionResponse> getSectionsBySemester(Long semesterId) {

        Semester semester = semesterDAO.findById(semesterId);

        if (semester == null) {
            throw new ResourceNotFoundException(
                    "Semester not found with ID : " + semesterId);
        }

        return sectionDAO.findBySemester(semester)
                .stream()
                .map(SectionMapper::toResponse)
                .collect(Collectors.toList());
    }
}