import api from "./api";

const BASE_URL = "/sections";

const sectionService = {

    getAllSections: async () => {

        const response = await api.get(BASE_URL);

        return response.data;
    },

    getSectionById: async (id) => {

        const response = await api.get(`${BASE_URL}/${id}`);

        return response.data;
    },

    getSectionByCode: async (code) => {

        const response = await api.get(`${BASE_URL}/code/${code}`);

        return response.data;
    },

    getSectionsByCourse: async (courseId) => {

        const response = await api.get(
            `${BASE_URL}/course/${courseId}`
        );

        return response.data;
    },

    getSectionsBySemester: async (semesterId) => {

        const response = await api.get(
            `${BASE_URL}/semester/${semesterId}`
        );

        return response.data;
    },

    createSection: async (section) => {

        const response = await api.post(
            BASE_URL,
            section
        );

        return response.data;
    },

    updateSection: async (id, section) => {

        const response = await api.put(
            `${BASE_URL}/${id}`,
            section
        );

        return response.data;
    },

    deleteSection: async (id) => {

        const response = await api.delete(
            `${BASE_URL}/${id}`
        );

        return response.data;
    }

};

export default sectionService;