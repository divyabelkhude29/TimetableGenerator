package com.timetable.interfaces;

import com.timetable.dto.auth.LoginRequest;
import com.timetable.dto.auth.LoginResponse;
import com.timetable.dto.auth.RegisterRequest;

public interface UserOperation {

    LoginResponse login(LoginRequest loginRequest);

    String register(RegisterRequest registerRequest);

    void logout(String token);

    void changePassword(Long userId,
                        String oldPassword,
                        String newPassword);

    void resetPassword(String email);

}