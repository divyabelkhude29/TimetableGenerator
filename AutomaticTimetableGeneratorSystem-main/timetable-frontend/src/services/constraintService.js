import api from "./api";

const BASE_URL = "/timetable-constraints";

const constraintService = {

    // Get All Constraints
    getAllConstraints: async () => {

        const response = await api.get(BASE_URL);

        return response.data;

    },

    // Get Constraint By ID
    getConstraintById: async (id) => {

        const response = await api.get(
            `${BASE_URL}/${id}`
        );

        return response.data;

    },

    // Create Constraint
    createConstraint: async (constraint) => {

        const response = await api.post(
            BASE_URL,
            constraint
        );

        return response.data;

    },

    // Update Constraint
    updateConstraint: async (id, constraint) => {

        const response = await api.put(
            `${BASE_URL}/${id}`,
            constraint
        );

        return response.data;

    },

    // Delete Constraint
    deleteConstraint: async (id) => {

        const response = await api.delete(
            `${BASE_URL}/${id}`
        );

        return response.data;

    },

    // Get Constraint By Key
    getConstraintByKey: async (constraintKey) => {

        const response = await api.get(
            `${BASE_URL}/key/${constraintKey}`
        );

        return response.data;

    }

};

export default constraintService;