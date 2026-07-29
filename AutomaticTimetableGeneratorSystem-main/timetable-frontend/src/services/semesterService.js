import api from "./api";

const BASE_URL = "/semesters";

const semesterService = {

    /*
     * Get All Semesters
     */
    getAllSemesters: async () => {

        const response = await api.get(BASE_URL);

        return response.data;

    },

    /*
     * Get Semester By Id
     */
    getSemesterById: async (id) => {

        const response = await api.get(`${BASE_URL}/${id}`);

        return response.data;

    },

    /*
     * NEW
     * Get Semesters By Course
     */
    getSemestersByCourse: async (courseId) => {

        const response = await api.get(
            `${BASE_URL}/course/${courseId}`
        );

        return response.data;

    },

    /*
     * Create Semester
     */
    createSemester: async (semester) => {

        const response = await api.post(
            BASE_URL,
            semester
        );

        return response.data;

    },

    /*
     * Update Semester
     */
    updateSemester: async (id, semester) => {

        const response = await api.put(
            `${BASE_URL}/${id}`,
            semester
        );

        return response.data;

    },

    /*
     * Delete Semester
     */
    deleteSemester: async (id) => {

        const response = await api.delete(
            `${BASE_URL}/${id}`
        );

        return response.data;

    }

};

export default semesterService;