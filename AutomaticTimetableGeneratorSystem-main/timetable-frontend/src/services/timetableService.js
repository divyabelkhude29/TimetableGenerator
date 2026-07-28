import api from "./api";

const BASE_URL = "/timetable";

/**
 * Get timetable
 */
const getTimetable = async (
    academicYear,
    courseId,
    semesterId,
    sectionId
) => {

    const response = await api.get(
        `${BASE_URL}/view`,
        {
            params: {
                academicYear,
                courseId,
                semesterId,
                sectionId
            }
        }
    );

    return response.data;

};

/**
 * Generate timetable
 */
const generateTimetable = async (request) => {

    const response = await api.post(
        "/timetable-generation/generate",
        request
    );

    return response.data;

};

/**
 * Preview timetable
 */
const previewTimetable = async (request) => {

    const response = await api.post(
        "/timetable-generation/preview",
        request
    );

    return response.data;

};

/**
 * Validate timetable
 */
const validateTimetable = async (request) => {

    const response = await api.post(
        "/timetable-generation/validate",
        request
    );

    return response.data;

};

/**
 * Regenerate timetable
 */
const regenerateTimetable = async (request) => {

    const response = await api.post(
        "/timetable-generation/regenerate",
        request
    );

    return response.data;

};

/**
 * Delete generated timetable
 */
const deleteTimetable = async (request) => {

    const response = await api.delete(
        "/timetable-generation",
        {
            data: request
        }
    );

    return response.data;

};

/**
 * Export PDF
 */
const exportPdf = async (
    academicYear,
    courseId,
    semesterId,
    sectionId
) => {

    const response = await api.get(
        `${BASE_URL}/export/pdf`,
        {
            params: {
                academicYear,
                courseId,
                semesterId,
                sectionId
            },
            responseType: "blob"
        }
    );

    return response.data;

};

/**
 * Export Excel
 */
const exportExcel = async (
    academicYear,
    courseId,
    semesterId,
    sectionId
) => {

    const response = await api.get(
        `${BASE_URL}/export/excel`,
        {
            params: {
                academicYear,
                courseId,
                semesterId,
                sectionId
            },
            responseType: "blob"
        }
    );

    return response.data;

};

const timetableService = {

    getTimetable,

    generateTimetable,

    previewTimetable,

    validateTimetable,

    regenerateTimetable,

    deleteTimetable,

    exportPdf,

    exportExcel

};

export default timetableService;