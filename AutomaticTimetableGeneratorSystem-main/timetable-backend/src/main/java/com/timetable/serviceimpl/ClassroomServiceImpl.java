package com.timetable.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.ClassroomDAO;
import com.timetable.dto.classroom.ClassroomRequest;
import com.timetable.dto.classroom.ClassroomResponse;
import com.timetable.entity.Classroom;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.mapper.ClassroomMapper;
import com.timetable.service.ClassroomService;

@Service
@Transactional
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomDAO classroomDAO;

    public ClassroomServiceImpl(ClassroomDAO classroomDAO) {
        this.classroomDAO = classroomDAO;
    }

    /**
     * Save Classroom
     */
    @Override
    public ClassroomResponse saveClassroom(ClassroomRequest request) {

        Classroom existing = classroomDAO.findByRoomNumber(request.getRoomNumber());

        if (existing != null) {
            throw new IllegalArgumentException(
                    "Room number already exists.");
        }

        Classroom classroom = ClassroomMapper.toEntity(request);

        Classroom savedClassroom = classroomDAO.save(classroom);

        return ClassroomMapper.toResponse(savedClassroom);
    }

    /**
     * Update Classroom
     */
    @Override
    public ClassroomResponse updateClassroom(Long classroomId,
                                             ClassroomRequest request) {

        Classroom classroom = classroomDAO.findById(classroomId);

        if (classroom == null) {
            throw new ResourceNotFoundException(
                    "Classroom not found with ID : " + classroomId);
        }

        Classroom duplicate =
                classroomDAO.findByRoomNumber(request.getRoomNumber());

        if (duplicate != null &&
                !duplicate.getClassroomId().equals(classroomId)) {

            throw new IllegalArgumentException(
                    "Room number already exists.");
        }

        ClassroomMapper.updateEntity(classroom, request);

        Classroom updatedClassroom = classroomDAO.update(classroom);

        return ClassroomMapper.toResponse(updatedClassroom);
    }

    /**
     * Delete Classroom
     */
    @Override
    public void deleteClassroom(Long classroomId) {

        Classroom classroom = classroomDAO.findById(classroomId);

        if (classroom == null) {
            throw new ResourceNotFoundException(
                    "Classroom not found with ID : " + classroomId);
        }

        classroomDAO.delete(classroomId);
    }

    /**
     * Get Classroom By ID
     */
    @Override
    @Transactional(readOnly = true)
    public ClassroomResponse getClassroomById(Long classroomId) {

        Classroom classroom = classroomDAO.findById(classroomId);

        if (classroom == null) {
            throw new ResourceNotFoundException(
                    "Classroom not found with ID : " + classroomId);
        }

        return ClassroomMapper.toResponse(classroom);
    }

    /**
     * Get All Classrooms
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClassroomResponse> getAllClassrooms() {

        return classroomDAO.findAll()
                .stream()
                .map(ClassroomMapper::toResponse)
                .collect(Collectors.toList());
    }

    /**
     * Get Classroom By Room Number
     */
    @Override
    @Transactional(readOnly = true)
    public ClassroomResponse getClassroomByRoomNumber(String roomNumber) {

        Classroom classroom = classroomDAO.findByRoomNumber(roomNumber);

        if (classroom == null) {
            throw new ResourceNotFoundException(
                    "Classroom not found with Room Number : " + roomNumber);
        }

        return ClassroomMapper.toResponse(classroom);
    }
}