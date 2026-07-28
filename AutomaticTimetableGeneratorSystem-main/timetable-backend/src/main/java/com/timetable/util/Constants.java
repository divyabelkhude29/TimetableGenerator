package com.timetable.util;

public final class Constants {

    private Constants() {
        // Prevent instantiation
    }

    /* ===========================
       API
       =========================== */

    public static final String API_BASE = "/api";

    public static final String AUTH_API = "/api/auth";

    public static final String ADMIN_API = "/api/admin";

    public static final String FACULTY_API = "/api/faculty";

    public static final String STUDENT_API = "/api/student";

    public static final String TIMETABLE_API = "/api/timetable";


    /* ===========================
       JWT
       =========================== */

    public static final String AUTHORIZATION = "Authorization";

    public static final String BEARER = "Bearer ";

    public static final String TOKEN_PREFIX = "Bearer ";


    /* ===========================
       DATE & TIME
       =========================== */

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String TIME_FORMAT = "HH:mm";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";


    /* ===========================
       COMMON MESSAGES
       =========================== */

    public static final String LOGIN_SUCCESS = "Login Successful";

    public static final String LOGIN_FAILED = "Invalid Username or Password";

    public static final String RECORD_SAVED = "Record Saved Successfully";

    public static final String RECORD_UPDATED = "Record Updated Successfully";

    public static final String RECORD_DELETED = "Record Deleted Successfully";

    public static final String RECORD_NOT_FOUND = "Record Not Found";

    public static final String DUPLICATE_RECORD = "Duplicate Record Found";

    public static final String ACCESS_DENIED = "Access Denied";


    /* ===========================
       STATUS
       =========================== */

    public static final String ACTIVE = "ACTIVE";

    public static final String INACTIVE = "INACTIVE";


    /* ===========================
       PAGINATION
       =========================== */

    public static final int DEFAULT_PAGE = 0;

    public static final int DEFAULT_SIZE = 10;


    /* ===========================
       VALIDATION
       =========================== */

    public static final int PASSWORD_MIN_LENGTH = 8;

    public static final int USERNAME_MIN_LENGTH = 4;

    public static final int USERNAME_MAX_LENGTH = 30;

}