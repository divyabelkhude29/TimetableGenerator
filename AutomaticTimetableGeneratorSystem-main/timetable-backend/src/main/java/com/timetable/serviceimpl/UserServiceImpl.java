package com.timetable.serviceimpl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.timetable.dao.UserDAO;
import com.timetable.dto.auth.LoginRequest;
import com.timetable.dto.auth.LoginResponse;
import com.timetable.dto.auth.RegisterRequest;
import com.timetable.dto.user.ChangePasswordRequest;
import com.timetable.dto.user.UpdateUserRequest;
import com.timetable.dto.user.UserResponse;
import com.timetable.entity.User;
import com.timetable.exception.AuthenticationException;
import com.timetable.exception.DuplicateRecordException;
import com.timetable.exception.ResourceNotFoundException;
import com.timetable.exception.ValidationException;
import com.timetable.mapper.UserMapper;
import com.timetable.service.UserService;
import com.timetable.util.RegexValidator;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserDAO userDAO,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {

        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    /**
     * Register New User
     */
    @Override
    public String register(RegisterRequest request) {

        validateRegistration(request);

        User user = userMapper.toEntity(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        user.setEnabled(true);
        user.setAccountLocked(false);

        userDAO.save(user);

        return "User Registered Successfully";
    }

    /**
     * Validate Registration
     */
    private void validateRegistration(RegisterRequest request) {

        if (request == null) {
            throw new ValidationException("Request cannot be null.");
        }

        if (!RegexValidator.isValidUsername(request.getUsername())) {
            throw new ValidationException(
                    "username",
                    "Invalid username.");
        }

        if (!RegexValidator.isValidEmail(request.getEmail())) {
            throw new ValidationException(
                    "email",
                    "Invalid email address.");
        }

        if (!RegexValidator.isValidMobile(request.getMobile())) {
            throw new ValidationException(
                    "mobile",
                    "Invalid mobile number.");
        }

        if (!RegexValidator.isValidPassword(request.getPassword())) {
            throw new ValidationException(
                    "password",
                    "Password does not satisfy security policy.");
        }

        if (userDAO.existsByUsername(request.getUsername())) {
            throw new DuplicateRecordException(
                    "User",
                    "username",
                    request.getUsername());
        }

        if (userDAO.existsByEmail(request.getEmail())) {
            throw new DuplicateRecordException(
                    "User",
                    "email",
                    request.getEmail());
        }

        if (userDAO.existsByMobile(request.getMobile())) {
            throw new DuplicateRecordException(
                    "User",
                    "mobile",
                    request.getMobile());
        }
    }

    /**
     * Validate Update Request
     */
    private void validateUpdate(UpdateUserRequest request,
                                Long userId) {

        if (request == null) {
            throw new ValidationException("Request cannot be null.");
        }

        if (!RegexValidator.isValidEmail(request.getEmail())) {

            throw new ValidationException(
                    "email",
                    "Invalid email.");
        }

        if (!RegexValidator.isValidMobile(request.getMobile())) {

            throw new ValidationException(
                    "mobile",
                    "Invalid mobile number.");
        }

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
     * Login
     * JWT generation will be added after JwtUtil is created.
     */
    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userDAO.findByUsername(request.getUsername());

        if (user == null) {

            throw new AuthenticationException(
                    "Invalid username or password.");
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new AuthenticationException(
                    "Invalid username or password.");
        }

        if (!user.getEnabled()) {

            throw new AuthenticationException(
                    "User account is disabled.");
        }

        if (user.getAccountLocked()) {

            throw new AuthenticationException(
                    "User account is locked.");
        }

        /*
         * Temporary response.
         * JWT token will be generated in Phase 4.
         */
        return new LoginResponse(
                "",
                user.getUsername(),
                user.getRole().name(),
                "Login Successful");
    }
    /**
     * Get User By Id
     */
    @Override
    public UserResponse getUserById(Long userId) {

        User user = userDAO.findById(userId);

        if (user == null) {
            throw new ResourceNotFoundException(
                    "User",
                    userId);
        }

        return userMapper.toResponse(user);
    }

    /**
     * Get User By Username
     */
    @Override
    public UserResponse getUserByUsername(String username) {

        User user = userDAO.findByUsername(username);

        if (user == null) {
            throw new ResourceNotFoundException(
                    "User",
                    "username",
                    username);
        }

        return userMapper.toResponse(user);
    }

    /**
     * Get All Users
     */
    @Override
    public List<UserResponse> getAllUsers() {

        List<User> users = userDAO.findAll();

        return userMapper.toResponseList(users);
    }

    /**
     * Update User
     */
    @Override
    public UserResponse updateUser(Long userId,
                                   UpdateUserRequest request) {

        User user = userDAO.findById(userId);

        if (user == null) {

            throw new ResourceNotFoundException(
                    "User",
                    userId);
        }

        validateUpdate(request, userId);

        userMapper.updateEntity(user, request);

        User updatedUser = userDAO.update(user);

        return userMapper.toResponse(updatedUser);
    }

    /**
     * Check Whether Username Exists
     */
    private void validateUsername(String username) {

        if (!RegexValidator.isValidUsername(username)) {

            throw new ValidationException(
                    "username",
                    "Invalid username.");
        }
    }

    /**
     * Check Whether Email Exists
     */
    private void validateEmail(String email) {

        if (!RegexValidator.isValidEmail(email)) {

            throw new ValidationException(
                    "email",
                    "Invalid email.");
        }
    }

    /**
     * Check Whether Mobile Number Exists
     */
    private void validateMobile(String mobile) {

        if (!RegexValidator.isValidMobile(mobile)) {

            throw new ValidationException(
                    "mobile",
                    "Invalid mobile number.");
        }
    }

    /**
     * Check Password Strength
     */
    private void validatePassword(String password) {

        if (!RegexValidator.isValidPassword(password)) {

            throw new ValidationException(
                    "password",
                    "Password does not satisfy security policy.");
        }
    }
    /**
     * Delete User
     */
    @Override
    public void deleteUser(Long userId) {

        User user = userDAO.findById(userId);

        if (user == null) {
            throw new ResourceNotFoundException(
                    "User",
                    userId);
        }

        userDAO.delete(userId);
    }

    /**
     * Change Password
     */
    @Override
    public void changePassword(Long userId,
                               ChangePasswordRequest request) {

        User user = userDAO.findById(userId);

        if (user == null) {
            throw new ResourceNotFoundException(
                    "User",
                    userId);
        }

        if (!passwordEncoder.matches(
                request.getOldPassword(),
                user.getPassword())) {

            throw new AuthenticationException(
                    "Old password is incorrect.");
        }

        validatePassword(request.getNewPassword());

        if (passwordEncoder.matches(
                request.getNewPassword(),
                user.getPassword())) {

            throw new ValidationException(
                    "password",
                    "New password cannot be the same as the old password.");
        }

        user.setPassword(
                passwordEncoder.encode(request.getNewPassword()));

        userDAO.update(user);
    }

    /**
     * Reset Password
     */
    @Override
    public void resetPassword(String email) {

        validateEmail(email);

        User user = userDAO.findByEmail(email);

        if (user == null) {
            throw new ResourceNotFoundException(
                    "User",
                    "email",
                    email);
        }

        String temporaryPassword = generateTemporaryPassword();

        user.setPassword(
                passwordEncoder.encode(temporaryPassword));

        userDAO.update(user);

        /*
         * TODO:
         * Send temporary password through Email Service.
         */
    }

    /**
     * Generate Temporary Password
     */
    private String generateTemporaryPassword() {

        final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final String lower = "abcdefghijklmnopqrstuvwxyz";
        final String digits = "0123456789";
        final String special = "@#$%&*!";

        final String allCharacters =
                upper + lower + digits + special;

        StringBuilder password = new StringBuilder();

        password.append(
                upper.charAt(
                        (int) (Math.random() * upper.length())));

        password.append(
                lower.charAt(
                        (int) (Math.random() * lower.length())));

        password.append(
                digits.charAt(
                        (int) (Math.random() * digits.length())));

        password.append(
                special.charAt(
                        (int) (Math.random() * special.length())));

        while (password.length() < 10) {

            password.append(
                    allCharacters.charAt(
                            (int) (Math.random() * allCharacters.length())));
        }

        return password.toString();
    }

    /**
     * Check User Exists
     */
    private User getUserOrThrow(Long userId) {

        User user = userDAO.findById(userId);

        if (user == null) {

            throw new ResourceNotFoundException(
                    "User",
                    userId);
        }

        return user;
    }

    /**
     * Check User Exists By Username
     */
    private User getUserByUsernameOrThrow(String username) {

        User user = userDAO.findByUsername(username);

        if (user == null) {

            throw new ResourceNotFoundException(
                    "User",
                    "username",
                    username);
        }

        return user;
    }
    /**
     * Check whether user account is enabled.
     */
    private void validateUserStatus(User user) {

        if (Boolean.FALSE.equals(user.getEnabled())) {

            throw new AuthenticationException(
                    "User account is disabled.");
        }

        if (Boolean.TRUE.equals(user.getAccountLocked())) {

            throw new AuthenticationException(
                    "User account is locked.");
        }
    }

    /**
     * Validate duplicate email during update.
     */
    private void validateDuplicateEmail(String email,
                                        Long userId) {

        User existingUser = userDAO.findByEmail(email);

        if (existingUser != null &&
                !existingUser.getUserId().equals(userId)) {

            throw new DuplicateRecordException(
                    "User",
                    "email",
                    email);
        }
    }

    /**
     * Validate duplicate mobile during update.
     */
    private void validateDuplicateMobile(String mobile,
                                         Long userId) {

        User existingUser = userDAO.findByMobile(mobile);

        if (existingUser != null &&
                !existingUser.getUserId().equals(userId)) {

            throw new DuplicateRecordException(
                    "User",
                    "mobile",
                    mobile);
        }
    }

    /**
     * Encode Password
     */
    private String encodePassword(String password) {

        return passwordEncoder.encode(password);
    }

    /**
     * Verify Password
     */
    private boolean matchesPassword(String rawPassword,
                                    String encodedPassword) {

        return passwordEncoder.matches(
                rawPassword,
                encodedPassword);
    }

    /**
     * Build Login Response
     *
     * NOTE:
     * Replace "" with JWT Token after JwtUtil is implemented.
     */
    private LoginResponse buildLoginResponse(User user,
                                             String token) {

        LoginResponse response = new LoginResponse();

        response.setToken(token);

        response.setUsername(user.getUsername());

        response.setRole(user.getRole().name());

        response.setMessage("Login Successful");

        return response;
    }

    /**
     * Future Enhancement
     *
     * EmailService Integration
     * Audit Logging
     * Login History
     * Password Expiry
     * OTP Verification
     * Refresh Token Support
     */
}