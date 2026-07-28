import api from "./api";

const BASE_URL = "/faculty-availability";

const facultyAvailabilityService = {

    getAllAvailability: async () => {
        const response = await api.get(BASE_URL);
        return response.data;
    },

    getAvailabilityById: async (id) => {
        const response = await api.get(`${BASE_URL}/${id}`);
        return response.data;
    },

    createAvailability: async (data) => {
        const response = await api.post(BASE_URL, data);
        return response.data;
    },

    updateAvailability: async (id, data) => {
        const response = await api.put(`${BASE_URL}/${id}`, data);
        return response.data;
    },

    deleteAvailability: async (id) => {
        const response = await api.delete(`${BASE_URL}/${id}`);
        return response.data;
    },

    getByFaculty: async (facultyId) => {
        const response = await api.get(
            `${BASE_URL}/faculty/${facultyId}`
        );
        return response.data;
    },

    getByDay: async (day) => {
        const response = await api.get(
            `${BASE_URL}/day/${day}`
        );
        return response.data;
    },

    getByFacultyAndDay: async (facultyId, day) => {
        const response = await api.get(
            `${BASE_URL}/faculty/${facultyId}/day/${day}`
        );
        return response.data;
    }

};

export default facultyAvailabilityService;