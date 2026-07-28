package com.timetable.validation;

import org.springframework.stereotype.Component;

import com.timetable.dao.ClassroomDAO;
import com.timetable.dto.classroom.ClassroomRequest;
import com.timetable.entity.Classroom;
import com.timetable.exception.ResourceNotFoundException;

@Component
public class ClassroomValidation {

    private final ClassroomDAO classroomDAO;

    public ClassroomValidation(ClassroomDAO classroomDAO) {
        this.classroomDAO = classroomDAO;
    }

    /**
     * Validate Before Create
     */
    public void validateForCreate(ClassroomRequest request) {

        if (classroomDAO.findByRoomNumber(request.getRoomNumber()) != null) {
            throw new IllegalArgumentException(
                    "Room number already exists.");
        }
    }

    /**
     * Validate Before Update
     */
    public void validateForUpdate(Long classroomId,
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
    }

    /**
     * Validate Classroom Exists
     */
    public Classroom validateClassroom(Long classroomId) {

        Classroom classroom = classroomDAO.findById(classroomId);

        if (classroom == null) {
            throw new ResourceNotFoundException(
                    "Classroom not found with ID : " + classroomId);
        }

        return classroom;
    }

    /**
     * Validate Room Number Exists
     */
    public Classroom validateRoomNumber(String roomNumber) {

        Classroom classroom = classroomDAO.findByRoomNumber(roomNumber);

        if (classroom == null) {
            throw new ResourceNotFoundException(
                    "Classroom not found with Room Number : " + roomNumber);
        }

        return classroom;
    }
}