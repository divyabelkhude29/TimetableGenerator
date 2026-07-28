import api from "./api";

const BASE_URL = "/allocations";

const allocationService = {

    getAllAllocations: async () => {

        const response = await api.get(BASE_URL);

        return response.data;
    },

    getAllocationById: async (allocationId) => {

        const response = await api.get(
            `${BASE_URL}/${allocationId}`
        );

        return response.data;
    },

    getByFaculty: async (facultyId) => {

        const response = await api.get(
            `${BASE_URL}/faculty/${facultyId}`
        );

        return response.data;
    },

    getBySubject: async (subjectId) => {

        const response = await api.get(
            `${BASE_URL}/subject/${subjectId}`
        );

        return response.data;
    },

    getBySection: async (sectionId) => {

        const response = await api.get(
            `${BASE_URL}/section/${sectionId}`
        );

        return response.data;
    },

    getBySemester: async (semesterId) => {

        const response = await api.get(
            `${BASE_URL}/semester/${semesterId}`
        );

        return response.data;
    },

    createAllocation: async (allocation) => {

        const response = await api.post(
            BASE_URL,
            allocation
        );

        return response.data;
    },

    updateAllocation: async (
        allocationId,
        allocation
    ) => {

        const response = await api.put(
            `${BASE_URL}/${allocationId}`,
            allocation
        );

        return response.data;
    },

    deleteAllocation: async (
        allocationId
    ) => {

        const response = await api.delete(
            `${BASE_URL}/${allocationId}`
        );

        return response.data;
    }

};

export default allocationService;