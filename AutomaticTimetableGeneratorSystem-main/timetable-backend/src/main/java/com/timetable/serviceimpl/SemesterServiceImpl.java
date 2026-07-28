package com.timetable.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.SemesterDAO;
import com.timetable.dto.semester.SemesterRequest;
import com.timetable.dto.semester.SemesterResponse;
import com.timetable.entity.Course;
import com.timetable.entity.Semester;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.mapper.SemesterMapper;
import com.timetable.service.SemesterService;
import com.timetable.validation.SemesterValidation;

@Service
@Transactional
public class SemesterServiceImpl implements SemesterService {

    private final SemesterDAO semesterDAO;
    private final SemesterValidation semesterValidation;

    public SemesterServiceImpl(SemesterDAO semesterDAO,
                               SemesterValidation semesterValidation) {
        this.semesterDAO = semesterDAO;
        this.semesterValidation = semesterValidation;
    }

    /**
     * Save Semester
     */
    @Override
    public SemesterResponse saveSemester(SemesterRequest request) {

        Course course = semesterValidation.validateForSave(request);

        Semester semester = SemesterMapper.toEntity(request);
        semester.setCourse(course);

        Semester savedSemester = semesterDAO.save(semester);

        return SemesterMapper.toResponse(savedSemester);
    }

    /**
     * Update Semester
     */
    @Override
    public SemesterResponse updateSemester(Long semesterId,
                                           SemesterRequest request) {

        Semester semester = semesterDAO.findById(semesterId);

        if (semester == null) {
            throw new ResourceNotFoundException(
                    "Semester not found with ID : " + semesterId);
        }

        Course course =
                semesterValidation.validateForUpdate(
                        semesterId,
                        request);

        SemesterMapper.updateEntity(semester, request);
        semester.setCourse(course);

        Semester updatedSemester =
                semesterDAO.update(semester);

        return SemesterMapper.toResponse(updatedSemester);
    }

    /**
     * Delete Semester
     */
    @Override
    public void deleteSemester(Long semesterId) {

        Semester semester = semesterDAO.findById(semesterId);

        if (semester == null) {
            throw new ResourceNotFoundException(
                    "Semester not found with ID : " + semesterId);
        }

        semesterDAO.delete(semesterId);
    }

    /**
     * Get Semester By ID
     */
    @Override
    @Transactional(readOnly = true)
    public SemesterResponse getSemesterById(Long semesterId) {

        Semester semester = semesterDAO.findById(semesterId);

        if (semester == null) {
            throw new ResourceNotFoundException(
                    "Semester not found with ID : " + semesterId);
        }

        return SemesterMapper.toResponse(semester);
    }

    /**
     * Get All Semesters
     */
    @Override
    @Transactional(readOnly = true)
    public List<SemesterResponse> getAllSemesters() {

        return semesterDAO.findAll()
                .stream()
                .map(SemesterMapper::toResponse)
                .toList();
    }

}