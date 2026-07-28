import api from "./api";

const BASE_URL = "/subject-workloads";

const subjectWorkloadService = {

    // Get All Subject Workloads
    getAllSubjectWorkloads: async () => {

        const response = await api.get(BASE_URL);

        return response.data;

    },

    // Get Subject Workload By Id
    getSubjectWorkloadById: async (id) => {

        const response = await api.get(
            `${BASE_URL}/${id}`
        );

        return response.data;

    },

    // Get Subject Workload By Allocation
    getSubjectWorkloadByAllocation: async (allocationId) => {

        const response = await api.get(
            `${BASE_URL}/allocation/${allocationId}`
        );

        return response.data;

    },

    // Create
    saveSubjectWorkload: async (data) => {

        const response = await api.post(
            BASE_URL,
            data
        );

        return response.data;

    },

    // Update
    updateSubjectWorkload: async (id, data) => {

        const response = await api.put(
            `${BASE_URL}/${id}`,
            data
        );

        return response.data;

    },

    // Delete
    deleteSubjectWorkload: async (id) => {

        const response = await api.delete(
            `${BASE_URL}/${id}`
        );

        return response.data;

    }

};

export default subjectWorkloadService;