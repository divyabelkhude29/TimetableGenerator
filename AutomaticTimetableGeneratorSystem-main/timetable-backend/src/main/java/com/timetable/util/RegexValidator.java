package com.timetable.util;

import java.util.regex.Pattern;

public final class RegexValidator {

    private RegexValidator() {
    }

    /* ===========================
       REGEX PATTERNS
       =========================== */

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private static final Pattern MOBILE_PATTERN =
            Pattern.compile("^[6-9]\\d{9}$");

    private static final Pattern USERNAME_PATTERN =
            Pattern.compile("^[A-Za-z][A-Za-z0-9_]{3,29}$");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,20}$");

    private static final Pattern ROOM_PATTERN =
            Pattern.compile("^[A-Za-z0-9-]{2,15}$");

    private static final Pattern SUBJECT_CODE_PATTERN =
            Pattern.compile("^[A-Z]{2,5}[0-9]{3}$");

    private static final Pattern DEPARTMENT_CODE_PATTERN =
            Pattern.compile("^[A-Z]{2,5}$");


    /* ===========================
       VALIDATION METHODS
       =========================== */

    public static boolean isValidEmail(String email) {
        return email != null &&
                EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidMobile(String mobile) {
        return mobile != null &&
                MOBILE_PATTERN.matcher(mobile).matches();
    }

    public static boolean isValidUsername(String username) {
        return username != null &&
                USERNAME_PATTERN.matcher(username).matches();
    }

    public static boolean isValidPassword(String password) {
        return password != null &&
                PASSWORD_PATTERN.matcher(password).matches();
    }

    public static boolean isValidRoomNumber(String roomNumber) {
        return roomNumber != null &&
                ROOM_PATTERN.matcher(roomNumber).matches();
    }

    public static boolean isValidSubjectCode(String subjectCode) {
        return subjectCode != null &&
                SUBJECT_CODE_PATTERN.matcher(subjectCode).matches();
    }

    public static boolean isValidDepartmentCode(String departmentCode) {
        return departmentCode != null &&
                DEPARTMENT_CODE_PATTERN.matcher(departmentCode).matches();
    }

}