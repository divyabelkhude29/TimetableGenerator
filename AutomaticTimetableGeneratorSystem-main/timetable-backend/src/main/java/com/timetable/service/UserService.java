package com.timetable.service;

import java.util.List;

import com.timetable.dto.auth.LoginRequest;
import com.timetable.dto.auth.LoginResponse;
import com.timetable.dto.auth.RegisterRequest;
import com.timetable.dto.user.ChangePasswordRequest;
import com.timetable.dto.user.UpdateUserRequest;
import com.timetable.dto.user.UserResponse;

public interface UserService {

    String register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    UserResponse getUserById(Long userId);

    UserResponse getUserByUsername(String username);

    List<UserResponse> getAllUsers();

    UserResponse updateUser(Long userId,
                            UpdateUserRequest request);

    void deleteUser(Long userId);

    void changePassword(Long userId,
                        ChangePasswordRequest request);

    void resetPassword(String email);

}