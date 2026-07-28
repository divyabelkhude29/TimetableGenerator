import api from "./api";

const BASE_URL = "/allocations";

const allocationService = {

    // ===========================
    // Get All Allocations
    // ===========================
    getAllAllocations: async () => {

        const response = await api.get(BASE_URL);

        return response.data;
    },

    // ===========================
    // Get Allocation By Id
    // ===========================
    getAllocationById: async (allocationId) => {

        const response = await api.get(
            `${BASE_URL}/${allocationId}`
        );

        return response.data;
    },

    // ===========================
    // Get By Faculty
    // ===========================
    getByFaculty: async (facultyId) => {

        const response = await api.get(
            `${BASE_URL}/faculty/${facultyId}`
        );

        return response.data;
    },

    // ===========================
    // Get By Subject
    // ===========================
    getBySubject: async (subjectId) => {

        const response = await api.get(
            `${BASE_URL}/subject/${subjectId}`
        );

        return response.data;
    },

    // ===========================
    // Get By Section
    // ===========================
    getBySection: async (sectionId) => {

        const response = await api.get(
            `${BASE_URL}/section/${sectionId}`
        );

        return response.data;
    },

    // ===========================
    // Get By Semester
    // ===========================
    getBySemester: async (semesterId) => {

        const response = await api.get(
            `${BASE_URL}/semester/${semesterId}`
        );

        return response.data;
    },

    // ===========================
    // Create Allocation
    // ===========================
    createAllocation: async (allocation) => {

        const response = await api.post(
            BASE_URL,
            allocation
        );

        return response.data;
    },

    // ===========================
    // Update Allocation
    // ===========================
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

    // ===========================
    // Delete Allocation
    // ===========================
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