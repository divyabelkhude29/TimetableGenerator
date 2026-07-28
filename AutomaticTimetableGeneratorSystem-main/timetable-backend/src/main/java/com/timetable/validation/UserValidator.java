package com.timetable.validation;

import org.springframework.stereotype.Component;

import com.timetable.dao.UserDAO;
import com.timetable.dto.auth.LoginRequest;
import com.timetable.dto.auth.RegisterRequest;
import com.timetable.dto.user.ChangePasswordRequest;
import com.timetable.dto.user.UpdateUserRequest;
import com.timetable.entity.User;
import com.timetable.exception.DuplicateRecordException;
import com.timetable.exception.ValidationException;
import com.timetable.util.RegexValidator;

@Component
public class UserValidator {

    private final UserDAO userDAO;

    public UserValidator(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    /**
     * Validate User Registration
     */
    public void validateRegistration(RegisterRequest request) {

        if (request == null) {
            throw new ValidationException("Request cannot be null.");
        }

        validateUsername(request.getUsername());

        validateEmail(request.getEmail());

        validateMobile(request.getMobile());

        validatePassword(request.getPassword());

        checkDuplicateUsername(request.getUsername());

        checkDuplicateEmail(request.getEmail());

        checkDuplicateMobile(request.getMobile());
    }

    /**
     * Validate Login Request
     */
    public void validateLogin(LoginRequest request) {

        if (request == null) {
            throw new ValidationException("Request cannot be null.");
        }

        if (request.getUsername() == null ||
                request.getUsername().trim().isEmpty()) {

            throw new ValidationException(
                    "username",
                    "Username is required.");
        }

        if (request.getPassword() == null ||
                request.getPassword().trim().isEmpty()) {

            throw new ValidationException(
                    "password",
                    "Password is required.");
        }
    }

    /**
     * Validate Update Request
     */
    public void validateUpdate(UpdateUserRequest request,
                               Long userId) {

        if (request == null) {

            throw new ValidationException(
                    "Request cannot be null.");
        }

        validateEmail(request.getEmail());

        validateMobile(request.getMobile());

        User emailUser = userDAO.findByEmail(request.getEmail());

        if (emailUser != null &&
                !emailUser.getUserId().equals(userId)) {

            throw new DuplicateRecordException(
                    "User",
                    "email",
                    request.getEmail());
        }

        User mobileUser = userDAO.findByMobile(request.getMobile());

        if (mobileUser != null &&
                !mobileUser.getUserId().equals(userId)) {

            throw new DuplicateRecordException(
                    "User",
                    "mobile",
                    request.getMobile());
        }
    }
    /**
     * Validate Change Password Request
     */
    public void validateChangePassword(ChangePasswordRequest request) {

        if (request == null) {
            throw new ValidationException("Request cannot be null.");
        }

        if (request.getOldPassword() == null ||
                request.getOldPassword().trim().isEmpty()) {

            throw new ValidationException(
                    "oldPassword",
                    "Old password is required.");
        }

        if (request.getNewPassword() == null ||
                request.getNewPassword().trim().isEmpty()) {

            throw new ValidationException(
                    "newPassword",
                    "New password is required.");
        }

        validatePassword(request.getNewPassword());
    }

    /**
     * Validate Username
     */
    public void validateUsername(String username) {

        if (username == null || username.trim().isEmpty()) {

            throw new ValidationException(
                    "username",
                    "Username is required.");
        }

        if (!RegexValidator.isValidUsername(username)) {

            throw new ValidationException(
                    "username",
                    "Invalid username format.");
        }
    }

    /**
     * Validate Email
     */
    public void validateEmail(String email) {

        if (email == null || email.trim().isEmpty()) {

            throw new ValidationException(
                    "email",
                    "Email is required.");
        }

        if (!RegexValidator.isValidEmail(email)) {

            throw new ValidationException(
                    "email",
                    "Invalid email address.");
        }
    }

    /**
     * Validate Mobile Number
     */
    public void validateMobile(String mobile) {

        if (mobile == null || mobile.trim().isEmpty()) {

            throw new ValidationException(
                    "mobile",
                    "Mobile number is required.");
        }

        if (!RegexValidator.isValidMobile(mobile)) {

            throw new ValidationException(
                    "mobile",
                    "Invalid mobile number.");
        }
    }

    /**
     * Validate Password
     */
    public void validatePassword(String password) {

        if (password == null || password.trim().isEmpty()) {

            throw new ValidationException(
                    "password",
                    "Password is required.");
        }

        if (!RegexValidator.isValidPassword(password)) {

            throw new ValidationException(
                    "password",
                    "Password does not satisfy security policy.");
        }
    }
    /**
     * Check Duplicate Username
     */
    public void checkDuplicateUsername(String username) {

        if (userDAO.existsByUsername(username)) {

            throw new DuplicateRecordException(
                    "User",
                    "username",
                    username);
        }
    }

    /**
     * Check Duplicate Email
     */
    public void checkDuplicateEmail(String email) {

        if (userDAO.existsByEmail(email)) {

            throw new DuplicateRecordException(
                    "User",
                    "email",
                    email);
        }
    }

    /**
     * Check Duplicate Mobile Number
     */
    public void checkDuplicateMobile(String mobile) {

        if (userDAO.existsByMobile(mobile)) {

            throw new DuplicateRecordException(
                    "User",
                    "mobile",
                    mobile);
        }
    }

    /**
     * Validate User Exists
     */
    public User validateUserExists(Long userId) {

        User user = userDAO.findById(userId);

        if (user == null) {

            throw new ValidationException(
                    "userId",
                    "User does not exist.");
        }

        return user;
    }

    /**
     * Validate Username Exists
     */
    public User validateUsernameExists(String username) {

        User user = userDAO.findByUsername(username);

        if (user == null) {

            throw new ValidationException(
                    "username",
                    "User not found.");
        }

        return user;
    }

    /**
     * Validate Email Exists
     */
    public User validateEmailExists(String email) {

        User user = userDAO.findByEmail(email);

        if (user == null) {

            throw new ValidationException(
                    "email",
                    "Email does not exist.");
        }

        return user;
    }

    /**
     * Validate User Status
     */
    public void validateUserStatus(User user) {

        if (user == null) {

            throw new ValidationException(
                    "user",
                    "User is null.");
        }

        if (Boolean.FALSE.equals(user.getEnabled())) {

            throw new ValidationException(
                    "enabled",
                    "User account is disabled.");
        }

        if (Boolean.TRUE.equals(user.getAccountLocked())) {

            throw new ValidationException(
                    "accountLocked",
                    "User account is locked.");
        }
    }

    /**
     * Validate User Before Login
     */
    public void validateLoginUser(User user) {

        validateUserStatus(user);

        if (user.getPassword() == null ||
                user.getPassword().trim().isEmpty()) {

            throw new ValidationException(
                    "password",
                    "Password not found.");
        }
    }

    /**
     * Validate Password Reset Email
     */
    public void validateResetPassword(String email) {

        validateEmail(email);

        validateEmailExists(email);
    }

}