import api from "./api";

const BASE_URL = "/timetable-generation";

const timetableGenerationService = {

    // Generate Timetable
    generateTimetable: async (request) => {

        const response = await api.post(
            `${BASE_URL}/generate`,
            request
        );

        return response.data;

    },

    // Preview Timetable
    previewTimetable: async (request) => {

        const response = await api.post(
            `${BASE_URL}/preview`,
            request
        );

        return response.data;

    },

    // Validate Generation
    validateGeneration: async (request) => {

        const response = await api.post(
            `${BASE_URL}/validate`,
            request
        );

        return response.data;

    },

    // Regenerate Timetable
    regenerateTimetable: async (request) => {

        const response = await api.post(
            `${BASE_URL}/regenerate`,
            request
        );

        return response.data;

    },

    // Delete Generated Timetable
    deleteGeneratedTimetable: async (request) => {

        const response = await api.delete(
            BASE_URL,
            {
                data: request
            }
        );

        return response.data;

    }

};

export default timetableGenerationService;