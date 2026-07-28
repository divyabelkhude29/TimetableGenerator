import api from "./api";

const BASE_URL = "/faculties";

const facultyService = {

    // Get All Faculty
    getAllFaculty: async () => {

        const response = await api.get(BASE_URL);

        return response.data;

    },

    // Get Faculty By Id
    getFacultyById: async (id) => {

        const response = await api.get(`${BASE_URL}/${id}`);

        return response.data;

    },

    // Create Faculty
    createFaculty: async (faculty) => {

        const response = await api.post(
            BASE_URL,
            faculty
        );

        return response.data;

    },

    // Update Faculty
    updateFaculty: async (id, faculty) => {

        const response = await api.put(
            `${BASE_URL}/${id}`,
            faculty
        );

        return response.data;

    },

    // Delete Faculty
    deleteFaculty: async (id) => {

        const response = await api.delete(
            `${BASE_URL}/${id}`
        );

        return response.data;

    }

};

export default facultyService;