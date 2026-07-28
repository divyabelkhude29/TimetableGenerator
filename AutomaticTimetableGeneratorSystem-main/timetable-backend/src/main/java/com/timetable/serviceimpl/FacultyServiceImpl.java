package com.timetable.serviceimpl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.DepartmentDAO;
import com.timetable.dao.FacultyDAO;
import com.timetable.dto.faculty.FacultyRequest;
import com.timetable.dto.faculty.FacultyResponse;
import com.timetable.entity.Department;
import com.timetable.entity.Faculty;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.mapper.FacultyMapper;
import com.timetable.service.FacultyService;
import com.timetable.validation.FacultyValidation;

@Service
@Transactional
public class FacultyServiceImpl implements FacultyService {

    private final FacultyDAO facultyDAO;
    private final DepartmentDAO departmentDAO;
    private final FacultyValidation facultyValidation;

    public FacultyServiceImpl(
            FacultyDAO facultyDAO,
            DepartmentDAO departmentDAO,
            FacultyValidation facultyValidation) {

        this.facultyDAO = facultyDAO;
        this.departmentDAO = departmentDAO;
        this.facultyValidation = facultyValidation;
    }

    /**
     * Save Faculty
     */
    @Override
    public FacultyResponse saveFaculty(FacultyRequest request) {

        facultyValidation.validateForCreate(request);

        Faculty faculty = FacultyMapper.toEntity(request);

        Department department =
                departmentDAO.findById(request.getDepartmentId());

        faculty.setDepartment(department);

        Faculty savedFaculty = facultyDAO.save(faculty);

        return FacultyMapper.toResponse(savedFaculty);
    }

    /**
     * Update Faculty
     */
    @Override
    public FacultyResponse updateFaculty(
            Long facultyId,
            FacultyRequest request) {

        facultyValidation.validateForUpdate(facultyId, request);

        Faculty faculty = facultyDAO.findById(facultyId);

        if (faculty == null) {
            throw new ResourceNotFoundException(
                    "Faculty not found with ID : " + facultyId);
        }

        FacultyMapper.updateEntity(faculty, request);

        Department department =
                departmentDAO.findById(request.getDepartmentId());

        faculty.setDepartment(department);

        Faculty updatedFaculty = facultyDAO.update(faculty);

        return FacultyMapper.toResponse(updatedFaculty);
    }

    /**
     * Delete Faculty
     */
    @Override
    public void deleteFaculty(Long facultyId) {

        Faculty faculty = facultyDAO.findById(facultyId);

        if (faculty == null) {
            throw new ResourceNotFoundException(
                    "Faculty not found with ID : " + facultyId);
        }

        facultyDAO.delete(facultyId);
    }

    /**
     * Get Faculty By ID
     */
    @Override
    @Transactional(readOnly = true)
    public FacultyResponse getFacultyById(Long facultyId) {

        Faculty faculty = facultyDAO.findById(facultyId);

        if (faculty == null) {
            throw new ResourceNotFoundException(
                    "Faculty not found with ID : " + facultyId);
        }

        return FacultyMapper.toResponse(faculty);
    }

    /**
     * Get All Faculties
     */
    @Override
    @Transactional(readOnly = true)
    public List<FacultyResponse> getAllFaculties() {

        return facultyDAO.findAll()
                .stream()
                .map(FacultyMapper::toResponse)
                .toList();
    }

}