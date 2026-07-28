package com.timetable.mapper;

import com.timetable.dto.classroom.ClassroomRequest;
import com.timetable.dto.classroom.ClassroomResponse;
import com.timetable.entity.Classroom;

public class ClassroomMapper {

    private ClassroomMapper() {
    }

    /**
     * Request DTO -> Entity
     */
    public static Classroom toEntity(ClassroomRequest request) {

        Classroom classroom = new Classroom();

        classroom.setRoomNumber(request.getRoomNumber());
        classroom.setBuildingName(request.getBuildingName());
        classroom.setFloorNumber(request.getFloorNumber());
        classroom.setCapacity(request.getCapacity());
        classroom.setRoomType(request.getRoomType());
        classroom.setActive(request.getActive());

        return classroom;
    }

    /**
     * Update Existing Entity
     */
    public static void updateEntity(Classroom classroom,
                                    ClassroomRequest request) {

        classroom.setRoomNumber(request.getRoomNumber());
        classroom.setBuildingName(request.getBuildingName());
        classroom.setFloorNumber(request.getFloorNumber());
        classroom.setCapacity(request.getCapacity());
        classroom.setRoomType(request.getRoomType());
        classroom.setActive(request.getActive());
    }

    /**
     * Entity -> Response DTO
     */
    public static ClassroomResponse toResponse(Classroom classroom) {

        ClassroomResponse response = new ClassroomResponse();

        response.setClassroomId(classroom.getClassroomId());
        response.setRoomNumber(classroom.getRoomNumber());
        response.setBuildingName(classroom.getBuildingName());
        response.setFloorNumber(classroom.getFloorNumber());
        response.setCapacity(classroom.getCapacity());
        response.setRoomType(classroom.getRoomType());
        response.setActive(classroom.getActive());

        return response;
    }
}