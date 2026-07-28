import api from "./api";

const BASE_URL = "/faculties";

const facultyService = {

    // ==========================================
    // Get All Faculty
    // Existing method - DO NOT REMOVE
    // Used by Faculty Availability and other files
    // ==========================================
    getAllFaculty: async () => {

        const response = await api.get(BASE_URL);

        return response.data;

    },


    // ==========================================
    // Get All Faculties
    // Alias for AllocationForm
    // Keeps compatibility with both:
    //
    // facultyService.getAllFaculty()
    // facultyService.getAllFaculties()
    // ==========================================
    getAllFaculties: async () => {

        const response = await api.get(BASE_URL);

        return response.data;

    },


    // ==========================================
    // Get Faculty By ID
    // ==========================================
    getFacultyById: async (id) => {

        const response = await api.get(
            `${BASE_URL}/${id}`
        );

        return response.data;

    },


    // ==========================================
    // Create Faculty
    // ==========================================
    createFaculty: async (faculty) => {

        const response = await api.post(
            BASE_URL,
            faculty
        );

        return response.data;

    },


    // ==========================================
    // Update Faculty
    // ==========================================
    updateFaculty: async (id, faculty) => {

        const response = await api.put(
            `${BASE_URL}/${id}`,
            faculty
        );

        return response.data;

    },


    // ==========================================
    // Delete Faculty
    // ==========================================
    deleteFaculty: async (id) => {

        const response = await api.delete(
            `${BASE_URL}/${id}`
        );

        return response.data;

    }

};

export default facultyService;