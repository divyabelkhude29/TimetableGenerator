package com.timetable.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.timetable.dto.auth.RegisterRequest;
import com.timetable.dto.user.UpdateUserRequest;
import com.timetable.dto.user.UserResponse;
import com.timetable.entity.User;

@Component
public class UserMapper {

    /**
     * RegisterRequest -> User
     */
    public User toEntity(RegisterRequest request) {

        if (request == null) {
            return null;
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());
        user.setRole(request.getRole());

        return user;
    }

    /**
     * User -> UserResponse
     */
    public UserResponse toResponse(User user) {

        if (user == null) {
            return null;
        }

        UserResponse response = new UserResponse();

        response.setUserId(user.getUserId());
        response.setUsername(user.getUsername());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setMobile(user.getMobile());
        response.setRole(user.getRole());
        response.setEnabled(user.getEnabled());
        response.setAccountLocked(user.getAccountLocked());

        // Add this only if createdAt exists in User entity
        // response.setCreatedAt(user.getCreatedAt());

        return response;
    }

    /**
     * List<User> -> List<UserResponse>
     */
    public List<UserResponse> toResponseList(List<User> users) {

        List<UserResponse> responses = new ArrayList<>();

        if (users == null) {
            return responses;
        }

        for (User user : users) {
            responses.add(toResponse(user));
        }

        return responses;
    }

    /**
     * Update User Entity
     */
    public void updateEntity(User user, UpdateUserRequest request) {

        if (user == null || request == null) {
            return;
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setMobile(request.getMobile());

        if (request.getRole() != null) {
            user.setRole(request.getRole());
        }

        if (request.getEnabled() != null) {
            user.setEnabled(request.getEnabled());
        }

        if (request.getAccountLocked() != null) {
            user.setAccountLocked(request.getAccountLocked());
        }
    }

}