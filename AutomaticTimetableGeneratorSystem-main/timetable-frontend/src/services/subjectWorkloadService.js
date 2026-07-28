import api from "./api";

const BASE_URL = "/subject-workloads";

const subjectWorkloadService = {

    // ==============================
    // GET ALL SUBJECT WORKLOADS
    // ==============================
    getAllSubjectWorkloads: async () => {

        const response = await api.get(BASE_URL);

        return response.data;

    },


    // ==============================
    // GET SUBJECT WORKLOAD BY ID
    // ==============================
    getSubjectWorkloadById: async (id) => {

        const response = await api.get(
            `${BASE_URL}/${id}`
        );

        return response.data;

    },


    // ==============================
    // GET BY ALLOCATION
    // ==============================
    getSubjectWorkloadByAllocation: async (allocationId) => {

        const response = await api.get(
            `${BASE_URL}/allocation/${allocationId}`
        );

        return response.data;

    },


    // ==============================
    // CREATE SUBJECT WORKLOAD
    // ==============================
    saveSubjectWorkload: async (data) => {

        const response = await api.post(
            BASE_URL,
            data
        );

        return response.data;

    },


    // ==============================
    // UPDATE SUBJECT WORKLOAD
    // ==============================
    updateSubjectWorkload: async (
        id,
        data
    ) => {

        const response = await api.put(
            `${BASE_URL}/${id}`,
            data
        );

        return response.data;

    },


    // ==============================
    // DELETE SUBJECT WORKLOAD
    // ==============================
    deleteSubjectWorkload: async (id) => {

        const response = await api.delete(
            `${BASE_URL}/${id}`
        );

        return response.data;

    }

};

export default subjectWorkloadService;