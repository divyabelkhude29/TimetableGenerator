import api from "./api";

/**
 * Get timetable
 *
 * Backend does not expose a "/timetable/view" endpoint -
 * the real, working endpoint is GET /timetables/section/{sectionId},
 * which returns the persisted timetable entries for that section.
 */
const getTimetable = async (
    academicYear,
    courseId,
    semesterId,
    sectionId
) => {

    const response = await api.get(
        `/timetables/section/${sectionId}`
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
 *
 * Real backend endpoint is POST /reports/pdf with a
 * ReportRequest body (only academicSectionId is required).
 */
const exportPdf = async (
    academicYear,
    courseId,
    semesterId,
    sectionId
) => {

    const response = await api.post(
        "/reports/pdf",
        {
            courseId,
            semesterId,
            academicSectionId: sectionId,
            reportType: "TIMETABLE",
            exportFormat: "PDF"
        },
        {
            responseType: "blob"
        }
    );

    return response.data;

};

/**
 * Export Excel
 *
 * Real backend endpoint is POST /reports/excel with a
 * ReportRequest body (only academicSectionId is required).
 */
const exportExcel = async (
    academicYear,
    courseId,
    semesterId,
    sectionId
) => {

    const response = await api.post(
        "/reports/excel",
        {
            courseId,
            semesterId,
            academicSectionId: sectionId,
            reportType: "TIMETABLE",
            exportFormat: "EXCEL"
        },
        {
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